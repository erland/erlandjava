package erland.game.isoadventure;

import erland.game.GameEnvironmentInterface;

import java.util.Vector;

/**
 * Implementation of the main game model that handles all the logic in the game
 */
public class IsoAdventureModel implements IsoObjectMapActionInterface {
    /** Block container for the game area */
    protected IrregularBlockContainerInterface cont;
    /** Game environment object */
    protected GameEnvironmentInterface environment;
    /** Block map */
    protected IsoObjectMapInterface blockMap;
    /** Moving map */
    protected IsoObjectMap movingMap;
    /** Map object representing all objects and blocks */
    protected IsoMap map;
    /** Object list */
    protected Vector objectList;
    /** A list of all players in the game */
    private IsoObjectInterface[] players;


    /**
     * Initiate the model
     * @param environment Game environment
     * @param cont Block container for the track
     */
    public void init(GameEnvironmentInterface environment, IrregularBlockContainerInterface cont) {
        this.environment = environment;
        this.cont = cont;
        LevelManager levelManager = new LevelManager();
        levelManager.init(environment);
        levelManager.setContainer(cont);
        LevelInfoInterface levelInfo = levelManager.getLevel(1);
        blockMap = levelInfo.getMap();
        IsoObjectInterface[] objectList = levelInfo.getObjectList();
        this.objectList = new Vector();
        for(int i=0;i<objectList.length;i++) {
            this.objectList.addElement(objectList[i]);
            objectList[i].setObjectMap(blockMap);
            objectList[i].setActionMap(this);
            blockMap.addObject(objectList[i],objectList[i].getPosX(),objectList[i].getPosY(),objectList[i].getPosZ());
        }
        map = new IsoMap();
        map.setContainer(cont);
        map.addObjectMap(blockMap);
        movingMap = new IsoObjectMap(blockMap.getSizeX(),blockMap.getSizeY(),blockMap.getSizeZ());
        map.addObjectMap(movingMap);
    }

    private void removePlayers(IsoObjectInterface[] players) {
        if(players!=null) {
            for(int i=0;i<players.length;i++) {
                objectList.removeElement(players[i]);
                blockMap.removeObject(players[i],players[i].getPosX(),players[i].getPosY(),players[i].getPosZ());
                movingMap.removeObjectFromMap(players[i]);
            }
        }
    }
    public void initPlayers(IsoObjectInterface[] players) {
        removePlayers(this.players);
        this.players = players;
        for(int i=0;i<players.length;i++) {
            players[i].setObjectMap(blockMap);
            players[i].setActionMap(this);
            players[i].setPos(1,1,1);
            objectList.addElement(players[i]);
            blockMap.addObject(players[i],players[i].getPosX(),players[i].getPosY(),players[i].getPosZ());
        }
    }
    /**
     * Update model, this will move all cars, check for collisions and apply frictions
     */
    public void update() {
        if(objectList!=null) {
            for(int i=0;i<objectList.size();i++) {
                IsoObjectInterface obj = (IsoObjectInterface) objectList.elementAt(i);
                obj.update();
            }
        }
    }

    public MapDrawInterface getMap() {
        return map;
    }

    private boolean isInsideMap(int x, int y, int z) {
        if(x<blockMap.getSizeX()&& y<blockMap.getSizeY() && z<blockMap.getSizeZ() && x>=0 && y>=0 && z>=0) {
            return true;
        }else {
            return false;
        }
    }

    public boolean isFree(IsoObjectInterface obj,int x, int y, int z) {
        if(isInsideMap(x,y,z)) {
            IsoObjectInterface block = blockMap.getObject(x,y,z);
            IsoObjectInterface movingObject = movingMap.getObject(x,y,z);
            if((block==null||block==obj)&&(movingObject==null||movingObject==obj)) {
                return true;
            }
        }
        return false;
    }

    private int getMovingPosX(int x, int y, int z, Direction direction) {
        if(direction == Direction.WEST) {
            x--;
        }else if(direction == Direction.EAST) {
            x++;
        }
        return x;
    }
    private int getMovingPosY(int x, int y, int z, Direction direction) {
        if(direction == Direction.NORTH) {
            y--;
        }else if(direction == Direction.SOUTH) {
            y++;
        }
        return y;
    }
    private int getMovingPosZ(int x, int y, int z, Direction direction) {
        if(direction == Direction.DOWN) {
            z--;
        }else if(direction == Direction.UP) {
            z++;
        }
        return z;
    }

    private Action getPushActionForDirection(Direction direction) {
        if(direction==Direction.WEST) {
            return Action.PUSH_WEST;
        }else if(direction==Direction.EAST) {
            return Action.PUSH_EAST;
        }else if(direction==Direction.NORTH) {
            return Action.PUSH_NORTH;
        }else if(direction==Direction.SOUTH) {
            return Action.PUSH_SOUTH;
        }else {
            return Action.NONE;
        }
    }

    public Action startActionOnObject(IsoObjectInterface obj, Action action) {
        if(action.isMove()||action.isPush()) {
            Direction direction = action.getDirection();
            int newX=getMovingPosX(obj.getPosX(),obj.getPosY(),obj.getPosZ(),direction);
            int newY=getMovingPosY(obj.getPosX(),obj.getPosY(),obj.getPosZ(),direction);
            int newZ=getMovingPosZ(obj.getPosX(),obj.getPosY(),obj.getPosZ(),direction);
            if(isInsideMap(newX,newY,newZ)) {
                if(isFree(obj,newX,newY,newZ)) {
                    movingMap.addObject(obj,newX,newY,newZ);
                    return action;
                }else {
                    IsoObjectInterface nextObj = blockMap.getObject(newX,newY,newZ);
                    if(nextObj!=null && nextObj.isMovable(action.getDirection())) {
                        nextObj.action(getPushActionForDirection(action.getDirection()));
                        movingMap.addObject(obj,newX,newY,newZ);
                        return getPushActionForDirection(action.getDirection());
                    }
                }
            }
        }else if(action==Action.JUMP) {
            if(isInsideMap(obj.getPosX(),obj.getPosY(),obj.getPosZ()+1)) {
                if(isFree(obj,obj.getPosX(),obj.getPosY(),obj.getPosZ()+1)) {
                    movingMap.addObject(obj,obj.getPosX(),obj.getPosY(),obj.getPosZ()+1);
                    return action;
                }
            }
        }else if(action==Action.DROP) {
            if(isInsideMap(obj.getPosX(),obj.getPosY(),obj.getPosZ()-1)) {
                if(isFree(obj,obj.getPosX(),obj.getPosY(),obj.getPosZ()-1)) {
                    movingMap.addObject(obj,obj.getPosX(),obj.getPosY(),obj.getPosZ()-1);
                    return action;
                }
            }
        }
        return Action.NONE;
    }

    public void endActionOnObject(IsoObjectInterface obj, Action action) {
        if(action.isMove() || action.isPush()) {
            Direction direction = action.getDirection();
            int newX = getMovingPosX(obj.getPosX(),obj.getPosY(),obj.getPosZ(),direction);
            int newY = getMovingPosY(obj.getPosX(),obj.getPosY(),obj.getPosZ(),direction);
            int newZ = getMovingPosZ(obj.getPosX(),obj.getPosY(),obj.getPosZ(),direction);
            if(isInsideMap(newX,newY,newZ)) {
                if(movingMap.getObject(newX,newY,newZ)==obj) {
                    movingMap.removeObject(obj,newX,newY,newZ);
                    obj.setPos(newX,newY,newZ);
                }
            }
        }else if(action==Action.JUMP) {
            if(isInsideMap(obj.getPosX(),obj.getPosY(),obj.getPosZ()+1)) {
                System.out.println("isInsideMap "+obj.getPosZ());
                if(movingMap.getObject(obj.getPosX(),obj.getPosY(),obj.getPosZ()+1)==obj) {
                    System.out.println("getObject "+obj.getPosZ());
                    movingMap.removeObject(obj,obj.getPosX(),obj.getPosY(),obj.getPosZ()+1);
                    obj.setPos(obj.getPosX(),obj.getPosY(),obj.getPosZ()+1);
                }
            }
        }else if(action==Action.DROP) {
            if(isInsideMap(obj.getPosX(),obj.getPosY(),obj.getPosZ()-1)) {
                System.out.println("isInsideMap "+obj.getPosZ());
                if(movingMap.getObject(obj.getPosX(),obj.getPosY(),obj.getPosZ()-1)==obj) {
                    System.out.println("getObject "+obj.getPosZ());
                    movingMap.removeObject(obj,obj.getPosX(),obj.getPosY(),obj.getPosZ()-1);
                    obj.setPos(obj.getPosX(),obj.getPosY(),obj.getPosZ()-1);
                }
            }
        }
    }
}
