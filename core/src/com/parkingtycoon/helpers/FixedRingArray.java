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

    public boolean debug = false;

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
                head = (head + 1) % elements.length; }}
        else if ((tail+1) % (elements.length) == 0) {
            hasBeanRound = true; }

        elements[(tail++) % (elements.length)] = value;
        tail %= elements.length;


        if (debug) test(value);
    }

    public float get(int index) {
        return elements[(head + index) % elements.length];
    }

    @Override
    public String toString() {
        return Arrays.toString(elements);
    }


    /**
     * This is a test to visually show how the FixedRingArray works
     *
     * @param args
     */
    public static void main(String[] args) {
        FixedRingArray queue = new FixedRingArray(5);
        queue.debug = true;

        queue.put(10f);
        queue.put(11f);
        queue.put(12f);
        queue.put(13f); //
        queue.put(14f);
        queue.put(15f);
        queue.put(16f);
        queue.put(17f);
        queue.put(18f);
        queue.put(19f);
        queue.put(20f);
        queue.put(21f);
        queue.put(22f);
        queue.put(23f);
        queue.put(24f);
        queue.put(25f);

        System.out.println("");

        for (int i=0; i<queue.size(); i++) {
            System.out.println(queue.get(i));
        }
    }

    public void test(float value) {
        System.out.println("hasBeanRound: "+hasBeanRound+"\t\t value: "+value+"\t\t tail: "+tail + "\t\t head: "+head +"\t"+toString());
    }

}
