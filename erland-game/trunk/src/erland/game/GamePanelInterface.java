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
 * The interface that each GamePanel object has to implement which
 * should be used toghether with {@link GamePanelHandlerInterface}
 * @author Erland Isaksson
 */
public interface GamePanelInterface {
    /**
     * Checks if game panel should be closed
     * @return true/false (Exit/Continue)
     */
    public boolean isExit();

    /**
     * Enable och disable cheatmode
     * @param enable true/false (Enable/Disable)
     */
    public void setCheatmode(boolean enable);

    /**
     * Exit game panel, called once before the game panel is closed
     */
    public void exit();

    /**
     * Init game panel, called once each time before the game panel is opened
     * @param environment The game environment the game panel exists in
     */
    public void init(GameEnvironmentInterface environment);

    /**
     * Update game logic, called once every frame
     */
    public void update();

    /**
     * Draw game screen, called once every frame
     */
    public void draw();
}
