package sk.kasci.sokoban;

import sk.kasci.sokoban.input.ConsoleInput;
import sk.kasci.sokoban.input.InputValue;
import sk.kasci.sokoban.objects.Map;
import sk.kasci.sokoban.objects.MapObject;
import sk.kasci.sokoban.objects.mapActors.Box;
import sk.kasci.sokoban.objects.mapObjects.Wall;

import java.util.ArrayList;
import java.util.Optional;

public class Game {

    private ArrayList<Map> maps;

    private boolean running = false;
    private Map activeMap;

    public Game(ArrayList<Map> maps) {
        this.maps = maps;
    }

    public Map getMap(int index) {
        if (index > maps.size()) throw new RuntimeException("Incorrect index of map");
        return maps.get(index);
    }

    private void loop() {
        while (running) {
            render();
            InputValue i = input();
            update(i);
        }
    }

    public void start() {
        this.running = true;
        this.activeMap = maps.get(0);
        loop();
    }

    private void update(InputValue inputValue) {
        switch (inputValue) {
            case QUIT: this.running = false;
            case UP: move(0,-1); break;
            case DOWN: move(0, 1); break;
            case LEFT: move(-1, 0); break;
            case RIGHT: move(1, 0); break;
            default: break;
        }
    }

    private void move(int dx, int dy) {
        if (dx > 1 || dx < -1 || dy > 1 || dy < -1) throw new RuntimeException("There is too much movement");
        int x = this.activeMap.player.getX();
        int y = this.activeMap.player.getY();
        if (x+dx > this.activeMap.sizeX || y+dy > this.activeMap.sizeY || this.activeMap.map[x+dx][y+dy] instanceof Wall) {
            return;
        }
        Optional<Box> boxOptional = this.activeMap.boxes.stream().filter(it -> it.getX() == x + dx && it.getY() == y + dy).findFirst();
        if (!boxOptional.isPresent()) {
            this.activeMap.player.move(dx, dy);
            return;
        }
        if (x+2*dx > this.activeMap.sizeX || y+2*dy > this.activeMap.sizeY || this.activeMap.map[x+2*dx][y+2*dy] instanceof Wall) {
            return;
        }
        Optional<Box> boxOptional2 = this.activeMap.boxes.stream().filter(it -> it.getX() == x + 2*dx && it.getY() == y + 2*dy).findFirst();
        if (boxOptional2.isPresent()) return;
        Box box = boxOptional.get();
        box.move(dx,dy);
        this.activeMap.player.move(dx, dy);
    }

    private InputValue input() {
        return ConsoleInput.getInput();
    }

    private void render() {
        System.out.println(activeMap.toString());
    }
}
