package erland.game.racer;

public class RacerLevelFactoryEditorIcon implements LevelFactoryInterface {
    protected int sizeX;
    protected int sizeY;
    protected BlockCloneInterface blockCloneManager;


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
