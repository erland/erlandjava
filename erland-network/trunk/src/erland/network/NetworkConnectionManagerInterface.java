package erland.network;

/**
 * Defines an interface of a connection manager that manages several
 * network connections
 * @author Erland Isaksson
 */
public interface NetworkConnectionManagerInterface {
    /**
     * Write a message to the client with the specified name
     * @param clientName The name of the client
     * @param message The message to write
     * @return true/false (success/failure)
     */
    public boolean write(String clientName, String message);

    /**
     * Write a message to all connected clients
     * @param message The message to write
     * @return true/false (success/failure)
     */
    public boolean write(String message);
}
