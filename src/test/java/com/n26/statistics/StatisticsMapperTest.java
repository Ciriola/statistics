package com.n26.statistics;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.math.BigDecimal;
import java.time.Instant;

@RunWith(JUnit4.class)
public class StatisticsMapperTest {

    private static final BigDecimal THREE_DECIMAL_PLACES_ROUNDING_UP = new BigDecimal("10.344");

    private static final BigDecimal THREE_DECIMAL_PLACES = new BigDecimal("10.345");
    private static final BigDecimal ONE_DECIMAL_PLACE = new BigDecimal("10.8");

    @Test
    public void convertBigDecimalWithMoreThanTwoDecimals_wellConvertedWithTwoDecimalPlacesAndRoundingUp() {
        final String formattedNumber = StatisticsMapper.convertBigDecimal(THREE_DECIMAL_PLACES);

        Assert.assertEquals("10.35", formattedNumber);
    }

    @Test
    public void convertBigDecimalWithJustOneDecimalPlace_wellConvertedWithTwoDecimalPlaces() {
        final String formattedNumber = StatisticsMapper.convertBigDecimal(ONE_DECIMAL_PLACE);

        Assert.assertEquals("10.80", formattedNumber);
    }

    @Test
    public void roundingUp_notRounded() {
        final String formattedNumber = StatisticsMapper.convertBigDecimal(THREE_DECIMAL_PLACES_ROUNDING_UP);

        Assert.assertEquals("10.34", formattedNumber);
    }

    @Test
    public void calculateAverageWhenStatisticIsEmpty_averageIsZero() {
        final BigDecimal average = StatisticsMapper.calculateAverage(new StatisticsDO());
        Assert.assertEquals(BigDecimal.ZERO, average);
    }

    @Test
    public void calculateAverageDefaultCase_averageIsCorrect() {
        StatisticsDO statisticsDO = new StatisticsDOBuilder()
                .withCount(3)
                .withSum(BigDecimal.TEN)
                .withMin(BigDecimal.ZERO)
                .withMax(BigDecimal.TEN).
                build();

        final BigDecimal average = StatisticsMapper.calculateAverage(statisticsDO);
        Assert.assertEquals(new BigDecimal("3.33"), average);
    }
}
