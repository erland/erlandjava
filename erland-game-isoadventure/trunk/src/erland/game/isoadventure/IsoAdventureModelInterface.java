package erland.game.isoadventure;

import erland.game.GameEnvironmentInterface;
import erland.game.BlockContainerInterface;

/**
 * Defines all methods needed for a game model for the racer gamel
 */
public interface IsoAdventureModelInterface {
    /** Begin moving left */
    void startMoveLeft();
    /** Begin moving right */
    void startMoveRight();
    /** Begin moving up */
    void startMoveUp();
    /** Begin moving down */
    void startMoveDown();
    /** Stop moveing left */
    void stopMoveLeft();
    /** Stop moving right */
    void stopMoveRight();
    /** Stop moving up */
    void stopMoveUp();
    /** Stop moving down */
    void stopMoveDown();
    /** Jump */
    void jump();
    /**
     * Initialize model
     * @param environment Game environment object
     * @param cont Block container for the game area
     */
    void init(GameEnvironmentInterface environment, IrregularBlockContainerInterface cont);

    /** Start the game */
    void start();

    /** Update model */
    void update();

    /**
     * Get the active map
     */
    MapDrawInterface getMap();

    /**
     * Get the player object
     */
    IsoObjectInterface getPlayerObject();

    /**
     * Get number of human controlled players
     * @return Number of human contolled players
     */
    int getNoOfHumanPlayers();

    /**
     * Check if game has ended
     * @return true if game has ended
     */
    boolean isEnd();
    /**
     * Check if it is game over
     * @return true if it is game over
     */
    boolean isGameOver();
    /**
     * Check if game has been completed
     * @return true if game has been completed
     */
    boolean isCompleted();
    /**
     * Check if game has been started
     * @return true if game has been started
     */
    boolean isStarted();
    /**
     * Check if game has been initialized
     * @return true if game has been initialized
     */
    boolean isInitialized();
    /**
     * Check if this is a multi player game
     * @return true if multi player
     */
    boolean isMultiplayer();

    /**
     * Get an information string from the model
     * @return The information string
     */
    String getInfoString();
    /**
     * Set a cheat parameter
     * @param parameter Cheat parameter name
     * @param value Cheat parameter value
     */
    void setCheatModeParameter(String parameter, String value);
}
