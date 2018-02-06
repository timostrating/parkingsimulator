package com.parkingtycoon.models;

import com.parkingtycoon.views.BaseView;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * This class is responsible for adding the responsibilities of a Model in the MVC architecture.
 *
 * @author GGG
 */
public abstract class BaseModel {

    protected List<BaseView> views = new ArrayList<>();

    /**
     * Allow a view te register himself to this model.
     *
     * @param view the view who would like to listen to this model.
     */
    public void registerView(BaseView view) {
        views.add(view);
        view.updateView(this);  // We do this for views who want to store the reference of model. If they do they need the updateView before the first render.
    }

    /**
     * Allow a view te unregister himself to this model.
     *
     * @param view the view who would like to stop listen to this model.
     */
    public void unregisterView(BaseView view) {
        views.remove(view);
    }

    /**
     * Update all view that have registered them self's at this model
     */
    public void notifyViews() {
        Iterator<BaseView> viewIterator = views.iterator();

        while (viewIterator.hasNext()){

            BaseView v = viewIterator.next();

            if (v.ended)
                viewIterator.remove();
            else
                v.updateView(this);
        }
    }

}


