package com.parkingtycoon.controllers;

import com.parkingtycoon.helpers.Delegate;

public class TimeController extends UpdateableController {

    private Delegate<UpdateableController> hourlyUpdates = new Delegate<>(false);
    private Delegate<UpdateableController> dailyUpdates = new Delegate<>(false);
    private Delegate<UpdateableController> weeklyUpdates = new Delegate<>(false);
    private Delegate<UpdateableController> monthlyUpdates = new Delegate<>(false);
    private Delegate<UpdateableController> yearlyUpdates = new Delegate<>(false);

    private Delegate.Notifier<UpdateableController> notifier = UpdateableController::update;

    private int minutes = 1;    // We keep track of the data for the end game results
    private int hours = 0;
    private int days = 0;
    private int weeks = 0;
    private int months = 0;
    private int years = 0;


    @Override
    public void update() {
        advanceTime();
    }

    private void advanceTime(){
        minutes++;

        if (minutes % 60 == 0) {
            hourlyUpdates.notifyObjects(notifier);
            hours++;
        }
        if (hours % 24 == 0) {
            dailyUpdates.notifyObjects(notifier);
            days++;
        }
        if (days % 7 == 0) {
            weeklyUpdates.notifyObjects(notifier);
            weeks ++;
        }
        if (days % 30 == 0) {    // TODO: We oversimplify this
            monthlyUpdates.notifyObjects(notifier);
            months++;
        }
        if (weeks % 52 == 0) {   // TODO: We simplify this a little bit
            yearlyUpdates.notifyObjects(notifier);
            years ++;
        }
    }

    public boolean registerUpdatable(UpdateableController updatable, TimeUpdates atTime) {
        return getDelegate(atTime).register(updatable);
    }

    public boolean unregisterUpdatable(UpdateableController updatable, TimeUpdates atTime) {
        return getDelegate(atTime).unregister(updatable);
    }

    private Delegate<UpdateableController> getDelegate(TimeUpdates atTime) {
        switch (atTime) {
            case HOURLY: return hourlyUpdates;
            case DAILY: return dailyUpdates;
            case WEEKLY: return weeklyUpdates;
            case MONTHLY: return monthlyUpdates;
            case YEARLY: return yearlyUpdates;
        }

        System.err.println("You did not enter a valid value from the TimeUpdates enum to the function determineWhich()");
        return null;
    }


    public enum TimeUpdates {
        HOURLY,
        DAILY,
        WEEKLY,
        MONTHLY,
        YEARLY
    }

}
