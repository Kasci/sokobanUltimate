package sk.kasci.sokoban.objects;

import sk.kasci.sokoban.objects.mapActors.Player;
import sk.kasci.sokoban.objects.mapActors.Box;
import sk.kasci.sokoban.objects.mapObjects.Empty;
import sk.kasci.sokoban.objects.mapObjects.Goal;
import sk.kasci.sokoban.utils.MapFactory;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class Map {

    public int sizeX, sizeY;
    public MapObject[][] map;
    public ArrayList<Box> boxes;
    public Player player;

    public Map() {
        this.boxes = new ArrayList<>();
    }

    public List<String> toList() {
       return Arrays.stream(toString().split("\n")).collect(Collectors.toList());
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        MapFactory factory  = new MapFactory();
        int rows = map.length;
        int cols = map[0].length;
        for (int j = 0; j < cols; j++) {
            for (int i = 0; i < rows; i++) {
                if (j == player.getY() && i == player.getX()) {
                    if (map[i][j] instanceof Goal) sb.append("+");
                    else sb.append("@");
                }
                else if (isBox(i,j)) {
                  if (map[i][j] instanceof Empty) sb.append("o");
                  else if (map[i][j] instanceof Goal) sb.append("*");
                }
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
