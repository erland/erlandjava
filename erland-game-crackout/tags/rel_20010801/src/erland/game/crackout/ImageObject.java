package erland.game.crackout;

import java.awt.*;

class ImageObject
	implements CollisionRect
{
	int x;
	int y;
	int width;
	int height;
	Image img;
	int offsetX;
	int offsetY;
	boolean raised;
	Color background;
	
	ImageObject(Image img, int offsetX, int offsetY, int x, int y, int width, int height,boolean raised, Color background)
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
	
	public void handleCollision(ActionInterface a)
	{
		// Do nothing		
	}
	void draw(Graphics g)
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
