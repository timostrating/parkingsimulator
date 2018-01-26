package com.parkingtycoon.views;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.parkingtycoon.helpers.AABB;
import com.parkingtycoon.helpers.IsometricConverter;
import com.parkingtycoon.helpers.Random;
import com.parkingtycoon.models.BaseModel;
import com.parkingtycoon.models.CarModel;
import com.parkingtycoon.pathfinding.PathFinder;

import java.util.List;

public class CarView extends AnimatedSpriteView {

    private List<PathFinder.Node> path;
    private Color pathColor = new Color((float) Math.random(), (float) Math.random(), (float) Math.random(), 1);
    private float extra = Random.randomInt(4, 10) / 100f;
    private AABB aabb;

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
            aabb = car.aabb;

            spritePosition.set(car.position);
            IsometricConverter.normalToIsometric(spritePosition);
            sprite.setCenter(spritePosition.x, spritePosition.y);

            if (spriteModel != null && !car.direction.isZero()) {

                int frame = 33 - (int) ((car.direction.angle() / 360.01f) * 33) - 1;

                setRegion(spriteModel.frames[frame]);
            }

        }

    }

    @Override
    public void debugRender(ShapeRenderer shapeRenderer) {

        shapeRenderer.setColor(Color.RED);

        if (aabb != null)
            shapeRenderer.rect(aabb.center.x - aabb.halfSize.x, aabb.center.y - aabb.halfSize.y, aabb.halfSize.x * 2, aabb.halfSize.y * 2);

        if (path != null) {
            shapeRenderer.setColor(pathColor);
            PathFinder.Node prevN = null;
            Vector2 prevIso = new Vector2();
            Vector2 iso = new Vector2();

            for (PathFinder.Node n : path) {
                iso.set(n.actualX, n.actualY);
                IsometricConverter.normalToIsometric(iso);
                if (prevN != null) {
                    shapeRenderer.line(prevN.actualX + extra, prevN.actualY + extra, n.actualX + extra, n.actualY + extra);

                    shapeRenderer.line(prevIso.x + extra, prevIso.y + extra, iso.x + extra, iso.y + extra);

                    shapeRenderer.line(n.actualX - .1f, n.actualY - .1f, n.actualX + .1f, n.actualY + .1f);
                    shapeRenderer.line(n.actualX - .1f, n.actualY + .1f, n.actualX + .1f, n.actualY - .1f);
                }
                prevN = n;
                prevIso.set(iso);
            }

        }

        super.debugRender(shapeRenderer);
    }
}

