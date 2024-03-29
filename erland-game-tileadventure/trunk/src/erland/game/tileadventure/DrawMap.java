package erland.game.tileadventure;
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

import java.awt.*;
import java.util.Vector;
import java.util.Arrays;

public abstract class DrawMap implements MapDrawInterface {
    private IrregularBlockContainerInterface cont;
    private MapObjectContainerInterface[] maps = new MapObjectContainerInterface[0];

    public void setContainer(IrregularBlockContainerInterface cont) {
        this.cont = cont;
    }

    protected IrregularBlockContainerInterface getContainer() {
        return cont;
    }

    public void removeAllObjectMaps() {
        maps = new MapObjectContainerInterface[0];
    }
    public void addObjectMap(MapObjectContainerInterface map) {
        Vector tmp = new Vector(Arrays.asList(maps));
        tmp.addElement(map);
        maps = (MapObjectContainerInterface[]) tmp.toArray(new MapObjectContainerInterface[0]);
    }

    protected MapObjectContainerInterface[] getMaps() {
        return maps;
    }


    public abstract void draw(Graphics g);
}

