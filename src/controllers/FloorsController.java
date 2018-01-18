package controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import models.Floor;
import models.FloorType;

import java.util.Random;

/**
 * Created by Sneeuwpopsneeuw on 17-Jan-18.
 */
public class FloorsController implements Listener {

    @FXML
    private Canvas canvas;
    private GraphicsContext gc;

    private int update;

    private Floor[] floors;
    Random rand;


    public FloorsController() {
        floors = new Floor[]{ new Floor() };
    }


    @FXML
    public void initialize() {
        gc = canvas.getGraphicsContext2D();
        gc.setLineWidth(2);
        rand = new Random();
        ApplicationController.getInstance().registerListener( this );
    }


    private static final int NODE_WIDTH = 40;
    private static final int NODE_HEIGHT = 40;

    @FXML
    public void step(ActionEvent event) {
        gc.setFill(Color.GREEN);

        for(int i=0; i<floors.length; i++)
            for(int x=0; x<40; x++)
                for(int y=0; y<40; y++)
                    if (floors[i].getFloorTypeAt((x+update)%3, (y+update)%3) == FloorType.PARKABLE)     // PARKABLE
                        gc.fillRect(x * NODE_WIDTH, y * NODE_HEIGHT, NODE_WIDTH, NODE_HEIGHT);


        gc.setFill(Color.BLACK);

        for(int i=0; i<floors.length; i++)
            for(int x=0; x<40; x++)
                for(int y=0; y<40; y++)
                    if (floors[i].getFloorTypeAt((x+update)%3, (y+update)%3) == FloorType.ROAD)         // road
                        gc.fillRect(x * NODE_WIDTH, y * NODE_HEIGHT, NODE_WIDTH, NODE_HEIGHT);
    }

    @Override
    public void onObservableChanged() {
        update++;
        step(null);
    }
}
