package com.parkingtycoon.models;

import com.parkingtycoon.views.BaseView;

import java.util.ArrayList;
import java.util.List;

/**
 * This class is responsible for adding the responbitities of a Model in the MVC architechture.
 */
public abstract class BaseModel {

    protected List<BaseView> views = new ArrayList<>();

    public void registerView(BaseView view) {
        views.add(view);
    }

    public void unregisterView(BaseView view) {
        views.remove(view);
    }

    public void notifyViews() {
        for (BaseView v : views)
            v.updateView(this);
    }

}


