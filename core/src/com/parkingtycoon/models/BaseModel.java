package com.parkingtycoon.models;

import com.parkingtycoon.views.BaseView;

import java.util.ArrayList;
import java.util.List;

/**
 * This class is responsible for adding the responsibilities of a Model in the MVC architecture.
 */
public abstract class BaseModel {

    protected List<BaseView> views = new ArrayList<>();

    public void registerView(BaseView view) {
        views.add(view);
        view.updateView(this);  // We do this for views who want to store the reference of model. If they do they need the updateView before the first render.
    }

    public void unregisterView(BaseView view) {
        views.remove(view);
    }

    public void notifyViews() {
        for (BaseView v : views)
            v.updateView(this);
    }

}


