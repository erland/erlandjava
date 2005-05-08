package erland.game.tileadventure;

import erland.util.*;
import erland.game.tileadventure.rect.RectBlockContainerData;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

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

public class RoomEditorLevelManager extends LevelManager {
    private Image paletteImage;
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
        TileGameEnvironmentInterface env = ((TileGameEnvironmentInterface)getEnvironment().getCustomEnvironment());
        SubImageHandler subImageHandler = env.createSubImageHandler(null);
        boolean bEnd = false;
        int i=0;
        DrawMap drawMap = ((TileGameEnvironmentInterface)getEnvironment().getCustomEnvironment()).createBlockMap();
        while(!bEnd) {
            LevelInfoInterface level = getLevel(++i);
            if(level!=null) {
                RoomExtendedLevelInfo extendedInfo = (RoomExtendedLevelInfo) level.getExtendedInfo();
                IrregularBlockContainerInterface cont = ((TileGameEnvironmentInterface)getEnvironment().getCustomEnvironment()).createBlockContainer(0,0,extendedInfo.getSizeX(),extendedInfo.getSizeY(),extendedInfo.getSizeZ());
                Image image = getEnvironment().getImageCreator().createCompatibleImage(cont.getDrawingSizeX(),cont.getDrawingSizeY(),Transparency.BITMASK);
                MapObjectContainerInterface map = level.getObjects();
                for (int x = 0; x < map.getSizeX(); x++) {
                    for (int y = 0; y < map.getSizeY(); y++) {
                        for (int z = 0; z < map.getSizeZ(); z++) {
                            MapObjectInterface o = map.getObject(x,y,z);
                            if(o!=null) {
                                o.setContainer(cont);
                            }
                        }
                    }
                }
                drawMap.setContainer(cont);
                drawMap.removeAllObjectMaps();
                drawMap.addObjectMap(map);
                drawMap.draw(image.getGraphics());
                Graphics g = subImageHandler.getGraphics(i-1);
                int height = (env.getSubImageWidth()*image.getHeight(null))/image.getWidth(null);
                int offsetY = env.getSubImageHeight()-height;
                g.drawImage(image,0,offsetY,env.getSubImageWidth(),offsetY+height,0,0,image.getWidth(null),image.getHeight(null),null);
                g.dispose();
            }else {
                bEnd = true;
            }
        }
        return subImageHandler.getImage();
    }

    public LevelInfoInterface createLevel(int level) {
        LevelInfoInterface levelInfo = getLevel(level);
        if(levelInfo==null) {
            RoomExtendedLevelInfo info = new RoomExtendedLevelInfo();
            info.setSizeX(getDefaultSizeX());
            info.setSizeY(getDefaultSizeY());
            info.setSizeZ(getDefaultSizeZ());
            MapObjectContainerInterface map = new MapBlockContainer(info.getSizeX(), info.getSizeY(), info.getSizeZ());
            levelInfo = new LevelInfo(map, info);
        }
        return levelInfo;
    }
    public LevelInfoInterface getLevel(int level) {
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
                    obj.setImage(getPaletteImage(), obj.getBlockType()-1);
                    map.setObject(obj, obj.getPosX(), obj.getPosY(), obj.getPosZ());
                } else {
                    bEnd = true;
                }
            }
            return new LevelInfo(map, info);
        }
        return null;
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
    private Image getPaletteImage() {
        if(paletteImage==null) {
            LevelManager levelManager = new MapObjectEditorLevelManager();
            levelManager.init(getEnvironment());
            paletteImage = levelManager.getImage();
        }
        return paletteImage;
    }
}