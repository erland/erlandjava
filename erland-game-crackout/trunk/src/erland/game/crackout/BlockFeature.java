package erland.game.crackout;

import erland.game.*;
import erland.util.*;
import java.awt.*;

/**
 * Implements a block that drops a feature when it is hit by the ball
 */
class BlockFeature extends BlockSimple
{
	/** Feature that should be dropped when the ball is hit, see {@link Feature.FeatureType} */
	protected int feature;
	/** Image for the feature */
	protected Image img;
	
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
		img = null;
		switch(feature) {
			case Feature.FeatureType.decreaseBallSpeed:
				description += "Decrease ball speed";
				img = images.getImage("feature_decballspeed.gif");
				break;			
			case Feature.FeatureType.decreaseBatSpeed:
				description += "Decrease bat speed";
				img = images.getImage("feature_decbatspeed.gif");
				break;			
			case Feature.FeatureType.doubleBat:
				description += "Double bat";
				img = images.getImage("feature_doublebat.gif");
				break;			
			case Feature.FeatureType.extraLife:
				description += "Extra life";
				img = images.getImage("feature_extralife.gif");
				break;			
			case Feature.FeatureType.increaseBallSpeed:
				description += "Increase ball speed";
				img = images.getImage("feature_incballspeed.gif");
				break;			
			case Feature.FeatureType.increaseBatSpeed:
				description += "Increase bat speed";
				img = images.getImage("feature_incbatspeed.gif");
				break;			
			case Feature.FeatureType.lockBat:
				description += "Lock bat";
				img = images.getImage("feature_lockbat.gif");
				break;			
			case Feature.FeatureType.newBall:
				description += "New ball";
				img = images.getImage("feature_newball.gif");
				break;			
			case Feature.FeatureType.safetyWall:
				description += "Safety wall";
				img = images.getImage("feature_safetywall.gif");
				break;			
			case Feature.FeatureType.missile:
				description += "Missile";
				img = images.getImage("feature_missile.gif");
				break;			
			case Feature.FeatureType.largeBat:
				description += "Large bat";
				img = images.getImage("feature_largebat.gif");
				break;			
			case Feature.FeatureType.smallBat:
				description += "Small bat";
				img = images.getImage("feature_smallbat.gif");
				break;			
			case Feature.FeatureType.bomb:
				description += "Bomb";
				img = images.getImage("feature_bomb.gif");
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
