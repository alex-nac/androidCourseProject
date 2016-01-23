package com.bubblezombie.game.Bubbles;

import com.bubblezombie.game.Util.Generator;

/**
 * Created by artem on 02.12.15.
 */
public class SimpleBubble extends Bubble {
    //amount of SimpleBubble's colors
    public static int COLORS_AMOUNT;

    public com.bubblezombie.game.Enums.BubbleColor _color;

    public void setColor(com.bubblezombie.game.Enums.BubbleColor newColor) {}

    public com.bubblezombie.game.Enums.BubbleColor getBubbleColor() { return _color; }

    public SimpleBubble() {
        this(com.bubblezombie.game.Enums.BubbleColor.NONE);
    }

    public SimpleBubble(com.bubblezombie.game.Enums.BubbleColor color) {
        super(com.bubblezombie.game.Enums.BubbleType.SIMPLE);
        if (color == com.bubblezombie.game.Enums.BubbleColor.NONE) {
            _color = com.bubblezombie.game.Enums.BubbleColor.values()[Generator.rand(COLORS_AMOUNT) + 1];
        } else {
            _color = color;
        }
    }
}
