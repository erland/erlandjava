package erland.game.isoadventure;

/**
 * Created by IntelliJ IDEA.
 * User: Erland Isaksson
 * Date: 2003-maj-03
 * Time: 18:29:54
 * To change this template use Options | File Templates.
 */
public interface IrregularBlockContainerInterface {
    int getSizeX();

    int getSizeY();

    int getSquareSizeX();

    int getSquareSizeY();

    int getSquareSizeZ();

    int getScrollingOffsetX();

    int getScrollingOffsetY();

    int getOffsetX();

    int getOffsetY();

    int getDrawingPositionX(int x, int y, int z);

    int getDrawingPositionY(int x, int y, int z);

    int getDrawingPositionX(int x, int y, int z, float dx, float dy, float dz);

    int getDrawingPositionY(int x, int y, int z, float dx, float dy, float dz);

    int getPositionX(int x, int y, int z);

    int getPositionY(int x, int y, int z);

    int getDrawingSizeX();

    int getDrawingSizeY();

    boolean getVisible(int posX, int posY, int posZ);

    int getScrollingSizeX();

    int getScrollingSizeY();

    int getPixelDrawingPositionX(int x);

    int getPixelDrawingPositionY(int y);

    int getPositionX(int x, int y, int z, float dx, float dy, float dz);

    int getPositionY(int x, int y, int z, float dx, float dy, float dz);
}
