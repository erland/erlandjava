package erland.game.racer;
/*
 * Copyright (C) 2003 Erland Isaksson (erland_i@hotmail.com)
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 2
 * of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA  02111-1307, USA.
 *
 */

import erland.game.GameEnvironmentInterface;

import java.awt.*;

/**
 * Implements the full implementation of a racing car
 * @author Erland Isaksson
 */
public class Car extends CarClient implements CollisionObjectInterface,FrictionSensitiveInterface, CarDrawInterface, CarClientInterface {
    /** Accelleration speed */
    private double accelerateSpeed;
    /** Maximum speed */
    private double topSpeed;
    /** Breaking speed */
    private double breakSpeed;
    /** Turning speed */
    private double turnSpeed;
    /** Slowdown speed */
    private double slowdownSpeed;
    /** Tyre grip factor */
    private double grip;
    /** Previous X pixel position of the car */
    private double oldPosX;
    /** Previous Y pixel position of the car */
    private double oldPosY;
    /** Current friction of the ground */
    private double friction;
    /** Current bounding rectangle of the car */
    private Rectangle bounds;
    /** Last reqeusted offset bounding rectangle of the car */
    private Rectangle boundsCustom;
    /** The next moving angle, will be set during collisions */
    private double nextMovingAngle;
    /** The next speed of the car, will be set during collisions */
    private double nextSpeed;
    /** Indicates that {@link #nextMovingAngle} and {@link #nextSpeed} is available */
    private boolean bNextAvailable;
    /** Indicates if car accellerated last time */
    private boolean bAccellerating;

    /**
     * Initialize the car in a new game environment
     * @param environment The game environment which the car exists in
     */
    public void init(GameEnvironmentInterface environment)
    {
        super.init(environment);
        bounds = new Rectangle((int)serverPosX,(int)serverPosY,12,12);
        boundsCustom = new Rectangle((int)serverPosX,(int)serverPosY,12,12);
        bNextAvailable=false;
        bAccellerating = false;
    }

    /**
     * Initialize the capabilities of the car
     * @param topSpeed The maximum speed of the car
     * @param accelerateSpeed The acceleration speed of the car
     * @param breakSpeed The brake speed of the car
     * @param turnSpeed The turning speed of the car
     * @param slowdownSpeed The slowdown speed of the car
     * @param grip The tyre grip factor of the car
     */
    public void setCapabilities(double topSpeed,double accelerateSpeed, double breakSpeed, double turnSpeed, double slowdownSpeed, double grip)
    {
        this.topSpeed = topSpeed;
        this.accelerateSpeed = accelerateSpeed;
        this.breakSpeed = breakSpeed;
        this.turnSpeed = turnSpeed;
        this.slowdownSpeed = slowdownSpeed;
        this.grip = grip;
    }

    /**
     * Set the pixel position of the car
     * @param x X pixel position
     * @param y Y pixel position
     */
    public void setServerPos(int x, int y)
    {
        super.setServerPos(x,y);
        oldPosX = serverPosX;
        oldPosY = serverPosY;
    }

    /**
     * Perform accelerate action
     */
    public void accelerating()
    {
        bAccellerating = true;
        serverSpeed += accelerateSpeed;
        if(serverSpeed>topSpeed) {
            serverSpeed=topSpeed;
        }
    }

    /**
     * Perform brake action
     */
    public void braking()
    {
        serverSpeed -= breakSpeed;
        if(serverSpeed<0) {
            serverSpeed = 0;
        }
    }
    /**
     * Perform turn right action
     */
    public void turnRight()
    {
        serverCarAngle += turnSpeed;
        if(serverCarAngle>360) {
            serverCarAngle -= 360;
        }
    }
    /**
     * Perform turn left action
     */
    public void turnLeft()
    {
        serverCarAngle -= turnSpeed;
        if(serverCarAngle<0) {
            serverCarAngle += 360;
        }
    }
    /**
     * Perform slow down action
     */
    public void slowdown()
    {
        serverSpeed -= slowdownSpeed;
        if(serverSpeed<0) {
            serverSpeed = 0;
        }
    }

    public boolean isAccelerating() {
        return bAccellerating;
    }
    public void clientUpdate() {
        oldPosX = serverPosX;
        oldPosY = serverPosY;
        serverMovingAngle = normalizeAngle(serverMovingAngle);
        serverCarAngle = normalizeAngle(serverCarAngle);

        serverPosX += serverSpeed*Math.cos((serverMovingAngle)*Math.PI/180);
        serverPosY += serverSpeed*Math.sin((serverMovingAngle)*Math.PI/180);

        serverPosX = normalizePosX(serverPosX);
        serverPosY = normalizePosY(serverPosY);

        setClientPos((int)serverPosX,(int)serverPosY);
        setClientCarAngle((int)serverCarAngle);
        setClientMovingAngle((int)serverMovingAngle);
        setClientSpeed((int)serverSpeed);
    }
    /**
     * Update the car position
     */
    public void serverUpdate()
    {
        bAccellerating = false;
        if(serverSpeed>topSpeed - (topSpeed/2)*friction) {
            serverSpeed = topSpeed - (topSpeed/2)*friction;
        }

        double xspeed1 = serverSpeed*Math.cos((serverMovingAngle)*Math.PI/180);
        double yspeed1 = serverSpeed*Math.sin((serverMovingAngle)*Math.PI/180);
        double steerspeed = (this.grip-(this.grip/2)*friction);
        double xspeed2 = steerspeed*Math.cos((serverCarAngle)*Math.PI/180);
        double yspeed2 = steerspeed*Math.sin((serverCarAngle)*Math.PI/180);
        xspeed1+=xspeed2;
        yspeed1+=yspeed2;
        if(xspeed1<0) {
            serverMovingAngle = normalizeAngle(180-(Math.atan((yspeed1/-xspeed1))*180/Math.PI));
        }else {
            serverMovingAngle = normalizeAngle(Math.atan((yspeed1/xspeed1))*180/Math.PI);
        }
        serverSpeed = Math.sqrt(xspeed1*xspeed1 + yspeed1*yspeed1)-steerspeed;

        clientUpdate();
        bounds.setLocation((int)serverPosX+10,(int)(serverPosY)+10);
    }


    /**
     * Get current bounding rectangle of the car
     * @return Bounding rectangle of the car
     */
    public Rectangle getBounds() {
        return bounds;
    }

    /**
     * Get bounding rectangle of the car with the specified offset
     * @param x The x pixel offset
     * @param y The y pixel offset
     * @return The bounding rectangle of the car
     */
    public Rectangle getBounds(int x,int y) {
        if(!isInsideContainer(x,y)) {
            return null;
        }
        boundsCustom.setLocation(x+10,y+10);
        return boundsCustom;
    }

    public void handleCollision(CollisionObjectInterface object) {
        double angle = object.getCollisionAngle();
        double speed = object.getCollisionSpeed();

        double xspeed1 = serverSpeed*Math.cos((serverMovingAngle)*Math.PI/180);
        double yspeed1 = serverSpeed*Math.sin((serverMovingAngle)*Math.PI/180);
        double xspeed2 = speed*Math.cos((angle)*Math.PI/180);
        double yspeed2 = speed*Math.sin((angle)*Math.PI/180);
        double newxspeed;
        double newyspeed;
        if((xspeed1<0 && xspeed2>0)||(xspeed1>0 && xspeed2<0)) {
            newxspeed = xspeed1+xspeed2;
        }else {
            newxspeed = (xspeed1+xspeed2)/2;
        }
        if(Math.abs(xspeed1)>Math.abs(xspeed2)) {
            newxspeed-=newxspeed/4;
        }else {
            newxspeed+=newxspeed/4;
        }
        if((yspeed1<0 && yspeed2>0)||(yspeed1>0 && yspeed2<0)) {
            newyspeed = yspeed1+yspeed2;
        }else {
            newyspeed = (yspeed1+yspeed2)/2;
        }
        if(Math.abs(yspeed1)>Math.abs(yspeed2)) {
            newyspeed-=newyspeed/4;
        }else {
            newyspeed+=newyspeed/4;
        }
        nextSpeed = Math.sqrt(newxspeed*newxspeed + newyspeed*newyspeed);
        if(newxspeed<0) {
            nextMovingAngle = normalizeAngle(180-(Math.atan((newyspeed/-newxspeed))*180/Math.PI));
        }else {
            nextMovingAngle = normalizeAngle(Math.atan((newyspeed/newxspeed))*180/Math.PI);
        }

        if(Math.abs(newxspeed-xspeed1)>0.2 || Math.abs(newyspeed-yspeed1)>0.2) {
        //final double MINCOLLISIONSPEED = -1.0;
        //if(Math.abs(xspeed1)>MINCOLLISIONSPEED || Math.abs(xspeed2)>MINCOLLISIONSPEED || Math.abs(yspeed1)>MINCOLLISIONSPEED || Math.abs(yspeed2)>MINCOLLISIONSPEED) {
            serverPosX = oldPosX;
            serverPosY = oldPosY;
            bNextAvailable=true;
        }
    }

    public double getCollisionAngle() {
        return serverMovingAngle;
    }

    public double getCollisionSpeed() {
        return serverSpeed;
    }

    public double getCollisionWeight() {
        return 0;
    }

    public void setFriction(double friction) {
        this.friction = friction;
    }

    public void applyCollisions() {
        if(bNextAvailable) {
            serverMovingAngle = nextMovingAngle;
            serverSpeed = nextSpeed;
            bNextAvailable=false;
            serverUpdate();
        }
    }
}
