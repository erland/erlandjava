package erland.game.pipes;
import erland.game.*;
import erland.util.*;
/**
 * Represents a block like following:
 * OOO
 * XXO
 * OXO
 */
class PipeBlockLeftDown extends PipeBlock
{
	ImageHandlerInterface images;
	public PipeBlockLeftDown(ImageHandlerInterface images) 
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
	    
		parts[0][1] = new PipePartLeftRight(images);
		parts[1][1] = new PipePartLeftDown(images);
		parts[1][2] = new PipePartUpDown(images);
		
		super.init(cont,parts,x,y);
	}
}
