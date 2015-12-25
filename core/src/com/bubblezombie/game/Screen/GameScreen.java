package com.bubblezombie.game.Screen;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.SerializationException;
import com.bubblezombie.game.BubbleMesh;
import com.bubblezombie.game.BubbleZombieGame;
import com.bubblezombie.game.Gun;
import com.bubblezombie.game.Util.BFS;
import com.bubblezombie.game.Util.GameConfig;

import java.io.IOException;


public class GameScreen extends BaseScreen {
    private static final String TAG = "GameScreen";

    // RESOURSE
    private static String RES_BG = "background/level_backgrounds/level_bridge_01.png";
    private static final String RES_SWAT = "game/swat.png";
    private static final String RES_POP_PAUSE = "game/UI_game/pop_pause.png";

    // general consts
    private static final int SWAT_CAR_X_OFFSET = BubbleZombieGame.width / 2 - 75;
    private static final int SWAT_CAR_Y_OFFSET = -13;

    // update loop delta time in ms
    private static final float DT = 1000/30;

    // game states
    private enum GameState { LOSE, WON, UNDEF };

    // whether the player losed the game
    private GameState currWonState = GameState.UNDEF;

    private Boolean _useDebugView;

    // sprite containers
    private Group _game = new Group();
    private Image _pause;
    private Group _UI = new Group();
    float i = 0f;
    // game object
    private World _space = new World(new Vector2(0, 0), true);
    //private var _debug:Debug;
    private BubbleMesh _mesh;
    private Gun _gun;
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
    GameScreen(BubbleZombieGame game, int levelNum) {
        super(game);

        _lvlNum = levelNum;
    }

    @Override
    public void show() {
        super.show();

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

        //_debug = new BitmapDebug(640, 480, 333333 ,true);
        //addChild(_debug.display);

        _useDebugView = cfg.useDebugView;

        //Bubble.MESH_BUBBLE_DIAMETR = cfg.meshBubbleDiametr;
        //SimpleBubble.COLORS_AMOUNT = cfg.colors;

        CreateGameConditionals(cfg);

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

        // mesh
        _mesh = new BubbleMesh(_space, cfg);
        //_mesh.addEventListener(ComboEvent.COMBO, ComboHandler); //score updating
        //_mesh.addEventListener(BubbleMesh.LAST_WAVE, StartWonTimer);
        //_mesh.addEventListener(BubbleMesh.CAR_EXPLOSION, ExplodeCar);
        //_mesh.addEventListener(BubbleMesh.ALL_EMENIES_KILLED, MasterAchHandler);
        //_mesh.addEventListener(BubbleMesh.NEW_ROW, _waveIndicator.NewRow);
        _game.addActor(_mesh.getView());

        BFS.setMesh(_mesh);

        _gun = new Gun(cfg, _space, _lvlNum >= 21, _mesh);
        //_gun.addEventListener(GunEvent.SHOOT, _indicator.SetNextSprite);
        //_gun.addEventListener(GunEvent.SHOOT, aimPointer.onNewBullet);
        //_gun.addEventListener(GunEvent.MOVED, aimPointer.onGunMoved);
        _game.addActor(_gun.getView());
        //stage.addEventListener(MouseEvent.MOUSE_DOWN, Shoot);

        ////////
        ///UI///
        ////////

        stage.addListener(new InputListener() {
            @Override
            public boolean keyDown(InputEvent event, int keycode) {
                if (keycode == Input.Keys.BACK) {
//                    dispose();
                    game.setScreen(new LevelSelectScreen(game));
                }
                return false;
//                return super.keyDown(event, keycode);
            }
        });

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
        var loseSensorCBT:CbType = new CbType();
        loseSensor.cbTypes.add(loseSensorCBT);
        loseSensor.space = _space;
        //we use ongoing because of situation when we shoot with bubble and while connecting to the mesh it is touching sensor
        _space.listeners.add(new InteractionListener(CbEvent.ONGOING, InteractionType.SENSOR, loseSensorCBT, Bubble.ConnectedBubbleCBType, GameLose));

        //bullet exploding cond.
        _space.listeners.add(new InteractionListener(CbEvent.BEGIN, InteractionType.COLLISION, _wallCBT, Bubble.BubbleCBType, BulletTouchedWall, 1));
        */
    }

    @Override
    public void render(float delta) {
        super.render(delta);

        Vector2 loc = _gun.getView().screenToLocalCoordinates(new Vector2(Gdx.input.getX(), Gdx.input.getY()));
        float _angle = (float)Math.atan2(loc.y + 34, loc.x - 13);
        _gun.setGunRotation(_angle * 180 / (float)Math.PI);
    }

    @Override
    public void dispose() {
        super.dispose();
    }
}
