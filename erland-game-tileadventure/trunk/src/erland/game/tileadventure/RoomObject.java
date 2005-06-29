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
import erland.util.ParameterValueStorageExInterface;
import erland.util.ParameterSerializable;

import java.awt.*;


public class RoomObject implements Cloneable, MapObjectInterface {
    /** The block container which the block exist in */
    private IrregularBlockContainerInterface cont;
    /** The x position of the block */
    private float posX;
    /** The y position of the block */
    private float posY;
    /** The z position of the block */
    private float posZ;
    /** The game environment of the block */
    private GameEnvironmentInterface environment;
    /** The blocks in the room */
    private MapObjectContainerInterface maps;
    /** The extended level info for the room */
    private ParameterSerializable extendedLevelInfo;

    public void init(GameEnvironmentInterface environment) {
        this.environment = environment;
    }

    public void setContainer(IrregularBlockContainerInterface cont) {
        this.cont = cont;
    }

    public IrregularBlockContainerInterface getContainer() {
        return cont;
    }

    public float getPosX() {
        return posX;
    }

    public float getPosY() {
        return posY;
    }

    public float getPosZ() {
        return posZ;
    }

    public void setPos(float x, float y, float z) {
        this.posX = x;
        this.posY = y;
        this.posZ = z;
    }

    public void draw(Graphics g) {
        // Nothing to draw
    }

    public Object clone() {
        RoomObject b=null;
        try {
            b = (RoomObject)super.clone();
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
        return b;
    }

    public void setBlocks(MapObjectContainerInterface maps) {
        this.maps = maps;
    }
    public MapObjectContainerInterface getBlocks() {
        return maps;
    }

    public void setExtendedLevelInfo(ParameterSerializable extendedLevelInfo) {
        this.extendedLevelInfo = extendedLevelInfo;
    }
    public ParameterSerializable getExtendedLevelInfo() {
        return extendedLevelInfo;
    }
}

