package erland.game;
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

import java.awt.*;

/**
 * Defines the methods that a game panel handler application
 * class needs to implement
 * @author Erland Isaksson
 */
public interface GamePanelHandlerApplicationInterface {
    /**
     * Will be called once for each game.
     * Should return list of prefereable display modes
     * @return Array of display modes
     */
    public DisplayMode[] getDisplayModes();

    /**
     * Will be called once for each game.
     * Should init all game panels and add them the the game handler and
     * perform all other initialization that is needed
     * @param handler The game handler which the game panel objects should be added to
     */
    public void initGames(GamePanelHandlerInterface handler);
}
