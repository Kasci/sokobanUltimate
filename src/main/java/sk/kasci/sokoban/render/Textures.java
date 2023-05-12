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
    public static BufferedImage PLAYER;

    static {
         BOX = Textures.load("box.png");
         GOAL = Textures.load("goal.png");
         BOX_ON_GOAL = Textures.load("boxOnGoal.png");
         WALL = Textures.load("wall.png");
         PLAYER = Textures.load("DOWN0.png");
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