package com.bubblezombie.game.Prefabs;

import com.bubblezombie.game.Util.Generator;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class PatternData {
    private ArrayList<Pattern> _patterns = new ArrayList<Pattern>();

    public PatternData() {}

    private Number[] getArrayFromString(String str, boolean prob) {
        String[] splited = str.split("_+");
        Number[] parsed = null;
        // TODO: это говнокод и копипаста
        if (!prob) {
            parsed = new Integer[splited.length];
        } else {
            parsed = new Double[splited.length];
        }
        for (int i = 0; i < splited.length; ++i) {
            if (!prob) {
                parsed[i] = Integer.parseInt(splited[i]);
            } else {
                parsed[i] = Double.parseDouble(splited[i]);
            }
        }
        return parsed;
    }

    public void addPattern(int firstMaxIndex, String prefabTypes, String prefabProbability,
                           int count, int minDistance, boolean canOverlay) {
        Pattern pattern = new Pattern();
        pattern.firstMaxIndex = firstMaxIndex;
        pattern.setPrefabTypes(new ArrayList<Integer>(
                Arrays.asList((Integer[]) getArrayFromString(prefabTypes, false))));
        pattern.setPrefabProbability(new ArrayList<Double>(
                Arrays.asList((Double[]) getArrayFromString(prefabProbability, true))));
        pattern.count = count;
        pattern.minDistance = minDistance;
        pattern.canOverlay = canOverlay;

        _patterns.add(pattern);
    }

    public Pattern getRandomPattern() {
        if (_patterns.size() == 0) {
            return null;
        }
        int ind = Generator.rand(_patterns.size());
        return _patterns.get(ind);
    }

}
