package com.bubblezombie.game.Screen;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.bubblezombie.game.BubbleZombieGame;
import com.bubblezombie.game.Util.ButtonFactory;
import com.bubblezombie.game.Util.FontFactory;
import com.bubblezombie.game.Util.SaveManager;

/**
 * Created by Alex on 12/12/15.
 * Screen appears after intro screen where we choose which level to play
 */
public class LevelSelectScreen extends BaseUIScreen {

    //used for game designer
    private static final Boolean ALL_LEVELS_OPENED = true;

    private static final int STARTX = 145;
    private static final int STARTY = 165;
    private static final int LEVEL_BUTTON_SIZE = 36;
    private static final int X_SPACE = 80;
    private static final int Y_SPACE = 46;

    private static final int LEVELS_AMOUNT = 25;

    //////////////////////
    ///STATIC_FUNCTIONS///
    //////////////////////

    //saving current state of the game progress
    public static void LevelCompleted(int number, int scores) {
        OpenLevel(number + 1);
        String key = "level" + number + "_scores";

        //we are interested in best scores
        if ((Integer)SaveManager.getSharedData(key) < scores)
            SaveManager.setSharedData(key, scores);

        SaveManager.saveSharedData();
    }


    //saving the amount of loses for this level
    public static void LevelFailed(int number) {
        String key = "level" + number + "_failed";
        int numberOfLoses = GetLevelFailsAmount(number);
        numberOfLoses++;
        SaveManager.setSharedData(key, numberOfLoses);

        SaveManager.saveSharedData();
    }

    //setting level opened
    public static void OpenLevel(int number) {
        String key = "level" + number + "_opened";
        SaveManager.setSharedData(key, true);

        SaveManager.saveSharedData();
    }

    //mark the level as skipped level
    public static void SkipLevel(int number) {
        String key = "level" + number + "_skipped";
        SaveManager.setSharedData(key, true);

        SaveManager.saveSharedData();
    }

    //getting how many times we lose this level
    public static int GetLevelFailsAmount(int number) {
        String key = "level" + number + "_failed";
        int numberOfLoses = 0;
        if(SaveManager.getSharedData(key))
            numberOfLoses = (Integer)SaveManager.getSharedData(key);

        return numberOfLoses;
    }

    //checking if we have passed this level
    public static Boolean GetLevelPassed(int number) {
        String key = "level" + number + "_scores";
        return (Boolean)SaveManager.getSharedData(key);
    }


    //getting total amount of scores for all the levels
    public static int GetTotalScores() {
        int totalScores = 0;
        for (int lvlNum = 1; lvlNum <= LEVELS_AMOUNT; lvlNum++) {
            String key = "level" + lvlNum + "_scores";
            if (SaveManager.getSharedData(key))
                totalScores += (Integer)SaveManager.getSharedData(key);
        }

        return totalScores;
    }




    private BitmapFont _europeExtBoldSize15;
    private TextField _totalScore, _bestScore;

    LevelSelectScreen(Game game) {
        super(game);
        _isMoreGamesBtn = true;
        _isLvlMapBtn = true;
        _isAchievmentsBtn = true;
        _isResetBtn = true;
    }

    @Override
    public void show() {
        super.show();

        SaveManager.setSharedData("level1_opened", true);

        //open all levels if we have this cheat
        if (ALL_LEVELS_OPENED)
            for (int k = 2; k <= LEVELS_AMOUNT; k++)
                SaveManager.setSharedData("level" + k + "_opened", true);

        Image levelmapContent = new Image(new Texture("background/screens/levelmap_content.png"));
        levelmapContent.setPosition((BubbleZombieGame.width - levelmapContent.getWidth()) / 2,
                (BubbleZombieGame.height - levelmapContent.getHeight()) / 2 + 25);
        actionArea.addActor(levelmapContent);

        _europeExtBoldSize15 = FontFactory.getEuropeExt(FontFactory.FontType.BUTTON, 15);


        // create level buttons
        for (int i = 0; i < 5; i++)
            for (int j = 0; j < 5; j++) {
                int levelNum = i * 5 + j + 1;
                String key = "level" + levelNum + "_opened";
                Button levelbtn;

                //if we can play this level
                if (SaveManager.getSharedData(key)) {
                    levelbtn = ButtonFactory.getTextButton("background/screens/but_level_border.png",
                            Integer.toString(levelNum), _europeExtBoldSize15, false, LEVEL_BUTTON_SIZE, LEVEL_BUTTON_SIZE);

                    // listeners
                    levelbtn.addListener(new ClickListener() {
                        @Override
                        public void clicked(InputEvent event, float x, float y) {
                            Gdx.app.log("next screen button", "starting level select screen...");
                            game.setScreen(new LevelSelectScreen(game));
                        }
                    });

                    //levelbtn.addEventListener(MouseEvent.ROLL_OVER, ShowBestScoresCB);
                    //levelbtn.addEventListener(MouseEvent.ROLL_OUT, ShowBestScoresCB);

                    // TODO: if player skipped the level mark it
                    if (Boolean.TRUE.equals(SaveManager.getSharedData("level" + levelNum + "_skipped")) && !GetLevelPassed(levelNum)) {
                        //levelbtn.white_border.visible = true;
                                /*
                                var colorTransform:ColorTransform = levelbtn.transform.colorTransform;

                                colorTransform.redMultiplier *= 0.8;
                                colorTransform.greenMultiplier *= 0.8;
                                colorTransform.blueMultiplier *= 0.8;


                                levelbtn.transform.colorTransform = colorTransform;
                                */
                    }

                }
                else {
                    levelbtn = ButtonFactory.getImageButton("background/screens/but_level_border.png",
                            "background/screens/but_level_lock.png", false, LEVEL_BUTTON_SIZE, LEVEL_BUTTON_SIZE);
                }

                levelbtn.setPosition(STARTX + j * X_SPACE, BubbleZombieGame.height - (STARTY + i * Y_SPACE));
                actionArea.addActor(levelbtn);
        }

        // create text fields
        TextField.TextFieldStyle textFieldStyle = new TextField.TextFieldStyle();
        textFieldStyle.font = _europeExtBoldSize15;
        textFieldStyle.fontColor = Color.YELLOW;

        _bestScore = new TextField("123", textFieldStyle);
        _bestScore.setPosition(130, 100);
        actionArea.addActor(_bestScore);

        _totalScore = new TextField("TOTAL: 765", textFieldStyle);
        _totalScore.setPosition(395, 100);
        actionArea.addActor(_totalScore);
    }

    @Override
    public void dispose() {
        moreGamesBtn.clearListeners();
        lvlMapBtn.clearListeners();
        achievmentsBtn.clearListeners();
        resetBtn.clearListeners();

        super.dispose();
    }
}
