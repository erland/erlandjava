package erland.network;

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
