package erland.game.tileadventure;

import erland.util.*;
import erland.game.tileadventure.rect.RectBlockContainerData;
import erland.game.BlockContainerInterface;

import java.awt.*;
import java.awt.image.BufferedImage;
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

public class AnimationEditorLevelManager extends LevelManager {
    private Image paletteImage;
    protected int getDefaultSizeX() {
        return 10;
    }

    protected int getDefaultSizeY() {
        return 5;
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
        return null;
    }

    public Image getImage() {
        TileGameEnvironmentInterface env = ((TileGameEnvironmentInterface)getEnvironment().getCustomEnvironment());
        SubImageHandler subImageHandler = env.createSubImageHandler(null);
        IrregularBlockContainerInterface cont = env.createRectBlockContainer(0,0,1,1,1);
        String prefix = ((TileGameEnvironmentInterface)getEnvironment().getCustomEnvironment()).getTileType();
        boolean bEnd = false;
        int i=0;
        while(!bEnd) {
            LevelInfoInterface level = getLevel(++i);
            if(level!=null) {
                AnimationExtendedLevelInfo info = (AnimationExtendedLevelInfo) level.getExtendedInfo();
                int[] images = info.getImages();
                MapEditorObject o = new MapEditorObject();
                o.init(getEnvironment());
                o.setContainer(cont);
                o.setPos(0,0,0);
                o.setImage(prefix+"ground.gif",images[0]);
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
            MapObjectContainerInterface map = new MapBlockContainer(getDefaultSizeX(),getDefaultSizeY(),getDefaultSizeZ());
            MapObjectExtendedLevelInfo info = new MapObjectExtendedLevelInfo();
            levelInfo = new LevelInfo(map,info);
        }
        return levelInfo;
    }

    public LevelInfoInterface getLevel(int level) {
        StorageInterface animationStorage = LevelManagerHelper.load(getEnvironment().getStorage(),getGroupName(),getObjectName(),""+level);
        String prefix = ((TileGameEnvironmentInterface)getEnvironment().getCustomEnvironment()).getTileType();
        if(animationStorage!=null) {
            MapObjectContainerInterface map = new MapBlockContainer(getDefaultSizeX(),getDefaultSizeY(),getDefaultSizeZ());
            AnimationExtendedLevelInfo info = new AnimationExtendedLevelInfo();
            ParameterValueStorageExInterface animationParameters = new ParameterStorageStringEx(animationStorage,null,getObjectName());
            info.read(animationParameters);
            int[] images = info.getImages();
            for (int i = 0; i < images.length; i++) {
                int image = images[i];
                MapEditorObject obj = new MapEditorObject();
                obj.init(getEnvironment());
                obj.setContainer(getContainer());
                obj.setBlockType(image+1);
                obj.setImage(prefix+"ground.gif", obj.getBlockType()-1);
                obj.setPos((i)%map.getSizeX(), (i)/map.getSizeX(), 0);
                map.setObject(obj, (int)obj.getPosX(), (int)obj.getPosY(), (int)obj.getPosZ());
            }
            return new LevelInfo(map,info);
        }else {
            return null;
        }
    }

    public void setLevel(int level, MapObjectContainerInterface blocks, ParameterSerializable extendedInfo) {
        StringStorage animationStorage = new StringStorage();
        ParameterValueStorageExInterface animationParameters = new ParameterStorageStringEx(animationStorage, null, null);
        Vector images = new Vector();
        for (int x = 0; x < blocks.getSizeX(); x++) {
            for (int y = 0; y < blocks.getSizeY(); y++) {
                for (int z = 0; z < blocks.getSizeZ(); z++) {
                    MapEditorObject o = (MapEditorObject) blocks.getObject(x, y, z);
                    if (o != null) {
                        images.add(new Integer(o.getBlockType()-1));
                    }
                }
            }
        }
        if (extendedInfo != null) {
            int[] intImages = new int[images.size()];
            for (int i = 0; i < intImages.length; i++) {
                intImages[i] = ((Integer)images.elementAt(i)).intValue();
            }
            ((AnimationExtendedLevelInfo)extendedInfo).setImages(intImages);
            extendedInfo.write(animationParameters);
        }
        LevelManagerHelper.store(getEnvironment().getStorage(),getGroupName(), getObjectName(), "" + level, animationStorage.load());
    }
}