package com.bubblezombie.game.Bubbles;

/**
 * Created by Alex on 24/12/15.
 */
public class ColorBomb extends Bubble {
    private BubbleColor _color;

    public ColorBomb() {
        super(BubbleType.COLOR_BOMB);
    }

    public BubbleColor getColor() {
        return _color;
    }
}
