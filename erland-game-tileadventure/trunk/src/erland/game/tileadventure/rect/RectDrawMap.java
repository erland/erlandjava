package erland.game.tileadventure.rect;
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

import erland.game.tileadventure.*;

import java.awt.*;
import java.util.Vector;

public class RectDrawMap extends DrawMap {
    public void draw(Graphics g) {
        BitmapObject.drawCount = 0;
        MapObjectContainerInterface[] maps = getMaps();
        if(maps.length>0) {
            MapObjectContainerInterface main = (MapObjectContainerInterface) maps[0];
            for(int z=0;z<main.getSizeZ();z++) {
                for(int y=0;y<main.getSizeY();y++) {
                    for(int x=0;x<main.getSizeX();x++) {
                        drawTile(maps, x, y, z, g);
                    }
                }
            }
        }
    }

    private void drawTile(MapObjectContainerInterface[] maps, int x, int y, int z, Graphics g) {
        MapObjectInterface obj = maps[0].getObject(x,y,z);
        if(obj!=null) {
            obj.draw(g);
        }
        for(int i=1;i<maps.length;i++) {
            MapObjectContainerInterface map = (MapObjectContainerInterface) maps[i];
            obj = map.getObject(x,y,z);
            if(obj!=null) {
                obj.draw(g);
            }
        }
    }
}

