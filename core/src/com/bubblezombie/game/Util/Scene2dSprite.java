package com.bubblezombie.game.Util;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.utils.SpriteDrawable;

public class Scene2dSprite extends Group {
    private SpriteDrawable _spriteDrawable;
    private Vector2 _AnchorPoint;
    private float rotation;

    public Scene2dSprite() {
        _AnchorPoint = new Vector2(0f, 0f);
    }

    public Scene2dSprite(Texture drawable) {
        _AnchorPoint = new Vector2(0f, 0f);
        _spriteDrawable = new SpriteDrawable(new Sprite(drawable));
    }

    public void setDrawable(Texture drawable) {
        _spriteDrawable = new SpriteDrawable(new Sprite(drawable));
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        Matrix4 m1 = batch.getTransformMatrix();
        Matrix4 m2 = m1.setTranslation(_AnchorPoint.x, _AnchorPoint.y, 0f);
        Matrix4 m3 = m2.rotate(new Vector3(0f, 0f, 1f), rotation);
        this.applyTransform(batch, m3);
        super.draw(batch, parentAlpha);
        if (_spriteDrawable == null) {
            return;
        }
        float oldAlpha = _spriteDrawable.getSprite().getColor().a;
        _spriteDrawable.getSprite().setAlpha(_spriteDrawable.getSprite().getColor().a * parentAlpha);
        Sprite spr = _spriteDrawable.getSprite();
        _spriteDrawable.draw(batch, spr.getX(), spr.getY(), spr.getOriginX(), spr.getOriginY(), spr.getWidth(), spr.getHeight(),
                spr.getScaleX(), spr.getScaleY(), spr.getRotation());
        _spriteDrawable.getSprite().setAlpha(oldAlpha);
    }

    public void rotate(float degrees) {
        rotation = degrees;
    }

    public void rotate(double radians) {
        rotate((float)(radians/(Math.PI)) * 180f);
    }

    public void setAnchorPoint(Vector2 pos) {
        setAnchorPoint(pos.x, pos.y);
    }

    public void setAnchorPoint(float x, float y) {
        _AnchorPoint = new Vector2(x, y);
    }

    public Vector2 getAnchorPoint() {
        return new Vector2(_spriteDrawable.getSprite().getOriginX(), _spriteDrawable.getSprite().getOriginY());
    }



}
