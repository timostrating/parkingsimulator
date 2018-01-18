package parkingsimulator.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Slider;
import javafx.scene.input.MouseEvent;
import parkingsimulator.interfaces.Updatable;
import parkingsimulator.models.ApplicationModel;
import parkingsimulator.views.ApplicationView;

/**
 * The ApplicationController is responsible for updating the simulation
 */
public class ApplicationController extends BaseController {

    @FXML
    private Slider speedSlider;
    @FXML
    private Button pauseButton;
    private ApplicationModel model;

    public ApplicationController() {

		/*
            Our application is dual-threaded.
			1 thread handles the User Interface.
			The other thread handles the simulation-updates.
		 */

        model = new ApplicationModel();
        model.registerView(new ApplicationView(model, this));

        speedSlider.setValue(model.updatesPerSecond);
        speedSlider.valueProperty().addListener(
                (observable, oldValue, newValue) -> model.updatesPerSecond = newValue.doubleValue()
        );
        pauseButton.setOnAction(event -> model.paused = !model.paused);

        new Thread(new Runnable() { // create simulation-updates thread
            @Override
            public void run() {
                ApplicationController.this.run();
            }
        }).start();

    }

    public void registerUpdatable(Updatable updatable) {
        model.updatables.add(updatable);
    }

    public void unregisterUpdatable(Updatable updatable) {
        model.updatables.remove(updatable);
    }


    /*
        The run method is called when the second Thread is created
        We make sure that update() is called guaranteed 60 times per second.

            while true {                            // infinite
                while deltaTime >= TIME_STEP {      // 60x per second
                    update();
                    deltaTime -= TIME_STEP
                }

                deltaTime += time elapsed since previous time
            }
     */
    private void run() {

        double prevTime = System.currentTimeMillis();
        double deltaTime = 0;

        while (model.isSimulatorRunning) {
            double timeStep = 1000d / model.updatesPerSecond;
            while (deltaTime >= timeStep) {

                if (!model.paused)
                    update();

                deltaTime -= timeStep;
            }

            double time = System.currentTimeMillis();
            deltaTime += Math.min(time - prevTime, 250);
            prevTime = time;
        }
    }

    private void update() {
        model.updates++;

        for (Updatable u : model.updatables)
            u.update();

        System.out.println(model.updates);
    }

    @FXML
    public void speedSliderChanged(MouseEvent mouseEvent) {

    }
}
