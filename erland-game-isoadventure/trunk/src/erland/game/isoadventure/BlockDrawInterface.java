package erland.game.isoadventure;

import java.awt.*;

/**
 * Created by IntelliJ IDEA.
 * User: Erland Isaksson
 * Date: 2003-maj-03
 * Time: 18:43:32
 * To change this template use Options | File Templates.
 */
public interface BlockDrawInterface {
    int getPosX();

    int getPosY();

    boolean getRedraw();

    void draw(Graphics g);
}
