package com.n26.transaction.service;

import com.n26.statistics.service.StatisticsAggregator;
import com.n26.statistics.model.StatisticsDO;
import com.n26.transaction.model.TransactionDO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Arrays;

@Service
public class TransactionService {

    private final StatisticsAggregator aggregator;
    private final TimeValidator timeValidator;

    @Autowired
    TransactionService(StatisticsAggregator aggregator, TimeValidator timeValidator) {
        this.aggregator = aggregator;
        this.timeValidator = timeValidator;
    }

    void validate(Instant timestamp) throws PastTimestampException, InvalidTransactionDataException {
        timeValidator.validate(timestamp);
    }

    StatisticsDO generateStatistics(StatisticsDO[] oneMinuteStats) {
        return Arrays.stream(oneMinuteStats)
                .filter(e -> !timeValidator.isExpired(e.getTimestamp()))
                .reduce(aggregator::combine)
                .orElseGet(StatisticsDO::new);
    }

    StatisticsDO combineOrReset(StatisticsDO stat, TransactionDO transaction) {
        if (timeValidator.isExpired(stat.getTimestamp())) {
            final Instant timestamp = transaction.getTimestamp();
            return new StatisticsDO(transaction.getAmount(), timestamp.truncatedTo(ChronoUnit.MILLIS));
        } else {
            return aggregator.add(stat, transaction.getAmount());
        }
    }
}
