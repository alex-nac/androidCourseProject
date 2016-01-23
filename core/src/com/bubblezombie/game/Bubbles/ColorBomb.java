package com.bubblezombie.game.Bubbles;

/**
 * Created by Alex on 24/12/15.
 */
public class ColorBomb extends Bubble {
    private com.bubblezombie.game.Enums.BubbleColor _color;

    public ColorBomb() {
        super(com.bubblezombie.game.Enums.BubbleType.COLOR_BOMB);
    }

    public com.bubblezombie.game.Enums.BubbleColor getBubbleColor() {
        return _color;
    }
}
