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
import java.net.SocketException;
import java.io.IOException;

/**
 * Helper class to make it easier to establish connection with a NetworkServer on a specified port
 * @author Erland Isaksson
 */
public class NetworkClient {
    /**
     * Establish connection with the specified server, callbacks will be made to the specified
     * client listner object
     * @param host The IP-address or hostname of the server
     * @param port The port number to connect to
     * @param retries The number of retries to make when establishing connection
     * @param username The username to login with
     * @param password The password to login with
     * @param listener The client listner object that callbacks should be sent to
     * @return The NetworkConnectionInterface of the established connection,
     * null if no connection was possible to establish
     */
    public static NetworkConnectionInterface connect(String host, int port, int retries,String username, String password, NetworkClientListenerInterface listener) {
        NetworkConnectionInterface connection;
        Socket socket;
        do {
            retries--;
            try {
                socket = new Socket(host,port);
            } catch (IOException e) {
                //e.printStackTrace();
                socket = null;
            }

            if(socket==null) {
                try {
                    Thread.sleep(5000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }while(socket==null && retries>=0);

        if(socket!=null) {
            try {
                socket.setTcpNoDelay(true);
            } catch (SocketException e) {
                e.printStackTrace();  //To change body of catch statement use Options | File Templates.
            }
            connection = NetworkConnectionFactory.create(username,password,socket,listener);
            if(connection.isAlive()) {
                return connection;
            }else {
                return null;
            }
        }else {
            return null;
        }
    }
}
