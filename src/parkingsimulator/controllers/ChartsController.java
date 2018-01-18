package parkingsimulator.controllers;

import parkingsimulator.interfaces.Updatable;

/**
 * Created by Hilko on 17-1-2018.
 */
public class ChartsController extends BaseController implements Updatable {

    public ChartsController() {

//        CompositionRoot.getInstance().applicationController.registerUpdatable(this);

    }

    @Override
    public void update() {
        System.out.println("hhhh");
    }
}
