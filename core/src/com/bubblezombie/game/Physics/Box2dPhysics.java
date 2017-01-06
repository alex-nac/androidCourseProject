package com.bubblezombie.game.Physics;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.utils.Disposable;
import com.bubblezombie.game.Util.Units;

import java.util.HashMap;
import java.util.Map;

public class Box2dPhysics implements GamePhysics, Disposable {
    private static int VELOCITY_ITERATIONS = 6;
    private static int POSITION_ITERATIONS = 4;

    private static boolean DO_SLEEP = true;
    private static Vector2 GRAVITY_VEC = new Vector2(0, 0);

    private World world;
    private Map<Integer, Body> entityIdToBodyMap;

    private Box2DDebugRenderer debugRenderer;
    private OrthographicCamera physicsDebugCamera;

    public Box2dPhysics(ContactListener contactListener) {
        Box2D.init();

        this.world = new World(GRAVITY_VEC, DO_SLEEP);

        world.setContactListener(contactListener);

        debugRenderer = new Box2DDebugRenderer();
        physicsDebugCamera = new OrthographicCamera();
        physicsDebugCamera.viewportHeight = Units.P2M(Gdx.graphics.getHeight());
        physicsDebugCamera.viewportWidth = Units.P2M(Gdx.graphics.getWidth());
        physicsDebugCamera.position.set(
                physicsDebugCamera.viewportWidth / 2,
                physicsDebugCamera.viewportHeight / 2, 0f);
        physicsDebugCamera.update();

        entityIdToBodyMap = new HashMap<>();
    }

    @Override
    public void Update(float dt) {
        world.step(dt, VELOCITY_ITERATIONS, POSITION_ITERATIONS);
    }

    @Override
    public void DrawDebugInfo() {
        debugRenderer.render(world, physicsDebugCamera.combined);
    }

    @Override
    public void SetIsSensor(int entityId, boolean isSensor) {
        Body body = entityIdToBodyMap.get(entityId);

        for (Fixture fixture : body.getFixtureList()) {
            fixture.setSensor(true);
        }
    }

    @Override
    public void SetIsBullet(int entityId, boolean isBullet) {
        Body body = entityIdToBodyMap.get(entityId);
        body.setBullet(isBullet);
    }

    @Override
    public void AddCircle(int entityId, int radius, Vector2 position) {
        CircleShape shape = new CircleShape();
        shape.setRadius(Units.P2M(radius));
        AddShape(entityId, shape, position);
    }

    private void AddShape(int entityId, Shape shape, Vector2 position) {
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        bodyDef.position.set(Units.P2M(position));

        Body body = world.createBody(bodyDef);
        body.setUserData(entityId);

        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = shape;
        body.createFixture(fixtureDef);

        entityIdToBodyMap.put(entityId, body);
    }

    @Override
    public void RemoveEntity(int entityId) {
        Body body = entityIdToBodyMap.get(entityId);
        world.destroyBody(body);
    }

    @Override
    public void SetLinearVelocity(int entityId, Vector2 velocity) {
        Body body = entityIdToBodyMap.get(entityId);
        body.setLinearVelocity(Units.P2M(velocity));
    }

    @Override
    public void dispose() {
        world.dispose();
        debugRenderer.dispose();
    }
}
