package erland.game.racer;
/*
 * Copyright (C) 2003 Erland Isaksson (erland_i@hotmail.com)
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

import erland.util.ParameterValueStorageExInterface;

/**
 * Class that implements a block that can be drawn as
 * a {@link BlockBitmap} but also has information about which
 * type of block it is
 * @author Erland Isaksson
 */
public class BlockGroup extends BlockBitmap {
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
        out.setParameter("x",Integer.toString(getPosX()));
        out.setParameter("y",Integer.toString(getPosY()));
        out.setParameter("blocktype",Integer.toString(blockType));
    }

    public void read(ParameterValueStorageExInterface in) {
        int x = Integer.valueOf(in.getParameter("x")).intValue();
        int y = Integer.valueOf(in.getParameter("y")).intValue();
        blockType = Integer.valueOf(in.getParameter("blocktype")).intValue();
        setPos(x,y);
    }
}
