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

import erland.util.ParameterSerializable;
import erland.util.ParameterValueStorageExInterface;
import erland.game.GameEnvironmentInterface;
import erland.game.BlockContainerInterface;
import erland.game.MapEditorBlockInterface;

/**
 * Defines all methods that a level object must implement
 */
public interface LevelInterface extends ParameterSerializable, LevelInfoInterface {
    /**
     * Initialize the level
     * @param environment The game enviornment
     */
    void init(GameEnvironmentInterface environment);

    /**
     * Set the block container the level exists in
     * @param cont The block container
     */
    void setContainer(BlockContainerInterface cont);

    /**
     * Write the level to specified storage object
     * @param out Storage object
     */
    void write(ParameterValueStorageExInterface out);

    /**
     * Read the level from the specified storage object
     * @param in Storage object
     */
    void read(ParameterValueStorageExInterface in);

    /**
     * Set the blocks in the level
     * @param blocks The blocks
     */
    void setBlocks(MapEditorBlockInterface[][] blocks);

    /**
     * Set the extended information about the level besides the blocks
     * @param extendedInfo Extended info about the level
     */
    void setExtendedInfo(ParameterSerializable extendedInfo);

    /**
     * Get the name of the level
     * @return The name of the level
     */
    String getName();

    /**
     * Set the name of the level
     * @param name The name of the level
     */
    void setName(String name);

}
