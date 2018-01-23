package com.parkingtycoon.helpers;

import java.util.Random;
import java.util.Arrays;

public class LicenceGenerator{

    String[] mLetters = {"B","C","D","F","G","H","J","K","L","M","N","P","Q","R","S","T","V","W","X","Y","Z"};

    public int randomNumberGenerator(int max){
       Random r = new Random();
       int getal = r.nextInt(max);
       return getal;
    }
    public String getRandomLicencePlate(){
       String nummerBord;
       nummerBord = Integer.toString(randomNumberGenerator(10));
       nummerBord += Integer.toString(randomNumberGenerator(10));
       nummerBord += "-";
       nummerBord += mLetters[randomNumberGenerator(21)];
       nummerBord += mLetters[randomNumberGenerator(21)];
       nummerBord += mLetters[randomNumberGenerator(21)];
       nummerBord += "-";
       nummerBord += Integer.toString(randomNumberGenerator(10));
       return nummerBord;
    }



    public static void main(String[] args) {
        LicenceGenerator licencePlate = new LicenceGenerator();
        for (int i = 0; i < 100; i++){
            System.out.println(licencePlate.getRandomLicencePlate());
        }
    }

}


