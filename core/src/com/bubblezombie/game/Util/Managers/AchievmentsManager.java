package com.bubblezombie.game.Util.Managers;

import com.bubblezombie.game.Screen.LevelSelectScreen;
import com.bubblezombie.game.Util.Managers.SaveManager;

/**
 * Created by Alex on 17/12/15.
 */
public class AchievmentsManager {

    public static final int DEFENDER = 0;
    public static final int VETERAN = 1;
    public static final int ZOMBIEHUNTER = 2;
    public static final int RECORDBREAKER = 3;
    public static final int MASTER = 4;

    public static final int DEFENDER_LEVELS_AMOUNT = 15;
    public static final int VETERAN_LEVEL = 25;
    public static final int ZOMBIEHUNTER_ZOMBIE_AMOUNT = 1000;
    public static final int RECORDBREAKER_POINTS = 20000;

    public static final int MASTER_ACH_SCORE_BONUS = 500;


    //return true if we have enough levels completed
    public static Boolean CheckForDEFENDER() {
        if (SaveManager.getSharedData("ach" + DEFENDER + "_passed"))
            return false;

        int numLevelsCompleted = 0;
        for (int i = 1; i <= LevelSelectScreen.LEVELS_AMOUNT; i++)
            if (LevelSelectScreen.GetLevelPassed(i)) numLevelsCompleted++;

        if (numLevelsCompleted >= DEFENDER_LEVELS_AMOUNT) {
            SaveManager.setSharedData("ach" + DEFENDER + "_passed", true);
            SaveManager.saveSharedData();
            return true;
        } else return false;
    }

    //return true if we completed certain level
    public static Boolean CheckForVETERAN() {
        if (SaveManager.getSharedData("ach" + VETERAN + "_passed"))
            return false;

        if (LevelSelectScreen.GetLevelPassed(VETERAN_LEVEL)) {
            SaveManager.setSharedData("ach" + VETERAN + "_passed", true);
            SaveManager.saveSharedData();
            return true;
        } else return false;
    }

    //if we killed enough zombies
    public static Boolean CheckForZOMBIEHUNTER(int killedZombies) {
        if (SaveManager.getSharedData("ach" + ZOMBIEHUNTER + "_passed"))
            return false;

        String key = "zombie_killed_ach";
        SaveManager.setSharedData(key, (Integer) SaveManager.getSharedData(key) + killedZombies);

        if ((Integer) SaveManager.getSharedData(key) >= ZOMBIEHUNTER_ZOMBIE_AMOUNT) {
            SaveManager.setSharedData("ach" + ZOMBIEHUNTER + "_passed", true);
            return true;
        } else return false;
    }

    //if we get enougth scores
    public static Boolean CheckForRECORDBREAKER() {
        if (SaveManager.getSharedData("ach" + RECORDBREAKER + "_passed"))
            return false;

        if (LevelSelectScreen.GetTotalScores() >= RECORDBREAKER_POINTS) {
            SaveManager.setSharedData("ach" + RECORDBREAKER + "_passed", true);
            SaveManager.saveSharedData();
            return true;
        } else return false;
    }

    //if we killed all bubbles before the plane wave
    public static Boolean CheckForMASTER() {
        //return false if we have already passed this achievment
        if (SaveManager.getSharedData("ach" + MASTER + "_passed"))
            return false;

        SaveManager.setSharedData("ach" + MASTER + "_passed", true);
        SaveManager.saveSharedData();
        return true;
    }

    //checking if we have passed achievment
    public static Boolean IsAchievmentPassed(int ach) {
        String key = "ach" + ach + "_passed";
        return SaveManager.getSharedData(key);
    }
}
