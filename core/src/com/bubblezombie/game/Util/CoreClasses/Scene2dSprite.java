package com.bubblezombie.game.Util.CoreClasses;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;

public class Scene2dSprite extends Actor {
    protected Sprite _sprite;
    //private static Sprite dot = new Sprite(new Texture(Gdx.files.internal("background/UI_buttons/btn_background.png")));
    //private static final int dotRadius = 5;

    // ctor with anchor point at center
    public Scene2dSprite(Texture texture) {
        this(texture, new Vector2(texture.getWidth() / 2, texture.getHeight() / 2));
    }

    // ctor with custom anchor point
    public Scene2dSprite(Texture texture, Vector2 anchorPoint) {
        _sprite = new Sprite(texture);
        _sprite.setPosition(-anchorPoint.x, -anchorPoint.y);
        _sprite.setOrigin(anchorPoint.x, anchorPoint.y);
        super.setSize(_sprite.getWidth(), _sprite.getHeight());
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        _sprite.setPosition(getX() - _sprite.getOriginX(), getY() - _sprite.getOriginY());
        _sprite.draw(batch);
        super.draw(batch, parentAlpha);

        // DEBUG: we use this to see anchor points
        //dot.setSize(dotRadius * 2, dotRadius * 2);
        //dot.setPosition(getX() - dotRadius, getY() - dotRadius);
        //dot.draw(batch);
    }

    public void setAlpha(float a) {
        Color col = getColor();
        col.a = a;
        this.setColor(col);
    }

    @Override
    public void setRotation(float degrees) {
        super.setRotation(degrees);
        _sprite.setRotation(degrees);
    }

    @Override
    public void setScaleX(float scaleX) {
        super.setScaleX(scaleX);
        _sprite.setScale(scaleX, _sprite.getScaleY());
    }

    @Override
    public void setScaleY(float scaleY) {
        super.setScaleY(scaleY);
        _sprite.setScale(_sprite.getScaleX(), scaleY);
    }

    @Override
    public void setScale(float scaleXY) {
        super.setScale(scaleXY);
        _sprite.setScale(scaleXY);
    }

    @Override
    public void setScale(float scaleX, float scaleY) {
        super.setScale(scaleX, scaleY);
        _sprite.setScale(scaleX, scaleY);
    }

    @Override
    public void setSize(float width, float height) {
        // because of the new size we need to also scale origin point
        float widthFactor = width / _sprite.getWidth();
        float heightFactor = height / _sprite.getHeight();
        _sprite.setOrigin(_sprite.getOriginX() * widthFactor, _sprite.getOriginY() * heightFactor);

        super.setSize(width, height);
        _sprite.setSize(width, height);
    }

    @Override
    public void setHeight(float height) {
        super.setHeight(height);
        setSize(_sprite.getWidth(), height);
    }

    @Override
    public void setWidth(float width) {
        super.setWidth(width);
        setSize(width, _sprite.getHeight());
    }

    //public void setDrawable(Texture drawable) { _sprite = new SpriteDrawable(new Sprite(drawable)); }
    //public void setAnchorPoint(Vector2 pos) { setAnchorPoint(pos.x, pos.y); }
    //public void setAnchorPoint(float x, float y) { this.setOrigin(x, y); }
    //public Vector2 getAnchorPoint() { return new Vector2(_sprite.getOriginX(), _sprite.getOriginY()); }
}
