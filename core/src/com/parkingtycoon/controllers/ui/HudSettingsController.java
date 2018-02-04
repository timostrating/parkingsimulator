package com.parkingtycoon.controllers.ui;

import com.badlogic.gdx.scenes.scene2d.Stage;
import com.kotcrab.vis.ui.widget.tabbedpane.Tab;
import com.kotcrab.vis.ui.widget.tabbedpane.TabbedPaneAdapter;
import com.parkingtycoon.controllers.BaseController;
import com.parkingtycoon.helpers.interfaces.Showable;
import com.parkingtycoon.views.ui.HudSettingsView;

public class HudSettingsController extends BaseController implements Showable {

    private final HudSettingsView view;


    public HudSettingsController(Stage stage) {
        HudSettingsEmployeeController employee = new HudSettingsEmployeeController(stage);

        view = new HudSettingsView(stage, employee.view);
        view.hide();

        view.tabbedPane.addListener(new TabbedPaneAdapter() {
            @Override
            public void switchedTab (Tab tab) {
                view.container.clearChildren();
                view.container.add(tab.getContentTable()).expand().fill();
            }
        });

    }

    @Override
    public void show(Stage stage) {
        view.show(stage);
    }

}
