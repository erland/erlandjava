package erland.game.isoadventure;

public interface IsoObjectMapActionInterface {
    boolean isFree(IsoObjectInterface obj,int x, int y, int z);
    Action startActionOnObject(IsoObjectInterface obj, Action action);
    void endActionOnObject(IsoObjectInterface obj, Action action);
}
