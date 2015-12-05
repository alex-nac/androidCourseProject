package com.bubblezombie.game.Prefabs;

import com.bubblezombie.game.Bubbles.Bubble;
import com.bubblezombie.game.Bubbles.BubbleColor;
import com.bubblezombie.game.Bubbles.Zombie;
import com.bubblezombie.game.Util.GameConfig;
import com.bubblezombie.game.Util.Pair;

import java.util.ArrayList;

public class PrefabManager {
    private PrefabData _prefabData;
    private PatternData _patternData;
    private ArrayList<ArrayList<Bubble>> _mesh;

    private boolean _isFirstRowOffseted;

    public PrefabManager(GameConfig cfg) {
        this._prefabData = cfg.prefabData;
        this._patternData = cfg.patternData;
    }

    public void init(boolean islastRowOffseted, ArrayList<ArrayList<Bubble>> mesh) {
        this._mesh = mesh;

        _isFirstRowOffseted = mesh.size() % 2 == 1 ? islastRowOffseted : !islastRowOffseted;
    }

    public void applyPattern(int rowNum) {
        Pattern currPattern = _patternData.getRandomPattern();
        if (currPattern == null) {
            return;
        }
        int colNum = (int)Math.round(Math.random() * currPattern.firstMaxIndex);
        int prefabCount = 0;

        //applying pattern to the row until we get out of bounds or get out of max prefabs count
        while (colNum < _mesh.get(0).size() && prefabCount < currPattern.count) {
            Pair<Integer, Integer> typeAndProb = currPattern.GetNextPrefabTypeAndProb();
            if (Math.random() > typeAndProb.second) {
                //set next col
                colNum += currPattern.minDistance;
                continue;
            }

            ArrayList<Pair<Integer, Integer>> prefab = _prefabData.getPrefab(typeAndProb.first);

            //checking if prefab can be applied
            boolean canBeApplied = true;
            for (Pair<Integer, Integer> offS : prefab)
            if (!isOverlayable(getDot(rowNum, colNum, offS))) {
                canBeApplied = false;
                break;
            }

            //and if we can - we put it
            if (canBeApplied) {
                prefabCount++;
                for (Pair<Integer, Integer> offS : prefab) {
                    Pair<Integer, Integer> pos = getDot(rowNum, colNum, offS);
                    if (at(pos.first, pos.second)) {
                        ((Zombie)_mesh.get(pos.first).get(pos.second)).color = BubbleColor.UBER_BLACK;
                        ((Zombie)_mesh.get(pos.first).get(pos.second)).canOverlay = currPattern.canOverlay;
                    }
                }
            }
            //set next col
            colNum += currPattern.minDistance;

        }

    }

    /**
     * @param row row of bubble
     * @param col column of bubble
     * @return true, if bubble exist or not
     */
    private boolean at(int row, int col) {
        return row >= 0 && row < this._mesh.size() && col >= 0 && col < this._mesh.get(0).size();
    }

    /**
     * Getting appropriate dot with offset
     * @param row needed row
     * @param col needed column
     * @param offset
     * @return
     */
    private Pair<Integer, Integer> getDot(int row, int col, Pair<Integer, Integer> offset) {
        boolean isCurrRowOffseted = row % 2 == 0 ? _isFirstRowOffseted : !_isFirstRowOffseted;

        int delta = 0;
        //if we have changed row's offset
        if (Math.abs(offset.first % 2) == 1) {
            if (!isCurrRowOffseted && offset.second > 0) {
                delta = -1;
            } else if (isCurrRowOffseted && offset.second < 0) {
                delta = 1;
            }
        }
        return new Pair<Integer, Integer>(row + offset.first, col + offset.second + delta);
    }

    /**
     * Checking if we can put prefab at pos
     * @param pos position to put prefab
     * @return
     */
    private boolean isOverlayable(Pair<Integer, Integer> pos) {
        return !at(pos.first, pos.second) || ((Zombie) _mesh.get(pos.first).get(pos.second)).canOverlay;
    }

}