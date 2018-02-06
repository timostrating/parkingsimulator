package com.parkingtycoon.controllers.ui;

import com.badlogic.gdx.scenes.scene2d.Stage;
import com.kotcrab.vis.ui.widget.tabbedpane.Tab;
import com.kotcrab.vis.ui.widget.tabbedpane.TabbedPaneAdapter;
import com.parkingtycoon.controllers.BaseController;
import com.parkingtycoon.helpers.interfaces.Showable;
import com.parkingtycoon.views.ui.HudSettingsView;

/**
 * This Class is responsible for processing the user requests in the Settings window.
 *
 * @author Timo Strating
 */
public class HudSettingsController extends BaseController implements Showable {

    private final HudSettingsView view;


    public HudSettingsController(Stage stage) {
        HudSettingsEmployeeController employee = new HudSettingsEmployeeController(stage);
        HudSettingsFinancialController financial = new HudSettingsFinancialController(stage);

        view = new HudSettingsView(stage, employee.view, financial.view);
        view.hide();

        view.tabbedPane.addListener(new TabbedPaneAdapter() {
            @Override
            public void switchedTab (Tab tab) {
                view.container.clearChildren();
                view.container.add(tab.getContentTable()).expand().fill();
            }
        });

    }

    /**
     * Give the view the responsibility of processing the Showable interface.
     *
     * @param stage the stage where the View should be added to.
     */
    @Override
    public void show(Stage stage) {
        view.show(stage);
    }

}
