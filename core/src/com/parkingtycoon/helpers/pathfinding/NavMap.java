package com.parkingtycoon.helpers.pathfinding;

/**
 * Navigation Maps are used by the PathFinder to determine where to go.
 *
 * @author Hilko Janssen
 */
public abstract class NavMap {

    /**
     * Whether or not diagonal paths are allowed on this map
     */
    public boolean allowDiagonalPaths = false;

    /**
     * This method should say whether the PathFinder is allowed to go here
     *
     * @param x         The x-coordinate
     * @param y         The y-coordinate
     * @param firstNode Whether this is the first node of the path
     * @param lastNode  Whether this is te last node of the path
     * @return          Is the PathFinder allowed to go here?
     */
    public abstract boolean open(int x, int y, boolean firstNode, boolean lastNode);

    /**
     * This method should return a score higher than 0 if it wants the PathFinder to avoid this place
     *
     * @param x X-coordinate
     * @param y Y-coordinate
     * @return  The avoid score
     */
    public abstract int avoidScore(int x, int y);

}
