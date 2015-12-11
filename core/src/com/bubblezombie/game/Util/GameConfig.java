package com.bubblezombie.game.Util;

import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.utils.XmlReader;
import com.bubblezombie.game.Prefabs.PatternData;
import com.bubblezombie.game.Prefabs.PrefabData;
import com.bubblezombie.game.Prefabs.PrefabException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.FileHandler;

/**
 * Configuration class for game
 */
public class GameConfig {

    public int wonTime;
    public int useDebugView;
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
    public double popupProbability;

    public GameConfig(FileHandle data) throws IOException {
        // TODO: if xml file doesn't contain at least one of these fields - crashes (but shouldn't)
        XmlReader reader = new XmlReader();
        XmlReader.Element parsed = reader.parse(data);
        wonTime = parsed.getInt("won_time");
        BGclassName = parsed.get("background_class_name");
        tutorClassName = parsed.get("tutorial");
        frozenTime = parsed.getInt("frozen_time");
        useDebugView = parsed.getInt("use_debug_view");
        meshBubbleDiametr = parsed.getInt("mesh_bubble_diameter");
        planeButtonTime = parsed.getInt("plane_button_time");
        // TODO: Airplane
//        if (planeButtonTime < Airplane.SPACE_PLANE_TIME && planeButtonTime != 0 && planeButtonTime != -1)
//            throw new LevelCreationException("Введенное тобой время для самолетной кнопки меньше времени полета самолета, иными словами меньше "
//                    + Airplane.SPACE_PLANE_TIME + " секунд" );

        waveVelocity = parsed.getInt("wave_velocity");
        rowsNum = parsed.getInt("rows_num");
        columnsNum = parsed.getInt("columns_num");
        rowsShowed = parsed.getInt("rows_showed");
        uberZombieAmount = parsed.getInt("zombie");
        offset = parsed.getInt("mesh_offset");

        useWalls = parsed.getInt("use_walls");
        leftWallEdge = parsed.getInt("left_wall_edge");
        rightWallEdge = parsed.getInt("right_wall_edge");

        parsed.getChildByName("zombie_sprayer");
        for (XmlReader.Element sprayer : parsed.getChildByName("zombie_sprayer").getChildrenByName("zombie")) {
            sprayers.add(
                    new Pair<Integer, Integer>(sprayer.getInt("activeGuns"), sprayer.getInt("time"))
            );
        }

        colors = parsed.getInt("colors");
        superBulletPercent = parsed.getInt("super_bullet_percent");
        bombPercent = parsed.getInt("bomb_percent");
        freezeBombPercent = parsed.getInt("freeze_bomb_percent");
        colorBombPercent = parsed.getInt("color_bomb_percent");

        basicScores = parsed.getInt("basic_scores");
        basicComboBonus = parsed.getInt("basic_combo_bonus");
        uberScores = parsed.getInt("uber_scores");
        steamScores = parsed.getInt("steam_scores");

        //save prefabs arrays
        prefabData = new PrefabData();
        try {
            for (XmlReader.Element array: parsed.getChildrenByName("Arrays")) {
                ArrayList<String> coords = new ArrayList<String>();
                for (XmlReader.Element dot: array.getChildrenByName("dot")) {
                    coords.add(dot.getAttribute("position"));
                }
                prefabData.addArrFromCoordArr(coords);
            }
        } catch (PrefabException e) {
            e.printStackTrace();
        }

        //save prefabs patterns
        patternData = new PatternData();
        for (XmlReader.Element pattern: parsed.getChildByName("Patterns").getChildrenByName("pattern")) {
            patternData.addPattern(
                    pattern.getIntAttribute("firstMaxIndex"),
                    pattern.getAttribute("prefabTypes"),
                    pattern.getAttribute("probability"),
                    pattern.getIntAttribute("count"),
                    pattern.getIntAttribute("minDistance"),
                    pattern.getBooleanAttribute("canOverlay")
            );
        }

        //save popups
        XmlReader.Element popUps = parsed.getChildByName("Pop_ups");
        for (XmlReader.Element popup: popUps.getChildrenByName("Pop_up")) {
            popupsVector.add(popup.getText());
        }
        popupSpawnPeriod = popUps.getIntAttribute("spawnPeriod");
        popupMaxTimeOffset = popUps.getIntAttribute("maxTimeOffset");
        popupLifeTime = popUps.getIntAttribute("lifeTime");
        popupProbability = popUps.getFloatAttribute("probability");
    }
}
