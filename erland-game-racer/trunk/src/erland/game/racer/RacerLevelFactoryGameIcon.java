package erland.game.racer;

/**
 * A factory object that is used to creates the blocks that is used
 * to compose a track
 */
public class RacerLevelFactoryGameIcon implements LevelFactoryInterface {
    /** The object that is used to clone the blocks */
    protected BlockCloneInterface blockCloneManager;

    /**
     * Creates the factory
     * @param blockCloneManager The object that is used to clone the blocks
     */
    public RacerLevelFactoryGameIcon(BlockCloneInterface blockCloneManager)
    {
        this.blockCloneManager = blockCloneManager;
    }

    public LevelInterface createLevel() {
        return new LevelGameIcon(blockCloneManager,1,1);
    }
}
