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

/**
 * Defines all methods needed for a game model for the game
 */
public interface TileAdventureModelInterface {
    /** Begin moving left */
    void startMoveLeft();
    /** Begin moving right */
    void startMoveRight();
    /** Begin moving up */
    void startMoveUp();
    /** Begin moving down */
    void startMoveDown();
    /** Stop moveing left */
    void stopMoveLeft();
    /** Stop moving right */
    void stopMoveRight();
    /** Stop moving up */
    void stopMoveUp();
    /** Stop moving down */
    void stopMoveDown();
    /** Jump */
    void jump();
    /**
     * Initialize model
     * @param environment Game environment object
     * @param cont Block container for the game area
     */
    void init(GameEnvironmentInterface environment, IrregularBlockContainerInterface cont);

    /** Start the game */
    void start();

    /** Update model */
    void update();

    /**
     * Get the active map
     */
    MapDrawInterface getMap();

    /**
     * Get the player object
     */
    GameObject getPlayerObject();

    /**
     * Get number of human controlled players
     * @return Number of human contolled players
     */
    int getNoOfHumanPlayers();

    /**
     * Check if game has ended
     * @return true if game has ended
     */
    boolean isEnd();
    /**
     * Check if it is game over
     * @return true if it is game over
     */
    boolean isGameOver();
    /**
     * Check if game has been completed
     * @return true if game has been completed
     */
    boolean isCompleted();
    /**
     * Check if game has been started
     * @return true if game has been started
     */
    boolean isStarted();
    /**
     * Check if game has been initialized
     * @return true if game has been initialized
     */
    boolean isInitialized();
    /**
     * Check if this is a multi player game
     * @return true if multi player
     */
    boolean isMultiplayer();

    /**
     * Get an information string from the model
     * @return The information string
     */
    String getInfoString();
    /**
     * Set a cheat parameter
     * @param parameter Cheat parameter name
     * @param value Cheat parameter value
     */
    void setCheatModeParameter(String parameter, String value);
}
