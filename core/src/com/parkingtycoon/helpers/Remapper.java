package com.parkingtycoon.helpers;

/**
 * This is a helper class the helps converting data from a given Range to a other Range.
 *
 * @author Timo Strating
 */
public class Remapper {

    /**
     * Remap Value from Range A to Range B
     * @param value the value that you would like to move to a different Range.
     * @param a1 the lowest value in the first Range.
     * @param a2 the highest value in the first Range.
     * @param b1 the lowest value in the second Range.
     * @param b2 the highest value in the second Range.
     * @return
     */
    public static float map(float value, float a1, float a2, float b1, float b2) {
        return b1 + (value-a1) * (b2-b1) / (a2-a1);
    }
}
