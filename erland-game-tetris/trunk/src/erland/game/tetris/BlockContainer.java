package erland.game.tetris;

import erland.util.*;
import java.awt.*;
import java.math.*;

public class BlockContainer
{
	BlockContainerData mainContainer;
	BlockContainerData previewContainer;
	BlockContainerData titleContainer;
	
	int squareSize;
	int offsetX, offsetY;
	int spaceBetweenSquares;
	Block block;
	Block blockNext;
	boolean bEnd;
	boolean bStarted;
	int endBlink;
	int highScore;
	int savedHighScore;
	int score;
	int level;
	int rowsThisLevel;
	final int ROWS_PER_LEVEL=10;
	int updateCounter;
	final int MAX_LEVEL=9;
	final int MAX_SPEED=9;
	final int END_BLINK_SPEED=10;
	ParameterValueStorageInterface cookies;
	
	class BlockContainerData implements BlockMatrix
	{
		boolean matrix[][];
		Color colorMatrix[][];
		BlockContainerData(int sizeX, int sizeY) 
		{
			matrix = new boolean[sizeX][sizeY];
			colorMatrix = new Color[sizeX][sizeY];
		}
		void clear()
		{
			for(int y=0;y<getHeight();y++) {
				clearRow(y);
			}
		}
		
		void clearRow(int y)
		{
			for(int x=0;x<getWidth();x++) {
				matrix[x][y]=false;
			}
		}
		
		public Color getColor(int x, int y)
		{
			if(x>=0 && x<matrix.length && y<matrix[0].length && y>=0) {
				return colorMatrix[x][y];
			}else {
				return Color.black;
			}
		}
		
		public int getWidth()
		{
			return matrix.length;
		}
		public int getHeight()
		{
			return matrix[0].length;
		}
		public boolean isUsed(int x, int y)
		{
			if(x>=0 && x<matrix.length && y<matrix[0].length) {
				if(y>=0) {
					return matrix[x][y];
				}else {
					return false;
				}
			}else {
				return true;
			}
		}
		public void setUsed(int x, int y, Color c)
		{
			if(x>=0 && x<matrix.length && y>=0 && y<matrix[0].length) {
				matrix[x][y]=true;
				colorMatrix[x][y]=c;
			}
		}
		public void setUnused(int x, int y)
		{
			if(x>=0 && x<matrix.length && y>=0 && y<matrix[0].length) {
				matrix[x][y] = false;
			}
		}
	}


	public BlockContainer(ParameterValueStorageInterface cookies, int offsetX, int offsetY, int sizeX,int sizeY, int squareSize, int spaceBetweenSquares)
	{
		this.cookies = cookies;
		mainContainer = new BlockContainerData(sizeX,sizeY);
		previewContainer = new BlockContainerData(4,2);
		titleContainer = new BlockContainerData(34,5);
		titleContainer.clear();
		makeTitle(titleContainer);
		this.offsetX = offsetX;
		this.offsetY = offsetY;
		this.squareSize = squareSize;
		this.spaceBetweenSquares = spaceBetweenSquares;
		init();
		bStarted=false;
		if(cookies!=null) {
			try {
				highScore = Integer.valueOf(cookies.getParameter("highscore")).intValue();
			}catch(NumberFormatException e) {
				highScore = 0;
			}
		}else {
			highScore = 0;
		}
		savedHighScore = highScore;
	}
	void makeTitle(BlockMatrix m) {
		//XXXXX-XXXXX-XXXXX-XXXX----X----XXXX
		//--X---X-------X---X---X---X---X----
		//--X---XXXX----X---XXXX----X----XXX-
		//--X---X-------X---X--X----X-------X
		//--X---XXXXX---X---X---X---X---XXXX-

		// "T"
		Color c = Color.red;
		m.setUsed(0,0,c);
		m.setUsed(1,0,c);
		m.setUsed(2,0,c);
		m.setUsed(3,0,c);
		m.setUsed(4,0,c);
		m.setUsed(2,1,c);
		m.setUsed(2,2,c);
		m.setUsed(2,3,c);
		m.setUsed(2,4,c);
		// "E"
		m.setUsed(6,0,c);
		m.setUsed(7,0,c);
		m.setUsed(8,0,c);
		m.setUsed(9,0,c);
		m.setUsed(10,0,c);
		m.setUsed(6,1,c);
		m.setUsed(6,2,c);
		m.setUsed(7,2,c);
		m.setUsed(8,2,c);
		m.setUsed(9,2,c);
		m.setUsed(6,3,c);
		m.setUsed(6,4,c);
		m.setUsed(7,4,c);
		m.setUsed(8,4,c);
		m.setUsed(9,4,c);
		m.setUsed(10,4,c);
		// "T"
		m.setUsed(12,0,c);
		m.setUsed(13,0,c);
		m.setUsed(14,0,c);
		m.setUsed(15,0,c);
		m.setUsed(16,0,c);
		m.setUsed(14,1,c);
		m.setUsed(14,2,c);
		m.setUsed(14,3,c);
		m.setUsed(14,4,c);
		// "R"
		m.setUsed(18,0,c);
		m.setUsed(18,1,c);
		m.setUsed(18,2,c);
		m.setUsed(18,3,c);
		m.setUsed(18,4,c);
		m.setUsed(19,0,c);
		m.setUsed(20,0,c);
		m.setUsed(21,0,c);
		m.setUsed(22,1,c);
		m.setUsed(19,2,c);
		m.setUsed(20,2,c);
		m.setUsed(21,2,c);
		m.setUsed(21,3,c);
		m.setUsed(22,4,c);
		// "I"
		m.setUsed(25,0,c);
		m.setUsed(25,1,c);
		m.setUsed(25,2,c);
		m.setUsed(25,3,c);
		m.setUsed(25,4,c);
		// "S"
		m.setUsed(30,0,c);
		m.setUsed(31,0,c);
		m.setUsed(32,0,c);
		m.setUsed(33,0,c);
		m.setUsed(29,1,c);
		m.setUsed(30,2,c);
		m.setUsed(31,2,c);
		m.setUsed(32,2,c);
		m.setUsed(33,3,c);
		m.setUsed(29,4,c);
		m.setUsed(30,4,c);
		m.setUsed(31,4,c);
		m.setUsed(32,4,c);
	}
	public void init()
	{
		bEnd = false;
		bStarted=true;
		score = 0;
		level=1;
		rowsThisLevel=0;
		updateCounter=0;
		endBlink=0;
		mainContainer.clear();
		previewContainer.clear();
		block=null;
		blockNext=null;
	}
	public void newGame()
	{
		if(bEnd || !bStarted) {
			init();
		}
	}
	
	public void draw(Graphics g)
	{
		int rightColumnX = offsetX + mainContainer.getWidth()*(squareSize+spaceBetweenSquares)+10;
		int rightColumnY = offsetY;
		drawContainer(g,mainContainer,offsetX,offsetY, squareSize,spaceBetweenSquares,1,Color.blue,Color.darkGray);
		drawContainer(g,previewContainer,offsetX + mainContainer.getWidth()*(squareSize+spaceBetweenSquares)+10,rightColumnY, squareSize, spaceBetweenSquares,1,Color.blue,Color.darkGray);
		drawContainer(g,titleContainer,rightColumnX,
						offsetY + mainContainer.getHeight()*(squareSize+spaceBetweenSquares)-titleContainer.getHeight()*(squareSize/2+spaceBetweenSquares)-20,squareSize/2,spaceBetweenSquares,0,Color.blue,Color.black);
		g.drawString("by Erland Isaksson",rightColumnX+147,offsetY + mainContainer.getHeight()*(squareSize+spaceBetweenSquares));
		rightColumnY += offsetY+previewContainer.getHeight()*(squareSize+spaceBetweenSquares)+20;
		if(score>highScore) {
			highScore=score;
		}
		String scoreString = String.valueOf(highScore);
		g.setColor(Color.white);
		g.drawString("LEVEL: "+String.valueOf(level),rightColumnX, rightColumnY);
		rightColumnY+=20;
		g.drawString("HIGHSCORE:",rightColumnX, rightColumnY);
		rightColumnY+=20;
		g.drawString(scoreString,rightColumnX,rightColumnY);
		rightColumnY+=20;
		scoreString = String.valueOf(score);
		g.drawString("SCORE:",rightColumnX,rightColumnY);
		rightColumnY+=20;
		g.drawString(scoreString,rightColumnX,rightColumnY);
		rightColumnY+=20;
		if(bEnd) {
			if(savedHighScore<highScore) {
				if(cookies!=null) {
					cookies.setParameter("highscore",String.valueOf(highScore));
				}
				savedHighScore = highScore;
			}
			if(endBlink<END_BLINK_SPEED) {
				if(level<=MAX_LEVEL) {
					g.drawString("GAME OVER", rightColumnX,rightColumnY);
					rightColumnY+=20;
					rightColumnY+=20;
					g.drawString("Press space for new game", rightColumnX, rightColumnY);
					rightColumnY+=20;
				}else {
					g.drawString("* CONGRATULATIONS *", rightColumnX, rightColumnY);
					rightColumnY+=20;
					g.drawString(" You have finished ", rightColumnX, rightColumnY);
					rightColumnY+=20;
					g.drawString("     the game      ", rightColumnX, rightColumnY);
					rightColumnY+=20;
					rightColumnY+=20;
					g.drawString("Press space for new game", rightColumnX, rightColumnY);
					rightColumnY+=20;
					
				}		
				endBlink++;
			}else {
				if(endBlink<(2*END_BLINK_SPEED)) {
					endBlink++;
				}else {
					endBlink=0;
				}
			}
		}
		if(!bStarted) {
			if(endBlink<END_BLINK_SPEED) {
				g.drawString("Press space for new game",rightColumnX, rightColumnY);
				rightColumnY+=20;
				endBlink++;
			}else {
				if(endBlink<(2*END_BLINK_SPEED)) {
					endBlink++;
				}else {
					endBlink=0;
				}
			}
		}
	}

	public void drawContainer(Graphics g, BlockMatrix m, int offsetX, int offsetY, int squareSize, int spaceBetweenSquares, int borderSize, Color borderColor, Color gridColor)
	{
		// Draw block container
		if(borderSize>=0) {
			g.setColor(borderColor);
			g.fillRect(offsetX, offsetY, borderSize, (squareSize+spaceBetweenSquares)*m.getHeight()-spaceBetweenSquares+borderSize);
			g.fillRect(offsetX, offsetY, (squareSize+spaceBetweenSquares)*m.getWidth()-spaceBetweenSquares+borderSize, borderSize);
			g.fillRect(offsetX+borderSize+(squareSize+spaceBetweenSquares)*m.getWidth()-spaceBetweenSquares, offsetY,borderSize,(squareSize+spaceBetweenSquares)*m.getHeight()-spaceBetweenSquares+borderSize*2);
			g.fillRect(offsetX, offsetY+borderSize+(squareSize+spaceBetweenSquares)*m.getHeight()-spaceBetweenSquares,(squareSize+spaceBetweenSquares)*m.getWidth()-spaceBetweenSquares+borderSize*2,borderSize);
		}
		if(spaceBetweenSquares>0) {
			g.setColor(gridColor);
			for(int x=1;x<m.getWidth();x++) {
				g.fillRect(offsetX + borderSize + x*(squareSize+spaceBetweenSquares)-spaceBetweenSquares,
							offsetY + borderSize,
							spaceBetweenSquares,
							m.getHeight()*(squareSize+spaceBetweenSquares)-spaceBetweenSquares);
			}
			for(int y=1;y<m.getHeight();y++) {
				g.fillRect(offsetX + borderSize,
							offsetY + borderSize + y*(squareSize+spaceBetweenSquares)-spaceBetweenSquares,
							m.getWidth()*(squareSize+spaceBetweenSquares)-spaceBetweenSquares,
							spaceBetweenSquares);
			}
		}
		for(int x=0;x<m.getWidth();x++) {
			for(int y=0;y<m.getHeight();y++) {
				if(m.isUsed(x,y)) {
					g.setColor(m.getColor(x,y));
					g.fillRect(offsetX +borderSize+ x*(squareSize+spaceBetweenSquares),offsetY +borderSize+ y*(squareSize+spaceBetweenSquares),squareSize, squareSize);
				}
			}
		}
	}	
	public void removeCompletedRows()
	{
		for(int y=mainContainer.getHeight()-1;y>=0;y--) {
			boolean bCompleted = true;
			boolean bCompletedWithSameColor=true;
			Color c=mainContainer.getColor(0,y);
			for(int x=0;x<mainContainer.getWidth();x++) {
				if(!mainContainer.isUsed(x,y)) {
					bCompleted = false;
				}else {
					if(c==null || !c.equals(mainContainer.getColor(x,y))) {
						bCompletedWithSameColor=false;
					}
				}
			}
			if(bCompleted) {
				for(int i=y;i>0;i--) {
					for(int j=0;j<mainContainer.getWidth();j++) {
						if(mainContainer.isUsed(j,i-1)) {
							mainContainer.setUsed(j,i,mainContainer.getColor(j,i-1));
						}else {
							mainContainer.setUnused(j,i);
						}
					}
				}
				mainContainer.clearRow(0);
				y++;
				if(!bCompletedWithSameColor) {
					score+=(10*level);
				}else {
					score+=(100*level);
				}
				rowsThisLevel++;
			}
		}
	}
	
	public void update() {
		if(!bEnd && bStarted) {
			updateCounter++;
			if(updateCounter>(MAX_SPEED-level)) {
				updateCounter=0;
				if(this.block==null) {
					if(this.blockNext==null) {
						blockNext = newBlock();
					}
					block = blockNext;
					previewContainer.clear();
					blockNext = newBlock();
					blockNext.init(1,0,0);
					blockNext.moveDown(previewContainer);
					block.init(mainContainer.getWidth()/2,-1,0);
					if(!block.moveDown(mainContainer)) {
						removeCompletedRows();
						bEnd = true;
					}
				}else {
					if(!block.moveDown(mainContainer)) {
						removeCompletedRows();
						block = null;
					}
				}
				if(rowsThisLevel>=ROWS_PER_LEVEL) {
					rowsThisLevel-=ROWS_PER_LEVEL;
					score+=200*level;
					level++;
				}
				if(level>MAX_LEVEL) {
					bEnd=true;
				}
			}
		}
	}

	Block newBlock()
	{
		Block block;
		switch((int)(Math.random()*7+1.0)) {
			case 1:
				block = new Block1();
				break;
			case 2:
				block = new Block2();
				break;
			case 3:
				block = new Block3();
				break;
			case 4:
				block = new Block4();
				break;
			case 5:
				block = new Block5();
				break;
			case 6:
				block = new Block6();
				break;
			case 7:
			default:
				block = new Block7();
				break;
		}
		return block;
	}	
	
	public boolean moveLeft()
	{
		if(block != null) {
			return block.moveLeft(mainContainer);
		}
		return false;
	}

	public boolean moveRight()
	{
		if(block != null) {
			return block.moveRight(mainContainer);
		}
		return false;
	}

	public boolean rotateRight()
	{
		if(block != null) {
			return block.rotateRight(mainContainer);
		}
		return false;
	}
	public boolean moveDown()
	{
		if(block != null) {
			return block.moveDown(mainContainer);
		}
		return false;
	}
}