package erland.game.crackout;
import erland.game.*;
import java.awt.*;
import java.util.*;

class Bomb
{
	int dieCount;
	Color color;
	int x;
	int y;
	int sizeX;
	int sizeY;
	int drawX;
	int drawY;
	int drawWidth;
	int drawHeight;
	BlockContainerInterface cont;
	boolean active;
	
	Bomb()
	{
		dieCount=255;
		color = Color.white;
	}

	void init(BlockContainerInterface cont, int x, int y, int sizeX, int sizeY)
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
	public void draw(Graphics g)
	{
		if(dieCount>10) {
			dieCount-=10;
			drawBomb(g,color,dieCount);
		}else {
			active=false;
		}
	}

	boolean checkCollision(CollisionRect rc, double deviation)
	{
		boolean bCollision = false;
		if((x+sizeX/2)>rc.left() && (x-sizeX/2)<rc.right()) {
			if((y+sizeY/2)>rc.top() && (y-sizeY/2)<rc.bottom()) {
				bCollision=true;
			}
		}
		return bCollision;
	}
	public int explode(ActionInterface a, Block blocks[], LinkedList monsters, double scoreMultiplier) 
	{
		int score=0;

		// Check collision with blocks
		if(blocks!=null) {
			for(int i=0;i<blocks.length;i++) {
				if(checkCollision(blocks[i],0)) {
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
				if(checkCollision(m,0)) {
					m.handleCollisionExplosive(a);
				}
			}
		}
		return score;
	}
	
	public boolean isActive()
	{
		return active;
	}
}