package com.bubblezombie.game.Util.CoreClasses;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;

public class SpriteActor extends Actor {
    private TextureRegion textureRegion;
    private float pivotPointX, pivotPointY;
    private float oldWidth, oldHeight;

    //private static Sprite dot = new Sprite(new Texture(Gdx.files.internal("background/UI_buttons/btn_background.png")));
    //private static final int dotRadius = 5;

    public SpriteActor(Texture texture) {
        this(new TextureRegion(texture));
    }

    public SpriteActor(Texture texture, Vector2 pivotPoint) {
        this(new TextureRegion(texture), pivotPoint);
    }

    public SpriteActor(TextureRegion textureRegion, Vector2 pivotPoint) {
        this.textureRegion = textureRegion;
        this.pivotPointX = pivotPoint.x;
        this.pivotPointY = pivotPoint.y;
        setOrigin(pivotPoint.x, pivotPoint.y);

        oldWidth = textureRegion.getRegionWidth();
        oldHeight = textureRegion.getRegionHeight();
        super.setSize(textureRegion.getRegionWidth(), textureRegion.getRegionHeight());
    }

    public SpriteActor(TextureRegion textureRegion) {
        this(textureRegion, new Vector2(textureRegion.getRegionWidth() / 2, textureRegion.getRegionHeight() / 2));
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        // TODO: выяснить почему это работает o_O, учитывая что я не учитываю scale для pivot point
        batch.draw(
                textureRegion,
                getX() - pivotPointX, getY() - pivotPointY,
                getOriginX(), getOriginY(),
                getWidth(), getHeight(),
                getScaleX(), getScaleY(),
                getRotation());


        // DEBUG: we use this to see pivot points
        //dot.setSize(dotRadius * 2, dotRadius * 2);
        //dot.setPosition(getX() - dotRadius, getY() - dotRadius);
        //dot.draw(batch);
    }

    public void setAlpha(float a) {
        Color col = getColor();
        col.a = a;
        setColor(col);
    }

    public void setPivotPoint(float pivotPointX, float pivotPointY) {
        this.pivotPointX = pivotPointX;
        this.pivotPointY = pivotPointY;
    }

    public void setPivotPointX(float pivotPointX) {
        this.pivotPointX = pivotPointX;
    }

    public void setPivotPointY(float pivotPointY) {
        this.pivotPointY = pivotPointY;
    }

    @Override
    protected void positionChanged() {
        float widthFactor = getWidth() / oldWidth;
        float heightFactor = getHeight() / oldHeight;

        pivotPointX *= widthFactor;
        pivotPointY *= heightFactor;
        setOrigin(getOriginX() * widthFactor, getOriginY() * heightFactor);

        oldWidth = getWidth();
        oldHeight = getHeight();
    }

    //public void setDrawable(Texture drawable) { sprite = new SpriteDrawable(new Sprite(drawable)); }
    //public void setAnchorPoint(Vector2 pos) { setAnchorPoint(pos.x, pos.y); }
    //public void setAnchorPoint(float x, float y) { this.setOrigin(x, y); }
    //public Vector2 getAnchorPoint() { return new Vector2(sprite.getOriginX(), sprite.getOriginY()); }
}
