package com.n26.statistics;

import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class StatisticsAggregator {

    public StatisticsDO add(StatisticsDO statistics, BigDecimal amount) {

        BigDecimal minimum = statistics.getCount() == 0 ? amount : statistics.getMin().min(amount);
        BigDecimal maximum = statistics.getCount() == 0 ? amount : statistics.getMax().max(amount);

        return new StatisticsDOBuilder()
                .withCount(statistics.getCount() + 1)
                .withSum(statistics.getSum().add(amount))
                .withMax(maximum)
                .withMin(minimum)
                .withTimestamp(statistics.getTimestamp())
                .build();
    }

    public StatisticsDO combine(StatisticsDO first, StatisticsDO second) {

        if(first.getCount() == 0) {
            return second;
        }

        if(second.getCount() == 0) {
            return first;
        }

        return new StatisticsDOBuilder()
                .withCount(first.getCount() + second.getCount())
                .withSum(first.getSum().add(second.getSum()))
                .withMax(first.getMax().max(second.getMax()))
                .withMin(first.getMin().min(second.getMin()))
                .build();
    }

}
