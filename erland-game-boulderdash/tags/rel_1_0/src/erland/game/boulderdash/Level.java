package erland.game.boulderdash;
/*
 * Copyright (C) 2003 Erland Isaksson (erland_i@hotmail.com)
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 2
 * of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA  02111-1307, USA.
 *
 */
import erland.game.*;
import java.util.*;

class Level
{
	/** Blocks which is part of this level */
	protected Block blocks[][];
	/** Level number of the level */
	protected int id;
	/** Block container which the blocks resides in */
	protected BlockContainerInterface cont;
	/** BoulderDash container interface */
	protected BoulderDashContainerInterface boulderCont;
    /** Game environment */
    protected GameEnvironmentInterface environment;

	/** Defines the different block types */
	abstract class BlockType
	{
		static final int bEmpty=0;
		static final int bEarth=1;
		static final int bBoulder=2;
		static final int bDiamond=3;
		static final int bMonster=4;
		static final int bPlayer=5;
	}
	
	/**
	 * Creates a new Level object
	 * @param environment Game environment object
	 * @param cont Block container which the blocks should reside in
	 */
	public Level(GameEnvironmentInterface environment, BoulderDashContainerInterface boulderCont, BlockContainerInterface cont)
	{
		blocks = new Block[0][0];
		id=0;
		this.environment = environment;
		this.cont = cont;
		this.boulderCont = boulderCont;
	}

	/**
	 * Sets the level number of the level
	 * @param id The level number of the level
	 */
	public void setId(int id)
	{
		this.id = id;
	}
	
	/**
	 * Get the level number of the level
	 * @return The level number of the level
	 */
	public int getId()
	{
		return id;
	}
	
	/**
	 * Get a copy of all blocks in the level
	 * @return A copy of all the blocks in the level
	 */
	public Block[][] getData()
	{
		Block[][] ret = new Block[blocks.length][blocks[0].length];
		for (int x=0; x<blocks.length; x++) {
			ret[x] = new Block[blocks[0].length];
			for (int y=0; y<blocks[0].length; y++) {
				ret[x][y]=null;
				if(blocks[x][y]!=null) {
					try {
						ret[x][y] = (Block)(blocks[x][y].clone());
					}catch(CloneNotSupportedException e) {
					}
				}
		    }
	    }
	    return ret;
	}
	
	/**
	 * Set the blocks which is part of this level
	 */
	public void setData(Block[][] blocks)
	{
		this.blocks =blocks;	
	}
	/**
	 * Get the block type number for the specified block
	 * @param block The block to get the block type number for
	 * @return The block type number for the block
	 */
	static public int getBlockType(Block block) 
	{
		if(block==null) {
			return BlockType.bEmpty;
		}else if(block instanceof BlockEarth) {
			return BlockType.bEarth;
		}else if(block instanceof BlockBoulder) {
			return BlockType.bBoulder;
		}else if(block instanceof BlockDiamond) {
			return BlockType.bDiamond;
		}else if(block instanceof BlockMonster) {
			return BlockType.bMonster;
		}else if(block instanceof Player) {
			return BlockType.bPlayer;
		}else {
			return BlockType.bEmpty;
		}
	}
	
	/**
	 * Creates a new block and returns it
     * @param environment Game environment
	 * @param boulderCont BoulderDash container interface
	 * @param cont Block container which the block should reside in
	 * @param x X position of the block (Square coordinate)
	 * @param y Y position of the block (Square coordinate)
	 * @param type Type of block to create, see {@link BlockType}
	 * @return The newly created block
	 */
	public static Block newBlock(GameEnvironmentInterface environment, BoulderDashContainerInterface boulderCont, BlockContainerInterface cont, int x, int y, int type)
	{
		Block block = null;
		switch(type) {
			case BlockType.bEmpty:
				block = null;
				break;
			case BlockType.bEarth:
				block = new BlockEarth();
				block.init(environment,boulderCont,cont,x,y);
				break;
			case BlockType.bBoulder:
				block = new BlockBoulder();
				block.init(environment,boulderCont,cont,x,y);
				break;
			case BlockType.bDiamond:
				block = new BlockDiamond();
				block.init(environment,boulderCont,cont,x,y);
				break;
			case BlockType.bMonster:
				block = new BlockMonster();
				block.init(environment,boulderCont,cont,x,y);
				break;
			case BlockType.bPlayer:
				block = new Player();
				block.init(environment,boulderCont,cont,x,y);
				break;
			default:
				block = null;
				break;
		}
		return block;
	}

	/**
	 * Initialize object from a level number and a String with level data
	 * @param id The level number
	 * @param data String with all blocks
	 * @return true/false (Success/Failure)
	 * @see #getDataAsString()
	 */
	public boolean load(int id, String data)
	{
		this.id = id;
		LinkedList blocklist = new LinkedList();
		blocklist.clear();
		
		int prev = 0;
		int next = data.indexOf("|");
		boolean bEnd = false;
		boolean bLast = false;
		int sizeX = 20;
		int sizeY = 20;
		if(next==-1) {
			bLast=true;
		}
		while(!bEnd) {
			if(bLast) {
				bEnd=true;
			}
			int type=0;
			int color=0;
			int x=0;
			int y=0;
			boolean bSuccess=false;
			String blockstr;
			if(bLast) {
				blockstr = data.substring(prev);
			}else {
				blockstr = data.substring(prev,next);
			}
			if(blockstr.length()>=5) {
				int prevpar = 0;
				int nextpar = blockstr.indexOf(",");
				if(nextpar>0) {
					type = Integer.valueOf(blockstr.substring(0,nextpar)).intValue();
					prevpar = nextpar+1;
					nextpar	=blockstr.indexOf(",",prevpar);
					if(nextpar>0) {
						String param = blockstr.substring(prevpar,nextpar);
						x = Integer.valueOf(param).intValue();
						if(x>=sizeX) {
							sizeX = x+1;
						}
						prevpar = nextpar+1;
						y = Integer.valueOf(blockstr.substring(prevpar)).intValue();
						if(y>=sizeY) {
							sizeY = y+1;
						}
						bSuccess = true;
					}
				}
			}else {
				break;
			}
			if(bSuccess) {
				Block block = newBlock(environment,boulderCont, cont,x,y,type);
				if(block!=null) {
					blocklist.add(block);
				}
			}
			prev = next+1;
			next = data.indexOf("|", prev);
			if(next==-1) {
				bLast = true;
			}
		}
		if(blocklist.size()>0) {
			blocks = new Block[sizeX][sizeY];
			ListIterator it = blocklist.listIterator();
			int i=0;
			for (int x=0; x<blocks.length; x++) {
				blocks[x] = new Block[sizeX];
				for (int y=0; y<blocks[0].length; y++) {
					blocks[x][y]=null;
			    }
		    }
			while(it.hasNext()) {
				Block b =(Block)(it.next());
				blocks[b.getPosX()][b.getPosY()] = b;
			}
			return true;
		}else {
			return false;
		}
	}

	/**
	 * Get the level data as a String
	 * @return A String with all blocks in the level in text format
	 * @see #load(int,String)
	 */
	public String getDataAsString()
	{
		String res="";
		if(blocks!=null) {
			for (int x=0; x<blocks.length; x++) {
				for (int y=0; y<blocks[0].length; y++) {
					if(res.length()!=0) {
						res += "|";
					}
					int blockType = getBlockType(blocks[x][y]);
					res += String.valueOf(blockType) + "," 
						+ String.valueOf(x) + ","
						+ String.valueOf(y);
				}
		    }
		}
		return res;
	}
}