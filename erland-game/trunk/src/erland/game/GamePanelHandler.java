package erland.game;

/**
 * A helper class that should be used when running a game panel handler application using the
 * {@link GamePanelHandlerApplicationInterface}
 * @author Erland Isaksson
 */
public class GamePanelHandler {
    /**
     * Starts a new game panel application, this method will not return until the
     * application has exited.
     * @param application The game panel handler applicaton object to use
     * @param handler The game panel handler implementation to use
     */
    public static void run(GamePanelHandlerApplicationInterface application, GamePanelHandlerImplementationInterface handler) {
        handler.run(application);
    }
}
