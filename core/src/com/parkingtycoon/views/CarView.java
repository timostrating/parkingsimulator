package com.parkingtycoon.views;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.parkingtycoon.helpers.IsometricConverter;
import com.parkingtycoon.helpers.Random;
import com.parkingtycoon.models.BaseModel;
import com.parkingtycoon.models.CarModel;
import com.parkingtycoon.pathfinding.PathFinder;

import java.util.List;

public class CarView extends AnimatedSpriteView {

    private Vector2 lastDirection = new Vector2();
    private int lastFrame = -1;
    private List<PathFinder.Node> path;
    private Color pathColor = new Color((float) Math.random(), (float) Math.random(), (float) Math.random(), 1);
    private float extra = Random.randomInt(4, 10) / 100f;
    private int currentNode = 0;

    public CarView() {
        super("sprites/cars/pontiac", true);
    }

    @Override
    public void start() {
        super.start();
    }

    @Override
    public void updateView(BaseModel model) {

        if (model instanceof CarModel) {

            CarModel car = (CarModel) model;

            path = car.path;
            currentNode = car.currentNode;

            spritePosition.set(car.position);
            IsometricConverter.normalToIsometric(spritePosition);
            sprite.setPosition(spritePosition.x, spritePosition.y);

            if (spriteModel != null && !lastDirection.equals(car.direction)) {

                lastDirection.set(car.direction);

                int frame = 33 - (int) ((car.direction.angle() / 360.01f) * 33) - 1;
                if (lastFrame != -1 && frame < lastFrame)
                    frame = lastFrame - 1;
                else if (lastFrame != -1 && frame > lastFrame)
                    frame = lastFrame + 1;

                setRegion(spriteModel.frames[frame]);
                lastFrame = frame;
            }

        }

    }

    @Override
    public void debugRender(ShapeRenderer shapeRenderer) {

        shapeRenderer.setColor(Color.RED);
        Vector2 gridPos = IsometricConverter.isometricToNormal(spritePosition.cpy());
        shapeRenderer.line(gridPos.x - .5f, gridPos.y - .5f, gridPos.x + .5f, gridPos.y + .5f);
        shapeRenderer.line(gridPos.x - .5f, gridPos.y + .5f, gridPos.x + .5f, gridPos.y - .5f);

        if (path != null) {
            shapeRenderer.setColor(pathColor);
            PathFinder.Node prevN = null;

            for (PathFinder.Node n : path) {
                if (prevN != null) {
                    shapeRenderer.line(prevN.actualX + extra, prevN.actualY + extra, n.actualX + extra, n.actualY + extra);

                    shapeRenderer.line(n.actualX - .1f, n.actualY - .1f, n.actualX + .1f, n.actualY + .1f);
                    shapeRenderer.line(n.actualX - .1f, n.actualY + .1f, n.actualX + .1f, n.actualY - .1f);
                }
                prevN = n;
            }

            if (path == null)
                return;

            PathFinder.Node node = path.get(currentNode);
            shapeRenderer.line(gridPos.x, gridPos.y, node.actualX, node.actualY);

        }
    }
}

