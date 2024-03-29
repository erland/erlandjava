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
import java.util.*;
import java.awt.*;

/**
 * Implements a missile object which will go straight up from the initial position
 * and explode all blocks it collides with
 */
class Missile
{
	/** Width of the missile */
	protected int missileSizeX;
	/** Height of the missile */
	protected int missileSizeY;
	/** X position of the missile */
	protected double x;
	/** Y position of the missile */
	protected double y;
	/** Speed of the missile */
	protected int speed;
	/** Block container which the missile resides in */
	protected BlockContainerInterface cont;
	/** Horisontal drawing offset */
	protected int offsetX;
	/** Vertical drawing offset */
	protected int offsetY;
	/** Indicates if the missile is visible(active) or not */
	protected boolean active;
	/** Image for the missile */
	protected Image img;
		
	/**
	 * Creates a new Missile object
	 */
	public Missile()
	{
		active=true;
	}

	/**
	 * Initialize object
	 * @param environment Game environment handler
	 * @param cont Block container which the missile resides in
	 * @param x X position of the missile
	 * @param y Y position of the missile
	 * @param missileSizeX Width of the missile
	 * @param missileSizeY Height of the missile
	 * @param speed Speed of the missile
	 */
	public void init(GameEnvironmentInterface environment, BlockContainerInterface cont, int x, int y, int missileSizeX, int missileSizeY, int speed)
	{
		this.cont = cont;
		this.offsetX = cont.getOffsetX();
		this.offsetY = cont.getOffsetY();
		this.x = x;
		this.y = y;
		this.missileSizeX = missileSizeX;
		this.missileSizeY = missileSizeY;
		this.speed = speed;
		active = true;
		img = environment.getImageHandler().getImage("missile.gif");
	}
	
	/**
	 * Move the missile to its next position and handles any collision that has occured
	 * @param a Action object which implements all collision actions
	 * @param blocks Array with all blocks which the missile might collide with
	 * @param scoreMultiplier Every score increase should be multiplied with this number
	 * @return The score should be increased with this number
	 */
	public int move(ActionInterface a, Block blocks[], double scoreMultiplier)
	{
		int score=0;
		for(int i=0;i<speed;i++) {
			y-=1;
			score += handleCollision(a,blocks, scoreMultiplier);
		}
		return score;
	}
	/**
	 * Check if the missile has collided with any blocks and handle the collision if it
	 * has occured
	 * @param a Action object which implements all collision actions
	 * @param blocks Array with all blocks which the missile might collide with
	 * @param scoreMultiplier Every score increase should be multiplied with this number
	 * @return The score should be increased with this number
	 */
	protected int handleCollision(ActionInterface a, Block blocks[], double scoreMultiplier)
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
				if(checkCollision(blocks[i])) {
					blocks[i].handleCollisionExplosive(a);
					score += scoreMultiplier*blocks[i].getScore();
				}
			}
		}
		
		return score;
	}
	/**
	 * Check if the missile has collided with the specified rectangle/block
	 * @param rc Rectangle to check collision with
	 * @return true/false (Collision/No collision)
	 */
	protected boolean checkCollision(CollisionRect rc)
	{
		boolean bCollision = false;
		if((x+missileSizeX)>rc.left() && x<rc.right()) {
			if((y+missileSizeY)>rc.top() && y<rc.bottom()) {
				bCollision = true;
			}
		}
		return bCollision;
	}
	
	/** 
	 * Draw the missile
	 * @param g Graphics object to draw on
	 */
	public void draw(Graphics g)
	{
		if(active) {
			//g.setColor(Color.white);
			//g.fillRect((int)(offsetX+x),(int)(offsetY+y),missileSizeX,missileSizeY);
			g.drawImage(img,(int)(offsetX+x),(int)(offsetY+y),null);
		}
	}
	
	/**
	 * Checks if the missile is visible(active) or not
	 * @return true/false (Visible/Not visible)
	 */
	public boolean isActive()
	{
		return active;
	}
}
