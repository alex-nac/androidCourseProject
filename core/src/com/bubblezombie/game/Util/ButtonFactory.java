package com.bubblezombie.game.Util;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;

public class ButtonFactory {
    public static Button getButtonFromStyle(Button.ButtonStyle style, float height, float width) {
        Button button = new Button(style);
        button.setHeight(height);
        button.setWidth(width);
        return button;
    }

    public static Button getButtonFromStyle(Button.ButtonStyle style, float xPos, float yPos,
                                     float height, float width) {
        Button button = getButtonFromStyle(style, height, width);
        button.setPosition(xPos, yPos);
        return button;
    }


    public static Button getTextButton(String path, float height, float width) {
        TextButton.TextButtonStyle tbs = new TextButton.TextButtonStyle();
        tbs.up = new TextureRegionDrawable(new TextureRegion(new Texture(Gdx.files.internal(path))));
        return getButtonFromStyle(tbs, height, width);
    }

    public static Button getTextButton(String path, float xPos, float yPos, float height, float width) {
        Button textButton = getTextButton(path, height, width);
        textButton.setPosition(xPos, yPos);
        return textButton;
    }

    public static Button getTextButton(String pathUp, String pathDown, float height, float width) {
        TextButton.TextButtonStyle tbs = new TextButton.TextButtonStyle();
        tbs.up = new TextureRegionDrawable(new TextureRegion(new Texture(Gdx.files.internal(pathUp))));
        tbs.down = new TextureRegionDrawable(new TextureRegion(new Texture(Gdx.files.internal(pathDown))));
        return getButtonFromStyle(tbs, height, width);
    }

    public static Button getTextButton(String pathUp, String pathDown,
                                float xPos, float yPos, float height, float width) {
        TextButton.TextButtonStyle tbs = new TextButton.TextButtonStyle();
        tbs.up = new TextureRegionDrawable(new TextureRegion(new Texture(Gdx.files.internal(pathUp))));
        tbs.down = new TextureRegionDrawable(new TextureRegion(new Texture(Gdx.files.internal(pathDown))));
        return getButtonFromStyle(tbs, xPos, yPos, height, width);
    }

    public static Button getTextButton(String backgroundPath, String buttonText, boolean isShaded,
                                       float height, float width) {
        TextButton.TextButtonStyle tbs = new TextButton.TextButtonStyle();
        TextButton textButton = (TextButton) getTextButton(backgroundPath, height, width);
        textButton.setText(buttonText);
        if (isShaded) {

        }
        return textButton;
    }

    public static Button getImageButton(String path, float height, float width) {
        ImageButton.ImageButtonStyle ibs = new ImageButton.ImageButtonStyle();
        ibs.imageUp = new TextureRegionDrawable(new TextureRegion(new Texture(Gdx.files.internal(path))));
        return getButtonFromStyle(ibs, height, width);
    }

    public static Button getImageButton(String path, float xPos, float yPos, float height, float width) {
        com.badlogic.gdx.scenes.scene2d.ui.Button imageButton = getImageButton(path, height, width);
        imageButton.setPosition(xPos, yPos);
        return imageButton;
    }

    public static Button getImageButton(String pathUp, String pathDown, float height, float width) {
        ImageButton.ImageButtonStyle ibs = new ImageButton.ImageButtonStyle();
        ibs.imageUp= new TextureRegionDrawable(new TextureRegion(new Texture(Gdx.files.internal(pathUp))));
        ibs.imageDown = new TextureRegionDrawable(new TextureRegion(new Texture(Gdx.files.internal(pathDown))));
        return getButtonFromStyle(ibs, height, width);
    }

    public static Button getImageButton(String pathUp, String pathDown,
                                 float xPos, float yPos, float height, float width) {
        Button imageButton = getImageButton(pathUp, pathDown, height, width);
        imageButton.setPosition(xPos, yPos);
        return imageButton;
    }

    public static Button getImageButton(String pathUp, String pathDown,
                                 String pathDisabled, float height, float width) {
        ImageButton.ImageButtonStyle ibs = new ImageButton.ImageButtonStyle();
        ibs.imageUp = new TextureRegionDrawable(new TextureRegion(new Texture(Gdx.files.internal(pathUp))));
        ibs.imageDown = new TextureRegionDrawable(new TextureRegion(new Texture(Gdx.files.internal(pathDown))));
        ibs.imageDisabled = new TextureRegionDrawable(new TextureRegion(new Texture(Gdx.files.internal(pathDisabled))));
        return getButtonFromStyle(ibs, height, width);
    }

    public static Button getImageButton(String pathUp, String pathDown, String pathDisabled,
                                 float xPos, float yPos, float height, float width) {
        Button imageButton = getImageButton(pathUp, pathDown, pathDisabled, height, width);
        imageButton.setPosition(xPos, yPos);
        return imageButton;
    }

    public static Button getImageButton(String backgroundPath, String buttonImagePath,
                                        boolean isShaded, float height, float width) {
        ImageButton imageButton = (ImageButton) getImageButton(buttonImagePath, height, width);
        imageButton.setBackground(new TextureRegionDrawable(new TextureRegion(new Texture(Gdx.files.internal(backgroundPath)))));
        if (isShaded) {

        }
        return imageButton;
    }
}
