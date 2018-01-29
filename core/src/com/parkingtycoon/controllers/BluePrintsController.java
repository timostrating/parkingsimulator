package com.parkingtycoon.controllers;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.math.Vector2;
import com.parkingtycoon.CompositionRoot;
import com.parkingtycoon.Game;
import com.parkingtycoon.helpers.CoordinateRotater;
import com.parkingtycoon.helpers.IsometricConverter;
import com.parkingtycoon.models.BluePrintModel;
import com.parkingtycoon.models.BuildableModel;
import com.parkingtycoon.models.FloorModel;
import com.parkingtycoon.views.BluePrintView;

import java.util.ArrayList;
import java.util.EnumSet;

/**
 * The responsibility of this class is to place buildings on the current floor.
 */
public class BluePrintsController extends UpdateableController {

    private BluePrintModel toBeBuilt;
    private BluePrintView bluePrintView;
    private boolean clicked, unset;

    public ArrayList<BluePrintModel> bluePrints = new ArrayList<BluePrintModel>() {{

        add(new BluePrintModel(
                // title:
                "Entrance barrier",
                // description:
                "Your garage needs at least one entrance.",
                // sprite:
                "sprites/entrance", 0, 0,
                // price:
                100,
                // FloorTypes that this building can be build on:
                EnumSet.of(FloorModel.FloorType.ROAD, FloorModel.FloorType.GRASS),
                // Floor that will appear under the building:
                new FloorModel.FloorType[][]{
                        {null, FloorModel.FloorType.ROAD, null},
                        {FloorModel.FloorType.GRASS, FloorModel.FloorType.ROAD, FloorModel.FloorType.GRASS},
                        {null, FloorModel.FloorType.ROAD, null}
                },
                // builder:
                (x, y, angle, floor) -> CompositionRoot.getInstance().entrancesController.createEntrance(x, y, angle, floor)
        ));

        add(new BluePrintModel(
                // title:
                "Exit",
                // description:
                "This is where customers pay and leave",
                // sprite:
                "sprites/entrance", 0, 0,
                // price:
                200,
                // FloorTypes that this building can be build on:
                EnumSet.of(FloorModel.FloorType.ROAD),
                // Floor that will appear under the building:
                new FloorModel.FloorType[][]{
                        {FloorModel.FloorType.GRASS, FloorModel.FloorType.ROAD, FloorModel.FloorType.GRASS},
                },
                // builder:
                (x, y, angle, floor) -> CompositionRoot.getInstance().exitsController.createExit(x, y, angle, floor)
        ));

    }};

    public BluePrintsController() {
        CompositionRoot root = CompositionRoot.getInstance();
        root.simulationController.registerUpdatable(this);

        root.inputController.onKeyDown.put(Input.Keys.R, () -> {
            if (toBeBuilt == null || unset)
                return false;

            int newAngle = toBeBuilt.getAngle() + 1;
            toBeBuilt.setAngle(newAngle >= 4 ? 0 : newAngle);
            return true;
        });

        root.inputController.onKeyDown.put(Input.Keys.ESCAPE, () -> {
            if (toBeBuilt == null || unset)
                return false;

            unset = true;
            return true;
        });

        root.inputController.onMouseButtonDown.add((screenX, screenY, button) -> {
            if (toBeBuilt != null && !unset) {
                if (button == 0)
                    clicked = true;
                if (button == 1)
                    unset = true;
                else
                    return false;
                return true;
            }
            return false;
        });

        // temporary:
        root.inputController.onKeyDown.put(Input.Keys.NUM_1, () -> {
            setToBeBuilt(bluePrints.get(0));
            return true;
        });

        // temporary:
        root.inputController.onKeyDown.put(Input.Keys.NUM_2, () -> {
            setToBeBuilt(bluePrints.get(1));
            return true;
        });

    }

    public void unsetToBeBuilt() {
        if (toBeBuilt != null) {
            toBeBuilt.setActive(false);
            toBeBuilt.unregisterView(bluePrintView);
            toBeBuilt.unregisterView(CompositionRoot.getInstance().floorsController.view);
            bluePrintView = null;
            toBeBuilt = null;
        }
    }

    public void setToBeBuilt(BluePrintModel bluePrint) {

        unsetToBeBuilt();
        toBeBuilt = bluePrint;
        bluePrintView = new BluePrintView(toBeBuilt.spritePath);
        bluePrintView.show();
        toBeBuilt.setActive(true);
        toBeBuilt.registerView(bluePrintView);
        toBeBuilt.registerView(CompositionRoot.getInstance().floorsController.view);
        toBeBuilt.setAngle(0);

    }

    @Override
    public void update() {

        if (toBeBuilt != null) {

            CompositionRoot root = CompositionRoot.getInstance();

            Vector2 cursor = IsometricConverter.cursorToNormal();
            int x = (int) cursor.x, y = (int) cursor.y;

            boolean canBuild = canBuild(toBeBuilt, x, y);
            toBeBuilt.x = x;
            toBeBuilt.y = y;

            if (clicked && canBuild && root.financialController.spend(toBeBuilt.price)) {

                build(toBeBuilt, x, y);

            } else if (clicked && !canBuild) {
                // todo: message: can't build here
            }

        }

        if (unset)
            unsetToBeBuilt();

        unset = false;
        clicked = false;
    }

    /**
     * Checks if a building can be build on a given position
     *
     * @param bluePrint The bluePrint of the building
     * @param originX   The x-position of the building
     * @param originY   The y-position of the building
     * @return Can the building be build here?
     */
    public boolean canBuild(BluePrintModel bluePrint, int originX, int originY) {

        FloorsController floorsController = CompositionRoot.getInstance().floorsController;
        FloorModel floor = floorsController.floors.get(floorsController.getCurrentFloor());

        boolean canBuild = true;
        int width = bluePrint.tiles.length, height = bluePrint.tiles[0].length;
        bluePrint.validTiles = new boolean[width][];

        for (int x = 0; x < width; x++) {

            for (int y = 0; y < height; y++) {

                if (bluePrint.tiles[x][y] == null)
                    continue;

                int worldX = CoordinateRotater.rotate(x, width, y, height, bluePrint.getAngle()) + originX;
                int worldY = CoordinateRotater.rotate(y, height, x, width, bluePrint.getAngle()) + originY;

                boolean canBuildHere =

                        // cannot build in noBuildZone:
                        FloorsController.inBuildZone(worldX, worldY)

                            // can building be built on this floorType?
                            && bluePrint.canBuildOn.contains(floor.tiles[worldX][worldY])

                            // is there already another building here?
                            && (floor.buildings[worldX] == null || floor.buildings[worldX][worldY] == null);

                canBuild &= canBuildHere;

                if (bluePrint.validTiles[x] == null)
                    bluePrint.validTiles[x] = new boolean[height];

                bluePrint.validTiles[x][y] = canBuildHere;
            }
        }

        bluePrint.setCanBuild(canBuild);
        return canBuild;
    }

    private void build(BluePrintModel bluePrint, int originX, int originY) {

        FloorsController floorsController = CompositionRoot.getInstance().floorsController;
        int floorIndex = floorsController.getCurrentFloor();
        FloorModel floor = floorsController.floors.get(floorIndex);

        BuildableModel building = bluePrint.builder.build(originX, originY, bluePrint.getAngle(), floorIndex);

        int width = bluePrint.tiles.length, height = bluePrint.tiles[0].length;

        for (int x = 0; x < width; x++) {

            for (int y = 0; y < height; y++) {

                FloorModel.FloorType floorType = bluePrint.tiles[x][y];
                if (floorType == null)
                    continue;

                int worldX = CoordinateRotater.rotate(x, width, y, height, bluePrint.getAngle()) + originX;
                int worldY = CoordinateRotater.rotate(y, height, x, width, bluePrint.getAngle()) + originY;

                if (floor.buildings[worldX] == null)
                    floor.buildings[worldX] = new BuildableModel[Game.WORLD_HEIGHT];

                floor.buildings[worldX][worldY] = building;

                floor.setTile(worldX, worldY, floorType);

            }
        }
    }
}
