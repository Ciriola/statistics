package com.n26.transaction.service;

/**
 * This exception is raised in two cases, the the transaction
 * timestamp is in the future and if one of the fields of the
 * transaction is not correct
 */
public class InvalidTransactionDataException extends Exception {

    public InvalidTransactionDataException(String message) {
        super(message);
    }
}
