package erland.game.tetris;

import java.awt.*;

/**
 * Represents the block:
 * <br>xx00
 * <br>0xx0
 * @author Erland Isaksson
 */
class Block7 extends Block
{
	/** The color of the block */
	protected static Color color = Color.magenta;
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
			if(!m.isUsed(x,y) && !m.isUsed(x,y-1) && !m.isUsed(x-1,y-1) && !m.isUsed(x+1,y)) {
				return true;
			}
		}else if(rotation == 90) {
			if(!m.isUsed(x,y) && !m.isUsed(x+1,y) && !m.isUsed(x+1,y-1) && !m.isUsed(x,y+1)) {
				return true;
			}
		}else if(rotation == 180) {
			if(!m.isUsed(x,y) && !m.isUsed(x,y+1) && !m.isUsed(x+1,y+1) && !m.isUsed(x-1,y)) {
				return true;
			}
		}else if(rotation == 270) {
			if(!m.isUsed(x,y) && !m.isUsed(x-1,y) && !m.isUsed(x-1,y+1) && !m.isUsed(x,y-1)) {
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
			m.setUnused(x,y-1);
			m.setUnused(x-1,y-1);
			m.setUnused(x+1,y);
		}else if(rotation == 90) {
			m.setUnused(x,y);
			m.setUnused(x+1,y);
			m.setUnused(x+1,y-1);
			m.setUnused(x,y+1);
		}else if(rotation == 180) {
			m.setUnused(x,y);
			m.setUnused(x,y+1);
			m.setUnused(x+1,y+1);
			m.setUnused(x-1,y);
		}else if(rotation == 270) {
			m.setUnused(x,y);
			m.setUnused(x-1,y);
			m.setUnused(x-1,y+1);
			m.setUnused(x,y-1);
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
			m.setUsed(x,y-1,color);
			m.setUsed(x-1,y-1,color);
			m.setUsed(x+1,y,color);
		}else if(rotation == 90) {
			m.setUsed(x,y,color);
			m.setUsed(x+1,y,color);
			m.setUsed(x+1,y-1,color);
			m.setUsed(x,y+1,color);
		}else if(rotation == 180) {
			m.setUsed(x,y,color);
			m.setUsed(x,y+1,color);
			m.setUsed(x+1,y+1,color);
			m.setUsed(x-1,y,color);
		}else if(rotation == 270) {
			m.setUsed(x,y,color);
			m.setUsed(x-1,y,color);
			m.setUsed(x-1,y+1,color);
			m.setUsed(x,y-1,color);
		}
	}
}