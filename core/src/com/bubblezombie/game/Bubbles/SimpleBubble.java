package com.bubblezombie.game.Bubbles;

/**
 * Created by artem on 02.12.15.
 */
public class SimpleBubble extends Bubble {
    //amount of SimpleBubble's colors
    public static int COLORS_AMOUNT;


    protected int color;

    public void setColor(int newColor) {}

    public int getColor() {
        return color;
    }
    public SimpleBubble() {
        this(0);
    }

    public SimpleBubble(int color) {
        super(BubbleType.SIMPLE);

        if (color == 0) {
            this.color = (int)Math.floor(Math.random() * SimpleBubble.COLORS_AMOUNT) + 1;
        } else {
            this.color = color;
        }
    }
    @Override
    public void delete(boolean withPlane ) {
        super.delete();
    }

}
