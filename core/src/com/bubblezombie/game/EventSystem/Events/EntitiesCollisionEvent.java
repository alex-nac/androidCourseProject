package com.bubblezombie.game.EventSystem.Events;

import com.bubblezombie.game.EventSystem.Event;
import com.bubblezombie.game.EventSystem.EventType;

public class EntitiesCollisionEvent extends BaseEvent implements Event {

    public enum CollisionState {
        BEGIN_CONTACT,
        END_CONTACT,
        PRE_SOLVE,
        POST_SOLVE
    }

    private int entityAId, entityBId;
    private CollisionState collisionState;

    public int getEntityAId() {
        return entityAId;
    }

    public int getEntityBId() {
        return entityBId;
    }

    public CollisionState getCollisionState() {
        return collisionState;
    }

    public EntitiesCollisionEvent(int entityAId, int entityBId) {
        this(entityAId, entityBId, CollisionState.BEGIN_CONTACT);
    }

    public EntitiesCollisionEvent(int entityAId, int entityBId, CollisionState collisionState) {
        super(EventType.ENTITIES_COLLISION);

        this.entityAId = entityAId;
        this.entityBId = entityBId;
        this.collisionState = collisionState;
    }
}
