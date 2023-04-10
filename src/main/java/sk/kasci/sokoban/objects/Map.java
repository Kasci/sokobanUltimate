package sk.kasci.sokoban.objects;

import sk.kasci.sokoban.objects.mapActors.Player;
import sk.kasci.sokoban.objects.mapActors.Box;
import sk.kasci.sokoban.objects.mapObjects.Empty;
import sk.kasci.sokoban.objects.mapObjects.Goal;
import sk.kasci.sokoban.utils.MapFactory;

import java.util.ArrayList;

public class Map {

    public MapObject[][] map;
    public ArrayList<Box> boxes;
    public Player player;

    public Map() {
        this.boxes = new ArrayList<>();
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        MapFactory factory  = new MapFactory();
        int rows = map.length;
        int cols = map[0].length;
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                if (i == player.getX() && j == player.getY()) sb.append("@");
                else if (isBox(i,j) && map[i][j] instanceof Empty) sb.append("$");
                else if (isBox(i,j) && map[i][j] instanceof Goal) sb.append("*");
                else sb.append(factory.toMap(map[i][j]));
            }
            sb.append("\n");
        }
        return sb.toString();
    }

    private boolean isBox(int x, int y) {
        return this.boxes.stream().anyMatch(it -> it.getX() == x && it.getY() == y);
    }
}
