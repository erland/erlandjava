package erland.game;
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

import erland.game.GamePanelInterface;
import erland.game.BlockContainerInterface;
import erland.game.component.EButton;
import erland.game.component.EPanel;

import javax.swing.*;
import java.awt.event.*;
import java.awt.*;

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
    private BlockContainerInterface cont;
    /** Block container for blocks in the block selection area */
    private BlockContainerInterface contSelect;
    /** Matrix with all selection blocks */
    private MapEditorBlockInterface selectBlocks[][];
    /** Matrix with all blocks in the map */
    private MapEditorBlockInterface mapBlocks[][];
    /** Currently selected block in the block selection area */
    private MapEditorBlockInterface selectedBlock;
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
    }

    /**
     * Get block container for the map area
     * @param offsetX X pixel offset from upper left corner
     * @param offsetY Y pixel offset from upper left corner
     * @return The block container
     */
    protected abstract BlockContainerInterface getMapContainer(int offsetX, int offsetY);

    /**
     * Get block container for the block selection area
     * @param offsetX X pixel offset from upper left corner
     * @param offsetY Y pixel offset from upper left corner
     * @return The block container
     */
    protected abstract BlockContainerInterface getSelectContainer(int offsetX, int offsetY);

    /**
     * Get matrix with all blocks in the block selection area
     * @return Matrix with the blocks
     */
    protected abstract MapEditorBlockInterface[][] getSelectBlocks();

    /**
     * Get matrix with all blocks in the map
     * @return Matrix with the blocks
     */
    protected abstract MapEditorBlockInterface[][] getMapBlocks();


    /**
     * Prepare a new block so it can be inserted in the map
     * @param oldBlock Old block at the same position
     * @param newBlock New block that should be inserted
     * @return Prepared block that should be inserted, may be same as newBlock
     * but may also be a completely new block based on newBlock and oldBlock
     */
    protected MapEditorBlockInterface prepareNewBlock(MapEditorBlockInterface oldBlock,MapEditorBlockInterface newBlock)
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

        contSelect = getSelectContainer(0,0);
        cont = getMapContainer(0,0);
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
            mapBlocks[posX][posY] = prepareNewBlock(mapBlocks[posX][posY],cloneBlock(selectedBlock,cont,posX,posY));
        }else {
            if(isEmptyAllowed()) {
                mapBlocks[posX][posY] = null;
            }
        }
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
        if(selectBlocks[posX][posY]!=null) {
            selectedBlock = selectBlocks[posX][posY];
        }else {
            selectedBlock = null;
        }
    }
    protected void leftMouseDragged(int x, int y)
    {
        Rectangle rc = new Rectangle(cont.getOffsetX(),cont.getOffsetY(),cont.getDrawingSizeX(),cont.getDrawingSizeY());
        if(rc.contains(x,y)) {
            int posX = (int)(x-rc.getMinX()+cont.getScrollingOffsetX())/cont.getSquareSize();
            int posY = (int)(y-rc.getMinY()+cont.getScrollingOffsetY())/cont.getSquareSize();
            if(posX>=0 && posX<mapBlocks.length && posY>=0 && posY<mapBlocks[posX].length) {
                clickedMapBlock(posX,posY);
            }
        }
    }
    protected void leftMousePressed(int x, int y)
    {
        Rectangle rc = new Rectangle(cont.getOffsetX(),cont.getOffsetY(),cont.getDrawingSizeX(),cont.getDrawingSizeY());
        Rectangle rcSelect = new Rectangle(contSelect.getOffsetX(),contSelect.getOffsetY(),contSelect.getDrawingSizeX(),contSelect.getDrawingSizeY());
        if(rc.contains(x,y)) {
            leftMouseDragged(x,y);
        }else if(rcSelect.contains(x,y)) {
            int posX = (int)(x-rcSelect.getMinX()+contSelect.getScrollingOffsetX())/contSelect.getSquareSize();
            int posY = (int)(y-rcSelect.getMinY()+contSelect.getScrollingOffsetY())/contSelect.getSquareSize();
            if(posX>=0 && posX<selectBlocks.length && posY>=0 && posY<selectBlocks[posX].length) {
                clickedSelectBlock(posX,posY);
            }
        }
    }

    /**
     * Update all blocks
     */
    protected void updateBlocks()
    {
        selectBlocks = getSelectBlocks();
        mapBlocks = getMapBlocks();
    }
    public void update() {
    }

    /**
     * Draw the specified block
     * @param g Graphics object to draw on
     * @param block Block to draw
     */
    protected abstract void drawBlock(Graphics g, MapEditorBlockInterface block);

    /**
     * Clone specified block
     * @param block Block to clone
     * @param cont Block container to put new block in
     * @param x X position to put new block at
     * @param y Y position to put new block at
     * @return Newly cloned block
     */
    protected abstract MapEditorBlockInterface cloneBlock(MapEditorBlockInterface block,BlockContainerInterface cont, int x, int y);

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
     * Called when the standard save button is pressed, the current blocks in
     * the map area should be saved to the map
     * @param blocks The blocks in the map area
     */
    protected void saveButton(MapEditorBlockInterface[][] blocks)
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

    public void draw() {
        Graphics g = environment.getScreenHandler().getCurrentGraphics();
        g.clearRect(0,0,environment.getScreenHandler().getWidth(),environment.getScreenHandler().getHeight());
        g.setColor(Color.red);
        g.drawRect(cont.getDrawingPositionX(0)-1,cont.getDrawingPositionY(0)-1,cont.getDrawingSizeX()+1,cont.getDrawingSizeY()+1);
        g.drawRect(contSelect.getDrawingPositionX(0)-1,contSelect.getDrawingPositionY(0)-1,contSelect.getDrawingSizeX()+1,contSelect.getDrawingSizeY()+1);
        for(int x=0;x<mapBlocks.length;x++) {
            for(int y=0;y<mapBlocks[x].length;y++) {
                if(cont.getVisible(x,y)) {
                    if(mapBlocks[x][y]!=null) {
                        drawBlock(g,mapBlocks[x][y]);
                    }
                }
            }
        }
        for(int x=0;x<selectBlocks.length;x++) {
            for(int y=0;y<selectBlocks[x].length;y++) {
                if(contSelect.getVisible(x,y)) {
                    if(selectBlocks[x][y]!=null) {
                        drawBlock(g,selectBlocks[x][y]);
                    }
                }
            }
        }
        if(selectedBlock!=null) {
            g.setColor(Color.white);
            g.drawRect(contSelect.getDrawingPositionX(selectedBlock.getPosX()),contSelect.getDrawingPositionY(selectedBlock.getPosY()),contSelect.getSquareSize(),contSelect.getSquareSize());
        }
        drawFinish(g);

        environment.getScreenHandler().paintComponents(g);
    }

    public void setCheatmode(boolean enable) {
    }

    public GameEnvironmentInterface getEnvironment() {
        return environment;
    }
}
