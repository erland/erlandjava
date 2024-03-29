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

import erland.game.GameEnvironmentInterface;

import java.util.Vector;

public class LevelManager {
    private GameEnvironmentInterface environment;
    private IrregularBlockContainerInterface cont;

    public void init(GameEnvironmentInterface environment) {
        this.environment = environment;
    }
    public void setContainer(IrregularBlockContainerInterface cont) {
        this.cont = cont;
    }
    private IsoBitmapObject newBlock(int type,IsoObjectMapInterface map, int x, int y,int z,int subImageTop, int subImageBottom) {
        if(type==0) {
            IsoBitmapObject block = new IsoBitmapObject();
            block.init(environment);
            block.setContainer(cont);
            block.setObjectMap(map);
            block.setPos(x,y,z);
            block.setImage("ground.gif",subImageTop,subImageBottom);
            return block;
        }else if(type==1) {
            IsoBitmapObjectMovable block = new IsoBitmapObjectMovable();
            block.init(environment);
            block.setContainer(cont);
            block.setObjectMap(map);
            block.setPos(x,y,z);
            block.setImage("ground.gif",subImageTop,subImageBottom);
            return block;
        }else {
            return null;
        }
    }
    public LevelInfoInterface getLevel(int level) {
        IsoObjectMap map = new IsoObjectMap(cont.getSizeX(),cont.getSizeY(),10);
        int i=0;
        for(int x=0;x<map.getSizeX();x++,i++) {
            for(int y=0;y<map.getSizeY();y++,i++) {
                newBlock(0,map,x,y,0,i%2,3);
            }
        }
        int y=5;
        for(int z=1;z<map.getSizeZ();z++) {
            for(int x=2;x<6;x++) {
                newBlock(0,map,x,y,z,0,3);
            }
        }
        Vector objectListVector = new Vector();
        y=8;
        for(int z=1;z<map.getSizeZ();z++) {
            for(int x=2;x<6;x++) {
                objectListVector.addElement(newBlock(1,null,x,y,z,1,3));
            }
        }
        IsoObjectInterface[] objectList = new IsoObjectInterface[objectListVector.size()];
        for(i=0;i<objectList.length;i++) {
            objectList[i]=(IsoObjectInterface) objectListVector.elementAt(i);
        }
        LevelInfoInterface levelInfo = new LevelInfo(map,(IsoObjectInterface[])objectList);
        return levelInfo;
    }
}
