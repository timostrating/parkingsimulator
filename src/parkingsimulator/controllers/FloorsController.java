package parkingsimulator.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import parkingsimulator.Floor;
import parkingsimulator.FloorType;
import parkingsimulator.Listener;

import java.util.Random;

/**
 * Created by Sneeuwpopsneeuw on 17-Jan-18.
 */
public class FloorsController implements Listener {

    @FXML
    private Canvas canvas;
    private GraphicsContext gc;

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
//        ApplicationController.getInstance().registerListener( this );
    }


    private static final int NODE_WIDTH = 40;
    private static final int NODE_HEIGHT = 40;

    @FXML
    public void step(ActionEvent event) {
        for(int i=0; i<floors.length; i++) {
            for(int x=0; x<floors[i].getRows(); x++) {
                for(int y=0; y<floors[i].getColmns(); y++) {
                    if (floors[i].getFloorTypeAt(x, y) == FloorType.PARKABLE)
                        gc.setFill(Color.GREEN);
                    else
                        gc.setFill(Color.BLACK);

//                    gc.strokeOval(x * NODE_WIDTH +5, y * NODE_HEIGHT +5, NODE_WIDTH-10, NODE_HEIGHT-10);
                    gc.fillRect(x * NODE_WIDTH, y * NODE_HEIGHT, NODE_WIDTH, NODE_HEIGHT);

                }
            }
        }
    }

    @Override
    public void onObservableChanged() {
        step(null);
    }
}
