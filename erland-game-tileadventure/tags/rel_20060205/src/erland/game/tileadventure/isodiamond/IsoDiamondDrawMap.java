package erland.game.tileadventure.isodiamond;

import erland.game.tileadventure.*;

import java.awt.*;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

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

public class IsoDiamondDrawMap extends DrawMap {
    /** Logging instance */
    private static Log LOG = LogFactory.getLog(LivingObject.class);

    public void draw(Graphics g) {
        BitmapObject.drawCount = 0;
        MapObjectContainerInterface[] maps = getMaps();
        if(maps.length>0) {
            MapObjectContainerInterface main = (MapObjectContainerInterface) maps[0];
            int noOfRows = main.getSizeX();
            int prevX = 0;
            int prevY = 0;
            for(int row=0;row<noOfRows;row++) {
                for(int x=0,y=row;y>=0;y--,x++) {
                    boolean bRedraw = false;
                    for(int z=0;z<main.getSizeZ();z++) {
                        drawTile(maps, x, y, z, g);
                        GameObject block = (GameObject) main.getBlock(x,y,z);
                        GameObject drawnBlock = (GameObject) main.getBlock(x-1,y,z);
                        if(!bRedraw && drawnBlock!=null && block==drawnBlock) {
                            bRedraw = true;
                        }
                        List objects = main.getObjects(x,y,z);
                        List drawnObjects = main.getObjects(x-1,y,z);
                        for(int i=0;!bRedraw&&drawnObjects!=null && i<drawnObjects.size();i++) {
                            GameObject drawnObject = (GameObject) drawnObjects.get(i);
                            for(int j=0;!bRedraw&&objects!=null && j<objects.size();j++) {
                                GameObject object = (GameObject) objects.get(j);
                                //System.out.println("Checking "+drawnObject+" against "+object);
                                if(object==drawnObject) {
                                    bRedraw=true;
                                    break;
                                }
                            }
                        }
                        /*
                        GameObject obj = (GameObject)main.getBlock(x,y,z);
                        if(!bRedraw && obj!=null && (obj.getPosX()>obj.getMovingPosX())) {
                            bRedraw = true;
                        }else if(!bRedraw) {
                            for(int i=1;i<maps.length;i++) {
                                MapObjectContainerInterface map = (MapObjectContainerInterface) maps[i];
                                obj = (GameObject) map.getBlock(x,y,z);
                                if(!bRedraw && obj!=null && (obj.getPosX()<obj.getMovingPosX())) {
                                    bRedraw = true;
                                    break;
                                }
                            }
                        }
                        */
                    }
                    if(bRedraw) {
                        //System.out.println("*************Redraw ("+prevX+","+prevY+")******************");
                        for(int z=0;z<main.getSizeZ();z++) {
                            drawTile(maps, prevX, prevY, z, g,true);
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
                        drawTile(maps, x, y, z, g);
                        GameObject block = (GameObject) main.getBlock(x,y,z);
                        GameObject drawnBlock = (GameObject) main.getBlock(x-1,y,z);
                        if(!bRedraw && drawnBlock!=null && block==drawnBlock) {
                            bRedraw = true;
                        }
                        List objects = main.getObjects(x,y,z);
                        List drawnObjects = main.getObjects(x-1,y,z);
                        for(int i=0;!bRedraw&&drawnObjects!=null && i<drawnObjects.size();i++) {
                            GameObject drawnObject = (GameObject) drawnObjects.get(i);
                            for(int j=0;!bRedraw&&objects!=null && j<objects.size();j++) {
                                GameObject object = (GameObject) objects.get(j);
                                //System.out.println("Checking "+drawnObject+" against "+object);
                                if(object==drawnObject) {
                                    bRedraw=true;
                                    break;
                                }
                            }
                        }
                        /*
                        GameObject obj = (GameObject) main.getBlock(x,y,z);
                        if(!bRedraw && obj!=null && (obj.getPosX()>obj.getMovingPosX() && obj.getPosX()>prevX)) {
                            bRedraw = true;
                        }else if(!bRedraw) {
                            for(int i=1;i<maps.length;i++) {
                                MapObjectContainerInterface map = (MapObjectContainerInterface) maps[i];
                                obj = (GameObject) map.getBlock(x,y,z);
                                if(!bRedraw && obj!=null && (obj.getPosX()<obj.getMovingPosX() && obj.getMovingPosX()>prevX)) {
                                    bRedraw = true;
                                    break;
                                }
                            }
                        }
                        */
                    }
                    if(bRedraw && prevX<x) {
                        for(int z=0;z<main.getSizeZ();z++) {
                            drawTile(maps, prevX, prevY, z, g,true);
                        }
                    }
                    prevX = x;
                    prevY = y;
                }
            }
        }
        //System.out.println("Draw count:"+BitmapObject.drawCount);
    }

    private void drawTile(MapObjectContainerInterface[] maps, int x, int y, int z, Graphics g) {
        drawTile(maps,x,y,z,g,false);
    }
    private void drawTile(MapObjectContainerInterface[] maps, int x, int y, int z, Graphics g,boolean drawRect) {
        MapObjectInterface obj = maps[0].getBlock(x,y,z);
        if(obj!=null) {
            obj.draw(g);
            if(drawRect) {
                drawRect(g,obj);
            }
        }
        List objects = maps[0].getObjects(x,y,z);
        for(int j=0;j<objects.size();j++) {
            obj = (MapObjectInterface) objects.get(j);
            obj.draw(g);
            if(drawRect) {
                drawRect(g,obj);
            }
        }
        for(int i=1;i<maps.length;i++) {
            MapObjectContainerInterface map = (MapObjectContainerInterface) maps[i];
            obj = map.getBlock(x,y,z);
            if(obj!=null) {
                obj.draw(g);
                if(drawRect) {
                    drawRect(g,obj);
                }
            }
            objects = map.getObjects(x,y,z);
            for(int j=0;j<objects.size();j++) {
                obj = (MapObjectInterface) objects.get(j);
                obj.draw(g);
                if(drawRect) {
                    drawRect(g,obj);
                }
            }
        }
    }
    private void drawRect(Graphics g, MapObjectInterface obj) {
        if(LOG.isDebugEnabled() && obj.getContainer().getVisible(obj.getPosX(),obj.getPosY(),obj.getPosZ())) {
            Box3D box = obj.getBoundingBox();
            int x1 = getContainer().getPixelDrawingPositionX(box.getMinX(),box.getMinY(),box.getMinZ());
            int x2 = getContainer().getPixelDrawingPositionX(box.getMinX(),box.getMinY(),box.getMaxZ());
            int x3 = getContainer().getPixelDrawingPositionX(box.getMinX(),box.getMaxY(),box.getMinZ());
            int x4 = getContainer().getPixelDrawingPositionX(box.getMinX(),box.getMaxY(),box.getMaxZ());
            int x5 = getContainer().getPixelDrawingPositionX(box.getMaxX(),box.getMinY(),box.getMinZ());
            int x6 = getContainer().getPixelDrawingPositionX(box.getMaxX(),box.getMinY(),box.getMaxZ());
            int x7 = getContainer().getPixelDrawingPositionX(box.getMaxX(),box.getMaxY(),box.getMinZ());
            int x8 = getContainer().getPixelDrawingPositionX(box.getMaxX(),box.getMaxY(),box.getMaxZ());

            int y1 = getContainer().getPixelDrawingPositionY(box.getMinX(),box.getMinY(),box.getMinZ());
            int y2 = getContainer().getPixelDrawingPositionY(box.getMinX(),box.getMinY(),box.getMaxZ());
            int y3 = getContainer().getPixelDrawingPositionY(box.getMinX(),box.getMaxY(),box.getMinZ());
            int y4 = getContainer().getPixelDrawingPositionY(box.getMinX(),box.getMaxY(),box.getMaxZ());
            int y5 = getContainer().getPixelDrawingPositionY(box.getMaxX(),box.getMinY(),box.getMinZ());
            int y6 = getContainer().getPixelDrawingPositionY(box.getMaxX(),box.getMinY(),box.getMaxZ());
            int y7 = getContainer().getPixelDrawingPositionY(box.getMaxX(),box.getMaxY(),box.getMinZ());
            int y8 = getContainer().getPixelDrawingPositionY(box.getMaxX(),box.getMaxY(),box.getMaxZ());

            g.setColor(Color.RED);
            g.drawLine(x1,y1,x2,y2);
            g.drawLine(x3,y3,x4,y4);
            g.drawLine(x1,y1,x3,y3);
            g.drawLine(x2,y2,x4,y4);

            g.setColor(Color.RED);
            g.drawLine(x5,y5,x6,y6);
            g.drawLine(x7,y7,x8,y8);
            g.drawLine(x5,y5,x7,y7);
            g.drawLine(x6,y6,x8,y8);

            g.setColor(Color.RED);
            g.drawLine(x1,y1,x5,y5);
            g.drawLine(x2,y2,x6,y6);
            g.drawLine(x3,y3,x7,y7);
            g.drawLine(x4,y4,x8,y8);
        }
    }
}

