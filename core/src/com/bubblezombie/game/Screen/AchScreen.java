package com.bubblezombie.game.Screen;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.bubblezombie.game.AchievmentsManager;
import com.bubblezombie.game.BubbleZombieGame;
import com.bubblezombie.game.Util.ButtonFactory;

/**
 * Created by Alex on 17/12/15.
 */
public class AchScreen extends BaseUIScreen {
    private static final String TAG = "AchScreen";

    private static final int ACH_BUTTONS_X = 465;
    private static final int ACH_BUTTONS_START_Y = 110;
    private static final int ACH_BUTTONS_EMPTY_SPACE = 5;
    private static final float ACH_BUTTONS_SIZE = 45.0f;

    AchScreen(Game game) {
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

        backBtn.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Gdx.app.log("back button", "starting level select screen...");
                game.setScreen(new LevelSelectScreen(game));
            }
        });

        Image achContent = new Image(new Texture(Gdx.files.internal("background/screens/ach_content.png")));
        achContent.setPosition((BubbleZombieGame.width - achContent.getWidth()) / 2,
                (BubbleZombieGame.height - achContent.getHeight()) / 2 + 15);
        actionArea.addActor(achContent);

        for (int i = 0; i <= 4; i++) {
            Button achButton;

            if (AchievmentsManager.IsAchievmentPassed(i)) {
                achButton = ButtonFactory.getImageButton("background/screens/but_level_border.png",
                        "background/screens/but_tip.png", false, ACH_BUTTONS_SIZE, ACH_BUTTONS_SIZE);
            } else {
                achButton = ButtonFactory.getImageButton("background/screens/but_level_border.png",
                        "background/screens/but_level_lock.png", false, ACH_BUTTONS_SIZE, ACH_BUTTONS_SIZE);
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
