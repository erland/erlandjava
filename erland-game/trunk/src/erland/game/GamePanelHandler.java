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
 * A helper class that should be used when running a game panel handler application using the
 * {@link GamePanelHandlerApplicationInterface}
 * @author Erland Isaksson
 */
public class GamePanelHandler {
    /**
     * Starts a new game panel application, this method will not return until the
     * application has exited.
     * @param application The game panel handler applicaton object to use
     * @param handler The game panel handler implementation to use
     */
    public static void run(GamePanelHandlerApplicationInterface application, GamePanelHandlerImplementationInterface handler) {
        handler.run(application);
    }
}
