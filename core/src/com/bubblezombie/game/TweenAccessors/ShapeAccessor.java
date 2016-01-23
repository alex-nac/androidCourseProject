package com.bubblezombie.game.TweenAccessors;

import com.badlogic.gdx.physics.box2d.Shape;

import aurelienribon.tweenengine.TweenAccessor;

/**
 * Created by Alex on 23/01/16.
 */
public class ShapeAccessor implements TweenAccessor<Shape> {

    // tween types
    public static final int RADIUS = 1;

    @Override
    public int getValues(Shape target, int tweenType, float[] returnValues) {
        switch (tweenType) {
            case RADIUS: returnValues[0] = target.getRadius(); return 1;
            default: throw new AssertionError("No such tween type");
        }
    }

    @Override
    public void setValues(Shape target, int tweenType, float[] newValues) {
        switch (tweenType) {
            case RADIUS: target.setRadius(newValues[0]); break;
            default: throw new AssertionError("No such tween type");
        }
    }
}
