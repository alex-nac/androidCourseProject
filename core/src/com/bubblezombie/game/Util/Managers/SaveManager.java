package com.bubblezombie.game.Util.Managers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;

import java.util.HashMap;


/**
 * static class that helps us to save game progress
 * among launches
 */
public class SaveManager {
    private static HashMap<String, Object> _sharedData;
    private static Preferences preferences;
    public SaveManager() {}


    /**
     * initializes the game-info database
     * MUST be invoked before other methods downhere
     * @param path to file contains saves
     */
    public static void initialize(String path) {
        preferences = Gdx.app.getPreferences(path);
        _sharedData = (HashMap<String, Object>) preferences.get();
        checkSharedData();
    }

    /**
     * @param key string
     * @param <T> return type
     * @return a value associated with given key
     */
    public static <T> T getSharedData(String key) {
        T res = null;
        try {
            res = (T) _sharedData.get(key);
        } catch (NullPointerException e) {
            Gdx.app.log("data", " wasn't initialized");
            e.printStackTrace();
        }
        if (res == null) {
            return (T) Boolean.FALSE;
        }
        return res;
    }

    /**
     * storage new value associated with key string
     * @param key string
     * @param value to be storaged
     * @param <T> Type to be storaged
     */
    public static <T> void setSharedData(String key, T value) {
        try {
            _sharedData.put(key, value);
        } catch (NullPointerException e) {
            Gdx.app.log("data", " wasn't initialized");
            e.printStackTrace();
        }
    }

    /**
     * saves temporal map to local storage
     */
    public static void saveSharedData() {
        try {
            preferences.put(_sharedData).flush();
        } catch (NullPointerException e) {
            Gdx.app.log("data", " wasn't initialized");
            e.printStackTrace();
        }
    }

    /**
     * cleans all saved data
     */
    public static void clearData() {
        _sharedData.clear();
        saveSharedData();
    }

    /**
     * puts all necessary values to map and saves it
     */
    private static void checkSharedData() {
        if (_sharedData != null && _sharedData.get("wasLaunched") == null) {
            _sharedData.put("wasLaunched", true);
            _sharedData.put("was_airplane_tutorial_showed", false);
            _sharedData.put("soundEnabled", true);
            saveSharedData();
        }
    }
}
