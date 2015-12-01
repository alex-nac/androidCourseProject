package com.bubblezombie.game.Screen;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Scaling;
import com.badlogic.gdx.utils.viewport.ScalingViewport;
import com.bubblezombie.game.BubbleZombieGame;


/**
 * Created by artem on 12.11.15.
 */
public class MainMenuScreen implements Screen {


    Button muteButton;
    Button playButton;

    Music backgroundMusic;
    Stage stage;
    TextureRegion background;
    SpriteBatch batch;
    OrthographicCamera camera;
    Game game;
    
    public MainMenuScreen(Game game) {
        this.game = game;
    }

    @Override
    public void show() {
        backgroundMusic = Gdx.audio.newMusic(Gdx.files.internal("sound/ost/Danger_In_The_Streets_full_mix_demo.mp3"));
        backgroundMusic.setLooping(true);
        backgroundMusic.play();

        Gdx.app.log("main_menu", "showed");

        playButton = new Button(new TextureRegionDrawable(new TextureRegion(new Texture(Gdx.files.internal("background/play_button.png")))));
        playButton.setPosition(240.0f, 250.0f);
        playButton.addListener(new ChangeListener() {
            public void changed(ChangeEvent event, Actor actor) {
                Gdx.app.log("play button", "starting game...");
                game.setScreen(new GameScreen(MainMenuScreen.this));
            }
        });
        muteButton = new Button(new TextureRegionDrawable(new TextureRegion(new Texture(Gdx.files.internal("background/mute.png")))));
        muteButton.setPosition(545.0f, 10.0f);
        muteButton.addListener(new ChangeListener() {
            public void changed(ChangeEvent event, Actor actor) {
                if (backgroundMusic.isPlaying()) {
                    backgroundMusic.pause();
                    Gdx.app.log("mute button", "muted");
                } else {
                    backgroundMusic.play();
                    Gdx.app.log("mute button", "unmuted");
                }
            }
        });

        batch = new SpriteBatch();
        camera = new OrthographicCamera();
        camera.setToOrtho(false, BubbleZombieGame.width, BubbleZombieGame.height);

        stage = new Stage(new ScalingViewport(Scaling.stretch, BubbleZombieGame.width, BubbleZombieGame.height));
        stage.clear();
        Gdx.input.setInputProcessor(stage);
        stage.addActor(muteButton);
        stage.addActor(playButton);
        background = new TextureRegion(new Texture(Gdx.files.internal("background/ZombiFon1_2.png")), 0, 0, 640, 480);

    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        stage.act();

        camera.update();

        batch.setProjectionMatrix(camera.combined);

        batch.begin();
        batch.draw(background, 0, 0);

        if (!backgroundMusic.isPlaying()) {
            muteButton.draw(batch, 1);
        }
        playButton.draw(batch, 0.7f);
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
