package erland.game.racer;
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

import erland.game.*;
import erland.game.component.EPanel;
import erland.game.component.ETextField;
import erland.util.*;

import javax.swing.*;
import javax.swing.event.ChangeListener;
import javax.swing.event.ChangeEvent;
import java.awt.*;

/**
 * This is a base class for all different map/icon editors
 * in the racer game
 */
public abstract class RacerMapEditor extends MapEditor {
    /** Block container for the editor area */
    protected BlockContainerData cont;
    /** Block container for the block select area */
    protected BlockContainerData contSelect;
    /** Blocks in the editor area */
    protected MapEditorBlockInterface[][] mapBlocks;
    /** Blocks in the select block area */
    protected MapEditorBlockInterface[][] selectBlocks;
    /** Spinner that changes the current level/block edited */
    protected SpinnerNumberModel levelNoSpinner;
    /** Currently edited level/block number */
    protected int levelNo = 1;
    /** Level manager that handles loading/saving of the different levels/blocks */
    protected LevelManager levelManager;
    /** Extended information about the currently edited level/block */
    protected ParameterSerializable extendedLevelInfo;


    public void init(GameEnvironmentInterface environment)
    {
        selectBlocks = null;
        mapBlocks = null;
        cont = null;
        contSelect = null;
        super.init(environment);
        // Preload images if not already loaded
        environment.getImageHandler().getImage("car1.gif");
        environment.getImageHandler().getImage("car2.gif");
        environment.getImageHandler().getImage("car3.gif");
        environment.getImageHandler().getImage("car4.gif");
        environment.getImageHandler().getImage("mapicons.gif");
        environment.getImageHandler().getImage("specialmapicons.gif");
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


    protected BlockContainerInterface getMapContainer(int offsetX, int offsetY)
    {
        if(cont==null) {
            cont = new BlockContainerData(offsetX+10,offsetY+10,getSizeX(),getSizeY(),32);
        }
        return cont;
    }


    protected BlockContainerInterface getSelectContainer(int offsetX, int offsetY)
    {
        if(contSelect==null) {
            contSelect = new BlockContainerData(offsetX+10+33*10,offsetY+10,getSelectSizeX(),getSelectSizeY(),32);
        }
        return contSelect;
    }

    /**
     * Get a new block based on the specified block from the select block area
     * @param blockNo Block number of the block to get
     * @return A newly created block
     */
    protected MapEditorBlockInterface getSelectBlock(int blockNo)
    {
        BlockGroup b = new BlockGroup();
        b.init(getEnvironment());
        b.setImage(getMapBlockImage(),blockNo);
        b.setBlockType(blockNo+1);
        return b;
    }

    /**
     * Get block number of the first block in the select block area
     * @return The block number
     */
    protected int getFirstSelectBlock()
    {
        return 0;
    }
    /**
     * Get the number of blocks in the select block area
     * @return The number of blocks
     */
    protected int getNoOfSelectBlocks()
    {
        return -1;
    }
    protected MapEditorBlockInterface[][] getSelectBlocks()
    {
        if(selectBlocks==null) {
            selectBlocks = new MapEditorBlockInterface[contSelect.getSizeX()][contSelect.getSizeY()];
            int i=getFirstSelectBlock();
            for(int x=0;x<selectBlocks.length;x++) {
                for(int y=0;y<selectBlocks[0].length-1;y++) {
                    if(getNoOfSelectBlocks()==-1 || i<(getNoOfSelectBlocks()+getFirstSelectBlock())) {
                        selectBlocks[x][y] = getSelectBlock(i);
                        if(selectBlocks[x][y]!=null) {
                            selectBlocks[x][y].setContainer(contSelect);
                            selectBlocks[x][y].setPos(x,y);
                        }
                        i++;
                    }else {
                        selectBlocks[x][y]=null;
                    }
                }
            }
        }
        return selectBlocks;
    }

    protected MapEditorBlockInterface[][] getMapBlocks()
    {
        if(mapBlocks==null) {
            LevelInfoInterface levelInfo = levelManager.getLevel(getLevelNo());
            mapBlocks = null;
            if(levelInfo!=null) {
                mapBlocks = levelInfo.getBlocks();
                setExtendedLevelInfo(levelInfo.getExtendedInfo());
            }
            if(mapBlocks==null) {
                mapBlocks = new MapEditorBlockInterface[cont.getSizeX()][cont.getSizeY()];
            }
        }
        return mapBlocks;
    }


    protected void drawBlock(Graphics g, MapEditorBlockInterface block)
    {
        ((Block)block).draw(g,0);
    }

    protected MapEditorBlockInterface cloneBlock(MapEditorBlockInterface block,BlockContainerInterface cont, int x, int y)
    {
        Block b = (Block)block;
        Block newBlock = null;
        try {
            newBlock = (Block)b.clone();
            newBlock.setPos(x,y);
            newBlock.setContainer(cont);
        } catch (CloneNotSupportedException e) {
        }
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
        ETextField label = ETextField.create(getLevelLabelText());
        label.setEditable(false);
        p.getContainer().add(label.getComponent());
        levelNoSpinner = new SpinnerNumberModel(levelNo,1,getMaxLevel(),1);
        levelNoSpinner.addChangeListener(new ChangeListener() {
            public void stateChanged(ChangeEvent e) {
                loadButton();
            }
        });
        JSpinner track = new JSpinner(levelNoSpinner);
        (((JSpinner.DefaultEditor)(track.getEditor())).getTextField()).setEditable(false);
        p.getContainer().add(track);
        panel.getContainer().add(p.getComponent());
    }

    protected void initButtons(EPanel panel)
    {
        initLevelChangeButtons(panel);
    }
    /**
     * Get currently edited level/block
     * @return Block/level number of the currently edited block
     */
    protected int getLevelNo()
    {
        int no =0;
        try {
            no = levelNoSpinner.getNumber().intValue();
        } catch (NumberFormatException e) {
        }
        return no;
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

    protected void saveButton(MapEditorBlockInterface[][] blocks)
    {
        levelManager.setLevel(getLevelNo(),blocks,getExtendedLevelInfo());
    }

    protected void loadButton()
    {
        mapBlocks = null;
        LevelInfoInterface levelInfo = levelManager.getLevel(getLevelNo());
        if(levelInfo!=null) {
            mapBlocks = levelInfo.getBlocks();
            setExtendedLevelInfo(levelInfo.getExtendedInfo());
        }
        updateBlocks();
    }
}
