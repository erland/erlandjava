package erland.game.crackout;

import java.awt.*;

class Bat
	implements CollisionRect
{
	int x;
	int y;
	int leftLimit;
	int rightLimit;
	int sizeX;
	int sizeY;
	int speed;
	int offsetX;
	int offsetY;
	int wantedSizeX;
	
	public void init(int offsetX, int offsetY, int leftLimit, int rightLimit, int x, int y, int sizeX, int sizeY, int speed)
	{
		this.x = x;
		this.y = y;
		this.leftLimit = leftLimit;
		this.rightLimit = rightLimit;
		this.sizeX = sizeX;
		this.sizeY = sizeY;
		this.speed = speed;
		this.offsetX = offsetX;
		this.offsetY = offsetY;
		this.wantedSizeX = sizeX;
	}
	
	public int left()
	{
		return x;
	}
	
	public int right()
	{
		return x+sizeX;
	}
	
	public int top()
	{
		return y;
	}
	
	public int bottom()
	{
		return y+sizeY;
	}
		
	public boolean moveRight()
	{
		x+=speed;
		if(x>(rightLimit-sizeX)) {
			x = rightLimit-sizeX;
			return false;
		}
		return true;
	}
	
	public boolean moveLeft()
	{
		x-=speed;
		if(x<leftLimit) {
			x=leftLimit;
			return false;
		}
		return true;
	}
	public void draw(Graphics g)
	{
		if(wantedSizeX!=sizeX) {
			changeSize();
		}
		g.setColor(Color.gray);
		g.fillRect(offsetX+x,offsetY+y,sizeX,sizeY);
		g.setColor(new Color(0x666666));
		g.drawRect(offsetX+x+1,offsetY+y+1,sizeX-3,sizeY-3);
		g.setColor(new Color(0x444444));
		g.drawRect(offsetX+x,offsetY+y,sizeX-1,sizeY-1);
	}
	public void handleCollision(ActionInterface a) {
	}
	void setSpeed(int speed)
	{
		if(speed>=1) {
			if(speed>16) {
				speed=16;
			}
			this.speed = speed;
		}
	}
	
	int getSpeed()
	{
		return this.speed;
	}
	
	int getSizeX()
	{
		return this.sizeX;
	}
	
	void changeSize()
	{
		if(wantedSizeX>sizeX) {
			if(wantedSizeX>(sizeX+1)) {
				sizeX+=2;
				x--;
			}else {
				sizeX++;
			}
		}else if(wantedSizeX<sizeX) {
			if(wantedSizeX<(sizeX-1)) {
				sizeX-=2;
				x++;
			}else {
				sizeX--;
			}
		}
		x = x-(sizeX-this.sizeX)/2;
		this.sizeX = sizeX;
		if(x>(rightLimit-sizeX)) {
			x = rightLimit-sizeX;
		}
		if(x<leftLimit) {
			x=leftLimit;
		}
	}
	void setSizeXFast(int sizeX)
	{
		this.sizeX = sizeX;
		wantedSizeX = sizeX;
	}

	void setSizeX(int sizeX)
	{
		wantedSizeX = sizeX;
	}
	void setLimit(int left, int right)
	{
		leftLimit = left;
		rightLimit = right;
		if(x>(rightLimit-sizeX)) {
			x = rightLimit-sizeX;
		}
		if(x<leftLimit) {
			x=leftLimit;
		}
	}
}
