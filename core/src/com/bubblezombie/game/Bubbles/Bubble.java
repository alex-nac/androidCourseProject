package com.bubblezombie.game.Bubbles;

import com.badlogic.gdx.math.Vector2;
import com.bubblezombie.game.BubbleMesh;

/**
 * Created by artem on 01.12.15.
 */
public class Bubble {
    private static final int MAX_TIMES_WALL_TOUCHED = 2;   //after this the bubble exploding
    private static final int LIFE_TIME = 4;                //how long does this bubble live in seconds

    public static final int DIAMETR = 44;          		//diametr of the bubble
    public static final double FROZEN_TIME = 0.1;   	//time to give frozen to near bubbles
    public static Number MESH_BUBBLE_DIAMETR;

//    //CallBack Types for physics engine
//    private static var _connectedBubbleCBType:CbType = new CbType();  //assigned when bubble is connected to the mesh
//    public static function get ConnectedBubbleCBType():CbType { return _connectedBubbleCBType; }
//
//    private static var _bubbleCBType:CbType = new CbType();           //assigned to all bubbles
//    public static function get BubbleCBType():CbType { return _bubbleCBType; }


    /////////////
    //VARIABLES//
    /////////////

    protected BubbleMesh mesh;                  	//we save ref to the mesh when connect bubble
//    protected var _effects:Sprite = new Sprite();    	//here we place all sprites that need to be on top of zombies
    private Number scale;						 	//bubble's movieclip scale
//    private var _view:MovieClip = new MovieClip();   	//bubble's view
//    private var _body:Body = new Body();             	//bubble's body in physics world
    private BubbleType type;                           	//bubble's type
    private Vector2 meshPosition;
    private boolean isConnected = false;
    private boolean wasCallbackCalled = false;     //have we called the BubbleHDR function for ths bubble
//    private var _lifeTimer:Timer;
    private int timesWallTouched = 0; 				//how many times we have touched the wall


//    private var _frozenMC:MovieClip = new ice_01_mc();	//ice movieclip
    private boolean isFrozen = false;			    //if bubble is frozened or not
//    private var _iceTween:GTween;

    //////////////////
    //GETTES/SETTERS//
    //////////////////

    public boolean isConnected() {
        return isConnected;
    }
    public BubbleType getType() {
        return type;
    }
//    public function get space():Space { return _body.space; }
    public Vector2 getPosition() {
        return null;
//        return body.position.copy();
    }
    public BubbleMesh getMesh() {
        return mesh;
    }
    public Vector2 getMeshPosition() {
        return null;
//        return mesh.GetMeshPos(this);
    }
//    public function get view():MovieClip { return _view; }
//    public function get effects():Sprite { return _effects; }
//    public function get x():Number { return _body.position.x; }
//    public function get y():Number { return _body.position.y; }
    public Number getScale() {
        return scale;
    }
    public boolean isFrozen() {
        return isFrozen;
    }
//    public function get hasBody():Boolean { return _body != null; }
    public boolean wasCallbackCalled() {
        return wasCallbackCalled;
    }
    public void setCallbackCalled(boolean value) {
        wasCallbackCalled = value;
    }
    public void setScale(Number value) {
        scale = value;
    }
//    public function set space(space:Space):void { _body.space = space; }
//    public function set isSensor(value:Boolean):void { _body.shapes.at(0).sensorEnabled = value; }
//    public function set isBullet(value:Boolean):void { _body.isBullet = value; }
//    public function set isConnected(value:Boolean):void {
//        _isConnected = value;
//        if (!_isConnected) _body.cbTypes.remove(Bubble.ConnectedBubbleCBType);
//    }
//
//    public function set velocity(vel:Vec2):void {
//        _body.allowMovement = true;
//        _body.velocity = vel;
//    }
//
//    public function set x(value:Number):void {
//        _body.position.x = value;
//        _view.x = value;
//        _effects.x = value;
//    }
//
//    public function set y(value:Number):void {
//        _body.position.y = value;
//        _view.y = value;
//        _effects.y = value;
//    }
//
//    public function set position(pos:Vec2):void {
//        _body.position = pos;
//        _view.x = pos.x;
//        _view.y = pos.y;
//        _effects.x = pos.x;
//        _effects.y = pos.y;
//    }
//
//    public function set view(sprite:MovieClip):void {
//        for (var i:int = 0; i < _view.numChildren; i++)
//        _view.removeChildAt(i);
//        _view.addChild(sprite);
//
//        if (_isFrozen) _effects.addChild(_frozenMC);
//    }
//
//    public function set isFrozen(value:Boolean):void {
//        if (_isFrozen == value || !hasBody) return;
//
//        //if bubble now in some trans-state then dispose previous tween
//        if (_iceTween) {
//            _iceTween.paused = true;
//            _iceTween.onComplete = null;
//            _iceTween = null;
//        }
//        _isFrozen = value;
//        if (value) {
//            //smoothly put ice
//            if (_frozenMC.alpha == 1) _frozenMC.alpha = 0; //we setting alpha 0 only if we have just put ice (preverting flirking)
//            _iceTween = new GTween(_frozenMC, 0.2, {alpha:1});
//            _effects.addChild(_frozenMC);
//        }
//        else {
//            //smoothly destroy ice
//            _iceTween = new GTween(_frozenMC, 2, {alpha:0});
//            _iceTween.onComplete = function (e:GTween):void { _effects.removeChild(_frozenMC); }
//        }
//    }
//
//    public function set mesh(newMesh:BubbleMesh):void {
//        if (newMesh == null) {
//            if (_isConnected) _mesh.Delete(this);
//            isConnected = false;
//        }
//        else
//            onConnected(newMesh);
//    }
    /////////////
    //FUNCTIONS//
    /////////////

    //saving data and setting the graphics
    public Bubble(BubbleType type) {

        this.type = type;

        //creating body
//        _body.shapes.add(new Circle(DIAMETR / 2));
//        _body.userData.ref = this;
//        _body.allowMovement = false;
//        _body.allowRotation = false;
//        _body.cbTypes.add(_bubbleCBType);
//        _body.isBullet = true;

        //ice is a little bit wider than diametr
//        var frozenMCScale:Number = 1.2 * DIAMETR / _frozenMC.width;
//        _frozenMC.scaleX = frozenMCScale;
//        _frozenMC.scaleY = frozenMCScale;

//        _view.addEventListener(Event.ENTER_FRAME, UpdateGraphics);

//        (Main.GSM.GetCurrentState() as GameState).addEventListener(State.PAUSE, onGameStateChanged);
//        (Main.GSM.GetCurrentState() as GameState).addEventListener(State.RESUME, onGameStateChanged);
//        (Main.GSM.GetCurrentState() as GameState).addEventListener(State.REMOVED, function onRemove(e:Event):void {
//            (Main.GSM.GetCurrentState() as GameState).removeEventListener(State.REMOVED, onRemove);
//            (Main.GSM.GetCurrentState() as GameState).removeEventListener(State.PAUSE, onGameStateChanged);
//            (Main.GSM.GetCurrentState() as GameState).removeEventListener(State.RESUME, onGameStateChanged);
//        });
    }
    public void delete() {
        this.delete(false);
    }
    public void delete(boolean withPlane) {

    }

}
