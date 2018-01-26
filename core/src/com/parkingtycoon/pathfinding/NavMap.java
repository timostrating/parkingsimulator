package com.parkingtycoon.pathfinding;

public abstract class NavMap {

    public boolean allowDiagonalPaths = false;

    public abstract boolean open(int x, int y, boolean firstNode, boolean lastNode);

    public abstract int avoidScore(int x, int y);

}
