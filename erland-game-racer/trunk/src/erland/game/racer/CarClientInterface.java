/**
 * Created by IntelliJ IDEA.
 * User: Erland Isaksson
 * Date: Jan 4, 2003
 * Time: 10:34:53 AM
 * To change this template use Options | File Templates.
 */
package erland.game.racer;

import erland.game.GameEnvironmentInterface;
import erland.game.BlockContainerInterface;

/**
 * This interface defines the methods needed for a Car object on the client
 */
public interface CarClientInterface extends CarDrawInterface {
    /**
     * Initialize the car in a new game environment
     * @param environment The game environment which the car exists in
     */
    void init(GameEnvironmentInterface environment);

    /**
     * Set the block container which the car exists in
     * @param cont The block container
     */
    void setContainer(BlockContainerInterface cont);

    /**
     * Set the main image for the car
     * @param image The image bitmap of the car
     */
    void setImage(String image);

    /**
     * Get the filename of the main image for the car
     * @return The filename of the image
     */
    String getImage();

    /**
     * Set the pixel position of the car
     * @param x X pixel position
     * @param y Y pixel position
     */
    void setClientPos(int x, int y);

    /**
     * Set the pixel position of the car
     * @param x X pixel position
     * @param y Y pixel position
     */
    void setServerPos(int x, int y);

    /**
     * Set the angle of the car
     * @param angle The angle
     */
    void setClientCarAngle(int angle);

    /**
     * Set the angle of the car
     * @param angle The angle
     */
    void setServerCarAngle(int angle);

    /**
     * Set the angle of the car
     * @param angle The angle
     */
    void setClientMovingAngle(int angle);

    /**
     * Set the angle of the car
     * @param angle The angle
     */
    void setServerMovingAngle(int angle);

    /**
     * Set the current speed of the car
     * @param speed The speed of the car
     */
    void setClientSpeed(double speed);

    /**
     * Set the current speed of the car
     * @param speed The speed of the car
     */
    void setServerSpeed(double speed);

    /**
     * Get the moving angle of the car
     * @return The angle
     */
    double getMovingAngle();

    /**
     * Get the angle of the car
     * @return The angle
     */
    double getCarAngle();

    /**
     * Get the current speed of the car
     * @return The current speed
     */
    double getSpeed();

    /**
     * Update the client position of the car
     */
    void clientUpdate();

    /**
     * Update the server position of the car
     */
    void serverUpdate();

    /**
     * Sets the identifier number of the car
     * @param id The identifier number
     */
    void setId(int id);

    /**
     * Gets the identifier number of the car
     * @return The identifier number of the car
     */
    int getId();

    /**
     * Set a cheat parameter
     * @param parameter The cheat parameter to set
     * @param value The value of the cheat parameter
     */
    void setCheatParameter(String parameter,String value);
}
