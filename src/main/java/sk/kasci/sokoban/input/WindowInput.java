package sk.kasci.sokoban.input;

import com.googlecode.lanterna.input.KeyStroke;
import com.googlecode.lanterna.screen.Screen;

import java.io.IOException;

import static sk.kasci.sokoban.input.InputValue.*;
import static sk.kasci.sokoban.input.InputValue.NONE;

public class WindowInput {

    public static InputValue getInput(Screen screen) {
        try {
            KeyStroke stroke = screen.readInput();
            if (stroke == null || stroke.getCharacter() == null) return NONE;
            switch (stroke.getCharacter()) {
                case 'w': return UP;
                case 's': return DOWN;
                case 'a': return LEFT;
                case 'd': return RIGHT;
                case 'q': return QUIT;
                case 'n': return NEXT;
                default: return NONE;
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
