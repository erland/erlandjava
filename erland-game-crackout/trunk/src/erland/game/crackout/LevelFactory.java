package erland.game.crackout;
import erland.util.*;
import erland.game.*;
import java.util.*;
import java.awt.Color;

/**
 * Level factory object
 * Manages all the levels available, both hardcoded and user made with the editor
 */
class LevelFactory
{
	/** Number of hardcoded levels */
	protected static final int MAX_DEFAULT_LEVELS=8;
	
	/** Temporary storage for array of block, used by {@link #getFullBlockList()} and {@link #newBlock(int, int,Color,int)} */
	protected LinkedList blocks;
	/** List of all user made levels */
	protected LinkedList levels;
	
	/** Image handler object */
	protected ImageHandlerInterface images;
	/** Width of blocks (Square coordinate) */
	protected int sizeX;
	/** Height of blocks (Square coordinate) */
	protected int sizeY;
	/** Parameter storage which contains user made levels */
	protected ParameterValueStorageInterface cookies;
	/** The last level number */
	protected int maxLevel;
	/** Block container which the blocks should reside in */
	protected BlockContainerInterface cont;

	/**
	 * Get a array with all different blocks available
	 * @return An array with all different blocks available
	 */
	public Block[] getFullBlockList()
	{
		blocks = new LinkedList();
		blocks.clear();
		
		newBlock(blocks.size()%sizeX,blocks.size()/sizeX,Color.yellow,Level.BlockType.bPlain);
		newBlock(blocks.size()%sizeX,blocks.size()/sizeX,Color.red,Level.BlockType.bMultiple);
		newBlock(blocks.size()%sizeX,blocks.size()/sizeX,Color.green,Level.BlockType.bStatic);

		newBlock(blocks.size()%sizeX,blocks.size()/sizeX,Color.green,Level.BlockType.fLockBat);
		newBlock(blocks.size()%sizeX,blocks.size()/sizeX,Color.white,Level.BlockType.fNewBall);
		newBlock(blocks.size()%sizeX,blocks.size()/sizeX,Color.blue,Level.BlockType.fIncreaseBallSpeed);
		newBlock(blocks.size()%sizeX,blocks.size()/sizeX,Color.yellow,Level.BlockType.fDecreaseBallSpeed);
		newBlock(blocks.size()%sizeX,blocks.size()/sizeX,Color.red,Level.BlockType.fIncreaseBatSpeed);
		newBlock(blocks.size()%sizeX,blocks.size()/sizeX,Color.green,Level.BlockType.fDecreaseBatSpeed);
		newBlock(blocks.size()%sizeX,blocks.size()/sizeX,Color.white,Level.BlockType.fDoubleBat);
		newBlock(blocks.size()%sizeX,blocks.size()/sizeX,Color.blue,Level.BlockType.fExtraLife);
		newBlock(blocks.size()%sizeX,blocks.size()/sizeX,Color.yellow,Level.BlockType.fSafetyWall);
		newBlock(blocks.size()%sizeX,blocks.size()/sizeX,Color.red,Level.BlockType.fMissile);
		newBlock(blocks.size()%sizeX,blocks.size()/sizeX,Color.green,Level.BlockType.fLargeBat);
		newBlock(blocks.size()%sizeX,blocks.size()/sizeX,Color.white,Level.BlockType.fSmallBat);
		newBlock(blocks.size()%sizeX,blocks.size()/sizeX,Color.blue,Level.BlockType.fBomb);

		newBlock(blocks.size()%sizeX,blocks.size()/sizeX,Color.green,Level.BlockType.fLockBatHold);
		newBlock(blocks.size()%sizeX,blocks.size()/sizeX,Color.white,Level.BlockType.fNewBallHold);
		newBlock(blocks.size()%sizeX,blocks.size()/sizeX,Color.blue,Level.BlockType.fIncreaseBallSpeedHold);
		newBlock(blocks.size()%sizeX,blocks.size()/sizeX,Color.yellow,Level.BlockType.fDecreaseBallSpeedHold);
		newBlock(blocks.size()%sizeX,blocks.size()/sizeX,Color.red,Level.BlockType.fIncreaseBatSpeedHold);
		newBlock(blocks.size()%sizeX,blocks.size()/sizeX,Color.green,Level.BlockType.fDecreaseBatSpeedHold);
		newBlock(blocks.size()%sizeX,blocks.size()/sizeX,Color.white,Level.BlockType.fDoubleBatHold);
		newBlock(blocks.size()%sizeX,blocks.size()/sizeX,Color.blue,Level.BlockType.fExtraLifeHold);
		newBlock(blocks.size()%sizeX,blocks.size()/sizeX,Color.yellow,Level.BlockType.fSafetyWallHold);
		newBlock(blocks.size()%sizeX,blocks.size()/sizeX,Color.red,Level.BlockType.fMissileHold);
		newBlock(blocks.size()%sizeX,blocks.size()/sizeX,Color.green,Level.BlockType.fLargeBatHold);
		newBlock(blocks.size()%sizeX,blocks.size()/sizeX,Color.white,Level.BlockType.fSmallBatHold);
		newBlock(blocks.size()%sizeX,blocks.size()/sizeX,Color.blue,Level.BlockType.fBombHold);

		newBlock(blocks.size()%sizeX,blocks.size()/sizeX,Color.blue,Level.BlockType.mBounceBlock);
		newBlock(blocks.size()%sizeX,blocks.size()/sizeX,Color.yellow,Level.BlockType.mBounceOnceBlock);

		Block ret[];
		if(blocks.size()>0) {
			ret = new Block[blocks.size()];
			ListIterator it=blocks.listIterator();
			int i=0;
			while(it.hasNext()) {
				ret[i++]=(Block)(it.next());			
			}
			blocks.clear();
			return ret;
		}else {
			return null;
		}
	}
	
	/**
	 * Creates a new block and put it into the {@link #blocks} list
	 * @param x X position of block (Square coordinate)
	 * @param y Y position of block (Square coordinate)
	 * @param c Color of block
	 * @param type Type of block, see {@link Level.BlockType}
	 */
	protected void newBlock(int x, int y, Color c, int type)
	{
		x*=Level.squareSizeX;
		y*=Level.squareSizeY;

		Block block = Level.newBlock(images, cont, x,y,c,type);
		if(block!=null) {
			blocks.add(block);
		}
	}
	/**
	 * Creates a new level factory
	 * @param images Image handler object
	 * @param cookies Parameter storage object which should be used when accessing user made level data
	 * @param cont Block container which the blocks should reside in
	 */
	public LevelFactory(ImageHandlerInterface images, ParameterValueStorageInterface cookies, BlockContainerInterface cont)
	{
		this.images = images;
		this.cont = cont;
		this.sizeX = cont.getSizeX();
		this.sizeY = cont.getSizeY();
		this.cookies = cookies;
		this.levels = new LinkedList();
		loadAll();
	}
	
	/**
	 * Get all blocks for a specific level
	 * The level data will be taken from a user made level if it exists, else a hardcoded
	 * level will be used
	 * @param level Level number to get data for
	 * @return Array of all blocks in the requested level, null if level does not exist
	 */
	public Block[] getLevel(int level)
	{
		if(level>maxLevel) {
			return null;
		}
		
		ListIterator it = levels.listIterator();
		Block[] blocks=null;
		
		while(it.hasNext()) {
			Level l=(Level)(it.next());
			if(l.getId()==level) {
				blocks=l.getData();
			}
		}
		if(blocks!=null) {
			return blocks;
		}else {
			return getDefaultLevel(level);
		}
	}
	
	/**
	 * Get all blocks for a specific hardcoded level
	 * @param level Level number to get data for
	 * @return Array of all blocks in the requested level
	 */
	protected Block[] getDefaultLevel(int level)
	{
		blocks = new LinkedList();
		blocks.clear();
		switch(level) {
			case 1:
				newBlock(1,1,Color.yellow,Level.BlockType.bPlain);
				newBlock(3,1,Color.yellow,Level.BlockType.bPlain);
				newBlock(6,1,Color.yellow,Level.BlockType.bPlain);
				newBlock(8,1,Color.yellow,Level.BlockType.bPlain);
				newBlock(1,2,Color.red,Level.BlockType.bPlain);
				newBlock(3,2,Color.red,Level.BlockType.bPlain);
				newBlock(6,2,Color.red,Level.BlockType.bPlain);
				newBlock(8,2,Color.red,Level.BlockType.bPlain);
				newBlock(1,3,Color.green,Level.BlockType.bPlain);
				newBlock(2,3,Color.green,Level.BlockType.bPlain);
				newBlock(3,3,Color.green,Level.BlockType.bPlain);
				newBlock(4,3,Color.green,Level.BlockType.bPlain);
				newBlock(5,3,Color.green,Level.BlockType.bPlain);
				newBlock(6,3,Color.green,Level.BlockType.bPlain);
				newBlock(7,3,Color.green,Level.BlockType.bPlain);
				newBlock(8,3,Color.green,Level.BlockType.bPlain);
				break;
			case 2:
				newBlock(0,4,Color.red,Level.BlockType.bPlain);
				newBlock(1,4,Color.red,Level.BlockType.bPlain);
				newBlock(2,4,Color.red,Level.BlockType.bPlain);
				newBlock(3,4,Color.red,Level.BlockType.bPlain);
				newBlock(4,4,Color.red,Level.BlockType.bPlain);
				newBlock(5,4,Color.red,Level.BlockType.bPlain);
				newBlock(6,4,Color.red,Level.BlockType.bPlain);
				newBlock(7,4,Color.red,Level.BlockType.bPlain);
				newBlock(8,4,Color.red,Level.BlockType.bPlain);
				newBlock(9,4,Color.red,Level.BlockType.bPlain);
				newBlock(8,3,Color.red,Level.BlockType.bPlain);
				newBlock(1,3,Color.red,Level.BlockType.bPlain);
				newBlock(7,2,Color.red,Level.BlockType.bPlain);
				newBlock(2,2,Color.red,Level.BlockType.bPlain);
				newBlock(6,1,Color.red,Level.BlockType.bPlain);
				newBlock(3,1,Color.red,Level.BlockType.bPlain);
				newBlock(5,0,Color.red,Level.BlockType.bPlain);
				newBlock(4,0,Color.red,Level.BlockType.bPlain);
				newBlock(4,1,Color.yellow,Level.BlockType.fExtraLife);
				newBlock(5,1,Color.yellow,Level.BlockType.fDoubleBat);
				newBlock(3,2,Color.green,Level.BlockType.bPlain);
				newBlock(4,2,Color.green,Level.BlockType.bPlain);
				newBlock(5,2,Color.green,Level.BlockType.bPlain);
				newBlock(6,2,Color.green,Level.BlockType.bPlain);
				newBlock(2,3,Color.blue,Level.BlockType.bPlain);
				newBlock(3,3,Color.blue,Level.BlockType.bPlain);
				newBlock(4,3,Color.blue,Level.BlockType.bPlain);
				newBlock(5,3,Color.blue,Level.BlockType.bPlain);
				newBlock(6,3,Color.blue,Level.BlockType.bPlain);
				newBlock(7,3,Color.blue,Level.BlockType.bPlain);
				break;
			case 3:
				newBlock(2,5,Color.red,Level.BlockType.bPlain);
				newBlock(3,5,Color.red,Level.BlockType.bPlain);
				newBlock(4,5,Color.green,Level.BlockType.bMultiple);
				newBlock(5,5,Color.green,Level.BlockType.bMultiple);
				newBlock(6,5,Color.red,Level.BlockType.bPlain);
				newBlock(7,5,Color.red,Level.BlockType.bPlain);
				newBlock(6,4,Color.red,Level.BlockType.bStatic);
				newBlock(7,3,Color.red,Level.BlockType.bStatic);
				newBlock(8,2,Color.red,Level.BlockType.bStatic);
				newBlock(3,4,Color.red,Level.BlockType.bStatic);
				newBlock(2,3,Color.red,Level.BlockType.bStatic);
				newBlock(1,2,Color.red,Level.BlockType.bStatic);
				newBlock(2,1,Color.green,Level.BlockType.bMultiple);
				newBlock(3,1,Color.red,Level.BlockType.bPlain);
				newBlock(4,1,Color.red,Level.BlockType.bPlain);
				newBlock(5,1,Color.red,Level.BlockType.bPlain);
				newBlock(6,1,Color.red,Level.BlockType.bPlain);
				newBlock(7,1,Color.green,Level.BlockType.bMultiple);
				newBlock(4,2,Color.white,Level.BlockType.mBounceOnceBlock);
				newBlock(5,2,Color.white,Level.BlockType.mBounceOnceBlock);
				break;
			case 4:
				newBlock(1,1,Color.green,Level.BlockType.bPlain);
				newBlock(2,1,Color.green,Level.BlockType.bPlain);
				newBlock(3,1,Color.green,Level.BlockType.bPlain);
				newBlock(1,2,Color.yellow,Level.BlockType.bPlain);
				newBlock(1,3,Color.blue,Level.BlockType.bPlain);
				newBlock(2,3,Color.blue,Level.BlockType.bPlain);
				newBlock(1,4,Color.white,Level.BlockType.bPlain);
				newBlock(1,5,Color.red,Level.BlockType.bPlain);
				newBlock(2,5,Color.red,Level.BlockType.bPlain);
				newBlock(3,5,Color.red,Level.BlockType.bPlain);
				newBlock(5,1,Color.green,Level.BlockType.bPlain);
				newBlock(6,1,Color.green,Level.BlockType.bPlain);
				newBlock(7,1,Color.green,Level.BlockType.bPlain);
				newBlock(6,2,Color.yellow,Level.BlockType.bPlain);
				newBlock(6,3,Color.blue,Level.BlockType.bPlain);
				newBlock(6,4,Color.white,Level.BlockType.bPlain);
				newBlock(5,5,Color.red,Level.BlockType.bPlain);
				newBlock(6,5,Color.red,Level.BlockType.bPlain);
				newBlock(7,5,Color.red,Level.BlockType.bPlain);
				break;
			case 5:
				newBlock(0,4,Color.red,Level.BlockType.fIncreaseBallSpeed);
				newBlock(1,4,Color.red,Level.BlockType.fIncreaseBallSpeed);
				newBlock(2,4,Color.red,Level.BlockType.fIncreaseBallSpeed);
				newBlock(3,4,Color.red,Level.BlockType.fIncreaseBallSpeed);
				newBlock(4,4,Color.red,Level.BlockType.fIncreaseBallSpeed);
				newBlock(5,4,Color.green,Level.BlockType.fDecreaseBallSpeed);
				newBlock(6,4,Color.green,Level.BlockType.fDecreaseBallSpeed);
				newBlock(7,4,Color.green,Level.BlockType.fDecreaseBallSpeed);
				newBlock(8,4,Color.green,Level.BlockType.fDecreaseBallSpeed);
				newBlock(9,4,Color.green,Level.BlockType.fDecreaseBallSpeed);
				newBlock(0,2,Color.red,Level.BlockType.fIncreaseBallSpeed);
				newBlock(1,2,Color.red,Level.BlockType.fIncreaseBallSpeed);
				newBlock(2,2,Color.red,Level.BlockType.fIncreaseBallSpeed);
				newBlock(3,2,Color.red,Level.BlockType.fIncreaseBallSpeed);
				newBlock(4,2,Color.red,Level.BlockType.fIncreaseBallSpeed);
				newBlock(5,2,Color.green,Level.BlockType.fDecreaseBallSpeed);
				newBlock(6,2,Color.green,Level.BlockType.fDecreaseBallSpeed);
				newBlock(7,2,Color.green,Level.BlockType.fDecreaseBallSpeed);
				newBlock(8,2,Color.green,Level.BlockType.fDecreaseBallSpeed);
				newBlock(9,2,Color.green,Level.BlockType.fDecreaseBallSpeed);
				newBlock(0,0,Color.yellow,Level.BlockType.fExtraLife);
				newBlock(1,0,Color.yellow,Level.BlockType.fExtraLife);
				newBlock(2,0,Color.yellow,Level.BlockType.fExtraLife);
				newBlock(3,0,Color.yellow,Level.BlockType.fExtraLife);
				newBlock(4,0,Color.yellow,Level.BlockType.fExtraLife);
				newBlock(5,0,Color.yellow,Level.BlockType.fExtraLife);
				newBlock(6,0,Color.yellow,Level.BlockType.fExtraLife);
				newBlock(7,0,Color.yellow,Level.BlockType.fExtraLife);
				newBlock(8,0,Color.yellow,Level.BlockType.fExtraLife);
				newBlock(9,0,Color.yellow,Level.BlockType.fExtraLife);
				break;
			case 6:
				newBlock(1,4,Color.green,Level.BlockType.mBounceBlock);
				newBlock(2,4,Color.red,Level.BlockType.mBounceBlock);
				newBlock(3,4,Color.yellow,Level.BlockType.mBounceBlock);
				newBlock(4,4,Color.yellow,Level.BlockType.bStatic);
				newBlock(5,4,Color.yellow,Level.BlockType.bStatic);
				newBlock(6,4,Color.yellow,Level.BlockType.mBounceBlock);
				newBlock(7,4,Color.red,Level.BlockType.mBounceBlock);
				newBlock(8,4,Color.green,Level.BlockType.mBounceBlock);
				break;
			case 7:
				newBlock(1,5,Color.green,Level.BlockType.fLockBat);
				newBlock(2,5,Color.red,Level.BlockType.fNewBall);
				newBlock(3,5,Color.green,Level.BlockType.fIncreaseBallSpeed);
				newBlock(4,5,Color.red,Level.BlockType.fDecreaseBallSpeed);
				newBlock(5,5,Color.green,Level.BlockType.fIncreaseBatSpeed);
				newBlock(6,5,Color.red,Level.BlockType.fDecreaseBatSpeed);
				newBlock(7,5,Color.green,Level.BlockType.fDoubleBat);
				newBlock(8,5,Color.red,Level.BlockType.fExtraLife);
				break;
			case 8:
				newBlock(0,4,Color.green,Level.BlockType.fNewBall);
				newBlock(1,4,Color.green,Level.BlockType.fNewBall);
				newBlock(2,4,Color.green,Level.BlockType.fNewBall);
				newBlock(3,4,Color.green,Level.BlockType.fNewBall);
				newBlock(4,4,Color.green,Level.BlockType.fNewBall);
				newBlock(5,4,Color.green,Level.BlockType.fNewBall);
				newBlock(6,4,Color.green,Level.BlockType.fNewBall);
				newBlock(7,4,Color.green,Level.BlockType.fNewBall);
				newBlock(8,4,Color.green,Level.BlockType.fNewBall);
				newBlock(9,4,Color.green,Level.BlockType.fNewBall);
				newBlock(0,5,Color.green,Level.BlockType.fNewBall);
				newBlock(1,5,Color.green,Level.BlockType.fNewBall);
				newBlock(2,5,Color.green,Level.BlockType.fNewBall);
				newBlock(3,5,Color.green,Level.BlockType.fNewBall);
				newBlock(4,5,Color.green,Level.BlockType.fNewBall);
				newBlock(5,5,Color.green,Level.BlockType.fNewBall);
				newBlock(6,5,Color.green,Level.BlockType.fNewBall);
				newBlock(7,5,Color.green,Level.BlockType.fNewBall);
				newBlock(8,5,Color.green,Level.BlockType.fNewBall);
				newBlock(9,5,Color.green,Level.BlockType.fNewBall);
				newBlock(3,1,Color.yellow,Level.BlockType.bStatic);
				newBlock(3,2,Color.yellow,Level.BlockType.bStatic);
				newBlock(3,3,Color.yellow,Level.BlockType.bStatic);
				newBlock(4,3,Color.yellow,Level.BlockType.bStatic);
				newBlock(5,3,Color.yellow,Level.BlockType.bStatic);
				newBlock(5,1,Color.yellow,Level.BlockType.bStatic);
				newBlock(5,2,Color.yellow,Level.BlockType.bStatic);
				newBlock(4,2,Color.yellow,Level.BlockType.bMultiple);
			default:
				break;
		}
		
		Block ret[];
		if(blocks.size()>0) {
			ret = new Block[blocks.size()];
			ListIterator it=blocks.listIterator();
			int i=0;
			while(it.hasNext()) {
				ret[i++]=(Block)(it.next());			
			}
			blocks.clear();
			return ret;
		}else {
			ret = new Block[0];
			return ret;
		}
	}	
	
	/**
	 * Get the last available level
	 * @return The number of the last available level
	 */
	public int getLastLevel()
	{
		return maxLevel;
	}
	
	/**
	 * Load all available user made levels
	 * @return true/false (Success/Failure)
	 */
	protected boolean loadAll()
	{
		if(cookies!=null) {
			String maxlevelstr = cookies.getParameter("maxlevel");
			levels.clear();
			
			if(maxlevelstr.length()>0) {
				maxLevel = Integer.valueOf(maxlevelstr).intValue();
				for(int i=1;i<=maxLevel;i++) {
					loadLevel(i);
				}
			}else {
				maxLevel = MAX_DEFAULT_LEVELS;
			}
		}else {
			maxLevel = MAX_DEFAULT_LEVELS;
		}
		return true;
	}
	
	/**
	 * Load a specific user made level
	 * @param level Level number of the level to load
	 * @return true/false (Success/Failure)
	 */
	protected boolean loadLevel(int level)
	{
		if(cookies!=null) {
			String leveldata = cookies.getParameter("level"+String.valueOf(level));
			if(leveldata.length()>0) {
				Level lev = new Level(images, cont);
				if(lev.load(level,leveldata)) {
					ListIterator it = levels.listIterator();
					while(it.hasNext()) {
						Level l = (Level)(it.next());
						if(l.getId()==level) {
							it.remove();
							break;
						}
					}
					levels.add(lev);
					return true;
				}
			}
		}
		return false;
	}
	
	/**
	 * Create and insert a new user made level after the specified level number
	 * @param level Level number of the level which the new level should be inserted after
	 * @return true/false (Success/Failure)
	 */
	public boolean newLevel(int level)
	{
		ListIterator it = levels.listIterator();
		while(it.hasNext()) {
			Level l = (Level)(it.next());
			if(l.getId()>level) {
				l.setId(l.getId()+1);
			}
		}
		Level lev = new Level(images, cont);
		lev.setId(level+1);
		levels.add(lev);
		maxLevel++;
		return true;
	}

	/**
	 * Delete the specified user made level
	 * @param level Level number of the level to delete
	 * @return true/false (Success/Failure)
	 */
	public boolean deleteLevel(int level)
	{
		if(maxLevel==1) {
			newLevel(1);
		}
		boolean bRemoved = false;
		ListIterator it = levels.listIterator();
		while(it.hasNext()) {
			Level l = (Level)(it.next());
			if(l.getId()==level) {
				it.remove();
				bRemoved=true;
				maxLevel--;
				it = levels.listIterator();
				while(it.hasNext()) {
					l=(Level)(it.next());
					if(l.getId()>level) {
						l.setId(l.getId()-1);
					}
				}
				return true;
			}
		}
		if(!bRemoved) {
			if(level<=MAX_DEFAULT_LEVELS) {
				maxLevel--;
				return true;
			}
		}
		return false;
	}
	
	/**
	 * Save the specified level as a user made level
	 * @param level Level number of the level to save
	 * @return true/false (Success/Failure)
	 */
	public boolean saveLevel(int level) 
	{
		if(cookies!=null) {
			ListIterator it = levels.listIterator();
			while(it.hasNext()) {
				Level l = (Level)(it.next());
				if(l.getId()==level) {
					cookies.setParameter("level"+String.valueOf(l.getId()),l.getDataAsString());
				}
			}
			cookies.setParameter("maxlevel",String.valueOf(maxLevel));
			return true;
		}
		return false;
	}
	
	/**
	 * Set level data for the specified user made level
	 * @param level Level number of the level to store
	 * @param blocks Level data to be used
	 * @return true/false (Success/Failure)
	 */
	public boolean storeLevel(int level, Block blocks[])
	{
		System.out.println("storeLevel:"+level);
		Level lev=null;
		ListIterator it = levels.listIterator();
		while(it.hasNext()) {
			Level l = (Level)(it.next());
			if(l.getId()==level) {
				lev = l;
			}
		}

		if(lev==null) {
			lev = new Level(images, cont);
			levels.add(lev);
		}
		lev.setId(level);
		lev.setData(blocks);
		
		return true;
	}
	/** 
	 * Save all user made levels
	 * @return true/false (Success/Failure)
	 */
	public boolean saveAll()
	{
		if(cookies!=null) {
			for(int i=0;i<maxLevel;i++) {
				cookies.delParameter("level"+String.valueOf(i+1));
			}
			ListIterator it = levels.listIterator();
			while(it.hasNext()) {
				Level l = (Level)(it.next());
				cookies.setParameter("level"+String.valueOf(l.getId()),l.getDataAsString());
			}
			cookies.setParameter("maxlevel",String.valueOf(maxLevel));
			return true;
		}else {
			return false;
		}
	}
}