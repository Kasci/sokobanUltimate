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

    private int width;
    private int height;
    private MapObject[][] map;
    private ArrayList<Box> boxes;
    private Player player;

    public Map(int width, int height) {
        this.boxes = new ArrayList<>();
        this.map = new MapObject[height][width];
        this.width = width;
        this.height = height;
    }

    public List<String> toList() {
       return Arrays.stream(toString().split("\n")).collect(Collectors.toList());
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        MapFactory factory  = new MapFactory();
        for (int y = 0; y < this.getHeight(); y++) {
            for (int x = 0; x < this.getWidth(); x++) {
                if (x == getPlayer().getX() && y == getPlayer().getY()) {
                    if (getMapObject(x,y) instanceof Goal) sb.append("+");
                    else sb.append("@");
                }
                else if (isBox(x,y)) {
                  if (getMapObject(x,y) instanceof Empty) sb.append("o");
                  else if (getMapObject(x,y) instanceof Goal) sb.append("*");
                }
                else sb.append(factory.toMap(getMapObject(x,y)));
            }
            sb.append("\n");
        }
        return sb.toString();
    }

    private boolean isBox(int x, int y) {
        return this.getBoxes().stream().anyMatch(it -> it.getX() == x && it.getY() == y);
    }

    public MapObject getMapObject(int x, int y) {
        return this.map[y][x];
    }

    public void setMapObject(int x, int y, MapObject obj) {
        this.map[y][x] = obj;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public Player getPlayer() {
        return player;
    }

    public void createPlayer(int x, int y) {
        this.player = new Player(x, y);
    }

    public ArrayList<Box> getBoxes() {
        return boxes;
    }

    public void createBox(int x, int y) {
        this.boxes.add(new Box(x,y));
    }
}
