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

/**
 * A factory object that is used to create levels/tracks
 */
public class RacerLevelFactorySimple implements LevelFactoryInterface {
    /** The number of horizontal blocks of the level */
    protected int sizeX;
    /** The number of vertical blocks of the level */
    protected int sizeY;
    /** The object that will be used when cloning blocks in the level */
    protected BlockCloneInterface blockCloneManager;

    /**
     * Create the factory object
     * @param blockCloneManager The object that will be used when cloning blocks in the level
     * @param sizeX The number of horizontal blocks of the level
     * @param sizeY The number of vertical blocks of the level
     */
    public RacerLevelFactorySimple(BlockCloneInterface blockCloneManager, int sizeX, int sizeY)
    {
        this.blockCloneManager = blockCloneManager;
        this.sizeX = sizeX;
        this.sizeY = sizeY;
    }

    public LevelInterface createLevel() {
        return new Level(blockCloneManager,sizeX, sizeY);
    }
}
