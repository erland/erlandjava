package erland.game.tileadventure.rect;

import erland.game.tileadventure.*;
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

public class RectEnvironment implements TileGameEnvironmentInterface {
    private GameEnvironmentInterface environment;
    private static final int SQUARE_SIZE_X=32;
    private static final int SQUARE_SIZE_Y=32;
    private static final int SQUARE_SIZE_Z=8;
    public RectEnvironment(GameEnvironmentInterface environment) {
        this.environment = environment;
    }
    public BlockContainerData createBlockContainer(int offsetX, int offsetY, int sizeX, int sizeY, int sizeZ, int visibleSizeX, int visibleSizeY) {
        return new RectBlockContainerData(offsetX, offsetY, 0,0,sizeX,sizeY, sizeZ, SQUARE_SIZE_X, SQUARE_SIZE_Y,SQUARE_SIZE_Z,visibleSizeX,visibleSizeY);
    }

    public BlockContainerData createBlockContainer(int offsetX, int offsetY, int sizeX, int sizeY, int sizeZ) {
        return createBlockContainer(offsetX,offsetY,sizeX,sizeY,sizeZ,SQUARE_SIZE_X*sizeX,SQUARE_SIZE_Y*sizeY);
    }

    public BlockContainerData createRectBlockContainer(int offsetX, int offsetY, int sizeX, int sizeY, int sizeZ) {
        return createBlockContainer(offsetX,offsetY,sizeX,sizeY,sizeZ);
    }
    public SubImageHandler createSubImageHandler(Image image) {
        if(image==null) {
            image = environment.getImageCreator().createCompatibleImage(getSubImageWidth()*8,getSubImageHeight()*8,Transparency.BITMASK);
        }
        return new SubImageHandler(image,getSubImageWidth(),getSubImageHeight(),8,8);
    }

    public int getSubImageWidth() {
        return SQUARE_SIZE_X;
    }

    public int getSubImageHeight() {
        return SQUARE_SIZE_Y;
    }

    public DrawMap createBlockMap() {
        return new RectDrawMap();
    }

    public String getTileType() {
        return "rect";
    }
}