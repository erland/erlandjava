package erland.game.racer;

import erland.util.SubImageHandler;
import erland.game.BlockContainerInterface;
import erland.game.GameEnvironmentInterface;

import java.awt.*;

/**
 * Implements the client part of the racing car
 * @author Erland Isaksson
 */
public class CarClient implements CarClientInterface {
    /** Block container which the car exists in */
    private BlockContainerInterface cont;
    /** Sub image handler object */
    private SubImageHandler subImages;
    /** Sub image handler object */
    private SubImageHandler serverSubImages;
    /** Game environment which the car exists in */
    private GameEnvironmentInterface environment;
    /** Current angle of the car in the server */
    protected double serverCarAngle;
    /** Current moving angle of the car in the server */
    protected double serverMovingAngle;
    /** Current speed of the car in the server */
    protected double serverSpeed;
    /** X pixel position of the car in the server */
    protected double serverPosX;
    /** Y pixel position of the car in the server */
    protected double serverPosY;
    /** Current angle of the car in the client */
    private double carAngle;
    /** Current moving angle of the car in the client */
    private double movingAngle;
    /** Current speed of the car in the client */
    private double speed;
    /** X pixel position of the car in the client */
    private double posX;
    /** Y pixel position of the car in the client */
    private double posY;
    /** The car identifer */
    private int id;
    /** The image file use for the car */
    private String imageFile;
    /** Specifies the client positioning logic that is used */
    private int serverUpdateType;
    /** Specifies if both the server car and client car should be shown */
    private int serverCarShow;

    public void init(GameEnvironmentInterface environment)
    {
        this.environment = environment;
        this.serverUpdateType = 2;
        this.serverCarShow = 0;
        id=-1;
    }

    public void setContainer(BlockContainerInterface cont)
    {
        this.cont = cont;
    }

    public void setImage(String image)
    {
        this.imageFile = image;
        if(environment.getImageHandler()!=null) {
            Image mainImage = environment.getImageHandler().getImage(image);
            subImages = new SubImageHandler(mainImage, 32,32,9,4);
            Image serverImage = environment.getImageHandler().getImage("server"+image);
            if(serverImage==null) {
                serverImage = environment.getImageHandler().getImage("servercar.gif");
            }
            serverSubImages = new SubImageHandler(serverImage, 32,32,9,4);
        }
    }

    public String getImage() {
        return imageFile;
    }

    public void setServerPos(int x, int y)
    {
        serverPosX = x;
        serverPosY = y;
    }

    public void setClientPos(int x, int y)
    {
        posX = x;
        posY = y;
    }

    public void setServerCarAngle(int angle)
    {
        serverCarAngle = angle;
    }

    public void setClientCarAngle(int angle)
    {
        carAngle = angle;
    }

    public void setServerMovingAngle(int angle)
    {
        serverMovingAngle = angle;
    }

    public void setClientMovingAngle(int angle)
    {
        movingAngle = angle;
    }

    public void setServerSpeed(double speed)
    {
        this.serverSpeed = speed;
    }


    public void setClientSpeed(double speed)
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


    /**
     * Normalize the specified angle, -90 will return 270
     * @param angle The angle to normalize
     * @return The normalized angle
     */
    protected static double normalizeAngle(double angle)
    {
        if(angle<0) {
            angle+=360;
        }
        if(angle>360) {
            angle-=360;
        }
        return angle;
    }

    public double getMovingAngle()
    {
        return movingAngle;
    }

    public double getCarAngle()
    {
        return carAngle;
    }

    public double getSpeed()
    {
        return speed;
    }

    /**
     * Get a normalized X position that is safely inside the block container
     * @param x The X position to normalize
     * @return The normalized X position
     */
    protected double normalizePosX(double x) {
        if(x<0) {
            x=0;
        }else if(x+32>cont.getScrollingSizeX()) {
            x=cont.getScrollingSizeX()-32;
        }
        return x;
    }
    /**
     * Get a normalized Y position that is safely inside the block container
     * @param y The Y position to normalize
     * @return The normalized Y position
     */
    protected double normalizePosY(double y) {
        if(y<0) {
            y=0;
        }else if(y+32>cont.getScrollingSizeY()) {
            y=cont.getScrollingSizeY()-32;
        }
        return y;
    }

    /**
     * Check if the specified X position is inside the block container
     * @param x The X position to check
     * @param y The Y position to check
     * @return <code>true</code> - Inside container
     *         <code>false</code> - Outside container
     */
    protected boolean isInsideContainer(int x, int y) {
        if(x<0) {
            return false;
        }else if(x+32>cont.getScrollingSizeX()) {
            return false;
        }
        if(y<0) {
            return false;
        }else if(y+32>cont.getScrollingSizeY()) {
            return false;
        }
        return true;
    }

    public void clientUpdate() {
        movingAngle = normalizeAngle(movingAngle);
        carAngle = normalizeAngle(carAngle);

        posX += speed*Math.cos((movingAngle)*Math.PI/180);
        posY += speed*Math.sin((movingAngle)*Math.PI/180);
        posX = normalizePosX(posX);
        posY = normalizePosY(posY);
    }

    /**
     * Server update logic for {@link #serverUpdateType}=1
     */
    public void serverUpdate1() {
        double diffX =serverPosX-posX;
        double absDiffX =Math.abs(serverPosX-posX);
        double diffY =serverPosY-posY;
        double absDiffY =Math.abs(serverPosY-posY);
        if(absDiffX>2 || absDiffY>2) {
            double serverAngle;
            if(diffX<0) {
                serverAngle = normalizeAngle(180-(Math.atan((diffY/-diffX))*180/Math.PI));
            }else {
                serverAngle = normalizeAngle(Math.atan((diffY/diffX))*180/Math.PI);
            }
            if(speed<0.4) {
                speed = 0.4;
            }
            double xspeed1 = speed/2.0*Math.cos((serverAngle)*Math.PI/180);
            double yspeed1 = speed/2.0*Math.sin((serverAngle)*Math.PI/180);
            double xspeed2 = speed*Math.cos((movingAngle)*Math.PI/180);
            double yspeed2 = speed*Math.sin((movingAngle)*Math.PI/180);
            xspeed1+=xspeed2;
            yspeed1+=yspeed2;
            if(xspeed1<0) {
                movingAngle = normalizeAngle(180-(Math.atan((yspeed1/-xspeed1))*180/Math.PI));
            }else {
                movingAngle = normalizeAngle(Math.atan((yspeed1/xspeed1))*180/Math.PI);
            }
            speed = Math.sqrt(xspeed1*xspeed1 + yspeed1*yspeed1);
            clientUpdate();
        }else {
            posX = serverPosX;
            posY = serverPosY;
            speed = serverSpeed;
            movingAngle = serverMovingAngle;
        }
        carAngle = serverCarAngle;
    }
    /**
     * Server update logic for {@link #serverUpdateType}=2
     */
    public void serverUpdate2() {
        double diffX =serverPosX-posX;
        double absDiffX =Math.abs(serverPosX-posX);
        if(absDiffX>1) {
            double moveX = diffX/2.0;
            double absMoveX = absDiffX/2.0;
            if(absMoveX<1) {
                moveX=moveX/absMoveX;
            }
            posX+= moveX;
        }else {
            posX = serverPosX;
        }
        double diffY =serverPosY-posY;
        double absDiffY =Math.abs(serverPosY-posY);
        if(absDiffY>1) {
            double moveY = diffY/2.0;
            double absMoveY = absDiffY/2.0;
            if(absMoveY<1) {
                moveY=moveY/absMoveY;
            }
            posY+= moveY;
        }else {
            posY = serverPosY;
        }
        speed = serverSpeed;

        movingAngle = serverMovingAngle;
        carAngle = serverCarAngle;
    }
    public void serverUpdate() {
        if(serverUpdateType==1) {
            serverUpdate1();
        }else {
            serverUpdate2();
        }
    }

    /**
     * Get the sub image number that should be used for the specified car angle
     * @param angle The car angle to get sub image for
     * @return The sub image number for the specified angle
     */
    private int getSubImage(double angle) {
        int image = (int)((angle+90)/10);
        if(image>35) {
            image-=36;
        }
        return image;
    }

    public void draw(Graphics g,int level)
    {
        if(level==0) {
            g.setColor(Color.white);
            if(serverCarShow>0) {
                serverSubImages.drawImage(g,getSubImage(serverCarAngle),cont.getPixelDrawingPositionX((int)serverPosX),cont.getPixelDrawingPositionY((int)serverPosY));
            }
            if(serverCarShow<2) {
                subImages.drawImage(g,getSubImage(carAngle),cont.getPixelDrawingPositionX((int)posX),cont.getPixelDrawingPositionY((int)posY));
            }
        }
    }
    public void setId(int id) {
        this.id = id;
    }
    public int getId() {
        return this.id;
    }

    public void setCheatParameter(String parameter, String value) {
        if(parameter.equals("SERVERUPDATETYPE")) {
            this.serverUpdateType=Integer.valueOf(value).intValue();
        }else if(parameter.equals("SHOWSERVERCAR")) {
            if(value.equals("NONE")) {
                serverCarShow=0;
            }else if(value.equals("BOTH")) {
                serverCarShow=1;
            }else if(value.equals("ONLY")) {
                serverCarShow=2;
            }

        }
    }
}
