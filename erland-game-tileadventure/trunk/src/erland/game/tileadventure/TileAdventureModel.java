package erland.game.tileadventure;
/*
 * Copyright (C) 2004 Erland Isaksson (erland_i@hotmail.com)
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 2
 * of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA  02111-1307, USA.
 *
 */

import erland.game.GameEnvironmentInterface;
import erland.game.tileadventure.isodiamond.IsoDiamondDrawMap;
import erland.game.tileadventure.rect.RectDrawMap;

import java.util.*;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * Implementation of the main game model that handles all the logic in the game
 */
public class TileAdventureModel implements GameObjectMapActionInterface, ActionHandlerInterface {
    /** Logging instance */
    private static Log LOG = LogFactory.getLog(TileAdventureModel.class);
    /** Block container for the game area */
    protected IrregularBlockContainerInterface cont;
    /** Game environment object */
    protected GameEnvironmentInterface environment;
    /** Room map */
    protected MapObjectContainerInterface roomMap;
    /** Object list */
    protected Vector objectList;
    /** A list of all players in the game */
    private GameObject[] players;
    /** A map with mapping between the object maps and rooms */
    private Map roomByBlockMap;
    /** A map with mapping between the object maps and moving maps */
    private Map movingMapByBlockMap;
    /** Mapping between a player and current room */
    private Map playerRoomMap;
    /** A list of all ongoing actions */
    private Map actionMap = new HashMap();

    /**
     * Initiate the model
     * @param environment Game environment
     * @param cont Block container for the track
     */
    public void init(GameEnvironmentInterface environment, IrregularBlockContainerInterface cont) {
        this.environment = environment;
        this.cont = cont;
        movingMapByBlockMap = new HashMap();
        playerRoomMap = new HashMap();
        roomByBlockMap = new HashMap();
        LevelManager levelManager = new WorldGameLevelManager();
        levelManager.init(environment);
        levelManager.setContainer(cont);
        LevelInfoInterface levelInfo = levelManager.getLevel(1);
        roomMap = levelInfo.getObjects();
        initRooms(roomMap);
    }
    private void initRooms(MapObjectContainerInterface rooms) {
        this.objectList = new Vector();
        for (int x = 0; x < rooms.getSizeX(); x++) {
            for (int y = 0; y < rooms.getSizeY(); y++) {
                for (int z = 0; z < rooms.getSizeZ(); z++) {
                    RoomObject room = (RoomObject) rooms.getBlock(x,y,z);
                    if(room!=null) {
                        initRoom(room,objectList);
                    }
                }
            }
        }
    }
    private void initPlayer(RoomObject room, GameObject player) {
        DrawMap map = ((TileGameEnvironmentInterface)(environment.getCustomEnvironment())).createBlockMap();
        map.setContainer(cont);
        map.addObjectMap(room.getBlocks());
        //map.addObjectMap((MapObjectContainerInterface) movingMapByBlockMap.get(room.getBlocks()));
        playerRoomMap.put(player,map);
    }
    private void initRoom(RoomObject room,List objectList) {
        MapObjectContainerInterface movingMap = new MapBlockContainer(room.getBlocks().getSizeX(),room.getBlocks().getSizeY(),room.getBlocks().getSizeZ());
        roomByBlockMap.put(room.getBlocks(),room);
        movingMapByBlockMap.put(room.getBlocks(),movingMap);
        MapObjectContainerInterface map = room.getBlocks();
        for (int x = 0; x < map.getSizeX(); x++) {
            for (int y = 0; y < map.getSizeY(); y++) {
                for (int z = 0; z < map.getSizeZ(); z++) {
                    GameObject o = (GameObject) map.getBlock(x,y,z);
                    if(o instanceof GameObjectUpdateInterface) {
                        o.setActionMap(this);
                        objectList.add(o);
                    }
                }
            }
        }
    }

    private RoomObject findStartRoom() {
        for (int x = 0; x < roomMap.getSizeX(); x++) {
            for (int y = 0; y < roomMap.getSizeY(); y++) {
                for (int z = 0; z < roomMap.getSizeZ(); z++) {
                    RoomObject room = (RoomObject) roomMap.getBlock(x,y,z);
                    if(room!=null) {
                        return room;
                    }
                }
            }
        }
        return null;
    }
    private void removePlayers(GameObject[] players) {
        if(players!=null) {
            for(int i=0;i<players.length;i++) {
                objectList.removeElement(players[i]);
                players[i].getObjectMap().removeBlock(players[i],(int)players[i].getPosX(),(int)players[i].getPosY(),(int)players[i].getPosZ());
                MapObjectContainerInterface movingMap = (MapObjectContainerInterface) movingMapByBlockMap.get(players[i].getObjectMap());
                movingMap.removeBlock(players[i]);
            }
        }
    }
    public void initPlayers(GameObject[] players) {
        removePlayers(this.players);
        this.players = players;
        RoomObject room = findStartRoom();
        MapObjectContainerInterface blockMap = room.getBlocks();
        for(int i=0;i<players.length;i++) {
            players[i].setObjectMap(blockMap);
            players[i].setActionMap(this);
            players[i].setPos(1,1,1);
            objectList.addElement(players[i]);
            blockMap.addObject(players[i],(int)players[i].getPosX(),(int)players[i].getPosY(),(int)players[i].getPosZ());
            initPlayer(room,players[i]);
        }
    }
    /**
     * Update model, this will move all cars, check for collisions and apply frictions
     */
    public void update() {
        if(objectList!=null) {
            for(int i=0;i<objectList.size();i++) {
                GameObjectUpdateInterface obj = (GameObjectUpdateInterface) objectList.elementAt(i);
                obj.update();
            }
        }
        for (Iterator it = actionMap.values().iterator(); it.hasNext();) {
            List list = (List) it.next();
            for(Iterator it2 = list.iterator();it2.hasNext();) {
                ActionInterface action = (ActionInterface) it2.next();
                action.start();
                if(action.perform()) {
                    action.stop();
                    it2.remove();
                }
            }
        }
    }

    public MapDrawInterface getMap(GameObject player) {
        return (MapDrawInterface) playerRoomMap.get(player);
    }
    private boolean isInsideMap(MapObjectContainerInterface map, float x, float y, float z) {
        if(x<map.getSizeX()&& y<map.getSizeY() && z<map.getSizeZ() && x>=0 && y>=0 && z>=0) {
            return true;
        }else {
            return false;
        }
    }

    public boolean isFree(MapObjectContainerInterface map,GameObject obj,float x, float y, float z) {
        if(isInsideMap(map,x,y,z)) {
            MapObjectInterface block = map.getBlock((int)x,(int)y,(int)z);
            if((block==null||block==obj)) {
                return true;
            }
        }
        return false;
    }

    public boolean isFree(GameObject obj,float x, float y, float z) {
        if(isInsideMap(obj.getObjectMap(),x,y,z)) {
            MapObjectInterface block = obj.getObjectMap().getBlock((int)x,(int)y,(int)z);
            //TODO: clean up
            //MapObjectContainerInterface movingMap = (MapObjectContainerInterface) movingMapByBlockMap.get(obj.getObjectMap());
            //MapObjectInterface movingObject = movingMap.getBlock((int)x,(int)y,(int)z);
            //if((block==null||block==obj)&&(movingObject==null||movingObject==obj)) {
            if(block==null || block==obj || !block.getBoundingBox().contains(x,y,z)) {
                return true;
            }
        }
        return false;
    }

    public boolean isFree(GameObject obj,Box3D boundingBox) {
        return isFree(obj,boundingBox.getMinX(),boundingBox.getMinY(),boundingBox.getMinZ()) &&
               isFree(obj,boundingBox.getMinX(),boundingBox.getMinY(),boundingBox.getMaxZ()) &&
               isFree(obj,boundingBox.getMinX(),boundingBox.getMaxY(),boundingBox.getMinZ()) &&
               isFree(obj,boundingBox.getMinX(),boundingBox.getMaxY(),boundingBox.getMaxZ()) &&
               isFree(obj,boundingBox.getMaxX(),boundingBox.getMinY(),boundingBox.getMinZ()) &&
               isFree(obj,boundingBox.getMaxX(),boundingBox.getMinY(),boundingBox.getMaxZ()) &&
               isFree(obj,boundingBox.getMaxX(),boundingBox.getMaxY(),boundingBox.getMinZ()) &&
               isFree(obj,boundingBox.getMaxX(),boundingBox.getMaxY(),boundingBox.getMaxZ());
    }

    public boolean move(GameObject obj, float x, float y, float z) {
        Box3D b = new Box3D();
        obj.fillBoundingBox(x,y,z,b);
        if(isFree(obj,b)) {
            Box3D c = obj.getBoundingBox();
            obj.getObjectMap().removeObject(obj,(int)c.getMinX(),(int)c.getMinY(),(int)c.getMinZ());
            obj.getObjectMap().removeObject(obj,(int)c.getMinX(),(int)c.getMinY(),(int)c.getMaxZ());
            obj.getObjectMap().removeObject(obj,(int)c.getMinX(),(int)c.getMaxY(),(int)c.getMinZ());
            obj.getObjectMap().removeObject(obj,(int)c.getMinX(),(int)c.getMaxY(),(int)c.getMaxZ());
            obj.getObjectMap().removeObject(obj,(int)c.getMaxX(),(int)c.getMinY(),(int)c.getMinZ());
            obj.getObjectMap().removeObject(obj,(int)c.getMaxX(),(int)c.getMinY(),(int)c.getMaxZ());
            obj.getObjectMap().removeObject(obj,(int)c.getMaxX(),(int)c.getMaxY(),(int)c.getMinZ());
            obj.getObjectMap().removeObject(obj,(int)c.getMaxX(),(int)c.getMaxY(),(int)c.getMaxZ());
            obj.setPos(x,y,z);
            c = obj.getBoundingBox();
            obj.getObjectMap().addObject(obj,(int)c.getMinX(),(int)c.getMinY(),(int)c.getMinZ());
            obj.getObjectMap().addObject(obj,(int)c.getMinX(),(int)c.getMinY(),(int)c.getMaxZ());
            obj.getObjectMap().addObject(obj,(int)c.getMinX(),(int)c.getMaxY(),(int)c.getMinZ());
            obj.getObjectMap().addObject(obj,(int)c.getMinX(),(int)c.getMaxY(),(int)c.getMaxZ());
            obj.getObjectMap().addObject(obj,(int)c.getMaxX(),(int)c.getMinY(),(int)c.getMinZ());
            obj.getObjectMap().addObject(obj,(int)c.getMaxX(),(int)c.getMinY(),(int)c.getMaxZ());
            obj.getObjectMap().addObject(obj,(int)c.getMaxX(),(int)c.getMaxY(),(int)c.getMinZ());
            obj.getObjectMap().addObject(obj,(int)c.getMaxX(),(int)c.getMaxY(),(int)c.getMaxZ());
            return true;
        }
        return false;
    }

    private float getMovingPosX(float x, float y, float z, Direction direction) {
        if(direction == Direction.WEST) {
            x--;
        }else if(direction == Direction.EAST) {
            x++;
        }
        return x;
    }
    private float getMovingPosY(float x, float y, float z, Direction direction) {
        if(direction == Direction.NORTH) {
            y--;
        }else if(direction == Direction.SOUTH) {
            y++;
        }
        return y;
    }
    private float getMovingPosZ(float x, float y, float z, Direction direction) {
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

    public ActionInterface isActionPossibleOnObject(GameObject obj, Action action) {
        MapObjectContainerInterface movingMap = (MapObjectContainerInterface) movingMapByBlockMap.get(obj.getObjectMap());
        MapObjectContainerInterface blockMap = (MapObjectContainerInterface) obj.getObjectMap();
        if(action.isMove()||action.isPush()) {
            Direction direction = action.getDirection();
            int newX=(int)getMovingPosX(obj.getPosX(),obj.getPosY(),obj.getPosZ(),direction);
            int newY=(int)getMovingPosY(obj.getPosX(),obj.getPosY(),obj.getPosZ(),direction);
            int newZ=(int)getMovingPosZ(obj.getPosX(),obj.getPosY(),obj.getPosZ(),direction);
            if(isInsideMap(blockMap,newX,newY,newZ)) {
                if(isFree(obj,newX,newY,newZ)) {
                    return action;
                }else {
                    GameObject nextObj = (GameObject)blockMap.getBlock(newX,newY,newZ);
                    if(nextObj!=null && nextObj.isMovable(action.getDirection())) {
                        //TODO: Push when move was requested, do we need to do anything special ?
                        return getPushActionForDirection(action.getDirection());
                    }
                }
            }else if(action.isMove()) {
                LOG.debug("Try changing room "+newX+","+newY+","+newZ);
                MapObjectContainerInterface currentRoomMap = obj.getObjectMap();
                RoomObject currentRoom = (RoomObject) roomByBlockMap.get(currentRoomMap);
                if(isFreeInNextRoom(currentRoom,newX,newY,newZ) && isAllowedToChangeRoom(obj)) {
                    return action;
                }
            }
        }else if(action==Action.JUMP) {
            if(isInsideMap(blockMap,obj.getPosX(),obj.getPosY(),obj.getPosZ()+1)) {
                if(isFree(obj,obj.getPosX(),obj.getPosY(),obj.getPosZ()+1)) {
                    return action;
                }
            }
        }else if(action==Action.DROP) {
            if(isInsideMap(blockMap,obj.getPosX(),obj.getPosY(),obj.getPosZ()-1)) {
                if(isFree(obj,obj.getPosX(),obj.getPosY(),obj.getPosZ()-1)) {
                    return action;
                }
            }
        }
        return Action.NONE;
    }
    public ActionInterface startActionOnObject(GameObject obj, Action action) {
        MapObjectContainerInterface movingMap = (MapObjectContainerInterface) movingMapByBlockMap.get(obj.getObjectMap());
        MapObjectContainerInterface blockMap = (MapObjectContainerInterface) obj.getObjectMap();
        if(action.isMove()||action.isPush()) {
            Direction direction = action.getDirection();
            int newX=(int)getMovingPosX(obj.getPosX(),obj.getPosY(),obj.getPosZ(),direction);
            int newY=(int)getMovingPosY(obj.getPosX(),obj.getPosY(),obj.getPosZ(),direction);
            int newZ=(int)getMovingPosZ(obj.getPosX(),obj.getPosY(),obj.getPosZ(),direction);
            if(isInsideMap(blockMap,newX,newY,newZ)) {
                if(isFree(obj,newX,newY,newZ)) {
                    movingMap.setBlock(obj,newX,newY,newZ);
                    return action;
                }else {
                    GameObject nextObj = (GameObject)blockMap.getBlock(newX,newY,newZ);
                    if(nextObj!=null && nextObj.isMovable(action.getDirection())) {
                        nextObj.action(getPushActionForDirection(action.getDirection()));
                        movingMap.setBlock(obj,newX,newY,newZ);
                        return getPushActionForDirection(action.getDirection());
                    }
                }
            }else if(action.isMove()) {
                LOG.debug("Try changing room "+newX+","+newY+","+newZ);
                MapObjectContainerInterface currentRoomMap = obj.getObjectMap();
                RoomObject currentRoom = (RoomObject) roomByBlockMap.get(currentRoomMap);
                if(isFreeInNextRoom(currentRoom,newX,newY,newZ) && isAllowedToChangeRoom(obj)) {
                    LOG.debug("Start changing room "+newX+","+newY+","+newZ);
                    movingMap.setBlock(obj,newX,newY,newZ);
                    return action;
                }
            }
        }else if(action==Action.JUMP) {
            if(isInsideMap(blockMap,obj.getPosX(),obj.getPosY(),obj.getPosZ()+1)) {
                if(isFree(obj,obj.getPosX(),obj.getPosY(),obj.getPosZ()+1)) {
                    movingMap.setBlock(obj,(int)obj.getPosX(),(int)obj.getPosY(),(int)obj.getPosZ()+1);
                    return action;
                }
            }
        }else if(action==Action.DROP) {
            if(isInsideMap(blockMap,obj.getPosX(),obj.getPosY(),obj.getPosZ()-1)) {
                if(isFree(obj,obj.getPosX(),obj.getPosY(),obj.getPosZ()-1)) {
                    movingMap.setBlock(obj,(int)obj.getPosX(),(int)obj.getPosY(),(int)obj.getPosZ()-1);
                    return action;
                }
            }
        }
        return Action.NONE;
    }

    public void endActionOnObject(GameObject obj, Action action) {
        MapObjectContainerInterface movingMap = (MapObjectContainerInterface) movingMapByBlockMap.get(obj.getObjectMap());
        MapObjectContainerInterface blockMap = (MapObjectContainerInterface) obj.getObjectMap();
        if(action.isMove() || action.isPush()) {
            Direction direction = action.getDirection();
            int newX = (int)getMovingPosX(obj.getPosX(),obj.getPosY(),obj.getPosZ(),direction);
            int newY = (int)getMovingPosY(obj.getPosX(),obj.getPosY(),obj.getPosZ(),direction);
            int newZ = (int)getMovingPosZ(obj.getPosX(),obj.getPosY(),obj.getPosZ(),direction);
            if(isInsideMap(blockMap,newX,newY,newZ)) {
                if(movingMap.getBlock(newX,newY,newZ)==obj) {
                    movingMap.removeBlock(obj,newX,newY,newZ);
                    obj.setPos(newX,newY,newZ);
                }
            }else {
                LOG.debug("Ending changing room "+newX+","+newY+","+newZ);
                MapObjectContainerInterface currentRoomMap = obj.getObjectMap();
                RoomObject currentRoom = (RoomObject) roomByBlockMap.get(currentRoomMap);
                if(isFreeInNextRoom(currentRoom,newX,newY,newZ) && isAllowedToChangeRoom(obj)) {
                    obj.setPos(newX,newY,newZ);
                    RoomObject room = findRoom(currentRoom,newX, newY, newZ);
                    if(newX<0) {
                        newX=room.getBlocks().getSizeX()-1;
                    }else if(newX>=currentRoomMap.getSizeX()) {
                        newX = 0;
                    }
                    if(newY<0) {
                        newY=room.getBlocks().getSizeY()-1;
                    }else if(newY>=currentRoomMap.getSizeY()) {
                        newY = 0;
                    }
                    if(newZ<0) {
                        newZ=room.getBlocks().getSizeZ()-1;
                    }else if(newZ>=currentRoomMap.getSizeZ()) {
                        newZ = 0;
                    }
                    initPlayer(room,obj);
                    obj.setObjectMap(room.getBlocks());
                    obj.setPos(newX,newY,newZ);
                    //objectList.add(obj);
                    LOG.debug("Changed room "+newX+","+newY+","+newZ);
                }
            }
        }else if(action==Action.JUMP) {
            if(isInsideMap(blockMap,obj.getPosX(),obj.getPosY(),obj.getPosZ()+1)) {
                LOG.debug("isInsideMap "+obj.getPosZ());
                if(movingMap.getBlock((int)obj.getPosX(),(int)obj.getPosY(),(int)obj.getPosZ()+1)==obj) {
                    LOG.debug("getBlock "+obj.getPosZ());
                    movingMap.removeBlock(obj,(int)obj.getPosX(),(int)obj.getPosY(),(int)obj.getPosZ()+1);
                    obj.setPos(obj.getPosX(),obj.getPosY(),obj.getPosZ()+1);
                }
            }
        }else if(action==Action.DROP) {
            if(isInsideMap(blockMap,obj.getPosX(),obj.getPosY(),obj.getPosZ()-1)) {
                LOG.debug("isInsideMap "+obj.getPosZ());
                if(movingMap.getBlock((int)obj.getPosX(),(int)obj.getPosY(),(int)obj.getPosZ()-1)==obj) {
                    LOG.debug("getBlock "+obj.getPosZ());
                    movingMap.removeBlock(obj,(int)obj.getPosX(),(int)obj.getPosY(),(int)obj.getPosZ()-1);
                    obj.setPos(obj.getPosX(),obj.getPosY(),obj.getPosZ()-1);
                }
            }
        }
    }
    private RoomObject findRoom(RoomObject currentRoom, float x, float y, float z) {
        int roomX = (int)currentRoom.getPosX();
        int roomY = (int)currentRoom.getPosY();
        int roomZ = (int)currentRoom.getPosZ();
        if(x<0) {
            roomX--;
        }else if(x>=currentRoom.getBlocks().getSizeX()) {
            roomX++;
        }
        if(y<0) {
            roomY--;
        }else if(y>=currentRoom.getBlocks().getSizeY()) {
            roomY++;
        }
        if(z<0) {
            roomZ--;
        }else if(z>=currentRoom.getBlocks().getSizeZ()) {
            roomZ++;
        }
        return (RoomObject) roomMap.getBlock(roomX,roomY,roomZ);
    }

    private boolean isFreeInNextRoom(RoomObject currentRoom, float x, float y, float z) {
        RoomObject room = findRoom(currentRoom, x,y,z);
        if(room!=null) {
            if(currentRoom.getPosX()>room.getPosX()) {
                x = room.getBlocks().getSizeX()-1;
            }else if(currentRoom.getPosX()<room.getPosX()) {
                x = 0;
            }
            if(currentRoom.getPosY()>room.getPosY()) {
                y = room.getBlocks().getSizeY()-1;
            }else if(currentRoom.getPosY()<room.getPosY()) {
                y = 0;
            }
            if(currentRoom.getPosZ()>room.getPosZ()) {
                z = room.getBlocks().getSizeZ()-1;
            }else if(currentRoom.getPosZ()<room.getPosZ()) {
                z = 0;
            }
            return isFree(room.getBlocks(),null,x,y,z);
        }
        return false;
    }
    private boolean isAllowedToChangeRoom(GameObject obj) {
        return obj instanceof LivingObject;
    }

    public void register(ActionInterface action) {
        List list = (List) actionMap.get(action.getController());
        if(list==null) {
            list = new ArrayList(10);
            actionMap.put(action.getController(),list);
        }
        for(Iterator it=list.iterator();it.hasNext();) {
            ActionInterface a = (ActionInterface) it.next();
            if(a.getClass().equals(action.getClass())) {
                a.stop();
                it.remove();
            }
        }
        list.add(action);
    }

    public void unregister(ActionInterface action) {
        List list = (List) actionMap.get(action.getController());
        if(list!=null) {
            list.remove(action);
        }
    }
}
