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

import java.awt.*;
import java.util.Vector;

public class IsoMap implements MapDrawInterface {
    private IrregularBlockContainerInterface cont;
    private Vector maps = new Vector();

    public void setContainer(IrregularBlockContainerInterface cont) {
        this.cont = cont;
    }

    public void removeAllObjectMaps() {
        maps.removeAllElements();
    }
    public void addObjectMap(IsoObjectMapInterface map) {
        maps.addElement(map);
    }

    public void draw(Graphics g) {
        if(maps.size()>0) {
            IsoObjectMapInterface main = (IsoObjectMapInterface) maps.elementAt(0);
            int noOfRows = main.getSizeX();
            int prevX = 0;
            int prevY = 0;
            for(int row=0;row<noOfRows;row++) {
                for(int x=0,y=row;y>=0;y--,x++) {
                    boolean bRedraw = false;
                    for(int z=0;z<main.getSizeZ();z++) {
                        drawTile(main, x, y, z, g);
                        IsoObjectInterface obj = main.getObject(x,y,z);
                        if(!bRedraw && obj!=null && (obj.getPosX()>obj.getMovingPosX())) {
                            bRedraw = true;
                        }else if(!bRedraw) {
                            for(int i=1;i<maps.size();i++) {
                                IsoObjectMapInterface map = (IsoObjectMapInterface) maps.elementAt(i);
                                obj = map.getObject(x,y,z);
                                if(!bRedraw && obj!=null && (obj.getPosX()<obj.getMovingPosX())) {
                                    bRedraw = true;
                                    break;
                                }
                            }
                        }
                    }
                    if(bRedraw) {
                        for(int z=0;z<main.getSizeZ();z++) {
                            drawTile(main, prevX, prevY, z, g);
                        }
                    }
                    prevX = x;
                    prevY = y;
                }
            }
            int noOfColumns = main.getSizeY()-1;
            for(int column=1;column<=noOfColumns;column++) {
                for(int x=column,y=noOfColumns;y>=column;y--,x++) {
                    boolean bRedraw = false;
                    for(int z=0;z<main.getSizeZ();z++) {
                        drawTile(main, x, y, z, g);
                        IsoObjectInterface obj = main.getObject(x,y,z);
                        if(!bRedraw && obj!=null && (obj.getPosX()>obj.getMovingPosX())) {
                            bRedraw = true;
                        }else if(!bRedraw) {
                            for(int i=1;i<maps.size();i++) {
                                IsoObjectMapInterface map = (IsoObjectMapInterface) maps.elementAt(i);
                                obj = map.getObject(x,y,z);
                                if(!bRedraw && obj!=null && (obj.getPosX()<obj.getMovingPosX())) {
                                    bRedraw = true;
                                    break;
                                }
                            }
                        }
                    }
                    if(bRedraw) {
                        for(int z=0;z<main.getSizeZ();z++) {
                            drawTile(main, prevX, prevY, z, g);
                        }
                    }
                    prevX = x;
                    prevY = y;
                }
            }
        }
    }

    private void drawTile(IsoObjectMapInterface main, int x, int y, int z, Graphics g) {
        IsoObjectInterface obj = main.getObject(x,y,z);
        if(obj!=null) {
            obj.draw(g);
        }
        for(int i=1;i<maps.size();i++) {
            IsoObjectMapInterface map = (IsoObjectMapInterface) maps.elementAt(i);
            obj = map.getObject(x,y,z);
            if(obj!=null) {
                obj.draw(g);
            }
        }
    }
}

