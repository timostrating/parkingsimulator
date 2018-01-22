package com.parkingtycoon.helpers;

import java.util.List;

public class Random {

    private final static java.util.Random R = new java.util.Random();

    public static int randomInt(int min, int max) {
        return R.nextInt(max - min) + min;
    }

    public static int randomInt(int max) {
        return R.nextInt(max);
    }

    public static float random() {
        return R.nextFloat();
    }

    public static <T> T choice(List<T> list) {
        return list.get(R.nextInt(list.size()));
    }

}
