package erland.game.crackout;
import erland.game.*;
import java.awt.*;

class BlockMonster extends BlockSimple
{
	int monster;
	
	BlockMonster()
	{
		super();
		monster = Monster.MonsterType.bounceBlock;
	}
	public Object clone()
    	throws CloneNotSupportedException
    {
    	BlockMonster obj = new BlockMonster();
		obj.init(images, cont, sizeX, sizeY, posX, posY, color,monster);
		return obj;
    }

	void init(ImageHandlerInterface images, BlockContainerInterface cont, int sizeX, int sizeY, int posX, int posY, Color color, int monsterType)
	{
		init(images, cont, sizeX, sizeY, posX, posY,color);
		monster = monsterType;
		description = "Monster block: ";
		switch(monster) {
			case Monster.MonsterType.bounceBlock:
				description+="Bounce";
				break;
			case Monster.MonsterType.bounceOnceBlock:
				description+="Bounce once";
				break;
			default:
				break;
		}
	}

	public void draw(Graphics g)
	{
		super.draw(g);
		if(active || dieCount>10) {
			//float hsb[] = new float[3];
			//Color.RGBtoHSB(color.getRed(),color.getBlue(),color.getGreen(),hsb);
			//hsb[2]=(float)dieCount/255;
			//Color c=Color.getHSBColor(hsb[0],hsb[1],hsb[2]);
			//Color.RGBtoHSB(c.getRed(),c.getBlue(),c.getGreen(),hsb);
			//hsb[2]=(float)hsb[2]*3/4;
			int offsetX = cont.getOffsetX();
			int offsetY = cont.getOffsetY();
			int squareSize = cont.getSquareSize();
			//g.setColor(Color.getHSBColor(hsb[0],hsb[1],hsb[2]));
			//g.fillRect(offsetX+posX*squareSize+(squareSize*sizeX)/2-squareSize/4-2,offsetY+posY*squareSize+squareSize/2-squareSize/4-2,squareSize/2+4,squareSize/2+4);
			//g.setColor(Color.black);
			//g.fillRect(offsetX+posX*squareSize+(squareSize*sizeX)/2-squareSize/4,offsetY+posY*squareSize+squareSize/2-squareSize/4,squareSize/2,squareSize/2);
			Image img=null;
			switch(monster) {
				case Monster.MonsterType.bounceBlock:
					img = images.getImage(images.MONSTER_BOUNCEBLOCK);
					break;
				case Monster.MonsterType.bounceOnceBlock:
					img = images.getImage(images.MONSTER_BOUNCEONCEBLOCK);
					break;
				default:
					img = images.getImage(images.MONSTER_BOUNCEBLOCK);
					break;
			}
			if(img!=null && active) {
				g.drawImage(img,offsetX+posX*squareSize+(squareSize*sizeX)/2-squareSize/4-1,offsetY+posY*squareSize+3,null);
			}
		}
	}
	
	public void handleCollision(ActionInterface a)
	{
		active=false;
		Monster m = new Monster();
		int offsetX = cont.getOffsetX();
		int offsetY = cont.getOffsetY();
		int squareSize = cont.getSquareSize();
		int boardSizeX = cont.getSizeX();
		int boardSizeY = cont.getSizeY();
		m.init(images,offsetX,offsetY,squareSize*boardSizeX,squareSize*boardSizeY,squareSize*posX+squareSize*sizeX/2-squareSize/2,squareSize*posY,2*Math.PI*Math.random(),monster);
		a.NewMonster(m);
	}

	int getMonster()
	{
		return monster;
	}
}
