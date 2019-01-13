package com.n26.statistics.model;

import java.math.BigDecimal;
import java.time.Instant;

public class StatisticsDO {

    private final Instant timestamp;

    private final BigDecimal sum;
    private final BigDecimal max;
    private final BigDecimal min;
    private final long count;

    public StatisticsDO() {
        this.sum = BigDecimal.ZERO;
        this.max = BigDecimal.ZERO;
        this.min = BigDecimal.ZERO;
        this.count = 0;

        this.timestamp = Instant.EPOCH;
    }

    public StatisticsDO(BigDecimal value, Instant timestamp) {
        this.sum = value;
        this.max = value;
        this.min = value;
        this.count = 1;

        this.timestamp = timestamp;
    }

    public StatisticsDO(Instant timestamp, BigDecimal sum, BigDecimal max, BigDecimal min, long count) {
        this.timestamp = timestamp;
        this.sum = sum;
        this.max = max;
        this.min = min;
        this.count = count;
    }

    public Instant getTimestamp() {
        return timestamp;
    }

    public BigDecimal getSum() {
        return sum;
    }

    public BigDecimal getMax() {
        return max;
    }

    public BigDecimal getMin() {
        return min;
    }

    public long getCount() {
        return count;
    }
}
