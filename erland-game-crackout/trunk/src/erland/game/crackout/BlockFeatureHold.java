package erland.game.crackout;

import erland.game.*;
import erland.util.*;
import java.awt.*;

/**
 * Implements a block that will start to flash a feature when
 * it is hit the first time and the get the feature you need
 * to hit the block a second time before it stops to flash the
 * feature
 */
class BlockFeatureHold extends BlockFeature
{
	/** 
	 * Number of times the block must be hit before it disappears.
	 * This is a counter that will be decreased every time the block is hit
	 */
	protected int hitCount;
	/** 
	 * Counter that is decreased continously after the {@link #hitCount} has been decreased to 1.
	 * The feature will start blinking when this counter starts to decrease and will disappear when
	 * this counter has been decreased to 0
	 */
	protected int featureDieCount;
	/** Counter that handles the blinking of the feature */
	protected int featureBlinkCount;
	/** The speed of the feature blink */
	protected static final int BLINK_SPEED=10;
	
	/**
	 * Creates an instance of the block
	 */
	public BlockFeatureHold()
	{
		hitCount=2;
		featureDieCount=0;
	}
	
	public Object clone()
    	throws CloneNotSupportedException
    {
    	BlockFeatureHold obj = new BlockFeatureHold();
		obj.init(images, cont, sizeX, sizeY, posX, posY, color, feature);
		return obj;
    }

	/**
	 * Initialize block
	 * @param images Image handler object
	 * @param cont Reference to block container object
	 * @param sizeX Width of block (Number of squares)
	 * @param sizeY Height of block (Number of squares)
	 * @param posX X position of block (Square coordinates)
	 * @param posY Y position of block (Square coordinates)
	 * @param color Color of the block
	 * @param featureType Type of feature that should be dropped when the block is hit, see {@link Feature.FeatureType}
	 */
	public void init(ImageHandlerInterface images, BlockContainerInterface cont, int sizeX, int sizeY, int posX, int posY, Color color, int featureType)
	{
		super.init(images,cont,sizeX, sizeY,posX,posY,color,featureType);
		hitCount=2;
		featureDieCount=0;
		description += ", needs 2 hits, second hit generates feature";
	}
	
	/**
	 * Draws the block after the feature has stopped blinking and during the blinking when
	 * the feature is not visible
	 * @param g Graphics object to draw on
	 */
	protected void drawSimple(Graphics g)
	{
		if(active) {
			drawBlock(g,color,0xFF);
		}else {
			if(dieCount>10) {
				dieCount-=10;
				drawBlock(g,color,dieCount);
			}
		}
	}
	public void draw(Graphics g)
	{
		if(hitCount>1) {
			super.draw(g);
		}else if(hitCount==1) {
			if(featureDieCount>0) {
				featureDieCount--;
				if(featureBlinkCount<BLINK_SPEED) {
					featureBlinkCount++;
					super.draw(g);
				}else if(featureBlinkCount<(BLINK_SPEED*2)) {
					featureBlinkCount++;
					drawSimple(g);
				}else {
					featureBlinkCount=0;
					drawSimple(g);
				}
			}else {
				drawSimple(g);
			}
		}else {
			drawSimple(g);
		}
	}

	public void handleCollision(ActionInterface a)
	{
		hitCount--;
		if(hitCount==1) {
			featureDieCount=256;
			featureBlinkCount=0;
		}
		if(hitCount<=0) {
			active=false;
			if(featureDieCount>0) {
				switch(feature) {
					case Feature.FeatureType.lockBat:
						a.LockBat();
						break;
					case Feature.FeatureType.newBall:
						a.NewBall();
						break;
					case Feature.FeatureType.increaseBallSpeed:
						a.IncreaseBallSpeed();
						break;
					case Feature.FeatureType.decreaseBallSpeed:
						a.DecreaseBallSpeed();
						break;
					case Feature.FeatureType.increaseBatSpeed:
						a.IncreaseBatSpeed();
						break;
					case Feature.FeatureType.decreaseBatSpeed:
						a.DecreaseBatSpeed();
						break;
					case Feature.FeatureType.doubleBat:
						a.DoubleBat();
						break;
					case Feature.FeatureType.extraLife:
						a.ExtraLife();
						break;
					case Feature.FeatureType.safetyWall:
						a.SafetyWall();
						break;
					case Feature.FeatureType.missile:
						a.NewMissile();
						break;
					case Feature.FeatureType.largeBat:
						a.LargeBat();
						break;
					case Feature.FeatureType.smallBat:
						a.SmallBat();
						break;
					case Feature.FeatureType.bomb:
						a.Explode(posX*cont.getSquareSize()+cont.getSquareSize()*sizeX/2,posY*cont.getSquareSize()+cont.getSquareSize()*sizeY/2,cont.getSquareSize()*3,cont.getSquareSize()*3);
						break;
					default:
						break;
				}
			}
		}
	}
}