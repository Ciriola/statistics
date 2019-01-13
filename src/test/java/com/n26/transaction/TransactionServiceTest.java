package com.n26.transaction;

import com.n26.statistics.StatisticsAggregator;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.math.BigDecimal;
import java.time.Clock;
import java.time.Instant;
import java.time.ZoneOffset;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;

@RunWith(MockitoJUnitRunner.class)
public class TransactionServiceTest {

    private static Clock clock;
    private static final Instant NOW = Instant.parse("2018-07-17T09:59:51.312Z");

    @Mock
    private TimeValidator validator;

    @Mock
    private StatisticsAggregator aggregator;

    @InjectMocks
    private TransactionService service;

    @BeforeClass
    public static void beforeClass() {
        clock = Clock.fixed(NOW, ZoneOffset.UTC);
    }

    @Test
    public void addTransaction() throws InvalidTransactionDataException, PastTimestampException {

        TransactionDO transactionDO = new TransactionDO(BigDecimal.TEN, clock.instant());
        doNothing().when(validator).validate(any(Instant.class));

        service.addTransaction(transactionDO);
    }

}
