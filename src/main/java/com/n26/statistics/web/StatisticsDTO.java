package com.n26.statistics.web;

public class StatisticsDTO {

    private String sum;
    private String avg;
    private String max;
    private String min;
    private long count;

    public StatisticsDTO(String sum, String avg, String max, String min, long count) {
        this.sum = sum;
        this.avg = avg;
        this.max = max;
        this.min = min;
        this.count = count;
    }

    public String getSum() {
        return sum;
    }

    public String getAvg() {
        return avg;
    }

    public String getMax() {
        return max;
    }

    public String getMin() {
        return min;
    }

    public long getCount() {
        return count;
    }

    public static class StatisticDTOBuilder {

        private String sum;
        private String avg;
        private String max;
        private String min;
        private long count;

        public StatisticDTOBuilder withSum(String sum) {
            this.sum = sum;
            return this;
        }

        public StatisticDTOBuilder withAvg(String avg) {
            this.avg = avg;
            return this;
        }

        public StatisticDTOBuilder withMax(String max) {
            this.max = max;
            return this;
        }

        public StatisticDTOBuilder withMin(String min) {
            this.min = min;
            return this;
        }

        public StatisticDTOBuilder withCount(long count) {
            this.count = count;
            return this;
        }

        public StatisticsDTO build() {
            return new StatisticsDTO(this.sum, this.avg, this.max, this.min, this.count);
        }
    }
}
