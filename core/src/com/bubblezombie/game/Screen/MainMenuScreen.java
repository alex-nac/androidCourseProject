package com.bubblezombie.game.Screen;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.bubblezombie.game.BubbleZombieGame;


/**
 * Created by artem on 12.11.15.
 */
public class MainMenuScreen implements Screen {
    private static final String TAG = "MainMenuScreen";

    private Viewport _viewport;
    private Image _mainMenuBGD;
    private TextButton _newGameBtn;

    private BitmapFont _europeExtBold;

    private Stage _stage;

    Music backgroundMusic;
    Game game;
    
    public MainMenuScreen(Game game) {
        this.game = game;
    }

    @Override
    public void show() {
        // music
        backgroundMusic = Gdx.audio.newMusic(Gdx.files.internal("sound/ost/Danger_In_The_Streets_full_mix_demo.mp3"));
        backgroundMusic.setLooping(true);
        backgroundMusic.play();

        // general objects
        _viewport = new FitViewport(BubbleZombieGame.width, BubbleZombieGame.height);
        _stage = new Stage(_viewport);
        Gdx.input.setInputProcessor(_stage);

        // fonts
        //_europeExtBold = new BitmapFont(Gdx.files.internal("fonts/EuropeExt_Bold.ttf"));

        // images
        _mainMenuBGD = new Image(new Texture(Gdx.files.internal("background/mainMenuBGD.png")));
/*
        // buttons
        TextButton.TextButtonStyle tbs = new TextButton.TextButtonStyle();
        //tbs.font = _europeExtBold;
        tbs.up = new TextureRegionDrawable(new TextureRegion(new Texture(Gdx.files.internal("background/but_main_play.png"))));

        _newGameBtn = new TextButton("PLAY", tbs);
        _newGameBtn.setPosition(328.75f, 213.0f);
        _newGameBtn.addListener( new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Gdx.app.log("play button", "starting game...");
                game.setScreen(new GameScreen(MainMenuScreen.this));
            }
        });

        /*
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
        */

        //_stage.addActor(_newGameBtn);
        _stage.addActor(_mainMenuBGD);

        Gdx.app.log(TAG, "showed");
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        _stage.act(Math.min(Gdx.graphics.getDeltaTime(), 1 / 60f));
        _stage.draw();
    }

    @Override
    public void resize(int width, int height) {
        _viewport.update(width, height);
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
        _europeExtBold.dispose();
        _stage.dispose();
        backgroundMusic.dispose();
    }
}
