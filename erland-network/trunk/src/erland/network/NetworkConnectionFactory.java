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

import java.net.Socket;

/**
 * A factory that creates network connections
 * @author Erland Isaksson
 */
public class NetworkConnectionFactory {
    /**
     * Creates a server connection
     * @param socket Socket for the connection
     * @param listener Server listening object that should recieve callbacks when something happends
     * @return The NetworkConnectionInterface object representing the connection
     */
    public static NetworkConnectionInterface create(Socket socket, NetworkServerListenerInterface listener) {
        return new NetworkConnection(socket, listener);
    }
    /**
     * Creates a client connection
     * @param username The username to login with
     * @param password The password to login with
     * @param socket Socket for the connection
     * @param listener Client listening object that should recieve callbacks when something happends
     * @return The NetworkConnectionInterface object representing the connection
     */
    public static NetworkConnectionInterface create(String username, String password, Socket socket, NetworkClientListenerInterface listener) {
        return new NetworkConnection(username, password ,socket, listener);
    }
}
