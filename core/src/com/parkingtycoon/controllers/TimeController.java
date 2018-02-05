package com.parkingtycoon.controllers;

import com.parkingtycoon.helpers.Delegate;
import com.parkingtycoon.helpers.interfaces.TimedUpdatable;

/**
 * This Class is responsible for enabling Controllers to be called every time when a amount of in game time has passes.
 *
 * @author Timo Strating
 */
public class TimeController extends UpdatableController {

    private final static int SECONDS_PER_UPDATE = 20;

    private Delegate<TimedUpdatable> minutelyUpdates = new Delegate<>(false);
    private Delegate<TimedUpdatable> hourlyUpdates = new Delegate<>(false);
    private Delegate<TimedUpdatable> dailyUpdates = new Delegate<>(false);
    private Delegate<TimedUpdatable> weeklyUpdates = new Delegate<>(false);
    private Delegate<TimedUpdatable> monthlyUpdates = new Delegate<>(false);
    private Delegate<TimedUpdatable> yearlyUpdates = new Delegate<>(false);

    private Delegate.Notifier<TimedUpdatable> notifier = TimedUpdatable::timedUpdate;

    private long seconds = 0;    // We keep track of the data for easier calculations
    private long minutes = 0;
    private long hours = 0;
    private long days = 0;
    private long weeks = 0;
    private long months = 0;


    /**
     * Advance the time every update
     */
    @Override
    public void update() {
        advanceTime();
    }

    /**
     * update all time counters and notify registers if a specific amount of time has passed.
     */
    private void advanceTime(){
        seconds += SECONDS_PER_UPDATE;

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

    /**
     * Register a TimeUpdatable as a item that would like te be updated.
     *
     * @param updatable The class that would like to receive the update at a specific in game time.
     * @param atTime the in game time frames between the updates.
     */
    public void registerTimedUpdatable(TimedUpdatable updatable, TimeUpdate atTime) {
        getDelegate(atTime).register(updatable);
    }

    /**
     * UnRegister a TimeUpdatable as a item that would like te be updated.
     *
     * @param updatable The class that would like to stop receiving the update at a specific in game time.
     * @param atTime the in game time frames between the updates.
     */
    public boolean unregisterTimedUpdatable(TimedUpdatable updatable, TimeUpdate atTime) {
        return getDelegate(atTime).unregister(updatable);
    }

    /**
     * This is a helper methode that selects the Delegate that is used for a specific time frame.
     *
     * @param atTime the TimeFrame for witch you search a delegate.
     * @return Te found delegate.
     */
    private Delegate<TimedUpdatable> getDelegate(TimeUpdate atTime) {
        switch (atTime) {
            case MINUTELY: return minutelyUpdates;
            case HOURLY: return hourlyUpdates;
            case DAILY: return dailyUpdates;
            case WEEKLY: return weeklyUpdates;
            case MONTHLY: return monthlyUpdates;
            case YEARLY: return yearlyUpdates;
        }

        System.err.println("You did not enter a valid value from the TimeUpdate enum");
        return null;
    }

    /**
     * All the TimeFrame Options
     */
    public enum TimeUpdate {
        MINUTELY,
        HOURLY,
        DAILY,
        WEEKLY,
        MONTHLY,
        YEARLY
    }

}
