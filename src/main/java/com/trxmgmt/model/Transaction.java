package com.trxmgmt.model;

import java.math.BigDecimal;

/**
 * Transaction.
 *
 * @author Sushil Mukundappanavar
 */
public class Transaction {

    private BigDecimal amount;
    private long timestamp;

    /**
     * Get transaction amount.
     *
     * @return Transaction amount.
     */
    public BigDecimal getAmount() {
        return amount;
    }

    /**
     * Set transaction amount.
     *
     * @param amount Transaction amount.
     */
    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    /**
     * Get transaction time in epoch millis in UTC time zone.
     *
     * @return Transaction time.
     */
    public long getTimestamp() {
        return timestamp;
    }

    /**
     * Set transaction time in epoch millis in UTC time zone.
     *
     * @param timestamp Transaction time.
     */
    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

}
