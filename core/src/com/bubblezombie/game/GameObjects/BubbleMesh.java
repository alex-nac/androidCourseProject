package com.bubblezombie.game.GameObjects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.bubblezombie.game.*;
import com.bubblezombie.game.Bubbles.Bubble;
import com.bubblezombie.game.Enums.*;
import com.bubblezombie.game.Bubbles.SimpleBubble;
import com.bubblezombie.game.Bubbles.Sprayer;
import com.bubblezombie.game.Bubbles.Zombie;
import com.bubblezombie.game.EventSystem.GameEvent;
import com.bubblezombie.game.EventSystem.IncorrentGameEventDataException;

import com.bubblezombie.game.GameLogic.BubbleZombieGameLogic;
import com.bubblezombie.game.Physics.CBTypeContactListener;
import com.bubblezombie.game.Physics.BodyData;
import com.bubblezombie.game.Screen.GameScreen;
import com.bubblezombie.game.Util.GameConfig;
import com.bubblezombie.game.Util.Managers.PopupManager;
import com.bubblezombie.game.Util.Units;

import java.util.ArrayList;

/**
 * Created by artem on 02.12.15.
 */
public class BubbleMesh extends Actor implements GameObject {

    private static final String LAST_WAVE = "LAST_WAVE";
    private static final String CAR_EXPLOSION = "CAR_EXPLOSION";
    private static final String NEW_ROW = "NEW_ROW";
    private static final String ALL_EMENIES_KILLED = "ALL_EMENIES_KILLED";

    private static final int STOP_ZOMBIE_ANIMATION_FPS = 25;

    public static final int EMPTY_SPACE = 1;  //horizontal space between the bubbles
    private static final int MESH_Y = 475;
    public static final float MESH_MOVING_TIME = 1.5f;

    /////////////
    //VARIABLES//
    /////////////

    private ArrayList<ArrayList<Bubble>> _mesh = new ArrayList<ArrayList<Bubble>>();
    private ArrayList<Bubble> _bubblesToConnect = new ArrayList<Bubble>(5);
    private ArrayList<Integer> _colors;
    private MeshPattern _meshPattern;
    private int _rowsNum;
    private int _wavesNum = 0;
    private int _enemiesNum = 0;  //sprayers + zombies
    private ArrayList<Boolean> _offset = new ArrayList<Boolean>(); //is row offseted or not
    private World _space;
    private Body _meshOriginBody;
    //private var _waveTimer:Timer;
    //private var _pauseMeshTimer:Timer;                // timer that preventing mesh from adding next row when it is frozen
    private Group _view = new Group();
    private Group _bubbleLayer = new Group();           // layer where all bubbles exist
    private Group _bubbleEffectsLayer = new Group();    // layer where bubble-specified effects exist (the axe)
    private Group _generalEffectsLayer = new Group();   // layer where general effects exist
    private Group _textEffectsLayer = new Group();
    private PopupManager _popupManager;
    private Boolean _wasMeshStopped = false;

    private Boolean _isMeshMoving = false;
    //private var _movingTween:GTween;   				//while moving mesh we need some timer
    //private var _generalEffectsTween:GTween;
    //private var _textEffectsTween:GTween;


    //////////////////
    //GETTES/SETTERS//
    //////////////////

    public int getColumnsNum() {
        return _meshPattern.getColumsNum();
    }

    public int getRowsNum() {
        return _rowsNum;
    }

    public Group getView() {
        return _view;
    }

    public World getSpace() {
        return _space;
    }

    public void setAllowMeshMovement(Boolean value) { /*if (!_wasMeshStopped) _waveTimer.isPaused = !value;*/ }

    public void setEnemiesNum(int value) {
        _enemiesNum = value;
        if (_enemiesNum == 0) {
            try {
                GameEvent event = new GameEvent(GameEvent.Type.ALL_ENEMIES_KILLED);
                event.setTarget(this);
                notify(event, false);
            } catch (IncorrentGameEventDataException e) {
                Gdx.app.log("Mesh", e.getMessage());
            }
        }
    }


    /////////////
    //FUNCTIONS//
    /////////////

    // creating
    public BubbleMesh(World space, GameConfig cfg) {
        _view.addActor(_bubbleLayer);
        _view.addActor(_generalEffectsLayer);
        _view.addActor(_bubbleEffectsLayer);
        _view.addActor(_textEffectsLayer);

        _space = space;
        _meshPattern = new MeshPattern(cfg);
        _colors = _meshPattern.getRemainingColors();

        BodyDef def = new BodyDef();
        def.type = BodyDef.BodyType.KinematicBody;
        def.position.set(Units.P2M((BubbleZombieGame.width - (Bubble.DIAMETR + EMPTY_SPACE) * _meshPattern.getColumsNum() +
                EMPTY_SPACE - 1.5f * Bubble.DIAMETR) / 2 + cfg.offset), Units.P2M(MESH_Y));
        _meshOriginBody = _space.createBody(def);
        _offset.add(!_meshPattern.isLastRowOffseted());

        // creating mesh
        CreateMesh(_meshPattern.getStartRowsNum());

        BubbleZombieGameLogic.instance.AddContactListener(new BubbleHDR());

        //_waveTimer = new Timer(_meshPattern.waveVel);
        //_waveTimer.addEventListener(Timer.TRIGGED, WaveTimerHandler);

        _popupManager = new PopupManager(cfg, _mesh);

        //StopAnimations(4);
    }

    // Update mesh
    @Override
    public void Update() {
        // firstable connect bubbles that was scheduled to connect
        for (Bubble bubble : _bubblesToConnect) ConnectBubble(bubble);
        _bubblesToConnect.clear();

        ///if (_pauseMeshTimer) _pauseMeshTimer.Update();
        //_waveTimer.Update();
        //_popupManager.Update();
    }

    @Override
    public void Pause() {
        _popupManager.onGameStateChanged(true);
        //if (_textEffectsTween) _textEffectsTween.paused = isPaused;
        //if (_generalEffectsTween) _generalEffectsTween.paused = isPaused;
        //if (_movingTween) _movingTween.paused = isPaused;
    }

    @Override
    public void Resume() {
    }

    @Override
    public void Delete() {
    }

    // determining position in mesh and return row and column
    public Vector2 getMeshPos(Bubble bubble) {
        Vector2 worldPos = bubble.getPosition();
        worldPos = worldPos.sub(Units.M2P(_meshOriginBody.getPosition()));
        worldPos.y *= -1;
        int row = MathUtils.ceil(worldPos.y / Bubble.DIAMETR) - 1;
        if (row < 0) {
            if (!_offset.get(0)) worldPos.x -= Bubble.DIAMETR / 2;
        } else {
            if (_offset.get(row)) worldPos.x -= Bubble.DIAMETR / 2;
        }
        int col = MathUtils.ceil(worldPos.x / (Bubble.DIAMETR + EMPTY_SPACE)) - 1;
        return new Vector2(row, col);
    }

    // getting all around bubbles
    public ArrayList<Bubble> getBubblesAround(Bubble bubble, boolean withNulls /*= false*/) {
        // if we meet place where there is no bubble and withNulls == true, we pushBack(null);

        ArrayList<Bubble> vec = new ArrayList<Bubble>();

        int i = (int) bubble.getMeshPosition().x;
        int j = (int) bubble.getMeshPosition().y;


        if (at(i, j - 1) != null) vec.add(_mesh.get(i).get(j - 1));
        else vec.add(null);
        if (at(i - 1, j - 1 + (_offset.get(i) ? 1 : 0)) != null)
            vec.add(_mesh.get(i - 1).get(j - 1 + (_offset.get(i) ? 1 : 0)));
        else vec.add(null);
        if (at(i - 1, j + (_offset.get(i) ? 1 : 0)) != null)
            vec.add(_mesh.get(i - 1).get(j + (_offset.get(i) ? 1 : 0)));
        else vec.add(null);
        if (at(i, j + 1) != null) vec.add(_mesh.get(i).get(j + 1));
        else vec.add(null);
        if (at(i + 1, j + (_offset.get(i) ? 1 : 0)) != null)
            vec.add(_mesh.get(i + 1).get(j + (_offset.get(i) ? 1 : 0)));
        else vec.add(null);
        if (at(i + 1, j - 1 + (_offset.get(i) ? 1 : 0)) != null)
            vec.add(_mesh.get(i + 1).get(j - 1 + (_offset.get(i) ? 1 : 0)));
        else vec.add(null);

        // Code HACK - dirty code, previous code in game use non-null return
        // and sprayer need null bubbles, so we add this option
        if (!withNulls)
            for (int k = 0; k < vec.size(); k++)
                if (vec.get(k) == null) {
                    vec.remove(k);
                    k--;
                }

        return vec;
    }

    public ArrayList<Bubble> getBubblesAround(Bubble bubble) {
        return getBubblesAround(bubble, false);
    }

    // checking if there is bubble in (i, j) position
    public Bubble at(int i, int j) {
        if (i < 0 || j < 0 || i > _mesh.size() - 1 || j > _mesh.get(i).size() - 1)
            return null;
        else
            return _mesh.get(i).get(j);
    }

    public Bubble at(float i, float j) {
        return at((int) i, (int) j);
    }

    public void DisconnectBubble(Bubble bubble) {
        if (!bubble.isConnected()) return;

        bubble.setMesh(null);
        Vector2 meshPos = getMeshPos(bubble);
        _mesh.get((int) meshPos.x).set((int) meshPos.y, null);

        if (bubble instanceof Zombie || bubble instanceof Sprayer) setEnemiesNum(_enemiesNum - 1);
        if (bubble instanceof SimpleBubble) MinusColor(((SimpleBubble) bubble).getBubbleColor());
    }

    public void Stop() {
        _wasMeshStopped = true;
        //_waveTimer.isPaused = true;
    }

    public void PlusColor(BubbleColor color) {
        _colors.set(color.getIndex(), _colors.get(color.getIndex()) + 1);
    }

    public void MinusColor(BubbleColor color) {
        _colors.set(color.getIndex(), _colors.get(color.getIndex()) - 1);
    }

    // froze the mesh
    public void Freeze() {
        setAllowMeshMovement(false);

        //if (_pauseMeshTimer) _pauseMeshTimer.removeEventListener(Timer.TRIGGED, Unfreeze);
        //_pauseMeshTimer = new Timer(_meshPattern.frozenTime);
        //_pauseMeshTimer.addEventListener(Timer.TRIGGED, Unfreeze);
    }

    /*
    private Unfreeze(e:Event):void {
        //_pauseMeshTimer.removeEventListener(Timer.TRIGGED, Unfreeze);
        //_pauseMeshTimer = null;
        setAllowMeshMovement(true);
        for (var i:int = 0; i < _rowsNum; i++)
        for each(var bubble:Bubble in _mesh[i]) {
            if (bubble) bubble.isFrozen = false;
        }
    }
    */

    public void AddEffect(Group effect, Boolean isTextEffect) {
        Group container;
        container = isTextEffect ? _textEffectsLayer : _generalEffectsLayer;
        effect.setX(effect.getX() - container.getX());
        effect.setY(effect.getY() - container.getY());
        container.addActor(effect);
    }

    public void AddEffect(Group effect) {
        AddEffect(effect, false);
    }

    // getting the low bound of the mesh
    public float GetDownMeshBound() {
        for (int i = _rowsNum - 1; i >= 0; i--)
            for (int j = 0; j < _mesh.get(i).size(); ++j) {
                Bubble bubble = _mesh.get(i).get(j);
                if (bubble != null) return bubble.getPosition().y + Bubble.DIAMETR / 2;
            }

        //if no bubbles in mesh return null
        return 0;
    }

    public int GetRemainingBubblesByColor(BubbleColor color) {
        return _colors.get(color.getIndex());
    }

    /////////////////////
    //PRIVATE FUNCTIONS//
    /////////////////////

    // adding one more row to the top of the mesh
    private void AddRow() {
        try {
            notify(new GameEvent(GameEvent.Type.NEW_ROW), false);
        } catch (IncorrentGameEventDataException e) {
            Gdx.app.log("Mesh", e.getMessage());
        }

        // firstable setting bubbles positions for our new row
        ArrayList<Bubble> topRow = _meshPattern.getNextRow();
        for (int j = 0; j < topRow.size(); j++) {
            if (_offset.get(0))
                topRow.get(j).setPosition(GetWorldPos(new Vector2(0, j)).sub(new Vector2(Bubble.DIAMETR / 2, 0)));
            else
                topRow.get(j).setPosition(GetWorldPos(new Vector2(0, j)).add(new Vector2(Bubble.DIAMETR / 2, 0)));
            topRow.get(j).setPosition(topRow.get(j).getPosition().sub(new Vector2(0, Bubble.DIAMETR)));
            topRow.get(j).setSpace(_space);
            topRow.get(j).setMesh(this);
            _bubbleLayer.addActor(topRow.get(j).getView());
            _bubbleEffectsLayer.addActor(topRow.get(j).getEffects());
        }

        _offset.add(0, !_offset.get(0));
        _mesh.add(0, topRow);
        _rowsNum++;
        _wavesNum++;
        _meshOriginBody.setTransform(_meshOriginBody.getPosition().x, _meshOriginBody.getPosition().y - Units.P2M(Bubble.DIAMETR), 0);

        // moving mesh
        //MoveMesh();

        // if it is the last wave
        //if (_wavesNum == _meshPattern.rowsNum - _meshPattern.startRowsNum)
        //dispatchEvent(new Event(LAST_WAVE));
    }

    /*
    private function MoveMesh(onComplete:Function = null):void {
        _meshOriginBody.setLinearVelocity(new Vector2(0, Bubble.DIAMETR / MESH_MOVING_TIME));
        _isMeshMoving = true;

        for each(var bblRow:Vector.<Bubble> in _mesh)
        for each (var bbl:Bubble in bblRow)
        if (bbl) bbl.velocity = new Vec2(0, Bubble.DIAMETR / MESH_MOVING_TIME);

        for each (bbl in _deletedBubbles) {
            if (bbl.hasBody) bbl.velocity = new Vec2(0, Bubble.DIAMETR / MESH_MOVING_TIME);
            else _deletedBubbles.splice(_deletedBubbles.indexOf(bbl), 1);
        }

        if (!_generalEffectsTween) _generalEffectsTween = new GTween(_generalEffectsLayer, MESH_MOVING_TIME, {y:_generalEffectsLayer.y + Bubble.DIAMETR});
        else _generalEffectsTween.setValue("y", _generalEffectsTween.getValue("y") + Bubble.DIAMETR);

        if (!_textEffectsTween) _textEffectsTween = new GTween(_textEffectsLayer, MESH_MOVING_TIME, {y:_textEffectsLayer.y + Bubble.DIAMETR});
        else _textEffectsTween.setValue("y", _textEffectsTween.getValue("y") + Bubble.DIAMETR);

        if (!_movingTween) {
            //we save it is correct position because of some strange timing problems, and due to it - we have incorrent mesh offset
            _meshOriginBody.userData.y = _meshOriginBody.position.y + Bubble.DIAMETR;

            _movingTween = new GTween(null, MESH_MOVING_TIME);
            _movingTween.onComplete = function(e:GTween):void {
                if (onComplete != null) onComplete();
                _isMeshMoving = false;
                _meshOriginBody.velocity.setxy(0, 0);
                _meshOriginBody.position.y = _meshOriginBody.userData.y;
                for each(var bblRow:Vector.<Bubble> in _mesh)
                for each (var bbl:Bubble in bblRow)
                if (bbl) {
                    bbl.velocity = new Vec2();
                    bbl.position = GetWorldPos(bbl.meshPosition);
                }

                for each (bbl in _deletedBubbles) {
                    if (bbl.hasBody) bbl.velocity = new Vec2(0, 0);
                    else _deletedBubbles.splice(_deletedBubbles.indexOf(bbl), 1);
                }
            }
        }
        else {
            _movingTween.beginning();
            _movingTween.paused = false;
            _meshOriginBody.userData.y = _meshOriginBody.position.y + Bubble.DIAMETR;
        }
    }
    */

    // collision with mesh handler
    private class BubbleHDR extends CBTypeContactListener {
        @Override
        public void beginContact(Contact contact) {
            if (!CheckForCbTypes(BodyData.CBType.BUBBLE, BodyData.CBType.CONNECTED_BUBBLE, contact))
                return;

            Bubble bubble = (Bubble) getOwnerA();

            if (bubble.wasCallbackCalled()) return;
            bubble.setCallbackCalled(true);

            /**
             *  now we are in world.step() function, hence we can't move bodies,
             *  change its type etc. Thats why we put bubbles to special list and
             *  later, right after the step() we connect it to our mesh
             */
            _bubblesToConnect.add(bubble);
        }
    }

    private void ConnectBubble(Bubble bubble) {
        //connect bubble to the mesh
        Vector2 meshPos = getMeshPos(bubble);
        if (meshPos.x < 0) {
            BubbleZombieGameLogic.instance.RemoveGameObject(bubble);
            return;
        }
        if (meshPos.y < 0) meshPos.y = 0;
        if (meshPos.y >= _meshPattern.getColumsNum()) meshPos.y = _meshPattern.getColumsNum() - 1;


        if (at(meshPos.x, meshPos.y) != null) {
            //throw (new Error("HEEEEEEEY!! Here we have already have bubble!! You're trying to put at " + meshPos + "while " +
            //"coordinates is " + bubble.position.x + " " + bubble.position.y));
            //dispatchEvent(new Event(CAR_EXPLOSION));
            BubbleZombieGameLogic.instance.RemoveGameObject(bubble);
            return;
        }


        //if we need to create new row
        if (meshPos.x > _mesh.size() - 1) {
            _rowsNum++;
            ArrayList<Bubble> arr = new ArrayList<Bubble>(_meshPattern.getColumsNum());
            for (int i = 0; i != _meshPattern.getColumsNum(); i++) arr.add(null);
            _mesh.add(arr);
            _offset.add(!_offset.get(_offset.size() - 1));
        }

        _mesh.get((int) meshPos.x).set((int) meshPos.y, bubble);
        if (_isMeshMoving) bubble.setVelocity(new Vector2(0, Bubble.DIAMETR / MESH_MOVING_TIME));
        else bubble.setVelocity(new Vector2(0, 0));

        _bubbleLayer.addActor(bubble.getView());
        _bubbleEffectsLayer.addActor(bubble.getEffects());

        if (bubble instanceof SimpleBubble) PlusColor(((SimpleBubble) bubble).getBubbleColor());

        Vector2 pos = GetWorldPos(meshPos);
        bubble.setPosition(pos);
        bubble.setMesh(BubbleMesh.this); // getting instance of the outer class
    }

    //determining world position by mesh coord
    private Vector2 GetWorldPos(Vector2 meshPos) {
        Vector2 pos = new Vector2(meshPos.y * (Bubble.DIAMETR + EMPTY_SPACE) + Bubble.DIAMETR / 2 *
                ((_offset.get((int) meshPos.x) ? 1 : 0) + 1), -(meshPos.x + 0.5f) * Bubble.DIAMETR);
        pos = pos.add(Units.M2P(_meshOriginBody.getPosition()));
        return pos;
    }

    /*
    private function WaveTimerHandler(e:Event):void {
        if (_wavesNum < _meshPattern.rowsNum - _meshPattern.startRowsNum) AddRow();
        else MoveMesh();
    }
    */

    // creating mesh at the beginning
    private void CreateMesh(int startRowAmount) {
        for (int i = 0; i < startRowAmount; i++) {

            _rowsNum++;
            _offset.add(0, !_offset.get(0));

            ArrayList<Bubble> topRow = _meshPattern.getNextRow();
            for (int j = 0; j < topRow.size(); j++) {
                topRow.get(j).setSpace(_space);
                topRow.get(j).setPosition(GetWorldPos(new Vector2(0, j)));

                topRow.get(j).setMesh(this);
                _bubbleLayer.addActor(topRow.get(j).getView());
                _bubbleEffectsLayer.addActor(topRow.get(j).getEffects());
                BubbleZombieGameLogic.instance.AddGameObject(topRow.get(j));
            }

            for (ArrayList<Bubble> bblRow : _mesh) {
                for (Bubble bbl : bblRow) {
                    if (bbl != null)
                        bbl.setPosition(bbl.getPosition().sub(new Vector2(0, Bubble.DIAMETR)));
                }
            }

            //for (var effect:DisplayObject in _bubbleEffectsLayer){
            //  effect.y -= Bubble.DIAMETR;
            //}

            _mesh.add(0, topRow);


            //if it is the last wave
            if (_wavesNum == _meshPattern.getRowsNum() - _meshPattern.getStartRowsNum()) {
                try {
                    GameEvent event = new GameEvent(GameEvent.Type.LAST_WAVE);
                    event.setTarget(this);
                    notify(event, false);
                } catch (IncorrentGameEventDataException e) {
                    e.printStackTrace();
                    Gdx.app.log("mesh", e.getMessage());
                }
                //break;
            }
        }

        _enemiesNum = _meshPattern.getColumsNum() * _meshPattern.getRowsNum();
    }
//
//    //if every == 3 every 3rd zombie will be deleted
//    private var _wasAnimationStopped:Boolean = false;
//    private function StopAnimations(every:int):void {
//        if (_wasAnimationStopped) return;
//
//        _wasAnimationStopped = true;
//
//        //firstable get all zombies from the pattern
//        var zombieVec:Vector.<Zombie> = _meshPattern.GetRemainingZombies();
//
//        //then from the mesh
//        for (var i:int = 0; i < _mesh.length; i++)
//        for (var j:int = 0; j < _meshPattern.columsNum; j++)
//        if (_mesh[i][j] is Zombie) zombieVec.push(_mesh[i][j]);
//
//        for (i = 0; i < zombieVec.length; i += every)
//            zombieVec[i].animationActive = false;
//    }
}
