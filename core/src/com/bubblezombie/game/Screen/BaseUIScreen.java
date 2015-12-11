package com.bubblezombie.game.Screen;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.WidgetGroup;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.bubblezombie.game.Util.ButtonFactory;

/**
 * Created by Alex on 7/12/15.
 * Handy class for creating basement of all menu screens:
 * ctor take booleans as a parameters in order to determine
 * which buttons we should display
 */

public class BaseUIScreen extends BaseScreen {
    private boolean _isMoreGamesBtn, _isBackBtn, _isAchievmentsBtn, _isRestartBtn,
            _isResetBtn, _isSkipLevelBtn, _isNextLvlBtn, _isLvlMapBtn;

    // TODO: may be protected interface?? ex: protected GetBackBtn() { if (!_isBackBtn) throw Exception("No such btn") }
    protected Button moreGamesBtn, backBtn, achievmentsBtn, restartBtn,
            resetBtn, skipLevelBtn, nextLvlBtn, lvlMapBtn;

    // area in the middle of the screen when we should put all stuff
    protected WidgetGroup actionArea;

    BaseUIScreen(Game game, boolean isMoreGamesBtn, boolean isBackBtn, boolean isAchievmentsBtn, boolean isRestartBtn,
                 boolean isResetBtn, boolean isSkipLevelBtn, boolean isNextLvlBtn, boolean isLvlMapBtn) {
        super(game);
        _isMoreGamesBtn = isMoreGamesBtn;
        _isBackBtn = isBackBtn;
        _isAchievmentsBtn = isAchievmentsBtn;
        _isRestartBtn = isRestartBtn;
        _isResetBtn = isResetBtn;
        _isSkipLevelBtn = isSkipLevelBtn;
        _isNextLvlBtn = isNextLvlBtn;
        _isLvlMapBtn = isLvlMapBtn;
    }

    @Override
    public void show() {
        Image BGsprite = new Image(new Texture(Gdx.files.internal("background/menu.png")));
        stage.addActor(BGsprite);

        actionArea = new WidgetGroup();
        stage.addActor(actionArea);

        // TODO: remember - we should have only one background for button

        // buttons:

        TextButton.TextButtonStyle tbs;

        // more games button
        if (_isMoreGamesBtn) {
            Button moreGamesBtn = ButtonFactory.getTextButton("background/UI_buttons/btn_background.png", "MORE GAMES", false, 45.0f, 45.0f);
            moreGamesBtn.setPosition(17.0f, 415.0f);
            stage.addActor(moreGamesBtn);
        }

        // level map button
        if (_isLvlMapBtn) {
            Button lvlMapBtn = ButtonFactory.getImageButton("background/UI_buttons/btn_background.png",
                    "background/UI_buttons/btn_menu.png", false, 63.0f, 63.0f);
            lvlMapBtn.setPosition(103.0f, 7.0f);
            stage.addActor(lvlMapBtn);
        }

        //back button
        if (_isBackBtn) {
            tbs = new TextButton.TextButtonStyle();
            tbs.up = new TextureRegionDrawable(new TextureRegion(new Texture(Gdx.files.internal("background/UI_buttons/but_menu.png"))));
            Button lvlMapBtn = new Button(tbs);
            lvlMapBtn.setPosition(103.0f, 7.0f);
            lvlMapBtn.setHeight(63.0f);
            lvlMapBtn.setWidth(63.0f);
            stage.addActor(lvlMapBtn);
        }

        // next level button
        if (_isNextLvlBtn) {
            tbs = new TextButton.TextButtonStyle();
            tbs.up = new TextureRegionDrawable(new TextureRegion(new Texture(Gdx.files.internal("background/UI_buttons/but_menu.png"))));
            Button nextScreenBtn = new Button(tbs);
            nextScreenBtn.setPosition(103.0f, 7.0f);
            nextScreenBtn.setHeight(63.0f);
            nextScreenBtn.setWidth(63.0f);
            stage.addActor(nextScreenBtn);
        }

        // sound ON button
        tbs = new TextButton.TextButtonStyle();
        tbs.up = new TextureRegionDrawable(new TextureRegion(new Texture(Gdx.files.internal("background/UI_buttons/but_menu.png"))));
        Button lvlMapBtn = new Button(tbs);
        lvlMapBtn.setPosition(103.0f, 7.0f);
        lvlMapBtn.setHeight(63.0f);
        lvlMapBtn.setWidth(63.0f);
        stage.addActor(lvlMapBtn);
    }

}
