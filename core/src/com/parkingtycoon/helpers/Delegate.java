package com.parkingtycoon.helpers;

import java.util.ArrayList;


/**
 * Tread safe implementation of C# delegates.
 * 
 * This class is a Helper to the Observer pattern - The old scenario would contain a ArrayList per Observable + the required functions
 *
 * @author GGG
 */
public class Delegate<T> {

    protected ArrayList<T> list = new ArrayList<>();
    private ArrayList<T> toBeAdded, toBeRemoved;
    private boolean multiThreaded;

    /**
     * This is the standard constructor for creating a new Delegate.
     *
     * @param multiThreaded should we account for the fact that multiple thread may try to reach the data?
     *                      If true than we will use separate queues for editing the list.
     */
    public Delegate(boolean multiThreaded) {
        this.multiThreaded = multiThreaded;
        if (multiThreaded) {
            toBeAdded = new ArrayList<>();
            toBeRemoved = new ArrayList<>();
        }
    }

    /**
     * We use this FunctionalInterface to make it possible to replace code with lambda's and method references.
     * @param <T> the generic object
     */
    @FunctionalInterface
    public interface Notifier<T> {
        void notify(T object);
    }

    /**
     * We use this FunctionalInterface to make it possible to replace code with lambda's and method references.
     * @param <T> the generic object
     */
    @FunctionalInterface
    public interface Starter<T> {
        void start(T object);
    }

    /**
     * We use this FunctionalInterface to make it possible to replace code with lambda's and method references.
     * @param <T> the generic object
     */
    @FunctionalInterface
    public interface Sorter<T> {
        float getIndex(T object);
    }

    /**
     * Register a Item to the List or waiting queues depending on the fact that you enabled multithreading support or not.
     *
     * @param object the object that you would like to add.
     * @return if it was successful
     */
    public boolean register(T object) {
        return multiThreaded ? toBeAdded.add(object) : list.add(object);
    }

    /**
     * UnRegister a Item to the List or waiting queues depending on the fact that you enabled multithreading support or not.
     *
     * @param object the object that you would like to remove.
     * @return if it was successful
     */
    public boolean unregister(T object) {
        return multiThreaded ? toBeRemoved.add(object) : list.remove(object);
    }

    /**
     * If you want multithreading support than you are required to call process to process the data between the queues
     * so that there can not accrue a multithreading problem.
     *
     * @param starter the Lambda or method reference that will point to the function that we will call only once on the
     *                first time that a item moves between the waiting queues and the list.
     */
    public void process(Starter<T> starter) {
        if (!multiThreaded)
            return;

        if (toBeAdded.size() > 0) {
            list.addAll(toBeAdded);
            for (T object : list)   // do not loop toBeAdded because toBeAdded might be modified at the same time in another thread.
                if (toBeAdded.contains(object))
                    starter.start(object); // todo nullpointer exception? renderController.preRender -> delegate.process():57. first check if null
            toBeAdded.clear();
        }

        if (toBeRemoved.size() > 0) {
            list.removeAll(toBeRemoved);
            toBeRemoved.clear();
        }

    }

    /**
     * Foreach registered item call the Notifier
     *
     * @param notifier the Lambda or method reference that will point to the function that we will call
     */
    public void notifyObjects(Notifier<T> notifier) {
        for (T object : list)
            notifier.notify(object);
    }

    /**
     * Sort the views bases on every render index of every view
     *
     * This is a Optimized Sorter that is optimised for processing non changing lists.
     *
     * @param sorter give us the generic item where we will sort on.
     */
    public void sort(Sorter<T> sorter) {
        for (int i = 1; i < list.size(); i++) {

            T temp = list.get(i);
            float index = sorter.getIndex(temp); // todo nullpointer exception?

            int j = i - 1;

            while (j >= 0 && sorter.getIndex(list.get(j)) < index) {
                list.set(j + 1, list.get(j));
                j--;
            }
            list.set(j + 1, temp);

        }
    }

}
