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

import erland.util.ImageHandlerInterface;
import erland.util.ImageCreatorInterface;

/**
 * Defines the interface of the game environment, an object implementing
 * this interface can be passed around the application instead of passing
 * a lot of small objects around for each particular information
 * @author Erland Isaksson
 */
public interface GameEnvironmentInterface extends GameServerEnvironmentInterface {
    /**
     * Get the current image handler object
     * @return The current active image handler object
     */
    public ImageHandlerInterface getImageHandler();
    /**
     * Get the current image creator object
     * @return The current image creator object
     */
    public ImageCreatorInterface getImageCreator();
    /**
     * Get the current screen handler object
     * @return The current screen handler object
     */
    public ScreenHandlerInterface getScreenHandler();
}
