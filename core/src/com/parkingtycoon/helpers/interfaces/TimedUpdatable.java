package com.parkingtycoon.helpers.interfaces;

/**
 * This Class is responsible for providing a basic interface for items who would like to be updated at a specific time frame inter fall.
 *
 * @author GGG
 */
@FunctionalInterface
public interface TimedUpdatable {

    /**
     * The method that will be called by the time loop. The time loop in en extension of the update loop.
     */
    void timedUpdate();

}
