package erland.game.boulderdash;
import erland.util.*;
import erland.game.*;
import erland.game.component.EButton;

import java.awt.*;
import java.awt.event.*;

/**
 * Main editor class that implements the level editor
 */
class BoulderDashEditor 
	implements GamePanelInterface, ActionListener
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
	/** The last calculated FPS in cheatmode */
	protected FpsCounter fps;
	/** Array with all the buttons */
	protected EButton buttons[];
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
	/** X scrolling position */
	protected int scrollX;
	/** Y scrolling position */
	protected int scrollY;
	/** Indicates if Exit button has been pressed */
	protected boolean bExit;
    private GameEnvironmentInterface environment;
    private MouseListener mouseListener;
    private MouseMotionListener mouseMotionListener;
    /** Matrix with indications about which position that needs to be redrawn */
    protected int redraw[][];
    /** Indicates if the level has been drawn at least once, 0 if it has been drawn */
    protected int drawnOnce;

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
	 * @param offsetX Horisontal drawing offset, nothing should be drawn to the left of this position
	 * @param offsetY Vertical drawing offset, nothing should be drawn above this position
	 */
	public BoulderDashEditor(int offsetX, int offsetY)
	{
		this.offsetX = offsetX;
		this.offsetY = offsetY;
	}

    public void init(GameEnvironmentInterface environ) {
        this.environment = environ;
        bExit = false;
		cheatMode = false;
		cont = new ChangeableBlockContainerData(offsetX+1, offsetY+1, 20,20,20);
		contVisible = new BlockContainerData(offsetX+1, offsetY+1, 20,20,20);
		contSelect = new BlockContainerData(contVisible.getDrawingPositionX(contVisible.getSizeX())+20,
											contVisible.getDrawingPositionY(0)+40,
											2,5,20);
		levelFactory = new LevelFactory(environment,null,cont);
		levelFactorySelect = new LevelFactory(environment,null,contSelect);

		int rightOffsetX = contSelect.getDrawingPositionX(0);
		int rightOffsetY = contSelect.getDrawingPositionY(contSelect.getSizeY())+10;
		buttons = new EButton[7];
		buttons[0] = EButton.create("Clear This");
		buttons[0].getComponent().setBounds(rightOffsetX,rightOffsetY,73,25);
		rightOffsetY+=30;
		buttons[1] = EButton.create("Delete");
		buttons[1].getComponent().setBounds(rightOffsetX,rightOffsetY,73,25);
		rightOffsetY+=30;
		buttons[2] = EButton.create("New");
		buttons[2].getComponent().setBounds(rightOffsetX,rightOffsetY,73,25);
		rightOffsetY+=30;
		buttons[3] = EButton.create("Get Default");
		buttons[3].getComponent().setBounds(rightOffsetX,rightOffsetY,73,25);
		rightOffsetY+=30;
		buttons[4] = EButton.create("Save This");
		buttons[4].getComponent().setBounds(rightOffsetX,rightOffsetY,73,25);
		rightOffsetY+=30;
		buttons[5] = EButton.create("Save All");
		buttons[5].getComponent().setBounds(rightOffsetX,rightOffsetY,73,25);
		rightOffsetY+=30;
		buttons[6] = EButton.create("Exit");
		buttons[6].getComponent().setBounds(rightOffsetX,rightOffsetY,73,25);
		if(buttons!=null) {
			for(int i=0;i<buttons.length;i++) {
				buttons[i].addActionListener(this);
				environment.getScreenHandler().add(buttons[i].getComponent());
			}
		}
		buttonObjects = new ImageObject[2];
		rightOffsetX = contSelect.getDrawingPositionX(contSelect.getSizeX())+10;
		rightOffsetY = offsetY-10;
		buttonObjects[0] = new ImageObject(environment.getImageHandler().getImage("button_arrowup.gif"),offsetX,offsetY,rightOffsetX,rightOffsetY,14,14,true,Color.lightGray);
		rightOffsetY+=17;
		buttonObjects[1] = new ImageObject(environment.getImageHandler().getImage("button_arrowdown.gif"),offsetX,offsetY,rightOffsetX,rightOffsetY,14,14,true,Color.lightGray);

		selectBlocks = levelFactorySelect.getAllBlocks();
		level=1;
		initLevel(level);
        mouseListener = new MouseAdapter() {
            public void mousePressed(MouseEvent e)
            {
                if((e.getModifiers() & e.BUTTON1_MASK)!=0) {
                    handleLeftMousePressed(environment.getScreenHandler().getScreenX(e.getX()),environment.getScreenHandler().getScreenY(e.getY()));
                }
            }
            public void mouseReleased(MouseEvent e)
            {
                if((e.getModifiers() & e.BUTTON1_MASK)!=0) {
                    handleLeftMouseReleased(environment.getScreenHandler().getScreenX(e.getX()),environment.getScreenHandler().getScreenY(e.getY()));
                }
            }
            public void mouseClicked(MouseEvent e)
            {
                if((e.getModifiers() & e.BUTTON1_MASK)!=0) {
                    handleLeftMouseClicked(environment.getScreenHandler().getScreenX(e.getX()),environment.getScreenHandler().getScreenY(e.getY()));
                }
            }
        };
        environment.getScreenHandler().getContainer().addMouseListener(mouseListener);
        mouseMotionListener = new MouseMotionAdapter() {
            public void mouseDragged(MouseEvent e)
            {
                if((e.getModifiers() & e.BUTTON1_MASK)!=0) {
                    handleLeftMouseDragged(environment.getScreenHandler().getScreenX(e.getX()),environment.getScreenHandler().getScreenY(e.getY()));
                }
            }
        };
        environment.getScreenHandler().getContainer().addMouseMotionListener(mouseMotionListener);
        environment.getScreenHandler().getContainer().requestFocus();
        environment.getScreenHandler().getContainer().setBackground(Color.black);
        fps = new FpsCounter(60);
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
        redraw = new int[blocks.length][blocks[0].length];
        drawnOnce = 2;
        for(int x=0;x<blocks.length;x++) {
            for(int y=0;y<blocks[0].length;y++) {
                redraw[x][y] = 2;
            }
        }
	}
	/**
	 * Exits the editor
	 */
	protected void exitEditor()
	{
		bExit = true;
	}

    public void exit() {
        if(buttons!=null) {
            for(int i=0;i<buttons.length;i++) {
                environment.getScreenHandler().remove(buttons[i].getComponent());
            }
        }
        environment.getScreenHandler().getContainer().removeMouseListener(mouseListener);
        environment.getScreenHandler().getContainer().removeMouseMotionListener(mouseMotionListener);
    }

	/**
	 * Indicates if the Exit button has been pressed
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
		int gameSizeX = contVisible.getSizeX()*contVisible.getSquareSize();
		int gameSizeY = contVisible.getSizeY()*contVisible.getSquareSize();
        fps.update();
		g.setColor(Color.blue);
		g.drawRect(offsetX, offsetY, gameSizeX+1,gameSizeY+1);
        g.clearRect(offsetX+gameSizeX+2,0,environment.getScreenHandler().getWidth()-(offsetX+gameSizeX+2),environment.getScreenHandler().getHeight());
		g.drawRect(contSelect.getDrawingPositionX(0), contSelect.getDrawingPositionY(0),
			contSelect.getDrawingSizeX(),contSelect.getDrawingSizeY());
		
		if(blocks!=null) {
			cont.setOffsetX(contVisible.getOffsetX()-scrollX*contVisible.getSquareSize());
			cont.setOffsetY(contVisible.getOffsetY()-scrollY*contVisible.getSquareSize());

            for(int x=0;x<blocks.length;x++) {
                for(int y=0;y<blocks[0].length;y++) {
                    if(blocks[x][y]==null && (drawnOnce>0 || redraw[x][y]>0)) {
                        g.clearRect(contVisible.getDrawingPositionX(x),
                            contVisible.getDrawingPositionY(y),
                            contVisible.getSquareSize(),
                            contVisible.getSquareSize());
                    }else if(redraw[x][y]>0) {
                        blocks[x][y].drawClear(g);
                    }
                }
            }
			for(int x=0;x<blocks.length;x++) {
				for(int y=0;y<blocks[0].length;y++) {
                    if(blocks[x][y]!=null && (drawnOnce>0 || redraw[x][y]>0)) {
                        if(blocks[x][y].getPosX()>=scrollX && blocks[x][y].getPosX()<(scrollX+contVisible.getSizeX()) &&
							blocks[x][y].getPosY()>=scrollY && blocks[x][y].getPosY()<(scrollY+contVisible.getSizeY())) {
							
							blocks[x][y].draw(g);
						}
					}
				}
			}
            for (int x=0; x<blocks.length; x++) {
                for (int y=0; y<blocks[0].length; y++) {
                    if(redraw[x][y]>0) {
                        redraw[x][y]--;
                    }
                }
            }
			if(drawnOnce>0) {
                drawnOnce--;
            }
		}else {
            g.clearRect(offsetX+1, offsetY+1, gameSizeX,gameSizeY);
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
			g.drawString("*** Cheatmode *** FPS=",rightColumnX,rightColumnY);
            fps.draw(g,g.getColor(),rightColumnX+150,rightColumnY);
		}
		rightColumnY+=20;
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
						blocks[posX][posY].init(environment,null,cont,posX,posY);
                        redraw[posX][posY] = 2;
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
						blocks[posX][posY].init(environment,null,cont,posX,posY);
                        redraw[posX][posY] = 2;
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
	public void setCheatmode(boolean cheat)
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
				if(e.getSource()==buttons[i].getComponent()) {
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
							exitEditor();
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
                redraw[x][y]=2;
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
		blocks = levelFactory.getLevel(level,null);
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
		blocks = levelFactory.getDefaultLevel(level,null);
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

    public void update() {
        // Do nothing
    }
}
