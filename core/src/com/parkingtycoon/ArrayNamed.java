package com.parkingtycoon;

import com.parkingtycoon.interfaces.Named;

import java.util.ArrayList;

/**
 * Created by hilkojj.
 */
public class ArrayNamed<T extends Named> extends ArrayList<T> {

    public T get(String name) {
        for (T obj : this) if (obj.name().equals(name)) return obj;
        return null;
    }

}
