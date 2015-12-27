package com.bubblezombie.game.Util;

import com.badlogic.gdx.math.Vector2;
import com.bubblezombie.game.BubbleMesh;
import com.bubblezombie.game.Bubbles.Bubble;
import com.bubblezombie.game.Bubbles.BubbleColor;
import com.bubblezombie.game.Bubbles.SimpleBubble;

import java.util.ArrayList;

/* Class helps us with bsf in bubble _mesh
* it can find if the bubble connected with
* _mesh root or find all the bubbles with the same
* type around current
*/
public class BFS {


    private static BubbleMesh _mesh;

    public static void setMesh(BubbleMesh mesh){
        _mesh = mesh;
    }

    /**
     * Get all the bubbles with the same color
     * for this we use bfs. If withZombie parametr
     * is true then we include zombie bubbles
     * with the same color
     * @param meshPos root of the bfs
     * @param withZombie
     * @return array with adjusting bubbles with same color
     */
        public static ArrayList<Bubble> getSameColorBubbles(Vector2 meshPos, boolean withZombie /*= false*/) {
            ArrayList<Bubble> bubbles = new ArrayList<Bubble>();     //bubbles for combo
            ArrayList<ArrayList<Boolean>> boolMesh = getBoolMesh();  //used edges
            ArrayList<Vector2> queue = new ArrayList<Vector2>();

            //first edge
            queue.add(meshPos);
            boolMesh.get((int)meshPos.x).set((int)meshPos.y, true);
            BubbleColor color = ((SimpleBubble)_mesh.at(meshPos.x, meshPos.y)).getColor();
            //bfs
            while(queue.size() != 0) {
                Vector2 v = queue.remove(0);
                Bubble bbl = _mesh.at(v.x, v.y);
                if (!withZombie && ((SimpleBubble)bbl).getColor() == BubbleColor.UBER_BLACK) {
                    continue; //if it is zombie and we don't want zombie go to the next
                }
                bubbles.add(_mesh.at(v.x, v.y));
                if (((SimpleBubble)bbl).getColor() == BubbleColor.UBER_BLACK) {
                    continue; //if it is zombie then we stop wave
                }
                for (Bubble bubble : _mesh.getBubblesAround(_mesh.at(v.x, v.y))) {
                    if (!(bubble instanceof SimpleBubble)) continue;
                    Vector2 point = bubble.getMeshPosition();
                    if (!boolMesh.get((int)point.x).get((int)point.y) && ( ((SimpleBubble)_mesh.at(point.x, point.y)).getColor() == color
                            || ((SimpleBubble)_mesh.at(point.x, point.y)).getColor() == BubbleColor.UBER_BLACK)) {
                        queue.add(point);
                        boolMesh.get((int)point.x).set((int)point.y,true);
                    }
                }
            }
            return bubbles;
        }

    /**
     * overload for default value
     * @param meshPos root of the bfs
     * @return array with adjusting bubbles with same color
     */
    public static ArrayList<Bubble> getSameColorBubbles(Vector2 meshPos) {
        return getSameColorBubbles(meshPos, false);
    }

    /**
     * doing bfs from all the bubbles in 1-st row
     * and returning the bubbles that isn't connected to the root
     * @return ArrayList of unrooted bubbles
     */
    public static ArrayList<Bubble> getUnrootedBubbles() {
        ArrayList<Bubble> bubbles = new ArrayList<Bubble>();     //bubbles for combo
        ArrayList<ArrayList<Boolean>> boolMesh = getBoolMesh();  //used edges

        for (int j = 0; j < _mesh.getColumnsNum(); j++) {
            if (_mesh.at(0, j) == null || boolMesh.get(0).get(j)) {
                continue;
            }

            ArrayList<Vector2> queue = new ArrayList<Vector2>();
            queue.add(new Vector2(0, j));
            boolMesh.get(0).set(j, true);

            while(queue.size() != 0) {
                Vector2 v = queue.remove(0);
                for (Bubble bubble : _mesh.getBubblesAround(_mesh.at((int)v.x, (int)v.y))) {
                    Vector2 point = bubble.getMeshPosition();
                    if (!boolMesh.get((int)point.x).get((int)point.y)) {
                        queue.add(point);
                        boolMesh.get((int)point.x).set((int)point.y, true);
                    }
                }
            }
        }

        for (int i = 0 ; i < boolMesh.size(); i++) {
            for (int j = 0; j < boolMesh.get(i).size(); j++) {
                if (!boolMesh.get(i).get(j) && _mesh.at(i, j) != null) {
                    bubbles.add(_mesh.at(i, j));
                }
            }
        }
        return bubbles;
    }

    /**
     * @return empty _mesh that show us if we were on an edge or not
     */
        private static ArrayList<ArrayList<Boolean>> getBoolMesh() {
            ArrayList<ArrayList<Boolean>> boolMesh = new ArrayList<ArrayList<Boolean>>(_mesh.getRowsNum());
            for (int i = 0; i < boolMesh.size(); i++) {
                ArrayList<Boolean> vec = new ArrayList<Boolean>(_mesh.getColumnsNum());
                for (int j = 0; j < vec.size(); j++)
                vec.set(j, false);
                boolMesh.set(i, vec);
            }

            return boolMesh;
        }



}
