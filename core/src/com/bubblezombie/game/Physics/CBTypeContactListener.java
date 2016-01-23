package com.bubblezombie.game.Physics;

import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Manifold;

/**
 * Created by Alex on 23/01/16.
 */
public class CBTypeContactListener implements ContactListener {
    private Object _ownerA, _ownerB;

    public Object getOwnerA() {
        if (_ownerA == null)
            throw new AssertionError("Owner A wasn't initialized in CBTypeContactListener");
        return _ownerA;
    }
    public Object getOwnerB() {
        if (_ownerB == null)
            throw new AssertionError("Owner B wasn't initialized in CBTypeContactListener");
        return _ownerB;
    }

    // return true if bodies have specified CbTypes
    protected boolean CheckForCbTypes(BodyData.CBType cb1, BodyData.CBType cb2, Contact contact) {
        BodyData dataA = (BodyData) contact.getFixtureA().getBody().getUserData();
        BodyData dataB = (BodyData) contact.getFixtureB().getBody().getUserData();

        if (dataA.hasCbtype(cb1) && dataB.hasCbtype(cb2)) {
            _ownerA = dataA.owner;
            _ownerB = dataB.owner;
            return true;
        }

        if (dataB.hasCbtype(cb1) && dataA.hasCbtype(cb2)) {
            _ownerA = dataB.owner;
            _ownerB = dataA.owner;
            return true;
        }

        return false;
    }

    @Override
    public void beginContact(Contact contact) {

    }

    @Override
    public void endContact(Contact contact) {

    }

    @Override
    public void preSolve(Contact contact, Manifold oldManifold) {

    }

    @Override
    public void postSolve(Contact contact, ContactImpulse impulse) {

    }
}
