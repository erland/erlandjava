package erland.game.racer;

/**
 * Defines the methods needed for a scan data container
 */
public interface CarScanDataContainerInterface {
    /**
     * Get all the scan data for the container
     * @return The scan data
     */
    public CarScanDataInterface[] getScanData();
}
