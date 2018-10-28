package com.trxmgmt.service;

import com.trxmgmt.model.Statistics;
import com.trxmgmt.model.Transaction;
import com.trxmgmt.util.FixedClock;
import org.assertj.core.api.Assertions;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.concurrent.TimeUnit;

/**
 * Test cases for {@link TrxStatService}.
 *
 * @author Sushil Mukundappanavar
 */
public class TrxStatServiceTest {

    private final FixedClock testClock = new FixedClock();
    private final TrxStatService service = new TrxStatService(testClock);

    @Test
    public void shouldComputeStatistics() {
        double amount = 10;
        double expectedSum = 0;
        int expectedCount = 0;

        testClock.setMillis(System.currentTimeMillis());
        long to = testClock.currentTimeMillis();
        long from = to - TimeUnit.SECONDS.toMillis(TrxStatService.durationForStats - 1);

        for (long timestamp = from; timestamp <= to; timestamp = timestamp + 1000) {
            service.insertTransaction(newTransaction(amount, timestamp));
            expectedSum += amount++;
            expectedCount++;
        }

        Statistics statistics = service.getStatistics();
        Assertions.assertThat(statistics.getCount()).isEqualTo(expectedCount).isEqualTo(TrxStatService.durationForStats);
        Assertions.assertThat(statistics.getSum()).isEqualTo(expectedSum);
    }

    @Test
    public void shouldIgnoreOldStatistics() {
        testClock.setMillis(System.currentTimeMillis());
        long to = testClock.currentTimeMillis();
        long from = to - TimeUnit.SECONDS.toMillis(TrxStatService.durationForStats - 1);

        for (long timestamp = from; timestamp <= to; timestamp = timestamp + 1000) {
            service.insertTransaction(newTransaction(10, timestamp));
        }

        // Forward clock by 5 seconds
        testClock.setMillis(testClock.currentTimeMillis() + 5000);
        Statistics statistics = service.getStatistics();
        Assertions.assertThat(statistics.getCount()).isEqualTo(TrxStatService.durationForStats - 5);
        Assertions.assertThat(statistics.getSum()).isEqualTo((TrxStatService.durationForStats - 5) * 10);

        // Forward clock far to the future
        testClock.setMillis(testClock.currentTimeMillis() + TrxStatService.durationForStats * 2000);
        statistics = service.getStatistics();
        Assertions.assertThat(statistics.getCount()).isZero();
        Assertions.assertThat(statistics.getSum()).isZero();
    }

    @Test
    public void shouldAcceptTransactions() {
        testClock.setMillis(System.currentTimeMillis());
        long validTime = testClock.currentTimeMillis();
        long invalidTime = validTime - TrxStatService.durationForStats * 1000;
        Assertions.assertThat(service.insertTransaction(newTransaction(10, validTime))).isTrue();
        Assertions.assertThat(service.insertTransaction(newTransaction(10, invalidTime))).isFalse();
    }

    private Transaction newTransaction(double amount, long timestamp) {
        Transaction transaction = new Transaction();
        transaction.setAmount(BigDecimal.valueOf(amount));
        transaction.setTimestamp(timestamp);
        return transaction;
    }

}
