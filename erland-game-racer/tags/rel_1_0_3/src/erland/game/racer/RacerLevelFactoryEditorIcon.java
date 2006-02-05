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
 * This is a factor that creates the icons that can be composed together to
 * make a block that can be used to build a track
 */
public class RacerLevelFactoryEditorIcon implements LevelFactoryInterface {
    /** The horizontal number of blocks in the container that the icon will be placed in */
    protected int sizeX;
    /** The vertical number of blocks in the container that the icon will be placed in */
    protected int sizeY;
    /** The object that will be used when making clones of the block */
    protected BlockCloneInterface blockCloneManager;

    /**
     * Creates a factory object to create icons
     * @param blockCloneManager The object that should be used when cloning blocks
     * @param sizeX The horizontal number of blocks in the container the blocks will be created in
     * @param sizeY The vertical number of blocks in the container the blocks will be created in
     */
    public RacerLevelFactoryEditorIcon(BlockCloneInterface blockCloneManager, int sizeX, int sizeY)
    {
        this.blockCloneManager = blockCloneManager;
        this.sizeX = sizeX;
        this.sizeY = sizeY;
    }

    public LevelInterface createLevel() {
        return new LevelEditorIcon(blockCloneManager,sizeX, sizeY);
    }
}
