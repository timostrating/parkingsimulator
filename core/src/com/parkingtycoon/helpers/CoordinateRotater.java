package com.parkingtycoon.helpers;

public class CoordinateRotater {

    public static int rotate(int i, int iLength, int j, int jLength, int angle) {
        switch (angle % 4) {
            case 0:
                return i;
            case 1:
                return j;
            case 2:
                return iLength - i - 1;
            case 3:
                return jLength - j - 1;
        }
        return 0;
    }

}