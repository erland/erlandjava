package erland.game.boulderdash;

import java.util.*;
import erland.util.*;
import erland.game.*;
class LevelFactory
{
	/** Boulderdash container object */
	protected BoulderDashContainerInterface c;
	
	/** Block container object which the blocks should be put in */
	protected BlockContainerInterface cont;
	
	/** The last available level */
	protected int maxLevel;
	
	/** THe last available default level */
	protected final int MAX_DEFAULT_LEVELS=5;
	
	/** List of all user made levels */
	protected LinkedList levels;

	/** Game environment */
	private GameEnvironmentInterface environment;

	/**
	 * Creates a new level factory
	 * @param environment Game environment
	 * @param cont Block container object which the blocks should be put in
	 */
	public LevelFactory(GameEnvironmentInterface environment, BoulderDashContainerInterface c, BlockContainerInterface cont)
	{
        this.environment = environment;
		this.cont = cont;
		this.c = c;
		this.levels = new LinkedList();
		this.maxLevel = MAX_DEFAULT_LEVELS;
		loadAll();
	}

	/**
	 * Get the blocks part of the specified level
	 * @param level The level number to get level data from
	 * @param player The player object to insert
	 * @return The matrix with all blocks on the level
	 */
	public Block[][] getDefaultLevel(int level,Player player)
	{
		Block[][] blocks = new Block[cont.getSizeX()][cont.getSizeY()];
		for(int x=0;x<cont.getSizeX();x++) {
			for(int y=0;y<cont.getSizeY();y++) {
				blocks[x][y] = new BlockEarth();
				blocks[x][y].init(environment,c,cont,x,y);
			}
		}
		
		for (int i=0; i<30; i++) {
			Block b = new BlockBoulder();
			insertAtRandomPos(blocks,b);
	    }
		for (int i=0; i<getNumberOfDiamonds(level); i++) {
			Block b = new BlockDiamond();
			insertAtRandomPos(blocks,b);
	    }
	    insertAtRandomPos(blocks,new BlockMonster());
		for (int i=0; i<100; i++) {
			insertAtRandomPos(blocks,(Block)null);
	    }
	    if(player!=null) {
		    insertAtRandomPos(blocks,player);
		}
	    return blocks;
	}
	
	/**
	 * Get all blocks for a specific level
	 * The level data will be taken from a user made level if it exists, else a hardcoded
	 * level will be used
	 * @param level Level number to get data for
	 * @param player The player object to insert
	 * @return All blocks in the requested level, null if level does not exist
	 */
	public Block[][] getLevel(int level,Player player)
	{
		if(level>maxLevel) {
			return null;
		}
		
		ListIterator it = levels.listIterator();
		Block[][] blocks=null;
		
		while(it.hasNext()) {
			Level l=(Level)(it.next());
			if(l.getId()==level) {
				blocks=l.getData();
			}
		}

		if(blocks!=null) {
		    if(player!=null) {
		    	if(!insertAtPreferedPos(blocks,player)) {
				    insertAtRandomPos(blocks,player);
		    	}
			}
			return blocks;
		}else {
			return getDefaultLevel(level,player);
		}
	}

	/**
	 * Return all different types of blocks
	 * @return Matrix with all different types of blocks
	 */
	public Block[][] getAllBlocks()
	{
		Block[][] blocks = new Block[cont.getSizeX()][cont.getSizeY()];
		for(int x=0;x<blocks.length;x++) {
			for(int y=0;y<blocks[0].length;y++) {
				blocks[x][y]=null;
			}
		}
		int i=0;
		blocks[i/blocks[0].length][i%blocks[0].length] = new BlockEarth();
		i++;
		blocks[i/blocks[0].length][i%blocks[0].length] = new BlockBoulder();
		i++;
		blocks[i/blocks[0].length][i%blocks[0].length] = new BlockDiamond();
		i++;
		blocks[i/blocks[0].length][i%blocks[0].length] = new BlockMonster();
		i++;
		blocks[i/blocks[0].length][i%blocks[0].length] = new Player();
		i++;
		
		for (int j=0;j<i;j++) {
			blocks[j/blocks[0].length][j%blocks[0].length].init(environment,c,cont,j/blocks[0].length,j%blocks[0].length);
		}
		return blocks;
	}
	
	/** 
	 * Get last level number
	 */
	public int getLastLevel()
	{
		return maxLevel;
	}
	
	/**
	 * Insert block at random position
	 * @param blocks Matrix of blocks the insert the block in
	 * @param block The block to insert
	 */
	protected void insertAtRandomPos(Block[][] blocks, Block block)
	{
		int randomPosX = (int)(Math.random()*cont.getSizeX());
		int randomPosY = (int)(Math.random()*cont.getSizeY());
		blocks[randomPosX][randomPosY] = block;
		if(block!=null) {
			block.init(environment,c,cont,randomPosX,randomPosY);
		}
	}
	 
	/**
	 * Insert player at random position
	 * @param blocks Matrix of blocks the insert the player in
	 * @param player The player to insert
	 */
	protected void insertAtRandomPos(Block[][] blocks, Player player)
	{
		boolean bInserted = false;
		while(!bInserted) {
			int randomPosX = (int)(Math.random()*cont.getSizeX());
			int randomPosY = (int)(Math.random()*cont.getSizeY());
			if(!(blocks[randomPosX][randomPosY] instanceof BlockDiamond)) {
				blocks[randomPosX][randomPosY]=null;
				player.init(environment,c,cont,randomPosX,randomPosY);
				bInserted = true;
			}
		}
	}
	
	/**
	 * Insert player at prefered position
	 * @param blocks Matrix of blocks the insert the player in
	 * @param player The player to insert
	 * @return true/false (Inserted/Not Inserted)
	 */
	protected boolean insertAtPreferedPos(Block[][] blocks, Player player)
	{
		boolean bInserted = false;
		for (int x=0; x<blocks.length; x++) {
			for (int y=0; y<blocks[0].length; y++) {
				if(Level.getBlockType(blocks[x][y])==Level.BlockType.bPlayer) {
					blocks[x][y] = null;
					if(!bInserted) {
						player.init(environment,c,cont,x,y);
						bInserted = true;
					}
				}
		    }
	    }
	    return bInserted;
	}

	/**
	 * Get the number of diamonds that has to be collected on
	 * the specified level
	 * @param level The level number
	 * @return Number of diamonds that has to be collected
	 */
	public int getNumberOfDiamonds(int level)
	{
		return 10;
	}
	
	/**
	 * Load all available user made levels
	 * @return true/false (Success/Failure)
	 */
	protected boolean loadAll()
	{
		if(environment.getStorage()!=null) {
			String maxlevelstr = environment.getStorage().getParameter("maxlevel");
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
		if(environment.getStorage()!=null) {
			String leveldata = environment.getStorage().getParameter("level"+String.valueOf(level));
			if(leveldata.length()>0) {
				Level lev = new Level(environment,c, cont);
				Log.println(this,"Loading level "+level+"...");
				if(lev.load(level,leveldata)) {
					Log.println(this,"Level "+level+" loaded");
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
		Level lev = new Level(environment,c,cont);
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
		if(environment.getStorage()!=null) {
			ListIterator it = levels.listIterator();
			while(it.hasNext()) {
				Level l = (Level)(it.next());
				if(l.getId()==level) {
					environment.getStorage().setParameter("level"+String.valueOf(l.getId()),l.getDataAsString());
				}
			}
			environment.getStorage().setParameter("maxlevel",String.valueOf(maxLevel));
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
	public boolean storeLevel(int level, Block blocks[][])
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
			lev = new Level(environment,c,cont);
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
		if(environment.getStorage()!=null) {
			for(int i=0;i<maxLevel;i++) {
				environment.getStorage().delParameter("level"+String.valueOf(i+1));
			}
			ListIterator it = levels.listIterator();
			while(it.hasNext()) {
				Level l = (Level)(it.next());
				environment.getStorage().setParameter("level"+String.valueOf(l.getId()),l.getDataAsString());
			}
			environment.getStorage().setParameter("maxlevel",String.valueOf(maxLevel));
			return true;
		}else {
			return false;
		}
	}
	
	/**
	 * Get the number of diamonds in the level data
	 * @param blocks The blocks to check for diamonds
	 * @return The number of diamonds in the level data
	 */
	public int getDiamonds(Block[][] blocks)
	{
		int diamonds = 0;
		for (int x=0; x<blocks.length; x++) {
			for (int y=0; y<blocks[0].length; y++) {
				if(Level.getBlockType(blocks[x][y])==Level.BlockType.bDiamond) {
					diamonds++;
				}
		    }
	    }
	    return diamonds;
	}
}