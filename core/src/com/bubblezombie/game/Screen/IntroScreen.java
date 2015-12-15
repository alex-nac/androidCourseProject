package com.bubblezombie.game.Screen;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.bubblezombie.game.BubbleZombieGame;

/**
 *  Created by Alex on 05/12/15.
 *  Intro state class, the game intro is shown after the PLAY button is pressed
 */

public class IntroScreen extends BaseUIScreen {
    private static final String TAG = "IntroScreen";

    IntroScreen(Game game) {
        super(game);
        _isMoreGamesBtn = true;
        _isLvlMapBtn = true;
        _isNextLvlBtn = true;
    }

    @Override
    public void show() {
        super.show();

        // TODO: real font, not just image
        Image introContent = new Image(new Texture(Gdx.files.internal("background/screens/intro_content.png")));
        introContent.setPosition((BubbleZombieGame.width - introContent.getWidth()) / 2,
                (BubbleZombieGame.height - introContent.getHeight()) / 2);
        stage.addActor(introContent);

        nextLvlBtn.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Gdx.app.log("next screen button", "starting level select screen...");
                game.setScreen(new LevelSelectScreen(game));
            }
        });

        lvlMapBtn.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Gdx.app.log("level map button", "starting main menu screen...");
                game.setScreen(new MainMenuScreen(game));
            }
        });

        Gdx.app.log(TAG, "showed");
    }

    @Override
    public void dispose() {
        nextLvlBtn.clearListeners();
        lvlMapBtn.clearListeners();
    }
}
