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
import com.kotcrab.vis.ui.widget.VisTextButton;
import com.kotcrab.vis.ui.widget.VisWindow;
import com.kotcrab.vis.ui.widget.tabbedpane.Tab;
import com.kotcrab.vis.ui.widget.tabbedpane.TabbedPane;
import com.kotcrab.vis.ui.widget.tabbedpane.TabbedPaneAdapter;
import com.parkingtycoon.helpers.interfaces.ClickListener;
import com.parkingtycoon.models.BaseModel;
import com.parkingtycoon.models.ui.DiagramModel;
import com.parkingtycoon.views.BaseView;
import com.parkingtycoon.views.components.HudBarDiagram;
import com.parkingtycoon.views.components.HudDiagram;
import com.parkingtycoon.views.components.HudLineDiagram;
import com.parkingtycoon.views.components.HudPieDiagram;

import java.util.ArrayList;
import java.util.Collections;

import static com.parkingtycoon.models.ui.DiagramModel.DiagramModelType;

/**
 * This Class is responsible for showing the Diagrams.
 */
public class HudDiagramsView extends BaseView {

    private HubDiagramsWindow window;
    private HudDiagram curDiagram;
    private HudLineDiagram lineDiagram;
    private HudPieDiagram pieDiagram;
    private HudBarDiagram barDiagram;

    private int width = 1000;
    private int height = 1000;

    private ArrayList<DiagramModelType> selectedDiagramsModels = new ArrayList<>();

    /**
     * Standard Constructor
     * @param stage the stage where the window will be added.
     * @param diagramModels all the diagram models that will be shown.
     */
    public HudDiagramsView(Stage stage, DiagramModel... diagramModels) {
        super();
        show();

        lineDiagram = new HudLineDiagram(width, height, diagramModels);
        pieDiagram = new HudPieDiagram(width, height, diagramModels);
        barDiagram = new HudBarDiagram(width, height, diagramModels);
        curDiagram = lineDiagram;

        window = new HubDiagramsWindow(curDiagram.generateDiagramTexture(selectedDiagramsModels));
        stage.addActor(window);
    }

    @Override
    public void updateView(BaseModel model) { }

    @Override
    public void preRender() {
        curDiagram.setStartPercentage(window.getDiagramZoom());
        window.setDiagram(curDiagram.generateDiagramTexture(selectedDiagramsModels));
    }

    @Override
    public float renderIndex() {
        return 0;
    }

    /**
     * this class is responsible for showing the diagrams window.
     */
    private class HubDiagramsWindow extends VisWindow {
        private final VisSlider slider;
        Image image;

        HubDiagramsWindow(Texture texture) {
            super("Stats");
            setSize(width/2, height/2);
            setResizable(true);
            addCloseButton();

            TabbedPane tabbedPane = new TabbedPane();
            tabbedPane.addListener(new TabbedPaneAdapter() {
                @Override
                public void switchedTab (Tab tab) {
                    selectedDiagramsModels.clear();
                    Collections.addAll(selectedDiagramsModels, ((DiagramTab)tab).getDiagramModelTypes());
                }
            });

            tabbedPane.add(new DiagramTab("Financial", DiagramModelType.MONEY));
            tabbedPane.add(new DiagramTab("Total Cars", DiagramModelType.TOTAL_CARS));
            tabbedPane.add(new DiagramTab("Cars", DiagramModelType.ADHOC_CARS, DiagramModelType.RESERVED_CARS, DiagramModelType.VIP_CARS ));
            tabbedPane.add(new DiagramTab("Adhoc Cars", DiagramModelType.ADHOC_CARS));
            tabbedPane.add(new DiagramTab("Reserved Cars", DiagramModelType.RESERVED_CARS));
            tabbedPane.add(new DiagramTab("Vip Cars", DiagramModelType.VIP_CARS));

            image = new Image();
            setDiagram(texture);

            slider = new VisSlider(0, 0.95f, 0.01f, false);

            VisTextButton pieChartButton = new VisTextButton("Pie Chart");
            pieChartButton.addListener((ClickListener) (event, actor) -> curDiagram = pieDiagram);

            VisTextButton barDiagramButton = new VisTextButton("Bar Chart");
            barDiagramButton.addListener((ClickListener) (event, actor) -> curDiagram = barDiagram);

            VisTextButton lineDiagramButton = new VisTextButton("Line Diagram");
            lineDiagramButton.addListener((ClickListener) (event, actor) -> curDiagram = lineDiagram);

            VisTable footerTable = new VisTable(true);
            footerTable.add(lineDiagramButton).padRight(10);
            footerTable.add(barDiagramButton).padRight(10);
            footerTable.add(pieChartButton).padRight(50);
            footerTable.add(slider);

            final VisTable container = new VisTable(true);
            container.row();
            container.add(image).expand();
            container.row();
            container.add(footerTable);

            add(tabbedPane.getTable()).expandX().fillX();
            row();
            add(container).expand().fill();
        }

        public void setDiagram(Texture texture) {
            TextureRegion textureRegion = new TextureRegion(texture, 0, 0, width, height);
            textureRegion.flip(false, true);
            Drawable drawable = new TextureRegionDrawable(textureRegion);
            image.setDrawable(drawable);
        }

        public float getDiagramZoom() {
            return slider.getValue();
        }

        /**
         * This class is responsible for showing a tab in the header of the diagrams window.
         */
        private class DiagramTab extends Tab {
            private String title;
            private DiagramModelType[] diagramModelTypes;

            public DiagramTab (String title, DiagramModelType... diagramModelTypes) {
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

            public DiagramModelType[] getDiagramModelTypes() {
                return diagramModelTypes;
            }
        }
    }

}