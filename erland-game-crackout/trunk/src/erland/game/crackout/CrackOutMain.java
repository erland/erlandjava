package erland.game.crackout;
import erland.util.*;
import erland.game.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;

class CrackOutMain 
	implements ActionInterface, ActionListener
{
	int offsetX;
	int offsetY;
	int squareSize;
	Bat bats[];
	LinkedList balls;
	LinkedList removeBalls;
	final int sizeX=20;
	final int sizeY=20;
	Block blocks[];
	LinkedList features;
	LinkedList monsters;
	LinkedList removeFeatures;
	LinkedList removeMonsters;
	LinkedList missiles;
	LinkedList bombs;
	int missileCount;
	boolean moveLeft;
	boolean moveRight;
	boolean bEnd;
	int blinkCounter;
	final int BLINK_SPEED=20;
	boolean bStarted;
	int savedHighScore;
	int highScore;
	int score;
	int level;
	int ballSize=15;
	int ballSpeed;
	int batSpeed;
	int lifes;
	int lockedBat;
	int doubleBatCountDown;
	int safetyWallCountDown;
	int batSizeCountDown;
	int safetyWallBlink;
	ImageHandlerInterface images;
	ParameterValueStorageInterface cookies;
	LevelFactory levelFactory;
	BlockContainerData cont;
	boolean bExit;
	//ImageObject exitButton;
	Button exitButton;
	boolean cheatMode;
	long frameTime=0;
	int fpsShow=0;
	long fps=0;
	Container container;
	
	public CrackOutMain(java.awt.Container container, ImageHandlerInterface images, ParameterValueStorageInterface cookies, int offsetX, int offsetY, int squareSize)
	{
		this.container = container;
		this.offsetX = offsetX;
		this.offsetY = offsetY;
		this.squareSize = squareSize;
		features = new LinkedList();
		monsters = new LinkedList();
		removeFeatures = new LinkedList();
		removeMonsters = new LinkedList();
		balls = new LinkedList();
		removeBalls = new LinkedList();
		missiles = new LinkedList();
		bombs = new LinkedList();
		bats = new Bat[2];
		this.images = images;
		this.cookies = cookies;
		bExit = false;
		cheatMode = false;
		cont = new BlockContainerData(offsetX+1, offsetY+1,sizeX, sizeY, squareSize);
		//exitButton = new ImageObject(images.getImage(images.BUTTON_EXIT),offsetX, offsetY, 360,260,75,35);
		exitButton = new Button("Exit");
		exitButton.setBounds(offsetX + 360,offsetY + 260,75,25);
		exitButton.addActionListener(this);
		container.add(exitButton);
		levelFactory = new LevelFactory(images,cookies,cont);
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
	
	void init()
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
	void handleDeath()
	{
		lifes--;
		if(lifes<=0) {
			bEnd=true;
		}else {
			ballSpeed = squareSize/4+level/2;
			batSpeed = squareSize/4+level/2;
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
			b.init(images,offsetX+1,offsetY+1,sizeX*squareSize,sizeY*squareSize,squareSize*sizeX/2-ballSize/2,squareSize*sizeY-squareSize*2-ballSize-1,ballSize,ballSpeed,2*Math.PI*25/32);
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
	boolean newLevel()
	{	
		level++;
		ballSpeed = squareSize/4+level/2;
		batSpeed = squareSize/4+level/2;
		lockedBat = 0;
		features.clear();
		monsters.clear();
		removeMonsters.clear();
		removeFeatures.clear();
		removeBalls.clear();
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
			b.init(images,offsetX+1,offsetY+1,sizeX*squareSize,sizeY*squareSize,squareSize*sizeX/2-ballSize/2,squareSize*sizeY-squareSize*2-ballSize-1,ballSize,ballSpeed,2*Math.PI*25/32);
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
	
	public void draw(Graphics g)
	{
		if((++fpsShow)%25==0) {
			long cur = System.currentTimeMillis();
			fps = 25000/(cur-frameTime);
			frameTime = cur;
		}
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
		if(missileCount>0) {
			g.drawString("Missiles: "+String.valueOf(missileCount),rightColumnX,rightColumnY);
		}
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
		if(cookies!=null) {
			//exitButton.draw(g);
		}
		g.setColor(Color.red);
		g.drawString("by Erland Isaksson",rightColumnX,offsetY+sizeY*squareSize);
	}
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
				}
			}else {
				if(balls.size()==0) {
					handleDeath();
				}
			}

		}
		ListIterator it=removeMonsters.listIterator();
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
	public void moveLeft()
	{
		moveLeft = true;
		moveRight = false;
	}
	public void moveRight()
	{
		moveLeft = false;
		moveRight = true;
	}
	public void stopMoveLeft()
	{
		moveLeft = false;
	}
	public void stopMoveRight()
	{
		moveRight = false;
	}
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
				m.init(images,cont,bats[0].left()+(bats[0].right()-bats[0].left())/2-5,y,10,20,8);
				missiles.add(m);
				missileCount--;
			}
		}else {
			start();
		}
	}
	public void start()
	{
		if(!bStarted && level>0) {
			bStarted=true;
		}else {
			newGame();
		}
	}
	public void newGame()
	{
		if(bEnd || !bStarted) {
			init();
			newLevel();
		}
	}
	
	public void NewBall()
	{
		Ball b = new Ball();
		Bat bat;
		if(bats[1]!=null) {
			bat = bats[1];
		}else {
			bat = bats[0];
		}
		b.init(images,offsetX+1,offsetY+1,sizeX*squareSize,sizeY*squareSize,bat.left()+(bat.right()-bat.left())/2-ballSize/2,bat.top()-ballSize-1,ballSize,ballSpeed,2*Math.PI*25/32);
		balls.add(b);
	}
	public void NewMonster(Monster m)
	{
		monsters.add(m);
	}
	public void NewFeature(Feature f)
	{
		features.add(f);
	}
	public void LockBat()
	{
		lockedBat=50;
	}
	public void RemoveMonster(Monster m)
	{
		removeMonsters.add(m);
	}
	public void RemoveFeature(Feature f)
	{
		removeFeatures.add(f);
	}
	public void RemoveBall(Ball b) {
		removeBalls.add(b);
	}
	public void IncreaseBallSpeed()
	{
		ListIterator it = balls.listIterator();
		while(it.hasNext()) {
			Ball b=(Ball)(it.next());
			b.setSpeed(b.getSpeed()+2);
		}
	}
	public void DecreaseBallSpeed()
	{
		ListIterator it = balls.listIterator();
		while(it.hasNext()) {
			Ball b=(Ball)(it.next());
			b.setSpeed(b.getSpeed()-2);
		}
	}
	public void IncreaseBatSpeed()
	{
		bats[0].setSpeed(bats[0].getSpeed()+2);
		if(bats[1]!=null) {
			bats[1].setSpeed(bats[0].getSpeed()+2);
		}
	}
	public void DecreaseBatSpeed()
	{
		bats[0].setSpeed(bats[0].getSpeed()-2);
		if(bats[1]!=null) {
			bats[1].setSpeed(bats[0].getSpeed()-2);
		}
	}
	public void ExtraLife()
	{
		lifes++;
	}
	public void DoubleBat()
	{
		doubleBatCountDown=512;
		if(bats[1]==null) {
			bats[1]=new Bat();
		}
		int offset = (bats[0].right()-bats[0].left())/4;
		bats[1].init(offsetX+1, offsetY+1, offset,squareSize*sizeX-offset,bats[0].left()+offset,(squareSize*sizeY)-squareSize*6, bats[0].getSizeX()/2, squareSize,bats[0].getSpeed());
	}
	public void SafetyWall()
	{	
		safetyWallBlink=0;
		safetyWallCountDown=512;
	}
	public void NewMissile()
	{
		missileCount++;
	}
	public void LargeBat()
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
	public void SmallBat()
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

	public void Explode(int x, int y, int sizeX, int sizeY)
	{
		Bomb b = new Bomb();
		b.init(cont,x,y,sizeX,sizeY);
		int tmp = b.explode(this,blocks,monsters,level);
		score += tmp;
		bombs.add(b);
	}
	boolean checkCollision(CollisionRect rc, int x, int y)
	{
		if(rc.left()<=x && rc.right()>=x) {			
			if(rc.top()<=y && rc.bottom()>=y) {
				return true;
			}
		}
		return false;
	}

	void handleMouseClicked(int x, int y) {
		/*if(checkCollision(exitButton,x,y)) {
			bExit = true;
		}*/
	}
	void handleMousePressed(int x, int y)
	{
	}
	void handleMouseReleased(int x, int y)
	{
	}
	
	boolean isExit()
	{
		return bExit;
	}
	
	void setCheatMode(boolean cheat)
	{
		cheatMode=cheat;
	}
	public void actionPerformed(ActionEvent e)
	{
		if(e.getSource()==exitButton) {
			bExit=true;
			container.remove(exitButton);
		}
	}
}
