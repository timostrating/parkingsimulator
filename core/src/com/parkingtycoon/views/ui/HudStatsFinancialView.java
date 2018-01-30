package com.parkingtycoon.views.ui;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.kotcrab.vis.ui.widget.VisSlider;
import com.kotcrab.vis.ui.widget.VisWindow;
import com.parkingtycoon.models.BaseModel;
import com.parkingtycoon.views.BaseView;
import com.parkingtycoon.views.ui.diagrams.HudDiagram;
import com.parkingtycoon.views.ui.diagrams.HudLineDiagram;

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
        if (flip % 30 == 0)
            window.setDiagram(diagram.generateDiagramTexture());

        flip++;
    }

    @Override
    public float renderIndex() {
        return 0;
    }

    @Override
    public void updateView(BaseModel model) {
        if (diagram instanceof HudLineDiagram)
            ((HudLineDiagram)diagram).setStartPoint(diagram.getDataLength() - sliderOption[window.getSliderIndex()]);

        diagram.update(model);
    }


    class HubStatsWindow extends VisWindow {
        private final VisSlider slider;
        Image image;

        HubStatsWindow(Texture texture) {
            super("Stats");
            setSize(width+10, height+10);
            setResizable(true);
            addCloseButton();

            image = new Image();
            setDiagram(texture);

            slider = new VisSlider(0, 5, 1, false);
            add(image).expand();
            row();
            add(slider);
        }

        public void setDiagram(Texture texture) {
            TextureRegion textureRegion = new TextureRegion(texture, 0, 0, width, height);
            Drawable drawable = new TextureRegionDrawable(textureRegion);
            image.setDrawable(drawable);
        }

        public int getSliderIndex() {
            return (int)slider.getValue();
        }
    }

}