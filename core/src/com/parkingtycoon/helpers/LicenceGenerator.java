package com.parkingtycoon.helpers;

import java.util.Random;

/**
 * This Class provides help of getting a correct license plate number.
 */
public class LicenceGenerator {

    static String[] mLetters = {"B","C","D","F","G","H","J","K","L","M","N","P","Q","R","S","T","V","W","X","Y","Z"};

    public static String getRandomLicencePlate(){
        Random r = new Random();
        String nummerBord = "";
        nummerBord += r.nextInt(10);
        nummerBord += r.nextInt(10);
        nummerBord += "-";
        nummerBord += mLetters[r.nextInt(mLetters.length)];
        nummerBord += mLetters[r.nextInt(mLetters.length)];
        nummerBord += mLetters[r.nextInt(mLetters.length)];
        nummerBord += "-";
        nummerBord += r.nextInt(10);
        return nummerBord;
    }
}


