package erland.game.tileadventure;

import erland.game.component.*;
import erland.game.tileadventure.rect.RectBlockContainerData;
import erland.util.ParameterSerializable;
import erland.util.ParameterValueStorageExInterface;

import javax.swing.*;
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

public class MapObjectEditor extends TileMapEditor {
    /** The draw map implementation to use */
    protected DrawMap drawMap;
    /** The level manager to use */
    protected LevelManager levelManager;
    /** The panel with settings for the tile */
    protected EPanel settingsPanel;
    /** The walkable setting checkbox */
    protected ECheckBox walkableCheckbox;
    /** The pushable setting checkbox */
    protected ECheckBox pushableCheckbox;
    /** The alive setting checkbox */
    protected ECheckBox aliveCheckbox;
    /** The enemy type setting list box panel */
    protected EPanel enemyTypePanel;
    /** The enemy type setting list box */
    protected EListBox enemyTypeListbox;
    /** The enemy type setting list box label*/
    protected ELabel enemyTypeLabel;
    /** The height setting spinner panel */
    protected EPanel heightPanel;
    /** The label of height of the block */
    protected ELabel heightLabel;
    /** The spinner for height of the block */
    protected ENumberSpinner heightSpinner;
    /** The extra object information */
    protected MapObjectExtendedLevelInfo extendedLevelInfo;
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
        return 1;
    }

    protected int getSizeY() {
        return 1;
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

    protected int getMaxHeight() {
        return 0;
    }

    protected Image getMapBlockImage() {
        if(paletteBitmap==null) {
            paletteBitmap = paletteLevelManager.getImage();
        }
        return paletteBitmap;
    }

    public void drawMapBlocks(Graphics g, MapObjectContainerInterface blocks) {
        drawMap.setContainer(getMapContainer());
        drawMap.removeAllObjectMaps();
        drawMap.addObjectMap(blocks);
        drawMap.draw(g);
    }

    public void drawSelectedFrame(Graphics g, MapObjectInterface selectedBlock) {
        g.setColor(Color.WHITE);
        int x1 = selectedBlock.getContainer().getPixelDrawingPositionX(selectedBlock.getPosX(),selectedBlock.getPosY(),selectedBlock.getPosZ());
        int y1 = selectedBlock.getContainer().getPixelDrawingPositionY(selectedBlock.getPosX(),selectedBlock.getPosY(),selectedBlock.getPosZ());
        int x2 = selectedBlock.getContainer().getPixelDrawingPositionX(selectedBlock.getPosX(),selectedBlock.getPosY()+1,selectedBlock.getPosZ());
        int y2 = selectedBlock.getContainer().getPixelDrawingPositionY(selectedBlock.getPosX(),selectedBlock.getPosY()+1,selectedBlock.getPosZ());
        int x3 = selectedBlock.getContainer().getPixelDrawingPositionX(selectedBlock.getPosX()+1,selectedBlock.getPosY(),selectedBlock.getPosZ());
        int y3 = selectedBlock.getContainer().getPixelDrawingPositionY(selectedBlock.getPosX()+1,selectedBlock.getPosY(),selectedBlock.getPosZ());
        int x4 = selectedBlock.getContainer().getPixelDrawingPositionX(selectedBlock.getPosX()+1,selectedBlock.getPosY()+1,selectedBlock.getPosZ());
        int y4 = selectedBlock.getContainer().getPixelDrawingPositionY(selectedBlock.getPosX()+1,selectedBlock.getPosY()+1,selectedBlock.getPosZ());
        g.drawLine(x1,y1,x2,y2);
        g.drawLine(x1,y1,x3,y3);
        g.drawLine(x4,y4,x2,y2);
        g.drawLine(x4,y4,x3,y3);
    }

    public void drawHoveringFrame(Graphics g, int posX, int posY) {
        g.setColor(Color.WHITE);
        int x1 = getMapContainer().getPixelDrawingPositionX(posX,posY,getMapPosZ());
        int y1 = getMapContainer().getPixelDrawingPositionY(posX,posY,getMapPosZ());
        int x2 = getMapContainer().getPixelDrawingPositionX(posX,posY+1,getMapPosZ());
        int y2 = getMapContainer().getPixelDrawingPositionY(posX,posY+1,getMapPosZ());
        int x3 = getMapContainer().getPixelDrawingPositionX(posX+1,posY,getMapPosZ());
        int y3 = getMapContainer().getPixelDrawingPositionY(posX+1,posY,getMapPosZ());
        int x4 = getMapContainer().getPixelDrawingPositionX(posX+1,posY+1,getMapPosZ());
        int y4 = getMapContainer().getPixelDrawingPositionY(posX+1,posY+1,getMapPosZ());
        g.drawLine(x1,y1,x2,y2);
        g.drawLine(x1,y1,x3,y3);
        g.drawLine(x4,y4,x2,y2);
        g.drawLine(x4,y4,x3,y3);
    }

    protected LevelManager getLevelManager() {
        levelManager.setContainer(getMapContainer());
        return levelManager;
    }

    protected void initFinish() {
        super.initFinish();
        drawMap = ((TileGameEnvironmentInterface)getEnvironment().getCustomEnvironment()).createBlockMap();
        levelManager = new MapObjectEditorLevelManager();
        levelManager.init(getEnvironment());
        paletteLevelManager = new AnimationEditorLevelManager();
        paletteLevelManager.init(getEnvironment());
        paletteBitmap = null;

        settingsPanel = EPanel.create();
        settingsPanel.getContainer().setSize(getEnvironment().getScreenHandler().getWidth()*2/5, getEnvironment().getScreenHandler().getHeight()/2);
        settingsPanel.getContainer().setLocation(10,100);

        settingsPanel.getContainer().setLayout(new BoxLayout(settingsPanel.getContainer(), BoxLayout.Y_AXIS));
        getEnvironment().getScreenHandler().add(settingsPanel.getComponent());
        walkableCheckbox = ECheckBox.create("Walkable");
        settingsPanel.getContainer().add(walkableCheckbox.getComponent());
        pushableCheckbox = ECheckBox.create("Pushable");
        settingsPanel.getContainer().add(pushableCheckbox.getComponent());
        aliveCheckbox = ECheckBox.create("Alive");
        settingsPanel.getContainer().add(aliveCheckbox.getComponent());
        enemyTypePanel = EPanel.create();
        enemyTypePanel.getContainer().setLayout(new FlowLayout());
        enemyTypeLabel = ELabel.create("Enemy type");
        enemyTypePanel.getContainer().add(enemyTypeLabel.getComponent());
        enemyTypeListbox = EListBox.create(new String[] {"type1","type2","type3"});
        enemyTypePanel.getContainer().add(enemyTypeListbox.getComponent());
        settingsPanel.getContainer().add(enemyTypePanel.getComponent());
        heightPanel = EPanel.create();
        heightPanel.getContainer().setLayout(new FlowLayout());
        heightLabel = ELabel.create("Height");
        heightPanel.getContainer().add(heightLabel.getComponent());
        heightSpinner = ENumberSpinner.create(1,1,10,1);
        heightPanel.getContainer().add(heightSpinner.getComponent());
        settingsPanel.getContainer().add(heightPanel.getComponent());
    }

    protected void exitFinish() {
        super.exitFinish();
        getEnvironment().getScreenHandler().remove(settingsPanel.getComponent());
    }

    protected void setExtendedLevelInfo(ParameterSerializable info) {
        if(info instanceof MapObjectExtendedLevelInfo) {
            extendedLevelInfo = (MapObjectExtendedLevelInfo) info;
        }else {
            extendedLevelInfo = new MapObjectExtendedLevelInfo();
            extendedLevelInfo.setPushable(Boolean.FALSE);
            extendedLevelInfo.setWalkable(Boolean.FALSE);
        }
    }

    protected ParameterSerializable getExtendedLevelInfo() {
        if(extendedLevelInfo==null) {
            extendedLevelInfo = new MapObjectExtendedLevelInfo();
        }
        extendedLevelInfo.setPushable(Boolean.valueOf(pushableCheckbox.getSelection()));
        extendedLevelInfo.setWalkable(Boolean.valueOf(walkableCheckbox.getSelection()));
        return extendedLevelInfo;
    }

    protected void updateBlocks() {
        super.updateBlocks();
        pushableCheckbox.setSelection(extendedLevelInfo.getPushable().booleanValue());
        walkableCheckbox.setSelection(extendedLevelInfo.getWalkable().booleanValue());
    }
}