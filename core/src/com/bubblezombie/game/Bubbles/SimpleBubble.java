package com.bubblezombie.game.Bubbles;

/**
 * Created by artem on 02.12.15.
 */
public class SimpleBubble extends Bubble {
    //amount of SimpleBubble's colors
    public static int COLORS_AMOUNT;


    public BubbleColor _color;

    public void setColor(BubbleColor newColor) {}

    public BubbleColor getColor() { return _color; }

    public SimpleBubble() {
        this(BubbleColor.NONE);
    }

    public SimpleBubble(BubbleColor color) {
        super(BubbleType.SIMPLE);
        if (color == BubbleColor.NONE) {
            _color = BubbleColor.values()[(int)Math.floor(Math.random() * SimpleBubble.COLORS_AMOUNT) + 1];
        } else {
            _color = color;
        }
    }
    @Override
    public void Delete(boolean withPlane) {
        super.Delete();
    }

}
