package com.trxmgmt.service;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.trxmgmt.model.Statistics;
import com.trxmgmt.model.Transaction;
import com.trxmgmt.util.SystemUtcClock;

import com.trxmgmt.util.Clock;

import java.math.BigDecimal;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * transaction statistics Services
 *
 * @author Sushil Mukundappanavar
 */
@Service
public class TrxStatService {

    
	public static int durationForStats=300;


    private final Statistics[] statistics = new Statistics[durationForStats];

    /**
     * stores all the reentrant locks for transaction statistics.
     */
    private final Lock[] locks = new Lock[statistics.length];

    /**
     * Corresponding seconds for transaction statistics.
     */
    private final long[] seconds = new long[statistics.length];

    /**
     * Clock for getting current time.
     */
    private final Clock clock;

    public TrxStatService() {
        this(new SystemUtcClock());
    }

    public TrxStatService(Clock clock) {
        this.clock = clock;
        for (int i = 0; i < statistics.length; i++) {
            statistics[i] = new Statistics();
            locks[i] = new ReentrantLock();
            seconds[i] = Long.MIN_VALUE;
        }
    }

    /**
     * Process transaction, and update real-time transaction statistics.
     *
     * @param transaction Transaction.
     * @return false, if transaction is too old (in such case the statistics are not affected).
     */
    public boolean insertTransaction(Transaction transaction) {
        long currentSeconds = TimeUnit.MILLISECONDS.toSeconds(clock.currentTimeMillis());
        long transactionSeconds = TimeUnit.MILLISECONDS.toSeconds(transaction.getTimestamp());
        if (currentSeconds - transactionSeconds >= durationForStats) {
            return false;
        }

        int statsIndex = (int) (transactionSeconds % durationForStats);
        Lock lock = locks[statsIndex];
        try {
            lock.lock();
            updateTrxList(statsIndex, transactionSeconds, transaction.getAmount());
            return true;
        } finally {
            lock.unlock();
        }
    }

    private void updateTrxList(int statsIndex, long transactionSeconds, BigDecimal bigDecimal) {
        Statistics stats = statistics[statsIndex];
        long statsSeconds = seconds[statsIndex];

        
        
        if (statsSeconds != transactionSeconds) {
            seconds[statsIndex] = transactionSeconds;
            statistics[statsIndex] = Statistics.forValue(bigDecimal);
        } else {
            statistics[statsIndex] = stats.merge(bigDecimal);
        }
    }

    /**
     * Get transaction statistics.
     *
     * @return Transaction statistics.
     */
    public Statistics getStatistics() {
        Statistics aggregated = new Statistics();
        long smallestValidSecond = TimeUnit.MILLISECONDS.toSeconds(clock.currentTimeMillis()) - durationForStats;
        
        for (int i = 0; i < statistics.length; i++) {
            Lock lock = locks[i];
            try {
                lock.lock();
                if (seconds[i] > smallestValidSecond) {
                    aggregated = aggregated.calculate(statistics[i]);
                }
            } finally {
                lock.unlock();
            }
        }
        return aggregated;
    }
    
 


}
