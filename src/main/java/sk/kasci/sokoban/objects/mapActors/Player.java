package sk.kasci.sokoban.objects.mapActors;

import sk.kasci.sokoban.objects.MapActor;

public class Player extends MapActor {

    private int steps = 0;
    public Player(int x, int y) {
        super(x,y);
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
}
