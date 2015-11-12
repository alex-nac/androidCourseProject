package com.bubblezombie.game.UI;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.collision.BoundingBox;

import java.awt.Rectangle;

/**
 * Created by artem on 12.11.15.
 */
public class Button {
    Rectangle rect;
    TextureRegion background;
    Vector2 touchPoint;
    boolean isClicked = false;
    public Button(Rectangle rect, TextureRegion background) {
        this.rect = rect;
        this.background = background;
    }
    public void render(SpriteBatch batch) {
        batch.draw(background, 100, 100);
    }

    public void update(float delta) {}
}
