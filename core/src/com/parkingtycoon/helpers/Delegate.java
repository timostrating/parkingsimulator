package com.parkingtycoon.helpers;

import java.util.ArrayList;


/**
 * Tread safe implementation of C# delegates.
 * 
 * This class is a Helper to the Observer pattern - The old scenario would contain a ArrayList per Observable + the required functions
 */
public class Delegate<T> {

    protected ArrayList<T> list = new ArrayList<>();
    private ArrayList<T> toBeAdded, toBeRemoved;
    private boolean multiThreaded;


    public Delegate(boolean multiThreaded) {
        this.multiThreaded = multiThreaded;
        if (multiThreaded) {
            toBeAdded = new ArrayList<>();
            toBeRemoved = new ArrayList<>();
        }
    }

    @FunctionalInterface
    public interface Notifier<T> {
        void notify(T object);
    }

    @FunctionalInterface
    public interface Starter<T> {
        void start(T object);
    }

    @FunctionalInterface
    public interface Sorter<T> {
        float getIndex(T object);
    }

    public boolean register(T object) {
        return multiThreaded ? toBeAdded.add(object) : list.add(object);
    }

    public boolean unregister(T object) {
        return multiThreaded ? toBeRemoved.add(object) : list.remove(object);
    }

    public void process(Starter<T> starter) {
        if (!multiThreaded)
            return;

        if (toBeAdded.size() > 0) {
            list.addAll(toBeAdded);
            for (T object : list)   // do not loop toBeAdded because toBeAdded might be modified at the same time in another thread.
                if (toBeAdded.contains(object))
                    starter.start(object);
            toBeAdded.clear();
        }

        if (toBeRemoved.size() > 0) {
            list.removeAll(toBeRemoved);
            toBeRemoved.clear();
        }

    }

    public void notifyObjects(Notifier<T> notifier) {
        for (T object : list)
            notifier.notify(object);
    }

    public void sort(Sorter<T> sorter) {
        for (int i = 1; i < list.size(); i++) {

            T temp = list.get(i);
            float index = sorter.getIndex(temp);

            int j = i - 1;

            while (j >= 0 && sorter.getIndex(list.get(j)) < index) {
                list.set(j + 1, list.get(j));
                j--;
            }
            list.set(j + 1, temp);

        }
    }

}
