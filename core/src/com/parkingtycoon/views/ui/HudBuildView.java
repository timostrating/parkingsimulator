package com.parkingtycoon.views.ui;

import com.badlogic.gdx.scenes.scene2d.Stage;
import com.kotcrab.vis.ui.layout.GridGroup;
import com.kotcrab.vis.ui.util.TableUtils;
import com.kotcrab.vis.ui.widget.VisScrollPane;
import com.kotcrab.vis.ui.widget.VisTable;
import com.kotcrab.vis.ui.widget.VisTextButton;
import com.kotcrab.vis.ui.widget.VisWindow;
import com.parkingtycoon.CompositionRoot;
import com.parkingtycoon.models.BaseModel;
import com.parkingtycoon.models.BluePrintModel;
import com.parkingtycoon.views.BaseView;

import java.util.ArrayList;

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

        private final ArrayList<BluePrintModel> bluePrints;

        HudBuildWindow() {
            super("Build");

            TableUtils.setSpacingDefaults(this);
            columnDefaults(0).left();

            setResizable(true);
            addCloseButton();
            CompositionRoot root = CompositionRoot.getInstance();
            bluePrints = root.bluePrintsController.bluePrints;

            VisTable table = new VisTable();
            table.setWidth(200);

            GridGroup group = new GridGroup(100, 4);

            for (BluePrintModel bluePrint : bluePrints) {
                VisTextButton button = new VisTextButton(bluePrint.title);
                button.addListener((com.parkingtycoon.helpers.interfaces.ClickListener) (event, actor) -> root.bluePrintsController.nextToBeBuilt = bluePrint);
                group.addActor(button);
            }

            VisScrollPane scrollPane = new VisScrollPane(group);
            scrollPane.setFadeScrollBars(false);
            scrollPane.setFlickScroll(false);
            scrollPane.setOverscroll(false, false);
            scrollPane.setScrollingDisabled(true, false); //disable X scrolling


            table.add(scrollPane).top().grow();
            table.row();

            VisTable footerTable = new VisTable();
            footerTable.addSeparator();
            footerTable.add("Description: ...").left();
            footerTable.add("Price: ...").right().padRight(20);
            table.add(footerTable).bottom().growX();

            add(table).grow();

//            table.debugAll();
//            debugAll();

            setSize(500, 500);
            centerWindow();
        }
    }

}
