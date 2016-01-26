package com.bubblezombie.game.GameObjects;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;
import com.bubblezombie.game.BubbleZombieGame;
import com.bubblezombie.game.Physics.BodyData;
import com.bubblezombie.game.Bubbles.Bubble;
import com.bubblezombie.game.Physics.CBTypeContactListener;
import com.bubblezombie.game.Screen.GameScreen;
import com.bubblezombie.game.TweenAccessors.ShapeAccessor;
import com.bubblezombie.game.Util.Units;

import java.util.ArrayList;

import aurelienribon.tweenengine.BaseTween;
import aurelienribon.tweenengine.Tween;
import aurelienribon.tweenengine.TweenCallback;

/**
 * Created by Alex on 23/01/16.
 */
public class BubbleDeleter implements GameObject {
    private static final int WAVE_VEL = 400;     // pixels in second
    private static final int MAX_RADIUS = 550;   // pixels

    private Body _sensorWave;
    private CircleShape _sensorShape;
    private ContactListener _sensorListener;
    private Tween _sensorTween;

    public BubbleDeleter(Vector2 startPos, ArrayList<Bubble> bubblesToDelete, World space) {

        // preparing bubbles for deleting
        for (Bubble bbl : bubblesToDelete) {
            bbl.setIsSensor(true);
            bbl.AddCBType(BodyData.CBType.DELETING_BUBBLE);
        }

        // deleting wave
        BodyDef def = new BodyDef();
        def.type = BodyDef.BodyType.DynamicBody;
        def.position.set(Units.P2M(startPos));
        _sensorWave = space.createBody(def);
        _sensorWave.setUserData(new BodyData(this, BodyData.CBType.DELETER));

        FixtureDef fdef = new FixtureDef();
        fdef.isSensor = true;
        _sensorShape = new CircleShape();
        _sensorShape.setRadius(Units.P2M(Bubble.DIAMETR / 2));
        fdef.shape = _sensorShape;
        _sensorWave.createFixture(fdef);

        // setting interaction
        _sensorListener = new WaveHDR();
        ((GameScreen) BubbleZombieGame.INSTANCE.getScreen()).AddContactListener(_sensorListener);

        // increasing radius
        _sensorTween = Tween.to(_sensorWave.getFixtureList().get(0).getShape(), ShapeAccessor.RADIUS, MAX_RADIUS / WAVE_VEL).target(Units.P2M(MAX_RADIUS));
        _sensorTween.setCallback(new TweenCallback() {
            @Override
            public void onEvent(int type, BaseTween<?> source) {
                // remove this object when tween completed
                ((GameScreen) BubbleZombieGame.INSTANCE.getScreen()).RemoveGameObject(BubbleDeleter.this);
                ((GameScreen) BubbleZombieGame.INSTANCE.getScreen()).RemoveContactListener(_sensorListener);
            }
        });
        ((GameScreen) BubbleZombieGame.INSTANCE.getScreen()).AddTween(_sensorTween);
    }

    @Override
    public void Update() { }

    @Override
    public void Pause() {
        _sensorTween.pause();
    }

    @Override
    public void Resume() {
        _sensorTween.resume();
    }

    @Override
    public void Delete() {
        if (_sensorTween != null) _sensorTween.kill();

        ((GameScreen) BubbleZombieGame.INSTANCE.getScreen()).RemoveContactListener(_sensorListener);
        _sensorWave.getWorld().destroyBody(_sensorWave);
    }

    private class WaveHDR extends CBTypeContactListener {
        @Override
        public void beginContact(Contact contact) {
            if (!CheckForCbTypes(BodyData.CBType.DELETER, BodyData.CBType.DELETING_BUBBLE, contact)) return;

            Bubble bubble = (Bubble) getOwnerB();
            ((GameScreen) BubbleZombieGame.INSTANCE.getScreen()).RemoveGameObject(bubble);
        }
    }
}
