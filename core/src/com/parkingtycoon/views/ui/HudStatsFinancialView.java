package com.parkingtycoon.views.ui;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.kotcrab.vis.ui.widget.VisSlider;
import com.kotcrab.vis.ui.widget.VisTable;
import com.kotcrab.vis.ui.widget.VisWindow;
import com.kotcrab.vis.ui.widget.tabbedpane.Tab;
import com.kotcrab.vis.ui.widget.tabbedpane.TabbedPane;
import com.kotcrab.vis.ui.widget.tabbedpane.TabbedPaneAdapter;
import com.parkingtycoon.models.BaseModel;
import com.parkingtycoon.models.ui.DiagramModel;
import com.parkingtycoon.views.BaseView;
import com.parkingtycoon.views.components.HudBarDiagram;
import com.parkingtycoon.views.components.HudDiagram;
import com.parkingtycoon.views.components.HudLineDiagram;

import java.util.ArrayList;
import java.util.Collections;

public class HudStatsFinancialView extends BaseView {

    private final HubStatsWindow window;
    private final HudDiagram curDiagram;
    private final HudLineDiagram lineDiagram;
    private final HudBarDiagram barDiagram;
    private final int width = 500;
    private final int height = 500;

    private int[] sliderOption = new int[] {Integer.MAX_VALUE, 1_000_000, 100_000, 10_000, 1000, 200};

    private ArrayList<DiagramModel.DiagramModelType> selectedDiagramsModels = new ArrayList<>();


    public HudStatsFinancialView(Stage stage) {
        super();
        show();

        lineDiagram = new HudLineDiagram(width, height);
        barDiagram = new HudBarDiagram(width, height);
        curDiagram = lineDiagram;

        window = new HubStatsWindow(curDiagram.generateDiagramTexture());
        stage.addActor(window);
    }

    private int flip = 0;

    @Override
    public void preRender() {
        if (flip % 1 == 0) {  // todo
            if (curDiagram instanceof HudLineDiagram)
                ((HudLineDiagram) curDiagram).setStartPoint(curDiagram.getDataLength() - sliderOption[window.getSliderIndex()]);

            window.setDiagram(curDiagram.generateDiagramTexture());
        }

        flip++;
    }

    @Override
    public float renderIndex() {
        return 0;
    }

    @Override
    public void updateView(BaseModel model) {
        DiagramModel diagramModel = (DiagramModel) model;
        if (selectedDiagramsModels.contains(diagramModel.getDiagramModelType())) {  // ArrayList contains() uses equals() on the backend
            curDiagram.update(diagramModel);
        }
    }


    private class HubStatsWindow extends VisWindow {
        private final VisSlider slider;
        Image image;

        HubStatsWindow(Texture texture) {
            super("Stats");
            setSize(width, height);
            setResizable(true);
            addCloseButton();

            final VisTable container = new VisTable();

            TabbedPane tabbedPane = new TabbedPane();
            tabbedPane.addListener(new TabbedPaneAdapter() {
                @Override
                public void switchedTab (Tab tab) {
                    selectedDiagramsModels.clear();
                    Collections.addAll(selectedDiagramsModels, ((DiagramTab)tab).getDiagramModelTypes());
                }
            });

            tabbedPane.add(new DiagramTab("Financial", new DiagramModel.DiagramModelType[] {DiagramModel.DiagramModelType.MONEY}));
            tabbedPane.add(new DiagramTab("Cars", new DiagramModel.DiagramModelType[] {DiagramModel.DiagramModelType.CARS}));

            add(tabbedPane.getTable()).expandX().fillX();
            row();
            add(container).expand().fill();

            image = new Image();
            setDiagram(texture);

            slider = new VisSlider(0, 5, 1, false);
            container.add(image).expand();
            container.row();
            container.add(slider);
        }

        public void setDiagram(Texture texture) {
            TextureRegion textureRegion = new TextureRegion(texture, 0, 0, width, height);
            Drawable drawable = new TextureRegionDrawable(textureRegion);
            image.setDrawable(drawable);
        }

        public int getSliderIndex() {
            return (int)slider.getValue();
        }


        private class DiagramTab extends Tab {
            private String title;
            private DiagramModel.DiagramModelType[] diagramModelTypes;

            public DiagramTab (String title, DiagramModel.DiagramModelType[] diagramModelTypes) {
                super(false, false);
                this.title = title;
                this.diagramModelTypes = diagramModelTypes;
            }

            @Override
            public String getTabTitle () {
                return title;
            }

            @Override
            public Table getContentTable() {
//                throw new NotImplementedException();
                return null;
            }

            public DiagramModel.DiagramModelType[] getDiagramModelTypes() {
                return diagramModelTypes;
            }
        }
    }

}