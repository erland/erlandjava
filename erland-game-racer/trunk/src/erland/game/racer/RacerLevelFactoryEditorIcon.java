package erland.game.racer;

/**
 * This is a factor that creates the icons that can be composed together to
 * make a block that can be used to build a track
 */
public class RacerLevelFactoryEditorIcon implements LevelFactoryInterface {
    /** The horizontal number of blocks in the container that the icon will be placed in */
    protected int sizeX;
    /** The vertical number of blocks in the container that the icon will be placed in */
    protected int sizeY;
    /** The object that will be used when making clones of the block */
    protected BlockCloneInterface blockCloneManager;

    /**
     * Creates a factory object to create icons
     * @param blockCloneManager The object that should be used when cloning blocks
     * @param sizeX The horizontal number of blocks in the container the blocks will be created in
     * @param sizeY The vertical number of blocks in the container the blocks will be created in
     */
    public RacerLevelFactoryEditorIcon(BlockCloneInterface blockCloneManager, int sizeX, int sizeY)
    {
        this.blockCloneManager = blockCloneManager;
        this.sizeX = sizeX;
        this.sizeY = sizeY;
    }

    public LevelInterface createLevel() {
        return new LevelEditorIcon(blockCloneManager,sizeX, sizeY);
    }
}
