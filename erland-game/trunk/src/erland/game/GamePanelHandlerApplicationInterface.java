package erland.game;

import java.awt.*;

/**
 * Defines the methods that a game panel handler application
 * class needs to implement
 * @author Erland Isaksson
 */
public interface GamePanelHandlerApplicationInterface {
    /**
     * Will be called once for each game.
     * Should return list of prefereable display modes
     * @return Array of display modes
     */
    public DisplayMode[] getDisplayModes();

    /**
     * Will be called once for each game.
     * Should init all game panels and add them the the game handler and
     * perform all other initialization that is needed
     * @param handler The game handler which the game panel objects should be added to
     */
    public void initGames(GamePanelHandlerInterface handler);
}
