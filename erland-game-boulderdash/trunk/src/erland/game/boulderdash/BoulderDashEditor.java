package erland.game.boulderdash;
import erland.util.*;
import erland.game.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;

/**
 * Main editor class that implements the level editor
 */
class BoulderDashEditor 
	implements ActionListener
{
	/** Horisontal drawing offset, nothing should be drawn to the left of this position */
	protected int offsetX;
	/** Vertical drawing offset, nothing should be drawn above this position */
	protected int offsetY;
	/** Current level edited */
	protected int level;
	/** Parameter storage which is used to save game data and highscores */
	protected ParameterValueStorageInterface cookies;
	/** Indicates if cheatmode is active or not */
	protected boolean cheatMode;
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
	/** Array with up/down arrows */
	protected ImageObject[] buttonObjects;
	/** Matrix with all the blocks on the game area */
	protected Block[][] blocks;
	/** Matrix with all the blocks on the game area */
	protected Block[][] selectBlocks;
	/** The selected block which is about to be pasted into the main level area */
	protected Block selected;
	/** Block container for the game area */
	protected BlockContainerData cont;
	/** Block container for the game area */
	protected BlockContainerData contVisible;
	/** Block container for the game area */
	protected BlockContainerData contSelect;
	/** Level factory object which creates the blocks for all levels */
	protected LevelFactory levelFactory;
	/** Level factory object which creates the blocks for all levels */
	protected LevelFactory levelFactorySelect;
	/** Image handler object */
	ImageHandlerInterface images;
	/** X scrolling position */
	protected int scrollX;
	/** Y scrolling position */
	protected int scrollY;
	/** Indicates if Exit button has been pressed */
	protected boolean bExit;
		
	class ChangeableBlockContainerData extends BlockContainerData
	{
		protected int offsetX;
		protected int offsetY;
		public ChangeableBlockContainerData(int offsetX, int offsetY, int sizeX, int sizeY, int squareSize)
		{
			super(offsetX,offsetY,sizeX,sizeY,squareSize);
			this.offsetX = offsetX;
			this.offsetY = offsetY;
		}
		public int getOffsetX()
		{
			return offsetX;
		}
		public int getOffsetY()
		{
			return offsetY;
		}
		public void setOffsetX(int offsetX)
		{
			this.offsetX = offsetX;
		}
		public void setOffsetY(int offsetY)
		{
			this.offsetY = offsetY;
		}
	}
	/**
	 * Creates a new instance of the main level editor class
	 * @param container Container object which all Button's should be added to
	 * @param cookies Parameter storage object which should be used to access stored game data and highscores
	 * @param offsetX Horisontal drawing offset, nothing should be drawn to the left of this position
	 * @param offsetY Vertical drawing offset, nothing should be drawn above this position
	 */
	public BoulderDashEditor(java.awt.Container container, ParameterValueStorageInterface cookies, ImageHandlerInterface images, int offsetX, int offsetY)
	{
		this.container = container;
		this.offsetX = offsetX;
		this.offsetY = offsetY;
		this.cookies = cookies;
		this.images = images;
		cheatMode = false;
		cont = new ChangeableBlockContainerData(offsetX, offsetY, 20,20,20);
		contVisible = new BlockContainerData(offsetX, offsetY, 20,20,20);
		contSelect = new BlockContainerData(contVisible.getDrawingPositionX(contVisible.getSizeX())+20, 
											contVisible.getDrawingPositionY(0)+40, 
											2,5,20);
		levelFactory = new LevelFactory((BoulderDashContainerInterface)null,cookies,images,cont);
		levelFactorySelect = new LevelFactory((BoulderDashContainerInterface)null,cookies,images,contSelect);

		int rightOffsetX = contSelect.getDrawingPositionX(0);
		int rightOffsetY = contSelect.getDrawingPositionY(contSelect.getSizeY())+10;
		buttons = new Button[7];
		buttons[0] = new Button("Clear This");
		buttons[0].setBounds(rightOffsetX,rightOffsetY,73,25);
		rightOffsetY+=30;
		buttons[1] = new Button("Delete");
		buttons[1].setBounds(rightOffsetX,rightOffsetY,73,25);
		rightOffsetY+=30;
		buttons[2] = new Button("New");
		buttons[2].setBounds(rightOffsetX,rightOffsetY,73,25);
		rightOffsetY+=30;
		buttons[3] = new Button("Get Default");
		buttons[3].setBounds(rightOffsetX,rightOffsetY,73,25);
		rightOffsetY+=30;
		buttons[4] = new Button("Save This");
		buttons[4].setBounds(rightOffsetX,rightOffsetY,73,25);
		rightOffsetY+=30;
		buttons[5] = new Button("Save All");
		buttons[5].setBounds(rightOffsetX,rightOffsetY,73,25);
		rightOffsetY+=30;
		buttons[6] = new Button("Exit");
		buttons[6].setBounds(rightOffsetX,rightOffsetY,73,25);
		if(buttons!=null) {
			for(int i=0;i<buttons.length;i++) {
				buttons[i].addActionListener(this);
				container.add(buttons[i]);
			}
		}
		buttonObjects = new ImageObject[2];
		rightOffsetX = contSelect.getDrawingPositionX(contSelect.getSizeX())+10;
		rightOffsetY = offsetY-10;
		buttonObjects[0] = new ImageObject(images.getImage("button_arrowup.gif"),offsetX,offsetY,rightOffsetX,rightOffsetY,14,14,true,Color.lightGray);
		rightOffsetY+=17;
		buttonObjects[1] = new ImageObject(images.getImage("button_arrowdown.gif"),offsetX,offsetY,rightOffsetX,rightOffsetY,14,14,true,Color.lightGray);

		selectBlocks = levelFactorySelect.getAllBlocks();
		level=1;
		initLevel(level);
	}
	

	/**
	 * Increase the level currently edited
	 */	
	void increaseLevel()
	{
		levelFactory.storeLevel(level,blocks);
		if(level<levelFactory.getLastLevel()) {
			level++;
		}else {
			level = levelFactory.getLastLevel();
		}
		initLevel(level);
	} 
	
	/**
	 * Decrease the level currently edited
	 */	
	void decreaseLevel()
	{
		levelFactory.storeLevel(level,blocks);
		if(level>1) {
			level--;
		}else {
			level = 1;
		}
		initLevel(level);
	} 

	/**
	 * Initiate all data for the specified level
	 * @param level The level to initialize data for
	 */
	void initLevel(int level) 
	{
		blocks = levelFactory.getLevel(level,null);
	}
	/**
	 * Exits the editor
	 */
	protected void exit()
	{
		bExit = true;
		if(buttons!=null) {
			for(int i=0;i<buttons.length;i++) {
				container.remove(buttons[i]);
			}
		}
	}

	/**
	 * Indicates if the Exit button has been pressed
	 * @return true/false (Pressed/Not pressed)
	 */
	protected boolean isExit()
	{
		return bExit;
	}
	
	/**
	 * Draw all the game graphics
	 * @param g Graphics object to draw on
	 */
	public void draw(Graphics g)
	{
		int gameSizeX = contVisible.getSizeX()*contVisible.getSquareSize();
		int gameSizeY = contVisible.getSizeY()*contVisible.getSquareSize();
		if((++fpsShow)>25) {
			fpsShow=0;
			long cur = System.currentTimeMillis();
			fps = 25000/(cur-frameTime);
			frameTime = cur;
		}
		g.setColor(Color.blue);
		g.drawRect(offsetX, offsetY, gameSizeX,gameSizeY);
		g.drawRect(contSelect.getDrawingPositionX(0), contSelect.getDrawingPositionY(0), 
			contSelect.getDrawingSizeX(),contSelect.getDrawingSizeY());
		
		if(blocks!=null) {
			cont.setOffsetX(contVisible.getOffsetX()-scrollX*contVisible.getSquareSize());
			cont.setOffsetY(contVisible.getOffsetY()-scrollY*contVisible.getSquareSize());
			for(int x=0;x<blocks.length;x++) {
				for(int y=0;y<blocks[0].length;y++) {
					if(blocks[x][y]!=null) {
						if(blocks[x][y].getPosX()>=scrollX && blocks[x][y].getPosX()<(scrollX+contVisible.getSizeX()) &&
							blocks[x][y].getPosY()>=scrollY && blocks[x][y].getPosY()<(scrollY+contVisible.getSizeY())) {
							
							blocks[x][y].draw(g);
						}
					}
				}
			}
		}
		if(selectBlocks!=null) {
			for (int x=0;x<selectBlocks.length; x++) {
				for (int y=0;y<selectBlocks[0].length; y++) {
					if(selectBlocks[x][y]!=null) {
						selectBlocks[x][y].draw(g);	
					}
				}
		    }
		}
		if(selected!=null) {
			g.setColor(Color.white);
			g.drawRect(contSelect.getDrawingPositionX(selected.getPosX()),
				contSelect.getDrawingPositionY(selected.getPosY()),
				contSelect.getSquareSize(),contSelect.getSquareSize());
		}
		
		// Draw buttons
		for(int i=0;i<buttonObjects.length;i++) {
			buttonObjects[i].draw(g);
		}

		g.setColor(Color.white);
		int rightColumnX = offsetX+gameSizeX+20;
		int rightColumnY = offsetY+20;
		g.drawString("Level: "+String.valueOf(level),rightColumnX, rightColumnY);
		rightColumnY+=20;
		if(cheatMode) {
			g.drawString("*** Cheatmode *** FPS=" + fps,rightColumnX,rightColumnY);
		}
		rightColumnY+=20;
		g.setColor(Color.red);
		g.drawString("by Erland Isaksson",rightColumnX,offsetY+gameSizeY);
	}




	/**
	 * Called when the left mouse button has been clicked (pressed + released)
	 * @param x X position of the mouse pointer
	 * @param y Y position of the mouse pointer
	 */
	public void handleLeftMouseClicked(int x, int y) {
		// Check buttons
		for(int i=0;i<buttonObjects.length;i++) 
		{
			if(checkCollision(buttonObjects[i],x-offsetX,y-offsetY)) {
				switch(i) {
					case 0:
						increaseLevel();
						break;
					case 1:
						decreaseLevel();
						break;
					default:
						break;
				}
			}
		}
		if(x>contVisible.getDrawingPositionX(0) && x<contVisible.getDrawingPositionX(contVisible.getSizeX())) {
			if(y>contVisible.getDrawingPositionY(0) && y<contVisible.getDrawingPositionY(contVisible.getSizeY())) {
				int posX = (x-contVisible.getOffsetX())/contVisible.getSquareSize();
				int posY = (y-contVisible.getOffsetY())/contVisible.getSquareSize();
				if(blocks[posX][posY]!=null) {
					blocks[posX][posY]=null;
				}else if(selected!=null) {
					try {
						blocks[posX][posY]=(Block)selected.clone();
						blocks[posX][posY].init(null,images,cont,posX,posY);
					}catch(CloneNotSupportedException e) {
						// Should never happend
					}
				}
			}
		}
		if(x>contSelect.getDrawingPositionX(0) && x<contSelect.getDrawingPositionX(contSelect.getSizeX())) {
			if(y>contSelect.getDrawingPositionY(0) && y<contSelect.getDrawingPositionY(contSelect.getSizeY())) {
				int posX = (x-contSelect.getOffsetX())/contSelect.getSquareSize();
				int posY = (y-contSelect.getOffsetY())/contSelect.getSquareSize();
				selected = null;
				if(selectBlocks[posX][posY]!=null) {
					selected = selectBlocks[posX][posY];
				}
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
		// Check buttons
		for(int i=0;i<buttonObjects.length;i++) 
		{
			buttonObjects[i].setRaised(true);
			if(checkCollision(buttonObjects[i],x-offsetX,y-offsetY)) {
				buttonObjects[i].setRaised(false);
			}
		}
	}
	/**
	 * Called when the left mouse button has been released
	 * @param x X position of the mouse pointer
	 * @param y Y position of the mouse pointer
	 */
	public void handleLeftMouseReleased(int x, int y)
	{
		// Check buttons
		for(int i=0;i<buttonObjects.length;i++) 
		{
			buttonObjects[i].setRaised(true);
		}
	}

	/**
	 * Called when the mouse has been dragged with the left 
	 * mouse button has been pressed down
	 * @param x X position of the mouse pointer
	 * @param y Y position of the mouse pointer
	 */
	public void handleLeftMouseDragged(int x, int y)
	{
		if(x>contVisible.getDrawingPositionX(0) && x<contVisible.getDrawingPositionX(contVisible.getSizeX())) {
			if(y>contVisible.getDrawingPositionY(0) && y<contVisible.getDrawingPositionY(contVisible.getSizeY())) {
				int posX = (x-contVisible.getOffsetX())/contVisible.getSquareSize();
				int posY = (y-contVisible.getOffsetY())/contVisible.getSquareSize();
				if(selected!=null) {
					try {
						blocks[posX][posY]=(Block)selected.clone();
						blocks[posX][posY].init(null,images,cont,posX,posY);
					}catch(CloneNotSupportedException e) {
						// Should never happend
					}
				}else {
					blocks[posX][posY]=null;
				}
			}
		}
	}

	/**
	 * Activate or deactivate cheatmode
	 * @param cheat true/false (Activate/Deactivate)
	 */
	public void setCheatMode(boolean cheat)
	{
		cheatMode=cheat;
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
							clearLevel();
							break;
						case 1:
							deleteLevel();
							break;
						case 2:
							newLevel();
							break;
						case 3:
							getDefaultLevel();
							break;
						case 4:
							saveThisLevel();
							break;
						case 5:
							saveAllLevels();
							break;
						case 6:
							exit();
							break;
						default:
							break;
					}
				}
			}
		}
	}

	/**
	 * Remove all blocks from the main level editor area
	 */
	protected void clearLevel()
	{
		blocks = new Block[20][20];
		for (int x=0; x<blocks.length; x++) {
			for (int y=0; y<blocks[0].length; y++) {
				blocks[x][y]=null;
		    }
	    }
	}

	/**
	 * Deletes the currently selected level
	 */
	protected void deleteLevel()
	{
		levelFactory.deleteLevel(level);
		if(level>levelFactory.getLastLevel()) {
			level = levelFactory.getLastLevel();			
		}
		blocks = levelFactory.getLevel(level,(Player)null);
	}
	/**
	 * Inserts a new level after the currently active level
	 */
	protected void newLevel()
	{
		levelFactory.newLevel(level);
		increaseLevel();
	}
	
	/**
	 * Get the hardcoded level data for the currently selected level
	 */
	protected void getDefaultLevel()
	{
		blocks = levelFactory.getDefaultLevel(level,(Player)null);
	}
	
	/**
	 * Save the currently active level to disk
	 */
	protected void saveThisLevel()
	{
		levelFactory.storeLevel(level,blocks);
		levelFactory.saveLevel(level);
	}

	/**
	 * Save all levels to disk
	 */
	protected void saveAllLevels()
	{
		levelFactory.storeLevel(level,blocks);
		levelFactory.saveAll();
	}
	
	/**
	 * Checks if the specified coordinate is inside the specified rectangle
	 * @param rc Rectangle to check if the coordinate is within
	 * @param x x coordinate to check
	 * @param y y coordinate to check
	 * @return true/false (Inside/Not inside)
	 */
	protected boolean checkCollision(CollisionRect rc, int x, int y)
	{
		if(rc.left()<=x && rc.right()>=x) {			
			if(rc.top()<=y && rc.bottom()>=y) {
				return true;
			}
		}
		return false;
	}
}
