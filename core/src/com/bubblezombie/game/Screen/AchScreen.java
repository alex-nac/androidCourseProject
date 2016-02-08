package com.bubblezombie.game.Screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.bubblezombie.game.Util.Managers.AchievmentsManager;
import com.bubblezombie.game.BubbleZombieGame;
import com.bubblezombie.game.Util.Factory.ButtonFactory;

/**
 * Created by Alex on 17/12/15.
 */
public class    AchScreen extends BaseUIScreen {
    private static final String TAG = "AchScreen";

    private static final int ACH_BUTTONS_X = 465;
    private static final int ACH_BUTTONS_START_Y = 110;
    private static final int ACH_BUTTONS_EMPTY_SPACE = 5;
    private static final float ACH_BUTTONS_SIZE = 45.0f;

    private static final String RES_ACH_CONTENT = "background/screens/ach_content.png";
    private static final String RES_BUT_LEVEL_BORDER = "background/screens/but_level_border.png";
    private static final String RES_BUT_LEVEL_LOCK = "background/screens/but_level_lock.png";
    private static final String RES_BUT_TIP = "background/screens/but_tip.png";


    AchScreen(BubbleZombieGame game) {
        super(game);
        _isMoreGamesBtn = true;
        _isBackBtn = true;

        _isAchievmentsBtn = true;
        _isAchievmentsBtnShaded = true;

        _isResetBtn = true;
        _isResetBtnShaded = true;
    }

    @Override
    public void show() {
        super.show();

        game.assetManager.load(RES_ACH_CONTENT, Texture.class);
        game.assetManager.load(RES_BUT_LEVEL_BORDER, Texture.class);
        game.assetManager.load(RES_BUT_LEVEL_LOCK, Texture.class);
        game.assetManager.load(RES_BUT_TIP, Texture.class);

        game.assetManager.finishLoading();

        backBtn.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Gdx.app.log("back button", "starting level select screen...");
                dispose();
                game.setScreen(new LevelSelectScreen(game));
            }
        });

        Image achContent = new Image(game.assetManager.get(RES_ACH_CONTENT, Texture.class));
        achContent.setPosition((BubbleZombieGame.width - achContent.getWidth()) / 2,
                (BubbleZombieGame.height - achContent.getHeight()) / 2 + 15);
        actionArea.addActor(achContent);


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

        for (int i = 0; i <= 4; i++) {
            Button achButton;

            if (AchievmentsManager.IsAchievmentPassed(i)) {
                achButton = ButtonFactory.getImageButton(game.assetManager.get(RES_BUT_LEVEL_BORDER, Texture.class),
                        game.assetManager.get(RES_BUT_TIP, Texture.class), false, ACH_BUTTONS_SIZE, ACH_BUTTONS_SIZE);
            } else {
                achButton = ButtonFactory.getImageButton(game.assetManager.get(RES_BUT_LEVEL_BORDER, Texture.class),
                        game.assetManager.get(RES_BUT_LEVEL_LOCK, Texture.class), false, ACH_BUTTONS_SIZE, ACH_BUTTONS_SIZE);
            }
            achButton.setPosition(ACH_BUTTONS_X, ACH_BUTTONS_START_Y + i * (ACH_BUTTONS_EMPTY_SPACE + ACH_BUTTONS_SIZE));
            actionArea.addActor(achButton);
        }

        Gdx.app.log(TAG, "showed");
    }

    @Override
    public void dispose() {
        backBtn.clearListeners();

        super.dispose();
    }
}
