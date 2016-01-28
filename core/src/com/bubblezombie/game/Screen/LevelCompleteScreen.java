package com.bubblezombie.game.Screen;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.bubblezombie.game.BubbleZombieGame;

/**
 * Created by Alex on 27/01/16.
 */
public class LevelCompleteScreen extends BaseUIScreen {

    private static final String RES_LEVEL_COMPLETE_CONTENT = "background/screens/level_complete_content.png";

    /*
    private int _lvlNum;
    private int _scores;
    private int _zombieKilled;
    private boolean _wasMasterAchBonusGained;
    */

    public LevelCompleteScreen(BubbleZombieGame game) {
        super(game);

        /*
        _lvlNum = lvlNum;
        _scores = scores;
        _zombieKilled = zombieKilled;
        _wasMasterAchBonusGained = wasMasterBonusGained;
        */

        _isMoreGamesBtn = true;
        _isLvlMapBtn = true;
        _isNextLvlBtn = true;
        _isRestartBtn = true;
    }

    @Override
    public void show() {
        super.show();

        game.assetManager.load(RES_LEVEL_COMPLETE_CONTENT, Texture.class);
        game.assetManager.finishLoading();

        Image levelCompleteContent = new Image(game.assetManager.get(RES_LEVEL_COMPLETE_CONTENT, Texture.class));
        levelCompleteContent.setPosition((BubbleZombieGame.width - levelCompleteContent.getWidth()) / 2,
                (BubbleZombieGame.height - levelCompleteContent.getHeight()) / 2);
        actionArea.addActor(levelCompleteContent);

        nextLvlBtn.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                dispose();
                game.setScreen(new GameScreen(game, 1));
            }
        });

        restartBtn.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                dispose();
                game.setScreen(new GameScreen(game, 1));
            }
        });

        lvlMapBtn.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                dispose();
                game.setScreen(new LevelSelectScreen(game));
            }
        });
    }
}
