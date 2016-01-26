package com.bubblezombie.game.Util;

import com.badlogic.gdx.math.Vector2;

/**
 * Created by Alex on 26/01/16.
 * Units Converter
 */
public class Units {

    // pixels in meter
    private static final int SCALE = 32;

    public static float M2P(float meters) { return meters * SCALE ; }
    public static float P2M(float pixels) { return pixels / SCALE ; }

    public static Vector2 M2P(Vector2 meters) { return new Vector2(meters.x * SCALE, meters.y * SCALE); }
    public static Vector2 P2M(Vector2 pixels) { return new Vector2(pixels.x / SCALE, pixels.y / SCALE); }
}
