package com.bubblezombie.game.Screen;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.utils.SpriteDrawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.bubblezombie.game.BubbleZombieGame;
import com.bubblezombie.game.Util.Scene2dSprite;

/**
 * Created by artem on 19.12.15.
 */
public class TestScreen implements Screen {

    Scene2dSprite test = new Scene2dSprite();
    Scene2dSprite s = new Scene2dSprite();
    Image b;
    int i = 0;
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
        test.setTransform(false);
        test.rotate(40f);
        s.setAnchorPoint(100f, 100f);
        test.addActor(s);
        Gdx.input.setInputProcessor(stage);
        stage.addActor(test);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0.5f, 0.5f, 0.5f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        test.rotate((float)i);
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
