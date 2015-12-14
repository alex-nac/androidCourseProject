package com.bubblezombie.game.Screen;

import com.badlogic.gdx.Game;
import com.bubblezombie.game.Util.SaveManager;

/**
 * Created by Alex on 12/12/15.
 * Screen appears after intro screen where we choose which level to play
 */
public class LevelSelectScreen extends BaseUIScreen {

    //used for game designer
    private static final Boolean ALL_LEVELS_OPENED = true;

    private static final int STARTX = 160;
    private static final int STARTY = 148;
    private static final float SCALE = 0.8f;
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




    LevelSelectScreen(Game game) {
        super(game);
    }
}
