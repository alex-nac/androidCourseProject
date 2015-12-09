package com.bubblezombie.game.Screen;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.bubblezombie.game.BubbleZombieGame;


/**
 * Created by artem on 12.11.15.
 */
public class MainMenuScreen extends BaseScreen {
    private static final String TAG = "MainMenuScreen";

    private Image _mainMenuBGD;
    private TextButton _newGameBtn;

    private BitmapFont _europeExtBold;

    private Music _backgroundMusic;
    
    public MainMenuScreen(Game game) {
        super(game);
    }

    @Override
    public void show() {
        super.show();

        // music
        _backgroundMusic = Gdx.audio.newMusic(Gdx.files.internal("sounds/menu.mp3"));
        _backgroundMusic.setLooping(true);
        //_backgroundMusic.play();

        // TODO: vector fonts
        // fonts
        _europeExtBold = new BitmapFont(Gdx.files.internal("fonts/sample_font.fnt"));

        // images
        _mainMenuBGD = new Image(new Texture(Gdx.files.internal("background/mainMenuBGD.png")));

        // buttons
        TextButton.TextButtonStyle tbs = new TextButton.TextButtonStyle();
        tbs.font = _europeExtBold;
        tbs.up = new TextureRegionDrawable(new TextureRegion(new Texture(Gdx.files.internal("background/but_main_play.png"))));

        _newGameBtn = new TextButton("PLAY", tbs);
        _newGameBtn.setPosition((BubbleZombieGame.width - _newGameBtn.getWidth()) / 2,
                (BubbleZombieGame.height - _newGameBtn.getHeight()) / 2);
        _newGameBtn.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Gdx.app.log("play button", "starting intro screen...");
                game.setScreen(new IntroScreen(game));
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

        stage.addActor(_mainMenuBGD);
        stage.addActor(_newGameBtn);

        Gdx.app.log(TAG, "showed");
    }

    @Override
    public void dispose() {
        _europeExtBold.dispose();
        _backgroundMusic.dispose();
        super.dispose();
    }
}
