package parkingsimulator.models;

import parkingsimulator.views.BaseView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Hilko on 18-1-2018.
 */
public abstract class BaseModel {

    protected List<BaseView> views = new ArrayList<>();

    public void registerView(BaseView view) {
        views.add(view);
    }

    public void unregisterView(BaseView view) {
        views.remove(view);
    }

    protected void notifyViews() {
        for (BaseView v : views)
            v.updateView();
    }

}


