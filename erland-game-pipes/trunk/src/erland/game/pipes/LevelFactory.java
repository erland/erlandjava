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
	/** Number of different block types */
	static final int MAX_BLOCK_TYPES = 25;
	/** Indicates which type of game this level factory creates levels for, see {@link GameType} */
	int gameType;
	
	/**
	 * Specifies the different types of games that exist
	 */
	abstract class GameType {
		static final int UntilWaterStopped=1;
		static final int UntilPoolsFilled=0;
	}

	/**
	 * Creates a new level factory
	 * @param cont Block container which the blocks should be placed in
	 * @param images Image handler object
	 */
	public LevelFactory(ParameterValueStorageInterface cookies, BlockContainerInterface cont, ImageHandlerInterface images, int gameType)
	{
		this.cont = cont;
		this.images = images;
		this.cookies = cookies;
		this.gameType = gameType;
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
				if(!isAllowedForGameType(block)) {
					block=null;
				}
			}
			if(block!=null) {
				block.init(cont,i%cont.getSizeX(),i/cont.getSizeY());
				block.updateBlock();
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
			PipeBlock block = getRandomStartBlock(ret);
			if(gameType==GameType.UntilPoolsFilled) {
				getRandomPoolBlock(ret,block);
			}
	    }

	    return ret;
	}
	
	/**
	 * Get a random type of start block at any position
	 * @param blocks Array with blocks in which the start block should be inserted
	 * @return The inserted start block
	 */
	protected PipeBlock getRandomStartBlock(PipeBlock blocks[])
	{
		int type = (int)(Math.random()*4);
		PipeBlock block = null;
		switch(type) {
			case Direction.LEFT:
				block = insertBlock(blocks,new PipeBlockPoolBig(images,true,Direction.LEFT),cont.getSizeX()/2+1,0,cont.getSizeX()-1,cont.getSizeY()-1,true,Direction.LEFT);
				break;
			case Direction.RIGHT:
				block = insertBlock(blocks,new PipeBlockPoolBig(images,true,Direction.RIGHT),0,0,cont.getSizeX()/2-2,cont.getSizeY()-1,true,Direction.RIGHT);
				break;
			case Direction.UP:
				block = insertBlock(blocks,new PipeBlockPoolBig(images,true,Direction.UP),0,cont.getSizeY()/2+1,cont.getSizeX()-1,cont.getSizeY()-1,true,Direction.UP);
				break;
			case Direction.DOWN:
				block = insertBlock(blocks,new PipeBlockPoolBig(images,true,Direction.DOWN),0,0,cont.getSizeX()-1,cont.getSizeY()/2-2,true,Direction.DOWN);
				break;
			default:
				block = insertBlock(blocks,new PipeBlockPoolBig(images,true,Direction.RIGHT),0,0,cont.getSizeX()/2-2,cont.getSizeY()-1,true,Direction.LEFT);
				break;
		}
		return block;
	}

	/**
	 * Get a random type of pool block at any position
	 * @param blocks Array with blocks in which the start block should be inserted
	 * @param start The start block which water might run from, if null the pool block 
	 * might be inserted anywhere on the game area
	 */
	protected PipeBlock getRandomPoolBlock(PipeBlock blocks[], PipeBlock startBlock)
	{
		int type = Direction.LEFT;
		if(startBlock!=null) {
			if((startBlock instanceof PipeBlockPoolBig)) {
				type = ((PipeBlockPoolBig)startBlock).getOpenDirection();
			}else if(startBlock instanceof PipeBlockStart) {
				type = ((PipeBlockStart)startBlock).getOpenDirection();
			}
			switch(type) {
				case Direction.LEFT:
					type = Direction.RIGHT;
					break;
				case Direction.RIGHT:
					type = Direction.LEFT;
					break;
				case Direction.UP:
					type = Direction.DOWN;
					break;
				case Direction.DOWN:
					type = Direction.UP;
					break;
				default:
					break;
			}
		}
		
		PipeBlock block;

		switch(type) {
			case Direction.LEFT:
				block = insertBlock(blocks,new PipeBlockPoolBig(images,false,Direction.LEFT),cont.getSizeX()/2+1,0,cont.getSizeX()-1,cont.getSizeY()-1,true,Direction.LEFT);
				break;
			case Direction.RIGHT:
				block = insertBlock(blocks,new PipeBlockPoolBig(images,false,Direction.RIGHT),0,0,cont.getSizeX()/2-2,cont.getSizeY()-1,true,Direction.RIGHT);
				break;
			case Direction.UP:
				block = insertBlock(blocks,new PipeBlockPoolBig(images,false,Direction.UP),0,cont.getSizeY()/2+1,cont.getSizeX()-1,cont.getSizeY()-1,true,Direction.UP);
				break;
			case Direction.DOWN:
				block = insertBlock(blocks,new PipeBlockPoolBig(images,false,Direction.DOWN),0,0,cont.getSizeX()-1,cont.getSizeY()/2-2,true,Direction.DOWN);
				break;
			default:
				block = insertBlock(blocks,new PipeBlockPoolBig(images,false,Direction.RIGHT),0,0,cont.getSizeX()/2-2,cont.getSizeY()-1,true,Direction.RIGHT);
		}
		return block;
	}

	/**
	 * Insert the block in a random position within the specified area
	 * The block will never overwrite a start block, pool block or another block
	 * of the same type as itself
	 * @param blocks Array with blocks in which the start block should be inserted
	 * @param block The block that should be inserted
	 * @param minX Min x position of the start block
	 * @param minY Min y position of the start block
	 * @param maxX Max x position of the start block
	 * @param maxY Max y position of the start block
	 * @param checkOpening Indicates that the opening should be checked so it is not blocked by an unmovable block
	 * @param direction The direction in which the opening of the block is
	 * @return The inserted and initialized block
	 */
	protected PipeBlock insertBlock(PipeBlock blocks[], PipeBlock block, int minX, int minY, int maxX, int maxY, boolean checkOpening, int direction)
	{
		int blockPos=-1;
		while(blockPos==-1) {
			int tmp =(int)(Math.random()*blocks.length);
			if(blocks.length>tmp) {
				if(blocks[tmp].getPosX()>=minX && blocks[tmp].getPosX()<=maxX &&
					blocks[tmp].getPosY()>=minY && blocks[tmp].getPosY()<=maxY) {
					int openX = blocks[tmp].getPosX();
					int openY = blocks[tmp].getPosY();
					switch(direction) {
						case Direction.LEFT:
							openX--;
							break;
						case Direction.RIGHT:
							openX++;
							break;
						case Direction.UP:
							openY--;
							break;
						case Direction.DOWN:
							openY++;
							break;
						default:
							break;
					}
					PipeBlock openingBlock = findBlock(blocks, openX,openY);
					if(openingBlock==null || openingBlock.isMovable()) {
						if(checkAdjacentBlocksForBlockingOpening(blocks, blocks[tmp].getPosX(), blocks[tmp].getPosY())) {
							if(!(blocks[tmp].getClass().equals(block.getClass())) && 
								!(blocks[tmp] instanceof PipeBlockPool) && 
								!(blocks[tmp] instanceof PipeBlockPoolBig) && 
								!(blocks[tmp] instanceof PipeBlockStart)) {
						
								blockPos=tmp;
							}
						}
					}
				}
			}
		}
		block.init(cont, blocks[blockPos].getPosX(),blocks[blockPos].getPosY());
		block.updateBlock();
		blocks[blockPos]=block;
		return block;
	}

	/**
	 * Check the blocks around a specific position to se if it matters if we
	 * put a unmovable block at this position, we need to make sure that we don't
	 * block any openings
	 */	
	protected boolean checkAdjacentBlocksForBlockingOpening(PipeBlock blocks[], int x, int y)
	{
		PipeBlock block = findBlock(blocks,x-1,y);
		if(block!=null && !isOpeningFree(block,x,y)) {
			return false;
		}
		block = findBlock(blocks,x+1,y);
		if(block!=null && !isOpeningFree(block,x,y)) {
			return false;
		}	
		block = findBlock(blocks,x,y-1);
		if(block!=null && !isOpeningFree(block,x,y)) {
			return false;
		}	
		block = findBlock(blocks,x,y+1);
		if(block!=null && !isOpeningFree(block,x,y)) {
			return false;
		}	
		return true;
	}
	
	/**
	 * Check if an unmovable block in the specified position would block
	 * the opening of the specified block
	 * @param block The block to check
	 * @param x The x position to check
	 * @param y The y position to check
	 * @return true/false (Opening free/Opening blocked)
	 */	
	protected boolean isOpeningFree(PipeBlock block, int x, int y)
	{
		int direction=-1;
		if(block instanceof PipeBlockPoolBig) {
			direction = ((PipeBlockPoolBig)block).getOpenDirection();
		}
		if(block instanceof PipeBlockPool) {
			direction = ((PipeBlockPool)block).getOpenDirection();
		}
		if(block instanceof PipeBlockStart) {
			direction = ((PipeBlockStart)block).getOpenDirection();
		}
		if(direction!=-1) {
			switch(direction) {
				case Direction.LEFT:
					if(block.getPosX()==x+1 && block.getPosY()==y) {
						return false;
					}
					break;
				case Direction.RIGHT:
					if(block.getPosX()==x-1 && block.getPosY()==y) {
						return false;
					}
					break;
				case Direction.UP:
					if(block.getPosX()==x && block.getPosY()==y+1) {
						return false;
					}
					break;
				case Direction.DOWN:
					if(block.getPosX()==x && block.getPosY()==y-1) {
						return false;
					}
					break;
				default:
					break;
			}
		}
		return true;
	}

	/**
	 * Finds a the block at the specified position and returns it
	 * @param blocks The array with blocks to search in
	 * @param x The x position of the block to find
	 * @param y The y position of the block to find
	 * @return The found block, null if not block was found
	 */
	protected PipeBlock findBlock(PipeBlock blocks[], int x, int y)
	{
		for (int i=0; i<blocks.length; i++) {
			if(blocks[i].getPosX() == x && blocks[i].getPosY()==y) {
				return blocks[i];
			}
	    }
	    return null;
	}
	/**
	 * Creates a random pipe block
	 * @return The created pipe block
	 */
	protected PipeBlock getRandomBlock()
	{
		int blockType = (int)(Math.random()*MAX_BLOCK_TYPES);
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
			case 12:
				block = new PipeBlockPoolBig(images,true,Direction.LEFT);
				break;
			case 13:
				block = new PipeBlockPoolBig(images,true,Direction.RIGHT);
				break;
			case 14:
				block = new PipeBlockPoolBig(images,true,Direction.UP);
				break;
			case 15:
				block = new PipeBlockPoolBig(images,true,Direction.DOWN);
				break;
			case 16:
				block = new PipeBlockPoolBig(images,false,Direction.LEFT);
				break;
			case 17:
				block = new PipeBlockPoolBig(images,false,Direction.RIGHT);
				break;
			case 18:
				block = new PipeBlockPoolBig(images,false,Direction.UP);
				break;
			case 19:
				block = new PipeBlockPoolBig(images,false,Direction.DOWN);
				break;
			case 20:
				block = new PipeBlockStart(images,Direction.LEFT);
				break;
			case 21:
				block = new PipeBlockStart(images,Direction.RIGHT);
				break;
			case 22:
				block = new PipeBlockStart(images,Direction.UP);
				break;
			case 23:
				block = new PipeBlockStart(images,Direction.DOWN);
				break;
			case 24:
				block = new PipeBlockUnmovable(images);
				break;
			default:
				block = new PipeBlockCross(images);
				break;
		}
		return block;
	}
	/**
	 * Checks if the specified block is allowed for the current
	 * game type
	 * @param block The block to check
	 * @return true/false (Allowed/Not allowed)
	 */
	protected boolean isAllowedForGameType(PipeBlock block)
	{
		if(gameType==GameType.UntilPoolsFilled) {
			// We need to have equal number of pools as start blocks, so
			// we can't allow pools to be created randomly
			if((block instanceof PipeBlockPool) || (block instanceof PipeBlockPoolBig)) {
				return false;
			}
		}else if(gameType==GameType.UntilWaterStopped) {
		}
		return true;
	}
	/**
	 * Check if a specific block type is allowed
	 * @param blockType Block type number to check
	 * @return true/false (Allowed/Not allowed)
	 */
	protected boolean isAllowed(int blockType)
	{
		String allowed=null;
		if(cookies!=null) {
			allowed = cookies.getParameter("block" + blockType + "allowed");
		}
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
		
		for (int i=0; i<MAX_BLOCK_TYPES; i++) {
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
		for (int i=0; i<MAX_BLOCK_TYPES; i++) {
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
		if(block instanceof PipeBlockCross) {
			blockType = 0;
		}else if(block instanceof PipeBlockUpDown) {
			blockType = 1;
		}else if(block instanceof PipeBlockLeftRight) {
			blockType = 2;
		}else if(block instanceof PipeBlockLeftDown) {
			blockType = 3;
		}else if(block instanceof PipeBlockLeftUp) {
			blockType = 4;
		}else if(block instanceof PipeBlockRightDown) {
			blockType = 5;
		}else if(block instanceof PipeBlockRightUp) {
			blockType = 6;
		}else if(block instanceof PipeBlockCrossSplit) {
			blockType = 7;
		}else if(block instanceof PipeBlockPool) {
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
		}else if(block instanceof PipeBlockPoolBig) {
			int openDirection = ((PipeBlockPoolBig)block).getOpenDirection();
			switch(openDirection) {
				case Direction.LEFT:
					blockType = 12;
					break;
				case Direction.RIGHT:
					blockType = 13;
					break;
				case Direction.UP:
					blockType = 14;
					break;
				case Direction.DOWN:
					blockType = 15;
					break;
				default:
					break;
			}
			boolean waterFilled = ((PipeBlockPoolBig)block).getWaterFilled();
			if(!waterFilled) {
				blockType+=4;
			}
		}else if(block instanceof PipeBlockStart) {
			int openDirection = ((PipeBlockStart)block).getOpenDirection();
			switch(openDirection) {
				case Direction.LEFT:
					blockType = 20;
					break;
				case Direction.RIGHT:
					blockType = 21;
					break;
				case Direction.UP:
					blockType = 22;
					break;
				case Direction.DOWN:
					blockType = 23;
					break;
				default:
					break;
			}
		}else if(block instanceof PipeBlockUnmovable) {
			return 24;
		}
		return blockType;
	}
	
	/**
	 * Save which blocks that are allowed and which that are not allowed
	 * @param blocks Array with all blocks that are allowed
	 */
	public void saveAllowedBlocks(PipeBlock blocks[])
	{
		if(cookies==null) {
			return;
		}
		for(int i=0;i<MAX_BLOCK_TYPES;i++) {
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
		String str=null;
		if(cookies!=null) {
			str = cookies.getParameter("emptyblocks");
		}
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
		String str=null;
		if(cookies!=null) {
			str = cookies.getParameter("startblocks");
		}
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
	 * Get time until water starts running
	 * @return Time until water starts running
	 */
	public int getTimeUntilWater()
	{
		String str=null;
		if(cookies!=null) {
			str = cookies.getParameter("timeuntilwater");
		}
		int timeUntilWater = 1000;
		if(str!=null && str.length()>0) {
			try {
				timeUntilWater = Integer.valueOf(str).intValue();
		    }
		    catch (Exception ex) {
		    }
		}
		return timeUntilWater;
	}

	/**
	 * Get water speed
	 * @return The initial water speed
	 */
	public int getWaterSpeed()
	{
		String str=null;
		if(cookies!=null) {
			str = cookies.getParameter("waterspeed");
		}
		int waterSpeed = 45;
		if(str!=null && str.length()>0) {
			try {
				waterSpeed = Integer.valueOf(str).intValue();
		    }
		    catch (Exception ex) {
		    }
		}
		return waterSpeed;
	}


	/**
	 * Get initial blocks to fill
	 * @return The initial number of blocks to fill
	 */
	public int getLeftToFill()
	{
		String str=null;
		if(cookies!=null) {
			str = cookies.getParameter("blockstofill");
		}
		int leftToFill = 16;
		if(str!=null && str.length()>0) {
			try {
				leftToFill = Integer.valueOf(str).intValue();
		    }
		    catch (Exception ex) {
		    }
		}
		return leftToFill;
	}
	/**
	 * Save number of empty blocks that will be inserted on each level
	 * @param number Number of blocks that will be empty on each level
	 */
	public void saveNumberOfEmptyBlocks(int number)
	{
		if(cookies==null) {
			return;
		}
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
		if(cookies==null) {
			return;
		}
		if(number==1) {
			cookies.delParameter("startblocks");
		}else {
			if(number<1) {
				number=1;
			}
			cookies.setParameter("startblocks",String.valueOf(number));
		}
	}
	
	/**
	 * Save time until water starts running
	 * @param number Time until water starts running
	 */
	public void saveTimeUntilWater(int number)
	{
		if(cookies==null) {
			return;
		}
		if(number==1000) {
			cookies.delParameter("timeuntilwater");
		}else {
			if(number<0) {
				number=0;
			}
			cookies.setParameter("timeuntilwater",String.valueOf(number));
		}
	}


	/**
	 * Save water speed
	 * @param number The initial speed of the water
	 */
	public void saveWaterSpeed(int number)
	{
		if(cookies==null) {
			return;
		}
		if(number==45) {
			cookies.delParameter("waterspeed");
		}else {
			if(number<0) {
				number=0;
			}else if(number>50) {
				number=50;
			}

			cookies.setParameter("waterspeed",String.valueOf(number));
		}
	}

	/**
	 * Save initial blocks to fill
	 * @param number The initial number of blocks to fill
	 */
	public void saveLeftToFill(int number)
	{
		if(cookies==null) {
			return;
		}
		if(number==16) {
			cookies.delParameter("blockstofill");
		}else {
			if(number<1) {
				number=1;
			}else if(number>1000) {
				number=1000;
			}

			cookies.setParameter("blockstofill",String.valueOf(number));
		}
	}
	/**
	 * Check if all wells/pools in the specified array of blocks
	 * are full with water
	 * @param blocks The array of blocks to check
	 * @return true/false (All pools full/All pools not full)
	 */
	public boolean isPoolsFilled(PipeBlock blocks[])
	{
		boolean isFull = true;
		for (int i=0; i<blocks.length; i++) {
			if((blocks[i] instanceof PipeBlockPoolBig) ||
				(blocks[i] instanceof PipeBlockPool)) {
					if(!blocks[i].isWaterFilled()) {
						isFull=false;
					}
					
			}
	    }
	    return isFull;
	}
}