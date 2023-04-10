package sk.kasci.sokoban.utils;

import sk.kasci.sokoban.objects.MapObject;
import sk.kasci.sokoban.objects.mapObjects.Empty;
import sk.kasci.sokoban.objects.mapObjects.Goal;
import sk.kasci.sokoban.objects.mapObjects.Wall;

public class MapFactory {

    public MapObject get(char c) {
        switch (c) {
            case '#': return new Wall();
            case '.': return new Goal();
            default: return new Empty();
        }
    }

    public char toMap(MapObject map) {
        if (map instanceof Wall) return '#';
        if (map instanceof Goal) return '.';
        return ' ';
    }
}
