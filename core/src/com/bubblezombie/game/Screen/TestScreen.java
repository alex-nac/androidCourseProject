package com.bubblezombie.game.Screen;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.bubblezombie.game.BubbleZombieGame;
import com.bubblezombie.game.Util.Scene2dSprite;

/**
 * Created by artem on 19.12.15.
 */
public class TestScreen implements Screen {

    Group grid = new Group();
    Scene2dSprite test = new Scene2dSprite();
    Scene2dSprite s = new Scene2dSprite();
    Image b;
    int i = 1;
    protected Game game;
    protected Stage stage;
    private Viewport _viewport;

    public TestScreen(Game game) {
        this.game = game;
    }


    @Override
    public void show() {
        // general objects
        _viewport = new FitViewport(BubbleZombieGame.width, BubbleZombieGame.height);
        stage = new Stage(_viewport);
        s.setDrawable(new Texture(Gdx.files.internal("background/screens/mute.png")));
        test.setDrawable(new Texture(Gdx.files.internal("background/screens/UI_screen_glass.png")));
        test.setPosition(100f, 100f);
        test.setAnchorPoint(200f, 100f);
        s.setPosition(50f ,50f);
        s.setAnchorPoint(40f, 40f);
        test.addActor(s);
        Gdx.input.setInputProcessor(stage);
        stage.addActor(test);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0.5f, 0.5f, 0.5f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        test.rotate((float)i);
        s.rotate((float)-i);
        i = (i + 1) % 360;
        stage.act(Math.min(Gdx.graphics.getDeltaTime(), 1 / 60f));
        stage.draw();

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
