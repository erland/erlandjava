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


import java.applet.Applet;

/**
 * Abstract class of a game panel handler for a full screen game
 * @author Erland Isaksson
 */
public class GamePanelHandlerForApplet extends GamePanelHandlerForApplication {
    /** Applet object */
    private Applet applet;

    /**
     * Creates a new instance using the specified applet object
     * @param applet The applet object
     */
    public GamePanelHandlerForApplet(Applet applet) {
        this.applet = applet;
    }

    public boolean getFullScreen() {
        return false;
    }

    public boolean askUserForFullscreen() {
        return false;
    }

    public ScreenHandlerInterface createScreenHandler() {
        return new AppletScreenHandler(applet,getApplication().getDisplayModes());

    }

    public void initExtraButtons(int firstX, int firstY, int buttonWidth, int buttonHeight, int buttonSpaceBetween) {
        // No extra buttons
    }
}
