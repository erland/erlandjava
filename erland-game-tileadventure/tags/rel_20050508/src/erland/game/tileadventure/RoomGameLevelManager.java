package erland.game.tileadventure;

import erland.util.*;
import erland.game.tileadventure.rect.RectBlockContainerData;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

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

public class RoomGameLevelManager extends LevelManager {
    private Map cacheMap = new HashMap();
    protected int getDefaultSizeX() {
        return 10;
    }

    protected int getDefaultSizeY() {
        return 10;
    }

    protected int getDefaultSizeZ() {
        return 10;
    }

    protected String getGroupName() {
        return "rooms";
    }

    protected String getObjectName() {
        return "room";
    }

    protected String getPartName() {
        return "object";
    }

    public Image getImage() {
        // This level manager can not create images
        return null;
    }

    public LevelInfoInterface createLevel(int level) {
        // This level manager can not create levels
        return null;
    }
    public LevelInfoInterface getLevel(int level) {
        LevelInfo levelInfo = getFromCache(level);
        if(levelInfo!=null) {
            return cloneLevel(levelInfo);
        }
        StorageInterface roomStorage = LevelManagerHelper.load(getEnvironment().getStorage(),getGroupName(),getObjectName(),""+level);
        if (roomStorage != null) {
            RoomExtendedLevelInfo info = new RoomExtendedLevelInfo();
            info.setSizeX(getDefaultSizeX());
            info.setSizeY(getDefaultSizeY());
            info.setSizeZ(getDefaultSizeZ());
            MapObjectContainerInterface map = new MapBlockContainer(info.getSizeX(), info.getSizeY(), info.getSizeZ());
            String prefix = ((TileGameEnvironmentInterface) getEnvironment().getCustomEnvironment()).getTileType();
            ParameterValueStorageExInterface roomParameters = new ParameterStorageGroupWithId(roomStorage, null, getObjectName(), getPartName());
            info.read(roomParameters);
            map = new MapBlockContainer(info.getSizeX(), info.getSizeY(), info.getSizeZ());

            LevelManager objectLevelManager = new MapObjectGameLevelManager();
            objectLevelManager.init(getEnvironment());

            boolean bEnd = false;
            int i = 1;
            while (!bEnd) {
                StorageInterface objectStorage = roomParameters.getParameterAsStorage(getPartName() + "." + (i++));
                if (objectStorage != null) {
                    ParameterValueStorageExInterface objectParameters = new ParameterStorageString(objectStorage, null, getPartName());
                    MapEditorObject obj = new MapEditorObject();
                    obj.init(getEnvironment());
                    obj.setContainer(getContainer());
                    obj.read(objectParameters);

                    LevelInfoInterface objectInfo = objectLevelManager.getLevel(obj.getBlockType());
                    MapObjectContainerInterface objectMap = objectInfo.getObjects();
                    GameObject o = (GameObject) objectMap.getObject(0,0,0);
                    o.setContainer(getContainer());
                    o.setObjectMap(map);
                    o.setPos(obj.getPosX(),obj.getPosY(),obj.getPosZ());
                } else {
                    bEnd = true;
                }
            }
            levelInfo = new LevelInfo(map, info);
            setInCache(level,levelInfo);
            return levelInfo;
        }
        return null;
    }

    private LevelInfo getFromCache(int level) {
        return (LevelInfo) cacheMap.get(new Integer(level));
    }
    private void setInCache(int level, LevelInfo data) {
        cacheMap.put(new Integer(level),data);
    }

    private LevelInfo cloneLevel(LevelInfo level) {
        MapObjectContainerInterface newBlock = cloneObjects(level.getObjects());
        ParameterSerializable newLevelInfo = null;
        ParameterValueStorageExInterface storage = new ParameterStorageString(new StringStorage(),null);
        if(level.getExtendedInfo()!=null) {
            level.getExtendedInfo().read(storage);
            newLevelInfo = new RoomExtendedLevelInfo();
            newLevelInfo.write(storage);
        }
        return new LevelInfo(newBlock,newLevelInfo);
    }
    private MapObjectContainerInterface cloneObjects(MapObjectContainerInterface objects) {
        MapObjectContainerInterface newMap = new MapBlockContainer(objects.getSizeX(),objects.getSizeY(),objects.getSizeZ());
        for (int x = 0; x < newMap.getSizeX(); x++) {
            for (int y = 0; y < newMap.getSizeY(); y++) {
                for (int z = 0; z < newMap.getSizeZ(); z++) {
                    MapObjectInterface o = objects.getObject(x,y,z);
                    if(o!=null) {
                        GameObject newObj = (GameObject) o.clone();
                        newObj.setObjectMap(newMap);
                        newMap.setObject(newObj,x,y,z);
                    }
                }
            }
        }
        return newMap;
    }
    public void setLevel(int level, MapObjectContainerInterface blocks, ParameterSerializable extendedInfo) {

        StringStorage roomStorage = new StringStorage();
        ParameterValueStorageExInterface roomParameters = new ParameterStorageGroupWithId(roomStorage, null, null, getPartName());
        if (extendedInfo != null) {
            extendedInfo.write(roomParameters);
        }
        int i = 1;
        for (int x = 0; x < blocks.getSizeX(); x++) {
            for (int y = 0; y < blocks.getSizeY(); y++) {
                for (int z = 0; z < blocks.getSizeZ(); z++) {
                    MapEditorObject o = (MapEditorObject) blocks.getObject(x, y, z);
                    if (o != null) {
                        StringStorage objectStorage = new StringStorage();
                        ParameterValueStorageExInterface objectParameters = new ParameterStorageStringEx(objectStorage, null, null);
                        o.write(objectParameters);
                        roomParameters.setParameter(getPartName() + "." + (i++), objectStorage.load());
                    }
                }
            }
        }
        LevelManagerHelper.store(getEnvironment().getStorage(),getGroupName(), getObjectName(), "" + level, roomStorage.load());
    }
}