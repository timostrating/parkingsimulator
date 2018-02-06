package com.parkingtycoon.helpers;

import com.parkingtycoon.helpers.interfaces.Named;

import java.util.ArrayList;

/**
 * This Class is responsible for providing a simple way of managing Named items
 */
public class ArrayNamed<T extends Named> extends ArrayList<T> {

    /**
     * Get a Named item by name
     *
     * @param name The name of the item you want to find
     * @return     The item with that name
     */
    public T get(String name) {
        for (T obj : this) if (obj.name().equals(name)) return obj;
        return null;
    }

}
