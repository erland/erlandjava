package erland.game;

/**
 * Defines the interface for the game panel handler
 * @author Erland Isaksson
 */
public interface GamePanelHandlerInterface {
    /**
     * Sets the word which should be used to toggle cheat mode on/off
     * @param cheatWord The cheat mode toggle word
     */
    public void cheatWord(String cheatWord);
    /**
     * Adds a new GamePanel object
     * @param name The name of the game panel
     * @param description The description of the game panel
     * @param panel The game panel object itself
     */
    public void addPanel(String name, String description, GamePanelInterface panel);
}
