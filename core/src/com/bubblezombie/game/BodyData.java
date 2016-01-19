package com.bubblezombie.game;

import java.util.HashSet;

/*
 * Class helps us save physical body owner
 * and check CbTypes in ContactListener
 */

public class BodyData {
    public Object owner;

    private static HashSet<CBType> set = new HashSet<CBType>();

    public BodyData(Object owner, CBType... cbtypes) {
        this.owner = owner;

        for (CBType type: cbtypes)
            addCbtype(type);
    }

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
