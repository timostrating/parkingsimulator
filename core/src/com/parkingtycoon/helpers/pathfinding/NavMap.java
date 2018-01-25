package com.parkingtycoon.helpers.pathfinding;

/**
 * This Class is responsible for providing a basic blueprint for a map that can be used by the Pathfinder.
 */
public abstract class NavMap {

    public boolean allowDiagonalPaths = false;

    public abstract boolean open(int x, int y, boolean firstNode, boolean lastNode);

}
