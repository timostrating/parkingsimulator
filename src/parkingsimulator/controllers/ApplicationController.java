package parkingsimulator.controllers;

import parkingsimulator.interfaces.IUpdatable;
import parkingsimulator.models.ApplicationModel;
import parkingsimulator.views.ApplicationView;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * The ApplicationController is responsible for updating the simulation
 *
 */
public class ApplicationController extends BaseController {

    public final static double TIME_STEP = 1000d / 60d;

    private boolean isSimutatorRunning = true;

    private int updates = 0;

    private List<IUpdatable> updatables = new ArrayList<>();

    private ApplicationModel model;

    public ApplicationController() {

		/*
            Our application is dual-threaded.
			1 thread handles the User Interface.
			The other thread handles the simulation-updates.
		 */

        model = new ApplicationModel();
        model.registerView(new ApplicationView(model, this));

        new Thread(new Runnable() { // create simulation-updates thread
            @Override
            public void run() {
                ApplicationController.this.run();
            }
        }).start();

    }

    public void registerUpdatable(IUpdatable updatable) {
        updatables.add(updatable);
    }

    public void unregisterUpdatable(IUpdatable updatable) {
        updatables.remove(updatable);
    }


    /*
        The run method is called when the second Thread is created
        We make sure that update() is called guaranteed 60 times per second.

            while true { 							// infinite
                while deltaTime >= TIME_STEP		// 60x per second
                    update();

                render(); 							// as many times as possible
            }
     */
    public void run() {

        double prevTime = System.currentTimeMillis();
        double deltaTime = 0;

        while (isSimutatorRunning) { // loop
            while (deltaTime >= TIME_STEP) {
                callUpdates();
                deltaTime -= TIME_STEP;
            }

            double time = System.currentTimeMillis();
            deltaTime += Math.min(time - prevTime, 250);
            prevTime = time;
        }
    }


    private void callUpdates() {
        updates++;

        for (IUpdatable u : updatables)
            u.update();

//		notifyListeners();
    }

}
