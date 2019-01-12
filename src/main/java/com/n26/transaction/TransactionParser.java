package com.n26.transaction;

import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.format.DateTimeParseException;

@Component
public class TransactionParser {

    TransactionDO parseTransaction(Transaction transaction) throws InvalidTransactionDataException {
        BigDecimal amount = parseAmount(transaction);
        Instant timestamp = parseTimestamp(transaction);
        return new TransactionDO(amount, timestamp);
    }

    BigDecimal parseAmount(Transaction transaction) throws InvalidTransactionDataException {
        try{
            return new BigDecimal(transaction.getAmount());

        } catch(NumberFormatException e) {
            throw new InvalidTransactionDataException("Transaction amount : " + transaction.getAmount());
        }
    }

    Instant parseTimestamp(Transaction transaction) throws InvalidTransactionDataException {
        try{
            return Instant.parse(transaction.getTimestamp());
        } catch(DateTimeParseException e) {
            throw new InvalidTransactionDataException("Transaction timestamp : " + transaction.getTimestamp());
        }
    }

}
