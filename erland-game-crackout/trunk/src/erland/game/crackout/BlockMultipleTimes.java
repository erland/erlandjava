package erland.game.crackout;
import erland.game.*;
import erland.util.*;
import java.awt.*;

/**
 * Implements a block that has to be hit multiple times before it disappears
 */
class BlockMultipleTimes extends BlockSimple
{
	/** 
	 * Number of times the block must be hit before it disappers.
	 * This is a counter that will be decreased every time the block is hit
	 */
	protected int hitCount;
	/** Counter that handles the blinking every time the block is hit */
	protected int blinkCount;
	
	/**
	 * Creates an instance of the block
	 */
	public BlockMultipleTimes()
	{
		hitCount=0;
		blinkCount=0;
		dieCount=255;
	}
	public Object clone()
    	throws CloneNotSupportedException
    {
    	BlockMultipleTimes obj = new BlockMultipleTimes();
		obj.init(images, cont, sizeX, sizeY, posX, posY, color,hitCount);
		return obj;
    }

	/**
	 * Initialize block
	 * @param images Image handler object
	 * @param cont Reference to block container object
	 * @param sizeX Width of block (Number of squares)
	 * @param sizeY Height of block (Number of squares)
	 * @param posX X position of block (Square coordinates)
	 * @param posY Y position of block (Square coordinates)
	 * @param color Color of the block
	 * @param hitCount Number of times the block must be hit
	 */
	public void init(ImageHandlerInterface images, BlockContainerInterface cont, int sizeX, int sizeY, int posX, int posY, Color color, int hitCount)
	{
		init(images,cont, sizeX, sizeY, posX, posY,color);
		this.hitCount= hitCount;
		description = "Simple block, needs " + String.valueOf(hitCount) + " hits";
	}
	
	public int getScore()
	{
		return 10;
	}
	public boolean isRemovable()
	{
		return true;	
	}
	public void handleCollision(ActionInterface a)
	{
		blinkCount=255;
		hitCount--;
		if(hitCount<=0) {
			active=false;
		}
	}

	public void handleCollisionExplosive(ActionInterface a)
	{
		hitCount=0;
		active=false;
	}

	/** 
	 * Draw pattern that is visible as long as {@link #hitCount} is greater than 1
	 */
	protected void drawPattern(Graphics g, Color color, int brightness)
	{
		float hsb[] = new float[3];
		Color.RGBtoHSB(color.getRed(),color.getGreen(),color.getBlue(),hsb);
		if(brightness>255) {
			brightness-=256;
			hsb[1]=(float)((double)(256-brightness*2/3)/255.0);
			//hsb[1]=(float)hsb[1]*(2/3);
		}else {
			hsb[2]=(float)((double)brightness/255.0);
			hsb[2]=(float)hsb[2]*2/3;
		}
		g.setColor(Color.getHSBColor(hsb[0],hsb[1],hsb[2]));
		int offsetX = cont.getOffsetX();
		int offsetY = cont.getOffsetY();
		int squareSize = cont.getSquareSize();
		int x1, x2, y1, y2;
		for(int i=3;i<(squareSize*sizeX+squareSize*sizeY-3);i+=4) {
			if(i<=squareSize*sizeY) {
				x1 = offsetX+posX*squareSize+1;
				y1 = offsetY+posY*squareSize-2+i;
				x2 = offsetX+posX*squareSize+i-2;
				y2 = offsetY+posY*squareSize+1;
				g.drawLine(x1,y1,x2,y2);
			}else if(i>(squareSize*sizeX)) {
				x1 = offsetX+posX*squareSize+i-squareSize+1;
				y1 = offsetY+posY*squareSize+squareSize-2;
				x2 = offsetX+posX*squareSize+squareSize*sizeX-2;
				y2 = offsetY+posY*squareSize+1-squareSize*sizeX+i;
				g.drawLine(x1,y1,x2,y2);
			}else {
				x1 = offsetX+posX*squareSize+i-squareSize+1;
				y1 = offsetY+posY*squareSize+squareSize-2;
				x2 = offsetX+posX*squareSize+i-2;
				y2 = offsetY+posY*squareSize+1;
				g.drawLine(x1,y1,x2,y2);
			}
		}	
	}
	public void draw(Graphics g)
	{
		if(active) {
			if(blinkCount<=0) {
				drawBlock(g,color,0xFF);

				if(hitCount>1) {
					drawPattern(g,color, 0xFF);
				}
					
			}else {
				drawBlock(g,color,blinkCount+256);
				blinkCount-=20;
				if(hitCount>0) {
					drawPattern(g,color,blinkCount+256);
				}
			}
		}else {
			if(dieCount>0) {
				dieCount-=5;
				drawBlock(g,color,dieCount);
			}
		}
	}
	
}