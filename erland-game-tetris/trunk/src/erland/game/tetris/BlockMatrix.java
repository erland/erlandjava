package erland.game.tetris;

import java.awt.Color;

public interface BlockMatrix
{
	public int getWidth();
	
	public int getHeight();
	
	public Color getColor(int x, int y);
	
	public boolean isUsed(int x, int y); 
	
	public void setUsed(int x, int y, Color c);
	
	public void setUnused(int x, int y);
}
