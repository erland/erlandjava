package erland.game.pipes;
import erland.game.*;
import erland.util.*;
/**
 * Represents a block like following:
 * O?O
 * ?X?
 * O?O
 */
class PipeBlockPool extends PipeBlock
{
	/** Image handler object */
	ImageHandlerInterface images;
	/** Indicates which sides that are open */
	int openDirection;
	
	/**
	 * Creates a new pipe block
	 * @param images Image handler object
	 * @param openDirection Indicates which part that is open
	 */
	public PipeBlockPool(ImageHandlerInterface images, int openDirection) 
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
	    
	    if(openDirection==Direction.LEFT) {
			parts[0][1] = new PipePartLeftRight(images);
		}
		if(openDirection==Direction.RIGHT) {
			parts[2][1] = new PipePartLeftRight(images);
		}
	    if(openDirection==Direction.UP) {
			parts[1][0] = new PipePartUpDown(images);
		}
	    if(openDirection==Direction.DOWN) {
			parts[1][2] = new PipePartUpDown(images);
		}
		parts[1][1] = new PipePartPool(images);
		
		super.init(cont,parts,x,y);
	}
	
	/**
	 * Check which direction the pool is open against
	 * @return The direction which the pool is open against
	 */
	public int getOpenDirection() {
		return openDirection;
	}

	protected int getScore(int x, int y)
	{
		if(x==1 && y==1) {
			return super.getScore(x,y)*((PipePartPool)parts[x][y]).getLevelsFilled();
		}else {
			return super.getScore(x,y);
		}
	}
}
