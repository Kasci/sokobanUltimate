package sk.kasci.sokoban;

import com.googlecode.lanterna.SGR;
import com.googlecode.lanterna.TerminalSize;
import com.googlecode.lanterna.TextCharacter;
import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.gui2.*;
import com.googlecode.lanterna.screen.Screen;
import com.googlecode.lanterna.screen.TerminalScreen;
import com.googlecode.lanterna.screen.VirtualScreen;
import com.googlecode.lanterna.terminal.DefaultTerminalFactory;
import com.googlecode.lanterna.terminal.Terminal;
import sk.kasci.sokoban.utils.LevelLoader;

import java.io.IOException;
import java.util.EnumSet;

public class Main {
    public static void main(String[] args) throws IOException {
        LevelLoader loader = new LevelLoader();
        Game game = new Game(loader.loadLevels("levels.txt"));

        game.start();

    }
}
