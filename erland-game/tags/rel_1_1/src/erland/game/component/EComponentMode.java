package erland.game.component;
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
 * Static class that is used to select which implementation of
 * the Exxxx components that should be used
 * @author Erland Isaksson
 */
public class EComponentMode {
    /** Indicates if applet compatible components should be used */
    private static boolean bApplet = false;

    /**
     * Sets if applet compatible components should be used or not
     * @param applet true/false (Applet compatible/Not applet compatible)
     */
    static public void setAppletMode(boolean applet) {
        bApplet = applet;
    }
    /**
     * Check if the current component mode is applet compatible
     * @return true/false (Applet compatible/Not applet compatible)
     */
    static boolean isApplet() {
        return bApplet;
    }
}
