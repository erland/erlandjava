package erland.game.pipes;
import java.awt.*;
import java.awt.event.*;
import erland.game.*;
import erland.util.*;

/**
 * This is the class that implements the options screen
 */
class PipesOptions
	implements ActionListener
{
	/** Horisontal drawing offset, nothing should be drawn to the left of this position */
	protected int offsetX;
	/** Vertical drawing offset, nothing should be drawn above this position */
	protected int offsetY;
	/** Parameter storage which is used to save game data and highscores */
	protected ParameterValueStorageInterface cookies;
	/** Counter that contains the time since last FPS calculation in cheatmode */
	protected long frameTime=0;
	/** Counter that contains the number of frames drawn since the last FPS calculation in cheatmode */
	protected int fpsShow=0;
	/** The last calculated FPS in cheatmode */
	protected long fps=0;
	/** Container object which all Buttons should be added to */
	protected Container container;
	/** Array with all the buttons */
	protected Button buttons[];
	/** Blocks allowed */
	protected PipeBlock blocksAllowed[];
	/** Blocks not allowed */
	protected PipeBlock blocksNotAllowed[];
	/** Image handler object */
	protected ImageHandlerInterface images;
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
	/**
	 * Creates a new instance of the main game class
	 * @param container Container object which all Button's should be added to
	 * @param cookies Parameter storage object which should be used to access stored game data and highscores
	 * @param offsetX Horisontal drawing offset, nothing should be drawn to the left of this position
	 * @param offsetY Vertical drawing offset, nothing should be drawn above this position
	 */
	public PipesOptions(java.awt.Container container, ParameterValueStorageInterface cookies, ImageHandlerInterface images, int offsetX, int offsetY)
	{
		this.container = container;
		this.offsetX = offsetX;
		this.offsetY = offsetY;
		this.cookies = cookies;
		this.images = images;
		this.contAllowed = new BlockContainerData(offsetX+1, offsetY+20,2,10,blockSize);
		this.contNotAllowed = new BlockContainerData(offsetX+1+5*blockSize, offsetY+20,2,10,blockSize);
		levelFactoryAllowed = new LevelFactory(cookies,contAllowed,images);
		levelFactoryNotAllowed = new LevelFactory(cookies,contNotAllowed,images);

		buttons = new Button[4];
		buttons[0] = new Button("Save");
		buttons[0].setBounds(contNotAllowed.getDrawingPositionX(0)+contNotAllowed.getSizeX()*blockSize+10,offsetY + 20,73,25);
		buttons[1] = new Button("Exit");
		buttons[1].setBounds(contNotAllowed.getDrawingPositionX(0)+contNotAllowed.getSizeX()*blockSize+10,offsetY + 50,73,25);
		buttons[2] = new Button("-->");
		buttons[2].setBounds(offsetX + contAllowed.getSizeX()*blockSize+10,offsetY + 50,73,25);
		buttons[3] = new Button("<--");
		buttons[3].setBounds(offsetX + contAllowed.getSizeX()*blockSize+10,offsetY + 80,73,25);
		if(buttons!=null) {
			for(int i=0;i<buttons.length;i++) {
				buttons[i].addActionListener(this);
				container.add(buttons[i]);
			}
		}
		textFields = new TextField[2];
		textFields[0] = new TextField(String.valueOf(levelFactoryAllowed.getNumberOfEmptyBlocks()),2);
		textFields[0].setBounds(150,contAllowed.getDrawingPositionY(contAllowed.getSizeY())+3,30,18);
		textFields[0].setBackground(Color.white);
		textFields[1] = new TextField(String.valueOf(levelFactoryAllowed.getNumberOfStartBlocks()),2);
		textFields[1].setBounds(150,contAllowed.getDrawingPositionY(contAllowed.getSizeY())+28,30,18);
		textFields[1].setBackground(Color.white);
		for (int i=0; i<textFields.length; i++) {
	    	container.add(textFields[i]);
	    }
	
		selectedBlock = null;
		blocksAllowed = levelFactoryAllowed.getAllowedBlocks();
		blocksNotAllowed = levelFactoryNotAllowed.getNotAllowedBlocks();
		bExit = false;
	}
	

	/**
	 * Exits the game
	 */
	protected void exit()
	{
		if(buttons!=null) {
			for(int i=0;i<buttons.length;i++) {
				container.remove(buttons[i]);
			}
		}
		if(textFields!=null) {
			for(int i=0;i<textFields.length;i++) {
				container.remove(textFields[i]);
			}
		}
		bExit = true;
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
	 * @param g Graphics object to draw on
	 */
	public void draw(Graphics g)
	{
		int gameSizeX = 8*blockSize+2;
		int gameSizeY = contAllowed.getSizeY()*blockSize+2;
		if((++fpsShow)>25) {
			fpsShow=0;
			long cur = System.currentTimeMillis();
			fps = 25000/(cur-frameTime);
			frameTime = cur;
		}
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
		g.drawString("Number of empty blocks:",offsetX,contAllowed.getDrawingPositionY(contAllowed.getSizeY())+15);
		g.drawString("Number of start blocks:",offsetX,contAllowed.getDrawingPositionY(contAllowed.getSizeY())+40);
		int rightColumnX = offsetX+gameSizeX+20;
		int rightColumnY = offsetY+20;
		g.setColor(Color.red);
		g.drawString("by Erland Isaksson",rightColumnX,offsetY+gameSizeY);
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
	 * Called when the left mouse button has been pressed down
	 * @param x X position of the mouse pointer
	 * @param y Y position of the mouse pointer
	 */
	public void handleLeftMousePressed(int x, int y)
	{
		
	}
	/**
	 * Called when the left mouse button has been released
	 * @param x X position of the mouse pointer
	 * @param y Y position of the mouse pointer
	 */
	public void handleLeftMouseReleased(int x, int y)
	{
	}

	/**
	 * Called when the mouse has been dragged with the left 
	 * mouse button has been pressed down
	 * @param x X position of the mouse pointer
	 * @param y Y position of the mouse pointer
	 */
	public void handleLeftMouseDragged(int x, int y)
	{
	}

	/**
	 * Called when a button has been clicked, checks which
	 * button it was and performs the appropriate action
	 */
	public void actionPerformed(ActionEvent e)
	{
		if(buttons!=null) {
			for(int i=0;i<buttons.length;i++) {
				if(e.getSource()==buttons[i]) {
					switch(i) {
						case 0:
							saveOptions();
							break;
						case 1:
							exit();
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
}