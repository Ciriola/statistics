package com.n26.transaction;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.math.BigDecimal;
import java.time.Instant;

@RunWith(JUnit4.class)
public class TransactionParserTest {

    private TransactionParser parser = new TransactionParser();

    private final static String EXAMPLE_AMOUNT = "12.3343";
    private final static String EXAMPLE_TIMESTAMP = "2018-07-17T09:59:51.312Z";


    @Test(expected = InvalidTransactionDataException.class)
    public void transactionWithInvalidAmount_raisingException() throws InvalidTransactionDataException {
        Transaction transaction = new Transaction("abcd", EXAMPLE_TIMESTAMP);
        parser.parseTransaction(transaction);
    }

    @Test(expected = InvalidTransactionDataException.class)
    public void transactionWithInvalidTimestamp_raisingException() throws InvalidTransactionDataException {
        Transaction transaction = new Transaction(EXAMPLE_AMOUNT, "abcd");
        parser.parseTransaction(transaction);
    }

    @Test
    public void transactionWithValidAmountAndTimestamp_returnsValidTransactionDO() throws InvalidTransactionDataException {
        BigDecimal expectedAmount = new BigDecimal(EXAMPLE_AMOUNT);
        Transaction transaction = new Transaction(EXAMPLE_AMOUNT, EXAMPLE_TIMESTAMP);
        TransactionDO actual = parser.parseTransaction(transaction);

        Instant expectedTimestamp = Instant.parse(EXAMPLE_TIMESTAMP);

        Assert.assertNotNull(actual);

        Assert.assertEquals(expectedAmount, actual.getAmount());
        Assert.assertEquals(expectedTimestamp, actual.getTimestamp());

    }
}
