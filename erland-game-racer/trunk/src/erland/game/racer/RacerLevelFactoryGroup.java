package erland.game.racer;

public class RacerLevelFactoryGroup implements LevelFactoryInterface {
    protected int sizeX;
    protected int sizeY;
    protected LevelManager blockManager;
    protected BlockCloneInterface blockCloneManager;

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
