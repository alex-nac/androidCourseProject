package com.bubblezombie.game.Prefabs;

import com.bubblezombie.game.Bubbles.Bubble;
import com.bubblezombie.game.Bubbles.BubbleColor;
import com.bubblezombie.game.Bubbles.BubbleType;
import com.bubblezombie.game.Bubbles.Zombie;
import com.bubblezombie.game.Util.GameConfig;
import com.bubblezombie.game.Util.Pair;

import java.util.ArrayList;

public class PrefabManager {
    private PrefabData prefabData;
    private PatternData patternData;
    private ArrayList<ArrayList<Bubble>> mesh;

    private boolean isFirstRowOffseted;

    public PrefabManager(GameConfig cfg) {
        this.prefabData = cfg.prefabData;
        this.patternData = cfg.patternData;
    }

    public void init(boolean islastRowOffseted, ArrayList<ArrayList<Bubble>> mesh) {
        this.mesh = mesh;

        isFirstRowOffseted = mesh.size() % 2 == 1 ? islastRowOffseted : !islastRowOffseted;
    }

    public void applyPattern(int rowNum) {
        Pattern currPattern = patternData.GetRandomPattern();
        if (currPattern == null) {
            return;
        }
        int colNum = (int)Math.round(Math.random() * currPattern.firstMaxIndex);
        int prefabCount = 0;

        //applying pattern to the row until we get out of bounds or get out of max prefabs count
        while (colNum < mesh.get(0).size() && prefabCount < currPattern.count) {
            Pair<Integer, Integer> typeAndProb = currPattern.GetNextPrefabTypeAndProb();
            if (Math.random() > typeAndProb.second) {
                //set next col
                colNum += currPattern.minDistance;
                continue;
            }

            ArrayList<Pair<Integer, Integer>> prefab = prefabData.GetPrefab(typeAndProb.first);

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
                        ((Zombie)mesh.get(pos.first).get(pos.second)).color = BubbleColor.UBER_BLACK;
//                        (_mesh[pos.first][pos.second] as Zombie).canOverlay = currPattern.canOverlay;
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
        return row >= 0 && row < this.mesh.size() && col >= 0 && col < this.mesh.get(0).size();
    }

    /**
     * Getting appropriate dot with offset
     * @param row
     * @param col
     * @param offset
     * @return
     */
    private Pair<Integer, Integer> getDot(int row, int col, Pair<Integer, Integer> offset) {
        boolean isCurrRowOffseted = row % 2 == 0 ? isFirstRowOffseted : !isFirstRowOffseted;

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
        return !at(pos.first, pos.second) || ((Zombie) mesh.get(pos.first).get(pos.second)).canOverlay;
    }

}