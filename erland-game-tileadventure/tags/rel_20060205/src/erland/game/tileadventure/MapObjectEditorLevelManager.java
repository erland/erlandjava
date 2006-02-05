package erland.game.tileadventure;

import erland.util.*;
import erland.game.tileadventure.rect.RectBlockContainerData;
import erland.game.BlockContainerInterface;

import java.awt.*;
import java.awt.image.BufferedImage;

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

public class MapObjectEditorLevelManager extends LevelManager {
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
        LevelInfoInterface levelInfo = getLevel(level);
        if(levelInfo==null) {
            MapObjectContainerInterface map = new MapBlockContainer(1,1,1);
            MapObjectExtendedLevelInfo info = new MapObjectExtendedLevelInfo();
            levelInfo = new LevelInfo(map,info);
        }
        return levelInfo;
    }

    public LevelInfoInterface getLevel(int level) {
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
            obj.setImage(getPaletteImage(),obj.getBlockType()-1);
            map.setBlock(obj,(int)obj.getPosX(),(int)obj.getPosY(),(int)obj.getPosZ());
            return new LevelInfo(map,info);
        }else {
            return null;
        }
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
    private Image getPaletteImage() {
        if(paletteImage==null) {
            LevelManager levelManager = new AnimationEditorLevelManager();
            levelManager.init(getEnvironment());
            paletteImage = levelManager.getImage();
        }
        return paletteImage;
    }
}