package com.parkingtycoon.controllers.ui;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.parkingtycoon.CompositionRoot;
import com.parkingtycoon.controllers.TimeController;
import com.parkingtycoon.helpers.interfaces.TimedUpdatable;
import com.parkingtycoon.views.ui.HudSettingsFinancialsTab;

/**
 * This class is responsible for processing all setting in the employee settings tab.
 *
 * @author Timo Strating.
 */
public class HudSettingsFinancialController implements TimedUpdatable {

    public HudSettingsFinancialsTab view;

    private final CompositionRoot root;

    /**
     * Setup the view
     *
     * @param stage the stage where the view should be added to
     */
    public HudSettingsFinancialController(Stage stage) {
        root = CompositionRoot.getInstance();
        root.timeController.registerTimedUpdatable(this, TimeController.TimeUpdate.MONTHLY);

        view = new HudSettingsFinancialsTab();

        view.adhockSpinner.addListener(new ChangeListener() {
            @Override
            public void changed (ChangeEvent event, Actor actor) {
                root.financialController.adhockTicketPrice = view.adhockTicketPrice.getValue();
            }
        });

        view.reservedSpinner.addListener(new ChangeListener() {
            @Override
            public void changed (ChangeEvent event, Actor actor) {
                root.financialController.reservedTicketPrice = view.reservedTicketPrice.getValue();
            }
        });

    }

    /**
     * apply the changes in the settings every set time frame.
     */
    @Override
    public void timedUpdate() {
//        Logger.info(view.employeesAmount.getValue() * view.employeesPrice.getValue());
        root.financialController.addAmount(view.vipMembersAmount.getValue() * view.vipMemberPrice.getValue());
    }

}



