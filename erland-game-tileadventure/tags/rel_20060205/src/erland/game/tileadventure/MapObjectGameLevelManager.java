package erland.game.tileadventure;

import erland.util.*;
import erland.game.tileadventure.rect.RectBlockContainerData;
import erland.game.BlockContainerInterface;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Map;
import java.util.HashMap;

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

public class MapObjectGameLevelManager extends LevelManager {
    private Map cacheMap = new HashMap();
    private AnimationGameLevelManager animationLevelManager;
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
        return "objects";
    }

    protected String getObjectName() {
        return "object";
    }
    public Image getImage() {
        TileGameEnvironmentInterface env = ((TileGameEnvironmentInterface)getEnvironment().getCustomEnvironment());
        SubImageHandler subImageHandler = env.createSubImageHandler(null);
        IrregularBlockContainerInterface cont = env.createRectBlockContainer(0,0,1,1,1);
        boolean bEnd = false;
        int i=0;
        while(!bEnd) {
            LevelInfoInterface level = getLevel(++i);
            if(level!=null) {
                MapObjectContainerInterface map = level.getObjects();
                MapEditorObject o = (MapEditorObject) map.getBlock(0,0,0);
                o.init(getEnvironment());
                o.setContainer(cont);
                o.setPos(0,0,0);
                o.draw(subImageHandler.getGraphics(i-1));
            }else {
                bEnd = true;
            }
        }
        return subImageHandler.getImage();
    }

    public LevelInfoInterface createLevel(int level) {
        // This cannot create new levels
        return null;
    }

    public LevelInfoInterface getLevel(int level) {
        LevelInfo levelInfo = getFromCache(level);
        if(levelInfo!=null) {
            return cloneLevel(levelInfo);
        }
        StorageInterface objectStorage = LevelManagerHelper.load(getEnvironment().getStorage(),getGroupName(),getObjectName(),""+level);
        String prefix = ((TileGameEnvironmentInterface)getEnvironment().getCustomEnvironment()).getTileType();
        if(objectStorage!=null) {
            MapObjectContainerInterface map = new MapBlockContainer(1,1,1);
            MapObjectExtendedLevelInfo info = new MapObjectExtendedLevelInfo();
            ParameterValueStorageExInterface objectParameters = new ParameterStorageStringEx(objectStorage,null,getObjectName());
            info.read(objectParameters);
            MapEditorObject obj = new MapEditorObject();
            obj.init(getEnvironment());
            obj.setContainer(getContainer());
            obj.read(objectParameters);

            GameObject o = getObject(obj,info);
            o.setContainer(getContainer());
            o.setObjectMap(map);
            o.setPos(obj.getPosX(),obj.getPosY(),obj.getPosZ());
            map.setBlock(o,(int)o.getPosX(),(int)o.getPosY(),(int)o.getPosZ());
            levelInfo = new LevelInfo(map, null);
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
            newLevelInfo = new MapObjectExtendedLevelInfo();
            newLevelInfo.write(storage);
        }
        return new LevelInfo(newBlock,newLevelInfo);
    }
    private MapObjectContainerInterface cloneObjects(MapObjectContainerInterface objects) {
        MapObjectContainerInterface newMap = new MapBlockContainer(objects.getSizeX(),objects.getSizeY(),objects.getSizeZ());
        for (int x = 0; x < newMap.getSizeX(); x++) {
            for (int y = 0; y < newMap.getSizeY(); y++) {
                for (int z = 0; z < newMap.getSizeZ(); z++) {
                    MapObjectInterface o = objects.getBlock(x,y,z);
                    if(o!=null) {
                        GameObject newObj = (GameObject) o.clone();
                        newObj.setObjectMap(newMap);
                        newMap.setBlock(newObj,x,y,z);
                    }
                }
            }
        }
        return newMap;
    }

    public void setLevel(int level, MapObjectContainerInterface blocks, ParameterSerializable extendedInfo) {

        StringStorage objectStorage = new StringStorage();
        ParameterValueStorageExInterface objectParameters = new ParameterStorageStringEx(objectStorage,null,null);
        MapEditorObject obj = (MapEditorObject) blocks.getBlock(0,0,0);
        if(obj!=null) {
            obj.write(objectParameters);
        }
        if(extendedInfo!=null) {
            extendedInfo.write(objectParameters);
        }

        LevelManagerHelper.store(getEnvironment().getStorage(),getGroupName(),getObjectName(),""+level,objectStorage.load() );
    }

    public GameObject getObject(MapEditorObject obj, MapObjectExtendedLevelInfo info) {
        GameObject o = null;
        if(animationLevelManager==null) {
            animationLevelManager = new AnimationGameLevelManager();
            animationLevelManager.init(getEnvironment());
        }
        LevelInfoInterface levelInfo = animationLevelManager.getLevel(obj.getBlockType());
        AnimationExtendedLevelInfo animationInfo = (AnimationExtendedLevelInfo) levelInfo.getExtendedInfo();
        String prefix = ((TileGameEnvironmentInterface)getEnvironment().getCustomEnvironment()).getTileType();
        if(info.getPushable().booleanValue()) {
            MovableObject tmp = new MovableObject();
            tmp.init(getEnvironment());
            tmp.setAnimation(prefix+"ground.gif",new Animation(animationInfo.getSpeed(),animationInfo.getImages()));
            o = tmp;
        }else if(animationInfo.getImages().length>1) {
            AnimatedObject tmp = new AnimatedObject();
            tmp.init(getEnvironment());
            tmp.setAnimation(prefix+"ground.gif",new Animation(animationInfo.getSpeed(),animationInfo.getImages()));
            o = tmp;
        }else {
            BitmapObject tmp = new BitmapObject();
            tmp.init(getEnvironment());
            tmp.setImage(prefix+"ground.gif",obj.getBlockType()-1);
            o = tmp;
        }
        return o;
    }
}