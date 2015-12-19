package com.bubblezombie.game.Screen;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.WidgetGroup;
import com.bubblezombie.game.BubbleZombieGame;
import com.bubblezombie.game.Util.Factory.ButtonFactory;
import com.bubblezombie.game.Util.Factory.FontFactory;

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

    private BitmapFont _europeExtBoldSize15;

    private static final String RES_MENU = "background/screens/menu.png";
    private static final String RES_SCREEN_GLASS = "background/screens/UI_screen_glass.png";
    private static final String RES_BTN_BACKGROUND = "background/UI_buttons/btn_background.png";
    private static final String RES_BTN_MENU = "background/UI_buttons/btn_menu.png";
    private static final String RES_BTN_ARROW_LEFT = "background/UI_buttons/btn_arrow_left.png";
    private static final String RES_BTN_ARROW_RESTART = "background/UI_buttons/btn_arrow_restart.png";
    private static final String RES_BTN_ARROW_RIGHT = "background/UI_buttons/btn_arrow_right.png";
    private static final String RES_BTN_ARROW_DOUBLE = "background/UI_buttons/btn_double_arrow.png";

    BaseUIScreen(BubbleZombieGame game) {
        super(game);
    }

    @Override
    public void show() {
        super.show();

        game.assetManager.load(RES_MENU, Texture.class);
        game.assetManager.load(RES_SCREEN_GLASS, Texture.class);
        game.assetManager.load(RES_BTN_MENU, Texture.class);
        game.assetManager.load(RES_BTN_BACKGROUND, Texture.class);
        game.assetManager.load(RES_BTN_ARROW_LEFT, Texture.class);
        game.assetManager.load(RES_BTN_ARROW_RESTART, Texture.class);
        game.assetManager.load(RES_BTN_ARROW_RIGHT, Texture.class);
        game.assetManager.load(RES_BTN_ARROW_DOUBLE, Texture.class);

        game.assetManager.finishLoading();


        
        Image BGsprite = new Image(game.assetManager.get(RES_MENU, Texture.class));
        stage.addActor(BGsprite);

        actionArea = new WidgetGroup();
        stage.addActor(actionArea);

        Image glass = new Image(game.assetManager.get(RES_SCREEN_GLASS, Texture.class));
        glass.setPosition((BubbleZombieGame.width - glass.getWidth()) / 2 + 3,
                (BubbleZombieGame.height - glass.getHeight()) / 2 + 12);
        glass.setTouchable(Touchable.disabled);
        stage.addActor(glass);

        // TODO: remember - we should have only one background for button

        // buttons:

        TextButton.TextButtonStyle tbs;

        _europeExtBoldSize15 = FontFactory.getEuropeExt(FontFactory.FontType.BUTTON, 15);

        // more games button
        if (_isMoreGamesBtn) {
            moreGamesBtn = ButtonFactory.getTextButton(game.assetManager.get(RES_BTN_BACKGROUND, Texture.class),
                    "MORE\nGAMES", _europeExtBoldSize15, _isMoreGamesBtnShaded, 45.0f, 45.0f);
            moreGamesBtn.setPosition(17.0f, 415.0f);
            stage.addActor(moreGamesBtn);
        }

        // level map button
        if (_isLvlMapBtn) {
            lvlMapBtn = ButtonFactory.getImageButton(game.assetManager.get(RES_BTN_BACKGROUND, Texture.class),
                    game.assetManager.get(RES_BTN_MENU, Texture.class), _isLvlMapBtnShaded, 63.0f, 63.0f);
            lvlMapBtn.setPosition(103.0f, 7.0f);
            stage.addActor(lvlMapBtn);
        }

        // back button
        if (_isBackBtn) {
            backBtn = ButtonFactory.getImageButton(game.assetManager.get(RES_BTN_BACKGROUND, Texture.class),
                    game.assetManager.get(RES_BTN_ARROW_LEFT, Texture.class), _isBackBtnShaded, 63.0f, 63.0f);
            backBtn.setPosition(103.0f, 7.0f);
            stage.addActor(backBtn);
        }

        // achievments button
        if (_isAchievmentsBtn) {
            achievmentsBtn = ButtonFactory.getTextButton(game.assetManager.get(RES_BTN_BACKGROUND, Texture.class),
                    "ACH", _europeExtBoldSize15, _isAchievmentsBtnShaded, 63.0f, 63.0f);
            achievmentsBtn.setPosition(288.5f, 7.0f);
            stage.addActor(achievmentsBtn);
        }

        // restart button
        if (_isRestartBtn) {
            restartBtn = ButtonFactory.getImageButton(game.assetManager.get(RES_BTN_BACKGROUND, Texture.class),
                    game.assetManager.get(RES_BTN_ARROW_RESTART, Texture.class), _isRestartBtnShaded, 63.0f, 63.0f);
            restartBtn.setPosition(288.5f, 7.0f);
            stage.addActor(restartBtn);
        }

        // next level button
        if (_isNextLvlBtn) {
            nextLvlBtn = ButtonFactory.getImageButton(game.assetManager.get(RES_BTN_BACKGROUND, Texture.class),
                    game.assetManager.get(RES_BTN_ARROW_RIGHT, Texture.class), _isNextLvlBtnShaded, 63.0f, 63.0f);
            nextLvlBtn.setPosition(469.0f, 7.0f);
            stage.addActor(nextLvlBtn);
        }

        // skip level button
        if (_isSkipLvlBtn) {
            skipLevelBtn = ButtonFactory.getImageButton(game.assetManager.get(RES_BTN_BACKGROUND, Texture.class),
                    game.assetManager.get(RES_BTN_ARROW_DOUBLE, Texture.class), _isSkipLvlBtnShaded, 63.0f, 63.0f);
            skipLevelBtn.setPosition(469.0f, 7.0f);
            stage.addActor(skipLevelBtn);
        }

        // reset button
        if (_isResetBtn) {
            resetBtn = ButtonFactory.getTextButton(game.assetManager.get(RES_BTN_BACKGROUND, Texture.class),
                    "RESET", _europeExtBoldSize15, _isResetBtnShaded, 63.0f, 63.0f);
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

    @Override
    public void dispose() {
        _europeExtBoldSize15.dispose();

        super.dispose();
    }
}
