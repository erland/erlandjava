package erland.game.tileadventure;

import erland.game.component.*;
import erland.game.tileadventure.rect.RectDrawMap;
import erland.game.tileadventure.rect.RectBlockContainerData;
import erland.util.ParameterSerializable;
import erland.util.ParameterValueStorageExInterface;
import erland.util.StringUtil;

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

public class AnimationEditor extends TileMapEditor {
    /** The draw map implementation to use */
    protected DrawMap drawMap;
    /** The level manager to use */
    protected LevelManager levelManager;
    /** The panel with settings for the tile */
    protected EPanel settingsPanel;
    /** The animation speed setting spinner panel */
    protected EPanel animationSpeedPanel;
    /** The label of animation speed of the block */
    protected ELabel animationSpeedLabel;
    /** The spinner for animation speed  of the block */
    protected ENumberSpinner animationSpeedSpinner;
    /** The extra object information */
    protected AnimationExtendedLevelInfo extendedLevelInfo;


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
        return 5;
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
        String prefix = ((TileGameEnvironmentInterface)getEnvironment().getCustomEnvironment()).getTileType();
        return getEnvironment().getImageHandler().getImage(prefix+"ground.gif");
    }

    public void drawMapBlocks(Graphics g, MapObjectContainerInterface blocks) {
        drawMap.setContainer(getMapContainer());
        drawMap.removeAllObjectMaps();
        drawMap.addObjectMap(blocks);
        drawMap.draw(g);
    }

    public void drawSelectedFrame(Graphics g, MapObjectInterface selectedBlock) {
        g.setColor(Color.WHITE);
        int x = selectedBlock.getContainer().getPixelDrawingPositionX(selectedBlock.getPosX(),selectedBlock.getPosY(),selectedBlock.getPosZ());
        int y = selectedBlock.getContainer().getPixelDrawingPositionY(selectedBlock.getPosX(),selectedBlock.getPosY(),selectedBlock.getPosZ());
        g.drawRect(x,y,selectedBlock.getContainer().getSquareSizeX(),selectedBlock.getContainer().getSquareSizeY());
    }

    public void drawHoveringFrame(Graphics g, int posX, int posY) {
        g.setColor(Color.WHITE);
        int x = getMapContainer().getPixelDrawingPositionX(posX,posY,getMapPosZ());
        int y = getMapContainer().getPixelDrawingPositionY(posX,posY,getMapPosZ());
        g.drawRect(x,y,getMapContainer().getSquareSizeX(),getMapContainer().getSquareSizeY());
    }

    protected LevelManager getLevelManager() {
        levelManager.setContainer(getMapContainer());
        return levelManager;
    }

    protected void initFinish() {
        super.initFinish();
        drawMap = new RectDrawMap();
        levelManager = new AnimationEditorLevelManager();
        levelManager.init(getEnvironment());
        settingsPanel = EPanel.create();
        settingsPanel.getContainer().setSize(getEnvironment().getScreenHandler().getWidth()*2/5, getEnvironment().getScreenHandler().getHeight()/2);
        settingsPanel.getContainer().setLocation(10,200);

        settingsPanel.getContainer().setLayout(new BoxLayout(settingsPanel.getContainer(), BoxLayout.Y_AXIS));
        getEnvironment().getScreenHandler().add(settingsPanel.getComponent());
        animationSpeedPanel = EPanel.create();
        animationSpeedPanel.getContainer().setLayout(new FlowLayout());
        animationSpeedLabel = ELabel.create("Speed");
        animationSpeedPanel.getContainer().add(animationSpeedLabel.getComponent());
        animationSpeedSpinner = ENumberSpinner.create(1,1,100,10);
        animationSpeedPanel.getContainer().add(animationSpeedSpinner.getComponent());
        settingsPanel.getContainer().add(animationSpeedPanel.getComponent());
    }

    protected void exitFinish() {
        super.exitFinish();
        getEnvironment().getScreenHandler().remove(settingsPanel.getComponent());
    }

    protected void setExtendedLevelInfo(ParameterSerializable info) {
        if(info instanceof AnimationExtendedLevelInfo) {
            extendedLevelInfo = (AnimationExtendedLevelInfo) info;
        }else {
            extendedLevelInfo = new AnimationExtendedLevelInfo();
            extendedLevelInfo.setSpeed(1);
        }
    }

    protected ParameterSerializable getExtendedLevelInfo() {
        if(extendedLevelInfo==null) {
            extendedLevelInfo = new AnimationExtendedLevelInfo();
        }
        extendedLevelInfo.setSpeed(animationSpeedSpinner.getValue());
        return extendedLevelInfo;
    }

    protected void updateBlocks() {
        super.updateBlocks();
        animationSpeedSpinner.setValue(extendedLevelInfo.getSpeed());
    }

    protected IrregularBlockContainerInterface getMapContainer() {
        if(cont==null) {
            TileGameEnvironmentInterface env = ((TileGameEnvironmentInterface)getEnvironment().getCustomEnvironment());
            BlockContainerData cont = env.createRectBlockContainer(10,10,getSizeX(),getSizeY(),getSizeZ());
            this.cont = cont;
        }
        return cont;
    }
}