package erland.game;

import erland.util.ParameterValueStorageExInterface;

/**
 * Defines the interface of the game server environment, an object implementing
 * this interface can be passed around the application instead of passing
 * a lot of small objects around for each particular information
 * @author Erland Isaksson
 */
public interface GameServerEnvironmentInterface {
    /**
     * Get the current storage object
     * @return The current storage object
     */
    public ParameterValueStorageExInterface getStorage();
    /**
     * Get the current high score object
     * @return The current high score object
     */
    public HighScoreInterface getHighScore();

    /**
     * Get the current high score list object
     * @return The current high score list object
     */
    public HighScoreListInterface getHighScoreList();
}
