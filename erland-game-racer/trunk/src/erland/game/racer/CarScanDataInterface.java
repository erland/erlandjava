package erland.game.racer;

/**
 * Defines the methods needed for a scan data ray
 */
public interface CarScanDataInterface {
    /** Action parameter: No turn or no accelleration/brake */
    public final static int NONE = 0;
    /** Action parameter: Left turn */
    public final static int LEFT = 1;
    /** Action parameter: Right turn */
    public final static int RIGHT = 2;
    /** Action parameter: Accellerate */
    public final static int ACCELERATE = 1;
    /** Action parameter: Brake */
    public final static int BREAK = 2;
    /** Sub direction type: Allways choose one of the sub scan data rays if main ray is best */
    public final static int CHOOSEONE = 1;
    /** Sub direction type: Choose a sub scan data ray if it is smaller than the others */
    public final static int CHOOSEONEIFSMALLEST = 2;


    /**
     * Get sub friction data for the ray
     * @return Sub friction data
     */
    public CarScanDataInterface[] getSubFrictionData();
    /**
     * Get sub scan data for the ray
     * @return Sub scan data
     */
    public CarScanDataInterface[] getSubDirectionData();
    /**
     * Get sub scan data type
     * @return Sub scan datat type {@link #CHOOSEONE}, {@link #CHOOSEONEIFSMALLEST}
     */
    public int getSubDirectionType();
    /**
     * Get accellerate/brake action of the ray
     * @return Accellerate/brake action {@link #NONE}, {@link #ACCELERATE}, {@link #BREAK}
     */
    public int getAccelerateBreak();
    /**
     * Get turn action of the ray
     * @return Turn action {@link #NONE}, {@link #LEFT}, {@link #RIGHT}
     */
    public int getTurnLeftRight();
    /**
     * Get the scan length of the ray
     * @return The scan length
     */
    public double getScanLength();
    /**
     * Get the amount of increasing scan length when speed of the car increases
     * @return The amount of increasing scan length
     */
    public double getSpeedScanLength();
    /**
     * Get angle of ray
     * @return The angle of the scan ray
     */
    public double getAngle();
}
