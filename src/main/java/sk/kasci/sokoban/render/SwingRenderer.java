package sk.kasci.sokoban.render;

import com.googlecode.lanterna.TextCharacter;
import sk.kasci.sokoban.Game;
import sk.kasci.sokoban.objects.Map;
import sk.kasci.sokoban.objects.MapObject;
import sk.kasci.sokoban.objects.mapActors.Box;
import sk.kasci.sokoban.objects.mapObjects.Empty;
import sk.kasci.sokoban.objects.mapObjects.Goal;
import sk.kasci.sokoban.objects.mapObjects.Wall;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class SwingRenderer implements Renderer{

    private JFrame frame;
    private Canvas canvas;

    private Graphics2D g;

    public SwingRenderer() {
        frame = new JFrame();
        frame.setSize(640, 640);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

        canvas = new Canvas();
        frame.add(canvas);
    }

    @Override
    public void init() {
        g = (Graphics2D) canvas.getGraphics().create();
    }

    @Override
    public void deinit() {
        g.dispose();
    }

    @Override
    public void render(Game game) {
        Map map = game.getActiveMap();
        int size = 32;
        int xOff = 0;
        int yOff = 0;
        for (int y = 0; y < map.getHeight(); y++) {
            for (int x = 0; x < map.getWidth(); x++) {
                g.setColor(getColor(map.getMapObject(x,y)));
                g.fillRect(x*size+xOff, y*size+yOff, size, size);
            }
        }
    }

    private Color getColor(MapObject mapObject) {
        if (mapObject instanceof Empty) {
            return Color.BLACK;
        } else if (mapObject instanceof Goal) {
            return Color.YELLOW;
        } else if (mapObject instanceof Wall) {
            return Color.BLUE;
        }
        return Color.RED;
    }

    @Override
    public void clear() {

    }
}
