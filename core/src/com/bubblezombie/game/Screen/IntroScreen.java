package com.bubblezombie.game.Screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.bubblezombie.game.BubbleZombieGame;

/**
 *  Created by Alex on 05/12/15.
 *  Intro state class, the game intro is shown after the PLAY button is pressed
 */

public class IntroScreen extends BaseUIScreen {
    private static final String TAG = "IntroScreen";

    private static final String RES_INTRO_CONTENT = "background/screens/intro_content.png";

    IntroScreen(BubbleZombieGame game) {
        super(game);
        _isMoreGamesBtn = true;
        _isLvlMapBtn = true;
        _isNextLvlBtn = true;
    }

    @Override
    public void show() {
        super.show();
        game.assetManager.load(RES_INTRO_CONTENT, Texture.class);

        game.assetManager.finishLoading();

        // TODO: real font, not just image
        Image introContent = new Image(game.assetManager.get(RES_INTRO_CONTENT, Texture.class));
        introContent.setPosition((BubbleZombieGame.width - introContent.getWidth()) / 2,
                (BubbleZombieGame.height - introContent.getHeight()) / 2);
        actionArea.addActor(introContent);

        nextLvlBtn.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {

                Gdx.app.log("next screen button", "starting level select screen...");
                dispose();
                game.setScreen(new LevelSelectScreen(game));
            }
        });

        lvlMapBtn.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Gdx.app.log("level map button", "starting main menu screen...");
                dispose();
                game.setScreen(new MainMenuScreen(game));
            }
        });


        stage.addListener(new InputListener() {
            @Override
            public boolean keyDown(InputEvent event, int keycode) {
                if (keycode == Input.Keys.BACK) {
                    //dispose();
                    game.setScreen(new MainMenuScreen(game));
                }
                return false;
//                return super.keyDown(event, keycode);
            }
        });

        Gdx.app.log(TAG, "showed");
    }

    @Override
    public void dispose() {
        nextLvlBtn.clearListeners();
        lvlMapBtn.clearListeners();

        super.dispose();
    }
}
