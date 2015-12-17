package com.bubblezombie.game.Util.Factory;

import com.badlogic.gdx.Gdx;
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

    public static TextButton getTextButton(String backgroundPath, String buttonText, BitmapFont font,
                                       boolean isShaded, float height, float width) {
        TextButton.TextButtonStyle tbs = new TextButton.TextButtonStyle();
        BubbleZombieGame.assetManager.load(backgroundPath, Texture.class);
        BubbleZombieGame.assetManager.finishLoadingAsset(backgroundPath);
        tbs.up = new TextureRegionDrawable(new TextureRegion(BubbleZombieGame.assetManager.get(backgroundPath, Texture.class)));
        tbs.font = font;
        tbs.font.getRegion().getTexture().setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        TextButton textButton = new TextButton(buttonText, tbs);

        if (height != 0.0f) textButton.setHeight(height);
        if (width != 0.0f) textButton.setWidth(width);

        if (isShaded) {
            if (!BubbleZombieGame.assetManager.isLoaded("background/UI_buttons/btn_shade.png")) {
                BubbleZombieGame.assetManager.load("background/UI_buttons/btn_shade.png", Texture.class);
                BubbleZombieGame.assetManager.finishLoadingAsset("background/UI_buttons/btn_shade.png");
            }
            Image shade = new Image(BubbleZombieGame.assetManager.get("background/UI_buttons/btn_shade.png", Texture.class));
            shade.setPosition(7.0f, 7.0f);
            textButton.addActor(shade);
        }

        return textButton;
    }

    public static Button getImageButton(String backgroundPath, String buttonImagePath,
                                        boolean isShaded, float height, float width) {
        ImageButton.ImageButtonStyle ibs = new ImageButton.ImageButtonStyle();
        BubbleZombieGame.assetManager.load(backgroundPath, Texture.class);
        BubbleZombieGame.assetManager.load(buttonImagePath, Texture.class);
        BubbleZombieGame.assetManager.finishLoadingAsset(backgroundPath);
        BubbleZombieGame.assetManager.finishLoadingAsset(buttonImagePath);
        ibs.imageUp =  new TextureRegionDrawable(new TextureRegion(BubbleZombieGame.assetManager.get(backgroundPath, Texture.class)));
        ibs.up = new TextureRegionDrawable(new TextureRegion(BubbleZombieGame.assetManager.get(buttonImagePath, Texture.class)));
        ImageButton imageButton = new ImageButton(ibs);
        imageButton.setHeight(height);
        imageButton.setWidth(width);

        if (isShaded) {
            if (!BubbleZombieGame.assetManager.isLoaded("background/UI_buttons/btn_shade.png")) {
                BubbleZombieGame.assetManager.load("background/UI_buttons/btn_shade.png", Texture.class);
                BubbleZombieGame.assetManager.finishLoadingAsset("background/UI_buttons/btn_shade.png");
            }
            imageButton.addActor(new Image(BubbleZombieGame.assetManager.get("background/UI_buttons/btn_shade.png", Texture.class)));
        }

        return imageButton;
    }
}
