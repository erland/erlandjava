package erland.game.racer;


import java.util.Vector;
import java.awt.*;

/**
 * Implements a car control manager that controls a number of cars by
 * scanning the ground for friction and chooses the way with least friction
 * @author Erland Isaksson
 */
public class CarControlManager implements CarControlManagerInterface {
    /** Friction object container */
    private FrictionObjectContainerInterface cont;
    /** Vector with all cars that should be controlled */
    private Vector cars;
    /** Vector with scan data for all cars that should be controlled */
    private Vector carData;
    /** Last calculated friction */
    private double lastFriction;
    /** Vector with all cars that should be checked for collision */
    private Vector collisionCars;

    /**
     * Handles the car and scandata for a car
     */
    private class CarData {
        /**
         * Creates a new object
         * @param car The car associated with this object
         * @param scanData The scan data associated with this object
         */
        public CarData(Car car,CarScanDataContainerInterface scanData) {
            this.car = car;
            this.scanData = scanData;
        }
        public Car car;
        public final CarScanDataContainerInterface scanData;
    }

    /**
     * Creates a new object
     */
    public CarControlManager()
    {
        cars = new Vector();
        carData = new Vector();
        collisionCars = new Vector();
    }

    public void setFrictionObjectContainer(FrictionObjectContainerInterface frictionContainer) {
        this.cont = frictionContainer;
    }

    /**
     * Add a new car which should be controlled
     * @param car The car object
     * @param scanData The scan data for the car object
     */
    public void addCar(Car car,final CarScanDataContainerInterface scanData)
    {
        if(!cars.contains(car)) {
            cars.addElement(car);
            carData.addElement(new CarData(car,scanData));
        }
    }
    /**
     * Add a new car which collistion should be checked with
     * @param car The car object
     */
    public void addCollisionCar(Car car)
    {
        if(!collisionCars.contains(car)) {
            collisionCars.addElement(car);
        }
    }
    /**
     * Remove a previously added car which collision should be checked with
     * @param car The car object
     */
    public void removeCollisionCar(Car car)
    {
        collisionCars.removeElement(car);
    }
    /**
     * Remove a previously added car which should be controlled
     * @param car The car object
     */
    public void removeCar(Car car)
    {
        cars.removeElement(car);
        for(int i=0;i<cars.size();i++) {
            CarData data = (CarData)carData.elementAt(i);
            if(data.car == car) {
                carData.removeElement(data);
            }
        }
    }

    /**
     * Check if one car with an offset would collide with another car
     * @param car1 The car which we control
     * @param car2 The car which we should check collision with
     * @param x The x offset of the controlled car
     * @param y The y offset of the controlled car
     * @return true/false (Collsion/Not collision)
     */
    protected boolean checkCollision(Car car1,Car car2,int x,int y)
    {
        Rectangle rc1 = car1.getBounds(x,y);
        Rectangle rc2 = car2.getBounds();
        if(rc1!=null) {
            if(rc1.intersects(rc2) || CommonRectangle.contains(rc1,rc2) || CommonRectangle.contains(rc2,rc1)) {
                return true;
            }
        }
        return false;
    }
    /**
     * Calculate friction of a car with a specific offset agains a friction object
     * @param car The car object
     * @param friction The friction object
     * @param x The x offset from the car
     * @param y The y offset from the car
     * @return The friction
     */
    protected double checkFriction(Car car,FrictionObjectInterface friction,int x, int y)
    {
        Rectangle rc1 = car.getBounds(x,y);
        if(rc1==null) {
            return 10.0;
        }
        Rectangle rc2 = friction.getBounds();
        if(rc1.intersects(rc2) || CommonRectangle.contains(rc1,rc2) || CommonRectangle.contains(rc2,rc1)) {
            return friction.getFriction();
        }else {
            return 0;
        }
    }

    /**
     * Calculate the friction of a car with offset against the friction objects close to the car
     * @param car The car object
     * @param angleChange The angle offset from the car angle that should be checked
     * @param scanLength The offset in car angle plus angle offset that should be checked
     * @param speedScanLength The speed multiplier offset that should be checked
     * @param carCollisions Indicates if collision with other cars should be checked
     * @return The calculated friction
     */
    protected double getFriction(Car car, double angleChange, double scanLength, double speedScanLength,boolean carCollisions)
    {
        int x = car.getPosX();
        int y = car.getPosY();
        double angle = car.normalizeAngle(car.getCarAngle()+angleChange);
        double speed = car.getSpeed();

        int newY = y+(int)((scanLength+speedScanLength*speed)*Math.sin((angle*Math.PI)/180));
        int newX = x+(int)((scanLength+speedScanLength*speed)*Math.cos((angle*Math.PI)/180));

        FrictionObjectInterface frictions[] = cont.getFrictionObjects(car,newX,newY);
        double maxFriction = 0;
        for(int j=0;j<frictions.length && frictions[j]!=null;j++) {
            double currentFriction = checkFriction(car,frictions[j],newX,newY);
            if(currentFriction>maxFriction) {
                maxFriction=currentFriction;
            }
        }
        if(carCollisions) {
            for(int j=0;j<collisionCars.size();j++) {
                Car c = (Car)collisionCars.elementAt(j);
                if(c!=null && car!=c) {
                    boolean collision = checkCollision(car,c,newX,newY);
                    if(collision && maxFriction<0.5) {
                        maxFriction=0.5;
                    }
                }
            }
        }
        return maxFriction;

    }

    /**
     * Perform action on the car
     * @param car The car object
     * @param accelerate Indicates if acceleration or braking should be performed
     * @param turn Indicates if left or right turn should be performed
     * @param friction Indicates the calculated friction
     */
    protected void performAction(Car car, int accelerate, int turn,double friction)
    {
        switch(accelerate) {
           case CarScanDataInterface.ACCELERATE:
                car.accelerating();
                break;
           case CarScanDataInterface.BREAK:
                if(friction>0) {
                    car.braking();
                }
                break;
           default:
                break;
        }

        switch(turn) {
            case CarScanDataInterface.LEFT:
                car.turnLeft();
                break;
            case CarScanDataInterface.RIGHT:
                car.turnRight();
                break;
            default:
                break;
        }
    }

    /**
     * Calculate friction for a car against specific scan data
     * @param car The car object
     * @param scanData The scan data
     * @param carCollisions Indicates if collision with other cars should be checked
     * @return The calculated friction
     */
    protected double getFriction(Car car,final CarScanDataInterface scanData,boolean carCollisions)
    {
        double friction = getFriction(car,scanData.getAngle(),scanData.getScanLength(),scanData.getSpeedScanLength(),carCollisions);
        if(scanData.getSubFrictionData() != null) {
            for(int i=0;i<scanData.getSubFrictionData().length;i++) {
                double subFriction = getFriction(car,scanData.getSubFrictionData()[i].getAngle(),scanData.getSubFrictionData()[i].getScanLength(),scanData.getSubFrictionData()[i].getSpeedScanLength(),carCollisions);
                if(subFriction>friction) {
                    friction = subFriction;
                }
            }
        }
        return friction;
    }

    /**
     * Calculates the prefered sub scan data
     * @param carData The car object
     * @param scanData The scan data
     * @param selectType Indicates how sub scan data should be choosen
     * @param carCollisions Indicates if collision with other cars should be checked
     * @return The index of the choosen sub scan data
     */
    protected int getPreferedDirection(CarData carData,final CarScanDataInterface scanData[],int selectType,boolean carCollisions)
    {
        double minFriction = 100;
        double equalFriction = minFriction;
        int minFrictionPos = -1;
        boolean bFirst=true;
        boolean bAllEqual = true;

        for(int i=0;i<scanData.length;i++) {
            double friction = getFriction(carData.car,scanData[i],carCollisions);
            if(bFirst) {
                bFirst = false;
                equalFriction = friction;
            }
            if(friction<minFriction) {
                minFriction=friction;
                minFrictionPos = i;
            }
            if(friction!=equalFriction) {
                bAllEqual = false;
            }
        }
        this.lastFriction = minFriction;
        if(selectType==CarScanDataInterface.CHOOSEONEIFSMALLEST && bAllEqual) {
            return -1;
        }
        return minFrictionPos;
    }

    /**
     * Control car by checking friction and collisions
     * @param carData The car object
     * @param scanData The scan data
     * @param selectType Indicates how sub scan data should be choosen
     * @param carCollisions Indicates if collision with other cars should be checked
     * @return The index of the choosen sub scan data
     */
    protected int controlCar(CarData carData, final CarScanDataInterface scanData[], int selectType,boolean carCollisions)
    {
        int pos = getPreferedDirection(carData,scanData,selectType,carCollisions);
        double friction = this.lastFriction;
        if(pos>=0) {
            performAction(carData.car,scanData[pos].getAccelerateBreak(),scanData[pos].getTurnLeftRight(),friction);

            if(scanData[pos].getSubDirectionData()!=null ) {
                controlCar(carData,scanData[pos].getSubDirectionData(),scanData[pos].getSubDirectionType(),carCollisions);
            }
        }
        this.lastFriction = friction;
        return pos;
    }

    /**
     * Controll all added cars
     * @param carCollisions Indicates if collisions with other cars should be checked
     */
    protected void handleCarControl(boolean carCollisions)
    {
        for(int i=0;i<carData.size();i++) {
            CarData carData = ((CarData)this.carData.elementAt(i));
            controlCar(carData,carData.scanData.getScanData(),CarScanDataInterface.CHOOSEONE,carCollisions);
            if(!carData.car.isAccelerating()) {
                if(lastFriction>0) {
                    carData.car.accelerating();
                }
            }

        }
    }
    public void controlCars() {
        handleCarControl(true);
    }


    /**
     * Draws scan data to the screen for debugging
     * @param g Graphics object to draw on
     * @param offsetX X offset to use when drawing
     * @param offsetY Y offset to use when drawing
     * @param car The car object
     * @param scanData The scan data
     * @param dynamicColors Indicates if different colors should be used for different frictions
     * @return Number of checked friction rectangles
     */
    protected int drawScanData(Graphics g, int offsetX, int offsetY,Car car,final CarScanDataInterface scanData,boolean dynamicColors)
    {
        int noOfRect = 0;
        int x = car.getPosX();
        int y = car.getPosY();
        double angle = car.normalizeAngle(car.getCarAngle()+scanData.getAngle());
        double speed = car.getSpeed();

        int newY = y+(int)((scanData.getScanLength()+scanData.getSpeedScanLength()*speed)*Math.sin((angle*Math.PI)/180));
        int newX = x+(int)((scanData.getScanLength()+scanData.getSpeedScanLength()*speed)*Math.cos((angle*Math.PI)/180));

        // Calculate friction
        double friction = getFriction(car,scanData.getAngle(),scanData.getScanLength(),scanData.getSpeedScanLength(),true);
        CarScanDataInterface subFrictionScanData[] =scanData.getSubFrictionData();
        if(subFrictionScanData != null) {
            for(int i=0;i<subFrictionScanData.length;i++) {
                noOfRect+=drawScanData(g,offsetX,offsetY,car,subFrictionScanData[i],dynamicColors);
                double subFriction = getFriction(car,subFrictionScanData[i].getAngle(),subFrictionScanData[i].getScanLength(),subFrictionScanData[i].getSpeedScanLength(),true);
                if(subFriction>friction) {
                    friction = subFriction;
                }
            }
        }

        // Get car rectangle which was checked for friction
        Rectangle rc = car.getBounds(newX,newY);
        if(g!=null && rc!=null) {
            if(dynamicColors) {
                if(friction == 0) {
                    g.setColor(Color.white);
                }else {
                    g.setColor(Color.gray);
                }
            }
            noOfRect++;
            g.drawRect(offsetX+(int)rc.getX(),offsetY+(int)rc.getY(), (int)rc.getWidth(), (int)rc.getHeight());
        }
        return noOfRect;
    }

    /**
     * Draw scan data for debugging on all controlled cars
     * @param g Graphics object to draw on
     * @param offsetX X offset to use when drawing
     * @param offsetY Y offset to use when drawing
     */
    public void draw(Graphics g, int offsetX, int offsetY)
    {
        for(int i=0;i<carData.size();i++) {
            int noOfRect = 0;
            CarData carData = ((CarData)this.carData.elementAt(i));
            CarScanDataInterface[] scanData = carData.scanData.getScanData();
            for(int j=0;j<scanData.length;j++) {
                noOfRect+=drawScanData(g,offsetX,offsetY,carData.car,scanData[j],true);
            }

            int pos = getPreferedDirection(carData,scanData,CarScanDataInterface.CHOOSEONE,true);
            if(pos>=0) {
                if(lastFriction ==0) {
                    g.setColor(Color.black);
                }else {
                    g.setColor(Color.red);
                }
                noOfRect+=drawScanData(g,offsetX,offsetY,carData.car,scanData[pos],false);
                if(scanData[pos].getSubDirectionData()!=null) {
                    int subPos = getPreferedDirection(carData,scanData[pos].getSubDirectionData(),scanData[pos].getSubDirectionType(),true);
                    if(subPos>=0) {
                        if(lastFriction==0) {
                            g.setColor(Color.black);
                        }else {
                            g.setColor(Color.red);
                        }
                        noOfRect+=drawScanData(g,offsetX,offsetY,carData.car,scanData[pos].getSubDirectionData()[subPos],false);
                    }
                }
                g.setColor(Color.white);
                g.drawString(String.valueOf(pos),carData.car.getPosX()+offsetX-20,carData.car.getPosY()+offsetY);
                g.drawString(String.valueOf(noOfRect),carData.car.getPosX()+offsetX-20,carData.car.getPosY()+offsetY-20);
            }
        }
    }

}
