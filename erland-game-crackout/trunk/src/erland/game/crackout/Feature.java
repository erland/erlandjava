package erland.game.crackout;
import erland.game.*;
import java.awt.*;

class Feature
	implements CollisionRect
{
	int sizeX;
	int sizeY;
	double x;
	double y;
	boolean active;
	int speed;
	int offsetX;
	int offsetY;
	int limitX;
	int limitY;
	int featureType;
	ImageHandlerInterface images;
	BlockContainerInterface cont;
	
	class FeatureType {
		static final int lockBat=1;
		static final int newBall=2;
		static final int increaseBallSpeed=3;
		static final int decreaseBallSpeed=4;
		static final int increaseBatSpeed=5;
		static final int decreaseBatSpeed=6;
		static final int doubleBat=7;
		static final int extraLife=8;
		static final int safetyWall=9;
		static final int missile=10;
		static final int largeBat=11;
		static final int smallBat=12;
		static final int bomb=13;
	}

	Feature()
	{
		active=true;
		featureType = FeatureType.lockBat;
	}
	void init(ImageHandlerInterface images, BlockContainerInterface cont, int posX, int posY, int featureType)
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
	int handleCollision(ActionInterface a, Bat bats[])
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
	int move(ActionInterface a, Bat bats[])
	{
		y+=speed;
		return handleCollision(a,bats);
	}
	void draw(Graphics g)
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
