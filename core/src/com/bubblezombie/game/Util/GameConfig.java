package com.bubblezombie.game.Util;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.utils.GdxRuntimeException;
import com.badlogic.gdx.utils.SerializationException;
import com.badlogic.gdx.utils.XmlReader;
import com.badlogic.gdx.utils.XmlReader.Element;
import com.badlogic.gdx.utils.Array;
import com.bubblezombie.game.Prefabs.PatternData;
import com.bubblezombie.game.Prefabs.PrefabData;
import com.bubblezombie.game.Prefabs.PrefabException;


import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;

/**
 * Configuration class for game
 */
public class GameConfig {
    private static final String TAG = "GameConfig";

    public Integer wonTime;
    public Boolean useDebugView;
    public String BGclassName;
    public String tutorClassName;
    public int meshBubbleDiametr;
    public int planeButtonTime;

    //mesh config
    public int waveVelocity;
    public int columnsNum;
    public int rowsShowed;
    public int rowsNum;
    public int uberZombieAmount;
    public ArrayList<Pair<Integer, Integer>> sprayers = new ArrayList<Pair<Integer, Integer>>();
    public int frozenTime;
    public int offset;

    //walls config
    public int useWalls;
    public int leftWallEdge;
    public int rightWallEdge;

    //bubbles config
    public int colors;

    //bullets config
    public int superBulletPercent;
    public int bombPercent;
    public int freezeBombPercent;
    public int colorBombPercent;

    //scores system
    public int basicScores;
    public int basicComboBonus;
    public int uberScores;
    public int steamScores;

    //prefabs
    public PrefabData prefabData;
    public PatternData patternData;

    //popups
    public ArrayList<String> popupsVector = new ArrayList<String>();
    public int popupSpawnPeriod;
    public int popupMaxTimeOffset;
    public int popupLifeTime;
    public float popupProbability;

    private static Method get = null,
            getInt = null,
            getBoolean = null,
            getChildByName = null,
            getChildrenByName = null,
            getAttribute = null,
            getIntAttribute = null,
            getBooleanAttribute = null,
            getFloatAttribute = null;

    public static void initialize() {
        // ебать, как я люблю джаву
        try {
            Class<Element> elementClass = Element.class;
            get = elementClass.getMethod("get", String.class);
            getInt = elementClass.getMethod("getInt", String.class);
            getBoolean = elementClass.getMethod("getBoolean", String.class);
            getChildrenByName = elementClass.getMethod("getChildrenByName", String.class);
            getChildByName = elementClass.getMethod("getChildByName", String.class);
            getAttribute = elementClass.getMethod("getAttribute", String.class);
            getIntAttribute = elementClass.getMethod("getIntAttribute", String.class);
            getBooleanAttribute = elementClass.getMethod("getBooleanAttribute", String.class);
            getFloatAttribute = elementClass.getMethod("getFloatAttribute", String.class);
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
    }

    /**
     * constructor reads all level parameters, and seeds to generate mesh
     * @param data file to be parsed
     * @throws IOException
     */
    public GameConfig(FileHandle data) throws IOException {
        // TODO: if xml file doesn't contain at least one of these fields - crashes (but shouldn't)
        // FIXED: that's why we need all these Methods
        XmlReader reader = new XmlReader();
        Element parsed = reader.parse(data);
        try {
            wonTime = safeGet(parsed, getInt, "won_time");
            BGclassName = safeGet(parsed, get, "background_class_name");
            tutorClassName = safeGet(parsed, get, "tutorial");
            frozenTime = safeGet(parsed, getInt, "frozen_time");
            useDebugView = safeGet(parsed, getBoolean, "use_debug_view");
            meshBubbleDiametr = safeGet(parsed, getInt, "mesh_bubble_diameter");
            planeButtonTime = safeGet(parsed, getInt, "plane_button_time");
            // TODO: Airplane
//        if (planeButtonTime < Airplane.SPACE_PLANE_TIME && planeButtonTime != 0 && planeButtonTime != -1)
//            throw new LevelCreationException("Введенное тобой время для самолетной кнопки меньше времени полета самолета, иными словами меньше "
//                    + Airplane.SPACE_PLANE_TIME + " секунд" );

            waveVelocity = safeGet(parsed, getInt, "wave_velocity");
            rowsNum = safeGet(parsed, getInt, "rows_num");
            columnsNum = safeGet(parsed, getInt, "columns_num");
            rowsShowed = safeGet(parsed, getInt, "rows_showed");
            uberZombieAmount = safeGet(parsed, getInt, "zombie");
            offset = safeGet(parsed, getInt, "mesh_offset");

            useWalls = safeGet(parsed, getInt, "use_walls");
            leftWallEdge = safeGet(parsed, getInt, "left_wall_edge");
            rightWallEdge = safeGet(parsed, getInt, "right_wall_edge");

            Element zombieSprayer = GameConfig.<Element>safeGet(parsed, getChildByName, "zombie_sprayer");
            if (zombieSprayer != null) {
                Element[] sprayer_list = safeGet(zombieSprayer, getChildrenByName, "zombie");
                for (Element sprayer : sprayer_list) {
                    sprayers.add(
                            new Pair<Integer, Integer>(sprayer.getInt("activeGuns"), sprayer.getInt("time"))
                    );
                }
            }

            colors = safeGet(parsed, getInt, "colors");
            superBulletPercent = safeGet(parsed, getInt, "super_bullet_percent");
            bombPercent = safeGet(parsed, getInt, "bomb_percent");
            freezeBombPercent = safeGet(parsed, getInt, "freeze_bomb_percent");
            colorBombPercent = safeGet(parsed, getInt, "color_bomb_percent");

            basicScores = safeGet(parsed, getInt, "basic_scores");
            basicComboBonus = safeGet(parsed, getInt, "basic_combo_bonus");
            uberScores = safeGet(parsed, getInt, "uber_scores");
            steamScores = safeGet(parsed, getInt, "steam_scores");

            //save prefabs arrays
            prefabData = new PrefabData();
            try {
                Array<Element> arrays = safeGet(parsed, getChildrenByName, "Arrays");
                if (arrays != null) {
                    for (int i = 0; i < arrays.size; ++i) {
                        ArrayList<String> coords = new ArrayList<String>();
                        Array<Element> array = GameConfig.<Array<Element>>safeGet(arrays.get(i), getChildrenByName, "dot");
                        for (Element dot : array) {
                            coords.add(GameConfig.<String>safeGet(dot, getAttribute, "position"));
                        }
                        prefabData.addArrFromCoordArr(coords);
                    }
                }
            } catch (PrefabException e) {
                e.printStackTrace();
            }
            //save prefabs patterns
            patternData = new PatternData();
            Element patternList = GameConfig.<Element>safeGet(parsed, getChildByName, "Patterns");
            if (patternList != null) {
                Array<Element> patterns = safeGet(patternList, getChildrenByName, "pattern");
                for (Element pattern : patterns) {
                    patternData.addPattern(
                            GameConfig.<Integer>safeGet(pattern, getIntAttribute, "firstMaxIndex"),
                            GameConfig.<String>safeGet(pattern, getAttribute, "prefabTypes"),
                            GameConfig.<String>safeGet(pattern, getAttribute, "probability"),
                            GameConfig.<Integer>safeGet(pattern, getIntAttribute, "count"),
                            GameConfig.<Integer>safeGet(pattern, getIntAttribute, "minDistance"),
                            GameConfig.<Boolean>safeGet(pattern, getBooleanAttribute, "canOverlay")
                    );
                }
            }

            //save popups
            Element popUps = safeGet(parsed, getChildByName, "Pop_ups");
            if (popUps != null) {
                for (Element popup : GameConfig.<Array<Element>>safeGet(popUps, getChildrenByName, "Pop_up")) {
                    // TODO: NOOOOOOOOOOOOOOOOOOOOOOOOOO!!!
                    // TODO: calling without string argument
                    popupsVector.add(popup.getText());
                }
                popupSpawnPeriod = safeGet(popUps, getIntAttribute, "spawnPeriod");
                popupMaxTimeOffset = safeGet(popUps, getIntAttribute, "maxTimeOffset");
                popupLifeTime = safeGet(popUps, getIntAttribute, "lifeTime");
                popupProbability = safeGet(popUps, getFloatAttribute, "probability");
            }
        } catch (NullPointerException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
    }


    private static <T> T safeGet(Object caller, Method getter, String name) throws NoSuchMethodException,
            IllegalAccessException, InvocationTargetException, InstantiationException {
        T result = null;
        try {
            result = (T) getter.invoke(caller, name);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            Gdx.app.log(TAG, name + "not found");
            if (getter.getReturnType().isPrimitive()) {
                result = (T) DefaultValues.getDefaultValueForClass(getter.getReturnType());
            }
        } catch (GdxRuntimeException e) {
            Gdx.app.log(TAG, "wrond file format");
            e.printStackTrace();
        } catch (SerializationException e) {
            Gdx.app.log(TAG, "file not found");
            e.printStackTrace();
        } catch (NullPointerException e) {
            Gdx.app.log(TAG, "init must be invoked");
            e.printStackTrace();
        } finally {
            return result;
        }
    }
}
