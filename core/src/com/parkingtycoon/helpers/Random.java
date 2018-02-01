package com.parkingtycoon.helpers;

import com.badlogic.gdx.graphics.Color;

import java.util.List;


/**
 * This Class helps getting random data.
 */
public class Random {

    private final static java.util.Random R = new java.util.Random();

    public static int randomInt(int min, int max) {
        return R.nextInt(max - min) + min;
    }

    public static int randomInt(int max) {
        return R.nextInt(max);
    }

    public static Color randomColor() {
        return new Color(R.nextFloat(), R.nextFloat(), R.nextFloat(), 1);
    }

    public static float random() {
        return R.nextFloat();
    }

    public static <T> T choice(List<T> list) {
        return list.get(R.nextInt(list.size()));
    }

    public static <T> T choice(T[] list) {
        return list[R.nextInt(list.length)];
    }

}
