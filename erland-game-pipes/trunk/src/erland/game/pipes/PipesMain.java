package erland.game.pipes;
import erland.util.*;
import erland.game.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;

/**
 * Main game class that controls the game logic and and screen redrawing
 */
class PipesMain 
	implements ActionListener, PipeBlockContainerInterface
{
	/** Horisontal drawing offset, nothing should be drawn to the left of this position */
	protected int offsetX;
	/** Vertical drawing offset, nothing should be drawn above this position */
	protected int offsetY;
	/** Indicates if the game is finished, either completed or GAME OVER */
	protected boolean bEnd;
	/** Counter that handles the blinking text when waiting for user input */
	protected int blinkCounter;
	/** Blinking speed of the blinking text when waiting for user input */
	protected final int BLINK_SPEED=20;
	/** Indicates if the game is currently running or if it is waiting for the user to press space */
	protected boolean bStarted;
	/** Last highscore saved to disk */
	protected int savedHighScore;
	/** Current highscore, may not be saved to disk */
	protected int highScore;
	/** Current score in the ongoing game */
	protected int score;
	/** Current level in the ongoing game */
	protected int level;
	/** Number of lifes left in the ongoing game */
	protected int lifes;
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
	/** Number of levels in the game */
	protected static final int MAX_LEVEL=10;
	/** Array with all the buttons */
	protected Button buttons[];
	/** List with all blocks that contains moving water */
	protected LinkedList blocksWithMovingWater;
	/** List with all blocks that shall be added to {@link #blocksWithMovingWater} */
	protected LinkedList addBlocksWithMovingWater;
	/** Blocks in the current level */
	protected PipeBlock blocks[];
	/** Image handler object */
	protected ImageHandlerInterface images;
	/** Block container interface object for main game area */
	protected BlockContainerInterface cont;
	/** Maximum water speed */
	protected static final int MAX_SPEED=50;
	/** Current water speed */
	protected int speed;
	/** Frames left until next water move */
	protected int speedCounter;
	/** Width of the game area (Number of blocks */
	protected static final int sizeX = 10;
	/** Height of the game area (Number of blocks)*/
	protected static final int sizeY = 10;
	/** Size of the blocks */
	protected static final int blockSize = 30;
	/** Currently selected block that the user wants to move */
	protected PipeBlock selectedBlock;	
	/** Currently moving block that the user has moved */
	protected PipeBlock movingBlock;	
	/** Counter that is decreased and when it reaches 0 the water will start running */
	protected int timeUntilWater;
	/** Level factory that creates all level data */
	protected LevelFactory levelFactory;
	/** Number of pipes that needs to be filled with water on this level */
	protected int leftToFill;
	
	/**
	 * Creates a new instance of the main game class
	 * @param container Container object which all Button's should be added to
	 * @param cookies Parameter storage object which should be used to access stored game data and highscores
	 * @param offsetX Horisontal drawing offset, nothing should be drawn to the left of this position
	 * @param offsetY Vertical drawing offset, nothing should be drawn above this position
	 */
	public PipesMain(java.awt.Container container, ParameterValueStorageInterface cookies, ImageHandlerInterface images, int offsetX, int offsetY)
	{
		this.container = container;
		this.offsetX = offsetX;
		this.offsetY = offsetY;
		this.cookies = cookies;
		this.images = images;
		this.cont = new BlockContainerData(offsetX+1, offsetY+1,sizeX,sizeY,blockSize);
		cheatMode = false;
		blocksWithMovingWater = new LinkedList();
		addBlocksWithMovingWater = new LinkedList();
		levelFactory = new LevelFactory(cont,images);

		// TODO: initiate all buttons
		// buttons = new Button[2];
		// buttons[0] = new Button("Button1");
		// buttons[0].setBounds(offsetX + 10,offsetY + 10,73,25);
		// buttons[1] = new Button("Button2");
		// buttons[2].setBounds(offsetX + 10,offsetY + 40,73,25);
		if(buttons!=null) {
			for(int i=0;i<buttons.length;i++) {
				buttons[i].addActionListener(this);
				container.add(buttons[i]);
			}
		}

		init();
		newLevel();
		level=0;
		try {
			if(cookies!=null) {
				highScore=Integer.valueOf(cookies.getParameter("highscore")).intValue();
			}
		}catch(NumberFormatException e) {
			highScore=0;
		}
		savedHighScore=highScore;
	}
	
	/**
	 * Initialize a new game
	 */
	protected void init()
	{
		bEnd = false;
		blinkCounter = 0;
		bStarted=false;
		level=0;
		score=0;
		lifes =3;
		blocksWithMovingWater.clear();
		addBlocksWithMovingWater.clear();
		speed = 45;
		speedCounter = 0;
		selectedBlock = null;
		movingBlock = null;
	}
	/**
	 * Does whatever neccesary when you have died, for example decreases the life counter
	 */
	protected void handleDeath()
	{
		lifes--;
		if(lifes<=0) {
			bEnd=true;
			bStarted=false;
		}else {
			bStarted=false;
		}
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
	}

	/**
	 * Goto the next level
	 * @return <code>true</code> - Success
	 * <br><code>false</code> - Game completed
	 */
	protected boolean newLevel()
	{	
		level++;
		if(level<=MAX_LEVEL) {
			if(level>1) {
				score+=level*100;
			}
			bStarted=false;
			return true;
		}else {
			return false;
		}				
	}
	
	protected void initLevel()
	{
		blocks = levelFactory.getLevel(level);
		PipeBlock startBlock = new PipeBlockStartRight(images);
		int startBlockPos=-1;
		while(startBlockPos==-1) {
			int tmp =(int)(Math.random()*sizeX*sizeY);
			if(blocks.length>tmp) {
				if(blocks[tmp].getPosX()<=sizeX/2) {
					startBlockPos=tmp;
				}
			}
		}
		startBlock.init(cont, blocks[startBlockPos].getPosX(),blocks[startBlockPos].getPosY());
		blocks[startBlockPos] = startBlock;
		addWater(blocks[startBlockPos].getPosX(), blocks[startBlockPos].getPosY(),1,1,PipePart.Direction.LEFT);
		leftToFill = 5+level*2;
		timeUntilWater = 1000-level*75;
		speed = 45+level/2;
	}
	
	/**
	 * Draw all the game graphics
	 * @param g Graphics object to draw on
	 */
	public void draw(Graphics g)
	{
		int gameSizeX = sizeX*blockSize+2;
		int gameSizeY = sizeX*blockSize+2;
		if((++fpsShow)>25) {
			fpsShow=0;
			long cur = System.currentTimeMillis();
			fps = 25000/(cur-frameTime);
			frameTime = cur;
		}
		g.setColor(Color.blue);
		g.drawRect(offsetX, offsetY, gameSizeX-1,gameSizeY-1);
		
		if(blocks!=null) {
			for (int i=0; i<blocks.length; i++) {
				blocks[i].draw(g);
		    }
		}
		
		if(selectedBlock!=null) {
			g.setColor(Color.white);
			g.drawRect(selectedBlock.getMovingDrawingPosX(),
			selectedBlock.getMovingDrawingPosY(),
			cont.getSquareSize(),cont.getSquareSize());
		}

		int rightColumnX = offsetX+gameSizeX+20;
		int rightColumnY = offsetY+20;
		if(!cheatMode && score>highScore) {
			highScore=score;
		}
		g.setColor(Color.white);
		g.drawString("HIGHSCORE:",rightColumnX, rightColumnY);
		rightColumnY+=20;
		g.drawString(String.valueOf(highScore),rightColumnX, rightColumnY);
		rightColumnY+=20;
		g.drawString("SCORE:", rightColumnX, rightColumnY);
		rightColumnY+=20;
		g.drawString(String.valueOf(score),rightColumnX, rightColumnY);
		rightColumnY+=20;
		g.drawString("Level: "+String.valueOf(level),rightColumnX, rightColumnY);
		rightColumnY+=20;
		g.drawString("Life: "+String.valueOf(lifes),rightColumnX, rightColumnY);
		rightColumnY+=20;
		g.drawString("Left to fill: "+String.valueOf(leftToFill),rightColumnX, rightColumnY);
		rightColumnY+=20;
		if(timeUntilWater>0) {
			g.drawString("Time until water: "+String.valueOf(timeUntilWater),rightColumnX, rightColumnY);
		}
		rightColumnY+=20;
		if(cheatMode) {
			g.drawString("*** Cheatmode *** FPS=" + fps,rightColumnX,rightColumnY);
			rightColumnY+=20;
		}
		rightColumnY+=20;
		if(bEnd) {
			if(highScore>savedHighScore) {
				if(cookies!=null) {
					cookies.setParameter("highscore",Integer.toString(highScore));
				}
				savedHighScore=highScore;
			}
			if(blinkCounter<BLINK_SPEED) {
				blinkCounter++;
				if(level==(MAX_LEVEL+1)) {
					g.drawString("CONGRATUALTIONS", rightColumnX, rightColumnY);
					rightColumnY+=20;
					g.drawString("You have finished", rightColumnX, rightColumnY);
					rightColumnY+=20;
					g.drawString("     the game    ", rightColumnX, rightColumnY);
					rightColumnY+=20;
				}else {
					g.drawString("***** GAME OVER *****",rightColumnX, rightColumnY);
					rightColumnY+=20;
				}				
				g.drawString("Press space for a new game",rightColumnX, rightColumnY);
				rightColumnY+=20;
			}else if(blinkCounter<(BLINK_SPEED*2)){
				blinkCounter++;
			}else {
				blinkCounter = 0;
			}
		}
		if(!bEnd && !bStarted) {
			if(blinkCounter<BLINK_SPEED) {
				blinkCounter++;
				if(level>=1) {
					g.drawString("**** LEVEL " + level + " ****",rightColumnX, rightColumnY);
					rightColumnY+=20;
					g.drawString("Press space to start", rightColumnX, rightColumnY);
					rightColumnY+=20;
				}else {
					g.drawString("Press space for a new game",rightColumnX, rightColumnY);
					rightColumnY+=20;
				}
				
			}else if(blinkCounter<(BLINK_SPEED*2)){
				blinkCounter++;
			}else {
				blinkCounter = 0;
			}
		}
		g.setColor(Color.red);
		g.drawString("by Erland Isaksson",rightColumnX,offsetY+gameSizeY);
	}

	/**
	 * Update the game logic for the next frame
	 */
	public void update()
	{
		if(!bStarted && level>0) {
			//TODO: Do something while waiting for user to press
			//      space to start playing next level
		}
		if(!bEnd && bStarted) {
			if(timeUntilWater>0) {
				// Update blocks
				for (int i=0; i<blocks.length; i++) {
					blocks[i].updateBlock();
			    }
				timeUntilWater--;
			}else {
				speedCounter++;
				if(speedCounter>(MAX_SPEED-speed)) {
					speedCounter=0;
					// Move water
					ListIterator it = blocksWithMovingWater.listIterator();
					while(it.hasNext()) {
						PipeBlock block = (PipeBlock)(it.next());
						if(block.hasMovingWater()) {
							block.moveWater(this);
						}
					}
				}
				// Update blocks
				for (int i=0; i<blocks.length; i++) {
					blocks[i].updateBlock();
			    }
	
				// Add new blocks with moving water
				ListIterator it=addBlocksWithMovingWater.listIterator();
				while(it.hasNext()) {
					PipeBlock block = (PipeBlock)(it.next());
					if(!blocksWithMovingWater.contains(block)) {
						blocksWithMovingWater.add(block);
						score+=10*level;
						if(leftToFill>0) {
							leftToFill--;
						}
					}
				}
				addBlocksWithMovingWater.clear();
				
				// Remove blocks with no longer moving water
				it=blocksWithMovingWater.listIterator();
				while(it.hasNext()) {
					PipeBlock block = (PipeBlock)(it.next());
					if(!block.hasMovingWater()) {
						it.remove();
					}
				}

				if(blocksWithMovingWater.size()<=0) {
					boolean bFinished = false;
					if(leftToFill==0) {
						if(!newLevel()) {
							bEnd=true;
							bStarted=false;
						}
					}else {
						handleDeath();
					}
				}
			}
			
			if(movingBlock!=null) {
				if(!movingBlock.isMoving()) {
					movingBlock=null;
				}
			}

		}
	}

	/**
	 * Called when space has been hit on the keyboard and
	 * performs appropriate action
	 */
	public void hitSpace()
	{
		if(bStarted) {
			// TODO: Handle space when game is running
		}else {
			start();
		}
	}

	/**
	 * Start the game
	 */
	protected void start()
	{
		if(!bStarted && level>0 && !bEnd) {
			initLevel();
			bStarted=true;
		}else {
			newGame();
			start();
		}
	}

	/**
	 * Starts a new game if no game is currently running
	 */
	protected void newGame()
	{
		if(bEnd || !bStarted) {
			init();
			newLevel();
		}
	}

	/**
	 * Called when the left mouse button has been clicked (pressed + released)
	 * @param x X position of the mouse pointer
	 * @param y Y position of the mouse pointer
	 */
	public void handleLeftMouseClicked(int x, int y) {
		// TODO: Handle a mouse click
	}
	/**
	 * Called when the left mouse button has been pressed down
	 * @param x X position of the mouse pointer
	 * @param y Y position of the mouse pointer
	 */
	public void handleLeftMousePressed(int x, int y)
	{
		if(bStarted) {
			int blockPosX = (x-offsetX-1)/blockSize;
			int blockPosY = (y-offsetY-1)/blockSize;
			for (int i=0; i<blocks.length; i++) {
				if(blockPosX==blocks[i].getPosX() && blockPosY==blocks[i].getPosY()) {
					selectedBlock = blocks[i];
					break;
				}
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
		selectedBlock = null;
	}

	/**
	 * Called when the mouse has been dragged with the left 
	 * mouse button has been pressed down
	 * @param x X position of the mouse pointer
	 * @param y Y position of the mouse pointer
	 */
	public void handleLeftMouseDragged(int x, int y)
	{
		if(bStarted) {
			int blockPosX = (x-offsetX-1)/blockSize;
			int blockPosY = (y-offsetY-1)/blockSize;
			if(selectedBlock!=null) {
				int selPosX = selectedBlock.getPosX();
				int selPosY = selectedBlock.getPosY();
				if(blockPosX!=selPosX || blockPosY!=selPosY) {
					if(Math.abs(blockPosX-selPosX)>Math.abs(blockPosY-selPosY)) {
						if(blockPosX>selPosX) {
							selectedBlock.moveRight(this);
							movingBlock=selectedBlock;
						}else {
							selectedBlock.moveLeft(this);
							movingBlock=selectedBlock;
						}
					}else {
						if(blockPosY>selPosY) {
							selectedBlock.moveDown(this);
							movingBlock=selectedBlock;
						}else {
							selectedBlock.moveUp(this);
							movingBlock=selectedBlock;
						}
					}
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
					// TODO: Handle click on buttons[i]
				}
			}
		}
	}

	public void addWater(int blockX, int blockY, int partX, int partY, int direction)
	{
		for (int i=0; i<blocks.length; i++) {
			if(blocks[i].getPosX()==blockX && blocks[i].getPosY()==blockY) {
				if(!blocks[i].isMoving()) {
					if(blocks[i].initWater(partX, partY, direction)) {
						addBlocksWithMovingWater.add(blocks[i]);
					}
				}
			}
	    }
	}
	public boolean isFreePos(int x, int y)
	{
		if(x>=0 && x<sizeX) {
			if(y>=0 && y<sizeY) {
				for (int i=0; i<blocks.length; i++) {
					if(blocks[i].getPosX()==x && blocks[i].getPosY()==y) {
						return false;
					}
			    }
			    return true;
			}
		}
		return false;
	}
}
