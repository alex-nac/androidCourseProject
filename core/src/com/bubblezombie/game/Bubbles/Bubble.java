package com.bubblezombie.game.Bubbles;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.utils.Timer;
import com.bubblezombie.game.GameLogic.BubbleZombieGameLogic;
import com.bubblezombie.game.Physics.BodyData;
import com.bubblezombie.game.GameObjects.BubbleMesh;
import com.bubblezombie.game.BubbleZombieGame;
import com.bubblezombie.game.GameObjects.GameObject;
import com.bubblezombie.game.Screen.GameScreen;
import com.bubblezombie.game.Util.CoreClasses.SpriteActor;
import com.bubblezombie.game.Util.Units;


import static com.badlogic.gdx.scenes.scene2d.actions.Actions.fadeIn;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.fadeOut;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.sequence;

public class Bubble implements GameObject {

    ////////////////////
    //STATIC VARIABLES//
    ////////////////////

    private static final int MAX_TIMES_WALL_TOUCHED = 2;    // after this the bubble exploding
    private static final int LIFE_TIME = 4;                 // how long does this bubble live in seconds

    public static final int DIAMETR = 44;          		    // diametr of the bubble
    public static final float FROZEN_TIME = 0.1f;   	    // time to give frozen to near bubbles
    public static float MESH_BUBBLE_DIAMETR;


    /////////////
    //VARIABLES//
    /////////////

    protected BubbleMesh _mesh;                      	        // we save ref to the _mesh when connect bubble
    protected Group _effects = new Group();                     // here we place all sprites that need to be on top of zombies
    protected float _scale;				    		 	        // bubble's movieclip _scale
    protected SpriteActor _view;                              // bubble's _view
    protected Body _body;                              	        // bubble's body in physics world
    protected com.bubblezombie.game.Enums.BubbleType type;                           	    // bubble's type
    protected Vector2 _meshPosition;
    protected boolean _isDead;
    protected boolean _isConnected = false;
    protected boolean _wasCallbackCalled = false;               // have we called the BubbleHDR function for ths bubble
    protected Timer _lifeTimer = new Timer();
    protected int _timesWallTouched = 0; 				        // how many times we have touched the wall


    private SpriteActor _frozenMC; //= new ice_01_mc();	    //ice movieclip
    private boolean _isFrozen = false;			                //if bubble is frozen or not
    //private var _iceTween:GTween;

    //////////////////
    //GETTES/SETTERS//
    //////////////////

    public boolean isDead() { return _isDead; }
    public boolean isConnected() { return _isConnected; }
    public com.bubblezombie.game.Enums.BubbleType getType() {
        return type;
    }
    public World getSpace() {
        return _body.getWorld();
    }
    public BubbleMesh getMesh() {
        return _mesh;
    }
    public Vector2 getMeshPosition() {
        return _mesh.getMeshPos(this);
    }
    public SpriteActor getView() {
        return _view;
    }
    public Group getEffects(){
        return _effects;
    }
    public float getX() { return Units.M2P(_body.getPosition().x); }
    public float getY() { return Units.M2P(_body.getPosition().y); }
    public Vector2 getPosition() {
        return Units.M2P(_body.getPosition().cpy());
    }
    public float getScale() {
        return _scale;
    }
    public boolean isFrozen() {
        return _isFrozen;
    }
    public boolean hasBody() {
        return _body != null;
    }
    public boolean wasCallbackCalled() {
        return _wasCallbackCalled;
    }

    public void setCallbackCalled(boolean value) {
        _wasCallbackCalled = value;
    }
    public void setScale(float value) { _scale = value; }
    public void setIsSensor(boolean value) { _body.getFixtureList().get(0).setSensor(true); }
    public void setIsBullet(Boolean value) { _body.setBullet(value); }
    public void setVelocity(Vector2 vel) {
        _body.setLinearVelocity(Units.P2M(vel));
    }

    // box2d feature - we create body when we set world
    public void setSpace(World space) {
        // body
        BodyDef bdef = new BodyDef();
        bdef.type = BodyDef.BodyType.DynamicBody;
        bdef.position.set(Units.P2M(_view.getX()), Units.P2M(_view.getY()));
        _body = space.createBody(bdef);

        // fixture
        FixtureDef fdef = new FixtureDef();
        CircleShape shape = new CircleShape();
        shape.setRadius(Units.P2M(DIAMETR / 2));
        fdef.shape = shape;
        _body.createFixture(fdef);

        // it is bullet
        _body.setBullet(true);

        _body.setUserData(new BodyData(this, BodyData.CBType.BUBBLE));
    }

    public void setIsConnected(boolean value) {
        _isConnected = value;
        if (!value && _body != null) ((BodyData)_body.getUserData()).removeCbtype(BodyData.CBType.CONNECTED_BUBBLE);
    }

    public void setX(float value) {
        if (_body != null) _body.setTransform(new Vector2(Units.P2M(value), _body.getPosition().y), 0f);
        _view.setX(value);
        _effects.setX(value);
    }

    public void setY(float value){
        if (_body != null) _body.setTransform(new Vector2(_body.getPosition().x, Units.P2M(value)), 0f);
        _view.setY(value);
        _effects.setY(value);
    }

    public void setPosition(Vector2 pos) {
        if (_body != null) _body.setTransform(Units.P2M(pos), 0f);
        _view.setX(pos.x);
        _view.setY(pos.y);
        _effects.setX(pos.x);
        _effects.setY(pos.y);
    }

    public void setView(SpriteActor sprite) {
        _view = sprite;
        //if (_isFrozen) _effects.addActor(_frozenMC);
    }

    public void setIsFrozen(boolean value) {
        // сейчас нет ледяных бомб
        assert false;

        if (_isFrozen == value || !hasBody()) {
            return;
        }

//        if bubble now in some trans-state then dispose previous tween
//        if (_iceTween) {
//            _iceTween.paused = true;
//            _iceTween.onComplete = null;
//            _iceTween = null;
//        }
        _isFrozen = value;
        if (value) {
            //smoothly put ice
            if (_frozenMC.getColor().a == 1) {
                _frozenMC.getColor().a = 0; //we setting alpha 0 only if we have just put ice (preverting flirking)
            }
            _frozenMC.addAction(fadeIn(0.2f));
            _effects.addActor(_frozenMC);
        } else {
            //smoothly destroy ice
            _frozenMC.addAction(sequence(fadeOut(2f), new Action() {
                public boolean act(float delta) {
                    _effects.removeActor(_frozenMC);
                    return true;
                }
            }));
        }
    }

    public void setMesh(BubbleMesh newMesh) {
        _mesh = newMesh;
        if (newMesh != null) onConnected(newMesh);
        else setIsConnected(false);
    }

    /////////////
    //FUNCTIONS//
    /////////////

    //saving data and setting the graphics
    public Bubble(com.bubblezombie.game.Enums.BubbleType type) {
        this.type = type;

        // ice is a little bit wider than diametr
        //float frozenMCScale = 1.2f * DIAMETR / _frozenMC.getWidth();
        //_frozenMC.setScale(frozenMCScale, frozenMCScale);
    }

    public void StartLifeTimer() {
        _lifeTimer.scheduleTask(new Timer.Task() {
            @Override
            public void run() {
                onLifeEnd(this);
            }
        }, LIFE_TIME);
    }

    @Override
    public void Update() {
        if (_body != null) {
            _view.setPosition(Units.M2P(_body.getPosition().x), Units.M2P(_body.getPosition().y));
            _effects.setPosition(Units.M2P(_body.getPosition().x), Units.M2P(_body.getPosition().y));
        }
    }

    @Override
    public void Pause() {
        _lifeTimer.stop();
    }

    @Override
    public void Resume() {
        _lifeTimer.start();
    }

    @Override
    public void Delete() {
        // destroy body
        if (_body != null) {
            _body.getWorld().destroyBody(_body);
            _body = null;
        }

        if (_effects.getParent() != null)
            _effects.getParent().removeActor(_effects);
        _effects = null;

        _view.getParent().removeActor(_view);
        _view = null;

        /*
        if (_iceTween) {
            _iceTween.paused = true;
            _iceTween.onComplete = null;
            _iceTween = null;
        }
        */

        _lifeTimer.clear();
    }


    private void onLifeEnd(Timer.Task e) {
        BubbleZombieGameLogic.instance.RemoveGameObject(this);
    }

    protected void onConnected(BubbleMesh mesh) {
        _isConnected = true;
        _body.setBullet(false);

        _body.getFixtureList().get(0).getShape().setRadius(Units.P2M(MESH_BUBBLE_DIAMETR / 2));
        ((BodyData) _body.getUserData()).addCbtype(BodyData.CBType.CONNECTED_BUBBLE);

        _body.setType(BodyDef.BodyType.KinematicBody);
        _body.setAngularVelocity(0f);
        _body.setLinearVelocity(new Vector2(0, 0));

        _lifeTimer.clear();
    }

    //return bubble's graphics
    public SpriteActor GetBubbleImage() {
        assert false;
        return null;
    }

    public void AddCBType(BodyData.CBType cbt) {
        ((BodyData)_body.getUserData()).addCbtype(cbt);
    }

    public void WallTouched() {
        if (_timesWallTouched == MAX_TIMES_WALL_TOUCHED && _mesh == null)
            BubbleZombieGameLogic.instance.RemoveGameObject(this);
        _timesWallTouched++;
    }
}
