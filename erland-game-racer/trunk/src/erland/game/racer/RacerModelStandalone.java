/**
 * Created by IntelliJ IDEA.
 * User: Erland Isaksson
 * Date: Jan 3, 2003
 * Time: 2:25:49 PM
 * To change this template use Options | File Templates.
 */
package erland.game.racer;

import erland.game.GameEnvironmentInterface;
import erland.game.BlockContainerInterface;

/**
 * Implementation of a stand alone version of {@link RacerModelInterface}
 */
public class RacerModelStandalone implements RacerModelInterface {
    /** Opponent cars */
    private Car[] opponentCars;
    /** Handling of user car */
    private RacerPlayer player;
    /** Model object */
    private RacerModel model;
    /** Car control manager for computer controlled cars */
    private CarControlManager computerCarControlManager;
    /** Game environment */
    private GameEnvironmentInterface environment;
    /** Block container */
    private BlockContainerInterface cont;
    /** Indicates if game is initialized */
    private boolean bInitialized;
    /** Indicates if game is started */
    private boolean bStarted;

    public void startTurnLeft() {
        player.startTurnLeft();
    }

    public void startTurnRight() {
        player.startTurnRight();
    }

    public void startAccellerating() {
        if(!isStarted()) {
            start();
        }
        player.startAccellerating();
    }

    public void startBraking() {
        player.startBraking();
    }

    public void stopTurnLeft() {
        player.stopTurnLeft();
    }

    public void stopTurnRight() {
        player.stopTurnRight();
    }

    public void stopAccellerating() {
        player.stopAccellerating();
    }

    public void stopBraking() {
        player.stopBraking();
    }

    public void update() {
        if(isStarted()) {
            player.controlCars();
            computerCarControlManager.controlCars();
            model.update();
        }
    }
    private Car[] initComputerCars(int noOfCars) {
        Car[] computerCars = new Car[noOfCars];
        for(int i=0;i<computerCars.length;i++) {
            computerCars[i] = CarFactory.createServerCar(environment, cont, i+1,i);
        }
        return computerCars;
    }

    public void init(GameEnvironmentInterface environment, BlockContainerInterface cont) {
        this.environment = environment;
        this.cont = cont;
        player = new RacerPlayer(environment,0);
        model = new RacerModel();
        opponentCars = initComputerCars(4-1);
        Car cars[] = new Car[1+opponentCars.length];
        model = new RacerModel();
        computerCarControlManager = new CarControlManager();
        CarScanDataContainerStandard computerCarScanData = new CarScanDataContainerStandard();

        cars[0] = player.getCar();
        computerCarControlManager.addCollisionCar(cars[0]);
        player.setFrictionObjectContainer(model);

        for(int i= 1,j=0;i<cars.length;i++,j++) {
            cars[i] = opponentCars[j];
            computerCarControlManager.addCar(opponentCars[j], computerCarScanData);
            computerCarControlManager.addCollisionCar(cars[i]);
            computerCarControlManager.setFrictionObjectContainer(model);
        }
        model.init(environment,cont);
        model.initCars(cars);
        bInitialized = true;
    }

    public boolean isInitialized() {
        return bInitialized;
    }

    public void start() {
        bStarted = true;
    }

    public CarDrawInterface[] getOpponentCars() {
        return opponentCars;
    }

    public CarDrawInterface getMyCar() {
        return player.getCar();
    }

    public BlockMapDrawInterface getMap() {
        return model.getMap();
    }

    public boolean isEnd() {
        return false;
    }

    public boolean isGameOver() {
        return false;
    }

    public boolean isCompleted() {
        return false;
    }

    public boolean isStarted() {
        return bStarted;
    }

    public int getNoOfHumanCars() {
        return 1;
    }

    public boolean isMultiplayer() {
        return false;
    }
    public String getInfoString() {
        return "";
    }

    public void setCheatModeParameter(String parameter, String value) {
        return;
    }
}
