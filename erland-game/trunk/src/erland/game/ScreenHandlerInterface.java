package erland.game;

import javax.swing.*;
import java.awt.*;


/**
 * Interface that represents the main screen object
 */
public interface ScreenHandlerInterface {
    /**
     * Get current Graphics object that can be drawn on
     * @return A Graphics object
     */
    public Graphics getCurrentGraphics();
    /**
     * Swap screens when double buffering is enabled
     * @return true - success<br>
     *         false - failure
     */
    public boolean swapScreens();

    /**
     * Adds a component
     * @param c The component to add to the screen
     */
    public void add(JComponent c);
    /**
     * Removes a component
     * @param c The component to remove from the screen
     */
    public void remove(JComponent c);
    /**
     * Get the width of the screen
     * @return Width of the screen in pixels
     */
    public int getWidth();
    /**
     * Get the height of the screen
     * @return Height of the screen in pixels
     */
    public int getHeight();
    /**
     * Deletes the screen, should be called when you have finished using the screen
     */
    public void dispose();
    /**
     * Paints the Component's added to the screen
     * @param g Graphics object to paint on
     */
    public void paintComponents(Graphics g);
    /**
     * Get the Frame object representing the screen
     * @return The Frame object of the screen
     */
    public Frame getFrame();
    /**
     * Set the mouse cursor
     * @param cursor The cursor to use, null means no cursor at all
     */
    public void setCursor(Cursor cursor);
}

