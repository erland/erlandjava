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

import erland.network.NetworkConnectionInterface;

/**
 * Defines the methods a game player object that should work together with
 * the {@link GameServer} needs to implement
 * @author Erland Isaksson
 */
public interface GamePlayerInterface {
    /**
     * Get the network connection for this player
     * @return The network connection
     */
    NetworkConnectionInterface getConnection();

    /**
     * Sets the network connection for this player
     * @param connection The network connection
     */
    void setConnection(NetworkConnectionInterface connection);
}
