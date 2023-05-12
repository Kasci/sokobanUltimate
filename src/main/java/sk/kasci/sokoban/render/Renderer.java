package sk.kasci.sokoban.render;

import sk.kasci.sokoban.Game;

public interface Renderer {

    void init();
    void deinit();
    void render(Game game);
    void clear();

}
