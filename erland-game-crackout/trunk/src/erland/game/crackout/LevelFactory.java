package erland.game.crackout;
import erland.util.*;
import erland.game.*;
import java.util.*;
import java.awt.Color;

class LevelFactory
{
	static final int MAX_DEFAULT_LEVELS=8;
	
	LinkedList blocks;
	LinkedList levels;
	
	ImageHandlerInterface images;
	int offsetX;
	int offsetY;
	int squareSize;
	int sizeX;
	int sizeY;
	ParameterValueStorageInterface cookies;
	int maxLevel;
	BlockContainerInterface cont;

	Block[] getFullBlockList()
	{
		blocks = new LinkedList();
		blocks.clear();
		
		newBlock(blocks.size()%sizeX,blocks.size()/sizeX,Color.yellow,Level.bPlain);
		newBlock(blocks.size()%sizeX,blocks.size()/sizeX,Color.red,Level.bMultiple);
		newBlock(blocks.size()%sizeX,blocks.size()/sizeX,Color.green,Level.bStatic);

		newBlock(blocks.size()%sizeX,blocks.size()/sizeX,Color.green,Level.fLockBat);
		newBlock(blocks.size()%sizeX,blocks.size()/sizeX,Color.white,Level.fNewBall);
		newBlock(blocks.size()%sizeX,blocks.size()/sizeX,Color.blue,Level.fIncreaseBallSpeed);
		newBlock(blocks.size()%sizeX,blocks.size()/sizeX,Color.yellow,Level.fDecreaseBallSpeed);
		newBlock(blocks.size()%sizeX,blocks.size()/sizeX,Color.red,Level.fIncreaseBatSpeed);
		newBlock(blocks.size()%sizeX,blocks.size()/sizeX,Color.green,Level.fDecreaseBatSpeed);
		newBlock(blocks.size()%sizeX,blocks.size()/sizeX,Color.white,Level.fDoubleBat);
		newBlock(blocks.size()%sizeX,blocks.size()/sizeX,Color.blue,Level.fExtraLife);
		newBlock(blocks.size()%sizeX,blocks.size()/sizeX,Color.yellow,Level.fSafetyWall);
		newBlock(blocks.size()%sizeX,blocks.size()/sizeX,Color.red,Level.fMissile);
		newBlock(blocks.size()%sizeX,blocks.size()/sizeX,Color.green,Level.fLargeBat);
		newBlock(blocks.size()%sizeX,blocks.size()/sizeX,Color.white,Level.fSmallBat);
		newBlock(blocks.size()%sizeX,blocks.size()/sizeX,Color.blue,Level.fBomb);

		newBlock(blocks.size()%sizeX,blocks.size()/sizeX,Color.green,Level.fLockBatHold);
		newBlock(blocks.size()%sizeX,blocks.size()/sizeX,Color.white,Level.fNewBallHold);
		newBlock(blocks.size()%sizeX,blocks.size()/sizeX,Color.blue,Level.fIncreaseBallSpeedHold);
		newBlock(blocks.size()%sizeX,blocks.size()/sizeX,Color.yellow,Level.fDecreaseBallSpeedHold);
		newBlock(blocks.size()%sizeX,blocks.size()/sizeX,Color.red,Level.fIncreaseBatSpeedHold);
		newBlock(blocks.size()%sizeX,blocks.size()/sizeX,Color.green,Level.fDecreaseBatSpeedHold);
		newBlock(blocks.size()%sizeX,blocks.size()/sizeX,Color.white,Level.fDoubleBatHold);
		newBlock(blocks.size()%sizeX,blocks.size()/sizeX,Color.blue,Level.fExtraLifeHold);
		newBlock(blocks.size()%sizeX,blocks.size()/sizeX,Color.yellow,Level.fSafetyWallHold);
		newBlock(blocks.size()%sizeX,blocks.size()/sizeX,Color.red,Level.fMissileHold);
		newBlock(blocks.size()%sizeX,blocks.size()/sizeX,Color.green,Level.fLargeBatHold);
		newBlock(blocks.size()%sizeX,blocks.size()/sizeX,Color.white,Level.fSmallBatHold);
		newBlock(blocks.size()%sizeX,blocks.size()/sizeX,Color.blue,Level.fBombHold);

		newBlock(blocks.size()%sizeX,blocks.size()/sizeX,Color.blue,Level.mBounceBlock);
		newBlock(blocks.size()%sizeX,blocks.size()/sizeX,Color.yellow,Level.mBounceOnceBlock);

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
	
	void newBlock(int x, int y, Color c, int type)
	{
		x*=Level.squareSizeX;
		y*=Level.squareSizeY;

		Block block = Level.newBlock(images, cont, x,y,c,type);
		if(block!=null) {
			blocks.add(block);
		}
	}
	LevelFactory(ImageHandlerInterface images, ParameterValueStorageInterface cookies, BlockContainerInterface cont)
	{
		this.images = images;
		this.cont = cont;
		this.offsetX = cont.getOffsetX();
		this.offsetY = cont.getOffsetY();
		this.squareSize = cont.getSquareSize();
		this.sizeX = cont.getSizeX();
		this.sizeY = cont.getSizeY();
		this.cookies = cookies;
		this.levels = new LinkedList();
		loadAll();
	}
	
	Block[] getLevel(int level)
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
	
	Block[] getDefaultLevel(int level)
	{
		blocks = new LinkedList();
		blocks.clear();
		switch(level) {
			case 1:
				newBlock(1,1,Color.yellow,Level.bPlain);
				newBlock(3,1,Color.yellow,Level.bPlain);
				newBlock(6,1,Color.yellow,Level.bPlain);
				newBlock(8,1,Color.yellow,Level.bPlain);
				newBlock(1,2,Color.red,Level.bPlain);
				newBlock(3,2,Color.red,Level.bPlain);
				newBlock(6,2,Color.red,Level.bPlain);
				newBlock(8,2,Color.red,Level.bPlain);
				newBlock(1,3,Color.green,Level.bPlain);
				newBlock(2,3,Color.green,Level.bPlain);
				newBlock(3,3,Color.green,Level.bPlain);
				newBlock(4,3,Color.green,Level.bPlain);
				newBlock(5,3,Color.green,Level.bPlain);
				newBlock(6,3,Color.green,Level.bPlain);
				newBlock(7,3,Color.green,Level.bPlain);
				newBlock(8,3,Color.green,Level.bPlain);
				break;
			case 2:
				newBlock(0,4,Color.red,Level.bPlain);
				newBlock(1,4,Color.red,Level.bPlain);
				newBlock(2,4,Color.red,Level.bPlain);
				newBlock(3,4,Color.red,Level.bPlain);
				newBlock(4,4,Color.red,Level.bPlain);
				newBlock(5,4,Color.red,Level.bPlain);
				newBlock(6,4,Color.red,Level.bPlain);
				newBlock(7,4,Color.red,Level.bPlain);
				newBlock(8,4,Color.red,Level.bPlain);
				newBlock(9,4,Color.red,Level.bPlain);
				newBlock(8,3,Color.red,Level.bPlain);
				newBlock(1,3,Color.red,Level.bPlain);
				newBlock(7,2,Color.red,Level.bPlain);
				newBlock(2,2,Color.red,Level.bPlain);
				newBlock(6,1,Color.red,Level.bPlain);
				newBlock(3,1,Color.red,Level.bPlain);
				newBlock(5,0,Color.red,Level.bPlain);
				newBlock(4,0,Color.red,Level.bPlain);
				newBlock(4,1,Color.yellow,Level.fExtraLife);
				newBlock(5,1,Color.yellow,Level.fDoubleBat);
				newBlock(3,2,Color.green,Level.bPlain);
				newBlock(4,2,Color.green,Level.bPlain);
				newBlock(5,2,Color.green,Level.bPlain);
				newBlock(6,2,Color.green,Level.bPlain);
				newBlock(2,3,Color.blue,Level.bPlain);
				newBlock(3,3,Color.blue,Level.bPlain);
				newBlock(4,3,Color.blue,Level.bPlain);
				newBlock(5,3,Color.blue,Level.bPlain);
				newBlock(6,3,Color.blue,Level.bPlain);
				newBlock(7,3,Color.blue,Level.bPlain);
				break;
			case 3:
				newBlock(2,5,Color.red,Level.bPlain);
				newBlock(3,5,Color.red,Level.bPlain);
				newBlock(4,5,Color.green,Level.bMultiple);
				newBlock(5,5,Color.green,Level.bMultiple);
				newBlock(6,5,Color.red,Level.bPlain);
				newBlock(7,5,Color.red,Level.bPlain);
				newBlock(6,4,Color.red,Level.bStatic);
				newBlock(7,3,Color.red,Level.bStatic);
				newBlock(8,2,Color.red,Level.bStatic);
				newBlock(3,4,Color.red,Level.bStatic);
				newBlock(2,3,Color.red,Level.bStatic);
				newBlock(1,2,Color.red,Level.bStatic);
				newBlock(2,1,Color.green,Level.bMultiple);
				newBlock(3,1,Color.red,Level.bPlain);
				newBlock(4,1,Color.red,Level.bPlain);
				newBlock(5,1,Color.red,Level.bPlain);
				newBlock(6,1,Color.red,Level.bPlain);
				newBlock(7,1,Color.green,Level.bMultiple);
				newBlock(4,2,Color.white,Level.mBounceOnceBlock);
				newBlock(5,2,Color.white,Level.mBounceOnceBlock);
				break;
			case 4:
				newBlock(1,1,Color.green,Level.bPlain);
				newBlock(2,1,Color.green,Level.bPlain);
				newBlock(3,1,Color.green,Level.bPlain);
				newBlock(1,2,Color.yellow,Level.bPlain);
				newBlock(1,3,Color.blue,Level.bPlain);
				newBlock(2,3,Color.blue,Level.bPlain);
				newBlock(1,4,Color.white,Level.bPlain);
				newBlock(1,5,Color.red,Level.bPlain);
				newBlock(2,5,Color.red,Level.bPlain);
				newBlock(3,5,Color.red,Level.bPlain);
				newBlock(5,1,Color.green,Level.bPlain);
				newBlock(6,1,Color.green,Level.bPlain);
				newBlock(7,1,Color.green,Level.bPlain);
				newBlock(6,2,Color.yellow,Level.bPlain);
				newBlock(6,3,Color.blue,Level.bPlain);
				newBlock(6,4,Color.white,Level.bPlain);
				newBlock(5,5,Color.red,Level.bPlain);
				newBlock(6,5,Color.red,Level.bPlain);
				newBlock(7,5,Color.red,Level.bPlain);
				break;
			case 5:
				newBlock(0,4,Color.red,Level.fIncreaseBallSpeed);
				newBlock(1,4,Color.red,Level.fIncreaseBallSpeed);
				newBlock(2,4,Color.red,Level.fIncreaseBallSpeed);
				newBlock(3,4,Color.red,Level.fIncreaseBallSpeed);
				newBlock(4,4,Color.red,Level.fIncreaseBallSpeed);
				newBlock(5,4,Color.green,Level.fDecreaseBallSpeed);
				newBlock(6,4,Color.green,Level.fDecreaseBallSpeed);
				newBlock(7,4,Color.green,Level.fDecreaseBallSpeed);
				newBlock(8,4,Color.green,Level.fDecreaseBallSpeed);
				newBlock(9,4,Color.green,Level.fDecreaseBallSpeed);
				newBlock(0,2,Color.red,Level.fIncreaseBallSpeed);
				newBlock(1,2,Color.red,Level.fIncreaseBallSpeed);
				newBlock(2,2,Color.red,Level.fIncreaseBallSpeed);
				newBlock(3,2,Color.red,Level.fIncreaseBallSpeed);
				newBlock(4,2,Color.red,Level.fIncreaseBallSpeed);
				newBlock(5,2,Color.green,Level.fDecreaseBallSpeed);
				newBlock(6,2,Color.green,Level.fDecreaseBallSpeed);
				newBlock(7,2,Color.green,Level.fDecreaseBallSpeed);
				newBlock(8,2,Color.green,Level.fDecreaseBallSpeed);
				newBlock(9,2,Color.green,Level.fDecreaseBallSpeed);
				newBlock(0,0,Color.yellow,Level.fExtraLife);
				newBlock(1,0,Color.yellow,Level.fExtraLife);
				newBlock(2,0,Color.yellow,Level.fExtraLife);
				newBlock(3,0,Color.yellow,Level.fExtraLife);
				newBlock(4,0,Color.yellow,Level.fExtraLife);
				newBlock(5,0,Color.yellow,Level.fExtraLife);
				newBlock(6,0,Color.yellow,Level.fExtraLife);
				newBlock(7,0,Color.yellow,Level.fExtraLife);
				newBlock(8,0,Color.yellow,Level.fExtraLife);
				newBlock(9,0,Color.yellow,Level.fExtraLife);
				break;
			case 6:
				newBlock(1,4,Color.green,Level.mBounceBlock);
				newBlock(2,4,Color.red,Level.mBounceBlock);
				newBlock(3,4,Color.yellow,Level.mBounceBlock);
				newBlock(4,4,Color.yellow,Level.bStatic);
				newBlock(5,4,Color.yellow,Level.bStatic);
				newBlock(6,4,Color.yellow,Level.mBounceBlock);
				newBlock(7,4,Color.red,Level.mBounceBlock);
				newBlock(8,4,Color.green,Level.mBounceBlock);
				break;
			case 7:
				newBlock(1,5,Color.green,Level.fLockBat);
				newBlock(2,5,Color.red,Level.fNewBall);
				newBlock(3,5,Color.green,Level.fIncreaseBallSpeed);
				newBlock(4,5,Color.red,Level.fDecreaseBallSpeed);
				newBlock(5,5,Color.green,Level.fIncreaseBatSpeed);
				newBlock(6,5,Color.red,Level.fDecreaseBatSpeed);
				newBlock(7,5,Color.green,Level.fDoubleBat);
				newBlock(8,5,Color.red,Level.fExtraLife);
				break;
			case 8:
				newBlock(0,4,Color.green,Level.fNewBall);
				newBlock(1,4,Color.green,Level.fNewBall);
				newBlock(2,4,Color.green,Level.fNewBall);
				newBlock(3,4,Color.green,Level.fNewBall);
				newBlock(4,4,Color.green,Level.fNewBall);
				newBlock(5,4,Color.green,Level.fNewBall);
				newBlock(6,4,Color.green,Level.fNewBall);
				newBlock(7,4,Color.green,Level.fNewBall);
				newBlock(8,4,Color.green,Level.fNewBall);
				newBlock(9,4,Color.green,Level.fNewBall);
				newBlock(0,5,Color.green,Level.fNewBall);
				newBlock(1,5,Color.green,Level.fNewBall);
				newBlock(2,5,Color.green,Level.fNewBall);
				newBlock(3,5,Color.green,Level.fNewBall);
				newBlock(4,5,Color.green,Level.fNewBall);
				newBlock(5,5,Color.green,Level.fNewBall);
				newBlock(6,5,Color.green,Level.fNewBall);
				newBlock(7,5,Color.green,Level.fNewBall);
				newBlock(8,5,Color.green,Level.fNewBall);
				newBlock(9,5,Color.green,Level.fNewBall);
				newBlock(3,1,Color.yellow,Level.bStatic);
				newBlock(3,2,Color.yellow,Level.bStatic);
				newBlock(3,3,Color.yellow,Level.bStatic);
				newBlock(4,3,Color.yellow,Level.bStatic);
				newBlock(5,3,Color.yellow,Level.bStatic);
				newBlock(5,1,Color.yellow,Level.bStatic);
				newBlock(5,2,Color.yellow,Level.bStatic);
				newBlock(4,2,Color.yellow,Level.bMultiple);
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
	int getLastLevel()
	{
		return maxLevel;
	}
	
	boolean loadAll()
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
	
	boolean loadLevel(int level)
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
	
	boolean newLevel(int level)
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

	boolean deleteLevel(int level)
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
	
	boolean saveLevel(int level) 
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
	
	boolean storeLevel(int level, Block blocks[])
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
	boolean saveAll()
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