package com.parkingtycoon.helpers;

import java.util.Arrays;

/**
 * Tis class is responsible for providing help as a good mix between a Queue and Array.
 *
 * A C++ example: http://gameprogrammingpatterns.com/event-queue.html#what-goes-in-the-queue
 *
 * @author Timo Strating
 */
public class FixedRingArray {

    private int head = 0;
    private int tail = 0;
    private float[] elements;
    private boolean hasBeanRound = false;


    public FixedRingArray(int length) {
        elements = new float[length];
    }

    public int size() {
        if (tail > head)
            return tail - head;
        else
            return elements.length - (tail - head);
    }

    public void put(float value) {
        if (hasBeanRound) {
            if ((tail) % (elements.length) == head) {
                head++;}}
        else if ((tail+1) % (elements.length) == 0) {
            hasBeanRound = true; }

        elements[(tail++) % (elements.length)] = value;
        tail %= elements.length;


        test(value);
    }

    public float get(int index) {
        return elements[(head + index) % elements.length];
    }

    public void test(float value) {
        System.out.println("hasBeanRound: "+hasBeanRound+"\t\t value: "+value+"\t\t tail: "+tail + "\t\t head: "+head +"\t"+toString());
    }

    @Override
    public String toString() {
        return Arrays.toString(elements);
    }
}
