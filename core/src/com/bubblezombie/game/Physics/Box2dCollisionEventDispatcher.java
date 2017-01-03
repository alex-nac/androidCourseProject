package com.bubblezombie.game.Physics;

import com.badlogic.gdx.physics.box2d.*;

public class Box2dCollisionEventDispatcher implements ContactListener {
    @Override
    public void beginContact(Contact contact) {
        EntityIdPair idPair = GetContactEntityIds(contact);

        // TODO: enque collision event
    }

    @Override
    public void endContact(Contact contact) {
        // TODO
    }

    @Override
    public void preSolve(Contact contact, Manifold oldManifold) {
        // TODO
    }

    @Override
    public void postSolve(Contact contact, ContactImpulse impulse) {
        // TODO
    }

    private EntityIdPair GetContactEntityIds(Contact contact) {
        Body bodyA = contact.getFixtureA().getBody();
        Body bodyB = contact.getFixtureB().getBody();

        if (!(bodyA.getUserData() instanceof Integer) ||
                !(bodyB.getUserData() instanceof Integer)) {
            throw new PhysicsException("[Box2D] Incorrect body user data type: expected Integer");
        }

        int entityIdA = (Integer) bodyA.getUserData();
        int entityIdB = (Integer) bodyB.getUserData();

        return new EntityIdPair(entityIdA, entityIdB);
    }

    private class EntityIdPair {
        EntityIdPair(int entityAId, int entityBId) {
            this.entityAId = entityAId;
            this.entityBId = entityBId;
        }

        int entityAId;
        int entityBId;
    }
}
