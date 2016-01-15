package com.bubblezombie.game.Util;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.SpriteDrawable;

import static java.lang.Math.max;

public class Scene2dSprite extends Group {
    protected SpriteDrawable _spriteDrawable;
    protected static SpriteDrawable dot = new SpriteDrawable(new Sprite(new Texture(Gdx.files.internal("background/UI_buttons/btn_background.png"))));
    protected static final int dotRadius = 5;

    public Scene2dSprite() {}

    public Scene2dSprite(Texture drawable) {
        _spriteDrawable = new SpriteDrawable(new Sprite(drawable));
        this.setHeight(_spriteDrawable.getSprite().getHeight());
        this.setWidth(_spriteDrawable.getSprite().getWidth());
    }

    public SpriteDrawable getDrawable() {
        return _spriteDrawable;
    }

    public void setDrawable(Texture drawable) {
        _spriteDrawable = new SpriteDrawable(new Sprite(drawable));
    }

    public void setAlpha(float a) {
        Color col = getColor();
        col.a = a;
        this.setColor(col);
        _spriteDrawable.getSprite().setAlpha(a);
    }
    @Override
    public void draw(Batch batch, float parentAlpha) {
        if (_spriteDrawable != null) {
            float oldAlpha = this.getColor().a;
            _spriteDrawable.getSprite().setAlpha(this.getColor().a * parentAlpha);
            _spriteDrawable.draw(batch, this.getX(), this.getY(), this.getOriginX(), this.getOriginY(), this.getWidth(), this.getHeight(),
                    this.getScaleX(), this.getScaleY(), this.getRotation());
            _spriteDrawable.getSprite().setAlpha(oldAlpha);
            // DEBUG
            dot.draw(batch, this.getOriginX() + this.getX() - dotRadius, this.getOriginY() + this.getY() - dotRadius, dotRadius * 2, dotRadius * 2);
            // DEBUG

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
