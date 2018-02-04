package com.parkingtycoon.controllers.ui;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.parkingtycoon.CompositionRoot;
import com.parkingtycoon.controllers.TimeController;
import com.parkingtycoon.helpers.Logger;
import com.parkingtycoon.helpers.interfaces.TimedUpdatable;
import com.parkingtycoon.views.ui.HudSettingsEmployeeTab;

public class HudSettingsEmployeeController implements TimedUpdatable {

    public HudSettingsEmployeeTab view;

    private final CompositionRoot root;


    public HudSettingsEmployeeController(Stage stage) {
        root = CompositionRoot.getInstance();
        root.timeController.registerTimedUpdatable(this, TimeController.TimeUpdate.MONTHLY);

        view = new HudSettingsEmployeeTab();

        view.employeesSpinner.addListener(new ChangeListener() {
            @Override
            public void changed (ChangeEvent event, Actor actor) {
                Logger.info("changed employeesSpinner to: " + view.employeesAmount.getValue());
            }
        });

        view.employeesPriceSpinner.addListener(new ChangeListener() {
            @Override
            public void changed (ChangeEvent event, Actor actor) {
                Logger.info("changed employeesPriceSpinner to: " + view.employeesPrice.getValue());
            }
        });

    }

    @Override
    public void timedUpdate() {
        Logger.info(view.employeesAmount.getValue() * view.employeesPrice.getValue());
        root.financialController.spend(view.employeesAmount.getValue() * view.employeesPrice.getValue(), true);
    }
}



