package erland.game.crackout;
import erland.game.*;
import java.awt.*;

/**
 * Implements a block that creates a new monster when it is hit
 */
class BlockMonster extends BlockSimple
{
	/** Type of monster to create when the block is hit, see {@link Monster.MonsterType} */
	protected int monster;
	/** Image for the monster */
	Image img;
	
	/**
	 * Create a new instance of the block
	 */
	public BlockMonster()
	{
		super();
		monster = Monster.MonsterType.bounceBlock;
	}
	public Object clone()
    	throws CloneNotSupportedException
    {
    	BlockMonster obj = new BlockMonster();
		obj.init(environment, cont, sizeX, sizeY, posX, posY, color,monster);
		return obj;
    }

	/**
	 * Initialize block
	 * @param environment Game environment object
	 * @param cont Reference to block container object
	 * @param sizeX Width of block (Number of squares)
	 * @param sizeY Height of block (Number of squares)
	 * @param posX X position of block (Square coordinates)
	 * @param posY Y position of block (Square coordinates)
	 * @param color Color of the block
	 * @param monsterType Type of monster that should be created when the block is hit
	 */
	public void init(GameEnvironmentInterface environment, BlockContainerInterface cont, int sizeX, int sizeY, int posX, int posY, Color color, int monsterType)
	{
		init(environment, cont, sizeX, sizeY, posX, posY,color);
		monster = monsterType;
		description = "Monster block: ";
		switch(monster) {
			case Monster.MonsterType.bounceBlock:
				description+="Bounce";
				img = environment.getImageHandler().getImage("monster_bounceblock.gif");
				break;
			case Monster.MonsterType.bounceOnceBlock:
				description+="Bounce once";
				img = environment.getImageHandler().getImage("monster_bounceonceblock.gif");
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
		m.init(environment,offsetX,offsetY,squareSize*boardSizeX,squareSize*boardSizeY,squareSize*posX+squareSize*sizeX/2-squareSize/2,squareSize*posY,2*Math.PI*Math.random(),monster);
		a.newMonster(m);
	}

	/**
	 * Get the type of monster that will be created when the block is hit
	 * @return Type of monster that will be created when the block is hit, see {@link Monster.MonsterType}
	 */
	public int getMonster()
	{
		return monster;
	}
}
