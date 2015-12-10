package com.bubblezombie.game.Util;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;

public class ButtonFactory {

    public static Button getTextButton(String backgroundPath, String buttonText, boolean isShaded,
                                       float height, float width) {
        TextButton.TextButtonStyle tbs = new TextButton.TextButtonStyle();
        tbs.up = new TextureRegionDrawable(new TextureRegion(new Texture(Gdx.files.internal(backgroundPath))));
        TextButton textButton = new TextButton(buttonText, tbs);
        if (isShaded) {

        }
        return textButton;
    }

    public static Button getImageButton(String backgroundPath, String buttonImagePath,
                                        boolean isShaded, float height, float width) {
        ImageButton.ImageButtonStyle ibs = new ImageButton.ImageButtonStyle();
        ibs.imageUp =  new TextureRegionDrawable(new TextureRegion(new Texture(Gdx.files.internal(buttonImagePath))));
        ImageButton imageButton = new ImageButton(ibs);
        imageButton.setBackground(new TextureRegionDrawable(new TextureRegion(new Texture(Gdx.files.internal(backgroundPath)))));
        if (isShaded) {

        }
        return imageButton;
    }
}
