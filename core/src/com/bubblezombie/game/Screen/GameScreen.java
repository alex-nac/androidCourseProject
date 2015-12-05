package com.bubblezombie.game.Screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.Scaling;
import com.badlogic.gdx.utils.viewport.ScalingViewport;
import com.bubblezombie.game.BubbleZombieGame;

/**
 * Created by artem on 01.12.15.
 */
public class GameScreen implements Screen {
    Screen menu;

    Stage stage;
    SpriteBatch batch;
    OrthographicCamera camera;
    Texture text;
    GameScreen(Screen s) {
        this.menu = s;
    }

    @Override
    public void show() {
        batch = new SpriteBatch();
        camera = new OrthographicCamera();
        camera.setToOrtho(false, BubbleZombieGame.width, BubbleZombieGame.height);
        stage = new Stage(new ScalingViewport(Scaling.stretch, BubbleZombieGame.width, BubbleZombieGame.height));
        stage.clear();
        Gdx.input.setInputProcessor(stage);
        text = new Texture(Gdx.files.internal("textures/header.png"));
    }

    @Override
    public void render(float delta) {

        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        stage.act();

        camera.update();

        batch.setProjectionMatrix(camera.combined);

        batch.begin();
        batch.draw(text,70, 200);
        batch.end();

    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {

    }
}
