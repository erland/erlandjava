package erland.game.racer;

/**
 * A factory object that is used to create levels/tracks
 */
public class RacerLevelFactorySimple implements LevelFactoryInterface {
    /** The number of horizontal blocks of the level */
    protected int sizeX;
    /** The number of vertical blocks of the level */
    protected int sizeY;
    /** The object that will be used when cloning blocks in the level */
    protected BlockCloneInterface blockCloneManager;

    /**
     * Create the factory object
     * @param blockCloneManager The object that will be used when cloning blocks in the level
     * @param sizeX The number of horizontal blocks of the level
     * @param sizeY The number of vertical blocks of the level
     */
    public RacerLevelFactorySimple(BlockCloneInterface blockCloneManager, int sizeX, int sizeY)
    {
        this.blockCloneManager = blockCloneManager;
        this.sizeX = sizeX;
        this.sizeY = sizeY;
    }

    public LevelInterface createLevel() {
        return new Level(blockCloneManager,sizeX, sizeY);
    }
}
