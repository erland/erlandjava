package erland.game;

/**
 * The interface that each GamePanel object has to implement which
 * should be used toghether with {@link GamePanelHandlerInterface}
 * @author Erland Isaksson
 */
public interface GamePanelInterface {
    /**
     * Checks if game panel should be closed
     * @return true/false (Exit/Continue)
     */
    public boolean isExit();

    /**
     * Enable och disable cheatmode
     * @param enable true/false (Enable/Disable)
     */
    public void setCheatmode(boolean enable);

    /**
     * Exit game panel, called once before the game panel is closed
     */
    public void exit();

    /**
     * Init game panel, called once each time before the game panel is opened
     * @param environment The game environment the game panel exists in
     */
    public void init(GameEnvironmentInterface environment);

    /**
     * Update game logic, called once every frame
     */
    public void update();

    /**
     * Draw game screen, called once every frame
     */
    public void draw();
}
