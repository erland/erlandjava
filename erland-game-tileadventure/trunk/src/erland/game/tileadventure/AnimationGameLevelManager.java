package erland.game.tileadventure;

import erland.util.*;
import erland.game.tileadventure.rect.RectBlockContainerData;
import erland.game.BlockContainerInterface;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Map;
import java.util.HashMap;
import java.util.Vector;

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

public class AnimationGameLevelManager extends LevelManager {
    private Map cacheMap = new HashMap();
    private Image paletteImage;
    protected int getDefaultSizeX() {
        return 1;
    }

    protected int getDefaultSizeY() {
        return 1;
    }

    protected int getDefaultSizeZ() {
        return 1;
    }

    protected String getGroupName() {
        return "animations";
    }

    protected String getObjectName() {
        return "animation";
    }
    protected String getPartName() {
        return "image";
    }

    public Image getImage() {
        // It is not possible to create an image with this level manager
        return null;
    }

    public LevelInfoInterface createLevel(int level) {
        // It is not possible to create new levels with this level manager
        return null;
    }

    public LevelInfoInterface getLevel(int level) {
        LevelInfo levelInfo = getFromCache(level);
        if(levelInfo!=null) {
            return cloneLevel(levelInfo);
        }
        StorageInterface animationStorage = LevelManagerHelper.load(getEnvironment().getStorage(),getGroupName(),getObjectName(),""+level);
        String prefix = ((TileGameEnvironmentInterface)getEnvironment().getCustomEnvironment()).getTileType();
        if(animationStorage!=null) {
            AnimationExtendedLevelInfo info = new AnimationExtendedLevelInfo();
            ParameterValueStorageExInterface animationParameters = new ParameterStorageStringEx(animationStorage,null,getObjectName());
            info.read(animationParameters);
            levelInfo = new LevelInfo(null,info);
            setInCache(level,levelInfo);
            return levelInfo;
        }else {
            return null;
        }
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
            newLevelInfo = new AnimationExtendedLevelInfo();
            newLevelInfo.write(storage);
        }
        return new LevelInfo(newBlock,newLevelInfo);
    }
    private MapObjectContainerInterface cloneObjects(MapObjectContainerInterface objects) {
        if(objects!=null) {
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
        return null;
    }

    public void setLevel(int level, MapObjectContainerInterface blocks, ParameterSerializable extendedInfo) {
        StringStorage animationStorage = new StringStorage();
        ParameterValueStorageExInterface animationParameters = new ParameterStorageGroupWithId(animationStorage, null, null, getPartName());
        int i = 1;
        for (int x = 0; x < blocks.getSizeX(); x++) {
            for (int y = 0; y < blocks.getSizeY(); y++) {
                for (int z = 0; z < blocks.getSizeZ(); z++) {
                    MapEditorObject o = (MapEditorObject) blocks.getObject(x, y, z);
                    if (o != null) {
                        MapEditorAnimationObject tmpObj = new MapEditorAnimationObject();
                        tmpObj.init(getEnvironment());
                        tmpObj.setBlockType(o.getBlockType());
                        StringStorage imageStorage = new StringStorage();
                        ParameterValueStorageExInterface imageParameters = new ParameterStorageStringEx(imageStorage, null, null);
                        tmpObj.write(imageParameters);
                        animationParameters.setParameter(getPartName() + "." + (i++), imageStorage.load());
                    }
                }
            }
        }
        if (extendedInfo != null) {
            extendedInfo.write(animationParameters);
        }
        LevelManagerHelper.store(getEnvironment().getStorage(),getGroupName(), getObjectName(), "" + level, animationStorage.load());
    }
}