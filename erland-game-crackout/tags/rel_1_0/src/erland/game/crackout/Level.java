package erland.game.crackout;
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
import erland.util.*;
import java.awt.*;
import java.util.*;

/**
 * Holds information of all level data for a single level
 */
class Level
{
	abstract class BlockType {
		public static final int fLockBat=1;
		public static final int fNewBall=2;
		public static final int fIncreaseBallSpeed=3;
		public static final int fDecreaseBallSpeed=4;
		public static final int fIncreaseBatSpeed=5;
		public static final int fDecreaseBatSpeed=6;
		public static final int fDoubleBat=7;
		public static final int fExtraLife=8;
		public static final int fSafetyWall=9;
		public static final int fMissile=10;
		public static final int fLargeBat=11;
		public static final int fSmallBat=12;
		public static final int fBomb=13;
	
		public static final int fLockBatHold=20;
		public static final int fNewBallHold=21;
		public static final int fIncreaseBallSpeedHold=22;
		public static final int fDecreaseBallSpeedHold=23;
		public static final int fIncreaseBatSpeedHold=24;
		public static final int fDecreaseBatSpeedHold=25;
		public static final int fDoubleBatHold=26;
		public static final int fExtraLifeHold=27;
		public static final int fSafetyWallHold=28;
		public static final int fMissileHold=29;
		public static final int fLargeBatHold=30;
		public static final int fSmallBatHold=31;
		public static final int fBombHold=32;
	
		public static final int mBounceBlock=50;
		public static final int mBounceOnceBlock=51;
		public static final int bPlain=70;
		public static final int bMultiple=71;
		public static final int bStatic=72;
	}

	/** Width of the blocks (Number of squares) */
	public static final int squareSizeX=2;
	/** Height of the blocks (Number of squares) */
	public static final int squareSizeY=1;
	/** Array with all the blocks which is part of this level */
	protected Block blocks[];
	/** The level number of this level */
	protected int id;
	/** Block container which the blocks resides in */
	protected BlockContainerInterface cont;
    protected GameEnvironmentInterface environment;
	/**
	 * Creates a new Level object
	 * @param environment Game environment object
	 * @param cont Block container which the blocks should reside in
	 */
	public Level(GameEnvironmentInterface environment, BlockContainerInterface cont)
	{
		blocks = new Block[0];
		id=0;
		this.environment= environment;
		this.cont = cont;
	}
	
	/**
	 * Set the level data for this level
	 * @param blocks Array with all the blocks that should be part of this level
	 */
	public void setData(Block blocks[])
	{
		this.blocks = blocks;
	}
	
	/**
	 * Set the level number of this level
	 * @param id The new level number
	 */
	public void setId(int id)
	{
		this.id = id;
	}
		
	/**
	 * Get the level number of this level
	 * @return The level number
	 */
	public int getId()
	{
		return id;
	}
	
	/**
	 * Get the level data for this level
	 * @return An array with all the blocks which is part of this level, a new copy of
	 * all the blocks is returned in the array so you can modify them any way you want to
	 */
	public Block[] getData()
	{
		Block res[]=new Block[blocks.length];
		for(int i=0;i<blocks.length;i++) {
			try {
				res[i] = (Block)(blocks[i].clone());
			}catch(CloneNotSupportedException e) {
				// Should never happend
				res[i] = null;
			}
		}
		return res;
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
			if(blockstr.length()>=7) {
				int prevpar = 0;
				int nextpar = blockstr.indexOf(",");
				if(nextpar>0) {
					type = Integer.valueOf(blockstr.substring(0,nextpar)).intValue();
					prevpar = nextpar+1;
					nextpar	=blockstr.indexOf(",",prevpar);
					if(nextpar>0) {
						String param = blockstr.substring(prevpar,nextpar);
						x = Integer.valueOf(param).intValue();
						prevpar = nextpar+1;
						nextpar	=blockstr.indexOf(",",prevpar);
						if(nextpar>0) {
							y = Integer.valueOf(blockstr.substring(prevpar,nextpar)).intValue();
							prevpar = nextpar+1;
							color = Integer.valueOf(blockstr.substring(prevpar)).intValue();
							bSuccess = true;
						}
					}
				}
			}else {
				break;
			}
			if(bSuccess) {
				Block block = newBlock(environment, cont,x,y,getBlockColor(color),type);
				blocklist.add(block);
			}
			prev = next+1;
			next = data.indexOf("|", prev);
			if(next==-1) {
				bLast = true;
			}
		}
		if(blocklist.size()>0) {
			blocks = new Block[blocklist.size()];
			ListIterator it = blocklist.listIterator();
			int i=0;
			while(it.hasNext()) {
				blocks[i++]=(Block)(it.next());
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
			for(int i=0;i<blocks.length;i++) {
				if(res.length()!=0) {
					res += "|";
				}
				int blockType = getBlockType(blocks[i]);
				int x = blocks[i].getPositionX();
				int y = blocks[i].getPositionY();
				int color = getBlockColor(blocks[i].getColor());
				res += String.valueOf(blockType) + "," 
					+ String.valueOf(x) + ","
					+ String.valueOf(y) + ","
					+ String.valueOf(color);
			}
		}
		return res;
	}

	/**
	 * Get the index for a specific color
	 * @param c The Color to get an index for
	 * @return The index of the color
	 */
	protected int getBlockColor(Color c)
	{
		if(c.equals(Color.red)) {
			return 0;
		}else if(c.equals(Color.green)) {
			return 1;
		}else if(c.equals(Color.yellow)) {
			return 2;
		}else if(c.equals(Color.white)) {
			return 3;
		}else if(c.equals(Color.blue)) {
			return 4;
		}else {
			return 0;
		}
	}
	
	/**
	 * Get the Color for a specific color index
	 * @param c The index to get the Color for
	 * @return The color for the specified index
	 */
	protected Color getBlockColor(int c)
	{
		switch(c) {
			case 0:
				return Color.red;
			case 1:
				return Color.green;
			case 2:
				return Color.yellow;
			case 3:
				return Color.white;
			case 4:
				return Color.blue;
			default:
				return Color.red;
		}
	}

	/**
	 * Get the block type number for a specific block
	 * @param b The Block to get a block type number for
	 * @return The block type number for the block, see {@link BlockType}
	 */
	protected static int getBlockType(Block b)
	{
		if(b.getClass().getName().equals("erland.game.crackout.BlockSimple")) {
			return BlockType.bPlain;
		}else if(b.getClass().getName().equals("erland.game.crackout.BlockMultipleTimes")) {
			return BlockType.bMultiple;
		}else if(b.getClass().getName().equals("erland.game.crackout.BlockStatic")) {
			return BlockType.bStatic;
		}else if(b.getClass().getName().equals("erland.game.crackout.BlockFeature")) {
			switch(((BlockFeature)b).getFeature()) {
				case Feature.FeatureType.lockBat:
					return BlockType.fLockBat;
				case Feature.FeatureType.newBall:
					return BlockType.fNewBall;
				case Feature.FeatureType.increaseBallSpeed:
					return BlockType.fIncreaseBallSpeed;
				case Feature.FeatureType.decreaseBallSpeed:
					return BlockType.fDecreaseBallSpeed;
				case Feature.FeatureType.increaseBatSpeed:
					return BlockType.fIncreaseBatSpeed;
				case Feature.FeatureType.decreaseBatSpeed:
					return BlockType.fDecreaseBatSpeed;
				case Feature.FeatureType.doubleBat:
					return BlockType.fDoubleBat;
				case Feature.FeatureType.extraLife:
					return BlockType.fExtraLife;
				case Feature.FeatureType.safetyWall:
					return BlockType.fSafetyWall;
				case Feature.FeatureType.missile:
					return BlockType.fMissile;
				case Feature.FeatureType.largeBat:
					return BlockType.fLargeBat;
				case Feature.FeatureType.smallBat:
					return BlockType.fSmallBat;
				case Feature.FeatureType.bomb:
					return BlockType.fBomb;
				default:
					return BlockType.fLockBat;
			}
		}else if(b.getClass().getName().equals("erland.game.crackout.BlockFeatureHold")) {
			switch(((BlockFeature)b).getFeature()) {
				case Feature.FeatureType.lockBat:
					return BlockType.fLockBatHold;
				case Feature.FeatureType.newBall:
					return BlockType.fNewBallHold;
				case Feature.FeatureType.increaseBallSpeed:
					return BlockType.fIncreaseBallSpeedHold;
				case Feature.FeatureType.decreaseBallSpeed:
					return BlockType.fDecreaseBallSpeedHold;
				case Feature.FeatureType.increaseBatSpeed:
					return BlockType.fIncreaseBatSpeedHold;
				case Feature.FeatureType.decreaseBatSpeed:
					return BlockType.fDecreaseBatSpeedHold;
				case Feature.FeatureType.doubleBat:
					return BlockType.fDoubleBatHold;
				case Feature.FeatureType.extraLife:
					return BlockType.fExtraLifeHold;
				case Feature.FeatureType.safetyWall:
					return BlockType.fSafetyWallHold;
				case Feature.FeatureType.missile:
					return BlockType.fMissileHold;
				case Feature.FeatureType.largeBat:
					return BlockType.fLargeBatHold;
				case Feature.FeatureType.smallBat:
					return BlockType.fSmallBatHold;
				case Feature.FeatureType.bomb:
					return BlockType.fBombHold;
				default:
					return BlockType.fLockBatHold;
			}
		}else if(b.getClass().getName().equals("erland.game.crackout.BlockMonster")) {
			switch(((BlockMonster)b).getMonster()) {
				case Monster.MonsterType.bounceBlock:
					return BlockType.mBounceBlock;
				case Monster.MonsterType.bounceOnceBlock:
					return BlockType.mBounceOnceBlock;
				default:
					return BlockType.mBounceOnceBlock;
			}
		}else {
			return BlockType.bPlain;
		}
	}
	
	/**
	 * Creates a new block and returns it
	 * @param environment Game environment object
	 * @param cont Block container which the block should reside in
	 * @param x X position of the block (Square coordinate)
	 * @param y Y position of the block (Square coordinate)
	 * @param c Color of the block
	 * @param type Type of block to create, see {@link BlockType}
	 * @return The newly created block
	 */
	public static Block newBlock(GameEnvironmentInterface environment, BlockContainerInterface cont, int x, int y, Color c, int type)
	{
		Block block = null;
		switch(type) {
			case BlockType.fLockBat:
				block = new BlockFeature();
				((BlockFeature)block).init(environment,cont,squareSizeX, squareSizeY,x,y,c,Feature.FeatureType.lockBat);
				break;
			case BlockType.fNewBall:
				block = new BlockFeature();
				((BlockFeature)block).init(environment,cont,squareSizeX, squareSizeY,x,y,c,Feature.FeatureType.newBall);
				break;
			case BlockType.fIncreaseBallSpeed:
				block = new BlockFeature();
				((BlockFeature)block).init(environment,cont,squareSizeX, squareSizeY,x,y,c,Feature.FeatureType.increaseBallSpeed);
				break;
			case BlockType.fDecreaseBallSpeed:
				block = new BlockFeature();
				((BlockFeature)block).init(environment,cont,squareSizeX, squareSizeY,x,y,c,Feature.FeatureType.decreaseBallSpeed);
				break;
			case BlockType.fIncreaseBatSpeed:
				block = new BlockFeature();
				((BlockFeature)block).init(environment,cont,squareSizeX, squareSizeY,x,y,c,Feature.FeatureType.increaseBatSpeed);
				break;
			case BlockType.fDecreaseBatSpeed:
				block = new BlockFeature();
				((BlockFeature)block).init(environment,cont,squareSizeX, squareSizeY,x,y,c,Feature.FeatureType.decreaseBatSpeed);
				break;
			case BlockType.fDoubleBat:
				block = new BlockFeature();
				((BlockFeature)block).init(environment,cont,squareSizeX, squareSizeY,x,y,c,Feature.FeatureType.doubleBat);
				break;
			case BlockType.fExtraLife:
				block = new BlockFeature();
				((BlockFeature)block).init(environment,cont,squareSizeX, squareSizeY,x,y,c,Feature.FeatureType.extraLife);
				break;
			case BlockType.fSafetyWall:
				block = new BlockFeature();
				((BlockFeature)block).init(environment,cont,squareSizeX, squareSizeY,x,y,c,Feature.FeatureType.safetyWall);
				break;
			case BlockType.fMissile:
				block = new BlockFeature();
				((BlockFeature)block).init(environment,cont,squareSizeX, squareSizeY,x,y,c,Feature.FeatureType.missile);
				break;
			case BlockType.fLargeBat:
				block = new BlockFeature();
				((BlockFeature)block).init(environment,cont,squareSizeX, squareSizeY,x,y,c,Feature.FeatureType.largeBat);
				break;
			case BlockType.fSmallBat:
				block = new BlockFeature();
				((BlockFeature)block).init(environment,cont,squareSizeX, squareSizeY,x,y,c,Feature.FeatureType.smallBat);
				break;
			case BlockType.fBomb:
				block = new BlockFeature();
				((BlockFeature)block).init(environment,cont,squareSizeX, squareSizeY,x,y,c,Feature.FeatureType.bomb);
				break;
			case BlockType.fLockBatHold:
				block = new BlockFeatureHold();
				((BlockFeatureHold)block).init(environment,cont,squareSizeX, squareSizeY,x,y,c,Feature.FeatureType.lockBat);
				break;
			case BlockType.fNewBallHold:
				block = new BlockFeatureHold();
				((BlockFeatureHold)block).init(environment,cont,squareSizeX, squareSizeY,x,y,c,Feature.FeatureType.newBall);
				break;
			case BlockType.fIncreaseBallSpeedHold:
				block = new BlockFeatureHold();
				((BlockFeatureHold)block).init(environment,cont,squareSizeX, squareSizeY,x,y,c,Feature.FeatureType.increaseBallSpeed);
				break;
			case BlockType.fDecreaseBallSpeedHold:
				block = new BlockFeatureHold();
				((BlockFeatureHold)block).init(environment,cont,squareSizeX, squareSizeY,x,y,c,Feature.FeatureType.decreaseBallSpeed);
				break;
			case BlockType.fIncreaseBatSpeedHold:
				block = new BlockFeatureHold();
				((BlockFeatureHold)block).init(environment,cont,squareSizeX, squareSizeY,x,y,c,Feature.FeatureType.increaseBatSpeed);
				break;
			case BlockType.fDecreaseBatSpeedHold:
				block = new BlockFeatureHold();
				((BlockFeatureHold)block).init(environment,cont,squareSizeX, squareSizeY,x,y,c,Feature.FeatureType.decreaseBatSpeed);
				break;
			case BlockType.fDoubleBatHold:
				block = new BlockFeatureHold();
				((BlockFeatureHold)block).init(environment,cont,squareSizeX, squareSizeY,x,y,c,Feature.FeatureType.doubleBat);
				break;
			case BlockType.fExtraLifeHold:
				block = new BlockFeatureHold();
				((BlockFeatureHold)block).init(environment,cont,squareSizeX, squareSizeY,x,y,c,Feature.FeatureType.extraLife);
				break;
			case BlockType.fSafetyWallHold:
				block = new BlockFeatureHold();
				((BlockFeatureHold)block).init(environment,cont,squareSizeX, squareSizeY,x,y,c,Feature.FeatureType.safetyWall);
				break;
			case BlockType.fMissileHold:
				block = new BlockFeatureHold();
				((BlockFeatureHold)block).init(environment,cont,squareSizeX, squareSizeY,x,y,c,Feature.FeatureType.missile);
				break;
			case BlockType.fLargeBatHold:
				block = new BlockFeatureHold();
				((BlockFeatureHold)block).init(environment,cont,squareSizeX, squareSizeY,x,y,c,Feature.FeatureType.largeBat);
				break;
			case BlockType.fSmallBatHold:
				block = new BlockFeatureHold();
				((BlockFeatureHold)block).init(environment,cont,squareSizeX, squareSizeY,x,y,c,Feature.FeatureType.smallBat);
				break;
			case BlockType.fBombHold:
				block = new BlockFeatureHold();
				((BlockFeatureHold)block).init(environment,cont,squareSizeX, squareSizeY,x,y,c,Feature.FeatureType.bomb);
				break;
			case BlockType.mBounceBlock:
				block = new BlockMonster();
				((BlockMonster)block).init(environment,cont,squareSizeX, squareSizeY,x,y,c,Monster.MonsterType.bounceBlock);
				break;
			case BlockType.mBounceOnceBlock:
				block = new BlockMonster();
				((BlockMonster)block).init(environment,cont,squareSizeX, squareSizeY,x,y,c,Monster.MonsterType.bounceOnceBlock);
				break;
			case BlockType.bPlain:
				block = new BlockSimple();
				((BlockSimple)block).init(environment,cont,squareSizeX, squareSizeY,x,y,c);
				break;
			case BlockType.bMultiple:
				block = new BlockMultipleTimes();
				((BlockMultipleTimes)block).init(environment,cont,squareSizeX, squareSizeY,x,y,c,2);
				break;
			case BlockType.bStatic:
				block = new BlockStatic();
				((BlockStatic)block).init(environment,cont,squareSizeX, squareSizeY,x,y);
				break;
			default:
				block = new BlockSimple();
				((BlockSimple)block).init(environment,cont,squareSizeX, squareSizeY,x,y,c);
				break;
		}
		return block;
	}
}