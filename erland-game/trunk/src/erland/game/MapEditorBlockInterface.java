package erland.game;
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

/**
 * This interface must be implemented by all block objects which should
 * be used together with the {@link MapEditor} class
 * @author Erland Isaksson
 */
public interface MapEditorBlockInterface extends ParameterSerializable {
    /**
     * Initialize block
     * @param environment The game environment the block exists in
     */
    public void init(GameEnvironmentInterface environment);
    /**
     * Set the container that the block exists in
     * @param cont The container that the block exists in
     */
    public void setContainer(BlockContainerInterface cont);
    /**
     * Get block X position
     * @return X position of the block
     */
    public int getPosX();
    /**
     * Get block Y position
     * @return Y position of the block
     */
    public int getPosY();
    /**
     * Set block position
     * @param x The X position of the block
     * @param y The Y position of the block
     */
    public void setPos(int x, int y);
}
