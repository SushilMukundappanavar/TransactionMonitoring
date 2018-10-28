package com.trxmgmt.util;

/**
 * Fixed clock. Always returns the explicitly specified time.
 *
 * @author Sushil Mukundappanavar
 */
public class FixedClock implements Clock {

    private long millis;

    public void setMillis(long millis) {
        this.millis = millis;
    }

    @Override
    public long currentTimeMillis() {
        return millis;
    }

}

