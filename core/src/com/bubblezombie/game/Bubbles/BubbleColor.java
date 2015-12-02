package com.bubblezombie.game.Bubbles;

/**
 * Created by artem on 02.12.15.
 */
public enum BubbleColor {
    PINK(0), YELLOW(1), RED(2), GREEN(3), BLUE(4), VIOLETT(5), UBER_BLACK(6), NONE(7);

    private final int num;
    BubbleColor(int from) {
        this.num = from;
    }
    public int getIndex() {
        return num;
    }
    public static BubbleColor getIndex(int col) {
        return BubbleColor.values()[col];
    }
}
