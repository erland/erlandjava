package erland.network;

/**
 * Represents a network connection and defines all methods needed to manage
 * an established network connection
 * @author Erland Isaksson
 */
public interface NetworkConnectionInterface {
    /**
     * Get the name of the connection
     * @return The name of the connection
     */
    public String getName();
    /**
     * Checks if the connection is still established
     * @return true/false (established/not established)
     */
    public boolean isAlive();
    /**
     * Request that the connection should be disconnected
     */
    public void disconnect();
    /**
     * Write data to the connection
     * @param message Data to write
     * @return true/false (success/failure)
     */
    public boolean write(String message);
}
