package erland.game;

public class BlockContainerData
	implements BlockContainerInterface
{
	int offsetX;
	int offsetY;
	int sizeX;
	int sizeY;
	int squareSize;
	public BlockContainerData(int offsetX, int offsetY, int sizeX, int sizeY, int squareSize)
	{
		this.offsetX = offsetX;
		this.offsetY = offsetY;
		this.sizeX = sizeX;
		this.sizeY = sizeY;
		this.squareSize = squareSize;
	}
	public int getOffsetX()
	{
		return offsetX;
	}
	public int getOffsetY()
	{
		return offsetY;
	}
	public int getSizeX()
	{
		return sizeX;
	}
	public int getSizeY()
	{
		return sizeY;
	}
	public int getSquareSize()
	{
		return squareSize;
	}
}