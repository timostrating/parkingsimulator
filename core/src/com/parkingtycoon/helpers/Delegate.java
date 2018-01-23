package com.parkingtycoon.helpers;

import java.util.ArrayList;

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
    public interface Processor<T> {
        void process(T object);
    }

    public boolean register(T object) {
        return multiThreaded ? toBeAdded.add(object) : list.add(object);
    }

    public boolean unregister(T object) {
        return multiThreaded ? toBeRemoved.add(object) : list.remove(object);
    }

    public void process(Processor<T> processor) {
        if (!multiThreaded)
            return;

        if (toBeAdded.size() > 0) {
            Logger.info("process");
            list.addAll(toBeAdded);
            for (T object : list)   // do not loop toBeAdded because toBeAdded might be modified at the same time in another thread.
                if (toBeAdded.contains(object))
                    processor.process(object);
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

    public void sort() {

    }

}
