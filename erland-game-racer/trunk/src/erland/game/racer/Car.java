package erland.game.racer;

import erland.util.ImageCreatorInterface;
import erland.util.ImageHandlerInterface;
import erland.util.SubImageHandler;
import erland.util.Log;
import erland.game.BlockContainerInterface;
import erland.game.GameEnvironmentInterface;

import java.awt.*;

public class Car implements CollisionObjectInterface,FrictionSensitiveInterface {
    protected BlockContainerInterface cont;
    protected SubImageHandler subImages;
    protected Image mainImage;
    protected double accelerateSpeed;
    protected double topSpeed;
    protected double breakSpeed;
    protected double turnSpeed;
    protected double slowdownSpeed;
    protected double grip;
    protected GameEnvironmentInterface environment;

    protected double carAngle;
    protected double movingAngle;
    protected double speed;
    protected int image;
    protected double posX;
    protected double posY;

    public double friction;

    protected Rectangle bounds;
    protected double nextMovingAngle;
    protected double nextSpeed;
    protected boolean bNextAvailable;

    public void init(GameEnvironmentInterface environment)
    {
        bounds = new Rectangle((int)posX,(int)posY,12,12);
        this.environment = environment;
        bNextAvailable=false;
    }
    public void setContainer(BlockContainerInterface cont)
    {
        this.cont = cont;
    }
    public void setImage(String image)
    {
        mainImage = environment.getImageHandler().getImage(image);
        subImages = new SubImageHandler(mainImage, 32,32,9,4);
    }

    public void setCapabilities(double topSpeed,double accelerateSpeed, double breakSpeed, double turnSpeed, double slowdownSpeed, double grip)
    {
        this.topSpeed = topSpeed;
        this.accelerateSpeed = accelerateSpeed;
        this.breakSpeed = breakSpeed;
        this.turnSpeed = turnSpeed;
        this.slowdownSpeed = slowdownSpeed;
        this.grip = grip;
    }

    public void setPos(int x, int y)
    {
        posX = x;
        posY = y;
    }
    public void setAngle(int angle)
    {
        carAngle = angle;
    }
    public void setSpeed(int speed)
    {
        this.speed = speed;
    }
    public int getPosX()
    {
        return (int)posX;
    }
    public int getPosY()
    {
        return (int)posY;
    }
    public void accelerating()
    {
        speed += accelerateSpeed;
        if(speed>topSpeed) {
            speed=topSpeed;
        }
    }

    public void breaking()
    {
        speed -= breakSpeed;
        if(speed<0) {
            speed = 0;
        }
    }
    public void turnRight()
    {
        carAngle += turnSpeed;
        if(carAngle>360) {
            carAngle -= 360;
        }
    }
    public void turnLeft()
    {
        carAngle -= turnSpeed;
        if(carAngle<0) {
            carAngle += 360;
        }
    }
    public void slowdown()
    {
        speed -= slowdownSpeed;
        if(speed<0) {
            speed = 0;
        }
    }

    public double normalizeAngle(double angle)
    {
        if(angle<0) {
            angle+=360;
        }
        if(angle>360) {
            angle-=360;
        }
        return angle;
    }
    public void update()
    {
        if(bNextAvailable) {
            movingAngle = nextMovingAngle;
            speed = nextSpeed;
            bNextAvailable=false;
        }

        if(speed>topSpeed - (topSpeed/2)*friction) {
            speed = topSpeed - (topSpeed/2)*friction;
        }
        if(Math.abs(movingAngle-carAngle)<grip) {
            movingAngle = carAngle;
        }else {
            double angle1 = movingAngle-carAngle;
            double angle2 = carAngle-movingAngle;
            angle1 = normalizeAngle(angle1);
            angle2 = normalizeAngle(angle2);

            if(angle1>angle2) {
                double gripSpeed = angle2/10;
                if(gripSpeed<grip) {
                    gripSpeed = grip;
                }
                movingAngle+=gripSpeed;
                if(angle2>90) {
                    slowdown();
                }
            }else {
                double gripSpeed = angle1/10;
                if(gripSpeed<grip) {
                    gripSpeed = grip;
                }
                movingAngle-=grip;
                if(angle1>90) {
                    slowdown();
                }
            }
        }
        movingAngle = normalizeAngle(movingAngle);
        carAngle = normalizeAngle(carAngle);

        image = (int)((carAngle+90)/10);
        if(image>35) {
            image-=36;
        }

        posX += speed*Math.cos((movingAngle)*Math.PI/180);
        posY += speed*Math.sin((movingAngle)*Math.PI/180);

        if(posX<0) {
            posX=0;
        }else if(posX+32>cont.getScrollingSizeX()) {
            posX=cont.getScrollingSizeX()-32;
        }
        if(posY<0) {
            posY=0;
        }else if(posY+32>cont.getScrollingSizeY()) {
            posY=cont.getScrollingSizeY()-32;
        }
        bounds.setLocation((int)posX+10,(int)(posY)+10);
    }

    public void draw(Graphics g,int level)
    {
        if(level==0) {
            g.setClip(cont.getOffsetX(),cont.getOffsetY(),cont.getDrawingSizeX(),cont.getDrawingSizeY());
            //g.setColor(Color.white);
            //g.drawRect(cont.getPixelDrawingPositionX(bounds.x), cont.getPixelDrawingPositionY(bounds.y),bounds.width,bounds.height);
            //g.drawString(String.valueOf((int)movingAngle),
            //        cont.getPixelDrawingPositionX((int)posX+32),cont.getPixelDrawingPositionY((int)posY));
            subImages.drawImage(g,image,cont.getPixelDrawingPositionX((int)posX),cont.getPixelDrawingPositionY((int)posY));

        }
    }

    public Rectangle getBounds() {
        return bounds;
    }

    public void handleCollision(CollisionObjectInterface object) {
        if(!bNextAvailable) {
            double angle = object.getCollisionAngle();
            double speed = object.getCollisionSpeed();

            double xspeed1 = this.speed*Math.cos((this.movingAngle)*Math.PI/180);
            double yspeed1 = this.speed*Math.sin((this.movingAngle)*Math.PI/180);
            double xspeed2 = speed*Math.cos((angle)*Math.PI/180);
            double yspeed2 = speed*Math.sin((angle)*Math.PI/180);
            double newxspeed;
            double newyspeed;
            if((xspeed1<0 && xspeed2>0)||(xspeed1>0 && xspeed2<0)) {
                newxspeed = xspeed1+xspeed2;
            }else {
                newxspeed = (xspeed1+xspeed2)/2;
            }
            if((yspeed1<0 && yspeed2>0)||(yspeed1>0 && yspeed2<0)) {
                newyspeed = yspeed1+yspeed2;
            }else {
                newyspeed = (yspeed1+yspeed2)/2;
            }
            nextSpeed = Math.sqrt(newxspeed*newxspeed + newyspeed*newyspeed);
            nextMovingAngle = Math.atan2(newxspeed,newyspeed)*180/Math.PI;
            if(nextMovingAngle<0) {
                nextMovingAngle+=360;
            }
            if(Math.abs(nextSpeed-speed)>0.05) {
                bNextAvailable=true;
            }
        }
    }

    public double getCollisionAngle() {
        return movingAngle;
    }

    public double getCollisionSpeed() {
        return speed;
    }

    public double getCollisionWeight() {
        return 0;
    }

    public void setFriction(double friction) {
        this.friction = friction;
    }
}
