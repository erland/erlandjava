package erland.game.pipes;
import erland.game.*;
import erland.util.*;
/**
 * Represents a block with a well like following:
 * OOO
 * O*X
 * OOO
 */
class PipeBlockStartRight extends PipeBlock
{
	ImageHandlerInterface images;
	public PipeBlockStartRight(ImageHandlerInterface images) 
	{
		this.images = images;
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
	    
		parts[1][1] = new PipePartWell(images);
		parts[2][1] = new PipePartLeftRight(images);
		
		super.init(cont,parts,x,y);
	}
}
