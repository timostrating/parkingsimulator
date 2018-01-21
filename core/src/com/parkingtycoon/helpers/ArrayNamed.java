package com.parkingtycoon.helpers;

import com.parkingtycoon.interfaces.INamed;

import java.util.ArrayList;

/**
 * Created by hilkojj.
 */
public class ArrayNamed<T extends INamed> extends ArrayList<T> {

    public T get(String name) {
        for (T obj : this) if (obj.name().equals(name)) return obj;
        return null;
    }

}
