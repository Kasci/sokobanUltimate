package sk.kasci.sokoban.objects.mapActors;

import sk.kasci.sokoban.objects.Direction;
import sk.kasci.sokoban.objects.MapActor;

public class Player extends MapActor {

    private int steps = 0;
    private Direction facing;
    public Player(int x, int y) {
        super(x,y);
        facing = Direction.DOWN;
    }

    public int getSteps() {
        return steps;
    }

    public void addSteps(int d) {
        steps += d;
    }

    @Override
    public void move(int dx, int dy) {
        super.move(dx, dy);
        addSteps(1);
    }

    public Direction getFacing() {
        return this.facing;
    }

    public void setFacing(Direction facing) {
        this.facing = facing;
    }
}
