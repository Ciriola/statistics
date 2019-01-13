package com.n26.transaction.service;

import com.n26.statistics.model.StatisticsDO;
import com.n26.transaction.model.TransactionDO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Arrays;
import java.util.concurrent.locks.ReentrantReadWriteLock;

import static com.n26.transaction.service.TimeValidator.TIME_UNITS;

@Service
public class ConcurrentTransactionService {

    private final ReentrantReadWriteLock lock = new ReentrantReadWriteLock();

    private final TransactionService transactionService;

    // statistics aggregated per millisecond
    private final StatisticsDO[] oneMinuteStats = new StatisticsDO[TIME_UNITS];

    @Autowired
    ConcurrentTransactionService(TransactionService transactionService) {
        this.transactionService = transactionService;
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

            lock.writeLock().lock();

            transactionService.validate(timestamp);

            int index = (int) (timestamp.toEpochMilli() % TIME_UNITS);

            oneMinuteStats[index] = transactionService.combineOrReset(oneMinuteStats[index], parsedRequest);

        } finally {

            lock.writeLock().unlock();
        }
    }

    public void deleteTransactions() {
        try {
            lock.writeLock().lock();

            Arrays.fill(oneMinuteStats, new StatisticsDO());
        } finally {
            lock.writeLock().unlock();
        }
    }

    public StatisticsDO getStatistics() {

        try {
            lock.readLock().lock();

            return transactionService.generateStatistics(oneMinuteStats);

        } finally {
            lock.readLock().unlock();
        }
    }

}
