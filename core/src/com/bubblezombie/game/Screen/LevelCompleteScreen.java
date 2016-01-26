package com.bubblezombie.game.Screen;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.bubblezombie.game.BubbleZombieGame;

/**
 * Created by Alex on 27/01/16.
 */
public class LevelCompleteScreen extends BaseUIScreen {
    private int _lvlNum;
    private int _scores;
    private int _zombieKilled;
    private boolean _wasMasterAchBonusGained;

    public LevelCompleteScreen(BubbleZombieGame game, /*isFailed:Boolean,*/ int lvlNum, int scores, int zombieKilled, boolean wasMasterBonusGained) {
        super(game);

        _lvlNum = lvlNum;
        _scores = scores;
        _zombieKilled = zombieKilled;
        _wasMasterAchBonusGained = wasMasterBonusGained;

        _isMoreGamesBtn = true;
        _isLvlMapBtn = true;
        _isNextLvlBtn = true;
        _isRestartBtn = true;
    }

    @Override
    public void show() {
        super.show();

        nextLvlBtn.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                dispose();
                game.setScreen(new GameScreen(game, _lvlNum + 1));
            }
        });
    }
}
