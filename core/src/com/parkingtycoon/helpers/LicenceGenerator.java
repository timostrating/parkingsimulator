package com.parkingtycoon.helpers;

/**
 * This Class provides help of getting a correct license plate number.
 */
public class LicenceGenerator {

    static String[] mLetters = {"B","C","D","F","G","H","J","K","L","M","N","P","Q","R","S","T","V","W","X","Y","Z"};

    public static String getRandomLicencePlate(){
        String nummerBord = "";
        nummerBord += Random.randomInt(10);
        nummerBord += Random.randomInt(10);
        nummerBord += "-";
        nummerBord += mLetters[Random.randomInt(mLetters.length)];
        nummerBord += mLetters[Random.randomInt(mLetters.length)];
        nummerBord += mLetters[Random.randomInt(mLetters.length)];
        nummerBord += "-";
        nummerBord += Random.randomInt(10);
        return nummerBord;
    }
}