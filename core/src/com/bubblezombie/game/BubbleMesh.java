package com.bubblezombie.game;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.bubblezombie.game.Bubbles.Bubble;
import com.bubblezombie.game.Bubbles.BubbleColor;

import java.util.ArrayList;

/**
 * Created by artem on 02.12.15.
 */
public class BubbleMesh extends Actor {
    private ArrayList<ArrayList<Bubble>> _mesh;
    private MeshPattern _meshPattern;
    private int _rowsNum;
    private ArrayList<Boolean> _offset;

    public Vector2 getMeshPos(Bubble bub) {
        return null;
    }
    public int getColumnsNum() {
        return _meshPattern.getColumsNum();
    }
    public int getRowsNum() {
        return _rowsNum;
    }
    //checking if there is bubble in (i, j) position
    public Bubble at(int i, int j) {
        if (i < 0 || j < 0 || i > _mesh.size() - 1 || j > _mesh.get(i).size() - 1)
            return null;
        else
            return _mesh.get(i).get(j);
    }
    public Bubble at(float i, float j) {
        return at((int)i, (int)j);
    }

    //getting all around bubbles
    public ArrayList<Bubble> getBubblesAround(Bubble bubble, boolean withNulls /*= false*/) {
        //if we meet place where there is no bubble and withNulls == true, we pushBack(null);

        ArrayList<Bubble> vec = new ArrayList<Bubble>();

        int i = (int)bubble.getMeshPosition().x;
        int j = (int)bubble.getMeshPosition().y;

        int[] dx = { 0, -1, -1, 0, 1,  1};
        int[] dy = {-1, -1,  0, 1, 0, -1};
        for (int k = 0; k < 6; ++k) {
            int offset = 0;
            if (dx[k] != 0)
                offset = _offset.get(i) ? 1 : 0;
            vec.add(_mesh.get(i + dx[k]).get(j + dy[k] + offset));
        }

//        if (at(i, j - 1)) vec.push(_mesh[i][j - 1]) else vec.push(null);
//        if (at(i - 1, j - 1 + _offset[i])) vec.push(_mesh[i - 1][j - 1 + _offset[i]]) else vec.push(null);
//        if (at(i - 1, j + _offset[i])) vec.push(_mesh[i - 1][j + _offset[i]]) else vec.push(null);
//        if (at(i, j + 1)) vec.push(_mesh[i][j + 1]) else vec.push(null);
//        if (at(i + 1, j + _offset[i])) vec.push(_mesh[i + 1][j + _offset[i]]) else vec.push(null);
//        if (at(i + 1, j - 1 + _offset[i])) vec.push(_mesh[i + 1][j - 1 + _offset[i]]) else vec.push(null);

        //Code HACK - dirty code, previous code in game use non-null return
        //and sprayer need null bubbles, so we add this option
        if (!withNulls)
            for (int k = 0; k < vec.size(); k++)
                if (vec.get(k) == null) {
                    vec.remove(k);
                    k--;
                }

        return vec;
    }

    public int GetRemainingBubblesByColor(BubbleColor color) { return 0; }

    public ArrayList<Bubble> getBubblesAround(Bubble bubble) {
        return getBubblesAround(bubble, false);
    }

}
