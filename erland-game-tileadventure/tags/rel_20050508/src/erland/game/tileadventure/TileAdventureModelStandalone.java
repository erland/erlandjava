package erland.game.tileadventure;
/*
 * Copyright (C) 2004 Erland Isaksson (erland_i@hotmail.com)
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

public class TileAdventureModelStandalone implements TileAdventureModelInterface {
    /** Handling of player */
    private TileAdventurePlayer player;
    /** Model object */
    private TileAdventureModel model;
    /** Game environment */
    private GameEnvironmentInterface environment;
    /** Block container */
    private IrregularBlockContainerInterface cont;
    /** Indicates if game is initialized */
    private boolean bInitialized;
    /** Indicates if game is started */
    private boolean bStarted;
    /** List of all player objects */
    private GameObject[] playerObjects;

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
    private GameObject createPlayer() {
        MovableObject obj = new LivingObject();
        obj.init(environment);
        obj.setContainer(cont);
        String prefix = ((TileGameEnvironmentInterface)(environment.getCustomEnvironment())).getTileType();
        obj.setAnimation(prefix+"ground.gif",new Animation(0,new int[] {2}));
        return obj;
    }
    private GameObject[] createPlayers() {
        return new GameObject[] {createPlayer()};
    }
    public void init(GameEnvironmentInterface environment, IrregularBlockContainerInterface cont) {
        this.environment = environment;
        this.cont = cont;

        model = new TileAdventureModel();
        model.init(environment,cont);

        player = new TileAdventurePlayer(environment,0);
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
        return model.getMap(playerObjects[0]);
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

    public GameObject getPlayerObject() {
        return player.getPlayerObject();
    }
}
