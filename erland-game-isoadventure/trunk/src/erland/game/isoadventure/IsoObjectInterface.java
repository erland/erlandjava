package erland.game.isoadventure;

import erland.game.GameEnvironmentInterface;

import java.awt.*;

public interface IsoObjectInterface {
    void init(GameEnvironmentInterface environment);

    void setContainer(IrregularBlockContainerInterface cont);

    void setObjectMap(IsoObjectMapInterface objectMap);

    int getPosX();

    int getPosY();

    int getPosZ();

    int getMovingPosX();

    int getMovingPosY();

    int getMovingPosZ();

    void setPos(int x, int y, int z);

    void update();

    boolean getRedraw();

    void draw(Graphics g);

    boolean action(Action action);

    void setActionMap(IsoObjectMapActionInterface actionMap);

    boolean isMovable(Direction direction);

    boolean isPushable(Direction direction);

    int getPixelPosX();

    int getPixelPosY();
}
