package erland.game.racer;

/**
 * A factory that is used to create blocks that consists of other blocks.
 * This is for example used in the {@link RacerMapTrackEditor} where the
 * blocks that is used to draw the track is composed by smaller icons.
 */
public class RacerLevelFactoryGroup implements LevelFactoryInterface {
    /** The horizontal number of blocks of the container where the blocks should be placed in */
    protected int sizeX;
    /** The vertical number of blocks of the container where the blocks should be placed in */
    protected int sizeY;
    /** The level manager managing the blocks that the main block should consist of */
    protected LevelManager blockManager;
    /** The object that is used when the blocks should be cloned */
    protected BlockCloneInterface blockCloneManager;

    /**
     * Creates the factory object
     * @param blockManager The level manager managing the blocks that the main block should consist of
     * @param blockCloneManager The object that is used when the blocks should be cloned
     * @param sizeX The horizontal number of blocks of the container where the blocks should be placed in
     * @param sizeY The vertical number of blocks of the container where the blocks should be placed in
     */
    public RacerLevelFactoryGroup(LevelManager blockManager,BlockCloneInterface blockCloneManager,int sizeX, int sizeY)
    {
        this.sizeX = sizeX;
        this.sizeY = sizeY;
        this.blockManager = blockManager;
        this.blockCloneManager = blockCloneManager;
    }

    public LevelInterface createLevel() {
        return new LevelGroup(blockManager,blockCloneManager,sizeX, sizeY);
    }
}
