package erland.game.isoadventure;

public interface IsoObjectMapInterface {
    IsoObjectInterface getObject(int x, int y, int z);

    int getSizeX();

    int getSizeY();

    int getSizeZ();

    void addObject(IsoObjectInterface obj, int x, int y, int z);

    void removeObject(IsoObjectInterface obj, int x, int y, int z);

    void removeObjectFromMap(IsoObjectInterface obj);
}
