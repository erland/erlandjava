package erland.game.pipes;
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
import java.awt.*;
import java.awt.event.*;
import erland.game.*;
import erland.game.component.EButton;


/**
 * This is the class that implements the options screen
 */
class PipesOptions
	implements GamePanelInterface, ActionListener
{
	/** Horisontal drawing offset, nothing should be drawn to the left of this position */
	protected int offsetX;
	/** Vertical drawing offset, nothing should be drawn above this position */
	protected int offsetY;
	/** Array with all the buttons */
	protected EButton buttons[];
	/** Blocks allowed */
	protected PipeBlock blocksAllowed[];
	/** Blocks not allowed */
	protected PipeBlock blocksNotAllowed[];
	/** Block container interface object for allowed blocks */
	protected BlockContainerInterface contAllowed;
	/** Block container interface object for allowed blocks */
	protected BlockContainerInterface contNotAllowed;
	/** Size of the blocks */
	protected static final int blockSize = 30;
	/** Currently selected block that the user wants to move */
	protected PipeBlock selectedBlock;	
	/** Indicates if the selected block is in the Allowed or NotAllowed container */
	protected boolean selectedAllowed;
	/** Level factory that creates all blocks for the allowed blocks */
	protected LevelFactory levelFactoryAllowed;
	/** Level factory that creates all blocks for the not allowed blocks*/
	protected LevelFactory levelFactoryNotAllowed;
	/** Indicates if the option screen should be exited */
	protected boolean bExit;
	/** Text fields for number of start blocks and number of empty blocks */
	protected TextField textFields[];

    protected int selPosX, selPosY;
    private GameEnvironmentInterface environment;
    private MouseListener mouseListener;
	/**
	 * Creates a new instance of the main game class
	 * @param offsetX Horisontal drawing offset, nothing should be drawn to the left of this position
	 * @param offsetY Vertical drawing offset, nothing should be drawn above this position
	 */
	public PipesOptions(int offsetX, int offsetY)
	{
		this.offsetX = offsetX;
		this.offsetY = offsetY;
	}

    public void init(GameEnvironmentInterface environ) {
        this.environment = environ;
		this.contAllowed = new BlockContainerData(offsetX+1, offsetY+20,3,10,blockSize);
		this.contNotAllowed = new BlockContainerData(offsetX+1+6*blockSize, offsetY+20,3,10,blockSize);
		levelFactoryAllowed = new LevelFactory(environment,contAllowed,LevelFactory.GameType.UntilWaterStopped);
		levelFactoryNotAllowed = new LevelFactory(environment,contNotAllowed,LevelFactory.GameType.UntilWaterStopped);

        int rightColumnX = contNotAllowed.getDrawingPositionX(0)+contNotAllowed.getSizeX()*blockSize+10;
		buttons = new EButton[4];
		buttons[0] = EButton.create("Save");
		buttons[0].getComponent().setBounds(rightColumnX,offsetY + 20,73,25);
		buttons[1] = EButton.create("Exit");
		buttons[1].getComponent().setBounds(rightColumnX,offsetY + 50,73,25);
		buttons[2] = EButton.create("-->");
		buttons[2].getComponent().setBounds(offsetX + contAllowed.getSizeX()*blockSize+10,offsetY + 50,73,25);
		buttons[3] = EButton.create("<--");
		buttons[3].getComponent().setBounds(offsetX + contAllowed.getSizeX()*blockSize+10,offsetY + 80,73,25);
		if(buttons!=null) {
			for(int i=0;i<buttons.length;i++) {
				buttons[i].addActionListener(this);
				environment.getScreenHandler().add(buttons[i].getComponent());
			}
		}
		textFields = new TextField[5];
		textFields[0] = new TextField(String.valueOf(levelFactoryAllowed.getNumberOfEmptyBlocks()),2);
		textFields[0].setBounds(rightColumnX + 150,100+3,40,18);
		textFields[0].setBackground(Color.white);
		textFields[1] = new TextField(String.valueOf(levelFactoryAllowed.getNumberOfStartBlocks()),2);
		textFields[1].setBounds(rightColumnX + 150,100+28,40,18);
		textFields[1].setBackground(Color.white);
		textFields[2] = new TextField(String.valueOf(levelFactoryAllowed.getTimeUntilWater()),2);
		textFields[2].setBounds(rightColumnX + 150,100+53,40,18);
		textFields[2].setBackground(Color.white);
		textFields[3] = new TextField(String.valueOf(levelFactoryAllowed.getWaterSpeed()),2);
		textFields[3].setBounds(rightColumnX + 150,100+78,40,18);
		textFields[3].setBackground(Color.white);
		textFields[4] = new TextField(String.valueOf(levelFactoryAllowed.getLeftToFill()),2);
		textFields[4].setBounds(rightColumnX + 150,100+103,40,18);
		textFields[4].setBackground(Color.white);
		for (int i=0; i<textFields.length; i++) {
	    	environment.getScreenHandler().add(textFields[i]);
	    }

		selectedBlock = null;
		blocksAllowed = levelFactoryAllowed.getAllowedBlocks();
		blocksNotAllowed = levelFactoryNotAllowed.getNotAllowedBlocks();
		bExit = false;
        mouseListener = new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                if((e.getModifiers() & e.BUTTON1_MASK)!=0) {
				    handleLeftMouseClicked(environment.getScreenHandler().getScreenX(e.getX()),environment.getScreenHandler().getScreenY(e.getY()));
			    }
            }
        };
        environment.getScreenHandler().getContainer().setBackground(Color.black);
        environment.getScreenHandler().getContainer().requestFocus();
        environment.getScreenHandler().getContainer().addMouseListener(mouseListener);
    }

	/**
	 * Exits the game
	 */
	protected void exitOptions()
	{
		bExit = true;
	}

    public void exit() {
        if(buttons!=null) {
            for(int i=0;i<buttons.length;i++) {
                environment.getScreenHandler().remove(buttons[i].getComponent());
            }
        }
        if(textFields!=null) {
            for(int i=0;i<textFields.length;i++) {
                environment.getScreenHandler().remove(textFields[i]);
            }
        }
        environment.getScreenHandler().getContainer().removeMouseListener(mouseListener);
    }

	/**
	 * Checks if the Exit button has been pressed
	 * @return true/false (Pressed/Not pressed)
	 */
	public boolean isExit()
	{
		return bExit;
	}
	/**
	 * Draw all the game graphics
	 */
	public void draw()
	{
        Graphics g = environment.getScreenHandler().getCurrentGraphics();
        g.clearRect(0,0,environment.getScreenHandler().getWidth(),environment.getScreenHandler().getHeight());
		int gameSizeX = (contAllowed.getSizeX() + contNotAllowed.getSizeX() +3)*blockSize+2;
		int gameSizeY = contAllowed.getSizeY()*blockSize+2;
		g.setColor(Color.white);
		g.drawString("Allowed:",contAllowed.getDrawingPositionX(0),contAllowed.getDrawingPositionY(0)-3);
		g.drawString("Not allowed:",contNotAllowed.getDrawingPositionX(0),contNotAllowed.getDrawingPositionY(0)-3);
		g.setColor(Color.blue);
		g.drawRect(contAllowed.getDrawingPositionX(0), contAllowed.getDrawingPositionY(0), 
			contAllowed.getSizeX()*contAllowed.getSquareSize(),contAllowed.getSizeY()*contAllowed.getSquareSize());
		g.drawRect(contNotAllowed.getDrawingPositionX(0), contNotAllowed.getDrawingPositionY(0), 
			contNotAllowed.getSizeX()*contNotAllowed.getSquareSize(),contNotAllowed.getSizeY()*contNotAllowed.getSquareSize());
		
		if(blocksAllowed!=null) {
			for (int i=0; i<blocksAllowed.length; i++) {
				blocksAllowed[i].draw(g);
		    }
		}
		if(blocksNotAllowed!=null) {
			for (int i=0; i<blocksNotAllowed.length; i++) {
				blocksNotAllowed[i].draw(g);
		    }
		}
		
		if(selectedBlock!=null) {
			g.setColor(Color.white);
			BlockContainerInterface cont;
			if(selectedAllowed) {
				cont = contAllowed;
			}else {
				cont = contNotAllowed;
			}
			g.drawRect(selectedBlock.getMovingDrawingPosX(),
			selectedBlock.getMovingDrawingPosY(),
			cont.getSquareSize(),cont.getSquareSize());
		}

		g.setColor(Color.white);
        int rightColumnX = contNotAllowed.getDrawingPositionX(0)+contNotAllowed.getSizeX()*blockSize+10;
		g.drawString("Number of empty blocks:",rightColumnX,100+15);
		g.drawString("Number of start blocks:",rightColumnX,100+40);
		g.drawString("Initial time until water:",rightColumnX,100+65);
		g.drawString("Initial water speed:",rightColumnX,100+90);
		g.drawString("Initial blocks to fill:",rightColumnX,100+115);
		rightColumnX = offsetX+gameSizeX+20;
		g.setColor(Color.red);
		g.drawString("by Erland Isaksson",rightColumnX,offsetY+gameSizeY);
        environment.getScreenHandler().paintComponents(g);
	}



	/**
	 * Called when the left mouse button has been clicked (pressed + released)
	 * @param x X position of the mouse pointer
	 * @param y Y position of the mouse pointer
	 */
	public void handleLeftMouseClicked(int x, int y) {
		BlockContainerInterface cont = null;
		PipeBlock[] blocks = null;
		if(x>=contAllowed.getDrawingPositionX(0) &&
			x<=(contAllowed.getDrawingPositionX(contAllowed.getSizeX()-1)+contAllowed.getSquareSize()) &&
			y>=contAllowed.getDrawingPositionY(0) &&
			y<=(contAllowed.getDrawingPositionY(contAllowed.getSizeY()-1)+contAllowed.getSquareSize())) {
				
			cont = contAllowed;
			selectedAllowed = true;
			blocks = blocksAllowed;
		}else if(x>=contNotAllowed.getDrawingPositionX(0) &&
			x<=(contNotAllowed.getDrawingPositionX(contNotAllowed.getSizeX()-1)+contNotAllowed.getSquareSize()) &&
			y>=contNotAllowed.getDrawingPositionY(0) &&
			y<=(contNotAllowed.getDrawingPositionY(contNotAllowed.getSizeY()-1)+contNotAllowed.getSquareSize())) {
				
			cont = contNotAllowed;
			selectedAllowed = false;
			blocks = blocksNotAllowed;
		}else {
			return;
		}
		
		int blockPosX = (x-cont.getOffsetX())/blockSize;
		int blockPosY = (y-cont.getOffsetY())/blockSize;
		selPosX=blockPosX;
		selPosY=blockPosY;
		selectedBlock=null;
		for (int i=0; i<blocks.length; i++) {
			if(blockPosX==blocks[i].getPosX() && blockPosY==blocks[i].getPosY()) {
				selectedBlock = blocks[i];
				break;
			}
	    }
	}

	/**
	 * Called when a button has been clicked, checks which
	 * button it was and performs the appropriate action
	 */
	public void actionPerformed(ActionEvent e)
	{
		if(buttons!=null) {
			for(int i=0;i<buttons.length;i++) {
				if(e.getSource()==buttons[i].getComponent()) {
					switch(i) {
						case 0:
							saveOptions();
							break;
						case 1:
							exitOptions();
							break;
						case 2:
							removeSelected();
							break;
						case 3:
							addSelected();
							break;
						default:
							break;
					}
				}
			}
		}
	}
	
	/**
	 * Saves the options the user has selected to disk
	 */
	protected void saveOptions()
	{
		levelFactoryAllowed.saveAllowedBlocks(blocksAllowed);
		
		int val=0;
		try {
			String str = textFields[0].getText();
			if(str!=null) {
				val = Integer.valueOf(str).intValue();
				levelFactoryAllowed.saveNumberOfEmptyBlocks(val);
			}
			
	    }
	    catch (Exception ex) {
	    }
		try {
			String str = textFields[1].getText();
			if(str!=null) {
				val = Integer.valueOf(str).intValue();
				levelFactoryAllowed.saveNumberOfStartBlocks(val);
			}
			
	    }
	    catch (Exception ex) {
	    }
		try {
			String str = textFields[2].getText();
			if(str!=null) {
				val = Integer.valueOf(str).intValue();
				levelFactoryAllowed.saveTimeUntilWater(val);
			}
			
	    }
	    catch (Exception ex) {
	    }
		try {
			String str = textFields[3].getText();
			if(str!=null) {
				val = Integer.valueOf(str).intValue();
				levelFactoryAllowed.saveWaterSpeed(val);
			}
			
	    }
	    catch (Exception ex) {
	    }
		try {
			String str = textFields[4].getText();
			if(str!=null) {
				val = Integer.valueOf(str).intValue();
				levelFactoryAllowed.saveLeftToFill(val);
			}
			
	    }
	    catch (Exception ex) {
	    }
	}

	/**
	 * Remove selected block from the blocks allowed
	 */
	protected void removeSelected()
	{
		if(selectedAllowed && selectedBlock!=null) {
			blocksAllowed = removeBlock(blocksAllowed,selectedBlock);
			blocksNotAllowed = addBlock(contNotAllowed,blocksNotAllowed,selectedBlock);
		}
	}

	/**
	 * Add selected block to the blocks allowed
	 */
	protected void addSelected()
	{
		if(!selectedAllowed && selectedBlock!=null) {
			blocksAllowed = addBlock(contAllowed,blocksAllowed,selectedBlock);
			blocksNotAllowed = removeBlock(blocksNotAllowed,selectedBlock);
		}
	}
	
	/**
	 * Add block to the specified container
	 * @param cont Block container that the blocks should be added to
	 * @param blocks Array of blocks that the blocks should be added to
	 * @param block Blocks that should be added
	 * @return Updated copy of the array in the blocks parameter
	 */
	protected PipeBlock[] addBlock(BlockContainerInterface cont, PipeBlock blocks[], PipeBlock block)
	{
		boolean bAdded = false;
		for(int x=0;!bAdded && x<cont.getSizeX();x++) {
			for(int y=0;!bAdded && y<cont.getSizeY();y++) {
				boolean bFound = false;
				for (int i=0; i<blocks.length; i++) {
					if(x==blocks[i].getPosX() && y==blocks[i].getPosY()) {
						bFound = true;
					}
			    }
			    if(!bFound) {
			    	bAdded = true;
			    	block.init(cont,x,y);	
			    }
			}
		}
		if(bAdded) {
			PipeBlock[] ret = new PipeBlock[blocks.length+1];
			for (int i=0; i<blocks.length; i++) {
				ret[i] = blocks[i];
		    }
		    ret[blocks.length] = block;
		    return ret;
		}else {
			return blocks;
		}
	}

	/**
	 * Remove block from the specified container
	 * @param blocks Array of blocks that the blocks should be removed from
	 * @param block Blocks that should be added
	 * @return Updated copy of the array in the blocks parameter
	 */
	protected PipeBlock[] removeBlock(PipeBlock blocks[], PipeBlock block)
	{
		boolean bRemoved = false;
		for (int i=0; i<blocks.length; i++) {
			if(blocks[i]==block) {
				blocks[i]=null;
				bRemoved = true;
			}
	    }
	    
	    if(bRemoved) {
	    	PipeBlock[] ret = new PipeBlock[blocks.length-1];
	    	int j=0;
	    	for (int i=0; i<blocks.length; i++) {
	    		if(blocks[i]!=null) {
	    			ret[j++]=blocks[i];
	    		}
	    		
		    }
		    return ret;
	    }else {
	    	return blocks;
	    }
	}

    public void setCheatmode(boolean enable) {
        // Do nothing
    }

    public void update() {
        // Do nothing
    }
}