package com.bubblezombie.game;

import com.bubblezombie.game.Bubbles.Bubble;
import com.bubblezombie.game.Bubbles.BubbleColor;
import com.bubblezombie.game.Bubbles.SimpleBubble;
import com.bubblezombie.game.Bubbles.Sprayer;
import com.bubblezombie.game.Bubbles.Zombie;
import com.bubblezombie.game.Prefabs.PrefabManager;
import com.bubblezombie.game.Util.GameConfig;
import com.bubblezombie.game.Util.Generator;
import com.bubblezombie.game.Util.Pair;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by artem on 02.12.15.
 */
public class MeshPattern {
    private ArrayList<ArrayList<Bubble>> pattern= new ArrayList<ArrayList<Bubble>>();
    private PrefabManager prefabManager;
    private int startRowsNum;
    private int colNum;
    private int rowsNum;
    private double waveVel;
    private int uberZombieAmount;
    private boolean isLastRowOffseted;
    private ArrayList<Pair<Integer, Double>> sprayers;
    private double frozenTime;

    public int getColumsNum() {
        return colNum;
    }
    public int getRowsNum() {
        return rowsNum;
    }
    public int getStartRowsNum(){
        return startRowsNum;
    }
    public double getWaveVel() {
        return waveVel;
    }
    public double getFrozenTime() {
        return frozenTime;
    }
    public boolean isLastRowOffseted() {
        return isLastRowOffseted;
    }


    public MeshPattern(GameConfig cfg) {

//      saving mesh parametrs
        startRowsNum = cfg.rowsShowed;
        colNum = cfg.columnsNum;
        rowsNum = cfg.rowsNum;
        waveVel = cfg.waveVelocity;
        uberZombieAmount = cfg.uberZombieAmount;
        sprayers = cfg.sprayers;
        frozenTime = cfg.frozenTime;
        prefabManager = new PrefabManager(cfg);

//      create mesh pattern
        createMeshPattern();
    }

    /**
     *
     * @return next bubble row from pattern
     */
    public ArrayList<Bubble> getNextRow() {
        if (pattern.size() != 0) {
            return pattern.remove(pattern.size() - 1);
        }
        else {
//            trace("NO MORE ROWS!!");
            return null;
        }
    }

    /**
     * get all zombies that we have in pattern at the moment
     * we use this function to stop their animation
     * @return
     */
    public ArrayList<Zombie> getRemainingZombies() {
        ArrayList<Zombie> zombieVec = new ArrayList<Zombie>();

        for (int i = 0; i < pattern.size(); ++i) {
            for (int j = 0; j< colNum ; ++j) {
                if (pattern.get(i).get(j) instanceof Zombie)
                    zombieVec.add((Zombie) pattern.get(i).get(j));
            }
        }
        return zombieVec;
    }

    public ArrayList<Integer> getRemainingColors() {
        int[] colors = {0, 0, 0, 0, 0, 0, 0, 0};

        for (int i = 0; i < pattern.size(); ++i){
            for (int j = 0;j< colNum ;++j){
                if (pattern.get(i).get(j) instanceof SimpleBubble) {
                    colors[((SimpleBubble) pattern.get(i).get(j)).color.getIndex()]++;
                }
            }
        }
        ArrayList<Integer> arr = new ArrayList<Integer>();
        for (int col: colors) {
            arr.add(col);
        }
        return arr;
    }


//    //filling pattern
    private void createMeshPattern() {
        ArrayList<Bubble> allBubbles = new ArrayList<Bubble>();  //collecting all bubble's types

        //fill it with random zombie
        for (int i = 0; i < rowsNum; ++i) {
            pattern.add(new ArrayList<Bubble>(colNum));
            for (int j = 0; j < colNum; ++j) {
                pattern.get(i).set(j, new Zombie());
            }
            allBubbles.addAll(new ArrayList<Bubble>(pattern.get(i)));
        }

        //apply patterns
        prefabManager.init(isLastRowOffseted, pattern);
        for (int i = 0; i < rowsNum; i++) {
            prefabManager.applyPattern(i);
        }

        int currentUberZombieAmount = 0;
        for (int i = 0; i < allBubbles.size(); i++) {
            if (((Zombie) allBubbles.get(i)).color == BubbleColor.UBER_BLACK) {
                currentUberZombieAmount++;
                allBubbles.remove(i);
            }
        }

        //randomly choose zombie position
        for (int i = 0; i < uberZombieAmount - currentUberZombieAmount; i++) {
            if (allBubbles.size() == 0)
                break;

            int randomInd = Generator.rand(allBubbles.size());
            ((Zombie)allBubbles.get(randomInd)).color = BubbleColor.UBER_BLACK;
            allBubbles.remove(randomInd);
        }


        //randomly choose sprayer position
        for (int i = 0; i < Math.min(sprayers.size(), allBubbles.size()); i++) {

            //chose random position
            int r = Generator.rand(rowsNum);
            int c = Generator.rand(colNum);

            //if it isn't "special" bubble already there set sprayrer here
            if (pattern.get(r).get(c) instanceof Zombie && ((Zombie)pattern.get(r).get(c)).color != BubbleColor.UBER_BLACK) {
                pattern.get(r).set(c, new Sprayer(sprayers.get(i).first, sprayers.get(i).second));
            } else {
                i--; //else find another places
            }

        }

    }
}
