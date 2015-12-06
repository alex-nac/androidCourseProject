package com.bubblezombie.game.Prefabs;

import com.bubblezombie.game.Util.Generator;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class PatternData {
    private ArrayList<Pattern> _patterns = new ArrayList<Pattern>();

    public PatternData() {}

    private List<Number> getArrayFromString(String str, Class instance) {
        ArrayList<Number> res = new ArrayList<Number>();
        String[] splited = str.split("_+");
        for (String aSplited : splited) {
            if (Integer.class.isInstance(instance)) {
                res.add(Integer.parseInt(aSplited));
            } else if (Double.class.isInstance(instance)) {
                res.add(Double.parseDouble(aSplited));
            }
        }
        return res;
    }

    public void addPattern(int firstMaxIndex, String prefabTypes, String prefabProbability,
                           int count, int minDistance, boolean canOverlay) {
        Pattern pattern = new Pattern();
        pattern.firstMaxIndex = firstMaxIndex;
        pattern.setPrefabTypes(new ArrayList<Integer>(
                Arrays.asList((Integer[]) getArrayFromString(prefabTypes, Integer.TYPE).toArray())));
        pattern.setPrefabProbability(new ArrayList<Double>(
                Arrays.asList((Double[]) getArrayFromString(prefabProbability, Double.TYPE).toArray())));
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
