package erland.game.isoadventure;
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

import erland.game.GameEnvironmentInterface;
import erland.game.BlockContainerInterface;

public class IsoAdventureModelStandalone implements IsoAdventureModelInterface {
    /** Handling of player */
    private IsoAdventurePlayer player;
    /** Model object */
    private IsoAdventureModel model;
    /** Game environment */
    private GameEnvironmentInterface environment;
    /** Block container */
    private IrregularBlockContainerInterface cont;
    /** Indicates if game is initialized */
    private boolean bInitialized;
    /** Indicates if game is started */
    private boolean bStarted;
    /** List of all player objects */
    private IsoObjectInterface[] playerObjects;

    public void startMoveLeft() {
        player.startMoveLeft();
    }

    public void startMoveRight() {
        player.startMoveRight();
    }

    public void startMoveUp() {
        player.startMoveUp();
    }

    public void startMoveDown() {
        player.startMoveDown();
    }

    public void stopMoveLeft() {
        player.stopMoveLeft();
    }

    public void stopMoveRight() {
        player.stopMoveRight();
    }

    public void stopMoveUp() {
        player.stopMoveUp();
    }

    public void stopMoveDown() {
        player.stopMoveDown();
    }

    public void jump() {
        player.jump();
    }

    public void update() {
        if(isStarted()) {
            player.update();
            model.update();
        }
    }
    private IsoObjectInterface createPlayer() {
        IsoBitmapObjectMovable obj = new IsoObjectLiving();
        obj.init(environment);
        obj.setContainer(cont);
        obj.setImage("ground.gif",1,2);
        return obj;
    }
    private IsoObjectInterface[] createPlayers() {
        return new IsoObjectInterface[] {createPlayer()};
    }
    public void init(GameEnvironmentInterface environment, IrregularBlockContainerInterface cont) {
        this.environment = environment;
        this.cont = cont;

        model = new IsoAdventureModel();
        model.init(environment,cont);

        player = new IsoAdventurePlayer(environment,0);
        playerObjects = createPlayers();
        player.setPlayerObject(playerObjects[0]);
        model.initPlayers(playerObjects);

        bInitialized = true;
    }
    public boolean isInitialized() {
        return bInitialized;
    }

    public void start() {
        if(!isStarted()) {
            bStarted = true;
        }
    }

    public MapDrawInterface getMap() {
        return model.getMap();
    }

    public boolean isEnd() {
        return false;
    }

    public boolean isGameOver() {
        return false;
    }

    public boolean isCompleted() {
        return false;
    }

    public boolean isStarted() {
        return bStarted;
    }
    public boolean isMultiplayer() {
        return false;
    }
    public String getInfoString() {
        return "";
    }
    public void setCheatModeParameter(String parameter, String value) {
        return;
    }

    public int getNoOfHumanPlayers() {
        return 1;
    }

    public IsoObjectInterface getPlayerObject() {
        return player.getPlayerObject();
    }
}
