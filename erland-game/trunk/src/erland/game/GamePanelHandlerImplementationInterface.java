package erland.game;


/**
 * Interface of a implementation of a game panel handler
 * @author Erland Isaksson
 */
public interface GamePanelHandlerImplementationInterface extends GameEnvironmentInterface {

    /**
     * Get the framrate in number of milliseconds between each frame
     * @return Number of milliseconds requested between each frame
     */
    public int getFrameDelay();

    /**
     * Check if we are in full screen mode
     * @return true/false (fullscreen/windowed)
     */
    public boolean getFullScreen();

    /**
     * Requests the handler or user to choose if fullscreen mode should be used
     * @return true/false (fullscreen/windowed)
     */
    public boolean askUserForFullscreen();

    /**
     * Requests extra parameters from the user or handler
     */
    public void askUserForOptions();

    /**
     * Creates and intialized custom button
     * @param firstX The x position of the first button
     * @param firstY The y posistion of the first botton
     * @param buttonWidth The optimal width of new buttons
     * @param buttonHeight The optimal height of new buttons
     * @param buttonSpaceBetween The optimal number of pixels between buttons
     */
    public void initExtraButtons(int firstX, int firstY,int buttonWidth, int buttonHeight,int buttonSpaceBetween);

    /**
     * Initialize the game panel handler implementation class
     */
    public void init();

    /**
     * Run the game panel handler implementation
     * @param application The application object to use
     */
    public void run(GamePanelHandlerApplicationInterface application);
}
