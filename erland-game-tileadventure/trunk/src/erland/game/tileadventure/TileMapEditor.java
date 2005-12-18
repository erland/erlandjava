package erland.game.tileadventure;

import erland.game.component.EPanel;
import erland.game.component.ETextField;
import erland.game.component.ELabel;
import erland.game.component.ENumberSpinner;
import erland.game.GameEnvironmentInterface;
import erland.game.tileadventure.rect.RectBlockContainerData;
import erland.util.ParameterSerializable;

import javax.swing.*;
import javax.swing.event.ChangeListener;
import javax.swing.event.ChangeEvent;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

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

public abstract class TileMapEditor extends MapEditor {
    /** Block container for the editor area */
    protected BlockContainerData cont;
    /** Block container for the block select area */
    protected BlockContainerData contPalette;
    /** Blocks in the editor area */
    protected MapObjectContainerInterface mapBlocks;
    /** Blocks in the select block area */
    protected MapObjectContainerInterface paletteBlocks;
    /** Spinner that changes the current level/block edited */
    protected ENumberSpinner levelNoSpinner;
    /** Currently edited level/block number */
    protected int levelNo = 1;
    /** Extended information about the currently edited level/block */
    protected ParameterSerializable extendedLevelInfo;
    /** Spinner that changes the current height level edited */
    protected ENumberSpinner heightLevelSpinner;


    public void init(GameEnvironmentInterface environment)
    {
        paletteBlocks = null;
        mapBlocks = null;
        cont = null;
        contPalette = null;
        super.init(environment);
    }
    /**
     * Get the name that is used for a list of the currently edited object type
     * when it is stored to file, for example "levels"
     * @return The name of the currently edited object type used in storage
     */
    abstract protected String getLevelFileLabel();

    /**
     * Get the name that is used for the currently edited object type
     * when it is stored to file, for example "level"
     * @return The name of the currently edited object type used in storage
     */
    abstract protected String getLevelFileGroupLabel();

    /**
     * Get the horizontal number of blocks in main editor area
     * @return The horizontal number of blocks
     */
    abstract protected int getSizeX();

    /**
     * Get the vertical number of blocks in the main editor area
     * @return The vertical number of blocks
     */
    abstract protected int getSizeY();

    /**
     * Get the height number of blocks in the main editor area
     * @return The height number of blocks
     */
    abstract protected int getSizeZ();

    /**
     * Get the horizontal number of blocks in the select block area
     * @return The horizontal number of blocks
     */
    abstract protected int getSelectSizeX();
    /**
     * Get the vertical number of blocks in the select block area
     * @return The vertical number of blocks
     */
    abstract protected int getSelectSizeY();

    /**
     * Get an image which is based on subimages of all blocks
     * in the block select area
     * @return
     */
    abstract protected Image getMapBlockImage();


    protected IrregularBlockContainerInterface getMapContainer()
    {
        if(cont==null) {
            cont = ((TileGameEnvironmentInterface)(getEnvironment().getCustomEnvironment())).createBlockContainer(10,10,getSizeX(),getSizeY(),getSizeZ());
            cont.setScrollingOffsetY(cont.getPositionY(0,0,cont.getSizeZ()));
        }
        return cont;
    }


    protected IrregularBlockContainerInterface getPaletteContainer()
    {
        if(contPalette==null) {
            TileGameEnvironmentInterface env = ((TileGameEnvironmentInterface)getEnvironment().getCustomEnvironment());
            contPalette = env.createRectBlockContainer(getMapContainer().getOffsetX()+340,getMapContainer().getOffsetY(),getSelectSizeX(),getSelectSizeY(),1);
        }
        return contPalette;
    }

    /**
     * Get a new block based on the specified block from the select block area
     * @param blockNo Block number of the block to get
     * @return A newly created block
     */
    protected MapObjectInterface getPaletteBlock(int blockNo)
    {
        if(blockNo>getFirstPaletteBlock()) {
            MapEditorObject b = new MapEditorObject();
            b.init(getEnvironment());
            b.setContainer(getPaletteContainer());
            b.setImage(getMapBlockImage(),blockNo-1);
            b.setBlockType(blockNo);
            return b;
        }else {
            return null;
        }
    }

    /**
     * Get block number of the first block in the select block area
     * @return The block number
     */
    protected int getFirstPaletteBlock()
    {
        return 0;
    }
    /**
     * Get the number of blocks in the select block area
     * @return The number of blocks
     */
    protected int getNoOfPaletteBlocks()
    {
        return -1;
    }
    protected MapObjectContainerInterface getPaletteBlocks()
    {
        if(paletteBlocks==null) {
            paletteBlocks = new MapBlockContainer(contPalette.getSizeX(),contPalette.getSizeY(),1);
            int i=getFirstPaletteBlock();
            for(int x=0;x<paletteBlocks.getSizeX();x++) {
                for(int y=0;y<paletteBlocks.getSizeY();y++) {
                    if(getNoOfPaletteBlocks()==-1 || i<(getNoOfPaletteBlocks()+getFirstPaletteBlock())) {
                        MapObjectInterface b = getPaletteBlock(i);
                        if(b!=null) {
                            b.setContainer(contPalette);
                            b.setPos(x,y,0);
                            paletteBlocks.setBlock(b,x,y,0);
                        }
                        i++;
                    }else {
                        paletteBlocks.setBlock(null,x,y,0);
                    }
                }
            }
        }
        return paletteBlocks;
    }

    protected MapObjectContainerInterface getMapBlocks()
    {
        if(mapBlocks==null) {
            LevelInfoInterface levelInfo = getLevelManager().getLevel(getLevelNo());
            if(levelInfo==null) {
                levelInfo = getLevelManager().createLevel(getLevelNo());
            }
            mapBlocks = levelInfo.getObjects();
            setExtendedLevelInfo(levelInfo.getExtendedInfo());
        }
        return mapBlocks;
    }


    protected MapObjectInterface cloneBlock(MapObjectInterface block,IrregularBlockContainerInterface cont, int x, int y, int z)
    {
        MapObjectInterface b = (MapObjectInterface)block;
        MapObjectInterface newBlock = null;
        newBlock = (MapObjectInterface)b.clone();
        newBlock.setPos(x,y,z);
        newBlock.setContainer(cont);
        return newBlock;
    }

    protected boolean isEmptyAllowed()
    {
        return true;
    }

    /**
     * Get the text that should be shown to the user that indicates
     * what short of object this editor makes it possible for the
     * user to edit, for example "Level:"
     * @return The name of the currently edited object type used in user interface
     */
    protected String getLevelLabelText()
    {
        return "Level:";
    }

    /**
     * Get the text that should be shown to the user that indicates
     * which height level of the map that is currently edited, for example "Height:"
     * If this method returns null, no height spinner will be visible
     * @return The name of the currently edited object type used in user interface
     */
    protected String getHeightLabelText()
    {
        return "Height:";
    }

    /**
     * Get the maximum height levels of blocks
     * @return The maximum numer of height levels of blocks
     */
    protected int getMaxHeight()
    {
        return mapBlocks!=null?mapBlocks.getSizeZ():0;
    }

    /**
     * Get the maximum allowed number of levels/blocks
     * @return The maximum numer of levels/blocks
     */
    protected int getMaxLevel()
    {
        return 10;
    }

    /**
     * Creates buttons for changing currently edited level/blocks
     * @param panel
     */
    protected void initLevelChangeButtons(EPanel panel)
    {
        EPanel p = EPanel.create(new FlowLayout());
        ELabel label = ELabel.create(getLevelLabelText());
        p.getContainer().add(label.getComponent());
        levelNoSpinner = ENumberSpinner.create(levelNo,1,getMaxLevel(),1);
        levelNoSpinner.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                loadButton();
            }
        });
        p.getContainer().add(levelNoSpinner.getComponent());
        panel.getContainer().add(p.getComponent());
    }

    /**
     * Creates buttons for changing currently edited height level
     * @param panel
     */
    protected void initHeightChangeButtons(EPanel panel)
    {
        EPanel p = EPanel.create(new FlowLayout());
        ELabel label = ELabel.create(getHeightLabelText());
        p.getContainer().add(label.getComponent());
        heightLevelSpinner = ENumberSpinner.create(0,0,getMaxHeight(),1);
        p.getContainer().add(heightLevelSpinner.getComponent());
        panel.getContainer().add(p.getComponent());
    }
    protected void initButtons(EPanel panel)
    {
        initLevelChangeButtons(panel);
        initHeightChangeButtons(panel);
    }
    /**
     * Get currently edited level/block
     * @return Block/level number of the currently edited block
     */
    protected int getLevelNo()
    {
        return levelNoSpinner.getValue();
    }

    /**
     * Get extended information about the currently edited level/block
     * @return Extended level/block information
     */
    protected ParameterSerializable getExtendedLevelInfo()
    {
        return extendedLevelInfo;
    }
    /**
     * Set the extended information for the currently edited level/block
     * @param info Extended level/block information
     */
    protected void setExtendedLevelInfo(ParameterSerializable info)
    {
        this.extendedLevelInfo = info;
    }

    /**
     * Prepare the map container for save
     * @param blocks The map container with all the blocks
     * @return The new or unmodified map container which should be saved
     */
    protected MapObjectContainerInterface prepareForSave(MapObjectContainerInterface blocks)
    {
        return blocks;
    }
    protected void saveButton(MapObjectContainerInterface blocks)
    {
        MapObjectContainerInterface saveBlocks = prepareForSave(blocks);
        getLevelManager().setLevel(getLevelNo(),saveBlocks,getExtendedLevelInfo());
    }

    /**
     * Prepare the map container after loading new map data
     * @param blocks The map container with all the blocks
     * @return The new or unmodified map container which should be used
     */
    protected MapObjectContainerInterface prepareAfterLoad(MapObjectContainerInterface blocks)
    {
        return blocks;
    }

    protected void loadButton()
    {
        mapBlocks = null;
        LevelInfoInterface levelInfo = getLevelManager().getLevel(getLevelNo());
        if(levelInfo!=null) {
            mapBlocks = prepareAfterLoad(levelInfo.getObjects());
            setExtendedLevelInfo(levelInfo.getExtendedInfo());
        }
        updateBlocks();
    }

    protected void updateBlocks() {
        super.updateBlocks();
        heightLevelSpinner.setMax(mapBlocks.getSizeZ());
        heightLevelSpinner.setValue(0);
    }

    protected int getMapPosZ() {
        return heightLevelSpinner.getValue();
    }

    /**
     * Gets the level manager or create a new one if it does not
     * already exist
     * @return The LevelManager object which we should use
     */
    protected abstract LevelManager getLevelManager();

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
}