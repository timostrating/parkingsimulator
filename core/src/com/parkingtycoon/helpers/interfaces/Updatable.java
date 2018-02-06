package com.parkingtycoon.helpers.interfaces;

/**
 * This Class is responsible for providing a basic interface for items who would like to be updated.
 *
 * @author GGG
 */
public interface Updatable {

    /**
     * This is the method that will be called when the update loop calls you.
     */
    void update();

}
