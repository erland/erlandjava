package erland.game;

import erland.game.ScreenHandlerInterface;
import erland.util.ImageHandlerInterface;
import erland.util.ImageCreatorInterface;

public interface GamePanel {
    boolean isExit();

    void exit();

    void init(GameEnvironmentInterface environment);

    void update();

    void draw();
}
