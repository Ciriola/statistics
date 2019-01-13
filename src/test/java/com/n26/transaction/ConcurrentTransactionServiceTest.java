package com.n26.transaction;

import com.n26.statistics.StatisticsAggregator;
import com.n26.statistics.StatisticsDO;
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
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class ConcurrentTransactionServiceTest {

    private static Clock clock;
    private static final Instant NOW = Instant.parse("2018-07-17T09:59:51.312Z");

    @Mock
    private TransactionService transactionService;

    @InjectMocks
    private ConcurrentTransactionService service;

    @BeforeClass
    public static void beforeClass() {
        clock = Clock.fixed(NOW, ZoneOffset.UTC);
    }

    @Test
    public void addTransaction_validateAndCombineCalled() throws InvalidTransactionDataException, PastTimestampException {

        TransactionDO transactionDO = new TransactionDO(BigDecimal.TEN, clock.instant());

        service.addTransaction(transactionDO);
        verify(transactionService, times(1)).validate(transactionDO.getTimestamp());
        verify(transactionService, times(1)).combineOrReset(any(StatisticsDO.class), eq(transactionDO));

    }

    @Test
    public void getStatistics() {

        service.getStatistics();
        verify(transactionService, times(1)).generateStatistics(any(StatisticsDO[].class));

    }

}
