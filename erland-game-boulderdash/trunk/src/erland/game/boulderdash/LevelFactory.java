package erland.game.boulderdash;

import java.util.*;
import erland.util.*;
import erland.game.*;
class LevelFactory
{
	/** Image handler object */
	protected ImageHandlerInterface images;
	
	/** Boulderdash container object */
	protected BoulderDashContainerInterface c;
	
	/** Block container object which the blocks should be put in */
	protected BlockContainerInterface cont;
	
	/**
	 * Creates a new level factory
	 * @param images Image handler object
	 * @param cont Block container object which the blocks should be put in
	 */
	public LevelFactory(BoulderDashContainerInterface c, ImageHandlerInterface images, BlockContainerInterface cont)
	{
		this.images = images;
		this.cont = cont;
		this.c = c;
	}

	/**
	 * Get the blocks part of the specified level
	 * @param level The level number to get level data from
	 * @return The matrix with all blocks on the level
	 */
	public Block[][] getLevel(int level,Player player)
	{
		Block[][] blocks = new Block[cont.getSizeX()][cont.getSizeY()];
		for(int x=0;x<cont.getSizeX();x++) {
			for(int y=0;y<cont.getSizeY();y++) {
				blocks[x][y] = new BlockEarth();
				blocks[x][y].init(c,images,cont,x,y);
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
	    insertAtRandomPos(blocks,player);
	    return blocks;
	}
	
	/** 
	 * Get last level number
	 */
	int getLastLevel()
	{
		return 100;
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
			block.init(c,images,cont,randomPosX,randomPosY);
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
				player.init(c,images,cont,randomPosX,randomPosY);
				bInserted = true;
			}
		}
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
}