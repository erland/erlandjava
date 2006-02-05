package erland.game.tileadventure;

import erland.util.*;

import java.awt.*;

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

public class WorldEditorLevelManager extends LevelManager {
    private Image paletteImage;
    protected String getGroupName() {
        return "worlds";
    }
    protected String getObjectName() {
        return "world";
    }
    protected String getPartName() {
        return "room";
    }
    protected int getDefaultSizeX() {
        return 10;
    }
    protected int getDefaultSizeY() {
        return 10;
    }
    protected int getDefaultSizeZ() {
        return 1;
    }
    public Image getImage() {
        // No image can be created with this level manager
        return null;
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
            WorldExtendedLevelInfo info = new WorldExtendedLevelInfo();
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
                    map.setBlock(obj, (int)obj.getPosX(), (int)obj.getPosY(), (int)obj.getPosZ());
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
                    MapEditorObject o = (MapEditorObject) blocks.getBlock(x, y, z);
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
            LevelManager levelManager = new RoomEditorLevelManager();
            levelManager.init(getEnvironment());
            paletteImage = levelManager.getImage();
        }
        return paletteImage;
    }
}