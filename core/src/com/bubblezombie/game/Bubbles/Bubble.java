package com.bubblezombie.game.Bubbles;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.Event;
import com.badlogic.gdx.utils.Timer;
import com.bubblezombie.game.BubbleMesh;
import com.bubblezombie.game.Util.Scene2dSprite;


import static com.badlogic.gdx.scenes.scene2d.actions.Actions.fadeIn;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.fadeOut;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.sequence;

public class Bubble {
    private static final int MAX_TIMES_WALL_TOUCHED = 2;   //after this the bubble exploding
    private static final int LIFE_TIME = 4;                //how long does this bubble live in seconds

    public static final int DIAMETR = 44;          		//diametr of the bubble
    public static final float FROZEN_TIME = 0.1f;   	//time to give frozen to near bubbles
    public static float MESH_BUBBLE_DIAMETR;

//    //CallBack Types for physics engine
//    private static var _connectedBubbleCBType:CbType = new CbType();  //assigned when bubble is connected to the _mesh
//    public static function get ConnectedBubbleCBType():CbType { return _connectedBubbleCBType; }
//
//    private static var _bubbleCBType:CbType = new CbType();           //assigned to all bubbles
//    public static function get BubbleCBType():CbType { return _bubbleCBType; }


    /////////////
    //VARIABLES//
    /////////////

    protected BubbleMesh _mesh;                      	//we save ref to the _mesh when connect bubble
    protected Scene2dSprite _effects = new Scene2dSprite();        	//here we place all sprites that need to be on top of zombies
    private double _scale;				    		 	//bubble's movieclip _scale
//    private var _view:MovieClip = new MovieClip();   	//bubble's _view
    private Scene2dSprite _view;
    private Body _body;                              	//bubble's body in physics world
    private BubbleType type;                           	//bubble's type
    private Vector2 _meshPosition;
    private boolean _isDead;
    private boolean _isConnected = false;
    private boolean _wasCallbackCalled = false;     //have we called the BubbleHDR function for ths bubble
    private Timer _lifeTimer = new Timer();
    private int _timesWallTouched = 0; 				//how many times we have touched the wall


    private Scene2dSprite _frozenMC; //= new ice_01_mc();	//ice movieclip
    private boolean _isFrozen = false;			    //if bubble is frozen or not

//    private var _iceTween:GTween;

    //////////////////
    //GETTES/SETTERS//
    //////////////////

    public boolean isDead() { return _isDead; }
    public boolean is_isConnected() {
        return _isConnected;
    }

    public BubbleType getType() {
        return type;
    }

    public World getSpace() {
        return _body.getWorld();
    }

    public Vector2 getPosition() {
        return _body.getPosition().cpy();
    }

    public BubbleMesh getMesh() {
        return _mesh;
    }

    public Vector2 getMeshPosition() {
        return _mesh.getMeshPos(this);
    }

    public Scene2dSprite getView() {
        return _view;
    }

    public Scene2dSprite getEffects(){
        return _effects;
    }

    public float getX(){
        return _body.getPosition().x;
    }

    public float getY() {
        return _body.getPosition().y;
    }

    public Number getScale() {
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

    public void setScale(double value) {
        _scale = value;
    }


    public void setSpace(World space) {
        /// TODO: ????
        BodyDef def = new BodyDef();
        def.type = BodyDef.BodyType.KinematicBody;
        def.position.set(_view.getX(), _view.getY());
        _body = space.createBody(def);
    }
    public void setIsSensor(boolean value) {
        FixtureDef fdef = new FixtureDef();
        fdef.isSensor = value;
        PolygonShape shape = new PolygonShape();
        fdef.shape = new CircleShape();
        fdef.shape.setRadius(MESH_BUBBLE_DIAMETR / 2f);
        fdef.shape = shape;
        _body.createFixture(fdef);
    }

    public void setIsBullet(Boolean value) { _body.setBullet(value); }
    public void setIsConnected(boolean value) {
        _isConnected = value;
//        if (!_isConnected) _body.cbTypes.remove(Bubble.ConnectedBubbleCBType);
    }
//
    public void setVelocity(Vector2 vel) {
        _body.setLinearVelocity(vel);
    }

    public void setX(float value) {
//        _body.position.x = value;
        _view.setX(value);
        _view.setY(value);
    }

    public void setY(float value){
//        _body.position.y = value;
        _view.setY(value);
        _effects.setY(value);
    }

    public void setPosition(Vector2 pos) {
//        body.position = pos;
        _view.setX(pos.x);
        _view.setY(pos.y);
        _effects.setX(pos.x);
        _effects.setY(pos.y);
    }

    public void setView(Scene2dSprite sprite) {
        _view.clearChildren();
        _view.addActor(sprite);
        if (_isFrozen) _effects.addActor(_frozenMC);
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
        if (newMesh == null) {
            if (_isConnected) {
                _mesh.Delete(this);
            }
            _isConnected = false;
        }
        else {
            onConnected(newMesh);
        }
    }

    /////////////
    //FUNCTIONS//
    /////////////

    //saving data and setting the graphics
    public Bubble(BubbleType type, World space) {
        this.type = type;

        //creating body
        BodyDef bdef = new BodyDef();
        bdef.type = BodyDef.BodyType.KinematicBody;
        bdef.position.set(_view.getX(), _view.getY());
        _body = space.createBody(bdef);

        FixtureDef fdef = new FixtureDef();
        fdef.shape = new CircleShape();
        fdef.shape.setRadius(MESH_BUBBLE_DIAMETR / 2f);
        _body.createFixture(fdef);

//        _body.userData.ref = this;
        _body.setLinearVelocity(new Vector2(0, 0));
        _body.setAngularVelocity(0f);
        _body.setBullet(true);
//        _body.cbTypes.add(_bubbleCBType);

        //ice is a little bit wider than diametr
        float frozenMCScale = 1.2f * DIAMETR / _frozenMC.getWidth();
        _frozenMC.setScale(frozenMCScale, frozenMCScale);

//        _view.addEventListener(Event.ENTER_FRAME, UpdateGraphics);

//        (Main.GSM.GetCurrentState() as GameState).addEventListener(State.PAUSE, onGameStateChanged);
//        (Main.GSM.GetCurrentState() as GameState).addEventListener(State.RESUME, onGameStateChanged);
//        (Main.GSM.GetCurrentState() as GameState).addEventListener(State.REMOVED, function onRemove(e:Event):void {
//            (Main.GSM.GetCurrentState() as GameState).removeEventListener(State.REMOVED, onRemove);
//            (Main.GSM.GetCurrentState() as GameState).removeEventListener(State.PAUSE, onGameStateChanged);
//            (Main.GSM.GetCurrentState() as GameState).removeEventListener(State.RESUME, onGameStateChanged);
//        });
    }

    public void StartLifeTimer() {
        _lifeTimer.scheduleTask(new Timer.Task() {
            @Override
            public void run() {
                onLifeEnd(this);
            }
        }, LIFE_TIME);
    }

    public void Delete() {
        this.Delete(false);
    }
    public void Delete(boolean withPlane) {
        _isDead = true;
    }

    public void Update() {}

    private void onLifeEnd(Timer.Task e) {
        Delete();
    }

    public void onConnected(BubbleMesh mesh) {
        _isConnected = true;
        _body.setBullet(false);
        _mesh = mesh;

        _body.getFixtureList().clear();
        FixtureDef fdef = new FixtureDef();
        fdef.shape = new CircleShape();
        fdef.shape.setRadius(MESH_BUBBLE_DIAMETR / 2f);
        _body.createFixture(fdef);

//        _body.cbTypes.add(_connectedBubbleCBType);

        _body.setType(BodyDef.BodyType.KinematicBody);

        // TODO: ???
        //  _body.allowMovement = false;
        _body.setAngularVelocity(0f);
        _body.setLinearVelocity(new Vector2(0, 0));

        if (_lifeTimer != null) {
            _lifeTimer.stop();
//            _lifeTimer.removeEventListener(TimerEvent.TIMER_COMPLETE, onLifeEnd);
        }
    }

//
//    //deleting the bubble from mesh and removing view
//    public function Delete(withPlane:Boolean = false):void {
//        mesh = null;
//        _mesh = null;
//        if (_body) _body.space = null;
//        _body = null;
//
//        if (_effects.parent) _effects.parent.removeChild(_effects);
//        _effects = null;
//
//        _view.parent.removeChild(_view);
//        _view.removeEventListener(Event.ENTER_FRAME, UpdateGraphics);
//        _view = null;
//
//        if (_iceTween) {
//            _iceTween.paused = true;
//            _iceTween.onComplete = null;
//            _iceTween = null;
//        }
//
//        if (_lifeTimer) {
//            _lifeTimer.stop();
//            _lifeTimer.removeEventListener(TimerEvent.TIMER_COMPLETE, onLifeEnd);
//        }
//        if (Main.GSM.GetCurrentState() is GameState) {
//            (Main.GSM.GetCurrentState() as GameState).removeEventListener(State.PAUSE, onGameStateChanged);
//            (Main.GSM.GetCurrentState() as GameState).removeEventListener(State.RESUME, onGameStateChanged);
//        }
//    }
//
    //return bubble's graphics
    public Scene2dSprite GetBubbleImage() {
        return null;
    }

//    public function AddCBT(cbt:CbType):void {
//        _body.cbTypes.add(cbt);
//    }
//
//
//    private function UpdateGraphics(e:Event):void {
//        _view.x = _body.position.x;
//        _view.y = _body.position.y;
//
//        _effects.x = _body.position.x;
//        _effects.y = _body.position.y;
//    }
//
//    //handling game pause/resume
//    public function onGameStateChanged(e:Event):void {
//        if (_lifeTimer) e.type == State.PAUSE ? _lifeTimer.stop() : _lifeTimer.start();
//    }
//
    public void WallTouched() {
        _timesWallTouched++;
        if (_timesWallTouched == MAX_TIMES_WALL_TOUCHED && _mesh == null)
            Delete();
    }



}
