package erland.game.pipes;
import erland.game.*;
import erland.util.*;
import java.util.*;

/**
 * Level factory that creates all level data
 */
class LevelFactory
{
	/** Image handler object */
	ImageHandlerInterface images;
	/** Block container object */
	BlockContainerInterface cont;
	/** Parameter storage object */
	ParameterValueStorageInterface cookies;

	/**
	 * Creates a new level factory
	 * @param cont Block container which the blocks should be placed in
	 * @param images Image handler object
	 */
	public LevelFactory(ParameterValueStorageInterface cookies, BlockContainerInterface cont, ImageHandlerInterface images)
	{
		this.cont = cont;
		this.images = images;
		this.cookies = cookies;
	}
	
	/**
	 * Get level data for a specific level
	 * @param level Level to get level data for
	 * @return Array with pipe blocks created for the specified level
	 */
	public PipeBlock[] getLevel(int level)
	{
		LinkedList blocks = new LinkedList();
		
		for (int i=0; i<(cont.getSizeX()*cont.getSizeY()); i++) {
			PipeBlock block=null;
			while(block==null) {
				block=getRandomBlock();
			}
			if(block!=null) {
				block.init(cont,i%cont.getSizeX(),i/cont.getSizeY());
				blocks.add(block);
			}
	    }
	    // Create empty pipe blocks
	    int nEmptyBlocks=getNumberOfEmptyBlocks();
	    if(nEmptyBlocks>blocks.size()) {
	    	nEmptyBlocks = blocks.size();
	    }
	    for (int i=0; i<nEmptyBlocks; i++) {
	    	boolean bRemoved = false;
	    	while(!bRemoved) {
	    		int tmp = (int)(Math.random()*blocks.size()-1);
	    		if(blocks.size()>tmp) {
	    			blocks.remove(tmp);
	    			bRemoved = true;
	    		}
	    	}
	    }
	    
	    // Create return parameter
	    PipeBlock ret[] = new PipeBlock[blocks.size()];
	    ListIterator it = blocks.listIterator();
	    int i=0;
	    while(it.hasNext()) {
	    	ret[i++]= (PipeBlock)(it.next());
	    }
	    
	    // Create start blocks
	    int nStartBlocks=getNumberOfStartBlocks();
		if(nStartBlocks>ret.length) {
			nStartBlocks=ret.length;
		}
	    for (i=0; i<nStartBlocks; i++) {
			getRandomStartBlock(ret);
	    }

	    return ret;
	}
	
	/**
	 * Get a random type of start block at any position
	 * @param blocks Array with blocks in which the start block should be inserted
	 */
	protected void getRandomStartBlock(PipeBlock blocks[])
	{
		int type = (int)(Math.random()*4);
		switch(type) {
			case Direction.LEFT:
				getStartBlock(blocks,Direction.LEFT);
				break;
			case Direction.RIGHT:
				getStartBlock(blocks,Direction.RIGHT);
				break;
			case Direction.UP:
				getStartBlock(blocks,Direction.UP);
				break;
			case Direction.DOWN:
				getStartBlock(blocks,Direction.DOWN);
				break;
			default:
				getStartBlock(blocks,Direction.RIGHT);
				break;
		}
	}

	/**
	 * Get a start block of a specific type at any position
	 * @param blocks Array with blocks in which the start block should be inserted
	 * @param direction Direction of the start block, see {@link Direction}
	 */
	protected void getStartBlock(PipeBlock blocks[], int direction)
	{
		switch(direction) {
			case Direction.LEFT:
				initStartBlock(blocks,new PipeBlockStart(images,Direction.LEFT),cont.getSizeX()/2+1,0,cont.getSizeX()-1,cont.getSizeY()-1);
				break;
			case Direction.RIGHT:
				initStartBlock(blocks,new PipeBlockStart(images,Direction.RIGHT),0,0,cont.getSizeX()/2-2,cont.getSizeY()-1);
				break;
			case Direction.UP:
				initStartBlock(blocks,new PipeBlockStart(images,Direction.UP),0,cont.getSizeY()/2+1,cont.getSizeX()-1,cont.getSizeY()-1);
				break;
			case Direction.DOWN:
				initStartBlock(blocks,new PipeBlockStart(images,Direction.DOWN),0,0,cont.getSizeX()-1,cont.getSizeY()/2-2);
				break;
		}
	}
	/**
	 * Insert the start block in a random position within the specified area
	 * @param blocks Array with blocks in which the start block should be inserted
	 * @param startBlock The start block that should be inserted
	 * @param minX Min x position of the start block
	 * @param minY Min y position of the start block
	 * @param maxX Max x position of the start block
	 * @param maxY Max y position of the start block
	 */
	protected void initStartBlock(PipeBlock blocks[], PipeBlock startBlock, int minX, int minY, int maxX, int maxY)
	{
		int startBlockPos=-1;
		while(startBlockPos==-1) {
			int tmp =(int)(Math.random()*blocks.length);
			if(blocks.length>tmp) {
				if(blocks[tmp].getPosX()>=minX && blocks[tmp].getPosX()<=maxX &&
					blocks[tmp].getPosY()>=minY && blocks[tmp].getPosY()<=maxY) {
					if(!blocks[tmp].getClass().getName().equals("erland.game.pipes.PipeBlockStart")) {
							startBlockPos=tmp;
						}
				}
			}
		}
		startBlock.init(cont, blocks[startBlockPos].getPosX(),blocks[startBlockPos].getPosY());
		blocks[startBlockPos]=startBlock;
	}
	/**
	 * Creates a random pipe block
	 * @return The created pipe block
	 */
	protected PipeBlock getRandomBlock()
	{
		int blockType = (int)(Math.random()*12);
		PipeBlock block = null;
		
		if(!isAllowed(blockType)) {
			return null;
		}		
		
		return createBlock(blockType);
	}

	/**
	 * Create a new block of a specific type
	 * @param blockType Type of block to create
	 * @return The created block
	 */
	protected PipeBlock createBlock(int blockType)
	{
		PipeBlock block=null;
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
			case 7:
				block = new PipeBlockCrossSplit(images);
				break;
			case 8:
				block = new PipeBlockPool(images, Direction.LEFT);
				break;
			case 9:
				block = new PipeBlockPool(images, Direction.RIGHT);
				break;
			case 10:
				block = new PipeBlockPool(images, Direction.UP);
				break;
			case 11:
				block = new PipeBlockPool(images, Direction.DOWN);
				break;
			default:
				block = new PipeBlockCross(images);
				break;
		}
		return block;
	}
	/**
	 * Check if a specific block type is allowed
	 * @param blockType Block type number to check
	 * @return true/false (Allowed/Not allowed)
	 */
	protected boolean isAllowed(int blockType)
	{
		String allowed = cookies.getParameter("block" + blockType + "allowed");
		if(allowed!=null) {
			if(blockType<=6 && allowed.equalsIgnoreCase("false")) {
				return false;
			}
			if(blockType>6 && !allowed.equalsIgnoreCase("true")) {
				return false;
			}
		}else {
			if(blockType>6) {
				return false;
			}
		}
		return true;
	}
	/**
	 * Get an array of all allowed blocks
	 * @return Array with pipe blocks that are currently allowed
	 */
	public PipeBlock[] getAllowedBlocks()
	{
		LinkedList blocks = new LinkedList();
		
		for (int i=0; i<12; i++) {
			if(isAllowed(i)) {
				PipeBlock block = createBlock(i);
				block.init(cont,blocks.size()/cont.getSizeY(),blocks.size()%cont.getSizeY());
				blocks.add(block);
			}
	    }
	    
	    PipeBlock[] ret = new PipeBlock[blocks.size()];
	    int i=0;
	    ListIterator it = blocks.listIterator();
	    while(it.hasNext()) {
	    	PipeBlock block = (PipeBlock)(it.next());
	    	ret[i++]=block;
	    }
	    return ret;
	}

	/**
	 * Get an array of all not allowed blocks
	 * @return Array with pipe blocks that are currently not allowed
	 */
	public PipeBlock[] getNotAllowedBlocks()
	{
		LinkedList blocks = new LinkedList();
		for (int i=0; i<12; i++) {
			if(!isAllowed(i)) {
				PipeBlock block = createBlock(i);
				block.init(cont,blocks.size()/cont.getSizeY(),blocks.size()%cont.getSizeY());
				blocks.add(block);
			}
	    }
	    
	    PipeBlock[] ret = new PipeBlock[blocks.size()];
	    int i=0;
	    ListIterator it = blocks.listIterator();
	    while(it.hasNext()) {
	    	PipeBlock block = (PipeBlock)(it.next());
	    	ret[i++]=block;
	    }
	    return ret;
	}
	
	/**
	 * Get the block type for specific block
	 * @param block The block to get the type of
	 * @return The block type of the block
	 */
	protected int getBlockType(PipeBlock block) {
		int blockType = 0;
		if(block.getClass().getName().equals("erland.game.pipes.PipeBlockCross")) {
			blockType = 0;
		}else if(block.getClass().getName().equals("erland.game.pipes.PipeBlockUpDown")) {
			blockType = 1;
		}else if(block.getClass().getName().equals("erland.game.pipes.PipeBlockLeftRight")) {
			blockType = 2;
		}else if(block.getClass().getName().equals("erland.game.pipes.PipeBlockLeftDown")) {
			blockType = 3;
		}else if(block.getClass().getName().equals("erland.game.pipes.PipeBlockLeftUp")) {
			blockType = 4;
		}else if(block.getClass().getName().equals("erland.game.pipes.PipeBlockRightDown")) {
			blockType = 5;
		}else if(block.getClass().getName().equals("erland.game.pipes.PipeBlockRightUp")) {
			blockType = 6;
		}else if(block.getClass().getName().equals("erland.game.pipes.PipeBlockCrossSplit")) {
			blockType = 7;
		}else if(block.getClass().getName().equals("erland.game.pipes.PipeBlockPool")) {
			int openDirection = ((PipeBlockPool)block).getOpenDirection();
			switch(openDirection) {
				case Direction.LEFT:
					blockType = 8;
					break;
				case Direction.RIGHT:
					blockType = 9;
					break;
				case Direction.UP:
					blockType = 10;
					break;
				case Direction.DOWN:
					blockType = 11;
					break;
				default:
					break;
			}
		}
		return blockType;
	}
	
	/**
	 * Save which blocks that are allowed and which that are not allowed
	 * @param blocks Array with all blocks that are allowed
	 */
	public void saveAllowedBlocks(PipeBlock blocks[])
	{
		for(int i=0;i<12;i++) {
			cookies.delParameter("block" + i + "allowed");
		}
		
		for (int i=0; i<blocks.length; i++) {
			int blockType = getBlockType(blocks[i]);
			if(blockType>6) {
				cookies.setParameter("block" + blockType + "allowed","true");
			}
	    }
	    for (int i=0; i<=6; i++) {
	    	boolean bFound = false;
	    	for (int j=0;j<blocks.length;j++) {
	    		int blockType = getBlockType(blocks[j]);
	    		if(blockType==i) {
	    			bFound = true;
	    		}
	    	}
	    	if(!bFound) {
	    		cookies.setParameter("block" + i + "allowed","false");
	    	}
	    }
	}
	
	/**
	 * Get number of empty blocks that will be inserted on each level
	 * @return Number of empty blocks that will be inserted on each level
	 */
	public int getNumberOfEmptyBlocks()
	{
		String str = cookies.getParameter("emptyblocks");
		int emptyBlocks = 20;
		if(str!=null && str.length()>0) {
			try {
				emptyBlocks = Integer.valueOf(str).intValue();
		    }
		    catch (Exception ex) {
		    }
		}
		return emptyBlocks;
	}

	/**
	 * Get number of start blocks that will be inserted on each level
	 * @return Number of start blocks that will be inserted on each level
	 */
	public int getNumberOfStartBlocks()
	{
		String str = cookies.getParameter("startblocks");
		int startBlocks = 1;
		if(str!=null && str.length()>0) {
			try {
				startBlocks = Integer.valueOf(str).intValue();
		    }
		    catch (Exception ex) {
		    }
		}
		return startBlocks;
	}
	
	/**
	 * Save number of empty blocks that will be inserted on each level
	 * @param number Number of blocks that will be empty on each level
	 */
	public void saveNumberOfEmptyBlocks(int number)
	{
		if(number==20) {
			cookies.delParameter("emptyblocks");
		}else {
			if(number<1) {
				number=1;
			}
			cookies.setParameter("emptyblocks",String.valueOf(number));
		}
	}

	/**
	 * Save number of start blocks that will be inserted on each level
	 * @param number Number of start blocks that will be on each level
	 */
	public void saveNumberOfStartBlocks(int number)
	{
		if(number==1) {
			cookies.delParameter("startblocks");
		}else {
			if(number<1) {
				number=1;
			}
			cookies.setParameter("startblocks",String.valueOf(number));
		}
	}
}