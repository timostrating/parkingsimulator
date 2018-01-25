package com.parkingtycoon.controllers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.parkingtycoon.CompositionRoot;
import com.parkingtycoon.helpers.IsometricConverter;
import com.parkingtycoon.helpers.Logger;
import com.parkingtycoon.helpers.UpdateableController;
import com.parkingtycoon.models.BluePrintModel;
import com.parkingtycoon.models.BuildableModel;
import com.parkingtycoon.models.FloorModel;

import java.util.ArrayList;
import java.util.EnumSet;

public class BluePrintsController extends UpdateableController {

    public BluePrintModel toBeBuilt;

    private ArrayList<BluePrintModel> bluePrints = new ArrayList<BluePrintModel>(){{

        add(new BluePrintModel(
                // title:
                "Entrance barrier",
                // description:
                "Your garage needs at least one entrance.",
                // price:
                100,
                // FloorTypes that this building can be build on:
                EnumSet.of(FloorModel.FloorType.ROAD),
                // builder:
                (x, y) -> CompositionRoot.getInstance().entrancesController.createEntrance(x, y)
        ));

    }};

    public BluePrintsController() {
        CompositionRoot root = CompositionRoot.getInstance();
        root.simulationController.registerUpdatable(this);
    }

    private void build() {

        Vector3 isometric = new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0);
        CompositionRoot.getInstance().renderController.getMainCamera().unproject(isometric);

        Vector2 normal = new Vector2(isometric.x, isometric.y);
        IsometricConverter.isometricToNormal(normal);

        BuildableModel building = bluePrints.get(0).builder.build((int) normal.x, (int) normal.y);
    }

    @Override
    public void update() {

        // TODO: Gdx.input not always working on seperate thread!!!
        toBeBuilt = bluePrints.get(0);

        if (Gdx.input.isButtonPressed(0) && CompositionRoot.getInstance().financialController.spend(toBeBuilt.price)) {

            Logger.info("gggg");

            build();
        }


    }
}
