package erland.game.racer;

/**
 * Defines all methods needed for an container with friction objects
 */
public interface FrictionObjectContainerInterface {
    /**
     * Get all friction objects close to a specified friction sensitive object
     * @param obj The friction sensitive object
     * @return An array of friction objects
     */
    public FrictionObjectInterface[] getFrictionObjects(FrictionSensitiveInterface obj);
    /**
     * Get all friction objects close to an offset from a specified friction sensitive object
     * @param obj The friction sensitive object
     * @param x The x offset
     * @param y The y offset
     * @return An array of friction objects
     */
    public FrictionObjectInterface[] getFrictionObjects(FrictionSensitiveInterface obj,int x,int y);
}
