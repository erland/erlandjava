package erland.game.pipes;
import erland.game.*;
/**
 * Represents a block like following:
 * OXO
 * OXX
 * OOO
 */
class PipeBlockRightUp extends PipeBlock
{
    /** Game environment object */
    GameEnvironmentInterface environment;

	/**
	 * Creates a new pipe block
	 * @param environment Game environment object
	 */
	public PipeBlockRightUp(GameEnvironmentInterface environment)
	{
		this.environment = environment;
	}
	public void init(BlockContainerInterface cont, int x, int y)
	{
		
		PipePart[][] parts = new PipePart[size][size];
		for (int i=0; i<size; i++) {
			parts[i] = new PipePart[size];
			for (int j=0; j<size; j++) {
				parts[i][j] = new PipePartNone(environment);
		    }
	    }
	    
		parts[2][1] = new PipePartLeftRight(environment);
		parts[1][1] = new PipePartRightUp(environment);
		parts[1][0] = new PipePartUpDown(environment);
		
		super.init(cont,parts,x,y);
	}
}
