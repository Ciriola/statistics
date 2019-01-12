package com.n26.transaction;

import com.n26.statistics.StatisticsAggregator;
import com.n26.statistics.StatisticsDO;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Arrays;
import java.util.concurrent.locks.ReentrantLock;

import static com.n26.transaction.TimeValidator.TIME_UNITS;

@Service
public class TransactionService {

    private final ReentrantLock lock = new ReentrantLock();

    private final StatisticsAggregator aggregator;
    private final TimeValidator timeValidator;

    // statistics aggregated per millisecond
    private final StatisticsDO[] oneMinuteStats = new StatisticsDO[TIME_UNITS];

    TransactionService(StatisticsAggregator aggregator, TimeValidator timeValidator) {
        this.aggregator = aggregator;
        this.timeValidator = timeValidator;
        Arrays.fill(oneMinuteStats, new StatisticsDO());
    }

    /**
     * This methods inserts the transaction (through its timestamp)
     * into the array, combining its amount with the amount
     * of possible transaction already present in the same array cell
     * (representing a specific millisecond
     * Just its amount if no other transaction present.
     *
     * @param parsedRequest the transaction to be added if no exception happens
     * @throws InvalidTransactionDataException
     * @throws PastTimestampException
     */
    public void addTransaction(TransactionDO parsedRequest) throws InvalidTransactionDataException, PastTimestampException {
        Instant timestamp = parsedRequest.getTimestamp();

        try {

            lock.lock();

            timeValidator.validate(timestamp);

            int index = (int) (timestamp.toEpochMilli() % TIME_UNITS);

            oneMinuteStats[index] = combineOrReset(oneMinuteStats[index], parsedRequest);

        } finally {

            lock.unlock();
        }
    }

    StatisticsDO combineOrReset(StatisticsDO stat, TransactionDO transaction) {
        if (timeValidator.isExpired(stat.getTimestamp())) {
            final Instant timestamp = transaction.getTimestamp();
            return new StatisticsDO(transaction.getAmount(), timestamp.truncatedTo(ChronoUnit.MILLIS));
        } else {
            return aggregator.add(stat, transaction.getAmount());
        }
    }

    public void deleteTransactions() {
        try {
            lock.lock();

            Arrays.fill(oneMinuteStats, new StatisticsDO());
        } finally {
            lock.unlock();
        }
    }
}
