/**
 * Created by IntelliJ IDEA.
 * User: Erland Isaksson
 * Date: Jan 3, 2003
 * Time: 2:19:05 PM
 * To change this template use Options | File Templates.
 */
package erland.game.racer;

import erland.game.GameEnvironmentInterface;
import erland.game.BlockContainerInterface;

/**
 * Defines all methods needed for a game model for the racer gamel
 */
public interface RacerModelInterface {
    /** Begin turn user car left */
    void startTurnLeft();
    /** Begin turn user car right */
    void startTurnRight();
    /** Begin accellerating user car */
    void startAccellerating();
    /** Begin braking user car */
    void startBraking();
    /** Stop turn user car left */
    void stopTurnLeft();
    /** Stop turn user car right */
    void stopTurnRight();
    /** Stop accellerating user car */
    void stopAccellerating();
    /** Stop braking user car */
    void stopBraking();
    /**
     * Initialize model
     * @param environment Game environment object
     * @param cont Block container for the game area
     */
    void init(GameEnvironmentInterface environment, BlockContainerInterface cont);
    /** Start the game */
    void start();
    /** Update model */
    void update();
    /**
     * Get number of human controlled cars
     * @return Number of human contolled cars
     */
    int getNoOfHumanCars();
    /**
     * Get all opponent cars
     * @return All opponent cars
     */
    CarDrawInterface[] getOpponentCars();
    /**
     * Get the car of the current user
     * @return The car of the current user
     */
    CarDrawInterface getMyCar();

    /**
     * Get the track/level map
     * @return The block map
     */
    BlockMapDrawInterface getMap();

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
