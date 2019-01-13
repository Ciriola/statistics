package com.n26.transaction.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.Clock;
import java.time.Instant;

@Component
public class TimeValidator {

    public static final int TIME_UNITS = 60000;

    private final Clock clock;

    @Autowired
    public TimeValidator(Clock clock) {
        this.clock = clock;
    }

    void validate(Instant timestamp) throws PastTimestampException, InvalidTransactionDataException {
        checkIsNotInTheFuture(timestamp);
        checkIsNotExpired(timestamp);
    }

    private void checkIsNotInTheFuture(Instant timestamp) throws InvalidTransactionDataException {
        Instant current = clock.instant();
        if(timestamp.isAfter(current)) {
            throw new InvalidTransactionDataException("Transaction is in the future. " +
                    "Current : " + current + " , Transaction : " + timestamp);
        }
    }

    private void checkIsNotExpired(Instant timestamp) throws PastTimestampException {
        Instant current = clock.instant();
        if(isExpired(timestamp, current)) {
            throw new PastTimestampException("Transaction has expired. " +
                    "Current : " + current + " , Transaction : " + timestamp);
        }
    }

    private boolean isExpired(Instant timestamp, Instant current) {
        return !timestamp.plusMillis(TIME_UNITS).isAfter(current);
    }

    boolean isExpired(Instant timestamp){
        return isExpired(timestamp, clock.instant());
    }
}
