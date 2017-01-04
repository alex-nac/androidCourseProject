package com.bubblezombie.game.GameLogic;

import aurelienribon.tweenengine.Tween;
import aurelienribon.tweenengine.TweenManager;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.SerializationException;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.bubblezombie.game.BubbleZombieGame;
import com.bubblezombie.game.Bubbles.Bubble;
import com.bubblezombie.game.Bubbles.SimpleBubble;
import com.bubblezombie.game.EventSystem.GameEvent;
import com.bubblezombie.game.EventSystem.GameEventListener;
import com.bubblezombie.game.GameObjects.BubbleMesh;
import com.bubblezombie.game.GameObjects.GameObject;
import com.bubblezombie.game.GameObjects.Gun;
import com.bubblezombie.game.Screen.LevelCompleteScreen;
import com.bubblezombie.game.Screen.LevelSelectScreen;
import com.bubblezombie.game.TweenAccessors.ShapeAccessor;
import com.bubblezombie.game.Util.BFS;
import com.bubblezombie.game.Util.GameConfig;
import com.bubblezombie.game.Util.Managers.GameObjectsManager;
import com.bubblezombie.game.Util.Units;

import java.io.IOException;
import java.util.ArrayList;

public class BubbleZombieGameLogic implements Disposable, GameLogic {

    // TODO: event system, it is DIRTY hack for now
    public static BubbleZombieGameLogic instance;

    private static final String TAG = "GameScreen";

    // RESOURSE
    private static String RES_BG = "background/level_backgrounds/level_bridge_01.png";
    private static final String RES_SWAT = "game/swat.png";
    private static final String RES_POP_PAUSE = "game/UI_game/pop_pause.png";

    // general consts
    private static final int SWAT_CAR_X_OFFSET = BubbleZombieGame.width / 2 - 75;
    private static final int SWAT_CAR_Y_OFFSET = -13;
    private static final int CONTACT_LISTENERS_START_CAPACITY = 5;


    // Update loop delta time in ms
    private static final float DT = 1000 / 30;

    // whether the player losed the game
    private GameState _currWonState = GameState.UNDEF;

    private Boolean _useDebugView;

    protected BubbleZombieGame game;
    protected Stage stage;
    private Viewport _viewport;

    // sprite containers
    private Group _game = new Group();
    private Image _pause;
    private Group _UI = new Group();

    // game objects
    private World _space = new World(new Vector2(0, 0), true);
    private BubbleMesh _mesh;
    private Gun _gun;

    // physics stuff
    private Box2DDebugRenderer _debug;
    private OrthographicCamera _physCamera;
    private ArrayList<ContactListener> _contactListeners = new ArrayList<ContactListener>(CONTACT_LISTENERS_START_CAPACITY); // need this in order to have more then one listener

    // managers
    private GameObjectsManager _gameObjectsManager = new GameObjectsManager();
    private TweenManager _tweenManager = new TweenManager();

    //private var _wonTimer:Timer
    //private var _score:Score;
    private int _lvlNum;
    //private var _slidingPanel:SlidingPanel;
    //private var _indicator:Indicator;
    //private var _waveIndicator:WaveIndicator;
    //private var _airplaneTimeLeftIndicator:AirplaneTimeLeftIndicator;
    //private var _airplane:Airplane;
    //private var _masterPopup:MasterPopup;

    //buttons
    private ImageButton _planeBtn;

    /**
     * @param levelNum - which level is we now playing?
     */
    public BubbleZombieGameLogic(BubbleZombieGame game, int levelNum) {
        this.game = game;
        instance = this;
        _lvlNum = levelNum;
    }

    @Override
    public void init() {
        _viewport = new FitViewport(BubbleZombieGame.width, BubbleZombieGame.height);
        stage = new Stage(_viewport);
        Gdx.input.setInputProcessor(stage);


        GameConfig cfg = null;
        try {
            cfg = new GameConfig(BubbleZombieGame.LVLC.GetLevel(_lvlNum));
        } catch (IOException e) {
            e.printStackTrace();
            Gdx.app.log(TAG, "Error parsing level data");
        } catch (SerializationException e) {
            e.printStackTrace();
            Gdx.app.log(TAG, "wrong xml format");
        }

        RES_BG = "background/level_backgrounds/" + cfg.BGclassName + ".png";

        game.assetManager.load(RES_BG, Texture.class);
        game.assetManager.load(RES_SWAT, Texture.class);
        game.assetManager.load(RES_POP_PAUSE, Texture.class);

        game.assetManager.finishLoading();

        //Main.SM.SetBackSong(new menu_snd());

        stage.addActor(_game);
        _pause = new Image(game.assetManager.get(RES_POP_PAUSE, Texture.class));
        _pause.setVisible(false);
        _pause.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                resume();
            }
        });
        stage.addActor(_pause);
        stage.addActor(_UI);

        SimpleBubble.COLORS_AMOUNT = cfg.colors;

        // physics debug draw and camera
        _debug = new Box2DDebugRenderer();
        _physCamera = new OrthographicCamera();
        _physCamera.viewportHeight = Units.P2M(Gdx.graphics.getHeight());
        _physCamera.viewportWidth = Units.P2M(Gdx.graphics.getWidth());
        _physCamera.position.set(_physCamera.viewportWidth / 2, _physCamera.viewportHeight / 2, 0f);
        _physCamera.update();

        _useDebugView = cfg.useDebugView;

        Bubble.MESH_BUBBLE_DIAMETR = cfg.meshBubbleDiametr;
        //SimpleBubble.COLORS_AMOUNT = cfg.colors;

        CreateGameConditionals(cfg);

        // this root contact listener just delegates method calls
        _space.setContactListener(new ContactListener() {
            @Override
            public void beginContact(Contact contact) {
                for (ContactListener cl:_contactListeners) cl.beginContact(contact);
            }

            @Override
            public void endContact(Contact contact) {
                for (ContactListener cl:_contactListeners) cl.endContact(contact);
            }

            @Override
            public void preSolve(Contact contact, Manifold oldManifold) {
                for (ContactListener cl:_contactListeners) cl.preSolve(contact, oldManifold);
            }

            @Override
            public void postSolve(Contact contact, ContactImpulse impulse) {
                for (ContactListener cl:_contactListeners) cl.postSolve(contact, impulse);
            }
        });

        // register tween accessors
        Tween.registerAccessor(Shape.class, new ShapeAccessor());

        //////////////////
        ///GAME OBJECTS///
        //////////////////

        // background
        Image back = new Image(game.assetManager.get(RES_BG, Texture.class));
        _game.addActor(back);

        // swat car body
        Image swatCar = new Image(game.assetManager.get(RES_SWAT, Texture.class));
        swatCar.setPosition(SWAT_CAR_X_OFFSET, SWAT_CAR_Y_OFFSET);
        _game.addActor(swatCar);

        // _mesh
        _mesh = new BubbleMesh(_space, cfg);
        _mesh.addListener(new GameEventListener() {
            @Override
            public void allEnemiesKilled(GameEvent event) {
                _currWonState = GameState.WON;
            }
        });
        //_mesh.addEventListener(ComboEvent.COMBO, ComboHandler); //score updating
        //_mesh.addEventListener(BubbleMesh.LAST_WAVE, StartWonTimer);
        //_mesh.addEventListener(BubbleMesh.CAR_EXPLOSION, ExplodeCar);
        //_mesh.addEventListener(BubbleMesh.ALL_EMENIES_KILLED, MasterAchHandler);
        //_mesh.addEventListener(BubbleMesh.NEW_ROW, _waveIndicator.NewRow);
        _game.addActor(_mesh.getView());
        AddGameObject(_mesh);
        BFS.setMesh(_mesh);

        // gun
        _gun = new Gun(cfg, _space, _lvlNum >= 21, _mesh);
        _gun.addListener(new GameEventListener() {
            @Override
            public void shoot(GameEvent event, Bubble nextBubble, final Bubble nowShootedBubble) {
                //_indicator.SetNextSprite
                //aimPointer.onGunMoved

                // gun dispatch SHOOT event when it is initialized, in that case nowShootedBubble == null
                if (nowShootedBubble != null) {
                    // add our bubble to control it
                    _gameObjectsManager.AddGameObject(nowShootedBubble);
                }
            }
        });
        _game.addActor(_gun.getView());
        stage.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                _gun.Shoot();
            }
        });
        AddGameObject(_gun);


        ////////
        ///UI///
        ////////

        stage.addListener(new InputListener() {
            @Override
            public boolean keyDown(InputEvent event, int keycode) {
                if (keycode == Input.Keys.BACK) {
                    //dispose();
                    game.setScreen(new LevelSelectScreen(game));
                }
                return false;
//                return super.keyDown(event, keycode);
            }
        });

    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        stage.act(Math.min(delta, 1 / 60.0f));
        stage.draw();

        //if (_useDebugView) _debug.render(_space, _physCamera.combined);

        Update(delta);
    }

    @Override
    public void pause() {
        _gameObjectsManager.Pause();
    }

    @Override
    public void resume() {
        _gameObjectsManager.Resume();
    }

    @Override
    public void dispose() {
        _gameObjectsManager.Dispose();
        _debug.dispose();
        game.assetManager.clear();
        stage.dispose();
    }

    @Override
    public void AddGameObject(GameObject gameObject) {
        _gameObjectsManager.AddGameObject(gameObject);
    }

    @Override
    public void RemoveGameObject(GameObject gameObject) {
        _gameObjectsManager.RemoveGameObject(gameObject);
    }

    @Override
    public void AddContactListener(ContactListener contactListener) {
        _contactListeners.add(contactListener);
    }

    @Override
    public void RemoveContactListener(ContactListener contactListener) {
        _contactListeners.remove(contactListener);
    }

    @Override
    public void AddTween(Tween tween) {
        _tweenManager.add(tween);
    }

    @Override
    public void Update(float delta) {

        // if we won the game
        if (_currWonState == GameState.WON) {
            dispose();
            game.setScreen(new LevelCompleteScreen(game));
        }

        _space.step(delta, 6, 4);
        _tweenManager.update(delta);
        _gameObjectsManager.Update();

        // set gun's rotation
        Vector2 loc = _gun.getView().screenToLocalCoordinates(new Vector2(Gdx.input.getX(), Gdx.input.getY()));
        float _angle = (float) Math.atan2(loc.y + 34, loc.x - 13);
        _gun.setGunRotation(_angle * 180 / (float) Math.PI);

        //_airplane.Update();
        //_wonTimer.Update();
        //_slidingPanel.Update(_score, _wonTimer.GetRemainingTime());
    }

    private void CreateGameConditionals(GameConfig cfg) {
        /*
        //won conditional
        _wonTimer = new Timer(cfg.wonTime);
        _wonTimer.isPaused = true;
        _wonTimer.addEventListener(Timer.TRIGGED, GameWon);

        //lose conditional
        var loseSensor:Body = new Body(BodyType.STATIC, new Vec2(0, 0));
        var shape:Shape = new Polygon(Polygon.rect(0, 416, stage.stageWidth, stage.stageHeight - 416));
        shape.sensorEnabled = true;
        loseSensor.shapes.add(shape);
        var loseSensorCBT:CBType = new CBType();
        loseSensor.cbTypes.add(loseSensorCBT);
        loseSensor.space = _space;
        //we use ongoing because of situation when we shoot with bubble and while connecting to the _mesh it is touching sensor
        _space.listeners.add(new InteractionListener(CbEvent.ONGOING, InteractionType.SENSOR, loseSensorCBT, Bubble.ConnectedBubbleCBType, GameLose));

        //bullet exploding cond.
        _space.listeners.add(new InteractionListener(CbEvent.BEGIN, InteractionType.COLLISION, _wallCBT, Bubble.BubbleCBType, BulletTouchedWall, 1));
        */
    }
}
