package com.bubblezombie.game.Util;

import com.badlogic.gdx.Gdx;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.TreeMap;

public class SaveManager {
    private static TreeMap<String, Object> _sharedData;
    private static String _fileName;
    public SaveManager() {}

    public static void initialize(String path) {
        _fileName = path;
        try {
            ObjectInputStream ois = new ObjectInputStream(new FileInputStream(_fileName));
            _sharedData =  (TreeMap<String, Object>)ois.readObject();
        } catch (FileNotFoundException e) {
            // if file doesn't exists - create it
            _sharedData = new TreeMap<String, Object>();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassCastException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            checkSharedData();
        }
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
            ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(_fileName));
            oos.writeObject(_sharedData);
        } catch (IOException e) {
            Gdx.app.log(_fileName, " - couldn't open this file for writting (or something else)");
            e.printStackTrace();
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
