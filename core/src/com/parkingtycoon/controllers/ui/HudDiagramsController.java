package com.parkingtycoon.controllers.ui;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.parkingtycoon.CompositionRoot;
import com.parkingtycoon.controllers.UpdatableController;
import com.parkingtycoon.helpers.interfaces.Showable;
import com.parkingtycoon.models.CarModel;
import com.parkingtycoon.models.ui.DiagramModel;
import com.parkingtycoon.views.BaseView;
import com.parkingtycoon.views.ui.HudDiagramsView;

/**
 * This Controller is responsible for setting up diagrams
 *
 * @author Timo Strating
 */
public class HudDiagramsController extends UpdatableController implements Showable {

    private final CompositionRoot root;
    private DiagramModel diagramMoney = new DiagramModel("Moneys diagram ???", DiagramModel.DiagramModelType.MONEY, Color.GREEN);
    private DiagramModel diagramTotalCars = new DiagramModel("Total diagram ???", DiagramModel.DiagramModelType.TOTAL_CARS, Color.RED);
    private DiagramModel diagramAdHocCars = new DiagramModel("Adhoc diagram ???", DiagramModel.DiagramModelType.ADHOC_CARS, new Color(.1f, 1, .3f, 1));
    private DiagramModel diagramReservedCars = new DiagramModel("Reserved diagram ???", DiagramModel.DiagramModelType.RESERVED_CARS, new Color(.1f, .2f, 1, 1));
    private DiagramModel diagramVipCars = new DiagramModel("Vip diagram ???", DiagramModel.DiagramModelType.VIP_CARS, Color.PINK);


    /**
     * Set the root variable, We do this because moving from the heap to the stack and other wise around is expansive.
     */
    public HudDiagramsController(Stage stage) {
        root = CompositionRoot.getInstance();
    }

    /**
     * A helper that registers a view to all models that this class is holding.
     */
    private void registerToModels(BaseView view) {
        diagramMoney.registerView(view);
        diagramTotalCars.registerView(view);
        diagramAdHocCars.registerView(view);
        diagramReservedCars.registerView(view);
        diagramVipCars.registerView(view);
    }

    /**
     * Update the models
     */
    @Override
    public void update() {
        diagramMoney.addToHistory(root.financialController.getAmount());

        long adHocs=0, reserveds=0, vips=0;
        for (CarModel carModel : root.carsController.pathFollowers) {
            if (!carModel.parked)
                continue;
            if (carModel.carType == CarModel.CarType.AD_HOC)
                adHocs++;
            if (carModel.carType == CarModel.CarType.RESERVED)
                reserveds++;
            if (carModel.carType == CarModel.CarType.VIP)
                vips++;
        }
        diagramTotalCars.addToHistory(root.carsController.pathFollowers.size());
        diagramAdHocCars.addToHistory(adHocs);
        diagramReservedCars.addToHistory(reserveds);
        diagramVipCars.addToHistory(vips);
    }

    /**
     * You are allowed to have multiple diagram windows open at the same time, so create a new view when you want to show.
     *
     * @param stage the stage to show the window on.
     */
    @Override
    public void show(Stage stage) {
        registerToModels(new HudDiagramsView(stage, diagramMoney, diagramTotalCars, diagramAdHocCars, diagramReservedCars, diagramVipCars));
    }
}
