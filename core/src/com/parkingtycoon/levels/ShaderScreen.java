package com.parkingtycoon.levels;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.*;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.badlogic.gdx.math.Matrix4;
import com.parkingtycoon.CompositionRoot;
import com.parkingtycoon.Game;
import com.parkingtycoon.controllers.InputController;
import com.parkingtycoon.views.ui.HudView;

/**
 * This Class is a TEST to see how shaders work
 *
 * @author Timo Strating
 */
public class ShaderScreen implements Screen {

    String vertexShader = "attribute vec4 a_position;    \n" +
            "attribute vec4 a_color;\n" +
            "attribute vec2 a_texCoord0;\n" +
            "uniform mat4 u_projTrans;\n" +
            "varying vec4 v_color;" +
            "varying vec2 v_texCoords;" +
            "void main()                  \n" +
            "{                            \n" +
            "   v_color = vec4(1, 1, 1, 1); \n" +
            "   v_texCoords = a_texCoord0; \n" +
            "   gl_Position =  u_projTrans * a_position;  \n"      +
            "}                            \n" ;

    String fragmentShader = "#ifdef GL_ES\n" +
            "precision mediump float;\n" +
            "#endif\n" +
            "varying vec4 v_color;\n" +
            "varying vec2 v_texCoords;\n" +
            "uniform sampler2D u_texture;\n" +
            "void main()                                  \n" +
            "{                                            \n" +
            "  gl_FragColor = v_color * texture2D(u_texture, v_texCoords);\n" +
            "}";

    ShaderProgram shader;
    Mesh mesh;
    Matrix4 matrix = new Matrix4();


    /**
     * The standard constructor for the simulation screen,
     *
     * @param game we require dat from the game, also probably should only the game create us.
     */
    public ShaderScreen(Game game) {
        System.out.println("-- SHADERS LOADING ... --");
        shader = new ShaderProgram(vertexShader, fragmentShader);

        mesh = new Mesh(true, 4, 6, VertexAttribute.Position(), VertexAttribute.ColorUnpacked(), VertexAttribute.TexCoords(0));
        mesh.setVertices(new float[]
                {-0.5f, -0.5f, 0, 1, 1, 1, 1, 0, 1,
                0.5f, -0.5f, 0, 1, 1, 1, 1, 1, 1,
                0.5f, 0.5f, 0, 1, 1, 1, 1, 1, 0,
                -0.5f, 0.5f, 0, 1, 1, 1, 1, 0, 0});
        mesh.setIndices(new short[]{0, 1, 2, 2, 3, 0});
        System.out.println("-- SHADERS LOADED --");

        root = CompositionRoot.getInstance();
        root.simulationController.startSimulation();

        this.game = game;

        worldCamera = new OrthographicCamera();
        worldCamera.setToOrtho(false, Game.VIEWPORT_WIDTH, Game.VIEWPORT_HEIGHT);
        worldCamera.zoom = 10;   // better default zoom level

        root = CompositionRoot.getInstance();
        root.renderController.setMainCamera(worldCamera);

        hud = root.hudController.view;

        CompositionRoot.getInstance().inputController.scrollListeners.add(zoomer);
    }


    final Game game;
    private CompositionRoot root;

    private OrthographicCamera worldCamera;
    private HudView hud;

    private InputController.ScrollListener zoomer = amount -> {
        worldCamera.zoom = Math.max(1, Math.min(18, worldCamera.zoom + amount * worldCamera.zoom / 16f));  //  1 < zoom < 18
        return true;
    };

    @Override
    public void show() { }

    /**
     * start rendering using the RenderController.
     * @param delta automatic given value.
     */
    @Override
    public void render(float delta) {

        shader.begin();
        shader.setUniformMatrix("u_projTrans", matrix);
        shader.setUniformi("u_texture", 0);
        mesh.render(shader, GL20.GL_TRIANGLES);

        root.renderController.preRender();      // preRender views
        game.batch.setProjectionMatrix(worldCamera.combined);
        game.shapeRenderer.setProjectionMatrix(worldCamera.combined);
        root.renderController.render();         // render views
        hud.render();                           // render UI

        shader.end();

        Gdx.graphics.setTitle("Parking Tycoon (fps: " + Gdx.graphics.getFramesPerSecond() + ") (updates/sec: " + root.simulationController.getUpdatesPerSecond() + ") (real updates/sec: " + root.simulationController.realUpdatesPerSecond + ")");
    }

    /**
     * When the Simulation Window changes in Size then this gets called automatic.
     * @param width automatic given value.
     * @param height automatic given value.
     */
    @Override
    public void resize(int width, int height) {
        worldCamera.setToOrtho(false, 10f * width / height, 10);
        hud.resize(width, height);
    }

    @Override
    public void pause() { }

    @Override
    public void resume() { }

    @Override
    public void hide() { }

    @Override
    public void dispose() {
        hud.dispose();
    }
}
