package com.trxmgmt.model;

import java.math.BigDecimal;

/**
 * Immutable statistics managing count, max/min and sum over the passed values.
 *
 * @author Sushil Mukundappanavar
 */
public final class Statistics {

    private long count = 0;
    private BigDecimal max = new BigDecimal("0.0");
    private BigDecimal min = new BigDecimal("0.0");
    private BigDecimal sum = new BigDecimal("0.0");

    public long getCount() {
        return count;
    }

    public BigDecimal getMax() {
        return max;
    }

    public BigDecimal getMin() {
        return min;
    }

    public BigDecimal getSum() {
        return sum;
    }

    public BigDecimal getAvg() {
        if (count == 0) {
            return BigDecimal.valueOf(0);
        }
        return sum.divide(BigDecimal.valueOf(count));
    }

    public Statistics merge(BigDecimal value) {
        Statistics merged = new Statistics();
        merged.count = count + 1;
        merged.max = max.max(value);
        if(min.compareTo(BigDecimal.ZERO)==0) 
        	merged.min = value;
        else 
        	merged.min = min.min(value);
        
       merged.sum = sum.add(value);
        return merged;
    }

    public Statistics calculate(Statistics other) {
        Statistics merged = new Statistics();
        merged.count = count + other.count;
        merged.max = max.max(other.max);
        if(min.compareTo(BigDecimal.ZERO)==0) 
        	merged.min = other.min;
        else       
        merged.min = min.min(other.min);
        merged.sum = sum.add(other.sum);
        return merged;
    }

    public static Statistics forValue(BigDecimal bigDecimal) {
        Statistics stats = new Statistics();
        stats.count = 1;
        stats.max = bigDecimal;
        stats.min = bigDecimal;
        stats.sum = bigDecimal;
        return stats;
    }



}
