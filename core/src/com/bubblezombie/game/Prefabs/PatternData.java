package com.bubblezombie.game.Prefabs;

import com.bubblezombie.game.Util.Generator;

import java.util.ArrayList;
import java.util.List;

public class PatternData {
    private ArrayList<Pattern> patterns = new ArrayList<Pattern>();

    public PatternData() {}

    private List<Integer> GetArrayFromString(String str) {
        ArrayList<Integer> res = new ArrayList<Integer>();
        String[] splited = str.split("_+");
        for (String aSplited : splited) {
            res.add(Integer.parseInt(aSplited));
        }
        return res;
    }

    public void AddPattern(int firstMaxIndex, String prefabTypes, String prefabProbability, int count, int minDistance, boolean canOverlay) {
        Pattern pattern = new Pattern();
        pattern.firstMaxIndex = firstMaxIndex;
        pattern.prefabTypes = new ArrayList<Integer>(GetArrayFromString(prefabTypes));
        pattern.prefabProbability = new ArrayList<Integer>(GetArrayFromString(prefabProbability));
        pattern.count = count;
        pattern.minDistance = minDistance;
        pattern.canOverlay = canOverlay;

        patterns.add(pattern);
    }

    public Pattern GetRandomPattern() {
        if (patterns.size() == 0) {
            return null;
        }
        int ind = Generator.rand(patterns.size());
        return patterns.get(ind);
    }

}
