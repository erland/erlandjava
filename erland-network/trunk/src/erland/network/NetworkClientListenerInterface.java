package erland.network;
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
 * Defines the callback methods that should be implemented by
 * an client listening on a network connection using a NetworkConnectionInterface object
 * @author Erland Isaksson
 */
public interface NetworkClientListenerInterface {
    /**
     * Called when connection with the server has been established, when
     * this method is called the login procedure has also succeeded
     * @param connection The connection object of the established connection
     */
    public void connected(NetworkConnectionInterface connection);
    /**
     * Called when a connection with the server has been disconnected
     * @param connection The connection object of the disconnected connection
     */
    public void disconnected(NetworkConnectionInterface connection);
    /**
     * Called when a message is recieved from the network
     * @param connection The connection object on which the message was recieved
     * @param message The recieved message
     */
    public void message(NetworkConnectionInterface connection, String message);
}
