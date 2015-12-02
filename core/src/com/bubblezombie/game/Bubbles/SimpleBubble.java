package com.bubblezombie.game.Bubbles;

/**
 * Created by artem on 02.12.15.
 */
public class SimpleBubble extends Bubble {
    //amount of SimpleBubble's colors
    public static int COLORS_AMOUNT;


    public BubbleColor color;

    public void setColor(int newColor) {}

//    public BubbleColor getColor() {
//        return color;
//    }
    public SimpleBubble() {
        this(BubbleColor.NONE);
    }

    public SimpleBubble(BubbleColor color) {
        super(BubbleType.SIMPLE);
        if (color == BubbleColor.NONE) {
            this.color = BubbleColor.values()[(int)Math.floor(Math.random() * SimpleBubble.COLORS_AMOUNT) + 1];
        } else {
            this.color = color;
        }
    }
    @Override
    public void delete(boolean withPlane ) {
        super.delete();
    }

}
