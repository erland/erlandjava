package erland.network;

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
