package erland.game.pipes;
import erland.game.*;
/**
 * Represents a block like following:
 * OOO
 * XXX
 * OOO
 */
class PipeBlockLeftRight extends PipeBlock
{
    /** Game environment object */
    GameEnvironmentInterface environment;

	/**
	 * Creates a new pipe block
	 * @param environment Game environment object
	 */
	public PipeBlockLeftRight(GameEnvironmentInterface environment)
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
		    parts[i][1] = new PipePartLeftRight(environment);
		}
		
		super.init(cont,parts,x,y);
	}
}