package erland.game.crackout;
import erland.game.*;
import java.awt.*;
import java.util.*;

class Level
{
	static final int fLockBat=1;
	static final int fNewBall=2;
	static final int fIncreaseBallSpeed=3;
	static final int fDecreaseBallSpeed=4;
	static final int fIncreaseBatSpeed=5;
	static final int fDecreaseBatSpeed=6;
	static final int fDoubleBat=7;
	static final int fExtraLife=8;
	static final int fSafetyWall=9;
	static final int fMissile=10;
	static final int fLargeBat=11;
	static final int fSmallBat=12;
	static final int fBomb=13;

	static final int fLockBatHold=20;
	static final int fNewBallHold=21;
	static final int fIncreaseBallSpeedHold=22;
	static final int fDecreaseBallSpeedHold=23;
	static final int fIncreaseBatSpeedHold=24;
	static final int fDecreaseBatSpeedHold=25;
	static final int fDoubleBatHold=26;
	static final int fExtraLifeHold=27;
	static final int fSafetyWallHold=28;
	static final int fMissileHold=29;
	static final int fLargeBatHold=30;
	static final int fSmallBatHold=31;
	static final int fBombHold=32;

	static final int mBounceBlock=50;
	static final int mBounceOnceBlock=51;
	static final int bPlain=70;
	static final int bMultiple=71;
	static final int bStatic=72;

	static final int squareSizeX=2;
	static final int squareSizeY=1;
	Block blocks[];
	int id;
	ImageHandlerInterface images;
	BlockContainerInterface cont;
	
	Level(ImageHandlerInterface images, BlockContainerInterface cont)
	{
		blocks = new Block[0];
		id=0;
		this.images = images;
		this.cont = cont;
	}
	
	void setData(Block blocks[])
	{
		this.blocks = blocks;
	}
	
	void setId(int id)
	{
		this.id = id;
	}
		
	int getId()
	{
		return id;
	}
	Block[] getData()
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


	boolean load(int id, String data)
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
				Block block = newBlock(images, cont,x,y,getBlockColor(color),type);
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

	String getDataAsString()
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

	int getBlockColor(Color c)
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
	
	Color getBlockColor(int c)
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

	int getBlockType(Block b)
	{
		if(b.getClass().getName().equals("erland.BlockSimple")) {
			return bPlain;
		}else if(b.getClass().getName().equals("erland.BlockMultipleTimes")) {
			return bMultiple;
		}else if(b.getClass().getName().equals("erland.BlockStatic")) {
			return bStatic;
		}else if(b.getClass().getName().equals("erland.BlockFeature")) {
			switch(((BlockFeature)b).getFeature()) {
				case Feature.FeatureType.lockBat:
					return fLockBat;
				case Feature.FeatureType.newBall:
					return fNewBall;
				case Feature.FeatureType.increaseBallSpeed:
					return fIncreaseBallSpeed;
				case Feature.FeatureType.decreaseBallSpeed:
					return fDecreaseBallSpeed;
				case Feature.FeatureType.increaseBatSpeed:
					return fIncreaseBatSpeed;
				case Feature.FeatureType.decreaseBatSpeed:
					return fDecreaseBatSpeed;
				case Feature.FeatureType.doubleBat:
					return fDoubleBat;
				case Feature.FeatureType.extraLife:
					return fExtraLife;
				case Feature.FeatureType.safetyWall:
					return fSafetyWall;
				case Feature.FeatureType.missile:
					return fMissile;
				case Feature.FeatureType.largeBat:
					return fLargeBat;
				case Feature.FeatureType.smallBat:
					return fSmallBat;
				case Feature.FeatureType.bomb:
					return fBomb;
				default:
					return fLockBat;
			}
		}else if(b.getClass().getName().equals("erland.BlockFeatureHold")) {
			switch(((BlockFeature)b).getFeature()) {
				case Feature.FeatureType.lockBat:
					return fLockBatHold;
				case Feature.FeatureType.newBall:
					return fNewBallHold;
				case Feature.FeatureType.increaseBallSpeed:
					return fIncreaseBallSpeedHold;
				case Feature.FeatureType.decreaseBallSpeed:
					return fDecreaseBallSpeedHold;
				case Feature.FeatureType.increaseBatSpeed:
					return fIncreaseBatSpeedHold;
				case Feature.FeatureType.decreaseBatSpeed:
					return fDecreaseBatSpeedHold;
				case Feature.FeatureType.doubleBat:
					return fDoubleBatHold;
				case Feature.FeatureType.extraLife:
					return fExtraLifeHold;
				case Feature.FeatureType.safetyWall:
					return fSafetyWallHold;
				case Feature.FeatureType.missile:
					return fMissileHold;
				case Feature.FeatureType.largeBat:
					return fLargeBatHold;
				case Feature.FeatureType.smallBat:
					return fSmallBatHold;
				case Feature.FeatureType.bomb:
					return fBombHold;
				default:
					return fLockBatHold;
			}
		}else if(b.getClass().getName().equals("erland.BlockMonster")) {
			switch(((BlockMonster)b).getMonster()) {
				case Monster.MonsterType.bounceBlock:
					return mBounceBlock;
				case Monster.MonsterType.bounceOnceBlock:
					return mBounceOnceBlock;
				default:
					return mBounceOnceBlock;
			}
		}else {
			return bPlain;
		}
	}

	static Block newBlock(ImageHandlerInterface images, BlockContainerInterface cont, int x, int y, Color c, int type)
	{
		Block block = null;
		switch(type) {
			case fLockBat:
				block = new BlockFeature();
				((BlockFeature)block).init(images,cont,squareSizeX, squareSizeY,x,y,c,Feature.FeatureType.lockBat);
				break;
			case fNewBall:
				block = new BlockFeature();
				((BlockFeature)block).init(images,cont,squareSizeX, squareSizeY,x,y,c,Feature.FeatureType.newBall);
				break;
			case fIncreaseBallSpeed:
				block = new BlockFeature();
				((BlockFeature)block).init(images,cont,squareSizeX, squareSizeY,x,y,c,Feature.FeatureType.increaseBallSpeed);
				break;
			case fDecreaseBallSpeed:
				block = new BlockFeature();
				((BlockFeature)block).init(images,cont,squareSizeX, squareSizeY,x,y,c,Feature.FeatureType.decreaseBallSpeed);
				break;
			case fIncreaseBatSpeed:
				block = new BlockFeature();
				((BlockFeature)block).init(images,cont,squareSizeX, squareSizeY,x,y,c,Feature.FeatureType.increaseBatSpeed);
				break;
			case fDecreaseBatSpeed:
				block = new BlockFeature();
				((BlockFeature)block).init(images,cont,squareSizeX, squareSizeY,x,y,c,Feature.FeatureType.decreaseBatSpeed);
				break;
			case fDoubleBat:
				block = new BlockFeature();
				((BlockFeature)block).init(images,cont,squareSizeX, squareSizeY,x,y,c,Feature.FeatureType.doubleBat);
				break;
			case fExtraLife:
				block = new BlockFeature();
				((BlockFeature)block).init(images,cont,squareSizeX, squareSizeY,x,y,c,Feature.FeatureType.extraLife);
				break;
			case fSafetyWall:
				block = new BlockFeature();
				((BlockFeature)block).init(images,cont,squareSizeX, squareSizeY,x,y,c,Feature.FeatureType.safetyWall);
				break;
			case fMissile:
				block = new BlockFeature();
				((BlockFeature)block).init(images,cont,squareSizeX, squareSizeY,x,y,c,Feature.FeatureType.missile);
				break;
			case fLargeBat:
				block = new BlockFeature();
				((BlockFeature)block).init(images,cont,squareSizeX, squareSizeY,x,y,c,Feature.FeatureType.largeBat);
				break;
			case fSmallBat:
				block = new BlockFeature();
				((BlockFeature)block).init(images,cont,squareSizeX, squareSizeY,x,y,c,Feature.FeatureType.smallBat);
				break;
			case fBomb:
				block = new BlockFeature();
				((BlockFeature)block).init(images,cont,squareSizeX, squareSizeY,x,y,c,Feature.FeatureType.bomb);
				break;
			case fLockBatHold:
				block = new BlockFeatureHold();
				((BlockFeatureHold)block).init(images,cont,squareSizeX, squareSizeY,x,y,c,Feature.FeatureType.lockBat);
				break;
			case fNewBallHold:
				block = new BlockFeatureHold();
				((BlockFeatureHold)block).init(images,cont,squareSizeX, squareSizeY,x,y,c,Feature.FeatureType.newBall);
				break;
			case fIncreaseBallSpeedHold:
				block = new BlockFeatureHold();
				((BlockFeatureHold)block).init(images,cont,squareSizeX, squareSizeY,x,y,c,Feature.FeatureType.increaseBallSpeed);
				break;
			case fDecreaseBallSpeedHold:
				block = new BlockFeatureHold();
				((BlockFeatureHold)block).init(images,cont,squareSizeX, squareSizeY,x,y,c,Feature.FeatureType.decreaseBallSpeed);
				break;
			case fIncreaseBatSpeedHold:
				block = new BlockFeatureHold();
				((BlockFeatureHold)block).init(images,cont,squareSizeX, squareSizeY,x,y,c,Feature.FeatureType.increaseBatSpeed);
				break;
			case fDecreaseBatSpeedHold:
				block = new BlockFeatureHold();
				((BlockFeatureHold)block).init(images,cont,squareSizeX, squareSizeY,x,y,c,Feature.FeatureType.decreaseBatSpeed);
				break;
			case fDoubleBatHold:
				block = new BlockFeatureHold();
				((BlockFeatureHold)block).init(images,cont,squareSizeX, squareSizeY,x,y,c,Feature.FeatureType.doubleBat);
				break;
			case fExtraLifeHold:
				block = new BlockFeatureHold();
				((BlockFeatureHold)block).init(images,cont,squareSizeX, squareSizeY,x,y,c,Feature.FeatureType.extraLife);
				break;
			case fSafetyWallHold:
				block = new BlockFeatureHold();
				((BlockFeatureHold)block).init(images,cont,squareSizeX, squareSizeY,x,y,c,Feature.FeatureType.safetyWall);
				break;
			case fMissileHold:
				block = new BlockFeatureHold();
				((BlockFeatureHold)block).init(images,cont,squareSizeX, squareSizeY,x,y,c,Feature.FeatureType.missile);
				break;
			case fLargeBatHold:
				block = new BlockFeatureHold();
				((BlockFeatureHold)block).init(images,cont,squareSizeX, squareSizeY,x,y,c,Feature.FeatureType.largeBat);
				break;
			case fSmallBatHold:
				block = new BlockFeatureHold();
				((BlockFeatureHold)block).init(images,cont,squareSizeX, squareSizeY,x,y,c,Feature.FeatureType.smallBat);
				break;
			case fBombHold:
				block = new BlockFeatureHold();
				((BlockFeatureHold)block).init(images,cont,squareSizeX, squareSizeY,x,y,c,Feature.FeatureType.bomb);
				break;
			case mBounceBlock:
				block = new BlockMonster();
				((BlockMonster)block).init(images,cont,squareSizeX, squareSizeY,x,y,c,Monster.MonsterType.bounceBlock);
				break;
			case mBounceOnceBlock:
				block = new BlockMonster();
				((BlockMonster)block).init(images,cont,squareSizeX, squareSizeY,x,y,c,Monster.MonsterType.bounceOnceBlock);
				break;
			case bPlain:
				block = new BlockSimple();
				((BlockSimple)block).init(images,cont,squareSizeX, squareSizeY,x,y,c);
				break;
			case bMultiple:
				block = new BlockMultipleTimes();
				((BlockMultipleTimes)block).init(images,cont,squareSizeX, squareSizeY,x,y,c,2);
				break;
			case bStatic:
				block = new BlockStatic();
				((BlockStatic)block).init(images,cont,squareSizeX, squareSizeY,x,y);
				break;
			default:
				block = new BlockSimple();
				((BlockSimple)block).init(images,cont,squareSizeX, squareSizeY,x,y,c);
				break;
		}
		return block;
	}
}