package erland.game;

import erland.util.ParameterSerializable;

public interface MapEditorBlockInterface extends ParameterSerializable {
    public void init(GameEnvironmentInterface environment);
    public void setContainer(BlockContainerInterface cont);
    public int getPosX();
    public int getPosY();
    public void setPos(int x, int y);
}
