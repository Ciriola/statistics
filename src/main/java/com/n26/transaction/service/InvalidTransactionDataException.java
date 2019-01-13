package com.n26.transaction.service;

public class InvalidTransactionDataException extends Exception {

    public InvalidTransactionDataException(String message) {
        super(message);
    }
}
