package com.bubblezombie.game.Util;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.bubblezombie.game.Screen.LevelSelectScreen;

import java.util.ArrayList;

// class helds level names in string format
public class LevelContainer {
    private static final Boolean IS_DEBUG = false;

    private static ArrayList<String> _levels;

    public FileHandle GetLevel(int number) {
        return Gdx.files.internal(_levels.get(number - 1));
    }

    public static void initialize() {
        _levels = new ArrayList<String>();
        for (int i = 1; i <= LevelSelectScreen.LEVELS_AMOUNT; ++i) {
            _levels.add(String.format("levels/level_%02d.xml", i));
        }
    }
}
