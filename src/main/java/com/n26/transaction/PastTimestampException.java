package com.n26.transaction;

public class PastTimestampException extends Exception {

    public PastTimestampException(String message) {
        super(message);
    }

}
