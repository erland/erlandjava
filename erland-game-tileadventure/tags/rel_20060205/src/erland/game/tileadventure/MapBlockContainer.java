package erland.game.tileadventure;

import java.util.List;
import java.util.ArrayList;

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

public class MapBlockContainer implements MapObjectContainerInterface {
    private MapObjectInterface blocks[][][];
    private List objects[][][];

    public MapBlockContainer(int sizeX, int sizeY, int sizeZ) {
        blocks = new MapObjectInterface[sizeX][sizeY][sizeZ];
        objects = new List[sizeX][sizeY][sizeZ];
        for (int x = 0; x < blocks.length; x++) {
            blocks[x] = new MapObjectInterface[sizeY][sizeZ];
            objects[x] = new List[sizeY][sizeZ];
            for (int y = 0; y < blocks[x].length; y++) {
                blocks[x][y] = new MapObjectInterface[sizeZ];
                objects[x][y] = new List[sizeZ];
                for (int z = 0; z < blocks[x][y].length; z++) {
                    blocks[x][y][z] = null;
                    objects[x][y][z] = new ArrayList(10);
                }
            }
        }
    }

    private boolean isInsideMap(int x, int y, int z) {
        if(x<blocks.length && y<blocks[0].length && z<blocks[0][0].length && x>=0 && y>=0 && z>=0) {
            return true;
        }else {
            return false;
        }
    }

    public int getSizeX() {
        return blocks.length;
    }

    public int getSizeY() {
        return blocks[0].length;
    }

    public int getSizeZ() {
        return blocks[0][0].length;
    }

    public MapObjectInterface getBlock(int x, int y, int z) {
        if(isInsideMap(x,y,z)) {
            return blocks[x][y][z];
        }else {
            return null;
        }
    }

    public void setBlock(MapObjectInterface block, int x, int y, int z) {
        if(isInsideMap(x,y,z)) {
            blocks[x][y][z]=block;
        }
    }

    public void removeBlock(MapObjectInterface block, int x, int y, int z) {
        if(isInsideMap(x,y,z)) {
            if(blocks[x][y][z]==block) {
                blocks[x][y][z]=null;
            }
        }
    }

    public void removeBlock(MapObjectInterface object) {
        for (int x = 0; x < blocks.length; x++) {
            for (int y = 0; y < blocks[x].length; y++) {
                for (int z = 0; z < blocks[x][y].length; z++) {
                    if(object==blocks[x][y][z]) {
                        blocks[x][y][z] = null;
                    }
                }
            }
        }
    }
    public void addObject(MapObjectInterface object,int x, int y, int z) {
        if(isInsideMap(x,y,z)) {
            for(int i=0;i<objects[x][y][z].size();i++) {
                if(objects[x][y][z].get(i)==object) {
                    return;
                }
            }
            //System.out.println("Add object ("+x+","+y+","+z+")="+object);
            objects[x][y][z].add(object);
        }
    }
    public void removeObject(MapObjectInterface object, int x, int y, int z) {
        if(isInsideMap(x,y,z)) {
            objects[x][y][z].remove(object);
        }
    }
    public void removeObject(MapObjectInterface object) {
        for (int x = 0; x < objects.length; x++) {
            for (int y = 0; y < objects[x].length; y++) {
                for (int z = 0; z < objects[x][y].length; z++) {
                    while(objects[x][y][z].remove(object));
                }
            }
        }
    }
    public List getObjects(int x, int y, int z) {
        if(isInsideMap(x,y,z)) {
            return objects[x][y][z];
        }else {
            return null;
        }
    }
}