package erland.game.pipes;
import erland.game.*;
import erland.util.*;
/**
 * Represents a block like following:
 * XXX
 * XXX
 * XXX
 */
class PipeBlockPoolBig extends PipeBlock
{
	/** Image handler object */
	protected ImageHandlerInterface images;
	/** Indicates if the pool should be water filled from the start */
	protected boolean waterFilled;
	/** Indicates which side of the pool that is opened */
	protected int openDirection;
	
	/**
	 * Creates a new pipe block
	 * @param images Image handler object
	 */
	public PipeBlockPoolBig(ImageHandlerInterface images, boolean waterFilled, int openDirection) 
	{
		this.images = images;
		this.waterFilled = waterFilled;
		this.openDirection = openDirection;
	}
	public void init(BlockContainerInterface cont, int x, int y)
	{
		
		PipePart[][] parts = new PipePart[size][size];
		parts[0][0] = new PipePartSimple(images,"","bigPoolLeftUp.gif",waterFilled,false,!waterFilled,false,!waterFilled);
		if(openDirection==Direction.UP) {
			parts[1][0] = new PipePartSimple(images,"","bigPoolUp.gif",waterFilled,!waterFilled,!waterFilled,true,!waterFilled);
		}else {
			parts[1][0] = new PipePartSimple(images,"","bigPoolUpClose.gif",waterFilled,!waterFilled,!waterFilled,false,!waterFilled);
		}
		parts[2][0] = new PipePartSimple(images,"","bigPoolRightUp.gif",waterFilled,!waterFilled,false,false,!waterFilled);
		if(openDirection==Direction.LEFT) {
			parts[0][1] = new PipePartSimple(images,"","bigPoolLeft.gif",waterFilled,true,!waterFilled,!waterFilled,!waterFilled);
		}else {
			parts[0][1] = new PipePartSimple(images,"","bigPoolLeftClose.gif",waterFilled,false,!waterFilled,!waterFilled,!waterFilled);
		}
		parts[1][1] = new PipePartSimple(images,"","",waterFilled,!waterFilled,!waterFilled,!waterFilled,!waterFilled);
		if(openDirection==Direction.RIGHT) {
			parts[2][1] = new PipePartSimple(images,"","bigPoolRight.gif",waterFilled,!waterFilled,true,!waterFilled,!waterFilled);
		}else {
			parts[2][1] = new PipePartSimple(images,"","bigPoolRightClose.gif",waterFilled,!waterFilled,false,!waterFilled,!waterFilled);
		}
		parts[0][2] = new PipePartSimple(images,"","bigPoolLeftDown.gif",waterFilled,false,!waterFilled,!waterFilled,false);
		if(openDirection==Direction.DOWN) {
			parts[1][2] = new PipePartSimple(images,"","bigPoolDown.gif",waterFilled,!waterFilled,!waterFilled,!waterFilled,true);
		}else {
			parts[1][2] = new PipePartSimple(images,"","bigPoolDownClose.gif",waterFilled,!waterFilled,!waterFilled,!waterFilled,false);
		}
		parts[2][2] = new PipePartSimple(images,"","bigPoolRightDown.gif",waterFilled,!waterFilled,false,!waterFilled,false);
		super.init(cont,parts,x,y);
		if(waterFilled) {
			initWater(0,0,Direction.RIGHT);
			initWater(1,0,Direction.UP);
			initWater(2,0,Direction.LEFT);
			initWater(0,1,Direction.LEFT);
			initWater(1,1,Direction.RIGHT);
			initWater(2,1,Direction.RIGHT);
			initWater(0,2,Direction.RIGHT);
			initWater(1,2,Direction.DOWN);
			initWater(2,2,Direction.LEFT);
		}
	}
	protected boolean addFilledPart(int x, int y)
	{
		return false;
	}
	/**
	 * Indicates if this block was filled with water from the beginning
	 * @return true/false (Water filled/Not water filled)
	 */
	public boolean getWaterFilled()
	{
		return waterFilled;
	}
	
	protected int getScore(int x, int y)
	{
		if(waterFilled) {
			return 0;
		}else {
			return super.getScore(x,y);
		}
	}

	protected boolean isMovable()
	{
		return false;
	}

	/**
	 * Check which direction the pool is open against
	 * @return The direction which the pool is open against
	 */
	public int getOpenDirection() {
		return openDirection;
	}
}
