package erland.game.pipes;
import erland.game.*;
import erland.util.*;
/**
 * Represents a block like following which can't be moved:
 * OOO
 * OOO
 * OOO
 */
class PipeBlockUnmovable extends PipeBlock
{
	/** Image handler object */
	ImageHandlerInterface images;
	
	/**
	 * Creates a new pipe block
	 * @param images Image handler object
	 */
	public PipeBlockUnmovable(ImageHandlerInterface images) 
	{
		this.images = images;
	}
	public void init(BlockContainerInterface cont, int x, int y)
	{
		
		PipePart[][] parts = new PipePart[size][size];
		for (int i=0; i<size; i++) {
			parts[i] = new PipePart[size];
			for (int j=0; j<size; j++) {
				parts[i][j] = new PipePartNone(images,"stone.gif");
		    }
	    }
	    
		super.init(cont,parts,x,y);
	}
	protected boolean isMovable()
	{
		return false;
	}
	public boolean isWaterFilled()
	{
		return true;
	}
}
