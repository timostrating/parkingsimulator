package com.parkingtycoon.helpers;

import com.badlogic.gdx.math.Vector2;

/**
 *
 * Axis Aligned Bounding Box
 *
 * With this class you can check if 2 boxes overlap
 * Example:
 * This is used to prevent cars from driving through each other.
 *
 */
public class AABB {

    public Vector2 center;
    public Vector2 halfSize;

    public AABB(Vector2 center, Vector2 halfSize) {
        this.center = center;
        this.halfSize = halfSize;
    }

    public boolean overlaps(AABB other) {
        if (Math.abs(center.x - other.center.x) > halfSize.x + other.halfSize.x)
            return false;
        if (Math.abs(center.y - other.center.y) > halfSize.y + other.halfSize.y)
            return false;
        return true;
    }

}
