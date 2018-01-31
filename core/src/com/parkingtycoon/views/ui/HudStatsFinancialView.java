package com.parkingtycoon.views.ui;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.kotcrab.vis.ui.widget.VisLabel;
import com.kotcrab.vis.ui.widget.VisSlider;
import com.kotcrab.vis.ui.widget.VisTable;
import com.kotcrab.vis.ui.widget.VisWindow;
import com.kotcrab.vis.ui.widget.tabbedpane.Tab;
import com.kotcrab.vis.ui.widget.tabbedpane.TabbedPane;
import com.kotcrab.vis.ui.widget.tabbedpane.TabbedPaneAdapter;
import com.parkingtycoon.models.BaseModel;
import com.parkingtycoon.views.BaseView;
import com.parkingtycoon.views.components.HudDiagram;
import com.parkingtycoon.views.components.HudLineDiagram;

public class HudStatsFinancialView extends BaseView {

    private final HubStatsWindow window;
    private final HudDiagram diagram;
    private final int width = 500;
    private final int height = 500;

    private int[] sliderOption = new int[] {Integer.MAX_VALUE, 1_000_000, 100_000, 10_000, 1000, 200};


    public HudStatsFinancialView(Stage stage) {
        super();
        show();

        diagram = new HudLineDiagram(width, height);
        window = new HubStatsWindow(diagram.generateDiagramTexture());
        stage.addActor(window);
    }

    private int flip = 0;

    @Override
    public void preRender() {
        if (flip % 1 == 0) {
            if (diagram instanceof HudLineDiagram)
                ((HudLineDiagram) diagram).setStartPoint(diagram.getDataLength() - sliderOption[window.getSliderIndex()]);

            window.setDiagram(diagram.generateDiagramTexture());
        }

        flip++;
    }

    @Override
    public float renderIndex() {
        return 0;
    }

    @Override
    public void updateView(BaseModel model) {
        diagram.update(model);
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
//                    container
                }
            });

            tabbedPane.add(new TestTab("tab1"));
            tabbedPane.add(new TestTab("tab2"));

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


        private class TestTab extends Tab {
            private String title;
            private Table content;

            public TestTab (String title) {
                super(false, false);
                this.title = title;

                content = new VisTable();
                content.add(new VisLabel(title));
            }

            @Override
            public String getTabTitle () {
                return title;
            }

            @Override
            public Table getContentTable () {
                return content;
            }
        }
    }

}