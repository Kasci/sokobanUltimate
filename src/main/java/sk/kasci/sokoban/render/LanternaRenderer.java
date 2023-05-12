package sk.kasci.sokoban.render;

import com.googlecode.lanterna.TextCharacter;
import com.googlecode.lanterna.screen.Screen;
import com.googlecode.lanterna.screen.TerminalScreen;
import com.googlecode.lanterna.screen.VirtualScreen;
import com.googlecode.lanterna.terminal.DefaultTerminalFactory;
import com.googlecode.lanterna.terminal.Terminal;
import sk.kasci.sokoban.Game;
import sk.kasci.sokoban.input.LanternaInput;
import sk.kasci.sokoban.objects.mapObjects.Goal;

import java.io.IOException;
import java.util.List;

public class LanternaRenderer implements Renderer{

    private Screen screen;

    public LanternaRenderer() {
        try {
            Terminal terminal = new DefaultTerminalFactory().createTerminal();
            screen = new TerminalScreen(terminal);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void init() {
        try {
            screen.startScreen();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void deinit() {
        try {
            screen.stopScreen();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void render(Game game) {
        renderMap(game);
        renderUI(game);
        try {
            this.screen.refresh();
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("It is not possible to render.");
        }
    }

    @Override
    public void clear() {
        this.screen.clear();
    }

    /**
     * renders UI
     */
    private void renderUI(Game game) {
        /* renders number of steps */
        int steps = game.getActiveMap().getPlayer().getSteps();
        TextCharacter[] textCharactersSteps = TextCharacter.fromString("Steps: " + Integer.toString(steps));
        for (int i = 0; i < textCharactersSteps.length; i++)
            this.screen.setCharacter(2+i,2,  textCharactersSteps[i]);

        /* render numeber of boxes on goals with total of boxes */
        long boxesOnGoal = game.getActiveMap().getBoxes().stream().filter(it -> game.getActiveMap().getMapObject(it.getX(), it.getY()) instanceof Goal).count();
        TextCharacter[] textCharactersBoxes = TextCharacter.fromString("Score: " + Long.toString(boxesOnGoal) + "/" + Integer.toString(game.getActiveMap().getBoxes().size()));
        for (int i = 0; i < textCharactersBoxes.length; i++)
            this.screen.setCharacter(15+i,2,  textCharactersBoxes[i]);

        /* if level is finished, renders message */
        if (game.isLevelFinished()) {
            TextCharacter[] textCharactersSuccess = TextCharacter.fromString("Level completed, Press N to continue.");
            for (int i = 0; i < textCharactersSuccess.length; i++)
                this.screen.setCharacter(2+i, 3, textCharactersSuccess[i]);
        }

    }

    /**
     * renders current status of the map
     */
    private void renderMap(Game game) {
        List<String> list = game.getActiveMap().toList();
        int xOff = 5;
        int yOff = 5;
        for (int y = 0; y < list.size(); y++) {
            for (int x = 0; x < list.get(y).length(); x++) {
                this.screen.setCharacter(x+xOff, y+yOff, new TextCharacter(list.get(y).charAt(x)));
            }
        }
    }

    public Screen getScreen() {
        return this.screen;
    }

}
