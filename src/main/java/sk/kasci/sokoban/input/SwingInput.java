package sk.kasci.sokoban.input;

import javax.swing.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class SwingInput implements Inputter {

    private InputValue lastInput = InputValue.NONE;

    public SwingInput(JFrame frame) {
        frame.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                if (e.getKeyChar() == 'w') lastInput = InputValue.UP;
                else if (e.getKeyChar() == 'a') lastInput = InputValue.LEFT;
                else if (e.getKeyChar() == 's') lastInput = InputValue.DOWN;
                else if (e.getKeyChar() == 'd') lastInput = InputValue.RIGHT;
            }
        });
    }
    @Override
    public InputValue getInput() {
        if (lastInput != InputValue.NONE) {
            InputValue ret = lastInput;
            lastInput = InputValue.NONE;
            return ret;
        }
        return InputValue.NONE;
    }
}
