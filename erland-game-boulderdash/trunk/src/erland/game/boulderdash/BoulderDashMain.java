package erland.game.boulderdash;
import erland.util.*;
import erland.game.*;
import erland.game.component.EButton;

import java.awt.*;
import java.awt.event.*;
import java.util.*;

/**
 * Main game class that controls the game logic and and screen redrawing
 */
class BoulderDashMain implements GamePanelInterface, ActionListener, BoulderDashContainerInterface
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
	/** Current score in the ongoing game */
	protected int score;
	/** Current level in the ongoing game */
	protected int level;
	/** Number of lifes left in the ongoing game */
	protected int lifes;
	/** Indicates if cheatmode is active or not */
	protected boolean bCheatMode;
	/** The last calculated FPS in cheatmode */
	protected FpsCounter fps;
	/** Array with all the buttons */
	protected EButton buttons[];
	/** Matrix with all the blocks on the game area */
	protected Block[][] blocks;
	/** Matrix with indications which positions that is allocated for future blocks */
	protected boolean allocated[][];
	/** Matrix with indications about which position that needs to be redrawn */
	protected int redraw[][];
	/** Block container for the game area */
	protected ChangeableBlockContainerData cont;
	/** Block container for the game area */
	protected BlockContainerData contVisible;
	/** Level factory object which creates the blocks for all levels */
	protected LevelFactory levelFactory;
	/** Indicates the number of diamonds that has been collected */
	protected int diamonds;
	/** Indicates the number of diamonds that should be collected on this level */
	protected int diamondsToCollect;
	/** Indicates if one of the arrow keys are pressed down */
	protected boolean moving;
	/** Indicates which of the arrow keys that are pressed down, see {@link erland.game.Direction} */
	protected int movingDirection;
	/** Player object, represents the player moving around in the game area */
	protected Player player;
	/** X scrolling position */
	protected int scrollX;
	/** Y scrolling position */
	protected int scrollY;
	/** Indicates if the Exit button has been pressed */
	protected boolean bExit;
	/** Indicates if the level has been drawn at least once, 0 if it has been drawn */
	protected int drawnOnce;
	/** Indicates the number of blocks that was redrawn, only used for testing */
	protected int redrawnBlocks;
    /** Game environment */
    private GameEnvironmentInterface environment;
    private KeyListener keyListener;

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
     * Class that catch all supported keyboard commands
     * and propagate them further to the main game class
     */
    class Keyboard extends KeyAdapter {
        /**
         * Called when a key is pressed down
         * @param e Information about the key pressed
         */
        public void keyPressed(KeyEvent e) {
            if(e.getKeyCode()==e.VK_LEFT) {
                moveLeft();
            }else if(e.getKeyCode()==e.VK_RIGHT) {
                moveRight();
            }else if(e.getKeyCode()==e.VK_UP) {
                moveUp();
            }else if(e.getKeyCode()==e.VK_DOWN) {
                moveDown();
            }else if(e.getKeyCode()==e.VK_SPACE) {
                hitSpace();
            }else if(bCheatMode && e.getKeyCode()==e.VK_F1) {
                // TODO: main.someCheatMethod();
            }
        }
        /**
         * Called when a key is released
         * @param e Information about the key released
         */
        public void keyReleased(KeyEvent e) {
            if(e.getKeyCode()==e.VK_LEFT) {
                stopMoveLeft();
            }else if(e.getKeyCode()==e.VK_RIGHT) {
                stopMoveRight();
            }else if(e.getKeyCode()==e.VK_UP) {
                stopMoveUp();
            }else if(e.getKeyCode()==e.VK_DOWN) {
                stopMoveDown();
            }
        }
    };

	/**
	 * Creates a new instance of the main game class
	 * @param offsetX Horisontal drawing offset, nothing should be drawn to the left of this position
	 * @param offsetY Vertical drawing offset, nothing should be drawn above this position
	 */
	public BoulderDashMain(int offsetX, int offsetY)
	{
		this.offsetX = offsetX;
		this.offsetY = offsetY;
	}

    public void init(GameEnvironmentInterface environment) {
        this.environment = environment;
        bExit = false;
		bCheatMode = false;
		cont = new ChangeableBlockContainerData(offsetX+1, offsetY+1, 20,20,20);
		contVisible = new BlockContainerData(offsetX+1, offsetY+1, 20,20,20);
		levelFactory = new LevelFactory(environment,this,cont);
		player = new Player();
        blocks = null;

		buttons = new EButton[1];
		buttons[0] = EButton.create("Exit");
		buttons[0].getComponent().setBounds(contVisible.getDrawingPositionX(contVisible.getSizeX())+10,
							contVisible.getDrawingPositionX(contVisible.getSizeX())+10,73,25);
		if(buttons!=null) {
			for(int i=0;i<buttons.length;i++) {
				buttons[i].addActionListener(this);
				environment.getScreenHandler().add(buttons[i].getComponent());
			}
		}

		init();
		newLevel();
		level=0;
        environment.getHighScore().load();
        keyListener = new Keyboard();
        environment.getScreenHandler().getContainer().requestFocus();
        environment.getScreenHandler().getContainer().addKeyListener(keyListener);
        environment.getScreenHandler().getContainer().setBackground(Color.black);
        fps = new FpsCounter(60);
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
		diamondsToCollect = levelFactory.getDiamonds(blocks);
		allocated = new boolean[blocks.length][blocks[0].length];
		redraw = new int[blocks.length][blocks[0].length];
		drawnOnce = 2;
		for(int x=0;x<blocks.length;x++) {
			for(int y=0;y<blocks[0].length;y++) {
				redraw[x][y] = 2;
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
	protected void exitGame()
	{
		bExit = true;
	}

    public void exit() {
        if(buttons!=null) {
            for(int i=0;i<buttons.length;i++) {
                environment.getScreenHandler().remove(buttons[i].getComponent());
            }
        }
        environment.getScreenHandler().getContainer().removeKeyListener(keyListener);
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
	 * Goto the next level
	 * @return <code>true</code> - Success
	 * <br><code>false</code> - Game completed
	 */
	protected boolean newLevel()
	{	
		level++;
		if(level<=levelFactory.getLastLevel()) {
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
	 */
	public void draw()
	{
        Graphics g = environment.getScreenHandler().getCurrentGraphics();
		redrawnBlocks = 0;
		int gameSizeX = contVisible.getSizeX()*contVisible.getSquareSize();
		int gameSizeY = contVisible.getSizeY()*contVisible.getSquareSize();
        fps.update();
		g.setColor(Color.blue);
		g.drawRect(offsetX, offsetY, gameSizeX+1,gameSizeY+1);
        g.clearRect(offsetX+gameSizeX+2,0,environment.getScreenHandler().getWidth()-(offsetX+gameSizeX+2),environment.getScreenHandler().getHeight());
		if(blocks!=null) {
			cont.setOffsetX(contVisible.getOffsetX()-scrollX*contVisible.getSquareSize());
			cont.setOffsetY(contVisible.getOffsetY()-scrollY*contVisible.getSquareSize());
			g.setColor(environment.getScreenHandler().getContainer().getBackground());
			player.drawClear(g);
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
							redrawnBlocks++;
						}
					}
				}
			}
			player.draw(g);
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
		
		
		int rightColumnX = offsetX+gameSizeX+20;
		int rightColumnY = offsetY+20;
		if(!bCheatMode) {
			environment.getHighScore().update(score);
		}
		g.setColor(Color.white);
		g.drawString("HIGHSCORE:",rightColumnX, rightColumnY);
		rightColumnY+=20;
		g.drawString(String.valueOf(environment.getHighScore().get()),rightColumnX, rightColumnY);
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
		if(bCheatMode) {
			g.drawString("*** Cheatmode *** FPS=",rightColumnX,rightColumnY);
            fps.draw(g,g.getColor(),rightColumnX+150,rightColumnY);
			rightColumnY+=20;
			g.drawString("Redrawn: " + redrawnBlocks,rightColumnX,rightColumnY);
		}else {
			rightColumnY+=20;
		}
		rightColumnY+=20;
		if(bEnd) {
            environment.getHighScore().save();
            if(blinkCounter<BLINK_SPEED) {
				blinkCounter++;
				if(level==(levelFactory.getLastLevel()+1)) {
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
        environment.getScreenHandler().paintComponents(g);
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
			
			if(blocks!=null) {
				for (int x=0; x<blocks.length; x++) {
					for (int y=0; y<blocks[0].length; y++) {
						if(blocks[x][y]!=null && blocks[x][y].needRedraw()) {
							redraw[x][y]=2;
						}
				    }
			    }
			}
			adjustScrollingOffset();	
			if(!player.isAlive()) {
					handleDeath();
			}else if(diamondsToCollect==diamonds) {
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
	 * Activate or deactivate cheatmode
	 * @param cheat true/false (Activate/Deactivate)
	 */
	public void setCheatmode(boolean cheat)
	{
		bCheatMode=cheat;
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
							exitGame();
							break;
						default:
							break;
					}
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
        if(player.movingDirection()==movingDirection) {
		    moving = false;
        }
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
        if(player.movingDirection()==movingDirection) {
    		moving = false;
        }
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
        if(player.movingDirection()==movingDirection) {
    		moving = false;
        }
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
        if(player.movingDirection()==movingDirection) {
    		moving = false;
        }
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
				if(player.isMoving() && player.movingDirection()==Direction.DOWN) {
					if((player.getMovingPosX()!=x || player.getMovingPosY()!=y)) {
						return true;
					}
				}else if(player.isMoving()){
					if((player.getMovingPosX()!=x || player.getMovingPosY()!=y)) {
						if(player.getPosX()!=x || player.getPosY()!=y) {
							return true;
						}else {
							if(player.moveCompleted()>0.5f) {
								return true;
							}
						}
					}
				}else {
					if(player.getPosX()!=x || player.getPosY()!=y) {
						return true;
					}
				}
			}
		}
		return false;
	}
	
	public boolean isKillable(int x, int y)
	{
		if(isInside(x,y)) {
			if(player.isAlive()) {
				if(/*height>0 && */player.getPosX()==x && player.getPosY()==y && (!player.isMoving() || player.moveCompleted()<0.5f)) {
					return true;
				}else if(/*height>1 && */player.getMovingPosX()==x && player.getMovingPosY()==y) {
					return true;
				}
			}
		}
		return false;
	}
	public boolean isDestroyable(int x, int y)
	{
		if(isInside(x,y)) {
			if(blocks[x][y]!=null) {
				return blocks[x][y].isDestroyable();
			}else if(player.isAlive()) {
				if(/*height>0 && */player.getPosX()==x && player.getPosY()==y && (!player.isMoving() || player.moveCompleted()<0.5f)) {
					return true;
				}else if(/*height>1 && */player.getMovingPosX()==x && player.getMovingPosY()==y) {
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
			if(blocks[x][y]!=null) {
				return blocks[x][y].destroy();
			}else if(player.isAlive()) {
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
	public boolean moveBlock(int x, int y, int direction,float speed)
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
				if(blocks[x][y].move(direction,speed)) {
					allocated[dx][dy]=true;
					return true;
				}
			}
		}
		return false;
	}

	public boolean digBlock(int x, int y, int direction, float speed)
	{
		if(isInside(x,y)) {
			if(blocks[x][y]!=null) {
				if(blocks[x][y].dig(direction,speed)) {
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
					redraw[oldX][oldY]=2;
					redraw[newX][newY]=2;
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
                redraw[b.getMovingPosX()][b.getMovingPosY()]=2;
				allocated[b.getMovingPosX()][b.getMovingPosY()]=false;
			}
			blocks[x][y] = null;
			redraw[x][y]=2;
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
			redraw[x][y]=2;
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
	
	public int getPlayerDirection(int x, int y) {
		if(Math.abs(x-player.getPosX())>Math.abs(y-player.getPosY())) {
			if((x-player.getPosX())>0) {
				return Direction.LEFT;
			}else {
				return Direction.RIGHT;
			}
		}else {
			if((y-player.getPosY())>0) {
				return Direction.UP;
			}else {
				return Direction.DOWN;
			}
		}
	}
}
