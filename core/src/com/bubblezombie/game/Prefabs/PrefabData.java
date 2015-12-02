package com.bubblezombie.game.Prefabs;

import com.bubblezombie.game.Util.Pair;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PrefabData {
    //container class that keeps all the prefabs array organized

        //container for all arrays
        private ArrayList<ArrayList<Pair<Integer, Integer>>> figures = new ArrayList<ArrayList<Pair<Integer, Integer>>>();

        public  PrefabData() {}

        //adding new array from array of coords strings like "-1_2"
        public void AddArrFromCoordArr(ArrayList<String> coords) throws PrefabException {
            Pattern pattern = Pattern.compile("(\\-?\\d+)_(\\-?\\d+)");
            Matcher matcher;
            ArrayList<Pair<Integer, Integer>> coordsVec = new ArrayList<Pair<Integer, Integer>>();
            for  (String str : coords) {
                matcher = pattern.matcher(str);
                if (!matcher.find()) {
                    throw new PrefabException("Wrong prefab format");
                }
                int x = Integer.parseInt(matcher.group(1));
                int y = Integer.parseInt(matcher.group(2));
                coordsVec.add(new Pair<Integer, Integer>(x, y));
            }
            figures.add(coordsVec);
        }

        //getting array
        public ArrayList<Pair<Integer, Integer>> GetPrefab(int index) {
            return figures.get(index);
        }

}
