package erland.game.tetris;

import java.awt.*;

public abstract class Block
{
	int rotation;
	int x,y;

	public abstract Color getColor();
	void init(int x, int y, int rotation)
	{
		this.x = x;
		this.y = y; 
		this.rotation = rotation;
	}
	
	public boolean moveDown(BlockMatrix m)
	{
		unset(m,x,y,rotation);
		if(check(m,x,y+1,rotation)) {
			set(m,x,y+1,rotation);
			return true;
		}else {
			set(m,x,y,rotation);
			return false;
		}
	}
	
	
	public boolean moveRight(BlockMatrix m)
	{
		unset(m,x,y,rotation);
		if(check(m,x+1,y,rotation)) {
			set(m,x+1,y,rotation);
			return true;
		}else {
			set(m,x,y,rotation);
			return false;
		}
	}

	public boolean moveLeft(BlockMatrix m)
	{
		unset(m,x,y,rotation);
		if(check(m,x-1,y,rotation)) {
			set(m,x-1,y,rotation);
			return true;
		}else {
			set(m,x,y,rotation);
			return false;
		}
	}
	
	public boolean rotateRight(BlockMatrix m) 
	{
		unset(m,x,y,rotation);
		if(check(m,x,y,rotation+90)) {
			set(m,x,y,rotation+90);
			return true;
		}else {
			set(m,x,y,rotation);
			return false;
		}
	}
	
	public boolean rotateLeft(BlockMatrix m)
	{
		unset(m,x,y,rotation);
		if(check(m,x,y,rotation-90)) {
			set(m,x,y,rotation-90);
			return true;
		}else {
			set(m,x,y,rotation);
			return false;
		}
	}	
	
	public abstract boolean check(BlockMatrix m, int x, int y, int rotation);
	public abstract void set(BlockMatrix m, int x, int y, int rotation);
	public abstract void unset(BlockMatrix m, int x, int y, int rotation);
	
}