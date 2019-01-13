package com.n26.statistics;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Locale;

public class StatisticsMapper {

    public static StatisticsDTO map(StatisticsDO statisticsDO) {

        return new StatisticsDTO.StatisticDTOBuilder()
                .withCount(statisticsDO.getCount())
                .withSum(convertBigDecimal( statisticsDO.getSum()))
                .withMax(convertBigDecimal( statisticsDO.getMax()))
                .withMin(convertBigDecimal( statisticsDO.getMin()))
                .withAvg(convertBigDecimal(calculateAverage(statisticsDO)))
                .build();
    }

    static BigDecimal calculateAverage(StatisticsDO statisticsDO) {
        if(statisticsDO.getCount() == 0) {
            return BigDecimal.ZERO;
        }
        return statisticsDO.getSum().divide(new BigDecimal(statisticsDO.getCount()), 2, RoundingMode.HALF_UP);
    }

    static String convertBigDecimal(BigDecimal bigDecimal) {
        final DecimalFormatSymbols decimalFormatSymbols = new DecimalFormatSymbols(Locale.ROOT);

        final DecimalFormat df = new DecimalFormat();
        df.setRoundingMode(RoundingMode.HALF_UP);
        df.setMaximumFractionDigits(2);
        df.setMinimumFractionDigits(2);
        df.setGroupingUsed(false);
        df.setDecimalFormatSymbols(decimalFormatSymbols);

        return df.format(bigDecimal);
    }
}
