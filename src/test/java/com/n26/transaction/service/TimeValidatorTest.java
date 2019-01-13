package com.n26.transaction.service;

import com.n26.transaction.service.InvalidTransactionDataException;
import com.n26.transaction.service.PastTimestampException;
import com.n26.transaction.service.TimeValidator;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.time.Clock;
import java.time.Instant;
import java.time.ZoneOffset;

@RunWith(JUnit4.class)
public class TimeValidatorTest {

    private static TimeValidator timeValidator;

    private static final Instant NOW = Instant.parse("2018-07-17T09:59:51.312Z");

    private static final Instant PAST_LIMIT_OK = Instant.parse("2018-07-17T09:58:51.313Z");
    private static final Instant PAST_LIMIT_KO = Instant.parse("2018-07-17T09:58:51.312Z");
    private static final Instant FUTURE = Instant.parse("2018-07-17T09:59:51.313Z");

    @BeforeClass
    public static void beforeClass() {
        Clock clock = Clock.fixed(NOW, ZoneOffset.UTC);
        timeValidator = new TimeValidator(clock);
    }

    @Test(expected = PastTimestampException.class)
    public void addTransactionWithTimestampInThePast_exceptionRaised()
            throws InvalidTransactionDataException, PastTimestampException {

        timeValidator.validate(PAST_LIMIT_KO);
    }

    @Test(expected = InvalidTransactionDataException.class)
    public void addTransactionWithTimestampInTheFuture_exceptionRaised()
            throws InvalidTransactionDataException, PastTimestampException {

        timeValidator.validate(FUTURE);
    }

    @Test
    public void addTransactionWithTimestampInThePastLimitCase_checkOk()
            throws InvalidTransactionDataException, PastTimestampException {

        timeValidator.validate(PAST_LIMIT_OK);
    }

    @Test
    public void addTransactionWithValidTimestamp_checkOk()
            throws InvalidTransactionDataException, PastTimestampException {

        timeValidator.validate(NOW);
    }

}
