package com.bubblezombie.game.Screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.bubblezombie.game.BubbleZombieGame;

/**
 * Created by Alex on 05/12/15.
 */
public class BaseScreen implements Screen {
    protected BubbleZombieGame game;
    protected Stage stage;
    private Viewport _viewport;

    public BaseScreen(BubbleZombieGame game) { this.game = game; }

    @Override
    public void show() {
        // general objects
        _viewport = new FitViewport(BubbleZombieGame.width, BubbleZombieGame.height);
        stage = new Stage(_viewport);
        Gdx.input.setInputProcessor(stage);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        stage.act(Math.min(delta, 1 / 60.0f));
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {
        _viewport.update(width, height);
    }

    @Override
    public void pause() { }

    @Override
    public void resume() { }

    @Override
    public void hide() { }

    @Override
    public void dispose() {
        game.assetManager.clear();
        stage.dispose();
    }
}
