package com.n26.error;

import com.n26.transaction.InvalidJsonException;
import com.n26.transaction.InvalidTransactionDataException;
import com.n26.transaction.PastTimestampException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class UncaughtExceptionHandler {

    private static final Logger LOG = LoggerFactory.getLogger(UncaughtExceptionHandler.class);

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @ExceptionHandler(PastTimestampException.class)
    public void handlePastTimestamp(PastTimestampException e) {
        LOG.debug("The timestamp is in the past. {} ", e.getMessage());
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(InvalidJsonException.class)
    public void handleInvalidJson(InvalidJsonException e) {
        LOG.debug("The json is invalid. {} ", e.getMessage());
    }

    @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
    @ExceptionHandler(InvalidTransactionDataException.class)
    public void handleInvalidTransactionData(InvalidTransactionDataException e) {
        LOG.debug("One of the transaction fields is not correct. {} ", e.getMessage());
    }
}
