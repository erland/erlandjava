/**
 * Created by IntelliJ IDEA.
 * User: Erland Isaksson
 * Date: Jan 3, 2003
 * Time: 4:44:18 PM
 * To change this template use Options | File Templates.
 */
package erland.game.racer;

/**
 * Defines the interface for a car control manager
 */
public interface CarControlManagerInterface {
    /**
     * Control all cars, for example start turning left when reaching a left turn
     */
    public void controlCars();

    /**
     * Set the friction object container to use
     * @param frictionContainer The friction object container to use
     */
    public void setFrictionObjectContainer(FrictionObjectContainerInterface frictionContainer);
}
