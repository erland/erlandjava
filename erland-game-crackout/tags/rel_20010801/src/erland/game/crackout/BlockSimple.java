package erland.game.crackout;
import erland.game.*;
import java.awt.*;

class BlockSimple extends Block
{
	int dieCount;
	Color color;
	
	/*class ColorType {
		public static final int red=1;
		public static final int green=2;
		public static final int blue=3;
		public static final int yellow=4;
		public static final int white=5;
	}*/
	BlockSimple()
	{
		dieCount=255;
		color = Color.blue;
		this.description = "Simple block";
	}
	public Object clone()
    	throws CloneNotSupportedException
    {
    	BlockSimple obj = new BlockSimple();
		obj.init(images, cont, sizeX, sizeY, posX, posY, color);
		return obj;
    }
	void init(ImageHandlerInterface images, BlockContainerInterface cont, int sizeX, int sizeY, int posX, int posY, Color color)
	{
		init(images, cont , sizeX, sizeY, posX, posY);
		this.color = color;
	}
	public int getScore()
	{
		return 10;
	}
	public boolean isRemovable()
	{
		return true;	
	}
	public void drawBlock(Graphics g, Color color, int brightness)
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
		g.fillRect(offsetX+posX*squareSize, offsetY+posY*squareSize, squareSize*sizeX-1, squareSize*sizeY-1);
		hsb[2]=darkness/3;
		g.setColor(Color.getHSBColor(hsb[0],hsb[1],hsb[2]));
		g.drawRect(offsetX+posX*squareSize, offsetY+posY*squareSize, squareSize*sizeX-1, squareSize*sizeY-1);
		
		hsb[2]=darkness*2/3;
		g.setColor(Color.getHSBColor(hsb[0],hsb[1],hsb[2]));
		g.drawRect(offsetX+1+posX*squareSize, offsetY+1+posY*squareSize, squareSize*sizeX-3, squareSize*sizeY-3);
	}
	public void draw(Graphics g)
	{
		if(active) {
			drawBlock(g,color,0xFF);
		}else {
			if(dieCount>10) {
				dieCount-=10;
				drawBlock(g,color,dieCount);
			}
		}
	}
	public void setColor(Color color)
	{
		this.color = color;
	}
	public Color getColor()
	{
		return this.color;
	}
}