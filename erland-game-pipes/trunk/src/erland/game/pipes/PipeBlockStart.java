package erland.game.pipes;
import erland.game.*;
import erland.util.*;
/**
 * Represents a block with a well like following:
 * O?O
 * ?*?
 * O?O
 */
class PipeBlockStart extends PipeBlock
{
	/** Image handler object */
	ImageHandlerInterface images;
	/** Indicates which sides of the block that are open for water */
	int openDirection;
		
	/**
	 * Creates a new pipe block
	 * @param images Image handler object
	 * @param openDirection Indicates which sides of the block that are open for water
	 */
	public PipeBlockStart(ImageHandlerInterface images, int openDirection) 
	{
		this.images = images;
		this.openDirection = openDirection;
	}
	public void init(BlockContainerInterface cont, int x, int y)
	{
		
		PipePart[][] parts = new PipePart[size][size];
		for (int i=0; i<size; i++) {
			parts[i] = new PipePart[size];
			for (int j=0; j<size; j++) {
				parts[i][j] = new PipePartNone(images);
		    }
	    }
	    
		parts[1][1] = new PipePartWell(images,true);
		int waterStart=0;
		switch(openDirection) {
			case Direction.LEFT:
				parts[0][1] = new PipePartLeftRight(images);
				waterStart=Direction.RIGHT;
				break;
			case Direction.RIGHT:
				parts[2][1] = new PipePartLeftRight(images);
				waterStart=Direction.LEFT;
				break;
			case Direction.UP:
				parts[1][0] = new PipePartUpDown(images);
				waterStart=Direction.DOWN;
				break;
			case Direction.DOWN:
				parts[1][2] = new PipePartUpDown(images);
				waterStart=Direction.UP;
				break;
			default:
				break;
		}
		
		super.init(cont,parts,x,y);
		initWater(1,1,waterStart);
	}
	protected boolean addFilledPart(int x, int y)
	{
		if(parts[x][y] instanceof PipePartWell) {
			return false;
		}else {
			return super.addFilledPart(x,y);
		}
	}
	protected int getScore(int x, int y)
	{
		if(parts[x][y] instanceof PipePartWell) {
			return 0;
		}else {
			return super.getScore(x,y);
		}
	}
	
	/**
	 * Check which direction the well is open against
	 * @return The direction which the well is open against
	 */
	public int getOpenDirection()
	{
		return openDirection;
	}
}
