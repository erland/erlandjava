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

import erland.game.MapEditorBlockInterface;

/**
 * Implements a block cloning that checks the blocktype of the
 * block and finds it among a group of original blocks and makes
 * a clone of the original block of the same type
 * @author Erland Isaksson
 */
public class BlockTypeClone implements BlockCloneInterface {
    /** Matrix with the original blocks to clone */
    private MapEditorBlockInterface[][] cloneBlocks;

    /**
     * Creates a new object with a matrix of original blocks
     * which should be possible to clone
     * @param cloneBlocks Matrix with blocks that should be possible
     * to clone
     */
    public BlockTypeClone(MapEditorBlockInterface[][] cloneBlocks)
    {
        this.cloneBlocks = cloneBlocks;
    }
    /**
     * Find blocks of the specified type among the original blocks
     * which should be possible to clone
     * @param blockNo The block type to find
     * @return The block from the original blocks which should be cloned
     */
    protected MapEditorBlockInterface findBlock(int blockNo)
    {
        for(int x=0;x<cloneBlocks.length;x++) {
            for(int y=0;y<cloneBlocks[x].length;y++) {
                if(cloneBlocks[x][y]!=null) {
                    if(cloneBlocks[x][y] instanceof BlockGroup) {
                        if(((BlockGroup)cloneBlocks[x][y]).getBlockType()==blockNo) {
                            return cloneBlocks[x][y];
                        }
                    }
                }
            }
        }
        return null;
    }


    public MapEditorBlockInterface cloneBlock(MapEditorBlockInterface block) {
        MapEditorBlockInterface b = null;
        if(block instanceof BlockGroup) {
            MapEditorBlockInterface b1 = findBlock(((BlockGroup)block).getBlockType());
            if(b1 != null) {
                try {
                    b = (MapEditorBlockInterface)((Block)b1).clone();
                } catch (CloneNotSupportedException e) {
                }
            }
        }
        return b;
    }
}
