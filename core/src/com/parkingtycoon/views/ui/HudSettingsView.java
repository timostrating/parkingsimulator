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

public class HudSettingsView extends BaseView implements Showable {

    public VisTable container;
    public TabbedPane tabbedPane;

    private HudSettingsWindow window;


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

    @Override
    public void show(Stage stage) {
        window.setVisible(true);
        window.setZIndex(999);
        stage.addActor(window);
    }

    @Override
    public void hide() {
        window.setZIndex(0);
        window.setVisible(false);
    }


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
