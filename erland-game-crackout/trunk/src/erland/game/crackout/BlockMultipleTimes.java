package erland.game.crackout;
import erland.game.*;
import java.awt.*;

class BlockMultipleTimes extends BlockSimple
{
	int hitCount;
	int blinkCount;
	
	BlockMultipleTimes()
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

	void init(ImageHandlerInterface images, BlockContainerInterface cont, int sizeX, int sizeY, int posX, int posY, Color color, int hitCount)
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

	void drawPattern(Graphics g, Color color, int brightness)
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