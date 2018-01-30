package com.parkingtycoon.controllers;

import com.parkingtycoon.helpers.Delegate;
import com.parkingtycoon.helpers.interfaces.Updatable;

/**
 * This Class is responsible for enabling Controllers to be called every time when a amount of in game time has passes.
 */
public class TimeController extends UpdateableController {

    private Delegate<Updatable> minutelyUpdates = new Delegate<>(false);
    private Delegate<Updatable> hourlyUpdates = new Delegate<>(false);
    private Delegate<Updatable> dailyUpdates = new Delegate<>(false);
    private Delegate<Updatable> weeklyUpdates = new Delegate<>(false);
    private Delegate<Updatable> monthlyUpdates = new Delegate<>(false);
    private Delegate<Updatable> yearlyUpdates = new Delegate<>(false);

    private Delegate.Notifier<Updatable> notifier = Updatable::update;

    private long seconds = 0;    // We keep track of the data for easier calculations
    private long minutes = 0;
    private long hours = 0;
    private long days = 0;
    private long weeks = 0;
    private long months = 0;


    @Override
    public void update() {
        advanceTime();
    }

    private void advanceTime(){
        seconds ++;

        if (seconds >= 60) {
            seconds -= 60;
            minutelyUpdates.notifyObjects(notifier);
            minutes++;
        }
        if (minutes >= 60) {
            minutes -= 60;
            hourlyUpdates.notifyObjects(notifier);
            hours++;
        }
        if (hours >= 24) {
            hours -= 24;
            dailyUpdates.notifyObjects(notifier);
            days++;
        }
        if (days >= 7) {
            days -= 7;
            weeklyUpdates.notifyObjects(notifier);
            weeks++;
        }
        if (weeks >= 4) {     // TODO: We oversimplify this
            weeks -= 4;
            monthlyUpdates.notifyObjects(notifier);
            months++;
        }
        if (months >= 12) {   // TODO: We oversimplify this
            months -= 12;
            yearlyUpdates.notifyObjects(notifier);
        }
    }

    public boolean registerUpdatable(Updatable updatable, TimeUpdate atTime) {
        return getDelegate(atTime).register(updatable);
    }

    public boolean unregisterUpdatable(Updatable updatable, TimeUpdate atTime) {
        return getDelegate(atTime).unregister(updatable);
    }

    private Delegate<Updatable> getDelegate(TimeUpdate atTime) {
        switch (atTime) {
            case MINUTELY: return minutelyUpdates;
            case HOURLY: return hourlyUpdates;
            case DAILY: return dailyUpdates;
            case WEEKLY: return weeklyUpdates;
            case MONTHLY: return monthlyUpdates;
            case YEARLY: return yearlyUpdates;
        }

        System.err.println("You did not enter a valid value from the TimeUpdate enum to the function determineWhich()");
        return null;
    }


    public enum TimeUpdate {
        MINUTELY,
        HOURLY,
        DAILY,
        WEEKLY,
        MONTHLY,
        YEARLY
    }

}
