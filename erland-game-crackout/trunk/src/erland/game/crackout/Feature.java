package erland.game.crackout;
import erland.game.*;
import java.awt.*;

/**
 * Implements a feature object that drops from the start position
 * to the bottom of the screen. The user wil get the feature if 
 * it is hit with the bat
 */
class Feature
	implements CollisionRect
{
	/** Width of object */
	protected int sizeX;
	/** Height of object */
	protected int sizeY;
	/** X position of object */
	protected double x;
	/** Y position of object */
	protected double y;
	/** Indicates if the object is visible(active) or not */
	protected boolean active;
	/** The speed the feature drops with */
	protected int speed;
	/** Horisontal drawing offset */
	protected int offsetX;
	/** Vertical drawing offset */
	protected int offsetY;
	/** Maximum allowed x position for any part of the feature object */
	protected int limitX;
	/** Maximum allowed y position for any part of the feature object */
	protected int limitY;
	/** Type of feature, see {@link FeatureType} */
	protected int featureType;
	/** Image handler object */
	protected ImageHandlerInterface images;
	/** Block container object which the feature resides in */
	protected BlockContainerInterface cont;
	
	/**
	 * Defines the features availeble
	 */
	abstract class FeatureType {
		/** Locks the bat so it can't be moved for a while */
		static final int lockBat=1;
		/** Add a new ball to the game */
		static final int newBall=2;
		/** Increase the ballspeed of all active balls */
		static final int increaseBallSpeed=3;
		/** Decrease the ballspeed of all active balls */
		static final int decreaseBallSpeed=4;
		/** Increase the bat speed */
		static final int increaseBatSpeed=5;
		/** Decrease the bat speed */
		static final int decreaseBatSpeed=6;
		/** Change to a double bat for a while */
		static final int doubleBat=7;
		/** Adds an extra life */
		static final int extraLife=8;
		/** Puts up a safety wall at the bottom which the ball will bounce on for a while */
		static final int safetyWall=9;
		/** Adds an extra missile */
		static final int missile=10;
		/** Change to a large bat for a while */
		static final int largeBat=11;
		/** Change to a small bat for a while */
		static final int smallBat=12;
		/** Explode bomb */
		static final int bomb=13;
	}

	/**
	 * Creates a new object
	 */
	public Feature()
	{
		active=true;
		featureType = FeatureType.lockBat;
	}
	
	/**
	 * Initialize object
	 * @param images Image handler object
	 * @param cont Block container which the feature resides in
	 * @param posX Initial x position of the feature
	 * @param posY Initial y position of the feature
	 * @param featureType Type of feature, see {@link FeatureType}
	 */
	public void init(ImageHandlerInterface images, BlockContainerInterface cont, int posX, int posY, int featureType)
	{
		this.cont = cont;
		x = posX;
		y = posY;
		sizeX = 20;
		sizeY = 10;
		speed=1;
		this.offsetX = cont.getOffsetX();
		this.offsetY = cont.getOffsetY();
		this.limitX = cont.getSquareSize()*cont.getSizeX();
		this.limitY = cont.getSquareSize()*cont.getSizeY();
		this.featureType = featureType;
		this.images = images;
	}
	public int left()
	{
		return (int)x;
	}
	public int right()
	{
		return (int)x+sizeX;
	}
	public int top()
	{
		return (int)y;
	}
	public int bottom()
	{
		return (int)y+sizeY;
	}
	public void handleCollision(ActionInterface a)
	{
		//active = false;
	}
	/**
	 * Check if the feature has reached the button or collided with a bat, 
	 * perform the feature if it has collided with the bat and remove the feature
	 * if it has reached the bottom of the screen
	 * @param a Action object implementing all collision actions
	 * @param bats The bats which collision should be checked with
	 * @return The score should be increased with this number
	 */
	protected int handleCollision(ActionInterface a, Bat bats[])
	{
		// Handle collsion with walls
		if(y>(limitY-sizeY)) {
			active=false;
		}
		
		// Handle collsion with bat
		for(int i=0;i<bats.length;i++) {
			Bat bat = bats[i];
			if(bat!=null) {
				if((x+sizeX)>bat.left() && x<bat.right()) {
					if((y+sizeY)>bat.top() && y<bat.bottom()) {
						active=false;
						switch(featureType) {
							case FeatureType.lockBat:
								a.LockBat();
								break;
							case FeatureType.newBall:
								a.NewBall();
								break;
							case FeatureType.increaseBallSpeed:
								a.IncreaseBallSpeed();
								break;
							case FeatureType.decreaseBallSpeed:
								a.DecreaseBallSpeed();
								break;
							case FeatureType.increaseBatSpeed:
								a.IncreaseBatSpeed();
								break;
							case FeatureType.decreaseBatSpeed:
								a.DecreaseBatSpeed();
								break;
							case FeatureType.doubleBat:
								a.DoubleBat();
								break;
							case FeatureType.extraLife:
								a.ExtraLife();
								break;
							case FeatureType.safetyWall:
								a.SafetyWall();
								break;
							case FeatureType.missile:
								a.NewMissile();
								break;
							case FeatureType.largeBat:
								a.LargeBat();
								break;
							case FeatureType.smallBat:
								a.SmallBat();
								break;
							case FeatureType.bomb:
								a.Explode((int)x+sizeX/2,(int)y+sizeY/2,cont.getSquareSize()*3,cont.getSquareSize()*3);
								break;
							default:
								break;
						}
						a.RemoveFeature(this);
					}
				}
			}
		}
		return 0;
	}
	
	/**
	 * Move the feature to the next position and handle collisions with bats
	 * @param a Action object implementing all collision actions
	 * @param bats The bats which collision should be checked against
	 * @return The score should be increased with this value
	 */
	public int move(ActionInterface a, Bat bats[])
	{
		y+=speed;
		return handleCollision(a,bats);
	}
	
	/**
	 * Draw the feature object
	 * @param g Graphics object to draw on
	 */
	public void draw(Graphics g)
	{
		if(active) {
			Image img=null;
			switch(featureType) {
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
			g.drawImage(images.getImage(ImageHandlerInterface.FEATURE),offsetX+(int)x,offsetY+(int)y,null);
			if(img!=null) {
				g.drawImage(img,offsetX+(int)x+5,offsetY+(int)y+5,null);
			}
		}
	}
	
}
