package erland.game;

import erland.network.NetworkConnectionInterface;

/**
 * Defines the methods a game player object that should work together with
 * the {@link GameServer} needs to implement
 * @author Erland Isaksson
 */
public interface GamePlayerInterface {
    /**
     * Get the network connection for this player
     * @return The network connection
     */
    NetworkConnectionInterface getConnection();

    /**
     * Sets the network connection for this player
     * @param connection The network connection
     */
    void setConnection(NetworkConnectionInterface connection);
}
