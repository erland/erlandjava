package erland.game.tileadventure.isodiamond;

import erland.game.tileadventure.*;
import erland.game.tileadventure.rect.RectBlockContainerData;
import erland.game.GameEnvironmentInterface;
import erland.util.SubImageHandler;

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

public class IsoDiamondEnvironment implements TileGameEnvironmentInterface {
    private GameEnvironmentInterface environment;
    private static final int SQUARE_SIZE_X=32;
    private static final int SQUARE_SIZE_Y=16;
    private static final int SQUARE_SIZE_Z=8;

    public IsoDiamondEnvironment(GameEnvironmentInterface environment) {
        this.environment = environment;
    }
    public BlockContainerData createBlockContainer(int offsetX, int offsetY, int sizeX, int sizeY, int sizeZ, int visibleSizeX, int visibleSizeY) {
        return new IsoDiamondBlockContainerData(offsetX, offsetY, 0,-getSubImageHeight()+SQUARE_SIZE_Y,sizeX,sizeY, sizeZ, SQUARE_SIZE_X, SQUARE_SIZE_Y,SQUARE_SIZE_Z,visibleSizeX,visibleSizeY);
    }

    public BlockContainerData createBlockContainer(int offsetX, int offsetY, int sizeX, int sizeY, int sizeZ) {
        BlockContainerData data = createBlockContainer(offsetX, offsetY, sizeX,sizeY, sizeZ, sizeX*(SQUARE_SIZE_X>>1)+sizeY*(SQUARE_SIZE_X>>1),sizeY*(SQUARE_SIZE_Y>>1)+sizeX*(SQUARE_SIZE_Y>>1)+sizeZ*SQUARE_SIZE_Z);
        data.setScrollingOffsetX(0);
        data.setScrollingOffsetY(-sizeZ*SQUARE_SIZE_Z);
        return data;
    }
    public BlockContainerData createRectBlockContainer(int offsetX, int offsetY, int sizeX, int sizeY, int sizeZ) {
        return new RectBlockContainerData(offsetX,offsetY,0,-getSubImageHeight()+SQUARE_SIZE_Y,sizeX,sizeY,sizeZ,getSubImageWidth(),getSubImageHeight(),SQUARE_SIZE_Z,sizeX*getSubImageWidth(),sizeY*getSubImageHeight());
    }
    public SubImageHandler createSubImageHandler(Image image) {
        if(image==null) {
            image = environment.getImageCreator().createCompatibleImage(getSubImageWidth()*8,getSubImageHeight()*8,Transparency.BITMASK);
        }
        return new SubImageHandler(image,getSubImageWidth(),getSubImageHeight(),8,8,0,0/*-getSubImageHeight()+SQUARE_SIZE_Y*/);
    }

    public int getSubImageWidth() {
        return SQUARE_SIZE_X;
    }

    public int getSubImageHeight() {
        return SQUARE_SIZE_Z*3+SQUARE_SIZE_Y;
    }

    public DrawMap createBlockMap() {
        return new IsoDiamondDrawMap();
    }

    public String getTileType() {
        return "isodiamond";
    }
}