package erland.game.racer;

import erland.game.*;

/**
 * Implementation of the main game model that handles all the logic in the game
 */
public class RacerModel implements FrictionObjectContainerInterface {
    /** Block container for the game area */
    protected BlockContainerInterface cont;
    /** Cars in the game */
    protected Car cars[];
    /** The currently active track/level */
    protected BlockMap map;
    /** Game environment object */
    protected GameEnvironmentInterface environment;
    /** The collision manager managing collision between cars */
    protected CollisionManager collisionManager;
    /** The friction manager applying friction to all cars */
    protected FrictionManager frictionManager;
    /** Friction objects last used */
    protected FrictionObjectInterface[] frictionObjects;


    /**
     * Initiate the model
     * @param environment Game environment
     * @param cont Block container for the track
     */
    public void init(GameEnvironmentInterface environment, BlockContainerInterface cont) {
        this.environment = environment;

        this.cars = null;
        this.cont = cont;
        map = BlockMapFactory.create(environment,cont,1);

        frictionObjects = new FrictionObjectInterface[16*9];
    }
    /**
     * Initialize model with updated cars
     * @param cars An array of all cars that should be used in the model
     */
    public void initCars(Car[] cars) {
        this.cars = cars;
        this.collisionManager = new CollisionManager();
        this.frictionManager = new FrictionManager(this);
        for(int i=0;i<cars.length;i++) {
            collisionManager.addObject(cars[i]);
            frictionManager.addObject(cars[i]);
            cars[i].setContainer(cont);
            positionCar(cars[i],i,map.getStartDirection(),map.getStartPosX(),map.getStartPosY());
        }
    }

    /**
     * Position a car on the start grid
     * @param c The car to position
     * @param carNo The start position on the grid
     * @param direction The direction of the grid
     * @param x The x position of the grid
     * @param y The y position of the grid
     */
    protected void positionCar(Car c, int carNo,int direction,int x, int y)
    {
        switch(direction) {
            case 0:
                c.setServerCarAngle(0);
                c.setClientCarAngle(0);
                c.setServerMovingAngle(0);
                c.setClientMovingAngle(0);
                c.setServerPos(3*(x+1)*cont.getSquareSize()-(carNo/2)*cont.getSquareSize()-(int)(2.5*cont.getSquareSize()),
                        3*(y+1)*cont.getSquareSize()-(carNo%2)*cont.getSquareSize()-(int)(1.5*cont.getSquareSize()));
                c.setClientPos(3*(x+1)*cont.getSquareSize()-(carNo/2)*cont.getSquareSize()-(int)(2.5*cont.getSquareSize()),
                        3*(y+1)*cont.getSquareSize()-(carNo%2)*cont.getSquareSize()-(int)(1.5*cont.getSquareSize()));
                break;
            case 1:
                c.setServerCarAngle(270);
                c.setClientCarAngle(270);
                c.setServerMovingAngle(270);
                c.setClientMovingAngle(270);
                c.setServerPos(3*(x+1)*cont.getSquareSize()-(carNo%2)*cont.getSquareSize()-(int)(1.5*cont.getSquareSize()),
                        3*(y)*cont.getSquareSize()+(carNo/2)*cont.getSquareSize()+(int)(1.5*cont.getSquareSize()));
                c.setClientPos(3*(x+1)*cont.getSquareSize()-(carNo%2)*cont.getSquareSize()-(int)(1.5*cont.getSquareSize()),
                        3*(y)*cont.getSquareSize()+(carNo/2)*cont.getSquareSize()+(int)(1.5*cont.getSquareSize()));
                break;
            case 2:
                c.setServerCarAngle(180);
                c.setClientCarAngle(180);
                c.setServerMovingAngle(180);
                c.setClientMovingAngle(180);
                c.setServerPos(3*(x)*cont.getSquareSize()+(carNo/2)*cont.getSquareSize()+(int)(1.5*cont.getSquareSize()),
                        3*(y+1)*cont.getSquareSize()-(carNo%2)*cont.getSquareSize()-(int)(1.5*cont.getSquareSize()));
                c.setClientPos(3*(x)*cont.getSquareSize()+(carNo/2)*cont.getSquareSize()+(int)(1.5*cont.getSquareSize()),
                        3*(y+1)*cont.getSquareSize()-(carNo%2)*cont.getSquareSize()-(int)(1.5*cont.getSquareSize()));
                break;
            case 3:
                c.setServerCarAngle(90);
                c.setClientCarAngle(90);
                c.setServerMovingAngle(90);
                c.setClientMovingAngle(90);
                c.setServerPos(3*(x+1)*cont.getSquareSize()-(carNo%2)*cont.getSquareSize()-(int)(1.5*cont.getSquareSize()),
                        3*(y+1)*cont.getSquareSize()-(carNo/2)*cont.getSquareSize()-(int)(2.5*cont.getSquareSize()));
                c.setClientPos(3*(x+1)*cont.getSquareSize()-(carNo%2)*cont.getSquareSize()-(int)(1.5*cont.getSquareSize()),
                        3*(y+1)*cont.getSquareSize()-(carNo/2)*cont.getSquareSize()-(int)(2.5*cont.getSquareSize()));
                break;
            default:
                break;
        }
    }

    /**
     * Update model, this will move all cars, check for collisions and apply frictions
     */
    public void update() {
        for(int i=0;i<cars.length;i++) {
            cars[i].serverUpdate();
        }
        frictionManager.handleFrictions();
        collisionManager.handleCollisions();
    }


    public FrictionObjectInterface[] getFrictionObjects(FrictionSensitiveInterface obj) {
        if(obj instanceof Car) {
            Car c = (Car)obj;

            return getFrictionObjects(obj,c.getPosX(),c.getPosY());
        }
        return frictionObjects;
    }

    public FrictionObjectInterface[] getFrictionObjects(FrictionSensitiveInterface obj,int objX, int objY) {
        if(obj instanceof Car) {
            Block[][] blocks = map.getBlocks(0);
            int blockX = objX/cont.getSquareSize();
            int blockY = objY/cont.getSquareSize();
            int i=0;
            for(int x=blockX-1;x<=blockX+1;x++) {
                for(int y=blockY-1;y<=blockY+1;y++) {
                    if(x>=0 && x<blocks.length && y>=0 && y<blocks[0].length) {
                        FrictionObjectInterface[] frictions = blocks[x][y].getFrictionObjects();
                        for(int j=0;j<frictions.length;j++) {
                            frictionObjects[i++] = frictions[j];
                        }
                    }
                }
            }
            for(;i<16*9;i++) {
                frictionObjects[i]=null;
            }
        }
        return frictionObjects;
    }

    /**
     * Get currently active track/level
     * @return Currently active {@link BlockMap}
     */
    public BlockMapDrawInterface getMap() {
        return map;
    }
}
