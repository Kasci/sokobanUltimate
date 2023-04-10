package sk.kasci.sokoban;

import com.googlecode.lanterna.TextCharacter;
import com.googlecode.lanterna.screen.Screen;
import sk.kasci.sokoban.input.InputValue;
import sk.kasci.sokoban.input.WindowInput;
import sk.kasci.sokoban.objects.Map;
import sk.kasci.sokoban.objects.mapActors.Box;
import sk.kasci.sokoban.objects.mapObjects.Goal;
import sk.kasci.sokoban.objects.mapObjects.Wall;

import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

public class Game {

    private Screen screen;

    private LinkedList<Map> maps;

    private boolean running = false;
    private Map activeMap;

    public Game(LinkedList<Map> maps) {
        this.maps = maps;
    }

    /**
     * Game loop, that is executed from start,
     * till Q is pressed.
     */
    private void loop() {
        while (running) {
            render();
            InputValue i = input();
            update(i);
        }
    }

    /**
     * Inits the game variables, starts the game loop
     * @param screen - parameter of Laterna screen
     */
    public void start(Screen screen) {
        this.running = true;
        this.activeMap = maps.pop();
        this.screen = screen;
        loop();
    }

    /**
     * Executes the update, based on input
     * either moves player or goes to the next level or
     * quits the game
     * @param inputValue enum provided from Input
     */
    private void update(InputValue inputValue) {
        switch (inputValue) {
            case QUIT: this.running = false;
            case UP: move(0,-1); break;
            case DOWN: move(0, 1); break;
            case LEFT: move(-1, 0); break;
            case RIGHT: move(1, 0); break;
            case NEXT: {
                if (isLevelFinished()) {
                    this.screen.clear();
                    this.activeMap = maps.pop();
                }
            } break;
            default: break;
        }
    }

    /**
     * Executes move of the player, only if there is no wall,
     * no box next to the wall or another box
     * @param dx step size in x-direction, now bounded to 1 or -1
     * @param dy step size in y-direction, now bounded to 1 or -1
     */
    private void move(int dx, int dy) {
        /* if step size is bigger than 1, throw an error for now */
        if (dx > 1 || dx < -1 || dy > 1 || dy < -1) throw new RuntimeException("There is too much movement");
        int x = this.activeMap.player.getX();
        int y = this.activeMap.player.getY();
        /* if we want to step oustide of map or into the wall, do nothing */
        if (x+dx > this.activeMap.sizeX || y+dy > this.activeMap.sizeY || this.activeMap.map[x+dx][y+dy] instanceof Wall) {
            return;
        }
        /* if there is no box, make step, otherwise continue checking */
        Optional<Box> boxOptional = this.activeMap.boxes.stream().filter(it -> it.getX() == x + dx && it.getY() == y + dy).findFirst();
        if (!boxOptional.isPresent()) {
            this.activeMap.player.move(dx, dy);
            return;
        }
        /* if there is wall behind the box, do nothing */
        if (x+2*dx > this.activeMap.sizeX || y+2*dy > this.activeMap.sizeY || this.activeMap.map[x+2*dx][y+2*dy] instanceof Wall) {
            return;
        }
        /* if there is another box behind a box, do nothing*/
        Optional<Box> boxOptional2 = this.activeMap.boxes.stream().filter(it -> it.getX() == x + 2*dx && it.getY() == y + 2*dy).findFirst();
        if (boxOptional2.isPresent()) return;

        /* otherwise move box and player */
        Box box = boxOptional.get();
        box.move(dx,dy);
        this.activeMap.player.move(dx, dy);
    }

    /**
     * consumes input and converts it to enum
     * @return enum value of action
     */
    private InputValue input() {
        return WindowInput.getInput(this.screen);
    }

    /**
     * renders window
     */
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

    /**
     * renders UI
     */
    private void renderUI() {
        /* renders number of steps */
        int steps = this.activeMap.player.getSteps();
        TextCharacter[] textCharactersSteps = TextCharacter.fromString("Steps: " + Integer.toString(steps));
        for (int i = 0; i < textCharactersSteps.length; i++)
            this.screen.setCharacter(2+i,2,  textCharactersSteps[i]);

        /* render numeber of boxes on goals with total of boxes */
        long boxesOnGoal = this.activeMap.boxes.stream().filter(it -> this.activeMap.map[it.getX()][it.getY()] instanceof Goal).count();
        TextCharacter[] textCharactersBoxes = TextCharacter.fromString("Score: " + Long.toString(boxesOnGoal) + "/" + Integer.toString(this.activeMap.boxes.size()));
        for (int i = 0; i < textCharactersBoxes.length; i++)
            this.screen.setCharacter(15+i,2,  textCharactersBoxes[i]);

        /* if level is finished, renders message */
        if (isLevelFinished()) {
            TextCharacter[] textCharactersSuccess = TextCharacter.fromString("Level completed, Press N to continue.");
            for (int i = 0; i < textCharactersSuccess.length; i++)
                this.screen.setCharacter(2+i, 3, textCharactersSuccess[i]);
        }

    }

    /**
     * renders current status of the map
     */
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

    /**
     * checks if all boxes are on goals
     * @return boolean if all boxes are on goals
     */
    private boolean isLevelFinished() {
        long onGoal = this.activeMap.boxes.stream().filter(it -> this.activeMap.map[it.getX()][it.getY()] instanceof Goal).count();
        int goals = this.activeMap.boxes.size();
        return goals == onGoal;
    }
}
