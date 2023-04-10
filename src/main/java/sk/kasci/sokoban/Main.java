package sk.kasci.sokoban;

import sk.kasci.sokoban.objects.Map;
import sk.kasci.sokoban.utils.LevelLoader;

public class Main {
    public static void main(String[] args) {
        LevelLoader loader = new LevelLoader();
        Game game = new Game(loader.loadLevels("levels.txt"));
        game.start();
    }
}
