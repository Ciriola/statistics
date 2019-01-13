package com.n26.transaction.service;

public class PastTimestampException extends Exception {

    public PastTimestampException(String message) {
        super(message);
    }

}
