package erland.game.crackout;
import erland.game.*;
import java.util.*;
import java.awt.*;

class Missile
{
	int missileSizeX;
	int missileSizeY;
	double x;
	double y;
	int speed;
	ImageHandlerInterface images;
	BlockContainerInterface cont;
	int offsetX;
	int offsetY;
	boolean active;
	
	Missile()
	{
		active=true;
	}

	void init(ImageHandlerInterface images, BlockContainerInterface cont, int x, int y, int missileSizeX, int missileSizeY, int speed)
	{
		this.cont = cont;
		this.images = images;
		this.offsetX = cont.getOffsetX();
		this.offsetY = cont.getOffsetY();
		this.x = x;
		this.y = y;
		this.missileSizeX = missileSizeX;
		this.missileSizeY = missileSizeY;
		this.speed = speed;
		active = true;
	}
	
	int move(ActionInterface a, Block blocks[], double scoreMultiplier)
	{
		int score=0;
		for(int i=0;i<speed;i++) {
			y-=1;
			score += handleCollision(a,blocks, scoreMultiplier);
		}
		return score;
	}
	int handleCollision(ActionInterface a, Block blocks[], double scoreMultiplier)
	{
		int score =0;
		// Handle collsion with walls
		if((y+missileSizeY)<0) {
			y=0;
			active=false;
		}
				
		// Check collision with blocks
		if(blocks!=null) {
			for(int i=0;i<blocks.length;i++) {
				if(checkCollision(blocks[i],0)) {
					blocks[i].handleCollisionExplosive(a);
					score += scoreMultiplier*blocks[i].getScore();
				}
			}
		}
		
		return score;
	}
	boolean checkCollision(CollisionRect rc, double deviation)
	{
		boolean bCollision = false;
		if((x+missileSizeX)>rc.left() && x<rc.right()) {
			if((y+missileSizeY)>rc.top() && y<rc.bottom()) {
				bCollision = true;
			}
		}
		return bCollision;
	}
	void draw(Graphics g)
	{
		if(active) {
			//g.setColor(Color.white);
			//g.fillRect((int)(offsetX+x),(int)(offsetY+y),missileSizeX,missileSizeY);
			g.drawImage(images.getImage(images.MISSILE),(int)(offsetX+x),(int)(offsetY+y),null);
		}
	}
	boolean isActive()
	{
		return active;
	}
}
