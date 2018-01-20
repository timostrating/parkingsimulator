package com.parkingtycoon;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.parkingtycoon.controllers.FloorsController;
import com.parkingtycoon.controllers.SimulationController;
import com.parkingtycoon.views.BaseView;
import com.parkingtycoon.views.SpriteView;

import java.util.ArrayList;

public class Game extends ApplicationAdapter {

	private Stage stage;
	private Skin skin;




	private static Game instance;

	public static Game getInstance() {
		return instance;
	}

	public int worldWidth, worldHeight;

	private SimulationController simulationController;
	private FloorsController floorsController;

	private ArrayList<BaseView> views = new ArrayList<>();

	private OrthographicCamera worldCamera;
	private SpriteBatch batch;

	public Game() {
		if (instance != null)
			throw new RuntimeException("Game already instantiated");
		instance = this;
	}

	@Override
	public void create () {
		skin = new Skin(Gdx.files.internal("ui/uiskin.json"));
		stage = new Stage(new ScreenViewport());

		final TextButton button = new TextButton("Click me", skin, "default");
		button.setWidth(200);
		button.setHeight(200);

		final Dialog dialog = new Dialog("Click message", skin);

		stage.addActor(button);
		Gdx.input.setInputProcessor(stage);

		worldCamera = new OrthographicCamera();
		batch = new SpriteBatch();
//
		resize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

		simulationController = new SimulationController();
		floorsController = new FloorsController();

		Gdx.input.setInputProcessor(new InputProcessor());

		new SpriteView("badlogic.jpg", null);

	}

	@Override
	public void render () {

		simulationController.update();

		Logger.info(worldCamera.unproject(new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0)));

        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        for (BaseView v : views)
        	v.preRender();

        batch.setProjectionMatrix(worldCamera.combined);
        batch.begin();

		for (BaseView v : views)
			v.draw(batch);

		batch.end();

		stage.act(Gdx.graphics.getDeltaTime());
		stage.draw();
	}

	@Override
	public void resize(int width, int height) {
		worldCamera.setToOrtho(false, 10f * width / height, 10);
	}

	public SimulationController getSimulationController() {
		return simulationController;
	}

	public FloorsController getFloorsController() {
		return floorsController;
	}

	public OrthographicCamera getWorldCamera() {
		return worldCamera;
	}

	public void registerView(BaseView view) {
		views.add(view);
	}

	public void unregisterView(BaseView view) {
		views.remove(view);
	}

	public Vector2 normalToIsometric(Vector2 convert) {

		float normalX = convert.x;
		float normalY = convert.y;

		convert.set(worldWidth * 2, worldHeight);

		convert.x += normalX * 2;
		convert.y -= normalX;

		convert.x -= normalY * 2;
		convert.y -= normalY;

		return convert;
	}

	public Vector2 isometricToNormal(Vector2 convert) {

		float isoX = convert.x;
		float isoY = convert.y;

		float xDiff = isoX - worldWidth * 2;
		float yDiff = -2 * (isoY - worldHeight);

		convert.x = (xDiff + yDiff) / 4;
		convert.y = (yDiff - xDiff) / 4;
		return convert;
	}

}

