package com.bubblezombie.game;

import com.badlogic.gdx.Gdx;
import com.bubblezombie.game.Bubbles.Bubble;
import com.bubblezombie.game.Enums.BubbleColor;
import com.bubblezombie.game.Bubbles.SimpleBubble;
import com.bubblezombie.game.Bubbles.Sprayer;
import com.bubblezombie.game.Bubbles.Zombie;
import com.bubblezombie.game.Prefabs.PrefabManager;
import com.bubblezombie.game.Util.GameConfig;
import com.bubblezombie.game.Util.Generator;
import com.bubblezombie.game.Util.Pair;

import java.util.ArrayList;

public class MeshPattern {

    private ArrayList<ArrayList<Bubble>> _pattern = new ArrayList<ArrayList<Bubble>>();
    private PrefabManager _prefabManager;
    private int _startRowsNum;
    private int _colNum;
    private int _rowsNum;
    private double _waveVel;
    private int _uberZombieAmount;
    private ArrayList<Pair<Integer, Integer>> _sprayers;
    private double _frozenTime;

    public int getColumsNum() {
        return _colNum;
    }
    public int getRowsNum() {
        return _rowsNum;
    }
    public int getStartRowsNum() {
        return _startRowsNum;
    }
    public double getWaveVel() {
        return _waveVel;
    }
    public double getFrozenTime() {
        return _frozenTime;
    }
    public boolean isLastRowOffseted() {
        return true;
    }


    public MeshPattern(GameConfig cfg) {
        // saving _mesh parameters
        _startRowsNum = cfg.rowsShowed;
        _colNum = cfg.columnsNum;
        _rowsNum = cfg.rowsNum;
        _waveVel = cfg.waveVelocity;
        _uberZombieAmount = cfg.uberZombieAmount;
        _sprayers = cfg.sprayers;
        _frozenTime = cfg.frozenTime;

        _prefabManager = new PrefabManager(cfg);

        // create _mesh _pattern
        createMeshPattern();
    }


    /**
     * @return next bubble row from _pattern
     */
    public ArrayList<Bubble> getNextRow() {
        if (_pattern.size() != 0) {
            return _pattern.remove(_pattern.size() - 1);
        } else {
            Gdx.app.log("MeshPattern", "NO MORE ROWS!!");
            return null;
        }
    }

    /**
     * get all zombies that we have in _pattern at the moment
     * we use this function to stop their animation
     */
    public ArrayList<Zombie> getRemainingZombies() {
        ArrayList<Zombie> zombieVec = new ArrayList<Zombie>();

        for (int i = 0; i < _pattern.size(); ++i) {
            for (int j = 0; j < _colNum; ++j) {
                if (_pattern.get(i).get(j) instanceof Zombie)
                    zombieVec.add((Zombie) _pattern.get(i).get(j));
            }
        }
        return zombieVec;
    }

    public ArrayList<Integer> getRemainingColors() {
        int[] colors = {0, 0, 0, 0, 0, 0, 0, 0};

        for (int i = 0; i < _pattern.size(); ++i) {
            for (int j = 0; j < _colNum; ++j) {
                if (_pattern.get(i).get(j) instanceof SimpleBubble) {
                    colors[((SimpleBubble) _pattern.get(i).get(j)).getBubbleColor().getIndex()]++;
                }
            }
        }
        ArrayList<Integer> arr = new ArrayList<Integer>();
        for (int col : colors) {
            arr.add(col);
        }
        return arr;
    }


    //    //filling _pattern
    private void createMeshPattern() {
        ArrayList<Bubble> allBubbles = new ArrayList<Bubble>();  //collecting all bubble's types

        //fill it with random zombie
        for (int i = 0; i < _rowsNum; ++i) {
            _pattern.add(new ArrayList<Bubble>(_colNum));
            for (int j = 0; j < _colNum; ++j) {
                _pattern.get(i).add(new Zombie());
            }
            allBubbles.addAll(new ArrayList<Bubble>(_pattern.get(i)));
        }

        //apply patterns
        _prefabManager.init(isLastRowOffseted(), _pattern);
        for (int i = 0; i < _rowsNum; i++) {
            _prefabManager.applyPattern(i);
        }

        int currentUberZombieAmount = 0;
        for (int i = 0; i < allBubbles.size(); i++) {
            if (((Zombie) allBubbles.get(i)).getBubbleColor() == BubbleColor.UBER_BLACK) {
                currentUberZombieAmount++;
                allBubbles.remove(i);
            }
        }

        //randomly choose zombie position
        for (int i = 0; i < _uberZombieAmount - currentUberZombieAmount; i++) {
            if (allBubbles.size() == 0)
                break;

            int randomInd = Generator.rand(allBubbles.size());
            ((Zombie) allBubbles.get(randomInd)).setColor(BubbleColor.UBER_BLACK);
            allBubbles.remove(randomInd);
        }


        //randomly choose sprayer position
        for (int i = 0; i < Math.min(_sprayers.size(), allBubbles.size()); i++) {

            //chose random position
            int r = Generator.rand(_rowsNum);
            int c = Generator.rand(_colNum);

            //if it isn't "special" bubble already there set sprayrer here
            if (_pattern.get(r).get(c) instanceof Zombie && ((Zombie) _pattern.get(r).get(c)).getBubbleColor() != BubbleColor.UBER_BLACK) {
                _pattern.get(r).set(c, new Sprayer(_sprayers.get(i).first, _sprayers.get(i).second));
            } else {
                i--; //else find another places
            }

        }

    }
}
