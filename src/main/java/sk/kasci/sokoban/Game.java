package sk.kasci.sokoban;

import sk.kasci.sokoban.input.InputValue;
import sk.kasci.sokoban.input.Inputter;
import sk.kasci.sokoban.input.LanternaInput;
import sk.kasci.sokoban.input.SwingInput;
import sk.kasci.sokoban.objects.Direction;
import sk.kasci.sokoban.objects.Map;
import sk.kasci.sokoban.objects.mapActors.Box;
import sk.kasci.sokoban.objects.mapObjects.Goal;
import sk.kasci.sokoban.objects.mapObjects.Wall;
import sk.kasci.sokoban.render.LanternaRenderer;
import sk.kasci.sokoban.render.Renderer;
import sk.kasci.sokoban.render.SwingRenderer;

import java.util.LinkedList;
import java.util.Optional;

public class Game {

    private LinkedList<Map> maps;

    private boolean running = false;

    Renderer renderer;
    Inputter inputter;
    private Map activeMap;

    public Map getActiveMap() {
        return activeMap;
    }

    public Game(LinkedList<Map> maps) {
        this.maps = maps;

//        renderer = new LanternaRenderer();
//        inputter = new LanternaInput(((LanternaRenderer)renderer).getScreen());
        renderer = new SwingRenderer();
        inputter = new SwingInput(((SwingRenderer)renderer).getFrame());
    }

    /**
     * Inits the game variables, starts the game loop
     */
    public void start() {
        this.running = true;
        this.activeMap = maps.pop();
        renderer.init();
        loop();
        renderer.deinit();
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
     * Executes the update, based on input
     * either moves player or goes to the next level or
     * quits the game
     * @param inputValue enum provided from Input
     */
    private void update(InputValue inputValue) {
        if (this.activeMap.getPlayer().getDX() != 0 || this.activeMap.getPlayer().getDY() != 0) {
            return;
        }
        switch (inputValue) {
            case QUIT: this.running = false;
            case UP:
                move(0,-1);
                this.activeMap.getPlayer().setFacing(Direction.UP);
                break;
            case DOWN:
                move(0, 1);
                this.activeMap.getPlayer().setFacing(Direction.DOWN);
                break;
            case LEFT:
                move(-1, 0);
                this.activeMap.getPlayer().setFacing(Direction.LEFT);
                break;
            case RIGHT:
                move(1, 0);
                this.activeMap.getPlayer().setFacing(Direction.RIGHT);
                break;
            case NEXT: {
                if (isLevelFinished()) {
                    this.renderer.clear();
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
        int x = this.activeMap.getPlayer().getX();
        int y = this.activeMap.getPlayer().getY();
        /* if we want to step oustide of map or into the wall, do nothing */
        if (x+dx > this.activeMap.getWidth() || y+dy > this.activeMap.getHeight() || this.activeMap.getMapObject(x+dx,y+dy) instanceof Wall) {
            return;
        }
        /* if there is no box, make step, otherwise continue checking */
        Optional<Box> boxOptional = this.activeMap.getBoxes().stream().filter(it -> it.getX() == x + dx && it.getY() == y + dy).findFirst();
        if (!boxOptional.isPresent()) {
            this.activeMap.getPlayer().move(dx, dy);
            return;
        }
        /* if there is wall behind the box, do nothing */
        if (x+2*dx > this.activeMap.getWidth() || y+2*dy > this.activeMap.getHeight() || this.activeMap.getMapObject(x+2*dx,y+2*dy) instanceof Wall) {
            return;
        }
        /* if there is another box behind a box, do nothing*/
        Optional<Box> boxOptional2 = this.activeMap.getBoxes().stream().filter(it -> it.getX() == x + 2*dx && it.getY() == y + 2*dy).findFirst();
        if (boxOptional2.isPresent()) return;

        /* otherwise move box and player */
        Box box = boxOptional.get();
        box.move(dx,dy);
        this.activeMap.getPlayer().move(dx, dy);
    }

    /**
     * consumes input and converts it to enum
     * @return enum value of action
     */
    private InputValue input() {
        return inputter.getInput();
    }

    /**
     * renders window
     */
    private void render() {
        renderer.render(this);
    }

    /**
     * checks if all boxes are on goals
     * @return boolean if all boxes are on goals
     */
    public boolean isLevelFinished() {
        long onGoal = this.activeMap.getBoxes().stream().filter(it -> this.activeMap.getMapObject(it.getX(), it.getY()) instanceof Goal).count();
        int goals = this.activeMap.getBoxes().size();
        return goals == onGoal;
    }
}
