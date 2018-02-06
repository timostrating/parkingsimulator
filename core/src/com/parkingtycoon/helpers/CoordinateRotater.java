package com.parkingtycoon.helpers;

public class CoordinateRotater {

    /**
     * This static method is used to rotate coordinates on an integer grid
     */
    public static int rotate(int i, int iLength, int j, int jLength, int angle) {
        switch (angle % 4) {
            case 0:
                return i;
            case 1:
                return jLength - j - 1;
            case 2:
                return iLength - i - 1;
            case 3:
                return j;
        }
        return 0;
    }

    private CoordinateRotater() {}

}