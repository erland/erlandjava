package erland.game.racer;

/**
 * Handles the scan data needed for computer controlled cars to make it
 * possible for them to choose the direction with least friction
 */
public class CarScanData implements CarScanDataInterface {
    /** The length multiplier of the scan ray */
    private final static int SCANLENGTH = 20;
    /** The amount of increasing of the scan ray length when speed of the car increases */
    private final static int SPEEDSCANLENGTH = 30;
    /** The angle of the scan ray */
    private double angle;
    /** The length of the scan ray */
    private double scanLength;
    /** Indicates how the car should turn if this ray is choosen as the best direction */
    private int turn;
    /** Indicates if the car should accellerate/break if this ray is choosen as the best direction */
    private int accelerate;
    /** Sub scan rays that also should be checked if this ray is the best */
    private CarScanDataInterface subScanData[];
    /** Indicates hos the subScanData should be checked */
    private int subType;
    /** Sub friction data that should be checked if this ray is the best */
    private CarScanDataInterface frictionSubScanData[];

    /**
     * Creates a scan ray object
     * @param angle The angel of the ray
     * @param scanLength The length of the ray
     * @param turn Indication how the car should turn if ray is choosen
     * @param accelerate Indication hos the car should accellerate if ray is choosen
     * @param subType Indication of how the subScanData should be checked
     * @param subScanData Sub rays that also should be checked if this ray is choosen
     * @param frictionSubScanData Sub friction data that should be checked if this ray is choosen
     */
    public CarScanData(double angle,double scanLength,int turn,int accelerate, int subType, CarScanDataInterface subScanData[], CarScanDataInterface frictionSubScanData[])
    {
        this.angle = angle;
        this.scanLength = scanLength;
        this.turn = turn;
        this.accelerate = accelerate;
        this.subScanData = subScanData;
        this.subType = subType;
        this.frictionSubScanData = frictionSubScanData;
    }

    public CarScanDataInterface[] getSubFrictionData() {
        return frictionSubScanData;
    }

    public CarScanDataInterface[] getSubDirectionData() {
        return subScanData;
    }

    public int getSubDirectionType() {
        return subType;
    }

    public int getAccelerateBreak() {
        return accelerate;
    }

    public int getTurnLeftRight() {
        return turn;
    }

    public double getScanLength() {
        return scanLength*SCANLENGTH;
    }

    public double getSpeedScanLength() {
        return scanLength*SPEEDSCANLENGTH;
    }

    public double getAngle() {
        return angle;
    }

}
