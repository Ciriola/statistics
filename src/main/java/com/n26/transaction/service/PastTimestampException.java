package com.n26.transaction.service;

/**
 * This exception is raised when the transaction timestamp
 * is older than 60 seconds
 */
public class PastTimestampException extends Exception {

    public PastTimestampException(String message) {
        super(message);
    }

}
