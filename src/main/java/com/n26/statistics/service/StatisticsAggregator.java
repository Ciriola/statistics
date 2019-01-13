package com.n26.statistics.service;

import com.n26.statistics.model.StatisticsDO;
import com.n26.statistics.model.StatisticsDOBuilder;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class StatisticsAggregator {

    /**
     * This method is adding a given amount to a statistic.
     * The statistic could be empty (with all zeros)
     *
     * @param statistics the statistics a
     * @param amount the amount to add
     * @return the updated statistic
     */
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

    /**
     * This method is combining two statistics.
     *
     * @param first the first statistic
     * @param second the second statistic
     * @return the combined statistic
     */
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
