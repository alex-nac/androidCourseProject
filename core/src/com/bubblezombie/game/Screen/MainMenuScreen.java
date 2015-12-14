package com.bubblezombie.game.Screen;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.bubblezombie.game.BubbleZombieGame;
import com.bubblezombie.game.Util.ButtonFactory;
import com.bubblezombie.game.Util.FontFactory;
import com.bubblezombie.game.Util.FontFactory.FontType;

public class MainMenuScreen extends BaseScreen {
    private static final String TAG = "MainMenuScreen";

    private Image _mainMenuBGD;
    private Button _newGameBtn;

    private BitmapFont _europeExtBold;

    private Music _backgroundMusic;
    
    public MainMenuScreen(Game game) {
        super(game);
    }

    @Override
    public void show() {
        super.show();
        // music
        _backgroundMusic = Gdx.audio.newMusic(Gdx.files.internal("sounds/ost/menu.mp3"));
        _backgroundMusic.setLooping(true);
        //_backgroundMusic.play();

        // TODO: vector fonts
        // fonts
        _europeExtBold = FontFactory.getEuropeExt(FontType.BUTTON, 40);

        // images
        _mainMenuBGD = new Image(new Texture(Gdx.files.internal("background/mainMenuBGD.png")));

        // buttons
        _newGameBtn = ButtonFactory.getTextButton("background/but_main_play.png", "PLAY", _europeExtBold, 1.0f, false, 0.0f, 0.0f);
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
        _newGameBtn.clearListeners();
        _europeExtBold.dispose();
        _backgroundMusic.dispose();
        super.dispose();
    }
}
