package com.bubblezombie.game.Util.Factory;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.bubblezombie.game.BubbleZombieGame;

// Factory class to quickly create buttons with specific properties

public class ButtonFactory {
    private static BubbleZombieGame _game;
    private static String RES_BTN_SHADE = "background/UI_buttons/btn_shade.png";

    public static void initialize(BubbleZombieGame game) {
        _game = game;
    }

    public static TextButton getTextButton(Texture backgroundTex, String buttonText, BitmapFont font,
                                       boolean isShaded, float height, float width) {
        TextButton.TextButtonStyle tbs = new TextButton.TextButtonStyle();

        tbs.up = new TextureRegionDrawable(new TextureRegion(backgroundTex));
        tbs.font = font;
        tbs.font.getRegion().getTexture().setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        TextButton textButton = new TextButton(buttonText, tbs);

        if (height != 0.0f) textButton.setHeight(height);
        if (width != 0.0f) textButton.setWidth(width);

        if (isShaded) {
            if (!_game.assetManager.isLoaded(RES_BTN_SHADE)) {
                _game.assetManager.load(RES_BTN_SHADE, Texture.class);
                _game.assetManager.finishLoadingAsset(RES_BTN_SHADE);
            }
            Image shade = new Image(_game.assetManager.get(RES_BTN_SHADE, Texture.class));
            shade.setPosition(7.0f, 7.0f);
            textButton.addActor(shade);
        }

        return textButton;
    }

    public static Button getImageButton(Texture backgroundTex, Texture buttonImageTex,
                                        boolean isShaded, float height, float width) {
        ImageButton.ImageButtonStyle ibs = new ImageButton.ImageButtonStyle();

        ibs.imageUp =  new TextureRegionDrawable(new TextureRegion(buttonImageTex));
        ibs.up = new TextureRegionDrawable(new TextureRegion(backgroundTex));
        ImageButton imageButton = new ImageButton(ibs);
        imageButton.setHeight(height);
        imageButton.setWidth(width);

        if (isShaded) {
            if (!_game.assetManager.isLoaded(RES_BTN_SHADE)) {
                _game.assetManager.load(RES_BTN_SHADE, Texture.class);
                _game.assetManager.finishLoadingAsset(RES_BTN_SHADE);
            }
            imageButton.addActor(new Image(_game.assetManager.get(RES_BTN_SHADE, Texture.class)));
        }

        return imageButton;
    }
}
