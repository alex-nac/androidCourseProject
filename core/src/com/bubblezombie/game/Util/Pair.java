package com.bubblezombie.game.Util;

/**
 * just a simple generic pair
 * @param <First>
 * @param <Second>
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
