package sk.kasci.sokoban;

import sk.kasci.sokoban.input.ConsoleInput;
import sk.kasci.sokoban.input.InputValue;
import sk.kasci.sokoban.objects.Map;
import sk.kasci.sokoban.objects.MapObject;

import java.util.ArrayList;

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
            case UP: this.activeMap.player.move(0,-1); break;
            case DOWN: this.activeMap.player.move(0, 1); break;
            case LEFT: this.activeMap.player.move(-1, 0); break;
            case RIGHT: this.activeMap.player.move(1, 0); break;
            default: break;
        }
    }

    private InputValue input() {
        return ConsoleInput.getInput();
    }

    private void render() {
        System.out.println(activeMap.toString());
    }
}
