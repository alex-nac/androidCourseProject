package com.bubblezombie.game.Prefabs;

import com.bubblezombie.game.Util.Generator;

import java.util.ArrayList;
import java.util.List;

public class PatternData {
    private ArrayList<Pattern> _patterns = new ArrayList<Pattern>();

    public PatternData() {}

    private List<Integer> getArrayFromString(String str) {
        ArrayList<Integer> res = new ArrayList<Integer>();
        String[] splited = str.split("_+");
        for (String aSplited : splited) {
            res.add(Integer.parseInt(aSplited));
        }
        return res;
    }

    public void addPattern(int firstMaxIndex, String prefabTypes, String prefabProbability, int count, int minDistance, boolean canOverlay) {
        Pattern pattern = new Pattern();
        pattern.firstMaxIndex = firstMaxIndex;
        pattern.setPrefabTypes(new ArrayList<Integer>(getArrayFromString(prefabTypes)));
        // TODO: probability is real number
        pattern.setPrefabProbability(new ArrayList<Integer>(getArrayFromString(prefabProbability)));
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
