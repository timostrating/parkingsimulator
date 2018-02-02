package com.parkingtycoon.views.ui;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.kotcrab.vis.ui.widget.VisSlider;
import com.kotcrab.vis.ui.widget.VisTable;
import com.kotcrab.vis.ui.widget.VisTextButton;
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
import com.parkingtycoon.views.components.HudPieDiagram;

import java.util.ArrayList;
import java.util.Collections;

import static com.parkingtycoon.models.ui.DiagramModel.DiagramModelType;

public class HudStatsFinancialView extends BaseView {

    private HubStatsWindow window;
    private HudDiagram curDiagram;
    private HudLineDiagram lineDiagram;
    private HudPieDiagram pieDiagram;
    private HudBarDiagram barDiagram;

    private final int width = 500;
    private final int height = 500;

    private int[] sliderOption = new int[] {Integer.MAX_VALUE, 1_000_000, 100_000, 10_000, 1000, 200};

    private ArrayList<DiagramModelType> selectedDiagramsModels = new ArrayList<>();


    public HudStatsFinancialView(Stage stage, DiagramModel... diagramModels) {
        super();
        show();

        lineDiagram = new HudLineDiagram(width, height, diagramModels);
        pieDiagram = new HudPieDiagram(width, height, diagramModels);
        barDiagram = new HudBarDiagram(width, height, diagramModels);
        curDiagram = lineDiagram;

        window = new HubStatsWindow(curDiagram.generateDiagramTexture(selectedDiagramsModels));
        stage.addActor(window);
    }

    @Override
    public void updateView(BaseModel model) {
        curDiagram.update(selectedDiagramsModels);
    }

    @Override
    public void preRender() {
//            if (curDiagram instanceof HudLineDiagram) TODO
//                ((HudLineDiagram) curDiagram).setStartPoint(curDiagram.getDataLength() - sliderOption[window.getSliderIndex()]);

        window.setDiagram(curDiagram.generateDiagramTexture(selectedDiagramsModels));
    }

    @Override
    public float renderIndex() {
        return 0;
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

            tabbedPane.add(new DiagramTab("Financial", DiagramModelType.MONEY));
            tabbedPane.add(new DiagramTab("Total Cars", DiagramModelType.TOTAL_CARS));
            tabbedPane.add(new DiagramTab("Cars", DiagramModelType.ADHOC_CARS, DiagramModelType.RESERVED_CARS, DiagramModelType.VIP_CARS ));
            tabbedPane.add(new DiagramTab("Vip Cars", DiagramModelType.VIP_CARS));

            add(tabbedPane.getTable()).expandX().fillX();
            row();
            add(container).expand().fill();

            image = new Image();
            setDiagram(texture);

            slider = new VisSlider(0, 5, 1, false);

            VisTextButton pieChartButton = new VisTextButton("Pie Chart");
            pieChartButton.addListener(new ChangeListener() {
                @Override
                public void changed (ChangeEvent event, Actor actor) {
                    curDiagram = pieDiagram;
                }
            });

            VisTextButton barDiagramButton = new VisTextButton("Bar Chart");
            barDiagramButton.addListener(new ChangeListener() {
                @Override
                public void changed (ChangeEvent event, Actor actor) {
                    curDiagram = barDiagram;
                }
            });

            VisTextButton lineDiagramButton = new VisTextButton("Line Diagram");
            lineDiagramButton.addListener(new ChangeListener() {
                @Override
                public void changed (ChangeEvent event, Actor actor) {
                    curDiagram = lineDiagram;
                }
            });

            VisTable footerTable = new VisTable();
            footerTable.add(lineDiagramButton).padRight(10);
            footerTable.add(barDiagramButton).padRight(10);
            footerTable.add(pieChartButton).padRight(50);
            footerTable.add(slider);

            container.add(image).expand();
            container.row();
            container.add(footerTable);
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