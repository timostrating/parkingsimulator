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

    /**
     * The standard constructor for creating a FixedRingArray.
     *
     * @param length the length of the FixedArray.
     */
    public FixedRingArray(int length) {
        elements = new float[length];
    }

    /**
     * Get the Length of the stored data. We use size because we need to calculate the value.
     *
     * @return the length of the stored data.
     */
    public int size() {
        if (tail > head)
            return tail - head;
        else
            return elements.length - (tail - head);
    }

    /**
     * Add a value to the array, if there is no space left on the right side of the array than the array will start to loop around on itself
     * In the case that the array has reached it maximum size than the array will override the fist value with the last.
     */
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

    /**
     * Get a value out of the float array.
     *
     * @param index the N item you want. may cause index out of range Exception, so check te size beforehand.
     * @return the value out of the float array.
     */
    public float get(int index) {
        return elements[(head + index) % elements.length];
    }

    /**
     * helper for converting the data to a string.
     *
     * @return the array as text.
     */
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

    /**
     * test methode
     * @param value the value that you are currently testing
     */
    public void test(float value) {
        System.out.println("hasBeanRound: "+hasBeanRound+"\t\t value: "+value+"\t\t tail: "+tail + "\t\t head: "+head +"\t"+toString());
    }

}
