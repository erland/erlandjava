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

import erland.game.GamePlayerInterface;
import erland.game.GameEnvironmentInterface;
import erland.network.NetworkConnectionInterface;

/**
 * Implementation of control of the user player
 */
public class TileAdventurePlayer implements GamePlayerInterface, ControllerInterface {
    /** Connection to client if networked game */
    private NetworkConnectionInterface connection;
    /** Indicates if the user is moving right */
    private boolean bMoveRight;
    /** Indicates if the user is moving  left */
    private boolean bMoveLeft;
    /** Indicates if the user is moving up */
    private boolean bMoveUp;
    /** Indicates if the user is moving down */
    private boolean bMoveDown;
    /** Indicates if the user should jump */
    private boolean bJump;
    /** The object that represents the player */
    private GameObject playerObject;
    /** The object that manages all actions */
    private ActionHandlerInterface actionHandler;

    /**
     * Create player object
     * @param environment Game environment
     * @param playerNo Player number (if networked game)
     */
    public TileAdventurePlayer(GameEnvironmentInterface environment, int playerNo) {
    }

    public void setConnection(NetworkConnectionInterface connection) {
        this.connection = connection;
    }
    public NetworkConnectionInterface getConnection() {
        return connection;
    }

    public void setPlayerObject(GameObject object) {
        playerObject = object;
    }
    public GameObject getPlayerObject() {
        return playerObject;
    }

    public ActionHandlerInterface getActionHandler() {
        return actionHandler;
    }

    public void setActionHandler(ActionHandlerInterface actionHandler) {
        this.actionHandler = actionHandler;
    }

    /** Start moving right */
    public void startMoveRight() {
        bMoveRight = true;
    }
    /** Start moving left */
    public void startMoveLeft() {
        bMoveLeft = true;
    }
    /** Start moving up */
    public void startMoveUp() {
        bMoveUp = true;
    }
    /** Start moving down */
    public void startMoveDown() {
        bMoveDown = true;
    }
    /** Stop moving right */
    public void stopMoveRight() {
        bMoveRight = false;
    }
    /** Stop moving left */
    public void stopMoveLeft() {
        bMoveLeft = false;
    }
    /** Stop moving up */
    public void stopMoveUp() {
        bMoveUp = false;
    }
    /** Stop moving down */
    public void stopMoveDown() {
        bMoveDown = false;
    }

    /** Jump */
    public void jump() {
        bJump = true;
    }
    public void update() {
        if(playerObject!=null) {
            if(bMoveLeft) {
                actionHandler.register(new MoveAction(this,MoveAction.WEST));
                //playerObject.action(Action.MOVE_WEST);
            }else if(bMoveRight) {
                actionHandler.register(new MoveAction(this,MoveAction.EAST));
                //playerObject.action(Action.MOVE_EAST);
            }else if(bMoveDown) {
                actionHandler.register(new MoveAction(this,MoveAction.SOUTH));
                //playerObject.action(Action.MOVE_SOUTH);
            }else if(bMoveUp) {
                actionHandler.register(new MoveAction(this,MoveAction.NORTH));
                //playerObject.action(Action.MOVE_NORTH);
            }else {
                actionHandler.register(new MoveAction(this,MoveAction.NONE));
            }
            //if(bJump) {
                //playerObject.action(Action.JUMP);
            //}
            bJump = false;
        }
    }
    /**
     * Check if player has completed game
     * @return true if game is completed
     */
    public boolean isCompleted() {
        return false;
    }

    /**
     * Check if game is over for player
     * @return true if game is over for player
     */
    public boolean isGameOver() {
        return false;
    }

    public GameObject getControlledObject() {
        return playerObject;
    }

    public void stopped(ActionInterface action) {
        //TODO: Implement
    }
}
