package com.parkingtycoon.helpers;

/**
 * This class is a Helper for all mathematical calculations in Integer space
 *
 * @author hilkojj.
 */
public class IntVector2 {
    public int x;
    public int y;

    public IntVector2() {
        x = 0;
        y = 0;
    }

    public IntVector2(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public IntVector2(IntVector2 vector) {
        this.x = vector.x;
        this.y = vector.y;
    }

    public boolean equals(Object obj) {
        if (!(obj instanceof IntVector2)) return false;
        IntVector2 vector = (IntVector2) obj;
        return vector.x == x && vector.y == y;
    }

    public String toString() {
        return "(" + x + ", " + y + ")";
    }

    public IntVector2 plus(IntVector2 v) {
        IntVector2 a = new IntVector2(this);
        a.x += v.x;
        a.y += v.y;
        return a;
    }

    public IntVector2 plus(int x, int y) {
        return new IntVector2(this.x + x, this.y + y);
    }

    public void set(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public void set(IntVector2 vec) {
        this.x = vec.x;
        this.y = vec.y;
    }

    public IntVector2 minus(IntVector2 v) {
        IntVector2 a = new IntVector2(this);
        a.x -= v.x;
        a.y -= v.y;
        return a;
    }

    public IntVector2 abs() {
        return new IntVector2(x < 0 ? x * -1 : x, y < 0 ? y * -1 : y);
    }

    public IntVector2 multiply(int multiply) {
        IntVector2 a = new IntVector2(this);
        a.x *= multiply;
        a.y *= multiply;
        return a;
    }

    public IntVector2 multiply(IntVector2 v) {
        IntVector2 a = new IntVector2(this);
        a.x *= v.x;
        a.y *= v.y;
        return a;
    }

    public IntVector2 multiply(int x, int y) {
        return new IntVector2(this.x * x, this.y * y);
    }

    public IntVector2 divide(int divide) {
        IntVector2 a = new IntVector2(this);
        a.x /= divide;
        a.y /= divide;
        return a;
    }

    public IntVector2 divide(IntVector2 v) {
        IntVector2 a = new IntVector2(this);
        a.x /= v.x;
        a.y /= v.y;
        return a;
    }

    public float length() {
        return (float) Math.sqrt(x * x + y * y);
    }

    public float lengthSquared() {
        return x * x + y * y;
    }

}