package erland.game.boulderdash;
import erland.util.*;
import erland.game.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;

/**
 * Main game class that controls the game logic and and screen redrawing
 */
class BoulderDashMain 
	implements ActionListener, BoulderDashContainerInterface
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
	protected static final int MAX_LEVEL=3;
	/** Array with all the buttons */
	protected Button buttons[];
	/** Matrix with all the blocks on the game area */
	protected Block[][] blocks;
	/** Matrix with indications which positions that is allocated for future blocks */
	protected boolean allocated[][];
	/** Block container for the game area */
	protected ChangeableBlockContainerData cont;
	/** Block container for the game area */
	protected BlockContainerData contVisible;
	/** Level factory object which creates the blocks for all levels */
	protected LevelFactory levelFactory;
	/** Indicates the number of diamonds that has been collected */
	protected int diamonds;
	/** Indicates if one of the arrow keys are pressed down */
	protected boolean moving;
	/** Indicates which of the arrow keys that are pressed down, see {@link erland.game#Direction} */
	protected int movingDirection;
	/** Player object, represents the player moving around in the game area */
	protected Player player;
	/** X scrolling position */
	protected int scrollX;
	/** Y scrolling position */
	protected int scrollY;
		
		
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
	 * Creates a new instance of the main game class
	 * @param container Container object which all Button's should be added to
	 * @param cookies Parameter storage object which should be used to access stored game data and highscores
	 * @param offsetX Horisontal drawing offset, nothing should be drawn to the left of this position
	 * @param offsetY Vertical drawing offset, nothing should be drawn above this position
	 */
	public BoulderDashMain(java.awt.Container container, ParameterValueStorageInterface cookies, ImageHandlerInterface images, int offsetX, int offsetY)
	{
		this.container = container;
		this.offsetX = offsetX;
		this.offsetY = offsetY;
		this.cookies = cookies;
		cheatMode = false;
		cont = new ChangeableBlockContainerData(offsetX, offsetY, 20,20,20);
		contVisible = new BlockContainerData(offsetX, offsetY, 10,10,20);
		levelFactory = new LevelFactory(this,images,cont);
		player = new Player();
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
	 * Initiate all data for the specified level
	 * @param level The level to initialize data for
	 */
	void initLevel(int level) 
	{
		diamonds=0;
		blocks = levelFactory.getLevel(level,player);
		allocated = new boolean[blocks.length][blocks[0].length];
		for(int x=0;x<blocks.length;x++) {
			for(int y=0;y<blocks[0].length;y++) {
				if(blocks[x][y]!=null) {
					allocated[x][y] = true;
				}else {
					allocated[x][y] = false;
				}
			}
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
			player.draw(g);
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
		g.drawString("Diamonds: "+String.valueOf(diamonds),rightColumnX, rightColumnY);
		rightColumnY+=20;
		if(cheatMode) {
			g.drawString("*** Cheatmode *** FPS=" + fps,rightColumnX,rightColumnY);
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
					g.drawString("    the game     ", rightColumnX, rightColumnY);
					rightColumnY+=20;
				}else {
					g.drawString("GAME OVER",rightColumnX, rightColumnY);
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
			if(blocks!=null) {
				for(int x=0;x<blocks.length;x++) {
					for(int y=0;y<blocks[0].length;y++) {
						if(blocks[x][y]!=null) {
							blocks[x][y].update();
						}
					}
				}
			}
			if(moving && !player.isMoving()) {
				Log.println(this,"moving:"+movingDirection);
				player.move(movingDirection);
			}
			player.update();
			
			adjustScrollingOffset();	
			if(!player.isAlive()) {
					handleDeath();
			}else if(levelFactory.getNumberOfDiamonds(level)==diamonds) {
				if(!newLevel()) {
					bEnd=true;
					bStarted=false;
				}
			}

		}
	}

	/**
	 * Adjust the scrolling offset
	 */
	protected void adjustScrollingOffset()
	{
		while((player.getPosX()-scrollX)>=(contVisible.getSizeX()-2)) {
			scrollX++;
		}
		while((player.getPosY()-scrollY)>=(contVisible.getSizeY()-2)) {
			scrollY++;
		}
		while((player.getPosX()-scrollX)<2) {
			scrollX--;
		}
		while((player.getPosY()-scrollY)<2) {
			scrollY--;
		}
		if(scrollX<0) {
			scrollX=0;
		}
		if(scrollX>cont.getSizeX()-contVisible.getSizeX()) {
			scrollX=cont.getSizeX()-contVisible.getSizeX();
		}
		if(scrollY<0) {
			scrollY=0;
		}
		if(scrollY>cont.getSizeY()-contVisible.getSizeY()) {
			scrollY=cont.getSizeY()-contVisible.getSizeY();
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
			initLevel(level);
			adjustScrollingOffset();	
			bStarted=true;
		}else {
			newGame();
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
			start();
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
		// TODO: Handle a mouse press
	}
	/**
	 * Called when the left mouse button has been released
	 * @param x X position of the mouse pointer
	 * @param y Y position of the mouse pointer
	 */
	public void handleLeftMouseReleased(int x, int y)
	{
		// TODO: Handle a mouse release
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

	/**
	 * Called when the left arrow is pressed
	 */
	public void moveLeft()
	{
		moving = true;
		movingDirection = Direction.LEFT;
	}

	/**
	 * Called when the left arrow is released
	 */
	public void stopMoveLeft()
	{
		moving = false;
	}
	/**
	 * Called when the right arrow is pressed
	 */
	public void moveRight()
	{
		moving = true;
		movingDirection = Direction.RIGHT;
	}

	/**
	 * Called when the right arrow is released
	 */
	public void stopMoveRight()
	{
		moving = false;
	}
	/**
	 * Called when the up arrow is pressed
	 */
	public void moveUp()
	{
		moving = true;
		movingDirection = Direction.UP;
	}

	/**
	 * Called when the up arrow is released
	 */
	public void stopMoveUp()
	{
		moving = false;
	}
	/**
	 * Called when the down arrow is pressed
	 */
	public void moveDown()
	{
		moving = true;
		movingDirection = Direction.DOWN;
	}

	/**
	 * Called when the down arrow is released
	 */
	public void stopMoveDown()
	{
		moving = false;
	}

	/**
	 * Checks if the specified block position is inside
	 * the game area
	 * @param x The x position to check
	 * @param y The y position to check
	 * @return true/false (Inside/Not inside)
	 */
	protected boolean isInside(int x, int y)
	{
		if(x>=0 && x<cont.getSizeX()) {
			if(y>=0 && y<cont.getSizeY()) {
				return true;
			}
		}
		return false;
	}
	public boolean isFree(int x, int y)
	{
		if(isInside(x,y)) {
			if(!allocated[x][y]) {
				if(!player.isAlive()) {
					return true;
				}
				if(player.movingDirection()==Direction.DOWN) {
					if((player.getMovingPosX()!=x || player.getMovingPosY()!=y)) {
						return true;
					}
				}else {
					if((player.getPosX()!=x || player.getPosY()!=y) && (player.getMovingPosX()!=x || player.getMovingPosY()!=y)) {
						return true;
					}
				}
			}
		}
		return false;
	}
	
	public boolean isDestroyable(int x, int y, int height)
	{
		if(isInside(x,y)) {
			if(player.isAlive()) {
				if(height>0 && player.getPosX()==x && player.getPosY()==y) {
					return true;
				}else if(height>1 && player.getMovingPosX()==x && player.getMovingPosY()==y) {
					return true;
				}
			}
		}
		return false;
	}

	public boolean isDigThrough(int x, int y)
	{
		if(isInside(x,y)) {
			if(blocks[x][y]!=null) {
				return blocks[x][y].isDigThrough();
			}
		}
		return false;
	}

	public boolean isSlippery(int x, int y)
	{
		if(isInside(x,y)) {
			if(blocks[x][y]!=null) {
				return blocks[x][y].isSlippery();
			}
		}
		return false;
	}

	public boolean isMovable(int x, int y)
	{
		if(isInside(x,y)) {
			if(blocks[x][y]!=null) {
				return blocks[x][y].isMovable();
			}
		}
		return false;
	}

	public void allocatePos(int x, int y)
	{
		if(isInside(x,y)) {
			allocated[x][y] = true;
		}
	}
	
	public void freePos(int x, int y)
	{
		if(isInside(x,y)) {
			allocated[x][y] = false;
		}
	}
	public boolean destroyBlock(int x, int y, int height)
	{
		if(isInside(x,y)) {
			if(player.isAlive()) {
				if(height>0 && player.getPosX()==x && player.getPosY()==y) {
					player.destroy();
					return true;
				}/*else if(height>1 && player.getMovingPosX()==x && player.getMovingPosY()==y) {
					player.destroy();
					return true;
				}*/
			}
		}
		return false;
	}
	public boolean moveBlock(int x, int y, int direction)
	{
		int dx=x;
		int dy=y;
		switch(direction) {
			case Direction.LEFT:
				dx--;
				break;
			case Direction.RIGHT:
				dx++;
				break;
			case Direction.UP:
				dy--;
				break;
			case Direction.DOWN:
				dy++;
				break;
			default:
				break;
		}
		if(isInside(dx,dy)) {
			if(!allocated[dx][dy]) {
				if(blocks[x][y].move(direction)) {
					allocated[dx][dy]=true;
					return true;
				}
			}
		}
		return false;
	}

	public boolean digBlock(int x, int y, int direction)
	{
		if(isInside(x,y)) {
			if(blocks[x][y]!=null) {
				if(blocks[x][y].dig(direction)) {
					return true;
				}
			}
		}
		return false;
	}

	
	public boolean setBlockPos(int oldX, int oldY, int newX, int newY)
	{
		if(isInside(oldX,oldY)) {
			if(isInside(newX,newY)) {
				if(blocks[newX][newY]==null) {
					blocks[newX][newY]=blocks[oldX][oldY];
					blocks[oldX][oldY]=null;
					allocated[oldX][oldY]=false;
					allocated[newX][newY]=true;
					blocks[newX][newY].setPos(newX,newY);
					return true;
				}
			}
		}
		return false;
	}
	
	public void delBlock(Block block)
	{
		int x = block.getPosX();
		int y = block.getPosY();
		if(isInside(x,y)) {
			Block b = blocks[x][y];
			if(blocks[x][y].isMoving()) {
				blocks[b.getMovingPosX()][b.getMovingPosY()]=null;
				allocated[b.getMovingPosX()][b.getMovingPosY()]=false;
			}
			blocks[x][y] = null;
			allocated[x][y] = false;
		}
	}
	
	public void addBlock(Block block)
	{
		int x = block.getPosX();
		int y = block.getPosY();
		if(isInside(x,y)) {
			blocks[x][y] = block;
			allocated[x][y] = true;
		}
	}
	
	public void increaseDiamonds()
	{
		diamonds++;
		increaseScore(10);
	}
	
	public void increaseScore(int score)
	{
		this.score+=score*level;
	}
}
