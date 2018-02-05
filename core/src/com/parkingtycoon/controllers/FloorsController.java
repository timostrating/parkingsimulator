package com.parkingtycoon.controllers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.*;
import com.parkingtycoon.CompositionRoot;
import com.parkingtycoon.Game;
import com.parkingtycoon.helpers.CoordinateRotater;
import com.parkingtycoon.helpers.IsometricConverter;
import com.parkingtycoon.helpers.Logger;
import com.parkingtycoon.models.BluePrintModel;
import com.parkingtycoon.models.BuildingModel;
import com.parkingtycoon.models.CarModel;
import com.parkingtycoon.models.FloorModel;
import com.parkingtycoon.views.FlagView;
import com.parkingtycoon.views.FloorsView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * This class is responsible for providing the floor of te simulation world
 */
public class FloorsController extends UpdateableController {

    public FloorsView view;
    public ArrayList<FloorModel> floors = new ArrayList<>();
    public FloorModel.FloorType nextFloorType;
    public boolean up, down;

    private int currentFloor = 0, nextFloor;

    public static final int BUILD_MARGIN = 25;

    public static boolean inBuildZone(int x, int y) {
        return x >= BUILD_MARGIN
                && x < Game.WORLD_WIDTH - BUILD_MARGIN
                && y >= BUILD_MARGIN
                && y < Game.WORLD_HEIGHT - BUILD_MARGIN;
    }

    public FloorsController() {

        super();
        CompositionRoot root = CompositionRoot.getInstance();

        view = new FloorsView();

//        for (int i = 0; i < 10; i++)
//            createFloor(i == 0);
//        setCurrentFloor(0);

        // temporary:
        root.inputController.onKeyDown.put(Input.Keys.NUM_3, () -> {
            nextFloorType = FloorModel.FloorType.ROAD;
            return true;
        });

        // temporary:
        root.inputController.onKeyDown.put(Input.Keys.NUM_4, () -> {
            nextFloorType = FloorModel.FloorType.PARKABLE;
            return true;
        });

        // temporary:
        root.inputController.onKeyDown.put(Input.Keys.NUM_5, () -> {
            nextFloorType = FloorModel.FloorType.CONCRETE;
            return true;
        });
        // temporary:
        root.inputController.onKeyDown.put(Input.Keys.NUM_6, () -> {
            nextFloorType = FloorModel.FloorType.GRASS;
            return true;
        });

        // temporary to output json:
        root.inputController.onKeyDown.put(Input.Keys.J, () -> {
            toJson();
            return true;
        });

        root.inputController.onKeyDown.put(Input.Keys.UP, () -> {
            up = true;
            return true;
        });

        root.inputController.onKeyDown.put(Input.Keys.DOWN, () -> {
            down = true;
            return true;
        });

        root.inputController.onMouseButtonDown.add((screenX, screenY, button) -> {

            if (!floorExists(currentFloor))
                return false;

            FloorModel f = floors.get(currentFloor);
            if (button == 0 && f.getNewFloorType() != null) {

                if (!f.fromFlagPlaced)
                    f.fromFlagPlaced = true;
                else
                    f.toFlagPlaced = true;

                return true;
            }
            return false;
        });
//        root.inputController.onKeyDown.put(Input.Keys.ESCAPE, () -> { // TODO: cant bind multiple listeners to one key!!!
//            FloorModel f = floors.get(currentFloor);
//            if (f.getNewFloorType() != null) {
//                f.stopPlacing = true;
//                Logger.info("ggg");
//                return true;
//            }
//            return false;
//        });
    }

    @Override
    public void update() {

        FloorModel floor = floors.get(currentFloor);

        if (up || down) {
            int toBeNext = currentFloor + (up ? 1 : -1);

            if (floorExists(toBeNext)) {

                floor.setTransition(false, up ? -1 : 1);
                new Timer().scheduleTask(new Timer.Task() {
                    @Override
                    public void run() {
                        nextFloor = toBeNext;
                    }
                }, FloorModel.TRANSITION_DURATION);
            }

            up = false;
            down = false;
        }

        if (nextFloor != currentFloor) {
            int prevIndex = currentFloor;
            setCurrentFloor(nextFloor);

            if (nextFloor != currentFloor) // the floor was just removed
                nextFloor = currentFloor;

            floor = floors.get(currentFloor);
            floor.setTransition(true, prevIndex <= currentFloor ? -1 : 1);
        }

        if (nextFloorType != null) {
            floor.setNewFloorType(nextFloorType);
            nextFloorType = null;
        }

        if (floor.getNewFloorType() != null) {
            setNewFloorFlag(floor);
            if (floor.toFlagPlaced)
                placeNewFloorTiles(floor);
            if (floor.stopPlacing)
                floor.setNewFloorType(null);
        }

        for (FloorModel f : floors) {
            for (int x = 0; x < Game.WORLD_WIDTH; x++) {
                for (int y = 0; y < Game.WORLD_HEIGHT; y++) {
                    f.waitingTime[x][y] += f.waitingTime[x][y] > 0 ? -1 : 0;
                }
            }
        }

    }

    private boolean floorExists(int index) {
        return index >= 0 && index < floors.size();
    }

    public void setCurrentFloor(int index) {
        if (!floorExists(index))
            return;

        int prevFloor = currentFloor;
        currentFloor = index;

        updateBuildings(prevFloor);
        updateBuildings(currentFloor);

        for (int i = 0; i < floors.size(); i++)
            floors.get(i).setCurrentFloor(i == index);

        CarsController carsController = CompositionRoot.getInstance().carsController;
        if (carsController != null)
            carsController.onFloorSwitch(index);
    }

    public int getCurrentFloor() {
        return currentFloor;
    }

    public void updateBuildings(int floorIndex) {
        FloorModel floor = floors.get(floorIndex);
        for (int x = 0; x < Game.WORLD_WIDTH; x++) {
            for (int y = 0; y < Game.WORLD_HEIGHT; y++) {
                if (floor.buildings[x] != null
                        && floor.buildings[x][y] != null) {
                    BuildingModel building = floor.buildings[x][y];
                    building.setOnActiveFloor(building.floor == currentFloor);
                }
            }
        }
    }

    private void createFloor(boolean first) {
        FloorModel floor = createEmptyFloor();

        for (int x = 0; x < Game.WORLD_WIDTH; x++) {

            for (int y = 0; y < Game.WORLD_HEIGHT; y++) {

                if (first)
                    floor.tiles[x][y] = inBuildZone(x, y) ?
                            FloorModel.FloorType.GRASS :
                            (x % 9 == 0 || y % 9 == 0 ? FloorModel.FloorType.ROAD : FloorModel.FloorType.GRASS);
                else
                    floor.tiles[x][y] = inBuildZone(x, y) ? FloorModel.FloorType.CONCRETE : null;
            }
        }

        floor.registerView(view);
        floors.add(floor);
    }

    private FloorModel createEmptyFloor() {
        FloorModel floor = new FloorModel();

        for (int x = 0; x < Game.WORLD_WIDTH; x++) {
            floor.tiles[x] = new FloorModel.FloorType[Game.WORLD_HEIGHT];
            floor.waitingTime[x] = new int[Game.WORLD_HEIGHT];
            floor.accessibleParkables[x] = new Boolean[Game.WORLD_HEIGHT];
            floor.parkedCars[x] = new CarModel[Game.WORLD_HEIGHT];
        }
        return floor;
    }

    private void setNewFloorFlag(FloorModel floor) {
        Vector2 cursor = IsometricConverter.cursorToNormal();

        int[] flagCoordinates = new int[]{
                Math.max(0, Math.min((int) cursor.x, Game.WORLD_WIDTH - 1)),
                Math.max(0, Math.min((int) cursor.y, Game.WORLD_HEIGHT - 1))
        };

        if (floor.getNewFloorFrom() == null)
            showFlag(floor, true);

        if (!floor.fromFlagPlaced)
            floor.setNewFloorFrom(flagCoordinates);
        else {
            if (floor.getNewFloorTo() == null)
                showFlag(floor, false);

            floor.setNewFloorTo(flagCoordinates);
        }

        Boolean[][] newFloorValid = new Boolean[Game.WORLD_WIDTH][];

        for (int x = Math.min(flagCoordinates[0], floor.getNewFloorFrom()[0]);
             x <= Math.max(flagCoordinates[0], floor.getNewFloorFrom()[0]);
             x++) {

            newFloorValid[x] = new Boolean[Game.WORLD_HEIGHT];

            for (int y = Math.min(flagCoordinates[1], floor.getNewFloorFrom()[1]);
                 y <= Math.max(flagCoordinates[1], floor.getNewFloorFrom()[1]);
                 y++) {

                newFloorValid[x][y] = inBuildZone(x, y)
                        && (floor.buildings[x] == null || floor.buildings[x][y] == null);

            }
        }

        floor.setNewFloorValid(newFloorValid);
    }

    private void showFlag(FloorModel floor, boolean green) {
        FlagView flag = new FlagView(green);
        flag.show();
        floor.registerView(flag);
    }

    private void placeNewFloorTiles(FloorModel floor) {

        CarsController carsController = CompositionRoot.getInstance().carsController;

        FloorModel.FloorType floorType = floor.getNewFloorType();
        Boolean[][] newFloorValid = floor.getNewFloorValid();

        for (int x = Math.min(floor.getNewFloorTo()[0], floor.getNewFloorFrom()[0]);
             x <= Math.max(floor.getNewFloorTo()[0], floor.getNewFloorFrom()[0]);
             x++) {

            if (newFloorValid[x] == null)
                continue;

            for (int y = Math.min(floor.getNewFloorTo()[1], floor.getNewFloorFrom()[1]);
                 y <= Math.max(floor.getNewFloorTo()[1], floor.getNewFloorFrom()[1]);
                 y++) {

                if (Boolean.TRUE.equals(newFloorValid[x][y]) && CompositionRoot.getInstance().financialController.spend(10)) {
                    floor.setTile(x, y, floorType);

                    CarModel parkedHere = floor.parkedCars[x][y];
                    if (parkedHere != null && !carsController.sendToExit(parkedHere)) {

                        carsController.sendToEndOfTheWorld(parkedHere, true);
                    }
                }
            }
        }

        floor.setNewFloorType(null);
        checkParkables(floor);
        carsController.onTerrainChange(floors.indexOf(floor), newFloorValid);
    }

    private void checkParkables(FloorModel floor) {

        for (int x = 0; x < Game.WORLD_WIDTH; x++) {

            FloorModel.FloorType prevType = FloorModel.FloorType.GRASS;

            for (int y = 0; y < Game.WORLD_HEIGHT; y++) {

                FloorModel.FloorType type = floor.tiles[x][y];
                floor.accessibleParkables[x][y] = type != FloorModel.FloorType.PARKABLE
                        || prevType == FloorModel.FloorType.ROAD
                        || isParkableAccessible(floor, x, y);

                prevType = type;
            }
        }
    }

    private boolean isParkableAccessible(FloorModel floor, int parkableX, int parkableY) {

        for (int angle = 0; angle < 4; angle++) {

            int x = parkableX + CoordinateRotater.rotate(1, 1, 0, 1, angle);
            int y = parkableY + CoordinateRotater.rotate(0, 1, 1, 1, angle);

            if (!Game.inWorld(x, y))
                continue;

            if (floor.tiles[x][y] == FloorModel.FloorType.ROAD)
                return true;

        }
        return false;
    }

    public void toJson() {

        JsonValue json = new JsonValue(JsonValue.ValueType.object);
        JsonValue jsonFloors = new JsonValue(JsonValue.ValueType.array);

        for (FloorModel floor : floors) {

            JsonValue floorArray = new JsonValue(JsonValue.ValueType.array);

            for (int x = 0; x < Game.WORLD_WIDTH; x++) {
                for (int y = 0; y < Game.WORLD_HEIGHT; y++) {

                    String floorType = floor.tiles[x][y] == null ? "null" : floor.tiles[x][y].toString();
                    floorArray.addChild(new JsonValue(floorType));

                }
            }
            jsonFloors.addChild(floorArray);
        }

        BluePrintsController bluePrintsController = CompositionRoot.getInstance().bluePrintsController;
        JsonValue allBuildingsJson = new JsonValue(JsonValue.ValueType.array);

        for (BuildingModel building : bluePrintsController.buildings) {

            JsonValue buildingJson = new JsonValue(JsonValue.ValueType.object);
            buildingJson.addChild("bluePrintID", new JsonValue(bluePrintsController.bluePrints.indexOf(building.bluePrint)));
            buildingJson.addChild("angle", new JsonValue(building.angle));
            buildingJson.addChild("floor", new JsonValue(building.floor));
            buildingJson.addChild("x", new JsonValue(building.x));
            buildingJson.addChild("y", new JsonValue(building.y));
            allBuildingsJson.addChild(buildingJson);
        }

        json.addChild("buildings", allBuildingsJson);
        json.addChild("floors", jsonFloors);

        SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MMM.yyyy HH.mm");
        String savePath = "saves/" + dateFormat.format(new Date(System.currentTimeMillis())) + ".parkingsimulatortycoon";

        FileHandle output = Gdx.files.local(savePath);
        output.writeString(json.toJson(JsonWriter.OutputType.json), false);

        Logger.info("Game saved to '" + savePath + "'");
    }

    public void fromJson(String savePath) {

        JsonValue json = new JsonReader().parse(Gdx.files.local(savePath));
        JsonValue jsonFloors = json.get("floors");

        floorsLoop:
        for (JsonValue jsonFloor = jsonFloors.child; jsonFloor != null; jsonFloor = jsonFloor.next) {

            String[] floorTypeStrings = jsonFloor.asStringArray();
            FloorModel floor = createEmptyFloor();

            int x = 0;
            int y = 0;
            for (int i = 0; i < floorTypeStrings.length; i++) {

                String floorTypeString = floorTypeStrings[i];
                FloorModel.FloorType floorType = floorTypeString.equals("null") ?
                        null : FloorModel.FloorType.valueOf(floorTypeStrings[i]);

                floor.tiles[x][y] = floorType;

                if (++y == Game.WORLD_HEIGHT) {
                    if (++x == Game.WORLD_WIDTH) {
                        floor.registerView(view);
                        floors.add(floor);
                        continue floorsLoop;
                    }
                    y = 0;
                }
            }
        }

        setCurrentFloor(0);

        JsonValue jsonAllBuildings = json.get("buildings");
        BluePrintsController bluePrintsController = CompositionRoot.getInstance().bluePrintsController;

        for (JsonValue jsonBuilding = jsonAllBuildings.child; jsonBuilding != null; jsonBuilding = jsonBuilding.next) {

            int id = jsonBuilding.getInt("bluePrintID");
            int x = jsonBuilding.getInt("x");
            int y = jsonBuilding.getInt("y");
            int angle = jsonBuilding.getInt("angle");
            int floorIndex = jsonBuilding.getInt("floor");

            BluePrintModel bluePrint = bluePrintsController.bluePrints.get(id);
            bluePrintsController.build(bluePrint, x, y, angle, floorIndex);
        }
    }

}
