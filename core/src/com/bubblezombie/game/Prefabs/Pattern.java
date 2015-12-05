package com.bubblezombie.game.Prefabs;

import com.bubblezombie.game.Util.Pair;

import java.util.ArrayList;

public class Pattern {

    // prefab types and prefab probability should be packege-protected because they can be assigned in other classes in package
    private ArrayList<Integer> _prefabTypes = new ArrayList<Integer>();
    private ArrayList<Integer> _prefabProbability = new ArrayList<Integer>();

    public int count;
    public int minDistance;
    public boolean canOverlay;
    public int firstMaxIndex;

    private int _lastPrefabUsed = -1;

    public void setPrefabTypes(ArrayList<Integer> value) {
        this._prefabTypes = value;
    }
    public void setPrefabProbability(ArrayList<Integer> value) {
        this._prefabProbability = value;
    }

    public Pair<Integer, Integer> GetNextPrefabTypeAndProb() {
        _lastPrefabUsed++;
        if (_lastPrefabUsed == _prefabTypes.size())
            _lastPrefabUsed = 0;
        return new Pair<Integer, Integer>(_prefabTypes.get(_lastPrefabUsed), _prefabProbability.get(_lastPrefabUsed));
    }
}
