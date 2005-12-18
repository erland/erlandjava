package erland.game.tileadventure;

import erland.game.GamePanelInterface;
import erland.game.GameEnvironmentInterface;
import erland.game.component.EPanel;
import erland.game.component.EButton;

import javax.swing.*;
import java.awt.event.*;
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

/**
 * Abstract class that implement basic behaviour in a map editor
 * @author Erland Isaksson
 */
public abstract class MapEditor implements GamePanelInterface {
    /** Indicates the the map editor should be closed */
    private boolean bQuit;
    /** MouseListener object */
    private MouseListener mouseListener;
    /** Block container for blocks in the map */
    private IrregularBlockContainerInterface cont;
    /** Block container for blocks in the block selection area */
    private IrregularBlockContainerInterface contPalette;
    /** Matrix with all selection blocks */
    private MapObjectContainerInterface paletteBlocks;
    /** Matrix with all blocks in the map */
    private MapObjectContainerInterface mapBlocks;
    /** Currently selected block in the block selection area */
    private MapObjectInterface selectedBlock;
    /** The x position of the block which the pointer is hovering over in the block map area */
    private int hoveringBlockPosX=-1;
    /** The x position of the block which the pointer is hovering over in the block map area */
    private int hoveringBlockPosY=-1;
    /** EPanel containing all the main buttons */
    private EPanel buttonPanel;
    /** MouseMotion listener */
    private MouseMotionListener mouseMotionListener;
    /** Game environment object */
    private GameEnvironmentInterface environment;

    public boolean isExit() {
        return bQuit;
    }

    public void exit() {
        environment.getScreenHandler().getContainer().removeMouseListener(mouseListener);
        environment.getScreenHandler().getContainer().removeMouseMotionListener(mouseMotionListener);
        if(buttonPanel!=null) {
            environment.getScreenHandler().remove(buttonPanel.getComponent());
        }
        exitFinish();
    }

    /**
     * Get block container for the map area
     * @return The block container
     */
    protected abstract IrregularBlockContainerInterface getMapContainer();

    /**
     * Get block container for the block selection area
     * @return The block container
     */
    protected abstract IrregularBlockContainerInterface getPaletteContainer();

    /**
     * Get matrix with all blocks in the block selection area
     * @return Matrix with the blocks
     */
    protected abstract MapObjectContainerInterface getPaletteBlocks();

    /**
     * Get matrix with all blocks in the map
     * @return Matrix with the blocks
     */
    protected abstract MapObjectContainerInterface getMapBlocks();


    /**
     * Prepare a new block so it can be inserted in the map
     * @param oldBlock Old block at the same position
     * @param newBlock New block that should be inserted
     * @return Prepared block that should be inserted, may be same as newBlock
     * but may also be a completely new block based on newBlock and oldBlock
     */
    protected MapObjectInterface prepareNewBlock(MapObjectInterface oldBlock,MapObjectInterface newBlock)
    {
        return newBlock;
    }

    public void init(GameEnvironmentInterface environ) {
        bQuit = false;
        this.environment = environ;

        mouseListener = new MouseAdapter() {
            public void mousePressed(MouseEvent e) {
                if (e.getButton() == 1) {
                    leftMousePressed(environment.getScreenHandler().getScreenX(e.getX()),environment.getScreenHandler().getScreenY(e.getY()));
                }
            }
        };
        mouseMotionListener = new MouseMotionAdapter() {
            public void mouseMoved(MouseEvent e) {
                mouseHovering(environment.getScreenHandler().getScreenX(e.getX()),environment.getScreenHandler().getScreenY(e.getY()));
            }

            public void mouseDragged(MouseEvent e) {
                if ((e.getModifiers() & e.BUTTON1_MASK)!=0) {
                    leftMouseDragged(environment.getScreenHandler().getScreenX(e.getX()),environment.getScreenHandler().getScreenY(e.getY()));
                }
            }
        };
        environment.getScreenHandler().getContainer().addMouseListener(mouseListener);
        environment.getScreenHandler().getContainer().addMouseMotionListener(mouseMotionListener);
        buttonPanel = initButtonPanel();
        if(buttonPanel!=null) {
            initDefaultButtons(buttonPanel);
            initButtons(buttonPanel);
            environment.getScreenHandler().add(buttonPanel.getComponent());
        }

        contPalette = getPaletteContainer();
        cont = getMapContainer();
        initFinish();
        updateBlocks();

        environment.getScreenHandler().getContainer().setBackground(Color.black);
    }

    /**
     * Called when a block on the map is clicked, this method will either insert the
     * selected block in the clicked map position or remove the current block at the
     * map position
     * @param posX X position of the block in the map that was clicked
     * @param posY Y position of the block in the map that was clicked
     */
    protected void clickedMapBlock(int posX, int posY)
    {
        if(selectedBlock!=null) {
            setMapBlock(prepareNewBlock(mapBlocks.getBlock(posX,posY,getMapPosZ()),cloneBlock(selectedBlock,cont,posX,posY,getMapPosZ())),posX,posY,getMapPosZ());
        }else {
            if(isEmptyAllowed()) {
                setMapBlock(null,posX,posY,getMapPosZ());
            }
        }
    }

    /**
     * Uppdates the map with a new block
     * @param block The new block to insert in map
     * @param posX The x position of the new block
     * @param posY The y position of the new block
     * @param posZ The z position of the new block
     */
    protected void setMapBlock(MapObjectInterface block, int posX, int posY, int posZ) {
        mapBlocks.setBlock(block,posX,posY,posZ);
    }

    /**
     * Called when the mouse pointer is hovering over a block on the map , this method will
     * just be called to make it possible to show which block the mouse is hovering over
     * The method is called with -1 in both parameters if the mouse is not hovering over any block
     * @param posX X position of the block in the map that was hovered over
     * @param posY Y position of the block in the map that was hovered over
     */
    protected void hoverMapBlock(int posX, int posY)
    {
        hoveringBlockPosX = posX;
        hoveringBlockPosY = posY;
    }

    /**
     * Called when a block in the selection area is clicked, this method will make
     * the clicked block the currently selected block or if there were no block
     * at the clicked make no block selected
     * @param posX X position of the block in the selection area that was clicked
     * @param posY Y position of the block in the selection area that was clicked
     */
    protected void clickedSelectBlock(int posX,int posY)
    {
        if(paletteBlocks.getBlock(posX,posY,getPalettePosZ())!=null) {
            selectedBlock = paletteBlocks.getBlock(posX,posY,getPalettePosZ());
        }else {
            selectedBlock = null;
        }
    }
    protected void leftMouseDragged(int x, int y)
    {
        Rectangle rc = new Rectangle(cont.getOffsetX(),cont.getOffsetY(),cont.getDrawingSizeX(),cont.getDrawingSizeY());
        if(rc.contains(x,y)) {

            int posX = cont.getBlockPositionX(x,y,getMapPosZ()*cont.getSquareSizeZ());
            int posY = cont.getBlockPositionY(x,y,getMapPosZ()*cont.getSquareSizeZ());
            if(posX>=0 && posX<mapBlocks.getSizeX() && posY>=0 && posY<mapBlocks.getSizeY()) {
                clickedMapBlock(posX,posY);
                hoverMapBlock(posX,posY);
            }else {
                hoverMapBlock(-1,-1);
            }
        }
    }
    protected void mouseHovering(int x, int y)
    {
        Rectangle rc = new Rectangle(cont.getOffsetX(),cont.getOffsetY(),cont.getDrawingSizeX(),cont.getDrawingSizeY());
        if(rc.contains(x,y)) {
            int posX = cont.getBlockPositionX(x,y,getMapPosZ()*cont.getSquareSizeZ());
            int posY = cont.getBlockPositionY(x,y,getMapPosZ()*cont.getSquareSizeZ());
            if(posX>=0 && posX<mapBlocks.getSizeX() && posY>=0 && posY<mapBlocks.getSizeY()) {
                hoverMapBlock(posX,posY);
            }else {
                hoverMapBlock(-1,-1);
            }
        }else {
            hoverMapBlock(-1,-1);
        }
    }
    protected void leftMousePressed(int x, int y)
    {
        Rectangle rc = new Rectangle(cont.getOffsetX(),cont.getOffsetY(),cont.getDrawingSizeX(),cont.getDrawingSizeY());
        Rectangle rcSelect = new Rectangle(contPalette.getOffsetX(),contPalette.getOffsetY(),contPalette.getDrawingSizeX(),contPalette.getDrawingSizeY());
        if(rc.contains(x,y)) {
            leftMouseDragged(x,y);
        }else if(rcSelect.contains(x,y)) {
            int posX = contPalette.getBlockPositionX(x,y,getMapPosZ()*cont.getSquareSizeZ());
            int posY = contPalette.getBlockPositionY(x,y,getMapPosZ()*cont.getSquareSizeZ());
            if(posX>=0 && posX<paletteBlocks.getSizeX() && posY>=0 && posY<paletteBlocks.getSizeY()) {
                clickedSelectBlock(posX,posY);
            }
        }
    }

    /**
     * Update all blocks
     */
    protected void updateBlocks()
    {
        paletteBlocks = getPaletteBlocks();
        mapBlocks = getMapBlocks();
    }
    public void update() {
    }

    /**
     * Draw the specified block
     * @param g Graphics object to draw on
     * @param block Block to draw
     */
    protected void drawBlock(Graphics g, MapObjectInterface block) {
        block.draw(g);
    }

    /**
     * Clone specified block
     * @param block Block to clone
     * @param cont Block container to put new block in
     * @param x X position to put new block at
     * @param y Y position to put new block at
     * @param z Z position to put new block at
     * @return Newly cloned block
     */
    protected abstract MapObjectInterface cloneBlock(MapObjectInterface block,IrregularBlockContainerInterface cont, int x, int y, int z);

    /**
     * Checks if it is allowed to have positions in the map without any block
     * @return true/false (Empty positions allowed/No empty positions allowed)
     */
    protected abstract boolean isEmptyAllowed();

    /**
     * Create and initialize all buttons
     * @param panel EPanel to put buttons on
     */
    protected void initButtons(EPanel panel)
    {
    }
    /**
     * Called once when the initialization of the map editor is finished
     */
    protected void initFinish()
    {
    }

    /**
     * Called once when exit of the map editor is finished
     */
    protected void exitFinish()
    {
    }

    /**
     * Called when the standard save button is pressed, the current blocks in
     * the map area should be saved to the map
     * @param blocks The blocks in the map area
     */
    protected void saveButton(MapObjectContainerInterface blocks)
    {
    }
    /**
     * Called when the standard load button is pressed, a new map should be
     * loaded
     */
    protected void loadButton()
    {
    }
    /**
     * Called when the standard exit button is pressed, the map editor should
     * exit
     */
    protected void exitButton()
    {
        bQuit = true;
    }
    /** Standard Exit button, used in {@link #getDefaultButtons} */
    protected final static int BUTTON_EXIT=1;
    /** Standard Save button, used in {@link #getDefaultButtons} */
    protected final static int BUTTON_SAVE=2;
    /** Standard Load button, used in {@link #getDefaultButtons} */
    protected final static int BUTTON_LOAD=3;
    /**
     * Called to get which default buttons that should be available, the return value
     * is a bitmask composed by {@link #BUTTON_EXIT}, {@link #BUTTON_LOAD}, {@link #BUTTON_SAVE}
     * @return Bitmask that indicates which default buttons that should be used
     */
    protected int getDefaultButtons()
    {
        return BUTTON_EXIT|BUTTON_SAVE|BUTTON_LOAD;
    }

    /**
     * Create and initialize default butons
     * @param panel EPanel which the buttons should be added to
     */
    protected void initDefaultButtons(EPanel panel)
    {
        int defaultbuttons = getDefaultButtons();
        if((defaultbuttons & BUTTON_EXIT)!=0) {
            EButton b = EButton.create("Exit");
            panel.getContainer().add(b.getComponent());
            b.addActionListener(new  ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    exitButton();
                }
            });
        }
        if((defaultbuttons & BUTTON_SAVE)!=0) {
            EButton b = EButton.create("Save");
            panel.getContainer().add(b.getComponent());
            b.addActionListener(new  ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    saveButton(mapBlocks);
                }
            });
        }
        if((defaultbuttons & BUTTON_LOAD)!=0) {
            EButton b = EButton.create("Load");
            panel.getContainer().add(b.getComponent());
            b.addActionListener(new  ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    loadButton();
                }
            });
        }
    }
    /**
     * Create and initialize button panel
     * @return The newly created EPanel for the buttons
     */
    protected EPanel initButtonPanel()
    {
        EPanel panel = EPanel.create();
        panel.getContainer().setSize(environment.getScreenHandler().getWidth()/5, environment.getScreenHandler().getHeight());
        panel.getContainer().setLocation(environment.getScreenHandler().getWidth()*4/5,0);

        panel.getContainer().setLayout(new BoxLayout(panel.getContainer(), BoxLayout.Y_AXIS));
        return panel;
    }

    /**
     * Called everything normal has been drawed, can be used to do extra drawing on top of
     * the other graphics
     * @param g The Graphics object to draw on
     */
    protected void drawFinish(Graphics g)
    {
    }

    public abstract void drawMapBlocks(Graphics g, MapObjectContainerInterface blocks);

    public abstract void drawSelectedFrame(Graphics g, MapObjectInterface selectedBlock);

    public abstract void drawHoveringFrame(Graphics g, int posX, int posY);

    public void drawPaletteBlocks(Graphics g, MapObjectContainerInterface paletteBlocks) {
        for(int x=0;x<paletteBlocks.getSizeX();x++) {
            for(int y=0;y<paletteBlocks.getSizeY();y++) {
                for (int z = 0; z < paletteBlocks.getSizeZ(); z++) {
                    if(contPalette.getVisible(x,y,z)) {
                        if(paletteBlocks.getBlock(x,y,z)!=null) {
                            drawBlock(g,paletteBlocks.getBlock(x,y,z));
                        }
                    }
                }
            }
        }
    }
    public void draw() {
        Graphics g = environment.getScreenHandler().getCurrentGraphics();
        g.clearRect(0,0,environment.getScreenHandler().getWidth(),environment.getScreenHandler().getHeight());
        g.setColor(Color.red);
        g.drawRect(cont.getOffsetX()-1,cont.getOffsetY()-1,cont.getDrawingSizeX()+1,cont.getDrawingSizeY()+1);
        g.drawRect(contPalette.getOffsetX()-1,contPalette.getOffsetY()-1,contPalette.getDrawingSizeX()+1,contPalette.getDrawingSizeY()+1);
        g.setClip(cont.getOffsetX(),cont.getOffsetY(),cont.getDrawingSizeX(),cont.getDrawingSizeY());
        drawMapBlocks(g,mapBlocks);
        g.setClip(contPalette.getOffsetX(),contPalette.getOffsetY(),contPalette.getDrawingSizeX(),contPalette.getDrawingSizeY());
        drawPaletteBlocks(g, paletteBlocks);
        g.setClip(null);

        if(selectedBlock!=null) {
            g.setColor(Color.white);
            drawSelectedFrame(g,selectedBlock);
        }
        if(hoveringBlockPosX>=0 && hoveringBlockPosY>=0) {
            drawHoveringFrame(g,hoveringBlockPosX,hoveringBlockPosY);
        }
        drawFinish(g);

        environment.getScreenHandler().paintComponents(g);
    }

    public void setCheatmode(boolean enable) {
    }

    public GameEnvironmentInterface getEnvironment() {
        return environment;
    }

    protected int getMapPosZ() {
        return 0;
    }
    protected int getPalettePosZ() {
        return 0;
    }
}
