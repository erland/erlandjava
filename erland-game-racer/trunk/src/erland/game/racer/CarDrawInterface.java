/**
 * Created by IntelliJ IDEA.
 * User: Erland Isaksson
 * Date: Jan 3, 2003
 * Time: 2:24:58 PM
 * To change this template use Options | File Templates.
 */
package erland.game.racer;

import java.awt.*;

/**
 * This interface defines the methods needed for a Car that should be drawn
 */
public interface CarDrawInterface {
    /**
     * Get the x pixel position of the car
     * @return The x pixel position
     */
    int getPosX();
    /**
     * Get the y pixel position of the car
     * @return The y pixel position
     */
    int getPosY();
    /**
     * Draw the the specified level of the car on the specified Graphics object
     * @param g Graphics object to draw on
     * @param level The level of the car which should be drawn
     */
    void draw(Graphics g, int level);
}
