package com.bubblezombie.game.Screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.bubblezombie.game.BubbleZombieGame;
import com.bubblezombie.game.Util.Factory.ButtonFactory;
import com.bubblezombie.game.Util.Factory.FontFactory;
import com.bubblezombie.game.Util.Managers.SaveManager;

/**
 * Created by Alex on 18/12/15.
 */
public class ResetScreen extends BaseUIScreen {
    private static final String TAG = "ResetScreen";

    private static final String RES_RESET_CONTENT = "background/screens/reset_content.png";
    private static final String RES_BUT_OK = "background/screens/but_ok.png";

    private static final int BTN_WIDTH = 92;
    private static final int BTN_HEIGHT = 38;
    private static final int YES_BTN_X = 208;
    private static final int BTN_Y = 160;
    private static final int BTN_DISTANCE = 140;

    private BitmapFont _europeExtBoldSize25;

    ResetScreen(BubbleZombieGame game) {
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

        game.assetManager.load(RES_RESET_CONTENT, Texture.class);
        game.assetManager.load(RES_BUT_OK, Texture.class);

        game.assetManager.finishLoading();

        Image resetContent = new Image(game.assetManager.get(RES_RESET_CONTENT, Texture.class));
        resetContent.setPosition((BubbleZombieGame.width - resetContent.getWidth()) / 2,
                (BubbleZombieGame.height - resetContent.getHeight()) / 2);
        actionArea.addActor(resetContent);

        backBtn.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Gdx.app.log("back button", "starting level select screen...");
                dispose();
                game.setScreen(new LevelSelectScreen(game));
            }
        });


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

        _europeExtBoldSize25 = FontFactory.getEuropeExt(FontFactory.FontType.BUTTON, 25);

        // YES
        Button yesBtn = ButtonFactory.getTextButton(game.assetManager.get(RES_BUT_OK, Texture.class), "Yes",
                _europeExtBoldSize25, false, BTN_HEIGHT, BTN_WIDTH);
        yesBtn.setPosition(YES_BTN_X, BTN_Y);
        yesBtn.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                SaveManager.clearData();
                SaveManager.setSharedData("level1_opened", true);

                //open all levels if we hacve this cheat
                if (LevelSelectScreen.ALL_LEVELS_OPENED)
                    for (int k = 2; k <= LevelSelectScreen.LEVELS_AMOUNT; k++)
                        SaveManager.setSharedData("level" + k + "_opened", true);

                dispose();
                game.setScreen(new LevelSelectScreen(game));
            }
        });
        actionArea.addActor(yesBtn);


        // NO
        Button noBtn = ButtonFactory.getTextButton(game.assetManager.get(RES_BUT_OK, Texture.class), "No",
                _europeExtBoldSize25, false, BTN_HEIGHT, BTN_WIDTH);
        noBtn.setPosition(YES_BTN_X + BTN_DISTANCE, BTN_Y);
        noBtn.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                dispose();
                game.setScreen(new LevelSelectScreen(game));
            }
        });
        actionArea.addActor(noBtn);

        Gdx.app.log(TAG, "showed");
    }

    @Override
    public void dispose() {
        backBtn.clearListeners();
        _europeExtBoldSize25.dispose();

        super.dispose();
    }
}
