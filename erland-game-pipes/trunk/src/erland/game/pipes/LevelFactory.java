package erland.game.pipes;
import erland.game.*;
import erland.util.*;
import java.util.*;

class LevelFactory
{
	ImageHandlerInterface images;
	BlockContainerInterface cont;

	public LevelFactory(BlockContainerInterface cont, ImageHandlerInterface images)
	{
		this.cont = cont;
		this.images = images;
	}
	
	public PipeBlock[] getLevel(int level)
	{
		LinkedList blocks = new LinkedList();
		
		for (int i=0; i<(cont.getSizeX()*cont.getSizeY()); i++) {
			PipeBlock block = getRandomBlock();
			if(block!=null) {
				block.init(cont,i%cont.getSizeX(),i/cont.getSizeY());
				blocks.add(block);
			}
	    }
	    for (int i=0; i<20; i++) {
	    	boolean bRemoved = false;
	    	while(!bRemoved) {
	    		int tmp = (int)(Math.random()*blocks.size()-1);
	    		if(blocks.size()>tmp) {
	    			blocks.remove(tmp);
	    			bRemoved = true;
	    		}
	    	}
	    }
	    PipeBlock ret[] = new PipeBlock[blocks.size()];
	    ListIterator it = blocks.listIterator();
	    int i=0;
	    while(it.hasNext()) {
	    	ret[i++]= (PipeBlock)(it.next());
	    }
	    return ret;
	}
	
	protected PipeBlock getRandomBlock()
	{
		int blockType = (int)(Math.random()*7);
		PipeBlock block = null;
		
		switch(blockType) {
			case 0:
				block = new PipeBlockCross(images);
				break;
			case 1:
				block = new PipeBlockUpDown(images);
				break;
			case 2:
				block = new PipeBlockLeftRight(images);
				break;
			case 3:
				block = new PipeBlockLeftDown(images);
				break;
			case 4:
				block = new PipeBlockLeftUp(images);
				break;
			case 5:
				block = new PipeBlockRightDown(images);
				break;
			case 6:
				block = new PipeBlockRightUp(images);
				break;
			//case 7:
			//	block = new PipeBlockCrossSplit(images);
			//	break;
			default:
				block = new PipeBlockCross(images);
				break;
		}
		return block;
	}
}