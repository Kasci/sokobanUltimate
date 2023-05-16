package sk.kasci.sokoban.render;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;

public class Textures {

    public static BufferedImage BOX;
    public static BufferedImage GOAL;
    public static BufferedImage BOX_ON_GOAL;
    public static BufferedImage WALL;
    public static BufferedImage[] PLAYER_UP, PLAYER_LEFT, PLAYER_DOWN, PLAYER_RIGHT;

    static {
         BOX = Textures.load("box.png");
         GOAL = Textures.load("goal.png");
         BOX_ON_GOAL = Textures.load("boxOnGoal.png");
         WALL = Textures.load("wall.png");
         PLAYER_UP = Textures.load("UP0.png", "UP1.png", "UP2.png");
         PLAYER_DOWN = Textures.load("DOWN0.png", "DOWN1.png", "DOWN2.png");
         PLAYER_RIGHT = Textures.load("RIGHT0.png", "RIGHT1.png", "RIGHT2.png");
         PLAYER_LEFT = Textures.load("LEFT0.png", "LEFT1.png", "LEFT2.png");
    }

    private static BufferedImage[] load(String... paths) {
        BufferedImage[] ret = new BufferedImage[paths.length];
        for (int i = 0; i < paths.length; i++) {
            ret[i] = load(paths[i]);
        }
        return ret;
    }
    private static BufferedImage load(String path) {
        URL url = Textures.class.getClassLoader().getResource("sprites/"+path);
        if (url == null) throw new RuntimeException("There is no such file");
        try {
            BufferedImage bi = ImageIO.read(url);
            return bi;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
