package com.bubblezombie.game.Physics;

import com.badlogic.gdx.physics.box2d.*;
import com.bubblezombie.game.EventSystem.Event;
import com.bubblezombie.game.EventSystem.EventManager;
import com.bubblezombie.game.EventSystem.Events.EntitiesCollisionEvent;

public class CollisionEventDispatcher implements ContactListener {
    EventManager eventManager;

    public CollisionEventDispatcher(EventManager eventManager) {
        this.eventManager = eventManager;
    }

    @Override
    public void beginContact(Contact contact) {
        SendEvent(contact, EntitiesCollisionEvent.CollisionState.BEGIN_CONTACT);
    }

    @Override
    public void endContact(Contact contact) {
        SendEvent(contact, EntitiesCollisionEvent.CollisionState.END_CONTACT);
    }

    @Override
    public void preSolve(Contact contact, Manifold oldManifold) {
        SendEvent(contact, EntitiesCollisionEvent.CollisionState.PRE_SOLVE);
    }

    @Override
    public void postSolve(Contact contact, ContactImpulse impulse) {
        SendEvent(contact, EntitiesCollisionEvent.CollisionState.POST_SOLVE);
    }

    private void SendEvent(Contact contact, EntitiesCollisionEvent.CollisionState s) {
        Body bodyA = contact.getFixtureA().getBody();
        Body bodyB = contact.getFixtureB().getBody();

        if (!(bodyA.getUserData() instanceof Integer) ||
                !(bodyB.getUserData() instanceof Integer)) {
            throw new PhysicsException("[Box2D] Incorrect body user data type: expected Integer");
        }

        int entityAId = (Integer) bodyA.getUserData();
        int entityBId = (Integer) bodyB.getUserData();

        Event e = new EntitiesCollisionEvent(entityAId, entityBId, s);
        eventManager.queueEvent(e);
    }
}
