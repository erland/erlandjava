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
 * Interface that represents the main screen object
 * @author Erland Isaksson
 */
public interface ScreenHandlerInterface {
    /**
     * Get current Graphics object that can be drawn on
     * @return A Graphics object
     */
    public Graphics getCurrentGraphics();
    /**
     * Swap screens when double buffering is enabled
     * @return true - success<br>
     *         false - failure
     */
    public boolean swapScreens();

    /**
     * Adds a component
     * @param c The component to add to the screen
     */
    public void add(Component c);
    /**
     * Removes a component
     * @param c The component to remove from the screen
     */
    public void remove(Component c);
    /**
     * Get the width of the screen
     * @return Width of the screen in pixels
     */
    public int getWidth();
    /**
     * Get the height of the screen
     * @return Height of the screen in pixels
     */
    public int getHeight();
    /**
     * Deletes the screen, should be called when you have finished using the screen
     */
    public void dispose();
    /**
     * Paints the Component's added to the screen
     * @param g Graphics object to paint on
     */
    public void paintComponents(Graphics g);
    /**
     * Get the Container object representing the screen
     * @return The Container object of the screen
     */
    public Container getContainer();
    /**
     * Set the mouse cursor
     * @param cursor The cursor to use, null means no cursor at all
     */
    public void setCursor(Cursor cursor);
    /**
     * Get the corresponding x coordinate on the screen, this removes the offset because of
     * for example title bar and borders when in windowed mode
     */
    public int getScreenX(int x);

    /**
     * Get the corresponding y coordinate on the screen, this removes the offset because of
     * for example title bar and borders when in windowed mode
     */
    public int getScreenY(int y);
    /**
     * Indicates if the screen has been closed
     */
    public boolean isExit();
}

