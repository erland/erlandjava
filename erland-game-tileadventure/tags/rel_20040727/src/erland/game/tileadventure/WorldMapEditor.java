package erland.game.tileadventure;

import java.awt.*;

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

public class WorldMapEditor extends TileMapEditor {
    /** The level manager to use */
    protected LevelManager levelManager;
    /** The draw map implementation to use */
    protected DrawMap drawMap;
    /** The level manager to use for palette bitmap */
    protected LevelManager paletteLevelManager;
    /** The palette bitmap */
    protected Image paletteBitmap;

    protected String getLevelFileLabel() {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    protected String getLevelFileGroupLabel() {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    protected int getSizeX() {
        return 10;
    }

    protected int getSizeY() {
        return 10;
    }

    protected int getSizeZ() {
        return 1;
    }

    protected int getSelectSizeX() {
        return 2;
    }

    protected int getSelectSizeY() {
        return 10;
    }

    protected Image getMapBlockImage() {
        if(paletteBitmap==null) {
            paletteBitmap = paletteLevelManager.getImage();
        }
        return paletteBitmap;
    }

    protected LevelManager getLevelManager() {
        levelManager.setContainer(getMapContainer());
        return levelManager;
    }

    public void drawMapBlocks(Graphics g, MapObjectContainerInterface blocks) {
        drawMap.setContainer(getMapContainer());
        drawMap.removeAllObjectMaps();
        drawMap.addObjectMap(blocks);
        drawMap.draw(g);
    }

    protected void initFinish() {
        super.initFinish();
        drawMap = ((TileGameEnvironmentInterface)getEnvironment().getCustomEnvironment()).createBlockMap();
        levelManager = new WorldEditorLevelManager();
        levelManager.init(getEnvironment());
        paletteLevelManager = new RoomEditorLevelManager();
        paletteLevelManager.init(getEnvironment());
        paletteBitmap = null;
    }
}