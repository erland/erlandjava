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

/**
 * Defines the interface for the game panel handler
 * @author Erland Isaksson
 */
public interface GamePanelHandlerInterface {
    /**
     * Sets the word which should be used to toggle cheat mode on/off
     * @param cheatWord The cheat mode toggle word
     */
    public void cheatWord(String cheatWord);
    /**
     * Adds a new GamePanel object
     * @param name The name of the game panel
     * @param description The description of the game panel
     * @param panel The game panel object itself
     */
    public void addPanel(String name, String description, GamePanelInterface panel);
}
