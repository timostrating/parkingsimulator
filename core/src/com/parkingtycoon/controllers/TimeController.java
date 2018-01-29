package com.parkingtycoon.controllers;

public class TimeController extends UpdateableController {

    private int day = 0;
    private int hour = 0;
    private int minutes = 0;


    @Override
    public void update() {
        advanceTime();
    }

    private void advanceTime(){
        // Advance the time by one minutes.
        minutes++;
        while (minutes > 59) {
            minutes -= 60;
            hour++;
        }
        while (hour > 23) {
            hour -= 24;
            day++;
        }
        while (day > 6) {
            day -= 7;
        }
    }

}
