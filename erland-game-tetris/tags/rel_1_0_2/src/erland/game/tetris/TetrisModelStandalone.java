package erland.game.tetris;
/*
 * Copyright (C) 2003 Erland Isaksson (erland_i@hotmail.com)
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 2
 * of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA  02111-1307, USA.
 *
 */

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
