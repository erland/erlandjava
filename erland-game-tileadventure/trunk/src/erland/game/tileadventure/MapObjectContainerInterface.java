package erland.game.tileadventure;

import java.util.List;

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

public interface MapObjectContainerInterface {
    public int getSizeX();
    public int getSizeY();
    public int getSizeZ();
    public MapObjectInterface getBlock(int x, int y, int z);
    public void setBlock(MapObjectInterface object, int x, int y, int z);
    public void removeBlock(MapObjectInterface object, int x, int y, int z);
    public void removeBlock(MapObjectInterface object);
    public List getObjects(int x, int y, int z);
    public void addObject(MapObjectInterface object, int x, int y, int z);
    public void removeObject(MapObjectInterface object, int x, int y, int z);
    public void removeObject(MapObjectInterface object);
}