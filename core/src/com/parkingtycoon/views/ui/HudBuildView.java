package com.parkingtycoon.views.ui;

import com.badlogic.gdx.scenes.scene2d.Stage;
import com.kotcrab.vis.ui.layout.GridGroup;
import com.kotcrab.vis.ui.util.TableUtils;
import com.kotcrab.vis.ui.widget.VisScrollPane;
import com.kotcrab.vis.ui.widget.VisTable;
import com.kotcrab.vis.ui.widget.VisTextButton;
import com.kotcrab.vis.ui.widget.VisWindow;
import com.parkingtycoon.models.BaseModel;
import com.parkingtycoon.views.BaseView;

public class HudBuildView extends BaseView {


    private final HudBuildWindow window;

    public HudBuildView(Stage stage) {
        super();
        show();

        window = new HudBuildWindow();
        stage.addActor(window);
    }

    @Override
    public void updateView(BaseModel model) {

    }

    @Override
    public float renderIndex() {
        return 0;
    }


    class HudBuildWindow extends VisWindow {

        HudBuildWindow() {
            super("Build");

            TableUtils.setSpacingDefaults(this);
            columnDefaults(0).left();

            setResizable(true);
            addCloseButton();

            VisTable table = new VisTable();
            table.setWidth(200);

            GridGroup group = new GridGroup(100, 4); //item size 64px, spacing 4px
            for (int i=0; i<10; i++) {
                group.addActor(new VisTextButton("IMG + title"));
            }

            VisScrollPane scrollPane = new VisScrollPane(group);
            scrollPane.setFadeScrollBars(false);
            scrollPane.setFlickScroll(false);
            scrollPane.setOverscroll(false, false);
            scrollPane.setScrollingDisabled(true, false); //disable X scrolling


            table.add(scrollPane).top().growX();
            table.row();

            VisTable footerTable = new VisTable();
            footerTable.addSeparator();
            footerTable.add("Description: ...").left();
            footerTable.add("Price: ...").right().padRight(20);
            table.add(footerTable).bottom().growX();

            add(table).growX();

//            table.debugAll();
//            debugAll();

            setSize(200, 200);
            centerWindow();
        }
    }

}
