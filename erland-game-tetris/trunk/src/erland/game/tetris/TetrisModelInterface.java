package erland.game.tetris;

import erland.game.GameEnvironmentInterface;

public interface TetrisModelInterface {
    BlockContainerData getMainContainer();
    Block getNextBlock();
    BlockContainerData getOpponentMainContainer();
    boolean isEnd();
    boolean isStarted();
    boolean isCompleted();
    String getHighScore();
    String getScore();
    String getOpponentScore();
    String getLevel();
    String getOpponentLevel();
    boolean isMultiplayer();
    boolean isOpponentConnected();

    void init(GameEnvironmentInterface environment);
    void moveLeft();
    void moveRight();
    void rotate();
    void moveDown();
    void startGame();
    void update();
}
