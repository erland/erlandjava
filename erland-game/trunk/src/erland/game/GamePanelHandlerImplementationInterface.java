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
 * Interface of a implementation of a game panel handler
 * @author Erland Isaksson
 */
public interface GamePanelHandlerImplementationInterface extends GameEnvironmentInterface {

    /**
     * Get the framrate in number of milliseconds between each frame
     * @return Number of milliseconds requested between each frame
     */
    public int getFrameDelay();

    /**
     * Check if we are in full screen mode
     * @return true/false (fullscreen/windowed)
     */
    public boolean getFullScreen();

    /**
     * Requests the handler or user to choose if fullscreen mode should be used
     * @return true/false (fullscreen/windowed)
     */
    public boolean askUserForFullscreen();

    /**
     * Requests extra parameters from the user or handler
     */
    public void askUserForOptions();

    /**
     * Creates and intialized custom button
     * @param firstX The x position of the first button
     * @param firstY The y posistion of the first botton
     * @param buttonWidth The optimal width of new buttons
     * @param buttonHeight The optimal height of new buttons
     * @param buttonSpaceBetween The optimal number of pixels between buttons
     */
    public void initExtraButtons(int firstX, int firstY,int buttonWidth, int buttonHeight,int buttonSpaceBetween);

    /**
     * Initialize the game panel handler implementation class
     */
    public void init();

    /**
     * Run the game panel handler implementation
     * @param application The application object to use
     */
    public void run(GamePanelHandlerApplicationInterface application);
}
