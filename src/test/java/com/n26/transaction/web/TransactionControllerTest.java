package com.n26.transaction.web;

import com.n26.transaction.model.TransactionDO;
import com.n26.transaction.service.ConcurrentTransactionService;
import com.n26.transaction.service.InvalidTransactionDataException;
import com.n26.transaction.service.PastTimestampException;
import com.n26.transaction.web.TransactionController;
import com.n26.transaction.web.TransactionDTO;
import com.n26.transaction.web.TransactionParser;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.math.BigDecimal;
import java.time.Instant;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class TransactionControllerTest {

    @Mock
    private ConcurrentTransactionService service;

    @Mock
    private TransactionParser parser;

    @InjectMocks
    private TransactionController controller;

    @Test
    public void testAddTransaction_serviceCalled() throws InvalidTransactionDataException, PastTimestampException {

        final TransactionDTO transactionDTO = new TransactionDTO.TransactionDTOBuilder()
                .withAmount("12.3343")
                .withTimestamp("2018-07-17T09:59:51.312Z")
                .build();

        final TransactionDO transactionDO = new TransactionDO(new BigDecimal("12.33"), Instant.EPOCH);
        when(parser.parseTransaction(transactionDTO)).thenReturn(transactionDO);

        controller.addTransaction(transactionDTO);

        verify(service, times(1)).addTransaction(transactionDO);
    }

    @Test
    public void testDeleteTransaction_serviceCalled() {
        controller.deleteTransactions();

        verify(service, times(1)).deleteTransactions();
    }
}
