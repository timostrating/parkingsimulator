package com.parkingtycoon.views.ui;

import com.badlogic.gdx.scenes.scene2d.Stage;
import com.kotcrab.vis.ui.util.TableUtils;
import com.kotcrab.vis.ui.widget.VisTable;
import com.kotcrab.vis.ui.widget.VisWindow;
import com.kotcrab.vis.ui.widget.tabbedpane.Tab;
import com.kotcrab.vis.ui.widget.tabbedpane.TabbedPane;
import com.kotcrab.vis.ui.widget.tabbedpane.TabbedPaneAdapter;
import com.parkingtycoon.helpers.Logger;
import com.parkingtycoon.models.BaseModel;
import com.parkingtycoon.views.BaseView;

public class HudSettingsView extends BaseView {

    private HudSettingsWindow window;

    public HudSettingsView(Stage stage) {
        Logger.info("oke");

        window = new HudSettingsWindow();
        stage.addActor(window);
    }

    @Override
    public void updateView(BaseModel model) { }

    @Override
    public float renderIndex() {
        return 0;
    }


    private class HudSettingsWindow extends VisWindow {

        HudSettingsWindow() {
            super("Settings");
            TableUtils.setSpacingDefaults(this);
            columnDefaults(0).left();

            setSize(300, 300);
            addCloseButton();

            final VisTable container = new VisTable();

            TabbedPane tabbedPane = new TabbedPane();
            tabbedPane.addListener(new TabbedPaneAdapter() {
                @Override
                public void switchedTab (Tab tab) {
                    container.clearChildren();
                    container.add(tab.getContentTable()).expand().fill();
                }
            });

            tabbedPane.add(new HudSettingsEmployeeTab());

            add(tabbedPane.getTable()).expandX().fill();
            row();
            add(container).expand().fill();
        }
    }
}
