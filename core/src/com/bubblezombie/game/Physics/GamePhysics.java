package com.bubblezombie.game.Physics;

import com.badlogic.gdx.math.Vector2;

public interface GamePhysics {
    void Update(float dt);

    void DrawDebugInfo();

    void SetIsSensor(int entityId, boolean isSensor);
    void SetIsBullet(int entityId, boolean isBullet);

    void AddCircle(int entityId, int radius, Vector2 position);
    void RemoveEntity(int entityId);

    void SetLinearVelocity(int entityId, Vector2 velocity);
}
