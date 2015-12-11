package com.bubblezombie.game.Screen;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.bubblezombie.game.BubbleZombieGame;

/**
 *  Created by Alex on 05/12/15.
 *  Intro state class, the game intro is shown after the PLAY button is pressed
 */

public class IntroScreen extends BaseUIScreen {

    IntroScreen(Game game) {
        super(game);
        _isResetBtn = true;
    }

    @Override
    public void show() {
        super.show();

        // TODO: real font, not just image
        Image introContent = new Image(new Texture(Gdx.files.internal("background/intro_content.png")));
        introContent.setPosition((BubbleZombieGame.width - introContent.getWidth()) / 2,
                (BubbleZombieGame.height - introContent.getHeight()) / 2);
        stage.addActor(introContent);


    }

    @Override
    public void dispose() {

    }
}
