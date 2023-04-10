package sk.kasci.sokoban;

import sk.kasci.sokoban.objects.Map;
import sk.kasci.sokoban.objects.MapObject;

import java.util.ArrayList;

public class Game {

    private ArrayList<Map> maps;

    public Game(ArrayList<Map> maps) {
        this.maps = maps;
    }

    public Map getMap(int index) {
        if (index > maps.size()) throw new RuntimeException("Incorrect index of map");
        return maps.get(index);
    }

}
