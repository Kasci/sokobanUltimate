package sk.kasci.sokoban.utils;

import sk.kasci.sokoban.objects.MapObject;
import sk.kasci.sokoban.objects.mapObjects.Empty;
import sk.kasci.sokoban.objects.mapObjects.Goal;
import sk.kasci.sokoban.objects.mapObjects.Wall;

public class MapFactory {
    /**
     * Method used to convert characters from input file to map object
     * @param c character from file
     * @return object of Map
     */
    public MapObject fromFile(char c) {
        switch (c) {
            case '#': return new Wall();
            case '.': return new Goal();
            default: return new Empty();
        }
    }
    /**
     * Method used to convert map objects to characters used for rendering
     * @param map object of Map
     * @return character from file
     */
    public char toMap(MapObject map) {
        if (map instanceof Wall) return '#';
        if (map instanceof Goal) return '.';
        return ' ';
    }
}
