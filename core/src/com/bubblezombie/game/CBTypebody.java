package com.bubblezombie.game;

import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;

import java.util.HashSet;

public class CBTypebody extends Body {

    private static HashSet<CBType> set = new HashSet<CBType>();

    /**
     * Constructs a new body with the given address
     *
     * @param world the world
     * @param addr  the address
     */
    protected CBTypebody(World world, long addr) {
        super(world, addr);
    }

//    public CBTypebody(Body body) {
//
//    }

    public void addCbtype(CBType element) {
        set.add(element);
    }

    public void removeCbtype(CBType element) {
        if (set.contains(element)) {
            set.remove(element);
        }
    }

    public boolean hasCbtype(CBType element) {
        return set.contains(element);
    }

}
