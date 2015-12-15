package com.bubblezombie.game.Screen;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.WidgetGroup;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.bubblezombie.game.Util.ButtonFactory;
import com.bubblezombie.game.Util.FontFactory;

/**
 * Created by Alex on 7/12/15.
 * Handy class for creating basement of all menu screens:
 * ctor take booleans as a parameters in order to determine
 * which buttons we should display
 *
 * USING: extend your state class from this, then in ctor set
 * booleans you need TRUE
 */

public class BaseUIScreen extends BaseScreen {
    protected boolean
            _isMoreGamesBtn = false,
            _isBackBtn = false,
            _isAchievmentsBtn = false,
            _isRestartBtn = false,
            _isResetBtn = false,
            _isSkipLvlBtn = false,
            _isNextLvlBtn = false,
            _isLvlMapBtn = false;
    protected boolean
            _isMoreGamesBtnShaded = false,
            _isBackBtnShaded = false,
            _isAchievmentsBtnShaded = false,
            _isRestartBtnShaded = false,
            _isResetBtnShaded = false,
            _isSkipLvlBtnShaded = false,
            _isNextLvlBtnShaded = false,
            _isLvlMapBtnShaded = false;

    // TODO: may be protected interface?? ex: protected GetBackBtn() { if (!_isBackBtn) throw Exception("No such btn") }
    protected Button moreGamesBtn, backBtn, achievmentsBtn, restartBtn,
            resetBtn, skipLevelBtn, nextLvlBtn, lvlMapBtn;

    // area in the middle of the screen when we should put all stuff
    protected WidgetGroup actionArea;

    private BitmapFont _europeExtBoldSize10;

    BaseUIScreen(Game game) {
        super(game);
    }

    @Override
    public void show() {
        super.show();
        
        Image BGsprite = new Image(new Texture(Gdx.files.internal("background/screens/menu.png")));
        stage.addActor(BGsprite);

        actionArea = new WidgetGroup();
        stage.addActor(actionArea);

        // TODO: remember - we should have only one background for button

        // buttons:

        TextButton.TextButtonStyle tbs;
        BitmapFont _europeExtBold = new BitmapFont(Gdx.files.internal("fonts/sample_font.fnt"));
        _europeExtBoldSize10 = FontFactory.getEuropeExt(FontFactory.FontType.BUTTON, 15);

        // more games button
        if (_isMoreGamesBtn) {
            moreGamesBtn = ButtonFactory.getTextButton("background/UI_buttons/btn_background.png",
                    "MORE\nGAMES", _europeExtBoldSize10, 0.5f, _isMoreGamesBtnShaded, 45.0f, 45.0f);
            moreGamesBtn.setPosition(17.0f, 415.0f);
            stage.addActor(moreGamesBtn);
        }

        // level map button
        if (_isLvlMapBtn) {
            lvlMapBtn = ButtonFactory.getImageButton("background/UI_buttons/btn_background.png",
                    "background/UI_buttons/btn_menu.png", _isLvlMapBtnShaded, 63.0f, 63.0f);
            lvlMapBtn.setPosition(103.0f, 7.0f);
            stage.addActor(lvlMapBtn);
        }

        // back button
        if (_isBackBtn) {
            backBtn = ButtonFactory.getImageButton("background/UI_buttons/btn_background.png",
                    "background/UI_buttons/btn_arrow_left.png", _isBackBtnShaded, 63.0f, 63.0f);
            backBtn.setPosition(103.0f, 7.0f);
            stage.addActor(backBtn);
        }

        // achievments button
        if (_isAchievmentsBtn) {
            achievmentsBtn = ButtonFactory.getTextButton("background/UI_buttons/btn_background.png",
                    "ACH", _europeExtBold, 1.0f, _isAchievmentsBtnShaded, 63.0f, 63.0f);
            achievmentsBtn.setPosition(288.5f, 7.0f);
            stage.addActor(achievmentsBtn);
        }

        // restart button
        if (_isBackBtn) {
            restartBtn = ButtonFactory.getImageButton("background/UI_buttons/btn_background.png",
                    "background/UI_buttons/btn_arrow_restart.png", _isRestartBtnShaded, 63.0f, 63.0f);
            restartBtn.setPosition(288.5f, 7.0f);
            stage.addActor(restartBtn);
        }

        // next level button
        if (_isNextLvlBtn) {
            nextLvlBtn = ButtonFactory.getImageButton("background/UI_buttons/btn_background.png",
                    "background/UI_buttons/btn_arrow_right.png", _isNextLvlBtnShaded, 63.0f, 63.0f);
            nextLvlBtn.setPosition(469.0f, 7.0f);
            stage.addActor(nextLvlBtn);
        }

        // skip level button
        if (_isSkipLvlBtn) {
            skipLevelBtn = ButtonFactory.getImageButton("background/UI_buttons/btn_background.png",
                    "background/UI_buttons/btn_arrow_double.png", _isSkipLvlBtnShaded, 63.0f, 63.0f);
            skipLevelBtn.setPosition(469.0f, 7.0f);
            stage.addActor(skipLevelBtn);
        }

        // reset button
        if (_isResetBtn) {
            resetBtn = ButtonFactory.getTextButton("background/UI_buttons/btn_background.png",
                    "RESET", _europeExtBold, 1.0f, _isResetBtnShaded, 63.0f, 63.0f);
            resetBtn.setPosition(469.0f, 7.0f);
            stage.addActor(resetBtn);
        }

        // TODO: sound buttons
        /*
        // sound ON button
        tbs = new TextButton.TextButtonStyle();
        tbs.up = new TextureRegionDrawable(new TextureRegion(new Texture(Gdx.files.internal("background/UI_buttons/but_menu.png"))));
        Button lvlMapBtn = new Button(tbs);
        lvlMapBtn.setPosition(103.0f, 7.0f);
        lvlMapBtn.setHeight(63.0f);
        lvlMapBtn.setWidth(63.0f);
        stage.addActor(lvlMapBtn);
        */
    }

}
