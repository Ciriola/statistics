package com.n26.transaction;

public class InvalidTransactionDataException extends Exception {

    public InvalidTransactionDataException(String message) {
        super(message);
    }
}
