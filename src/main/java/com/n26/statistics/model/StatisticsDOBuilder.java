package com.n26.statistics.model;

import java.math.BigDecimal;
import java.time.Instant;

public class StatisticsDOBuilder {

    private Instant timestamp;

    private BigDecimal sum;
    private BigDecimal max;
    private BigDecimal min;
    private long count;

    public StatisticsDOBuilder withTimestamp(Instant timestamp) {
        this.timestamp = timestamp;
        return this;
    }

    public StatisticsDOBuilder withSum(BigDecimal sum) {
        this.sum = sum;
        return this;
    }

    public StatisticsDOBuilder withMax(BigDecimal max) {
        this.max = max;
        return this;
    }

    public StatisticsDOBuilder withMin(BigDecimal min) {
        this.min = min;
        return this;
    }

    public StatisticsDOBuilder withCount(long count) {
        this.count = count;
        return this;
    }

    public StatisticsDO build() {
        return new StatisticsDO(timestamp, sum, max, min, count);
    }

}
