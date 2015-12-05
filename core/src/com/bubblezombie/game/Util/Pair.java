package com.bubblezombie.game.Util;

/**
 * Created by artem on 01.12.15.
 */
public class Pair<First, Second> {
    public First first;
    public Second second;
    public Pair(First first, Second second) {
        this.first = first;
        this.second = second;
    }
    public void swap() {
        if (first.getClass().equals(second.getClass())) {
            First tmp = first;
            first = (First)second;
            second = (Second)tmp;
        }
    }
}
