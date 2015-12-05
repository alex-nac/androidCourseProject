package com.bubblezombie.game.Prefabs;

import com.bubblezombie.game.Util.Pair;

import java.util.ArrayList;

public class Pattern {
    ArrayList<Integer> prefabTypes = new ArrayList<Integer>();
    ArrayList<Integer> prefabProbability = new ArrayList<Integer>();

    public int count;
    public int minDistance;
    public boolean canOverlay;
    public int firstMaxIndex;

    private int lastPrefabUsed = -1;

    public void setPrefabTypes(ArrayList<Integer> value) {
        this.prefabTypes = value;
    }
    public void setPrefabProbability(ArrayList<Integer> value) {
        this.prefabProbability = value;
    }

    public Pair<Integer, Integer> GetNextPrefabTypeAndProb() {
        lastPrefabUsed++;
        if (lastPrefabUsed == prefabTypes.size())
            lastPrefabUsed = 0;
        return new Pair<Integer, Integer>(prefabTypes.get(lastPrefabUsed), prefabProbability.get(lastPrefabUsed));
    }
}
