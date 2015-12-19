package com.bubblezombie.game.Screen;


import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.bubblezombie.game.BubbleZombieGame;
import com.bubblezombie.game.Util.GameConfig;

/**
 * Created by artem on 01.12.15.
 */
public class GameScreen extends BaseScreen {
    private static final String TAG = "GameScreen";

    // update loop delta time in ms
    private static final float DT = 1000/30;

    // game states
    private enum GameState { LOSE, WON, UNDEF };

    // whether the player losed the game
    private GameState currWonState = GameState.UNDEF;

    private Boolean _useDebugView;

    // sprite containers
    private Group _game = new Group();
    //private var _pause:pop_pause_mc = new pop_pause_mc();
    private Group _UI = new Group();

    // game objects
    private int _lvlNum;

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

        stage.addActor(_game);
        stage.addActor(_UI);

//        GameConfig cfg = new GameConfig(BubbleZombieGame.LVLC.GetLevel(_lvlNum));
//        _useDebugView = Boolean(cfg.useDebugView);
    }

    @Override
    public void render(float delta) {
        super.render(delta);

    }

    @Override
    public void dispose() {
        super.dispose();
    }
}
