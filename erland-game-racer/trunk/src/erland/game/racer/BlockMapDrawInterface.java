package erland.game.racer;

import java.awt.*;

/**
 * This interface defines the methods needed for a drawable map
 */
public interface BlockMapDrawInterface {
    /**
     * Draw the specified level of the block och the specified Graphics object
     * @param g The Graphics object to draw on
     * @param level The level to draw ( 0 is the background )
     */
    void draw(Graphics g, int level);
}
