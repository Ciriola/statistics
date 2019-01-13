package com.n26.transaction;

import com.n26.statistics.StatisticsAggregator;
import com.n26.statistics.StatisticsDO;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.math.BigDecimal;
import java.time.Clock;
import java.time.Instant;
import java.time.ZoneOffset;
import java.util.Arrays;

@RunWith(JUnit4.class)
public class TransactionServiceInternalTest {

    private static Clock clock;
    private static TimeValidator timeValidator;
    private static StatisticsAggregator statisticsAggregator;

    private TransactionService transactionService;

    private static final Instant NOW =              Instant.parse("2018-07-17T09:59:51.312Z");
    private static final Instant ONE_MIN_BEFORE =   Instant.parse("2018-07-17T09:58:51.312Z");

    @BeforeClass
    public static void beforeClass() {
        clock = Clock.fixed(NOW, ZoneOffset.UTC);
        timeValidator = new TimeValidator(clock);
        statisticsAggregator = new StatisticsAggregator();
    }

    @Before
    public void before() {
        transactionService = new TransactionService(statisticsAggregator, timeValidator);
    }

    @Test
    public void aggregateSingleTransactionToStatistic_aggregationDone() {
        final StatisticsDO statisticsDO = new StatisticsDO(new BigDecimal("20.2"), NOW);
        final TransactionDO transactionDO = new TransactionDO( new BigDecimal("10.01"), NOW);

        final StatisticsDO aggregate = transactionService.combineOrReset(statisticsDO, transactionDO);

        Assert.assertEquals(transactionDO.getTimestamp(), aggregate.getTimestamp());

        Assert.assertEquals(statisticsDO.getCount() + 1, aggregate.getCount());
        Assert.assertEquals(statisticsDO.getSum().add(transactionDO.getAmount()), aggregate.getSum());
        Assert.assertEquals(statisticsDO.getMax().max(transactionDO.getAmount()), aggregate.getMax());
        Assert.assertEquals(statisticsDO.getMin().min(transactionDO.getAmount()), aggregate.getMin());

    }

    @Test
    public void resetStatisticAtMinute_statisticResetWithTheNewValueOfTransaction() {
        final TransactionDO transactionDO = new TransactionDO(new BigDecimal("10.0"), NOW);
        final StatisticsDO statisticsDO = new StatisticsDO(new BigDecimal("20.0"), ONE_MIN_BEFORE);
        final StatisticsDO aggregate = transactionService.combineOrReset(statisticsDO, transactionDO);

        Assert.assertEquals(NOW, aggregate.getTimestamp());

        Assert.assertEquals(1, aggregate.getCount());
        Assert.assertEquals(transactionDO.getAmount(), aggregate.getSum());
        Assert.assertEquals(transactionDO.getAmount(), aggregate.getMax());
        Assert.assertEquals(transactionDO.getAmount(), aggregate.getMin());
    }

    @Test
    public void generateStatisticWhenNoStatistics_emptyStatisticReturned() {

        StatisticsDO[] statistics = new StatisticsDO[3];
        Arrays.fill(statistics, new StatisticsDO());

        final StatisticsDO statisticsDO = transactionService.generateStatistics(statistics);

        Assert.assertNotNull(statisticsDO);

        Assert.assertEquals(0, statisticsDO.getCount());
        Assert.assertEquals(BigDecimal.ZERO, statisticsDO.getSum());
        Assert.assertEquals(BigDecimal.ZERO, statisticsDO.getMax());
        Assert.assertEquals(BigDecimal.ZERO, statisticsDO.getMin());

    }

    @Test
    public void generateStatisticWhenOneStatisticsAdded_theSpecificStatisticReturned() {

        StatisticsDO statistic = new StatisticsDO(BigDecimal.TEN, clock.instant());

        StatisticsDO[] statistics = new StatisticsDO[3];
        Arrays.fill(statistics, new StatisticsDO());

        statistics[1] = statistic;

        final StatisticsDO statisticsDO = transactionService.generateStatistics(statistics);

        Assert.assertNotNull(statisticsDO);

        Assert.assertEquals(1, statisticsDO.getCount());
        Assert.assertEquals(BigDecimal.TEN, statisticsDO.getSum());
        Assert.assertEquals(BigDecimal.TEN, statisticsDO.getMax());
        Assert.assertEquals(BigDecimal.TEN, statisticsDO.getMin());

    }

    @Test
    public void generateStatisticWhenArrayOfStatisticsIsFull_combinationOfStatsReturned() {

        StatisticsDO statistic = new StatisticsDO(BigDecimal.TEN, clock.instant());
        StatisticsDO statisticTwo = new StatisticsDO(BigDecimal.ONE, clock.instant());

        StatisticsDO[] statistics = new StatisticsDO[3];
        Arrays.fill(statistics, new StatisticsDO());

        statistics[0] = statistic;
        statistics[1] = statisticTwo;
        statistics[2] = statistic;

        final StatisticsDO statisticsDO = transactionService.generateStatistics(statistics);

        Assert.assertNotNull(statisticsDO);

        Assert.assertEquals(3, statisticsDO.getCount());
        Assert.assertEquals(new BigDecimal(21), statisticsDO.getSum());
        Assert.assertEquals(BigDecimal.TEN, statisticsDO.getMax());
        Assert.assertEquals(BigDecimal.ONE, statisticsDO.getMin());

    }

}
