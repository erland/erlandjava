package erland.game.tetris;

import java.awt.Color;

/**
 * Represents a container with squares, where each square may
 * be (active/not active) and each square also has a colour
 * @author Erland Isaksson
 */
interface BlockMatrix
{
	/**
	 * Get the number of horisontal squares in the container
	 * @return Number of horisontal squares
	 */
	public int getWidth();
	
	/**
	 * Get the number of vertical squares in the container
	 * @return Number of vertical squares
	 */
	public int getHeight();
	
	/**
	 * Get the color of a specific square
	 * @param x The x position of the square
	 * @param y The y position of the square
	 * @return The color of the square, Color.black if it is outside the container
	 */
	public Color getColor(int x, int y);
	
	/**
	 * Checks if a specific square is active or not
	 * @param x The x position of the square
	 * @param y The y position of the square
	 * @return true/false (active/not active)
	 */
	public boolean isUsed(int x, int y); 
	
	/**
	 * Change the state to active for a specific square
	 * @param x The x position of the square
	 * @param y The y position of the square
	 * @param c The colour of the square
	 */
	public void setUsed(int x, int y, Color c);
	
	/**
	 * Change the state to not active for a specific square
	 * @param x The x position of the square
	 * @param y The y position of the square
	 */
	public void setUnused(int x, int y);
}
