package erland.game.isoadventure;

import java.util.Vector;

public class LevelInfo implements LevelInfoInterface {
    private IsoObjectMapInterface groundMap;
    private IsoObjectInterface[] objectList;

    public LevelInfo(IsoObjectMapInterface groundMap, IsoObjectInterface objectList[]) {
        this.groundMap = groundMap;
        this.objectList = objectList!=null?objectList:new IsoObjectInterface[0];
    }

    public IsoObjectMapInterface getMap() {
        return groundMap;
    }

    public IsoObjectInterface[] getObjectList() {
        return objectList;
    }
}
