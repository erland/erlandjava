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
 * A factory object that is used to creates the blocks that is used
 * to compose a track
 */
public class RacerLevelFactoryGameIcon implements LevelFactoryInterface {
    /** The object that is used to clone the blocks */
    protected BlockCloneInterface blockCloneManager;

    /**
     * Creates the factory
     * @param blockCloneManager The object that is used to clone the blocks
     */
    public RacerLevelFactoryGameIcon(BlockCloneInterface blockCloneManager)
    {
        this.blockCloneManager = blockCloneManager;
    }

    public LevelInterface createLevel() {
        return new LevelGameIcon(blockCloneManager,1,1);
    }
}
