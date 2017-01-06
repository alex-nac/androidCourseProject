package com.bubblezombie.game.Screen;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.bubblezombie.game.Physics.Box2dCollisionEventDispatcher;
import com.bubblezombie.game.Physics.Box2dPhysics;
import com.bubblezombie.game.Physics.GamePhysics;
import com.bubblezombie.game.Util.Units;

public class TestScreen implements Screen {

    protected Game game;
    private Stage stage;
    //OrthographicCamera camera;

    //World world = new World(new Vector2(0, 0), true);
    //Box2DDebugRenderer renderer = new Box2DDebugRenderer();

    private GamePhysics physics = new Box2dPhysics(new Box2dCollisionEventDispatcher());

    public TestScreen(Game game) {
        this.game = game;
    }


    @Override
    public void show() {
        Viewport viewport = new FitViewport(640, 480);
        stage = new Stage(viewport);

        physics.AddCircle(1, 50, new Vector2(100, 475));
        physics.SetLinearVelocity(1, new Vector2(0, 10));

        /*
        // body
        BodyDef bdef = new BodyDef();
        bdef.type = BodyDef.BodyType.DynamicBody;
        bdef.position.set(Units.P2M(100), Units.P2M(475));
        Body body = world.createBody(bdef);
        //body.setLinearVelocity(0, Units.P2M(10));

        // fixture
        FixtureDef fdef = new FixtureDef();
        CircleShape shape = new CircleShape();
        shape.setRadius(Units.P2M(50));
        fdef.shape = shape;
        body.createFixture(fdef);

        // camera
        camera = new OrthographicCamera();
        camera.viewportHeight = Units.P2M(Gdx.graphics.getHeight());
        camera.viewportWidth = Units.P2M(Gdx.graphics.getWidth());
        camera.position.set(camera.viewportWidth/2, camera.viewportHeight/2, 0f);
        camera.update();
        */
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0.5f, 0.5f, 0.5f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        physics.Update(delta);

        stage.draw();
        physics.DrawDebugInfo();
        //renderer.render(world, camera.combined);
    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {

    }
}