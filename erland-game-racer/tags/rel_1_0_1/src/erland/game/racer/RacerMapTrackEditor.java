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

import erland.game.BlockContainerData;
import erland.game.MapEditorBlockInterface;
import erland.game.component.EButton;
import erland.game.component.EPanel;
import erland.util.ParameterSerializable;

import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

/**
 * Implements a level editor that edits the main track
 */
public class RacerMapTrackEditor extends RacerMapEditor {
    /** Image with sub images of all blocks in the block select area */
    protected Image mapBlockImage;
    /** Extended information about the level, for example the position of the start grid */
    protected ExtendedLevelInfo levelInfo;
    /** Indicates if the next left click is setting the position of the start grid instead of inserting a block */
    protected boolean bMoveStartBlock;

    protected String getLevelFileLabel()
    {
        return "levels";
    }

    protected String getLevelFileGroupLabel()
    {
        return "level";
    }

    protected int getSizeX()
    {
        return 10;
    }
    protected int getSizeY()
    {
        return 10;
    }
    protected void initFinish()
    {
        levelManager = new LevelManager();
        mapBlockImage = getEnvironment().getImageCreator().createImage(256,512);
        LevelFactoryInterface levelFactoryLevel = new RacerLevelFactorySimple(new BlockTypeClone(getSelectBlocks()),cont.getSizeX(),cont.getSizeY());
        levelManager.init(getEnvironment(),levelFactoryLevel,getLevelFileLabel(),getLevelFileGroupLabel());
        levelManager.setContainer(cont);

        // Create icon image with block bitmaps
        LevelManager iconLevelManager = new LevelManager();
        LevelFactoryInterface levelFactoryIcon = new RacerLevelFactoryGameIcon(new BlockClone());
        iconLevelManager.init(getEnvironment(),levelFactoryIcon,"mapicons","mapicon");
        iconLevelManager.setContainer(new BlockContainerData(0,0,1,1,32));
        Image mapIconImage = getEnvironment().getImageCreator().createImage(256,512);
        int noOfBlocks = drawBlocksOnImage(iconLevelManager,mapIconImage,cont.getSquareSize(),true);

        BlockGroup[][] icons = new BlockGroup[noOfBlocks][1];
        for(int i=0;i<noOfBlocks;i++) {
            icons[i][0] = new BlockGroup();
            icons[i][0].init(getEnvironment());
            icons[i][0].setImage(mapIconImage,i);
            icons[i][0].setBlockType(i+1);
        }

        // Create zoomed out blocks
        LevelManager blockLevelManager = new LevelManager();
        LevelFactoryInterface levelFactoryBlock = new RacerLevelFactorySimple(new BlockTypeClone(icons),3,3);
        blockLevelManager.init(getEnvironment(),levelFactoryBlock,"mapblocks","mapblock");
        blockLevelManager.setContainer(new BlockContainerData(0,0,1,1,32));
        drawBlocksOnImage(blockLevelManager,mapBlockImage,11,true);

        // Create start blocks
        if(levelInfo==null) {
            levelInfo = new ExtendedLevelInfo();
            levelInfo.setStartDirection(0);
            levelInfo.setStartPosX(0);
            levelInfo.setStartPosY(0);
        }
        bMoveStartBlock = false;
    }

    /**
     * Draws all blocks handled by the specified level manager onto a image
     * @param levelManager The level manager handling the blocks that should be drawn to the image
     * @param image The image to draw on
     * @param blockSize The size the blocks should be drawn with, can be different than the original blocks
     * @param clearImage Indicates if the image should be cleard before drawing or not
     * @return Number of drawn blocks
     */
    protected int drawBlocksOnImage(LevelManager levelManager,Image image,int blockSize,boolean clearImage)
    {
        boolean bLast = false;
        int i=0;
        if(clearImage) {
            image.getGraphics().setColor(Color.black);
            image.getGraphics().fillRect(-1,-1,image.getWidth(null)+2,image.getHeight(null)+2);
        }
        Image imgTemp = getEnvironment().getImageCreator().createImage(cont.getSquareSize(),cont.getSquareSize());
        while(!bLast) {
            MapEditorBlockInterface[][] blocks = null;
            LevelInfoInterface levelInfo = levelManager.getLevel(i+1);
            if(levelInfo!=null) {
                blocks = levelInfo.getBlocks();
            }
            if(blocks!=null) {
                for(int x=0;x<blocks.length;x++) {
                    for(int y=0;y<blocks[0].length;y++) {
                        if(blocks[x][y]!=null) {
                            blocks[x][y].setPos(0,0);
                            imgTemp.getGraphics().setColor(Color.black);
                            imgTemp.getGraphics().fillRect(-1,-1,imgTemp.getWidth(null)+2,imgTemp.getHeight(null)+2);
                            drawBlock(imgTemp.getGraphics(),blocks[x][y]);
                            int posX = (i%8)*32+x*blockSize;
                            int posY = (i/8)*32+y*blockSize;
                            image.getGraphics().drawImage(imgTemp,posX,posY,posX+blockSize,posY+blockSize,0,0,32,32,null);

                        }
                    }
                }
                i++;
            }else {
                bLast = true;
            }
        }
        int noOfImages = i;

        image.getGraphics().setColor(Color.black);
        for(;i<8*8;i++) {
            int posX = (i%8)*32;
            int posY = (i/8)*32;
            image.getGraphics().fillRect(posX,posY,32,32);
        }
        return noOfImages;
    }

    protected int getSelectSizeX()
    {
        return 4;
    }
    protected int getSelectSizeY()
    {
        return 10;
    }

    protected Image getMapBlockImage()
    {
        return mapBlockImage;
    }
    protected int getMaxLevel()
    {
        return 10;
    }
    /**
     * Initialize buttons thats is needed to make it possible for the
     * user to select position and direction of the start grid
     * @param panel The panel to add the buttons to
     */
    protected void initStartBlockButtons(EPanel panel)
    {
        EButton b = EButton.create("Start Direction");
        panel.getContainer().add(b.getComponent());
        b.addActionListener(new  ActionListener() {
            public void actionPerformed(ActionEvent e) {
                setStartDirection();
            }
        });
        b = EButton.create("Start Position");
        panel.getContainer().add(b.getComponent());
        b.addActionListener(new  ActionListener() {
            public void actionPerformed(ActionEvent e) {
                setStartPosition();
            }
        });
    }

    protected void initButtons(EPanel panel)
    {
        super.initButtons(panel);
        initStartBlockButtons(panel);
    }

    /**
     * Rotates the start grid one step
     */
    protected void setStartDirection()
    {
        int next = levelInfo.getStartDirection()+1;
        if(next>=4) {
            levelInfo.setStartDirection(0);
        }else {
            levelInfo.setStartDirection(next);
        }
    }

    /**
     * Sets that the next click on the main editor area is
     * setting the position of the start grid
     */
    protected void setStartPosition()
    {
        bMoveStartBlock = true;
    }
    protected void clickedSelectBlock(int posX, int posY)
    {
        super.clickedSelectBlock(posX,posY);
        bMoveStartBlock = false;
    }
    protected void clickedMapBlock(int posX, int posY)
    {
        if(!bMoveStartBlock) {
            super.clickedMapBlock(posX,posY);
        }else {
            levelInfo.setStartPosX(posX);
            levelInfo.setStartPosY(posY);
        }
    }
    protected ParameterSerializable getExtendedLevelInfo()
    {
        return levelInfo;
    }
    protected void setExtendedLevelInfo(ParameterSerializable info)
    {
        if(info instanceof ExtendedLevelInfo) {
            levelInfo = (ExtendedLevelInfo)info;
        }else {
            levelInfo = new ExtendedLevelInfo();
            levelInfo.setStartDirection(0);
            levelInfo.setStartPosX(0);
            levelInfo.setStartPosY(0);
        }
    }
    protected void drawFinish(Graphics g)
    {
        g.setColor(Color.white);
        int startBlockPosX = levelInfo.getStartPosX();
        int startBlockPosY = levelInfo.getStartPosY();
        switch(levelInfo.getStartDirection()) {
            case 0:
                g.drawLine(cont.getDrawingPositionX(startBlockPosX),
                    cont.getDrawingPositionY(startBlockPosY)+cont.getSquareSize()/2,
                    cont.getDrawingPositionX(startBlockPosX+1),
                    cont.getDrawingPositionY(startBlockPosY)+cont.getSquareSize()/2);
                g.drawLine(cont.getDrawingPositionX(startBlockPosX+1),
                    cont.getDrawingPositionY(startBlockPosY)+cont.getSquareSize()/2,
                    cont.getDrawingPositionX(startBlockPosX+1)-cont.getSquareSize()/4,
                    cont.getDrawingPositionY(startBlockPosY)+cont.getSquareSize()/4);
                g.drawLine(cont.getDrawingPositionX(startBlockPosX+1),
                    cont.getDrawingPositionY(startBlockPosY)+cont.getSquareSize()/2,
                    cont.getDrawingPositionX(startBlockPosX+1)-cont.getSquareSize()/4,
                    cont.getDrawingPositionY(startBlockPosY+1)-cont.getSquareSize()/4);
                break;
            case 1:
                g.drawLine(cont.getDrawingPositionX(startBlockPosX)+cont.getSquareSize()/2,
                    cont.getDrawingPositionY(startBlockPosY),
                    cont.getDrawingPositionX(startBlockPosX)+cont.getSquareSize()/2,
                    cont.getDrawingPositionY(startBlockPosY+1));
                g.drawLine(cont.getDrawingPositionX(startBlockPosX)+cont.getSquareSize()/2,
                    cont.getDrawingPositionY(startBlockPosY),
                    cont.getDrawingPositionX(startBlockPosX)+cont.getSquareSize()/4,
                    cont.getDrawingPositionY(startBlockPosY)+cont.getSquareSize()/4);
                g.drawLine(cont.getDrawingPositionX(startBlockPosX)+cont.getSquareSize()/2,
                    cont.getDrawingPositionY(startBlockPosY),
                    cont.getDrawingPositionX(startBlockPosX+1)-cont.getSquareSize()/4,
                    cont.getDrawingPositionY(startBlockPosY)+cont.getSquareSize()/4);
                break;
            case 2:
                g.drawLine(cont.getDrawingPositionX(startBlockPosX),
                    cont.getDrawingPositionY(startBlockPosY)+cont.getSquareSize()/2,
                    cont.getDrawingPositionX(startBlockPosX+1),
                    cont.getDrawingPositionY(startBlockPosY)+cont.getSquareSize()/2);
                g.drawLine(cont.getDrawingPositionX(startBlockPosX),
                    cont.getDrawingPositionY(startBlockPosY)+cont.getSquareSize()/2,
                    cont.getDrawingPositionX(startBlockPosX)+cont.getSquareSize()/4,
                    cont.getDrawingPositionY(startBlockPosY)+cont.getSquareSize()/4);
                g.drawLine(cont.getDrawingPositionX(startBlockPosX),
                    cont.getDrawingPositionY(startBlockPosY)+cont.getSquareSize()/2,
                    cont.getDrawingPositionX(startBlockPosX)+cont.getSquareSize()/4,
                    cont.getDrawingPositionY(startBlockPosY+1)-cont.getSquareSize()/4);
                break;
            case 3:
                g.drawLine(cont.getDrawingPositionX(startBlockPosX)+cont.getSquareSize()/2,
                    cont.getDrawingPositionY(startBlockPosY),
                    cont.getDrawingPositionX(startBlockPosX)+cont.getSquareSize()/2,
                    cont.getDrawingPositionY(startBlockPosY+1));
                g.drawLine(cont.getDrawingPositionX(startBlockPosX)+cont.getSquareSize()/2,
                    cont.getDrawingPositionY(startBlockPosY+1),
                    cont.getDrawingPositionX(startBlockPosX)+cont.getSquareSize()/4,
                    cont.getDrawingPositionY(startBlockPosY+1)-cont.getSquareSize()/4);
                g.drawLine(cont.getDrawingPositionX(startBlockPosX)+cont.getSquareSize()/2,
                    cont.getDrawingPositionY(startBlockPosY+1),
                    cont.getDrawingPositionX(startBlockPosX+1)-cont.getSquareSize()/4,
                    cont.getDrawingPositionY(startBlockPosY+1)-cont.getSquareSize()/4);
                break;
            default:
                break;
        }
    }
}
