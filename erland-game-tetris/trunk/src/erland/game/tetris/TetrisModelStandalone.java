/**
 * Created by IntelliJ IDEA.
 * User: Erland Isaksson
 * Date: Dec 31, 2002
 * Time: 8:09:40 AM
 * To change this template use Options | File Templates.
 */
package erland.game.tetris;

import erland.game.*;

public class TetrisModelStandalone implements TetrisModelInterface {
    TetrisPlayer model;
    private boolean bStarted;
    GameEnvironmentInterface environment;
    HighScore highScore;

    public BlockContainerData getMainContainer() {
        return model.getMainContainer();
    }

    public Block getNextBlock() {
        return model.getNextBlock();
    }

    public BlockContainerData getOpponentMainContainer() {
        return null;
    }

    public boolean isEnd() {
        return model.isGameOver();
    }

    public boolean isStarted() {
        return bStarted;
    }

    public boolean isCompleted() {
        return model.isCompleted();
    }

    public String getHighScore() {
        return model.getNumberString(6,highScore.get());
    }

    public String getScore() {
        return model.getNumberString(6,model.getScore());
    }

    public String getOpponentScore() {
        return null;
    }

    public String getLevel() {
        return model.getNumberString(2,model.getLevel());
    }

    public String getOpponentLevel() {
        return null;
    }

    public boolean isMultiplayer() {
        return false;
    }

    public boolean isOpponentConnected() {
        return false;
    }

    public void init(GameEnvironmentInterface environment) {
        this.environment = environment;
        GameEnvironmentCustomizable serverEnvironment = new GameEnvironmentCustomizable(environment);
        highScore = new HighScore(environment.getStorage());
        highScore.load();
        serverEnvironment.setHighScore(highScore);
        model = new TetrisPlayer(serverEnvironment);
        bStarted = false;
    }

    public void moveLeft() {
        model.moveLeft();
    }

    public void moveRight() {
        model.moveRight();
    }

    public void rotate() {
        model.rotateRight();
    }

    public void moveDown() {
        model.moveDownBottom();
    }

    public void startGame() {
        bStarted = true;
        model.startNewGame();
    }

    public void update() {
        if(bStarted) {
            model.update();
            if(isEnd()) {
                bStarted = false;
            }
        }
    }
}
