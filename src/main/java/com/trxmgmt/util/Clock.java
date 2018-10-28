package com.trxmgmt.util;

/**
 * Clock related functionality.
 *
 * @author Sushil Mukundappanavar
 */
public interface Clock {

    /**
     * Get current time in milliseconds. Measured as difference in milliseconds
     * between the current time and midnight, January 1, 1970 UTC.
     *
     * @return Current time in milliseconds.
     */
    long currentTimeMillis();

}
