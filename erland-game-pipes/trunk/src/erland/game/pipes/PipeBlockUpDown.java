package erland.game.pipes;
import erland.game.*;
/**
 * Represents a block like following:
 * OXO
 * OXO
 * OXO
 */
class PipeBlockUpDown extends PipeBlock
{
    /** Game environment object */
    GameEnvironmentInterface environment;

	/**
	 * Creates a new pipe block
	 * @param environment Game environment object
	 */
	public PipeBlockUpDown(GameEnvironmentInterface environment)
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
	    
	    for(int i=0; i<size;i++) {
		    parts[1][i] = new PipePartUpDown(environment);
		}
		
		super.init(cont,parts,x,y);
	}
}