package com.n26.statistics;

import com.n26.transaction.ConcurrentTransactionService;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.math.BigDecimal;
import java.time.Instant;

import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class StatisticsControllerTest {

    @Mock
    private ConcurrentTransactionService service;

    @InjectMocks
    private StatisticsController controller;

    @Test
    public void testStatisticDTO_itReturnsAsExpected() {

        when(service.getStatistics()).thenReturn(new StatisticsDO(BigDecimal.TEN, Instant.EPOCH));
        final StatisticsDTO actual = controller.getStatistics();

        Assert.assertNotNull(actual);

        Assert.assertEquals(1, actual.getCount());
        Assert.assertEquals("10.00", actual.getSum());
        Assert.assertEquals("10.00", actual.getAvg());
        Assert.assertEquals("10.00", actual.getMax());
        Assert.assertEquals("10.00", actual.getMin());

    }
}
