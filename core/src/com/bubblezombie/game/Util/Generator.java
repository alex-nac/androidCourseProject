package com.bubblezombie.game.Util;

import java.util.Random;

/**
 * Created by artem on 02.12.15.
 */
public class Generator {
    public static final Random rand = new Random();
    public static int rand(int range) {
        return rand.nextInt(range);
    }
    public static double randDouble(double range) {
        return rand.nextDouble() * range;
    }
}
