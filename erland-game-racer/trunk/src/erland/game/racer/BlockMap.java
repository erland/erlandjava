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

import erland.game.BlockContainerInterface;

import java.awt.*;

/**
 * Implements a group block that consists of several smaller blocks
 * @author Erland Isaksson
 */
class BlockMap implements BlockMapDrawInterface {
    /** Matrix of blocks representing the background */
    Block groundData[][];
    /** Matrix of blocks representing the level 1 data */
    Block level1Data[][];
    /** Block container which the block exists in */
    BlockContainerInterface cont;
    /** Information about where the start position is */
    ExtendedLevelInfo extendedLevelInfo;

    /**
     * Set the block container which the block exists in
     * @param cont The block container
     */
    public void setContainer(BlockContainerInterface cont)
    {
        this.cont = cont;
    }

    /**
     * Set the sub blocks that the block consists of
     * @param groundBlocks Matrix of blocks representing the background level
     * @param level1Blocks Matrix of blocks representing the level 1 data
     */
    public void setMapData(Block groundBlocks[][],Block level1Blocks[][], ExtendedLevelInfo extendedLevelInfo) {
        groundData = groundBlocks;
        level1Data = level1Blocks;
        if(extendedLevelInfo!=null) {
            this.extendedLevelInfo = extendedLevelInfo;
        }else {
            this.extendedLevelInfo = new ExtendedLevelInfo();
            this.extendedLevelInfo.setStartDirection(0);
            this.extendedLevelInfo.setStartPosX(0);
            this.extendedLevelInfo.setStartPosY(0);
        }
    }

    public void draw(Graphics g,int level)
    {
        g.setClip(cont.getOffsetX(),cont.getOffsetY(),cont.getDrawingSizeX(),cont.getDrawingSizeY());
        for(int x=0;x<groundData.length;x++) {
            for(int y=0;y<groundData.length;y++) {
                if(groundData[x][y]!=null) {
                    if(cont.getVisible(groundData[x][y].getPosX(),groundData[x][y].getPosY())) {
                        groundData[x][y].draw(g,level);
                    }
                }
            }
        }
        if(level1Data != null) {
            for(int x=0;x<level1Data.length;x++) {
                for (int y = 0; y < level1Data[0].length; y++) {
                    if(level1Data[x][y]!=null) {
                        if(cont.getVisible(level1Data[x][y].getPosX(),level1Data[x][y].getPosY())) {
                            level1Data[x][y].draw(g,level);
                        }
                    }
                }
            }
        }
    }

    /**
     * Get all blocks on the specified level
     * @param level The level to get blocks for
     * @return A matrix of all blocks on the specified level
     */
    public Block[][] getBlocks(int level) {
        if(level==0) {
            return groundData;
        }else {
            return level1Data;
        }
    }
    /**
     * Get the direction of the start grid of the track
     * @return The direction of the start grid of the track
     */
    public int getStartDirection() {
        return extendedLevelInfo.getStartDirection();
    }
    /**
     * Get the X block coordinate of the start grid of the track
     * @return The X block coordinate of the start grid
     */
    public int getStartPosX() {
        return extendedLevelInfo.getStartPosX();
    }
    /**
     * Get the Y block coordinate of the start grid of the track
     * @return The Y block coordinate of the start grid
     */
    public int getStartPosY() {
        return extendedLevelInfo.getStartPosY();
    }
}
