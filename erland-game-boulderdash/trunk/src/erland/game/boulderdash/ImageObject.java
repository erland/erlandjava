package erland.game.boulderdash;

import java.awt.*;

/**
 * Implements an image object which is shown as a bitmap
 * which collision can be checked with. The drawn bitmap
 * can also have a background that can be raisen or lowered
 */
class ImageObject
	implements CollisionRect
{
	/** X position */
	protected int x;
	/** Y position */
	protected int y;
	/** Width of the object */
	protected int width;
	/** Height of the object */
	protected int height;
	/** Image to draw */
	protected Image img;
	/** Horisontal drawing offset */
	protected int offsetX;
	/** Vertical drawing offset */
	protected int offsetY;
	/** Indicates if the background is rasied(true) or lowered(false) */
	protected boolean raised;
	/** The background color */
	protected Color background;
	
	/**
	 * Creates a new instance
	 * @param img Image to draw
	 * @param offsetX Horisontal drawing offset
	 * @param offsetY Vertical drawing offset
	 * @param x X position
	 * @param y Y position
	 * @param width Width of the object
	 * @param height Height of the object 
	 * @param raised Indicates if the background is raised(true) or lowered(false) 
	 * @param background Background color to use
	 */
	public ImageObject(Image img, int offsetX, int offsetY, int x, int y, int width, int height,boolean raised, Color background)
	{
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		this.img = img;
		this.offsetX = offsetX;
		this.offsetY = offsetY;
		this.raised = raised;
		this.background = background;
	}
	
	/**
	 * Set the background drawing state
	 * @param raised Indicates if the background is raised(true) or lowered(false) 
	 */
	public void setRaised(boolean raised)
	{
		this.raised = raised;
	}
	
	public int left()
	{
		return x;
	}
	public int right()
	{
		return x+width;
	}
	public int top()
	{
		return y;
	}
	public int bottom()
	{
		return y+height;
	}
	
	/**
	 * Draw the object
	 * @param g Graphics object to draw on
	 */
	public void draw(Graphics g)
	{
		g.setColor(background);
		if(raised) {
			g.fill3DRect(offsetX+x,offsetY+y,width,height,true);
		}else {
			g.fill3DRect(offsetX+x,offsetY+y,width,height,false);
		}
		g.drawImage(img,offsetX+1+x,offsetY+1+y,null);
	}
}
