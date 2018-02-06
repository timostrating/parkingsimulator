package com.parkingtycoon.helpers.interfaces;

/**
 * This interface is used by objects that depend on the floor they're on.
 */
public interface FloorDependable {

    /**
     * This method is called to tell the object if it's on the currently active floor
     * @param isOnActiveFloor Is the object on the currently active floor?
     */
    void setOnActiveFloor(boolean isOnActiveFloor);

    /**
     * Returns whether or not this object is on the currently active floor.
     * @return Whether or not this object is on the currently active floor.
     */
    boolean isOnActiveFloor();

}
