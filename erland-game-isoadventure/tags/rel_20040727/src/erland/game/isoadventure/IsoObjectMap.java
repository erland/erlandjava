package erland.game.isoadventure;
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

public class IsoObjectMap implements IsoObjectMapInterface {
    private IsoObjectInterface[][][] map;
    public IsoObjectMap(int sizeX, int sizeY, int sizeZ) {
        map = new IsoObjectInterface[sizeZ][sizeX][sizeY];
    }
    public void addObject(IsoObjectInterface obj, int x, int y, int z) {
        if(isInsideMap(x,y,z)) {
            map[z][x][y] = obj;
        }
    }

    public void removeObject(IsoObjectInterface obj, int x, int y, int z) {
        if(isInsideMap(x,y,z)) {
            if(map[z][x][y]==obj) {
                map[z][x][y] = null;
            }
        }
    }

    public void removeObjectFromMap(IsoObjectInterface obj) {
        for(int z=0;z<map.length;z++) {
            for(int x=0;x<map[0].length;x++) {
                for(int y=0;y<map[0][0].length;y++) {
                    if(map[z][x][y]==obj) {
                        map[z][x][y]=null;
                    }
                }
            }
        }
    }

    private boolean isInsideMap(int x, int y, int z) {
        if(x<map[0].length && y<map[0][0].length && z<map.length && x>=0 && y>=0 && z>=0) {
            return true;
        }else {
            return false;
        }
    }
    public IsoObjectInterface getObject(int x, int y, int z) {
        if(isInsideMap(x,y,z)) {
            return map[z][x][y];
        }else {
            return null;
        }
    }

    public int getSizeX() {
        return map[0].length;
    }

    public int getSizeY() {
        return map[0][0].length;
    }

    public int getSizeZ() {
        return map.length;
    }
}
