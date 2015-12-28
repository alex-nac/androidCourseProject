package com.bubblezombie.game.Util;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.SpriteDrawable;

public class Scene2dSprite extends Group {
    private SpriteDrawable _spriteDrawable;

    public Scene2dSprite() {}

    public Scene2dSprite(Texture drawable) {
        _spriteDrawable = new SpriteDrawable(new Sprite(drawable));
    }

    public SpriteDrawable getDrawable() {
        return _spriteDrawable;
    }

    public void setDrawable(Texture drawable) {
        _spriteDrawable = new SpriteDrawable(new Sprite(drawable));
    }

    public void setAlpha(float a) { _spriteDrawable.getSprite().setAlpha(a); }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        if (_spriteDrawable != null) {
            float oldAlpha = _spriteDrawable.getSprite().getColor().a;
            _spriteDrawable.getSprite().setAlpha(_spriteDrawable.getSprite().getColor().a * parentAlpha);
            Sprite spr = _spriteDrawable.getSprite();
            this.setWidth(spr.getWidth());
            this.setHeight(spr.getHeight());
            _spriteDrawable.draw(batch, this.getX(), this.getY(), this.getOriginX(), this.getOriginY(), this.getWidth(), this.getHeight(),
                    this.getScaleX(), this.getScaleY(), this.getRotation());
            _spriteDrawable.getSprite().setAlpha(oldAlpha);

        }
        super.draw(batch, parentAlpha);
    }

    public void rotate(float degrees) {
        this.setRotation(degrees);
    }

    public void rotate(double radians) {
        rotate((float)(radians/(Math.PI)) * 180f);
    }

    public void setAnchorPoint(Vector2 pos) {
        setAnchorPoint(pos.x, pos.y);
    }

    public void setAnchorPoint(float x, float y) {
        this.setOrigin(x, y);


    }

    public Vector2 getAnchorPoint() {
        return new Vector2(_spriteDrawable.getSprite().getOriginX(), _spriteDrawable.getSprite().getOriginY());
    }



}
