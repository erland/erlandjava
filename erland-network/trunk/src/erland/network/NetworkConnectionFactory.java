package erland.network;

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
