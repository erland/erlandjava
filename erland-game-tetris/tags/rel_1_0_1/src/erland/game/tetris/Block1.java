package erland.game.tetris;
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

import java.awt.*;

/**
 * Represents the block:
 * <br>0x00
 * <br>xxx0
 * <br>0000
 * @author Erland Isaksson
 */
class Block1 extends Block
{
	/** The color of the block */
	protected static Color color = Color.red;
	public Color getColor()
	{
		return color;
	}
	protected boolean check(BlockMatrix m, int x, int y, int rotation)
	{
		if(rotation>=360) {
			rotation-=360;
		}else if(rotation<0) {
			rotation+=360;
		}

		if(rotation == 0) {
			if(!m.isUsed(x,y) && !m.isUsed(x+1,y) && !m.isUsed(x-1,y) && !m.isUsed(x,y-1)) {
				return true;
			}
		}else if(rotation == 90) {
			if(!m.isUsed(x,y) && !m.isUsed(x,y+1) && !m.isUsed(x,y-1) && !m.isUsed(x+1,y)) {
				return true;
			}
		}else if(rotation == 180) {
			if(!m.isUsed(x,y) && !m.isUsed(x+1,y) && !m.isUsed(x-1,y) && !m.isUsed(x,y+1)) {
				return true;
			}
		}else if(rotation == 270) {
			if(!m.isUsed(x,y) && !m.isUsed(x,y+1) && !m.isUsed(x,y-1) && !m.isUsed(x-1,y)) {
				return true;
			}
		}
		return false;
	}
	
	protected void unset(BlockMatrix m, int x, int y, int rotation)
	{
		if(rotation>=360) {
			rotation-=360;
		}else if(rotation<0) {
			rotation+=360;
		}
		
		if(rotation == 0) {
			m.setUnused(x,y);
			m.setUnused(x+1,y);
			m.setUnused(x-1,y);
			m.setUnused(x,y-1);
		}else if(rotation == 90) {
			m.setUnused(x,y);
			m.setUnused(x,y+1);
			m.setUnused(x,y-1);
			m.setUnused(x+1,y);
		}else if(rotation == 180) {
			m.setUnused(x,y);
			m.setUnused(x+1,y);
			m.setUnused(x-1,y);
			m.setUnused(x,y+1);
		}else if(rotation == 270) {
			m.setUnused(x,y);
			m.setUnused(x,y+1);
			m.setUnused(x,y-1);
			m.setUnused(x-1,y);
		}
	}
	
	protected void set(BlockMatrix m, int x, int y, int rotation)
	{
		if(rotation>=360) {
			rotation-=360;
		}else if(rotation<0) {
			rotation+=360;
		}
		
		this.x = x;
		this.y = y;
		this.rotation = rotation;
		
		if(rotation == 0) {
			m.setUsed(x,y,color);
			m.setUsed(x+1,y,color);
			m.setUsed(x-1,y,color);
			m.setUsed(x,y-1,color);
		}else if(rotation == 90) {
			m.setUsed(x,y,color);
			m.setUsed(x,y+1,color);
			m.setUsed(x,y-1,color);
			m.setUsed(x+1,y,color);
		}else if(rotation == 180) {
			m.setUsed(x,y,color);
			m.setUsed(x+1,y,color);
			m.setUsed(x-1,y,color);
			m.setUsed(x,y+1,color);
		}else if(rotation == 270) {
			m.setUsed(x,y,color);
			m.setUsed(x,y+1,color);
			m.setUsed(x,y-1,color);
			m.setUsed(x-1,y,color);
		}
	}
	
}