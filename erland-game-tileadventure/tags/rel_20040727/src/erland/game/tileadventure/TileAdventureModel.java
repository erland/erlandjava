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

import java.util.Vector;
import java.util.Map;
import java.util.List;
import java.util.HashMap;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * Implementation of the main game model that handles all the logic in the game
 */
public class TileAdventureModel implements GameObjectMapActionInterface {
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
                    RoomObject room = (RoomObject) rooms.getObject(x,y,z);
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
        map.addObjectMap((MapObjectContainerInterface) movingMapByBlockMap.get(room.getBlocks()));
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
                    GameObject o = (GameObject) map.getObject(x,y,z);
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
                    RoomObject room = (RoomObject) roomMap.getObject(x,y,z);
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
                players[i].getObjectMap().removeObject(players[i],players[i].getPosX(),players[i].getPosY(),players[i].getPosZ());
                MapObjectContainerInterface movingMap = (MapObjectContainerInterface) movingMapByBlockMap.get(players[i].getObjectMap());
                movingMap.removeObject(players[i]);
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
            blockMap.setObject(players[i],players[i].getPosX(),players[i].getPosY(),players[i].getPosZ());
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
    }

    public MapDrawInterface getMap(GameObject player) {
        return (MapDrawInterface) playerRoomMap.get(player);
    }
    private boolean isInsideMap(MapObjectContainerInterface map, int x, int y, int z) {
        if(x<map.getSizeX()&& y<map.getSizeY() && z<map.getSizeZ() && x>=0 && y>=0 && z>=0) {
            return true;
        }else {
            return false;
        }
    }

    public boolean isFree(MapObjectContainerInterface map,GameObject obj,int x, int y, int z) {
        if(isInsideMap(map,x,y,z)) {
            MapObjectInterface block = map.getObject(x,y,z);
            if((block==null||block==obj)) {
                return true;
            }
        }
        return false;
    }

    public boolean isFree(GameObject obj,int x, int y, int z) {
        if(isInsideMap(obj.getObjectMap(),x,y,z)) {
            MapObjectInterface block = obj.getObjectMap().getObject(x,y,z);
            MapObjectContainerInterface movingMap = (MapObjectContainerInterface) movingMapByBlockMap.get(obj.getObjectMap());
            MapObjectInterface movingObject = movingMap.getObject(x,y,z);
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

    public Action isActionPossibleOnObject(GameObject obj, Action action) {
        MapObjectContainerInterface movingMap = (MapObjectContainerInterface) movingMapByBlockMap.get(obj.getObjectMap());
        MapObjectContainerInterface blockMap = (MapObjectContainerInterface) obj.getObjectMap();
        if(action.isMove()||action.isPush()) {
            Direction direction = action.getDirection();
            int newX=getMovingPosX(obj.getPosX(),obj.getPosY(),obj.getPosZ(),direction);
            int newY=getMovingPosY(obj.getPosX(),obj.getPosY(),obj.getPosZ(),direction);
            int newZ=getMovingPosZ(obj.getPosX(),obj.getPosY(),obj.getPosZ(),direction);
            if(isInsideMap(blockMap,newX,newY,newZ)) {
                if(isFree(obj,newX,newY,newZ)) {
                    return action;
                }else {
                    GameObject nextObj = (GameObject)blockMap.getObject(newX,newY,newZ);
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
    public Action startActionOnObject(GameObject obj, Action action) {
        MapObjectContainerInterface movingMap = (MapObjectContainerInterface) movingMapByBlockMap.get(obj.getObjectMap());
        MapObjectContainerInterface blockMap = (MapObjectContainerInterface) obj.getObjectMap();
        if(action.isMove()||action.isPush()) {
            Direction direction = action.getDirection();
            int newX=getMovingPosX(obj.getPosX(),obj.getPosY(),obj.getPosZ(),direction);
            int newY=getMovingPosY(obj.getPosX(),obj.getPosY(),obj.getPosZ(),direction);
            int newZ=getMovingPosZ(obj.getPosX(),obj.getPosY(),obj.getPosZ(),direction);
            if(isInsideMap(blockMap,newX,newY,newZ)) {
                if(isFree(obj,newX,newY,newZ)) {
                    movingMap.setObject(obj,newX,newY,newZ);
                    return action;
                }else {
                    GameObject nextObj = (GameObject)blockMap.getObject(newX,newY,newZ);
                    if(nextObj!=null && nextObj.isMovable(action.getDirection())) {
                        nextObj.action(getPushActionForDirection(action.getDirection()));
                        movingMap.setObject(obj,newX,newY,newZ);
                        return getPushActionForDirection(action.getDirection());
                    }
                }
            }else if(action.isMove()) {
                LOG.debug("Try changing room "+newX+","+newY+","+newZ);
                MapObjectContainerInterface currentRoomMap = obj.getObjectMap();
                RoomObject currentRoom = (RoomObject) roomByBlockMap.get(currentRoomMap);
                if(isFreeInNextRoom(currentRoom,newX,newY,newZ) && isAllowedToChangeRoom(obj)) {
                    LOG.debug("Start changing room "+newX+","+newY+","+newZ);
                    movingMap.setObject(obj,newX,newY,newZ);
                    return action;
                }
            }
        }else if(action==Action.JUMP) {
            if(isInsideMap(blockMap,obj.getPosX(),obj.getPosY(),obj.getPosZ()+1)) {
                if(isFree(obj,obj.getPosX(),obj.getPosY(),obj.getPosZ()+1)) {
                    movingMap.setObject(obj,obj.getPosX(),obj.getPosY(),obj.getPosZ()+1);
                    return action;
                }
            }
        }else if(action==Action.DROP) {
            if(isInsideMap(blockMap,obj.getPosX(),obj.getPosY(),obj.getPosZ()-1)) {
                if(isFree(obj,obj.getPosX(),obj.getPosY(),obj.getPosZ()-1)) {
                    movingMap.setObject(obj,obj.getPosX(),obj.getPosY(),obj.getPosZ()-1);
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
            int newX = getMovingPosX(obj.getPosX(),obj.getPosY(),obj.getPosZ(),direction);
            int newY = getMovingPosY(obj.getPosX(),obj.getPosY(),obj.getPosZ(),direction);
            int newZ = getMovingPosZ(obj.getPosX(),obj.getPosY(),obj.getPosZ(),direction);
            if(isInsideMap(blockMap,newX,newY,newZ)) {
                if(movingMap.getObject(newX,newY,newZ)==obj) {
                    movingMap.removeObject(obj,newX,newY,newZ);
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
                if(movingMap.getObject(obj.getPosX(),obj.getPosY(),obj.getPosZ()+1)==obj) {
                    LOG.debug("getObject "+obj.getPosZ());
                    movingMap.removeObject(obj,obj.getPosX(),obj.getPosY(),obj.getPosZ()+1);
                    obj.setPos(obj.getPosX(),obj.getPosY(),obj.getPosZ()+1);
                }
            }
        }else if(action==Action.DROP) {
            if(isInsideMap(blockMap,obj.getPosX(),obj.getPosY(),obj.getPosZ()-1)) {
                LOG.debug("isInsideMap "+obj.getPosZ());
                if(movingMap.getObject(obj.getPosX(),obj.getPosY(),obj.getPosZ()-1)==obj) {
                    LOG.debug("getObject "+obj.getPosZ());
                    movingMap.removeObject(obj,obj.getPosX(),obj.getPosY(),obj.getPosZ()-1);
                    obj.setPos(obj.getPosX(),obj.getPosY(),obj.getPosZ()-1);
                }
            }
        }
    }
    private RoomObject findRoom(RoomObject currentRoom, int x, int y, int z) {
        int roomX = currentRoom.getPosX();
        int roomY = currentRoom.getPosY();
        int roomZ = currentRoom.getPosZ();
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
        return (RoomObject) roomMap.getObject(roomX,roomY,roomZ);
    }

    private boolean isFreeInNextRoom(RoomObject currentRoom, int x, int y, int z) {
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
}
