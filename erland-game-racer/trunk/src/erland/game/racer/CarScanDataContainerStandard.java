package erland.game.racer;

/**
 * Implements standard scan data for computer controlled cars
 */
public class CarScanDataContainerStandard implements CarScanDataContainerInterface {
    /**
     * Scan data that should be used
     */
    private final CarScanData scanData[] = new CarScanData[] {
        new CarScanData(   0.0, 1.0,  CarScanData.NONE,  CarScanData.ACCELERATE,  CarScanData.CHOOSEONEIFSMALLEST, new CarScanData[] {
            new CarScanData( -15.0, 1.0,   CarScanData.LEFT, CarScanData.NONE,  CarScanData.NONE, null, new CarScanData[] {
                new CarScanData( -15.0, 0.75,  CarScanData.NONE, CarScanData.NONE,  CarScanData.NONE, null, null),
                new CarScanData( -15.0, 0.5,  CarScanData.NONE, CarScanData.NONE,  CarScanData.NONE, null, null)
            }),
            new CarScanData(  15.0, 1.0,  CarScanData.RIGHT, CarScanData.NONE,  CarScanData.NONE, null, new CarScanData[] {
                new CarScanData(  15.0, 0.75, CarScanData.NONE, CarScanData.NONE,  CarScanData.NONE, null, null),
                new CarScanData(  15.0, 0.5, CarScanData.NONE, CarScanData.NONE,  CarScanData.NONE, null, null)
            }),
            new CarScanData( -15.0, 0.5,  CarScanData.LEFT, CarScanData.NONE,  CarScanData.NONE, null, new CarScanData[] {
                new CarScanData(  -15.0, 0.25, CarScanData.NONE, CarScanData.NONE,  CarScanData.NONE, null, null)
            }),
            new CarScanData(  15.0, 0.5, CarScanData.RIGHT, CarScanData.NONE,  CarScanData.NONE, null, new CarScanData[] {
                new CarScanData(   15.0, 0.25, CarScanData.NONE, CarScanData.NONE,  CarScanData.NONE, null, null)
            }),
        }, new CarScanData[] {
            new CarScanData(   0.0, 0.75,  CarScanData.NONE,  CarScanData.NONE,  CarScanData.NONE, null, null),
            new CarScanData(   0.0, 0.5,  CarScanData.NONE,  CarScanData.NONE,  CarScanData.NONE, null, null),
            new CarScanData(   0.0, 0.25, CarScanData.NONE,  CarScanData.NONE,  CarScanData.NONE, null, null),
            new CarScanData(  15.0, 0.25, CarScanData.NONE,  CarScanData.NONE,  CarScanData.NONE, null, null),
            new CarScanData( -15.0, 0.25, CarScanData.NONE,  CarScanData.NONE,  CarScanData.NONE, null, null)
        }),
        new CarScanData( -30.0, 1.0,  CarScanData.LEFT, CarScanData.ACCELERATE,  CarScanData.NONE, null, new CarScanData[] {
            new CarScanData( -30.0, 0.5,  CarScanData.NONE, CarScanData.NONE,  CarScanData.NONE, null, null),
            new CarScanData( -30.0, 0.25, CarScanData.NONE,  CarScanData.NONE,  CarScanData.NONE, null, null)
        }),
        new CarScanData(  30.0, 1.0, CarScanData.RIGHT, CarScanData.ACCELERATE,  CarScanData.NONE, null, new CarScanData[] {
            new CarScanData(  30.0, 0.5,  CarScanData.NONE, CarScanData.NONE,  CarScanData.NONE, null, null),
            new CarScanData(  30.0, 0.25, CarScanData.NONE,  CarScanData.NONE,  CarScanData.NONE, null, null)
        }),
        new CarScanData( -45.0, 1.0,  CarScanData.LEFT,      CarScanData.BREAK,  CarScanData.NONE, null, new CarScanData[] {
            new CarScanData( -45.0, 0.5,  CarScanData.NONE, CarScanData.NONE,  CarScanData.NONE, null, null)
        }),
        new CarScanData(  45.0, 1.0, CarScanData.RIGHT,      CarScanData.BREAK,  CarScanData.NONE, null, new CarScanData[] {
            new CarScanData(  45.0, 0.5,  CarScanData.NONE, CarScanData.NONE,  CarScanData.NONE, null, null)
        }),
        new CarScanData( -15.0, 0.75,  CarScanData.LEFT, CarScanData.ACCELERATE,  CarScanData.NONE, null, new CarScanData[] {
            new CarScanData( -15.0, 0.5, CarScanData.NONE,  CarScanData.NONE,  CarScanData.NONE, null, null),
            new CarScanData( -15.0, 0.25, CarScanData.NONE,  CarScanData.NONE,  CarScanData.NONE, null, null)
        }),
        new CarScanData(  15.0, 0.75, CarScanData.RIGHT, CarScanData.ACCELERATE,  CarScanData.NONE, null, new CarScanData[] {
            new CarScanData(  15.0, 0.5, CarScanData.NONE,  CarScanData.NONE,  CarScanData.NONE, null, null),
            new CarScanData(  15.0, 0.25, CarScanData.NONE,  CarScanData.NONE,  CarScanData.NONE, null, null)
        }),
        new CarScanData( -30.0, 0.5,  CarScanData.LEFT, CarScanData.ACCELERATE,  CarScanData.NONE, null, new CarScanData[] {
            new CarScanData( -30.0, 0.25, CarScanData.NONE,  CarScanData.NONE,  CarScanData.NONE, null, null)
        }),
        new CarScanData(  30.0, 0.5, CarScanData.RIGHT, CarScanData.ACCELERATE,  CarScanData.NONE, null, new CarScanData[] {
            new CarScanData(  30.0, 0.25, CarScanData.NONE,  CarScanData.NONE,  CarScanData.NONE, null, null)
        }),
        new CarScanData( -135.0, 1.0,  CarScanData.LEFT,     CarScanData.BREAK,  CarScanData.NONE, null, null),
        new CarScanData(  135.0, 1.0, CarScanData.RIGHT,     CarScanData.BREAK,  CarScanData.NONE, null, null)
    };

    public CarScanDataInterface[] getScanData() {
        return scanData;
    }
}
