package erland.game.tileadventure;

import erland.util.ParameterValueStorageExInterface;
import erland.util.StringUtil;

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

public class MapEditorObject extends BitmapObject {
    private int blockType;

    /**
     * Get block type of the block
     * @return The block type of the block
     */
    public int getBlockType()
    {
        return blockType;
    }
    /**
     * Set the block type of the block
     * @param blockType The block type
     */
    public void setBlockType(int blockType)
    {
        this.blockType = blockType;
    }

    public void write(ParameterValueStorageExInterface out) {
        out.setParameter("type",String.valueOf(blockType));
        out.setParameter("x",Integer.toString((int)getPosX()));
        out.setParameter("y",Integer.toString((int)getPosY()));
        out.setParameter("z",Integer.toString((int)getPosZ()));
    }

    public void read(ParameterValueStorageExInterface in) {
        int blockType = StringUtil.asInteger(in.getParameter("type"),new Integer(0)).intValue();
        int x = StringUtil.asInteger(in.getParameter("x"),new Integer(0)).intValue();
        int y = StringUtil.asInteger(in.getParameter("y"),new Integer(0)).intValue();
        int z = StringUtil.asInteger(in.getParameter("z"),new Integer(0)).intValue();
        setBlockType(blockType);
        setPos(x,y,z);
    }
}