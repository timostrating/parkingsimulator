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

    public BluePrintModel nextToBeBuilt;

    private BluePrintModel toBeBuilt;
    private BluePrintView bluePrintView;
    private boolean clicked;

    public ArrayList<BluePrintModel> bluePrints = new ArrayList<BluePrintModel>() {{

        add(new BluePrintModel(
                // title:
                "Entrance",
                // description:
                "Your garage needs at least one entrance.",
                // sprite:
                "sprites/entrance", 0, 0,
                // price:
                100,
                // FloorTypes that this building can be build on:
                EnumSet.of(FloorModel.FloorType.ROAD, FloorModel.FloorType.GRASS, FloorModel.FloorType.CONCRETE),
                // Floor that will appear under the building:
                new FloorModel.FloorType[][]{
                        {null, FloorModel.FloorType.ROAD, null},
                        {FloorModel.FloorType.GRASS, FloorModel.FloorType.BARRIER, FloorModel.FloorType.GRASS},
                        {null, FloorModel.FloorType.ROAD, null}
                },
                // builder:
                (x, y, angle, floor) -> CompositionRoot.getInstance().entrancesController.createEntrance(x, y, angle, floor, false),
                // build on all floors at once:
                false
        ));

        add(new BluePrintModel(
                // title:
                "V.I.P. Entrance",
                // description:
                "Build a special entrance for parking-subscribers.",
                // sprite:
                "sprites/entrance", 0, 0,
                // price:
                1000,
                // FloorTypes that this building can be build on:
                EnumSet.of(FloorModel.FloorType.ROAD, FloorModel.FloorType.GRASS, FloorModel.FloorType.CONCRETE),
                // Floor that will appear under the building:
                new FloorModel.FloorType[][]{
                        {null, FloorModel.FloorType.ROAD, null},
                        {FloorModel.FloorType.GRASS, FloorModel.FloorType.BARRIER, FloorModel.FloorType.GRASS},
                        {null, FloorModel.FloorType.ROAD, null}
                },
                // builder:
                (x, y, angle, floor) -> CompositionRoot.getInstance().entrancesController.createEntrance(x, y, angle, floor, true),
                // build on all floors at once:
                false
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
                EnumSet.of(FloorModel.FloorType.ROAD, FloorModel.FloorType.GRASS, FloorModel.FloorType.CONCRETE),
                // Floor that will appear under the building:
                new FloorModel.FloorType[][]{
                        {null, FloorModel.FloorType.ROAD, null},
                        {FloorModel.FloorType.GRASS, FloorModel.FloorType.BARRIER, FloorModel.FloorType.GRASS},
                        {null, FloorModel.FloorType.ROAD, null}
                },
                // builder:
                (x, y, angle, floor) -> CompositionRoot.getInstance().exitsController.createExit(x, y, angle, floor),
                // build on all floors at once:
                false
        ));

        add(new BluePrintModel(
                // title:
                "Car elevator",
                // description:
                "With elevators cars can switch floors.",
                // sprite:
                "sprites/entrance", 0, 0,
                // price:
                200,
                // FloorTypes that this building can be build on:
                EnumSet.of(FloorModel.FloorType.ROAD, FloorModel.FloorType.GRASS, FloorModel.FloorType.CONCRETE),
                // Floor that will appear under the building:
                new FloorModel.FloorType[][]{
                        {FloorModel.FloorType.CONCRETE, FloorModel.FloorType.CONCRETE, FloorModel.FloorType.CONCRETE},
                        {FloorModel.FloorType.CONCRETE, FloorModel.FloorType.ROAD, FloorModel.FloorType.CONCRETE},
                        {FloorModel.FloorType.CONCRETE, FloorModel.FloorType.ROAD, FloorModel.FloorType.CONCRETE},
                },
                // builder:
                (x, y, angle, floor) -> CompositionRoot.getInstance().exitsController.createExit(x, y, angle, floor),
                // build on all floors at once:
                true,
                // only allowed angles:
                0, 3
        ));

    }};

    public BluePrintsController() {
        super();
        CompositionRoot root = CompositionRoot.getInstance();

        root.inputController.onKeyDown.put(Input.Keys.R, () -> {
            if (nextToBeBuilt == null)
                return false;

            boolean next = false, found = false;
            for (int angle : nextToBeBuilt.allowedAngles) {
                if (next) {
                    toBeBuilt.setAngle(angle);
                    found = true;
                    break;
                }
                next = angle == nextToBeBuilt.getAngle();
            }
            if (!found)
                toBeBuilt.setAngle(toBeBuilt.allowedAngles[0]);

            return true;
        });

        root.inputController.onKeyDown.put(Input.Keys.ESCAPE, () -> {
            if (nextToBeBuilt == null)
                return false;

            nextToBeBuilt = null;
            return true;
        });

        root.inputController.onMouseButtonDown.add((screenX, screenY, button) -> {
            if (nextToBeBuilt != null) {
                if (button == 0)
                    clicked = true;
                if (button == 1)
                    nextToBeBuilt = null;
                else
                    return false;
                return true;
            }
            return false;
        });

        // temporary:
        root.inputController.onKeyDown.put(Input.Keys.NUM_1, () -> {
            nextToBeBuilt = bluePrints.get(0);
            return true;
        });

        // temporary:
        root.inputController.onKeyDown.put(Input.Keys.NUM_2, () -> {
            nextToBeBuilt = bluePrints.get(2);
            return true;
        });

        // temporary:
        root.inputController.onKeyDown.put(Input.Keys.NUM_7, () -> {
            nextToBeBuilt = bluePrints.get(1);
            return true;
        });

        // temporary:
        root.inputController.onKeyDown.put(Input.Keys.NUM_8, () -> {
            nextToBeBuilt = bluePrints.get(3);
            return true;
        });

    }

    private void unsetToBeBuilt() {
        if (toBeBuilt != null) {
            toBeBuilt.setActive(false);
            toBeBuilt.unregisterView(bluePrintView);
            toBeBuilt.unregisterView(CompositionRoot.getInstance().floorsController.view);
            bluePrintView = null;
            toBeBuilt = null;
        }
    }

    private void setToBeBuilt(BluePrintModel bluePrint) {

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

        if (nextToBeBuilt != toBeBuilt) {
            unsetToBeBuilt();
            if (nextToBeBuilt != null)
                setToBeBuilt(nextToBeBuilt);
        }

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

        BuildableModel building = bluePrint.builder.build(originX, originY, bluePrint.getAngle(), floorIndex);

        if (bluePrint.buildOnAllFloors)
            for (int i = 0; i < floorsController.floors.size(); i++)
                placeBuildingOnFloor(building, bluePrint, i, originX, originY);

        else
            placeBuildingOnFloor(building, bluePrint, floorIndex, originX, originY);
    }

    private void placeBuildingOnFloor(
            BuildableModel building,
            BluePrintModel bluePrint,
            int floorIndex,
            int originX, int originY) {

        Boolean[][] tilesChanged = new Boolean[Game.WORLD_WIDTH][];

        CompositionRoot root = CompositionRoot.getInstance();
        FloorModel floor = root.floorsController.floors.get(floorIndex);

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

                if (tilesChanged[worldX] == null)
                    tilesChanged[worldX] = new Boolean[Game.WORLD_HEIGHT];

                BuildableModel currentBuilding = floor.buildings[worldX][worldY];
                if (currentBuilding != null)
                    demolish(currentBuilding);

                floor.buildings[worldX][worldY] = building;
                tilesChanged[worldX][worldY] = true;

                floor.setTile(worldX, worldY, floorType);

            }
        }

        root.floorsController.updateBuildings(floorIndex);
        root.carsController.onTerrainChange(floorIndex, tilesChanged);
    }

    public void demolish(BuildableModel building) {

        FloorsController floorsController = CompositionRoot.getInstance().floorsController;

        if (building.onAllFloors)
            for (int i = 0; i < floorsController.floors.size(); i++)
                removeBuildingFromFloor(
                        building,
                        i
                );

        else
            removeBuildingFromFloor(
                    building,
                    building.floor
            );

        building.setDemolished(true);
    }

    private void removeBuildingFromFloor(BuildableModel building, int floorIndex) {

        Boolean[][] tilesChanged = new Boolean[Game.WORLD_WIDTH][];

        CompositionRoot root = CompositionRoot.getInstance();
        FloorModel floor = root.floorsController.floors.get(floorIndex);

        for (int x = 0; x < Game.WORLD_WIDTH; x++) {
            for (int y = 0; y < Game.WORLD_HEIGHT; y++) {
                if (floor.buildings[x] != null && floor.buildings[x][y] == building) {

                    if (tilesChanged[x] == null)
                        tilesChanged[x] = new Boolean[Game.WORLD_HEIGHT];

                    floor.setTile(x, y, floorIndex == 0 ? FloorModel.FloorType.GRASS : FloorModel.FloorType.CONCRETE);
                    floor.buildings[x][y] = null;

                    tilesChanged[x][y] = null;
                }
            }
        }

        root.carsController.onTerrainChange(floorIndex, tilesChanged);
    }

}
