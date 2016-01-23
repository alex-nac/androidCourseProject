package com.bubblezombie.game.Enums;

/**
 * Created by artem on 02.12.15.
 */
public enum BubbleColor {
    NONE(0), GREEN(1), YELLOW(2), RED(3), BLUE(4), PINK(5), VIOLETT(6), UBER_BLACK(7);

    private final int num;
    BubbleColor(int from) {
        this.num = from;
    }
    public int getIndex() {
        return num;
    }
    public static BubbleColor fromInt(int col) {
        return BubbleColor.values()[col];
    }
}
