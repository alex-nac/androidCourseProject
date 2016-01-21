package com.bubblezombie.game.Screen;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.bubblezombie.game.Bubbles.BubbleColor;
import com.bubblezombie.game.Bubbles.Zombie;
import com.bubblezombie.game.Util.Scene2dSprite;

import javafx.scene.Scene;

public class TestScreen implements Screen {

    protected Game game;
    Stage stage;
    Scene2dSprite testSprite;

    public TestScreen(Game game) {
        this.game = game;
    }


    @Override
    public void show() {
        Viewport viewport = new FitViewport(640, 480);
        stage = new Stage(viewport);
        //testSprite = new Scene2dSprite(new Texture(Gdx.files.internal("game/bubbles/zombie_pink.png")), new Vector2(200, 200));
        //testSprite.setPosition(250, 250);
        //Image testImage = new Image(new Texture(Gdx.files.internal("game/bubbles/bomb_normal_blue.png")));
        //testSprite.addActor(testImage);
        //testImage.setPosition(128, 128);
        //testSprite.setSize(40, 40);
        //testSprite.setHeight(40);
        //testSprite.setWidth(40);
        //testSprite.setRotation(45);
        //testSprite.setSize(50, 50);

        Zombie z = new Zombie(BubbleColor.GREEN);
        stage.addActor(z.getView());

        //Gdx.app.log("ABC", Integer.toString((int) testSprite.getWidth()));

        //stage.addActor(testSprite);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0.5f, 0.5f, 0.5f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

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