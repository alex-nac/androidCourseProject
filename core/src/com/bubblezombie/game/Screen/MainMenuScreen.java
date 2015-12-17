package com.bubblezombie.game.Screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.bubblezombie.game.BubbleZombieGame;
import com.bubblezombie.game.Util.Factory.ButtonFactory;
import com.bubblezombie.game.Util.Factory.FontFactory;
import com.bubblezombie.game.Util.Factory.FontFactory.FontType;

public class MainMenuScreen extends BaseScreen {
    private static final String TAG = "MainMenuScreen";
    private static final String RES_MAINMENUBGD = "background/screens/mainMenuBGD.png";
    private static final String RES_BUT_MAIN_PLAY = "background/screens/but_main_play.png";
    private static final String RES_MUSIC_MENU = "sounds/ost/menu.mp3";

    private Image _mainMenuBGD;
    private Button _newGameBtn;

    private BitmapFont _europeExtBold;

    private Music _backgroundMusic;
    
    public MainMenuScreen(BubbleZombieGame game) {
        super(game);
    }

    @Override
    public void show() {
        super.show();

        game.assetManager.load(RES_MAINMENUBGD, Texture.class);
        game.assetManager.load(RES_BUT_MAIN_PLAY, Texture.class);
        game.assetManager.load(RES_MUSIC_MENU, Music.class);

        game.assetManager.finishLoading();

        // music
        _backgroundMusic = game.assetManager.get(RES_MUSIC_MENU, Music.class);
        _backgroundMusic.setLooping(true);
        //_backgroundMusic.play();

        // TODO: vector fonts
        // fonts
        _europeExtBold = FontFactory.getEuropeExt(FontType.BUTTON, 40);

        // images
        _mainMenuBGD = new Image(game.assetManager.get(RES_MAINMENUBGD, Texture.class));

        // buttons
        _newGameBtn = ButtonFactory.getTextButton(game.assetManager.get(RES_BUT_MAIN_PLAY, Texture.class), "PLAY", _europeExtBold, false, 0.0f, 0.0f);
        _newGameBtn.setPosition((BubbleZombieGame.width - _newGameBtn.getWidth()) / 2,
                (BubbleZombieGame.height - _newGameBtn.getHeight()) / 2);
        _newGameBtn.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Gdx.app.log("play button", "starting intro screen...");
                dispose();
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

        super.dispose();
    }
}
