package erland.game.racer;

import erland.util.ParameterValueStorageInterface;
import erland.game.GameEnvironmentInterface;

public class RacerLevelFactorySimple implements LevelFactoryInterface {
    protected int sizeX;
    protected int sizeY;
    protected BlockCloneInterface blockCloneManager;


    public RacerLevelFactorySimple(BlockCloneInterface blockCloneManager, int sizeX, int sizeY)
    {
        this.blockCloneManager = blockCloneManager;
        this.sizeX = sizeX;
        this.sizeY = sizeY;
    }

    public LevelInterface createLevel() {
        return new LevelSimple(blockCloneManager,sizeX, sizeY);
    }
}
