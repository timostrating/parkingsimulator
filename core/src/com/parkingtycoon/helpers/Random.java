package com.parkingtycoon.helpers;

import com.badlogic.gdx.graphics.Color;

import java.util.List;

/**
 * This Class helps getting random data.
 */
public class Random {

    private final static java.util.Random R = new java.util.Random();

    /**
     * Get a random integer between min and max
     *
     * @param min Minimal value
     * @param max Maximal value
     * @return    A random integer between min and max
     */
    public static int randomInt(int min, int max) {
        return R.nextInt(max - min) + min;
    }

    /**
     * Get a random integer under max
     *
     * @param max Maximal value
     * @return    A random integer
     */
    public static int randomInt(int max) {
        return R.nextInt(max);
    }

    /**
     * This method creates a random color
     * @return Random color
     */
    public static Color randomColor() {
        return new Color(R.nextFloat(), R.nextFloat(), R.nextFloat(), 1);
    }

    /**
     * This method returns a random float
     * @return Random float
     */
    public static float random() {
        return R.nextFloat();
    }

    /**
     * This method will choose a random item out of a List
     * @param list List to choose from
     * @param <T>  ObjectType of the items
     * @return     A randomly chosen item
     */
    public static <T> T choice(List<T> list) {
        return list.get(R.nextInt(list.size()));
    }

    /**
     * This method will choose a random item out of an array
     * @param array Array to choose from
     * @param <T>   ObjectType of the items
     * @return      A randomly chosen item
     */
    public static <T> T choice(T[] array) {
        return array[R.nextInt(array.length)];
    }

}
