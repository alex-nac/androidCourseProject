package com.bubblezombie.game.Physics;

import java.util.HashSet;

/*
 * Class helps us save physical body owner
 * and check CbTypes in ContactListener
 */

public class BodyData {

    public static enum CBType {
        BUBBLE,             // just bubble
        CONNECTED_BUBBLE,   // bubble in mesh
        DELETING_BUBBLE,    // bubble prepared for deleting
        DELETER             // bubble deleter
    }

    public Object owner;

    private HashSet<CBType> _set = new HashSet<CBType>();

    public BodyData(Object owner, CBType... cbtypes) {
        this.owner = owner;

        for (CBType type: cbtypes)
            addCbtype(type);
    }

    public void addCbtype(CBType element) {
        _set.add(element);
    }

    public void removeCbtype(CBType element) {
        if (_set.contains(element)) {
            _set.remove(element);
        }
    }

    public boolean hasCbtype(CBType element) {
        return _set.contains(element);
    }

}
