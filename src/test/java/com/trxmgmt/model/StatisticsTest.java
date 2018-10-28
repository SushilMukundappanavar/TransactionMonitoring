package com.trxmgmt.model;

import java.math.BigDecimal;

import org.assertj.core.api.Assertions;
import org.junit.Test;

/**
 * Test cases for {@link Statistics}.
 *
 * @author Sushil Mukundappanavar
 */
public class StatisticsTest {

    @Test
    public void shouldBeInitializedWithCorrectValues() {
        Statistics stats = new Statistics();
        Assertions.assertThat(stats.getAvg()).isZero();
        Assertions.assertThat(stats.getSum()).isZero();
        Assertions.assertThat(stats.getCount()).isZero();
        Assertions.assertThat(stats.getMax()).isEqualTo(Double.MIN_VALUE);
        Assertions.assertThat(stats.getMin()).isEqualTo(Double.MAX_VALUE);
    }

    @Test
    public void shouldMergeStatistics() {
        Statistics statsA = new Statistics();
        Statistics statsB = new Statistics();

        statsA = statsA.merge(BigDecimal.valueOf(20));
        statsB = statsB.merge(BigDecimal.valueOf(30));
        Statistics merged = statsA.calculate(statsB);

        Assertions.assertThat(merged.getCount()).isEqualTo(BigDecimal.valueOf(2));
        Assertions.assertThat(merged.getAvg()).isEqualTo(BigDecimal.valueOf(25));
        Assertions.assertThat(merged.getMax()).isEqualTo(BigDecimal.valueOf(30));
        Assertions.assertThat(merged.getMin()).isEqualTo(BigDecimal.valueOf(20));
        Assertions.assertThat(merged.getSum()).isEqualTo(BigDecimal.valueOf(50));
    }

}
