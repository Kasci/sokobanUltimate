package sk.kasci.sokoban.render;

import sk.kasci.sokoban.Game;

import javax.swing.*;

public class SwingRenderer implements Renderer{

    private JFrame frame;
    private JPanel panel;

    public SwingRenderer() {
        frame = new JFrame();
        frame.setSize(640, 640);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

        panel = new JPanel();
        frame.add(panel);
    }

    @Override
    public void init() {

    }

    @Override
    public void deinit() {
        
    }

    @Override
    public void render(Game game) {

    }

    @Override
    public void clear() {

    }
}
