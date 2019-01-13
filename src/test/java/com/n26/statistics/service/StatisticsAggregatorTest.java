package com.n26.statistics.service;

import com.n26.statistics.model.StatisticsDO;
import com.n26.statistics.model.StatisticsDOBuilder;
import com.n26.statistics.service.StatisticsAggregator;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.math.BigDecimal;

@RunWith(JUnit4.class)
public class StatisticsAggregatorTest {

    private static StatisticsAggregator statisticsAggregator;

    @BeforeClass
    public static void beforeClass() {
        statisticsAggregator = new StatisticsAggregator();
    }

    @Test
    public void addTransactionAmountToEmptyStatistic_statisticContainsTransactionValues() {
        final StatisticsDO statistics = new StatisticsDO();
        final StatisticsDO actual = statisticsAggregator.add(statistics, BigDecimal.TEN);

        Assert.assertEquals(1, actual.getCount());
        Assert.assertEquals(BigDecimal.TEN, actual.getSum());
        Assert.assertEquals(BigDecimal.TEN, actual.getMax());
        Assert.assertEquals(BigDecimal.TEN, actual.getMin());
    }

    @Test
    public void addTransactionAmountToAStatistic_statisticWillBeACombination() {
        final StatisticsDO statisticOne = new StatisticsDOBuilder()
                .withCount(1)
                .withSum(BigDecimal.TEN)
                .withMax(BigDecimal.TEN)
                .withMin(BigDecimal.TEN)
                .build();

        final StatisticsDO statisticTwo = new StatisticsDOBuilder()
                .withCount(1)
                .withSum(BigDecimal.ONE)
                .withMax(BigDecimal.ONE)
                .withMin(BigDecimal.ONE)
                .build();

        final StatisticsDO actual = statisticsAggregator.combine(statisticOne, statisticTwo);

        Assert.assertEquals(2, actual.getCount());
        Assert.assertEquals(new BigDecimal("11"), actual.getSum());
        Assert.assertEquals(BigDecimal.TEN, actual.getMax());
        Assert.assertEquals(BigDecimal.ONE, actual.getMin());
    }

    @Test
    public void addTransactionAmountToASecondEmptyStatistic_statisticWillBeACombination() {
        final StatisticsDO statisticOne = new StatisticsDOBuilder()
                .withCount(1)
                .withSum(BigDecimal.TEN)
                .withMax(BigDecimal.TEN)
                .withMin(BigDecimal.TEN)
                .build();

        final StatisticsDO statisticTwo = new StatisticsDO();

        final StatisticsDO actual = statisticsAggregator.combine(statisticOne, statisticTwo);

        Assert.assertEquals(1, actual.getCount());
        Assert.assertEquals(BigDecimal.TEN, actual.getSum());
        Assert.assertEquals(BigDecimal.TEN, actual.getMax());
        Assert.assertEquals(BigDecimal.TEN, actual.getMin());
    }

    @Test
    public void addTransactionAmountToAFirstEmptyStatistic_statisticWillBeACombination() {
        final StatisticsDO statisticOne = new StatisticsDO();

        final StatisticsDO statisticTwo = new StatisticsDOBuilder()
                .withCount(1)
                .withSum(BigDecimal.TEN)
                .withMax(BigDecimal.TEN)
                .withMin(BigDecimal.TEN)
                .build();

        final StatisticsDO actual = statisticsAggregator.combine(statisticOne, statisticTwo);

        Assert.assertEquals(1, actual.getCount());
        Assert.assertEquals(BigDecimal.TEN, actual.getSum());
        Assert.assertEquals(BigDecimal.TEN, actual.getMax());
        Assert.assertEquals(BigDecimal.TEN, actual.getMin());
    }
}
