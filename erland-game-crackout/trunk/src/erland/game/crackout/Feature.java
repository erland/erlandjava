package erland.game.crackout;
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
import erland.game.*;
import erland.util.*;
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
	/** Image for feature */
	protected Image img;
	/** Image for feature background */
	protected Image imgBackground;
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
		/** explode bomb */
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
	 * @param environment Game environment object
	 * @param cont Block container which the feature resides in
	 * @param posX Initial x position of the feature
	 * @param posY Initial y position of the feature
	 * @param featureType Type of feature, see {@link FeatureType}
	 */
	public void init(GameEnvironmentInterface environment, BlockContainerInterface cont, int posX, int posY, int featureType)
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
		imgBackground = environment.getImageHandler().getImage("feature.gif");
		img = null;
		switch(featureType) {
			case Feature.FeatureType.safetyWall:
				img = environment.getImageHandler().getImage("feature_safetywall.gif");
				break;
			case Feature.FeatureType.newBall:
				img = environment.getImageHandler().getImage("feature_newball.gif");
				break;
			case Feature.FeatureType.lockBat:
				img = environment.getImageHandler().getImage("feature_lockbat.gif");
				break;
			case Feature.FeatureType.increaseBatSpeed:
				img = environment.getImageHandler().getImage("feature_incbatspeed.gif");
				break;
			case Feature.FeatureType.increaseBallSpeed:
				img = environment.getImageHandler().getImage("feature_incballspeed.gif");
				break;
			case Feature.FeatureType.extraLife:
				img = environment.getImageHandler().getImage("feature_extralife.gif");
				break;
			case Feature.FeatureType.doubleBat:
				img = environment.getImageHandler().getImage("feature_doublebat.gif");
				break;
			case Feature.FeatureType.decreaseBatSpeed:
				img = environment.getImageHandler().getImage("feature_decbatspeed.gif");
				break;
			case Feature.FeatureType.decreaseBallSpeed:
				img = environment.getImageHandler().getImage("feature_decballspeed.gif");
				break;
			case Feature.FeatureType.missile:
				img = environment.getImageHandler().getImage("feature_missile.gif");
				break;
			case Feature.FeatureType.largeBat:
				img = environment.getImageHandler().getImage("feature_largebat.gif");
				break;
			case Feature.FeatureType.smallBat:
				img = environment.getImageHandler().getImage("feature_smallbat.gif");
				break;
			case Feature.FeatureType.bomb:
				img = environment.getImageHandler().getImage("feature_bomb.gif");
				break;
			default:
				break;
		}
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
								a.lockBat();
								break;
							case FeatureType.newBall:
								a.newBall();
								break;
							case FeatureType.increaseBallSpeed:
								a.increaseBallSpeed();
								break;
							case FeatureType.decreaseBallSpeed:
								a.decreaseBallSpeed();
								break;
							case FeatureType.increaseBatSpeed:
								a.increaseBatSpeed();
								break;
							case FeatureType.decreaseBatSpeed:
								a.decreaseBatSpeed();
								break;
							case FeatureType.doubleBat:
								a.doubleBat();
								break;
							case FeatureType.extraLife:
								a.extraLife();
								break;
							case FeatureType.safetyWall:
								a.safetyWall();
								break;
							case FeatureType.missile:
								a.newMissile();
								break;
							case FeatureType.largeBat:
								a.largeBat();
								break;
							case FeatureType.smallBat:
								a.smallBat();
								break;
							case FeatureType.bomb:
								a.explode((int)x+sizeX/2,(int)y+sizeY/2,cont.getSquareSize()*3,cont.getSquareSize()*3);
								break;
							default:
								break;
						}
						a.removeFeature(this);
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
			g.drawImage(imgBackground,offsetX+(int)x,offsetY+(int)y,null);
			if(img!=null) {
				g.drawImage(img,offsetX+(int)x+5,offsetY+(int)y+5,null);
			}
		}
	}
	
}
