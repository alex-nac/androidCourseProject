package com.bubblezombie.game.Util;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;

// Factory class to quickly create buttons with specific properties

public class ButtonFactory {

    public static TextButton getTextButton(String backgroundPath, String buttonText, BitmapFont font,
                                       boolean isShaded, float height, float width) {
        TextButton.TextButtonStyle tbs = new TextButton.TextButtonStyle();
        tbs.up = new TextureRegionDrawable(new TextureRegion(new Texture(Gdx.files.internal(backgroundPath))));
        tbs.font = font;
        tbs.font.getRegion().getTexture().setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        TextButton textButton = new TextButton(buttonText, tbs);

        if (height != 0.0f) textButton.setHeight(height);
        if (width != 0.0f) textButton.setWidth(width);

        if (isShaded) {
            Image shade = new Image(new Texture(Gdx.files.internal("background/UI_buttons/btn_shade.png")));
            shade.setPosition(7.0f, 7.0f);
            textButton.addActor(shade);
        }

        return textButton;
    }

    public static Button getImageButton(String backgroundPath, String buttonImagePath,
                                        boolean isShaded, float height, float width) {
        ImageButton.ImageButtonStyle ibs = new ImageButton.ImageButtonStyle();
        ibs.imageUp =  new TextureRegionDrawable(new TextureRegion(new Texture(Gdx.files.internal(buttonImagePath))));
        ibs.up = new TextureRegionDrawable(new TextureRegion(new Texture(Gdx.files.internal(backgroundPath))));
        ImageButton imageButton = new ImageButton(ibs);
        imageButton.setHeight(height);
        imageButton.setWidth(width);

        if (isShaded) {
            imageButton.addActor(new Image(new Texture(Gdx.files.internal("background/UI_buttons/btn_shade.png"))));
        }

        return imageButton;
    }
}
