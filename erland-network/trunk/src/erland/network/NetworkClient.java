package erland.network;

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
