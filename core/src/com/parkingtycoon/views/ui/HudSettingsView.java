package com.parkingtycoon.views.ui;

import com.badlogic.gdx.scenes.scene2d.Stage;
import com.kotcrab.vis.ui.util.TableUtils;
import com.kotcrab.vis.ui.widget.VisTable;
import com.kotcrab.vis.ui.widget.VisWindow;
import com.kotcrab.vis.ui.widget.tabbedpane.Tab;
import com.kotcrab.vis.ui.widget.tabbedpane.TabbedPane;
import com.parkingtycoon.helpers.interfaces.Showable;
import com.parkingtycoon.models.BaseModel;
import com.parkingtycoon.views.BaseView;

/**
 * This class is responsible for showing the settings window.
 */
public class HudSettingsView extends BaseView implements Showable {

    public VisTable container;
    public TabbedPane tabbedPane;

    private HudSettingsWindow window;

    /**
     * the standard constructor to create the settings window.
     *
     * @param stage the stage where the window will be added.
     * @param tabs an array of tabs that will be added to the settings menu.
     */
    public HudSettingsView(Stage stage, Tab... tabs) {
        window = new HudSettingsWindow(tabs);
        stage.addActor(window);
    }

    @Override
    public void updateView(BaseModel model) { }

    @Override
    public float renderIndex() {
        return 0;
    }

    /**
     * Show only one window
     *
     * @param stage the stage where the showed item should be added to
     */
    @Override
    public void show(Stage stage) {
        window.setVisible(true);
        window.setZIndex(999);
        stage.addActor(window);
    }

    /**
     * Hide the window
     */
    @Override
    public void hide() {
        window.setZIndex(0);
        window.setVisible(false);
    }

    /**
     * this is the class that creates the settings window.
     */
    private class HudSettingsWindow extends VisWindow {

        HudSettingsWindow(Tab... tabs) {
            super("Settings");
            TableUtils.setSpacingDefaults(this);
            columnDefaults(0).left();

            setSize(300, 300);
            addCloseButton();

            container = new VisTable();

            tabbedPane = new TabbedPane();
            for (Tab tab : tabs)
                tabbedPane.add(tab);

            container.add(tabbedPane.getTabs().get(0).getContentTable()).expand().fill();

            add(tabbedPane.getTable()).expandX().fill();
            row();
            add(container).expand().fill();
        }
    }
}
