package erland.game.crackout;
import erland.game.*;
import erland.game.component.EButton;

import java.awt.*;
import java.awt.event.*;
import java.util.*;
/**
 * This is the main game object that handles most of the game logic
 */
class CrackOutMain
	implements GamePanelInterface, ActionInterface, ActionListener
{
	/** Horisontal drawing offset */
	protected int offsetX;
	/** Vertical drawing offset */
	protected int offsetY;
	/** Size of the squares all the blocks consists of */
	protected int squareSize;
	/** Array of all drawn bats (Can be 1 or 2) */
	protected Bat bats[];
    /** List of all active balls */
    protected LinkedList balls;
    /** List of balls that should bw added to currently active */
    protected LinkedList newBalls;
	/** List of all balls that should be removed from {@link #balls} */
	protected LinkedList removeBalls;
	/** Width of the main game area (Number of squares) */
	protected final int sizeX=20;
	/** Height of the main game area (Number of squares) */
	protected final int sizeY=20;
	/** Array with all the blocks in the current level */
	protected Block blocks[];
    /** List of all currently active features */
    protected LinkedList features;
    /** List of all currently active monsters */
    protected LinkedList monsters;
    /** List of features that should be added to currently active */
    protected LinkedList newFeatures;
    /** List of monsters that should be added to currently active */
    protected LinkedList newMonsters;
	/** List of all features that should be removed from {@link #features} */
	protected LinkedList removeFeatures;
	/** List of all monsters that should be removed from {@link #monsters} */
	protected LinkedList removeMonsters;
    /** List of all currently active missiles */
    protected LinkedList missiles;
    /** List of all currently active bombs */
    protected LinkedList bombs;
	/** Number of missiles that is left and can be launched */
	protected int missileCount;
	/** Indicates if the bat is moving left */
	protected boolean moveLeft;
	/** Indicates if the bat is moving right */
	protected boolean moveRight;
	/** Indicates if the game has been completed or GAME OVER has occurred */
	protected boolean bEnd;
	/** Counter that handles the blinking of text */
	protected int blinkCounter;
	/** The text blink speed */
	protected final int BLINK_SPEED=20;
	/** Indicates if the game is running or waiting for the user */
	protected boolean bStarted;
	/** Current score */
	protected int score;
	/** Current level */
	protected int level;
	/** The size of the ball (Can no longer be changed without making a new ball bitmap) */
	protected int ballSize=15;
	/** The speed all new balls get */
	protected int ballSpeed;
	/** The number of lifes left */
	protected int lifes;
	/** Counter that handles the locked bat state, greater than zero means locked bat */
	protected int lockedBat;
	/** Counter that handles double bat state, greater than zero means locked bat */
	protected int doubleBatCountDown;
	/** Counter that handles safety wall state, greater than zero means safety wall active */
	protected int safetyWallCountDown;
	/** Counter that handles changed bat size state, greater than zero means changed bat size */
	protected int batSizeCountDown;
	/** Counter that handles the blinking of the safety wall when it is about to disappear */
	protected int safetyWallBlink;
	/** Level factory object which contains all level data */
	protected LevelFactory levelFactory;
	/** Block container for the main game area */
	protected BlockContainerData cont;
	/** Indicates that the Exit button has been pressed */
	protected boolean bExit;
	/** The Exit button */
	protected EButton exitButton;
	/** Indicates if cheatmode is active or not */
	protected boolean bCheatMode;
	/** Counter used to calculate framerate when cheatmode is active */
	protected long frameTime=0;
	/** Counter used to indicate how many frames that shall be used to calculate a new framerate in cheatmode */
	protected FpsCounter fps;
    /** Game environment */
    private GameEnvironmentInterface environment;
    private KeyListener keyListener;

    /**
     * Takes care of all keyboard events
     */
    class Keyboard extends KeyAdapter {
        /**
         * Called when a key is pressed down
         * @param e KeyEvent event
         */
        public void keyPressed(KeyEvent e) {
            if(e.getKeyCode()==e.VK_LEFT) {
                moveLeft();
            }else if(e.getKeyCode()==e.VK_RIGHT) {
                moveRight();
            }else if(e.getKeyCode()==e.VK_SPACE) {
                hitSpace();
            }else if(bCheatMode && e.getKeyCode()==e.VK_F1) {
                increaseStartLevel();
            }else if(bCheatMode && e.getKeyCode()==e.VK_F2) {
                safetyWall();
            }else if(bCheatMode && e.getKeyCode()==e.VK_F3) {
                increaseBallSpeed();
            }else if(bCheatMode && e.getKeyCode()==e.VK_F4) {
                decreaseBallSpeed();
            }else if(bCheatMode && e.getKeyCode()==e.VK_F5) {
                increaseBatSpeed();
            }else if(bCheatMode && e.getKeyCode()==e.VK_F6) {
                decreaseBatSpeed();
            }else if(bCheatMode && e.getKeyCode()==e.VK_F7) {
                newBall();
            }else if(bCheatMode && e.getKeyCode()==e.VK_F8) {
                doubleBat();
            }else if(bCheatMode && e.getKeyCode()==e.VK_F9) {
                largeBat();
            }else if(bCheatMode && e.getKeyCode()==e.VK_F10) {
                smallBat();
            }
        }
        /**
         * Called when a key is released
         * @param e KeyEvent event
         */
        public void keyReleased(KeyEvent e) {
            if(e.getKeyCode()==e.VK_LEFT) {
                stopMoveLeft();
            }else if(e.getKeyCode()==e.VK_RIGHT) {
                stopMoveRight();
            }
        }
    }

	/**
	 * Creates a new instance of the main game object
	 * @param offsetX Horisontal drawing offset
	 * @param offsetY Vertical drawing offset
	 * @param squareSize The size of the square which all the blocks conists of
	 */
	public CrackOutMain(int offsetX, int offsetY, int squareSize)
	{
		this.offsetX = offsetX;
		this.offsetY = offsetY;
		this.squareSize = squareSize;
	}

    public void init(GameEnvironmentInterface environment) {
        this.environment = environment;
		features = new LinkedList();
		monsters = new LinkedList();
        removeFeatures = new LinkedList();
        newFeatures = new LinkedList();
        removeMonsters = new LinkedList();
        newMonsters = new LinkedList();
		balls = new LinkedList();
        removeBalls = new LinkedList();
        newBalls = new LinkedList();
        missiles = new LinkedList();
        bombs = new LinkedList();
		bats = new Bat[2];
		bExit = false;
		bCheatMode = false;
		cont = new BlockContainerData(offsetX+1, offsetY+1,sizeX, sizeY, squareSize);
		//exitButton = new ImageObject(images.getImage(images.BUTTON_EXIT),offsetX, offsetY, 360,260,75,35);
		exitButton = EButton.create("Exit");
		exitButton.getComponent().setBounds(offsetX + 360,offsetY + 260,75,25);
		exitButton.addActionListener(this);
		levelFactory = new LevelFactory(environment,cont);
		init();
		newLevel();
		level=0;
        environment.getHighScore().load();
        environment.getScreenHandler().add(exitButton.getComponent());
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
		missileCount=0;
		ballSize=squareSize;
		bEnd = false;
		blinkCounter = 0;
		bStarted=false;
		level=0;
		score=0;
		lifes =3;
	}
	
	/**
	 * Handle everything needed when the last ball has been lost
	 */
	protected void handleDeath()
	{
		lifes--;
		if(lifes<=0) {
			bEnd=true;
			bStarted=false;
		}else {
			ballSpeed = squareSize/4+level/2;
			int batSpeed = squareSize/4+level/2;
			lockedBat = 0;
			features.clear();
			monsters.clear();
			removeMonsters.clear();
			removeFeatures.clear();
			removeBalls.clear();
			balls.clear();
			missiles.clear();
			bombs.clear();
			bats[1]=null;
			Ball b = new Ball();
			b.init(environment,offsetX+1,offsetY+1,sizeX*squareSize,sizeY*squareSize,squareSize*sizeX/2-ballSize/2,squareSize*sizeY-squareSize*2-ballSize-1,ballSize,ballSpeed,2*Math.PI*25/32);
			balls.add(b);
			moveLeft = false;
			moveRight = false;
			bStarted=false;
			doubleBatCountDown=0;
			safetyWallCountDown=0;
			batSizeCountDown=0;
			bats[0].init(offsetX+1, offsetY+1, 0,squareSize*sizeX,(squareSize*sizeX)/2-squareSize*2,(squareSize*sizeY)-squareSize*2, squareSize*4, squareSize,batSpeed);
		}
	}
	
	/**
	 * Get and initialize next level
	 * @return true/false - false indicates that the game has been completed 
	 */
	protected boolean newLevel()
	{	
		level++;
		ballSpeed = squareSize/4+level/2;
		int batSpeed = squareSize/4+level/2;
		lockedBat = 0;
		features.clear();
		monsters.clear();
		removeMonsters.clear();
		removeFeatures.clear();
		removeBalls.clear();
        newMonsters.clear();
        newFeatures.clear();
        newBalls.clear();
		missiles.clear();
		bombs.clear();
		balls.clear();
		doubleBatCountDown=0;
		safetyWallCountDown=0;
		batSizeCountDown=0;
		if(level<=levelFactory.getLastLevel()) {
			if(level>1) {
				score+=level*100;
			}
			bats[0] = new Bat();
			bats[1] = null;
			bats[0].init(offsetX+1, offsetY+1, 0,squareSize*sizeX,(squareSize*sizeX)/2-squareSize*2,(squareSize*sizeY)-squareSize*2, squareSize*4, squareSize,batSpeed);
			Ball b = new Ball();
			b.init(environment,offsetX+1,offsetY+1,sizeX*squareSize,sizeY*squareSize,squareSize*sizeX/2-ballSize/2,squareSize*sizeY-squareSize*2-ballSize-1,ballSize,ballSpeed,2*Math.PI*25/32);
			balls.add(b);
			moveLeft = false;
			moveRight = false;
			bStarted=false;
			blocks = levelFactory.getLevel(level);
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
        fps.update();
        Graphics g = environment.getScreenHandler().getCurrentGraphics();
        g.clearRect(0,0,environment.getScreenHandler().getWidth(),environment.getScreenHandler().getHeight());
		g.setColor(Color.blue);
		g.fillRect(offsetX, offsetY, 1,sizeY*squareSize);
		g.fillRect(offsetX, offsetY, sizeX*squareSize,1);
		g.fillRect(offsetX+sizeX*squareSize,offsetY,1,sizeY*squareSize);
		if(safetyWallCountDown>0) {
			if(safetyWallCountDown<100) {
				if(safetyWallBlink<(BLINK_SPEED/4)) {
					safetyWallBlink++;
					g.fillRect(offsetX,offsetY+sizeY*squareSize,sizeX*squareSize,1);
				}else if(safetyWallBlink<(BLINK_SPEED/2)) {
					safetyWallBlink++;
				}else {
					safetyWallBlink=0;
				}
			}else {
				g.fillRect(offsetX,offsetY+sizeY*squareSize,sizeX*squareSize,1);
			}
		}
		for(int x=0;x<sizeX;x++) {
			//g.drawRect(offsetX+x*squareSize,offsetY, 0,sizeX*squareSize);
		}
		for(int y=0;y<sizeY;y++) {
			//g.drawRect(offsetX,offsetY+y*squareSize, sizeY*squareSize,0);
		}
		for(int i=0;i<blocks.length;i++) {
			blocks[i].draw(g);
		}
		ListIterator it = features.listIterator();
		while(it.hasNext()) {
			Feature f = (Feature)(it.next());
			f.draw(g);
		}
		bats[0].draw(g);
		if(bats[1]!=null) {
			bats[1].draw(g);
		}
		it = monsters.listIterator();
		while(it.hasNext()) {
			Monster m = (Monster)(it.next());
			m.draw(g);
		}
		it = balls.listIterator();
		while(it.hasNext()) {
			Ball b = (Ball)(it.next());
			b.draw(g);
		}
		it = missiles.listIterator();
		while(it.hasNext()) {
			Missile m = (Missile)(it.next());
			m.draw(g);
		}
		it = bombs.listIterator();
		while(it.hasNext()) {
			Bomb b=(Bomb)(it.next());
			b.draw(g);
		}
		int rightColumnX = offsetX+sizeX*squareSize+20;
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
		if(missileCount>0) {
			g.drawString("Missiles: "+String.valueOf(missileCount),rightColumnX,rightColumnY);
		}
		rightColumnY+=20;
		if(bCheatMode) {
			g.drawString("*** Cheatmode *** FPS=" ,rightColumnX,rightColumnY);
            fps.draw(g,Color.white,rightColumnX+150,rightColumnY);
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
		if(environment.getStorage()!=null) {
			//exitButton.draw(g);
		}
		g.setColor(Color.red);
		g.drawString("by Erland Isaksson",rightColumnX,offsetY+sizeY*squareSize);
        environment.getScreenHandler().paintComponents(g);
	}
	
	/**
	 * Update all the objects for the next frame
	 */
	public void update()
	{
		if(!bStarted && level>0) {
			if(moveLeft) {
				bats[0].moveLeft();
				if(bats[1]!=null) {
					bats[1].moveLeft();
				}
			}else if(moveRight) {
				bats[0].moveRight();
				if(bats[1]!=null) {
					bats[1].moveRight();
				}
			}
			ListIterator it=balls.listIterator();
			while(it.hasNext()) {
				Ball b = (Ball)(it.next());
				b.move(bats[0].left()+(bats[0].right()-bats[0].left())/2-ballSize/2,bats[0].top()-ballSize-1);
			}
		}
		if(!bEnd && bStarted) {
			if(safetyWallCountDown>0) {
				safetyWallCountDown--;
			}else {
				safetyWallCountDown=0;
			}
			if(doubleBatCountDown>0) {
				doubleBatCountDown--;
			}else {
				doubleBatCountDown=0;
				bats[1]=null;
			}
			if(batSizeCountDown>0) {
				batSizeCountDown--;
			}else {
				batSizeCountDown=0;
				bats[0].setSizeX(squareSize*4);
				if(bats[1]!=null) {
					bats[1].setSizeX(squareSize*2);
				}
			}
			if(lockedBat<=0) {
				lockedBat=0;
				if(moveLeft) {
					bats[0].moveLeft();
					if(bats[1]!=null) {
						bats[1].moveLeft();
					}
				}else if(moveRight) {
					bats[0].moveRight();
					if(bats[1]!=null) {
						bats[1].moveRight();
					}
				}
			}else {
				lockedBat--;
			}
			ListIterator it = features.listIterator();
			while(it.hasNext()) {
				Feature f=(Feature)(it.next());
				int tmp = f.move(this,bats);
				score += tmp;
			}
			it = monsters.listIterator();
			while(it.hasNext()) {
				Monster m=(Monster)(it.next());
				int tmp = m.move(this,bats); 
				score += tmp;
			}
			it = balls.listIterator();
			while(it.hasNext()) {
				Ball b=(Ball)(it.next());
				boolean safety=false;
				if(safetyWallCountDown>0) {
					safety=true;
				}
				int tmp = b.move(this,bats,blocks,monsters,level,safety);
				score += tmp;
			}
			it = missiles.listIterator();
			while(it.hasNext()) {
				Missile m=(Missile)(it.next());
				int tmp = m.move(this,blocks,level);
				score += tmp;
			}
			boolean bFinished=true;
			for(int i=0;i<blocks.length;i++) {
				if(blocks[i].isRemovable() && blocks[i].isActive()) {
					bFinished=false;
				}
			}
			if(bFinished) {
				if(!newLevel()) {
					bEnd=true;
					bStarted=false;
				}
			}else {
				if(balls.size()==0) {
					handleDeath();
				}
			}

		}
        ListIterator it=newMonsters.listIterator();
        while(it.hasNext()) {
            Monster m= (Monster) (it.next());
            monsters.add(m);
        }
        it=newFeatures.listIterator();
        while(it.hasNext()) {
            Feature f= (Feature) (it.next());
            features.add(f);
        }
        it=newBalls.listIterator();
        while(it.hasNext()) {
            Ball b= (Ball) (it.next());
            balls.add(b);
        }
		it=removeMonsters.listIterator();
		while(it.hasNext()) {
			Monster m=(Monster)(it.next());
			monsters.remove(m);
		}
		it=removeFeatures.listIterator();
		while(it.hasNext()) {
			Feature f=(Feature)(it.next());
			features.remove(f);
		}
		it=removeBalls.listIterator();
		while(it.hasNext()) {
			Ball b=(Ball)(it.next());
			balls.remove(b);
		}
		removeMonsters.clear();
		removeFeatures.clear();
		removeBalls.clear();
        newMonsters.clear();
        newFeatures.clear();
        newBalls.clear();
		it=missiles.listIterator();
		while(it.hasNext()) {
			Missile m=(Missile)(it.next());
			if(!m.isActive()) {
				it.remove();
			}
		}
		it=bombs.listIterator();
		while(it.hasNext()) {
			Bomb b=(Bomb)(it.next());
			if(!b.isActive()) {
				it.remove();
			}
		}
	}

	/**
	 * Start moving the bat left
	 */
	public void moveLeft()
	{
		moveLeft = true;
		moveRight = false;
	}
	/**
	 * Start moving the bat right
	 */
	public void moveRight()
	{
		moveLeft = false;
		moveRight = true;
	}
	/**
	 * Stop the bat from moving to left
	 */
	public void stopMoveLeft()
	{
		moveLeft = false;
	}
	/**
	 * Stop the bat from moving to right
	 */
	public void stopMoveRight()
	{
		moveRight = false;
	}
	
	/**
	 * Step to next level, should only be used in cheatmode
	 */
	public void increaseStartLevel()
	{
		if(!bStarted) {
			if(level>=(levelFactory.getLastLevel())) {
				level=0;
			}
			int prevLevel = level;
			init();
			level = prevLevel;
			newLevel();
			score=0;
		}
	}
	
	/**
	 * Performs the action associated with the space key.
	 * This means firing missiles if game is running or start the
	 * game if it is not running
	 */
	public void hitSpace()
	{
		if(bStarted) {
			if(missileCount>0) {
				Missile m = new Missile();
				int y;
				if(bats[1]!=null) {
					y=bats[1].top()-10;
				}else {
					y=bats[0].top()-10;
				}
				m.init(environment,cont,bats[0].left()+(bats[0].right()-bats[0].left())/2-5,y,10,20,8);
				missiles.add(m);
				missileCount--;
			}
		}else {
			start();
		}
	}
	
	/**
	 * Start the game when it waits for the user to press space
	 */
	public void start()
	{
		if(!bStarted && level>0 && !bEnd) {
			bStarted=true;
		}else {
			newGame();
		}
	}
	
	/**
	 * Start a new game
	 */
	public void newGame()
	{
		if(bEnd || !bStarted) {
			init();
			newLevel();
		}
	}
	
	public void newBall()
	{
		Ball b = new Ball();
		Bat bat;
		if(bats[1]!=null) {
			bat = bats[1];
		}else {
			bat = bats[0];
		}
		b.init(environment,offsetX+1,offsetY+1,sizeX*squareSize,sizeY*squareSize,bat.left()+(bat.right()-bat.left())/2-ballSize/2,bat.top()-ballSize-1,ballSize,ballSpeed,2*Math.PI*25/32);
		newBalls.add(b);
	}
	public void newMonster(Monster m)
	{
		newMonsters.add(m);
	}
	public void newFeature(Feature f)
	{
		newFeatures.add(f);
	}
	public void lockBat()
	{
		lockedBat=50;
	}
	public void removeMonster(Monster m)
	{
		removeMonsters.add(m);
	}
	public void removeFeature(Feature f)
	{
		removeFeatures.add(f);
	}
	public void removeBall(Ball b) {
		removeBalls.add(b);
	}
	public void increaseBallSpeed()
	{
		ListIterator it = balls.listIterator();
		while(it.hasNext()) {
			Ball b=(Ball)(it.next());
			b.setSpeed(b.getSpeed()+2);
		}
	}
	public void decreaseBallSpeed()
	{
		ListIterator it = balls.listIterator();
		while(it.hasNext()) {
			Ball b=(Ball)(it.next());
			b.setSpeed(b.getSpeed()-2);
		}
	}
	public void increaseBatSpeed()
	{
		bats[0].setSpeed(bats[0].getSpeed()+2);
		if(bats[1]!=null) {
			bats[1].setSpeed(bats[0].getSpeed()+2);
		}
	}
	public void decreaseBatSpeed()
	{
		bats[0].setSpeed(bats[0].getSpeed()-2);
		if(bats[1]!=null) {
			bats[1].setSpeed(bats[0].getSpeed()-2);
		}
	}
	public void extraLife()
	{
		lifes++;
	}
	public void doubleBat()
	{
		doubleBatCountDown=512;
		if(bats[1]==null) {
			bats[1]=new Bat();
		}
		int offset = (bats[0].right()-bats[0].left())/4;
		bats[1].init(offsetX+1, offsetY+1, offset,squareSize*sizeX-offset,bats[0].left()+offset,(squareSize*sizeY)-squareSize*6, bats[0].getSizeX()/2, squareSize,bats[0].getSpeed());
	}
	public void safetyWall()
	{	
		safetyWallBlink=0;
		safetyWallCountDown=512;
	}
	public void newMissile()
	{
		missileCount++;
	}
	public void largeBat()
	{
		batSizeCountDown=512;
		if(bats[0].getSizeX()<=(squareSize*4)) {
			bats[0].setSizeX(squareSize*6);
			if(bats[1]!=null) {
				bats[1].setSizeX(squareSize*3);
				int offset = (squareSize*6)/4;
				bats[1].setLimit(offset,squareSize*sizeX-offset);
			}
		}
	}
	public void smallBat()
	{
		batSizeCountDown=512;
		if(bats[0].getSizeX()>=(squareSize*4)) {
			bats[0].setSizeX(squareSize*2);
			if(bats[1]!=null) {
				bats[1].setSizeX(squareSize);
				int offset = (squareSize*2)/4;
				bats[1].setLimit(offset,squareSize*sizeX-offset);
			}
		}
	}

	public void explode(int x, int y, int sizeX, int sizeY)
	{
		Bomb b = new Bomb();
		b.init(cont,x,y,sizeX,sizeY);
		int tmp = b.explode(this,blocks,monsters,level);
		score += tmp;
		bombs.add(b);
	}

	/**
	 * Check if the Exit button has been pressed
	 * @return true/false (Exit pressed/Exit not pressed)
	 */
	public boolean isExit()
	{
		return bExit;
	}
	
	/**
	 * Activate or deactivate cheatmode
	 * @param cheat true neas cheatmode, false means normal mode
	 */
	public void setCheatmode(boolean cheat)
	{
		bCheatMode=cheat;
	}
	
	/**
	 * Handles the click on buttons
	 * @param e ActionEvent indicating which button that has been pressed
	 */
	public void actionPerformed(ActionEvent e)
	{
		if(e.getSource()==exitButton.getComponent()) {
			bExit=true;
		}
	}
    public void exit() {
        environment.getScreenHandler().remove(exitButton.getComponent());
        environment.getScreenHandler().getContainer().removeKeyListener(keyListener);
    }
}
