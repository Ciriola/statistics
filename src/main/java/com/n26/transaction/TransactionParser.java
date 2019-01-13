package com.n26.transaction;

import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.Instant;
import java.time.format.DateTimeParseException;

@Component
public class TransactionParser {

    TransactionDO parseTransaction(TransactionDTO transactionDTO) throws InvalidTransactionDataException {
        BigDecimal amount = parseAmount(transactionDTO);
        Instant timestamp = parseTimestamp(transactionDTO);
        return new TransactionDO(amount, timestamp);
    }

    BigDecimal parseAmount(TransactionDTO transactionDTO) throws InvalidTransactionDataException {
        try{
            return new BigDecimal(transactionDTO.getAmount()).setScale(2, RoundingMode.HALF_UP);

        } catch(NumberFormatException e) {
            throw new InvalidTransactionDataException("Transaction amount : " + transactionDTO.getAmount());
        }
    }

    Instant parseTimestamp(TransactionDTO transactionDTO) throws InvalidTransactionDataException {
        try{
            return Instant.parse(transactionDTO.getTimestamp());
        } catch(DateTimeParseException e) {
            throw new InvalidTransactionDataException("Transaction timestamp : " + transactionDTO.getTimestamp());
        }
    }

}
