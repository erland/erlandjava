package erland.game.racer;

public class RacerLevelFactoryGameIcon implements LevelFactoryInterface {
    protected BlockCloneInterface blockCloneManager;


    public RacerLevelFactoryGameIcon(BlockCloneInterface blockCloneManager)
    {
        this.blockCloneManager = blockCloneManager;
    }

    public LevelInterface createLevel() {
        return new LevelGameIcon(blockCloneManager,1,1);
    }
}
