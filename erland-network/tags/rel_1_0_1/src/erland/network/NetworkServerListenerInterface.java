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
 * Defines the callback methods that should be implemented by a server listening
 * on a network connection with the NetworkConnectionInterface
 * @author Erland Isaksson
 */
public interface NetworkServerListenerInterface extends NetworkClientListenerInterface {
    /**
     * Called when a clients wants to login the the server. The server should validate the
     * username and password and tell if the client is allowed to connect or not
     * @param connection The connection on wich the login request was received
     * @param username The username the client wants to login with
     * @param password The password the client wants to login with
     * @return true/false (allowed/not allowed)
     */
    public boolean login(NetworkConnectionInterface connection, String username, String password);
}
