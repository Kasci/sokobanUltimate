package sk.kasci.sokoban.render;

import com.googlecode.lanterna.TextCharacter;
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
        frame.setSize(1280, 1280);
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
        frame.dispose();
    }

    @Override
    public void render(Game game) {
        /**
         * Tento image bude sluzit ako buffer, ak by sme ho odstranili a priamo kreslili na obrazovku tak cele to bude blikat
         * */
        BufferedImage bi = new BufferedImage(1280, 1280, BufferedImage.TYPE_INT_RGB);
        Graphics2D gr = (Graphics2D) bi.getGraphics();
        Map map = game.getActiveMap();
        int size = 64;
        int xOff = 0;
        int yOff = 64;
        for (int y = 0; y < map.getHeight(); y++) {
            for (int x = 0; x < map.getWidth(); x++) {
                renderTexture(gr, map, getTexture(map.getMapObject(x,y)), x, y, size, xOff, yOff);
            }
        }
        for (Box b: map.getBoxes()) {
            MapObject mapObject = map.getMapObject(b.getX(), b.getY());
            if (mapObject instanceof Goal) {
                renderTexture(gr, map, Textures.BOX_ON_GOAL, b.getX(), b.getY(), size, xOff, yOff);
            } else {
                renderTexture(gr, map, Textures.BOX, b.getX(), b.getY(), size, xOff - b.decDX(), yOff - b.decDY());
            }
        }
        Player player = map.getPlayer();
        renderTexture(gr, map, getPlayerTexture(player, Math.abs(player.getDX()+player.getDY())), player.getX(), player.getY(), size, xOff - player.decDX(), yOff - player.decDY());

        renderUI(gr, game);

        g.drawImage(bi, 0, 0, canvas);
    }

    private BufferedImage getPlayerTexture(Player player, int idx) {
        int[] i = new int[] {1,0,2,0};
        switch (player.getFacing()) {
            case UP: return Textures.PLAYER_UP[i[idx/16%4]];
            case LEFT: return Textures.PLAYER_LEFT[i[idx/16%4]];
            case DOWN: return Textures.PLAYER_DOWN[i[idx/16%4]];
            case RIGHT: return Textures.PLAYER_RIGHT[i[idx/16%4]];
        }
        throw new RuntimeException("Unsupported direction");
    }

    private void renderUI(Graphics2D gr, Game game) {
        Font font = gr.getFont().deriveFont(48f);
        gr.setFont(font);
        gr.drawString("Steps: " + game.getActiveMap().getPlayer().getSteps(), 10,64);
        long boxesOnGoal = game.getActiveMap().getBoxes().stream().filter(it -> game.getActiveMap().getMapObject(it.getX(), it.getY()) instanceof Goal).count();
        gr.drawString("Score: " + Long.toString(boxesOnGoal) + "/" + Integer.toString(game.getActiveMap().getBoxes().size()),  1000,64);

        if (game.isLevelFinished()) {
            gr.drawString("Level completed, Press N to continue.",150,64);
        }
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

    private void renderTexture(Graphics2D gr, Map map, BufferedImage image, int x, int y, int size, int xOff, int yOff) {
        gr.drawImage(image, x* size + xOff, y* size + yOff, canvas);
        MapObject mapObject = map.getMapObject(x, y);
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

    private BufferedImage getTexture(MapObject mapObject) {
        if (mapObject instanceof Empty) {
            return new BufferedImage(64,64,BufferedImage.TYPE_INT_RGB);
        } else if (mapObject instanceof Goal) {
            return Textures.GOAL;
        } else if (mapObject instanceof Wall) {
            return Textures.WALL;
        }
        throw new RuntimeException("There is no such Map Object defined");
    }

    @Override
    public void clear() {

    }

    public JFrame getFrame() {
        return frame;
    }
}
