package com.bubblezombie.game.Util;

/**
 * Created by artem on 02.12.15.
 */

import com.bubblezombie.game.Prefabs.PatternData;
import com.bubblezombie.game.Prefabs.PrefabData;

import java.util.ArrayList;

/**
 * Configuration class for game
 */
public class GameConfig {
    public double wonTime;
    public int useDebugView;
    public String BGclassName;
    public String tutorClassName;
    public double meshBubbleDiametr;
    public double planeButtonTime;

    //mesh config
    public double waveVelocity;
    public int columnsNum;
    public int rowsShowed;
    public int rowsNum;
    public int uberZombieAmount;
    public ArrayList<Pair<Integer, Double>> sprayers = new ArrayList<Pair<Integer, Double>>();
    public double frozenTime;
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
    public double popupSpawnPeriond;
    public double popupMaxTimeOffset;
    public double popupLifeTime;
    public double popupProbability;
}
