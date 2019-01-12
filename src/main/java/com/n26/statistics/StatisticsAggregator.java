package com.n26.statistics;

import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class StatisticsAggregator {

    public StatisticsDO add(StatisticsDO statistics, BigDecimal amount) {
        return new StatisticsDOBuilder()
                .withCount(statistics.getCount()+1)
                .withSum(statistics.getSum().add(amount))
                .withMax(statistics.getMax().max(amount))
                .withMin(statistics.getMin().min(amount))
                .withTimestamp(statistics.getTimestamp())
                .build();
    }

    public StatisticsDO combine(StatisticsDO first, StatisticsDO second) {
        return new StatisticsDOBuilder()
                .withCount(first.getCount()+second.getCount())
                .withSum(first.getSum().add(second.getSum()))
                .withMax(first.getMax().max(second.getMax()))
                .withMin(first.getMin().min(second.getMin()))
                .build();
    }

}
