package sk.kasci.sokoban;

import com.googlecode.lanterna.TextCharacter;
import com.googlecode.lanterna.screen.Screen;
import sk.kasci.sokoban.input.ConsoleInput;
import sk.kasci.sokoban.input.InputValue;
import sk.kasci.sokoban.input.WindowInput;
import sk.kasci.sokoban.objects.Map;
import sk.kasci.sokoban.objects.MapObject;
import sk.kasci.sokoban.objects.mapActors.Box;
import sk.kasci.sokoban.objects.mapObjects.Goal;
import sk.kasci.sokoban.objects.mapObjects.Wall;

import java.io.IOException;
import java.rmi.UnexpectedException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class Game {

    private Screen screen;

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

    public void start(Screen screen) {
        this.running = true;
        this.activeMap = maps.get(0);
        this.screen = screen;
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
        return WindowInput.getInput(this.screen);
    }

    private void render() {
        renderMap();
        renderUI();
        try {
            this.screen.refresh();
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("It is not possible to render.");
        }
    }

    private void renderUI() {
        int steps = this.activeMap.player.getSteps();
        TextCharacter[] textCharactersSteps = TextCharacter.fromString("Steps: " + Integer.toString(steps));
        for (int i = 0; i < textCharactersSteps.length; i++)
            this.screen.setCharacter(2+i,2,  textCharactersSteps[i]);

        long boxesOnGoal = this.activeMap.boxes.stream().filter(it -> this.activeMap.map[it.getX()][it.getY()] instanceof Goal).count();
        TextCharacter[] textCharactersBoxes = TextCharacter.fromString("Score: " + Long.toString(boxesOnGoal) + "/" + Integer.toString(this.activeMap.boxes.size()));
        for (int i = 0; i < textCharactersBoxes.length; i++)
            this.screen.setCharacter(15+i,2,  textCharactersBoxes[i]);
    }

    private void renderMap() {
        List<String> list = activeMap.toList();
        int xOff = 5;
        int yOff = 5;
        for (int y = 0; y < list.size(); y++) {
            for (int x = 0; x < list.get(y).length(); x++) {
                this.screen.setCharacter(x+xOff, y+yOff, new TextCharacter(list.get(y).charAt(x)));
            }
        }
    }
}
