package erland.game.crackout;

import erland.game.*;
import java.awt.*;

class BlockFeatureHold extends BlockFeature
{
	int hitCount;
	int featureDieCount;
	int featureBlinkCount;
	static final int BLINK_SPEED=10;
	
	BlockFeatureHold()
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

	void init(ImageHandlerInterface images, BlockContainerInterface cont, int sizeX, int sizeY, int posX, int posY, Color color, int featureType)
	{
		super.init(images,cont,sizeX, sizeY,posX,posY,color,featureType);
		hitCount=2;
		featureDieCount=0;
		description += ", needs 2 hits, second hit generates feature";
	}
	
	public void drawSimple(Graphics g)
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