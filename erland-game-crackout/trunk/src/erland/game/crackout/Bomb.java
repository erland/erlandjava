package erland.game.crackout;
import erland.game.*;
import java.awt.*;
import java.util.*;

/**
 * Implements a bomb 
 */
class Bomb
{
	/** Counter that handles the explosion fadeout */
	protected int dieCount;
	/** Color of the explosion */
	protected Color color;
	/** X position of the explosion (Pixel coordinate) */
	protected int x;
	/** Y position of the explosion (Pixel coordinate) */
	protected int y;
	/** Width of the explosion (Number of pixels) */
	protected int sizeX;
	/** Height of the explosion (Number of pixels) */
	protected int sizeY;
	/** Leftmost drawing position of the explosion square */
	protected int drawX;
	/** Topmost drawing position of the explosion square */
	protected int drawY;
	/** Drawing width of the explosion square */
	protected int drawWidth;
	/** Drawing height of the explosion square */
	protected int drawHeight;
	/** Block container that the bomb resides in */
	protected BlockContainerInterface cont;
	/** Indicates if the bomb is visible or not */
	protected boolean active;
	
	/**
	 * Creates a new bomb
	 */
	public Bomb()
	{
		dieCount=255;
		color = Color.white;
	}

	/**
	 * Initialize the object
	 * @param cont Reference to block container
	 * @param x X position of the explosion (Pixel coordinate)
	 * @param y Y position of the explosion (Pixel coordinate)
	 * @param sizeX Width of the explosion (Number of pixels)
	 * @param sizeY Height of the explosion (Number of pixels)
	 */
	public void init(BlockContainerInterface cont, int x, int y, int sizeX, int sizeY)
	{
		this.cont = cont;
		this.x = x;
		this.y = y;
		this.sizeX = sizeX;
		this.sizeY = sizeY;	
		this.active = true;	

		drawX = x-sizeX/2;
		drawY = y-sizeY/2;
		drawWidth = sizeX;
		drawHeight = sizeY;
		
		if(drawX<0) {
			drawWidth+=drawX;
			drawX=drawWidth/2;
		}
		int contWidth = cont.getSizeX()*cont.getSquareSize();
		if(drawX>contWidth) {
			drawWidth-=(drawX-contWidth);
			drawX = contWidth-drawWidth/2;
		}
		if(drawY<0) {
			drawHeight+=drawY;
			drawY=drawHeight/2;
		}
		int contHeight = cont.getSizeY()*cont.getSquareSize();
		if(drawY>contHeight) {
			drawHeight-=(drawY-contHeight);
			drawY = contHeight-drawHeight/2;
		}
	}
	/**
	 * Draw the explosion flash
	 * @param g Graphics object to draw on
	 * @param color The color of the explosion
	 * @param brightness The brightness of the explosion square(0=black, 255=normal, 511=white)
	 */
	public void drawBomb(Graphics g, Color color, int brightness)
	{
		float hsb[] = new float[3];
		Color.RGBtoHSB(color.getRed(), color.getGreen(), color.getBlue(),hsb);

		if(brightness>255) {
			brightness-=256;
			hsb[1]=(float)((double)(256-brightness)/255.0);
		}else {
			hsb[2]=(float)((double)brightness/255.0);
		}
		Color c = Color.getHSBColor(hsb[0],hsb[1],hsb[2]);
		g.setColor(c);
		Color.RGBtoHSB(c.getRed(), c.getGreen(),c.getBlue(),hsb);
		float darkness = hsb[2];
		int offsetX = cont.getOffsetX();
		int offsetY = cont.getOffsetY();
		int squareSize = cont.getSquareSize();
		g.fillRect(offsetX+drawX, offsetY+drawY, drawWidth, drawHeight);
	}
	
	/**
	 * Draws the bomb/explosion
	 * @param g Graphics object to draw on
	 */
	public void draw(Graphics g)
	{
		if(dieCount>10) {
			dieCount-=10;
			drawBomb(g,color,dieCount);
		}else {
			active=false;
		}
	}

	/**
	 * Checks if the specified rectangle is hit by the explosion
	 * @param rc The rectangle which will be checked if it is hit
	 * @return true/false (Hit/Not hit)
	 */
	protected boolean checkCollision(CollisionRect rc)
	{
		boolean bCollision = false;
		if((x+sizeX/2)>rc.left() && (x-sizeX/2)<rc.right()) {
			if((y+sizeY/2)>rc.top() && (y-sizeY/2)<rc.bottom()) {
				bCollision=true;
			}
		}
		return bCollision;
	}
	/**
	 * explode the bomb and takes care of any hits on blocks or monster
	 * @param a Reference to an object that implements all collision actions
	 * @param blocks An array of all blocks that should be checked if they are hit
	 * @param monsters A list of all monster that should be checked if they are hit
	 * @param scoreMultiplier All score increases due to the explosion should be multiplied with this number
	 * @return The score should be increased with this number
	 */
	public int explode(ActionInterface a, Block blocks[], LinkedList monsters, double scoreMultiplier) 
	{
		int score=0;

		// Check collision with blocks
		if(blocks!=null) {
			for(int i=0;i<blocks.length;i++) {
				if(checkCollision(blocks[i])) {
					blocks[i].handleCollisionExplosive(a);
					score += scoreMultiplier*blocks[i].getScore();
				}
			}
		}

		// Check collision with monsters
		if(monsters!=null) {
			ListIterator it = monsters.listIterator();
			while(it.hasNext()) {
				Monster m = (Monster)(it.next());
				if(checkCollision(m)) {
					m.handleCollisionExplosive(a);
				}
			}
		}
		return score;
	}
	
	/**
	 * Checks if the explosion is finished or not
	 * @return true/false (Still active/Finished)
	 */
	public boolean isActive()
	{
		return active;
	}
}