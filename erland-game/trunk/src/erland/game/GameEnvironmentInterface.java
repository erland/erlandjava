package erland.game;

import erland.util.ImageHandlerInterface;
import erland.util.ImageCreatorInterface;

/**
 * Defines the interface of the game environment, an object implementing
 * this interface can be passed around the application instead of passing
 * a lot of small objects around for each particular information
 * @author Erland Isaksson
 */
public interface GameEnvironmentInterface extends GameServerEnvironmentInterface {
    /**
     * Get the current image handler object
     * @return The current active image handler object
     */
    public ImageHandlerInterface getImageHandler();
    /**
     * Get the current image creator object
     * @return The current image creator object
     */
    public ImageCreatorInterface getImageCreator();
    /**
     * Get the current screen handler object
     * @return The current screen handler object
     */
    public ScreenHandlerInterface getScreenHandler();
}
