package com.bubblezombie.game.Util;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;

public class ButtonFactory {
    public ButtonFactory () {}

    public com.badlogic.gdx.scenes.scene2d.ui.Button getButtonFromStyle(com.badlogic.gdx.scenes.scene2d.ui.Button.ButtonStyle style,
                                                                         float xPos, float yPos) {
        com.badlogic.gdx.scenes.scene2d.ui.Button button = new com.badlogic.gdx.scenes.scene2d.ui.Button(style);
        button.setPosition(xPos, yPos);
        return button;
    }

    public com.badlogic.gdx.scenes.scene2d.ui.Button getButtonFromStyle(com.badlogic.gdx.scenes.scene2d.ui.Button.ButtonStyle style,
                                                                         float xPos, float yPos, float height, float width) {
        com.badlogic.gdx.scenes.scene2d.ui.Button button = getButtonFromStyle(style, xPos, yPos);
        button.setHeight(height);
        button.setWidth(width);
        return button;
    }


    public com.badlogic.gdx.scenes.scene2d.ui.Button getTextButton(String path, float xPos, float yPos) {
        TextButton.TextButtonStyle tbs = new TextButton.TextButtonStyle();
        tbs.up = new TextureRegionDrawable(new TextureRegion(new Texture(Gdx.files.internal(path))));
        return getButtonFromStyle(tbs, xPos, yPos);
    }

    public com.badlogic.gdx.scenes.scene2d.ui.Button getTextButton(String path, float xPos, float yPos,
                                                                   float height, float width) {
        com.badlogic.gdx.scenes.scene2d.ui.Button textBtn = getTextButton(path, xPos, yPos);
        textBtn.setHeight(height);
        textBtn.setWidth(width);
        return textBtn;
    }

    public com.badlogic.gdx.scenes.scene2d.ui.Button getTextButton(String pathUp, String pathDown,
                                                                   float xPos, float yPos) {
        TextButton.TextButtonStyle tbs = new TextButton.TextButtonStyle();
        tbs.up = new TextureRegionDrawable(new TextureRegion(new Texture(Gdx.files.internal(pathUp))));
        tbs.down = new TextureRegionDrawable(new TextureRegion(new Texture(Gdx.files.internal(pathDown))));
        return getButtonFromStyle(tbs, xPos, yPos);
    }

    public com.badlogic.gdx.scenes.scene2d.ui.Button getTextButton(String pathUp, String pathDown,
                                                                   float xPos, float yPos, float height, float width) {
        TextButton.TextButtonStyle tbs = new TextButton.TextButtonStyle();
        tbs.up = new TextureRegionDrawable(new TextureRegion(new Texture(Gdx.files.internal(pathUp))));
        tbs.down = new TextureRegionDrawable(new TextureRegion(new Texture(Gdx.files.internal(pathDown))));
        return getButtonFromStyle(tbs, xPos, yPos, height, width);
    }


    public com.badlogic.gdx.scenes.scene2d.ui.Button getImageButton(String path, float xPos, float yPos) {
        ImageButton.ImageButtonStyle ibs = new ImageButton.ImageButtonStyle();
        ibs.imageUp = new TextureRegionDrawable(new TextureRegion(new Texture(Gdx.files.internal(path))));
        return getButtonFromStyle(ibs, xPos, yPos);
    }

    public com.badlogic.gdx.scenes.scene2d.ui.Button getImageButton(String path, float xPos, float yPos,
                                                                     float height, float width) {
        com.badlogic.gdx.scenes.scene2d.ui.Button imageButton = getImageButton(path, xPos, yPos);
        imageButton.setHeight(height);
        imageButton.setWidth(width);
        return imageButton;
    }

    public com.badlogic.gdx.scenes.scene2d.ui.Button getImageButton(String pathUp, String pathDown,
                                                                    float xPos, float yPos) {
        ImageButton.ImageButtonStyle ibs = new ImageButton.ImageButtonStyle();
        ibs.imageUp= new TextureRegionDrawable(new TextureRegion(new Texture(Gdx.files.internal(pathUp))));
        ibs.imageDown = new TextureRegionDrawable(new TextureRegion(new Texture(Gdx.files.internal(pathDown))));
        return getButtonFromStyle(ibs, xPos, yPos);
    }

    public com.badlogic.gdx.scenes.scene2d.ui.Button getImageButton(String pathUp, String pathDown,
                                                                    float xPos, float yPos, float height, float width) {
        com.badlogic.gdx.scenes.scene2d.ui.Button imageButton = getImageButton(pathUp, pathDown, xPos, yPos);
        imageButton.setHeight(height);
        imageButton.setWidth(width);
        return imageButton;
    }

    public com.badlogic.gdx.scenes.scene2d.ui.Button getImageButton(String pathUp, String pathDown,
                                                                    String pathDisabled,
                                                                    float xPos, float yPos) {
        ImageButton.ImageButtonStyle ibs = new ImageButton.ImageButtonStyle();
        ibs.imageUp= new TextureRegionDrawable(new TextureRegion(new Texture(Gdx.files.internal(pathUp))));
        ibs.imageDown = new TextureRegionDrawable(new TextureRegion(new Texture(Gdx.files.internal(pathDown))));
        ibs.imageDisabled = new TextureRegionDrawable(new TextureRegion(new Texture(Gdx.files.internal(pathDisabled))));
        return getButtonFromStyle(ibs, xPos, yPos);
    }

    public com.badlogic.gdx.scenes.scene2d.ui.Button getImageButton(String pathUp, String pathDown,
                                                                    String pathDisabled,
                                                                    float xPos, float yPos,
                                                                    float height, float width) {
        com.badlogic.gdx.scenes.scene2d.ui.Button imageButton = getImageButton(pathUp, pathDown, pathDisabled, xPos, yPos);
        imageButton.setHeight(height);
        imageButton.setWidth(width);
        return imageButton;
    }
}
