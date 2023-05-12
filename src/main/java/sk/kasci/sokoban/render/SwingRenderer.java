package sk.kasci.sokoban.render;

import sk.kasci.sokoban.Game;
import sk.kasci.sokoban.objects.Map;
import sk.kasci.sokoban.objects.MapObject;
import sk.kasci.sokoban.objects.mapActors.Box;
import sk.kasci.sokoban.objects.mapActors.Player;
import sk.kasci.sokoban.objects.mapObjects.Empty;
import sk.kasci.sokoban.objects.mapObjects.Goal;
import sk.kasci.sokoban.objects.mapObjects.Wall;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

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
        /**
         * Tento image bude sluzit ako buffer, ak by sme ho odstranili a priamo kreslili na obrazovku tak cele to bude blikat
         * */
        BufferedImage bi = new BufferedImage(640, 640, BufferedImage.TYPE_INT_RGB);
        Graphics2D gr = (Graphics2D) bi.getGraphics();
        Map map = game.getActiveMap();
        int size = 32;
        int xOff = 0;
        int yOff = 0;
        for (int y = 0; y < map.getHeight(); y++) {
            for (int x = 0; x < map.getWidth(); x++) {
                renderObject(gr, map, getColor(map.getMapObject(x,y)), x, y, size, xOff, yOff);

            }
        }
        for (Box b: map.getBoxes()) {
            renderObject(gr, map, Color.ORANGE, b.getX(), b.getY(), size, xOff, yOff);

        }
        Player player = map.getPlayer();
        renderObject(gr, map, Color.WHITE, player.getX(), player.getY(), size, xOff, yOff);

        g.drawImage(bi, 0, 0, canvas);
    }

    private void renderObject(Graphics2D gr, Map map, Color color, int x, int y, int size, int xOff, int yOff) {
        gr.setColor(color);
        gr.fillRect(x* size + xOff, y* size + yOff, size, size);
        MapObject mapObject = map.getMapObject(x, y);
        if (mapObject instanceof Goal) {
            gr.setColor(getColor(mapObject));
            gr.fillRect(x* size + xOff +5, y* size + yOff +5, size -10, size -10);
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

    public JFrame getFrame() {
        return frame;
    }
}
