package com.bubblezombie.game.Util;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;

import java.util.HashMap;

public class SaveManager {
    private static HashMap<String, Object> _sharedData;
    private static Preferences preferences;
    public SaveManager() {}

    public static void initialize(String path) {
        preferences = Gdx.app.getPreferences(path);
        _sharedData = (HashMap<String, Object>) preferences.get();
        checkSharedData();
    }
    public static <T> T getSharedData(String key) {
        T res = null;
        try {
            res = (T) _sharedData.get(key);
        } catch (NullPointerException e) {
            Gdx.app.log("data", " wasn't initialized");
            e.printStackTrace();
        }
        return res;
    }
    public static <T> void setSharedData(String key, T value) {
        try {
            _sharedData.put(key, value);
        } catch (NullPointerException e) {
            Gdx.app.log("data", " wasn't initialized");
            e.printStackTrace();
        }
    }
    public static void saveSharedData() {
        try {
            preferences.put(_sharedData).flush();
        } catch (NullPointerException e) {
            Gdx.app.log("data", " wasn't initialized");
            e.printStackTrace();
        }
    }
    public static void clearData() {
        _sharedData.clear();
        saveSharedData();
    }

    private static void checkSharedData() {
        if (_sharedData != null && _sharedData.get("wasLaunched") == null) {
            _sharedData.put("wasLaunched", true);
            _sharedData.put("was_airplane_tutorial_showed", false);
            _sharedData.put("soundEnabled", true);
            saveSharedData();
        }
    }
}
