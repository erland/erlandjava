package erland.game.crackout;

import erland.game.*;
import java.awt.*;

/**
 * Implements a block that drops a feature when it is hit by the ball
 */
class BlockFeature extends BlockSimple
{
	/** Feature that should be dropped when the ball is hit, see {@link Feature.FeatureType} */
	protected int feature;
	
	/**
	 * Creates the block
	 */
	public BlockFeature()
	{
		feature = Feature.FeatureType.lockBat;
	}
	
	public Object clone()
    	throws CloneNotSupportedException
    {
    	BlockFeature obj = new BlockFeature();
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
		init(images, cont, sizeX, sizeY, posX, posY,color);
		feature = featureType;
		description = "Feature block: ";
		switch(feature) {
			case Feature.FeatureType.decreaseBallSpeed:
				description += "Decrease ball speed";
				break;			
			case Feature.FeatureType.decreaseBatSpeed:
				description += "Decrease bat speed";
				break;			
			case Feature.FeatureType.doubleBat:
				description += "Double bat";
				break;			
			case Feature.FeatureType.extraLife:
				description += "Extra life";
				break;			
			case Feature.FeatureType.increaseBallSpeed:
				description += "Increase ball speed";
				break;			
			case Feature.FeatureType.increaseBatSpeed:
				description += "Increase bat speed";
				break;			
			case Feature.FeatureType.lockBat:
				description += "Lock bat";
				break;			
			case Feature.FeatureType.newBall:
				description += "New ball";
				break;			
			case Feature.FeatureType.safetyWall:
				description += "Safety wall";
				break;			
			case Feature.FeatureType.missile:
				description += "Missile";
				break;			
			case Feature.FeatureType.largeBat:
				description += "Large bat";
				break;			
			case Feature.FeatureType.smallBat:
				description += "Small bat";
				break;			
			case Feature.FeatureType.bomb:
				description += "Bomb";
				break;
			default:
				break;
		}
	}

	public void draw(Graphics g)
	{
		super.draw(g);
		if(active || dieCount>10) {
			float hsb[] = new float[3];
			Color.RGBtoHSB(color.getRed(),color.getBlue(),color.getGreen(),hsb);
			hsb[2]=(float)dieCount/255;
			Color c=Color.getHSBColor(hsb[0],hsb[1],hsb[2]);
			Color.RGBtoHSB(c.getRed(),c.getBlue(),c.getGreen(),hsb);
			hsb[2]=(float)hsb[2]*2/3;
			int offsetX = cont.getOffsetX();
			int offsetY = cont.getOffsetY();
			int squareSize = cont.getSquareSize();
			//g.setColor(Color.getHSBColor(hsb[0],hsb[1],hsb[2]));
			//g.fillOval(offsetX+posX*squareSize+(squareSize*sizeX)/2-squareSize/4-1,offsetY+posY*squareSize+squareSize/2-squareSize/4-1,squareSize/2+2,squareSize/2+2);
			//g.setColor(Color.black);
			//g.fillOval(offsetX+posX*squareSize+(squareSize*sizeX)/2-squareSize/4,offsetY+posY*squareSize+squareSize/2-squareSize/4,squareSize/2,squareSize/2);
			Image img=null;
			switch(feature) {
				case Feature.FeatureType.safetyWall:
					img = images.getImage(ImageHandlerInterface.FEATURE_SAFETYWALL);
					break;
				case Feature.FeatureType.newBall:
					img = images.getImage(ImageHandlerInterface.FEATURE_NEWBALL);
					break;
				case Feature.FeatureType.lockBat:
					img = images.getImage(ImageHandlerInterface.FEATURE_LOCKBAT);
					break;
				case Feature.FeatureType.increaseBatSpeed:
					img = images.getImage(ImageHandlerInterface.FEATURE_INCBATSPEED);
					break;
				case Feature.FeatureType.increaseBallSpeed:
					img = images.getImage(ImageHandlerInterface.FEATURE_INCBALLSPEED);
					break;
				case Feature.FeatureType.extraLife:
					img = images.getImage(ImageHandlerInterface.FEATURE_EXTRALIFE);
					break;
				case Feature.FeatureType.doubleBat:
					img = images.getImage(ImageHandlerInterface.FEATURE_DOUBLEBAT);
					break;
				case Feature.FeatureType.decreaseBatSpeed:
					img = images.getImage(ImageHandlerInterface.FEATURE_DECBATSPEED);
					break;
				case Feature.FeatureType.decreaseBallSpeed:
					img = images.getImage(ImageHandlerInterface.FEATURE_DECBALLSPEED);
					break;
				case Feature.FeatureType.missile:
					img = images.getImage(ImageHandlerInterface.FEATURE_MISSILE);
					break;
				case Feature.FeatureType.largeBat:
					img = images.getImage(ImageHandlerInterface.FEATURE_LARGEBAT);
					break;
				case Feature.FeatureType.smallBat:
					img = images.getImage(ImageHandlerInterface.FEATURE_SMALLBAT);
					break;
				case Feature.FeatureType.bomb:
					img = images.getImage(ImageHandlerInterface.FEATURE_BOMB);
					break;
				default:
					break;
			}
			if(img!=null && active) {
				g.drawImage(img,offsetX+posX*squareSize+(squareSize*sizeX)/2-squareSize/4,offsetY+posY*squareSize+2,null);
			}
		}
	}
	
	public void handleCollision(ActionInterface a)
	{
		active=false;
		Feature f = new Feature();
		int offsetX = cont.getOffsetX();
		int offsetY = cont.getOffsetY();
		int squareSize = cont.getSquareSize();
		int boardSizeX = cont.getSizeX();
		int boardSizeY = cont.getSizeY();
		f.init(images, cont,squareSize*posX+squareSize*sizeX/2-squareSize/2,squareSize*posY,feature);
		a.NewFeature(f);
	}
	
	/**
	 * Get the feature that will be dropped when the block is hit
	 * @return The feature that will be dropped when the block is hit
	 */
	public int getFeature()
	{
		return feature;
	}
}
