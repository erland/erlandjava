/*
 * Created by IntelliJ IDEA.
 * User: Erland Isaksson
 * Date: 2002-apr-14
 * Time: 12:07:27
 * To change template for new class use
 * Code Style | Class Templates options (Tools | IDE Options).
 */
package erland.game.racer;

import erland.game.MapEditorBlockInterface;
import erland.game.BlockContainerInterface;
import erland.game.GameEnvironmentInterface;
import erland.util.SubImageHandler;
import erland.util.Log;
import erland.util.ParameterValueStorageInterface;

import java.awt.*;

public class BlockGroup extends BlockBitmap {
    protected int blockType;

    public int getBlockType()
    {
        return blockType;
    }
    public void setBlockType(int blockType)
    {
        this.blockType = blockType;
    }

    public void write(ParameterValueStorageInterface out) {
        out.setParameter("x",Integer.toString(getPosX()));
        out.setParameter("y",Integer.toString(getPosY()));
        out.setParameter("blocktype",Integer.toString(blockType));
    }

    public void read(ParameterValueStorageInterface in) {
        int x = Integer.valueOf(in.getParameter("x")).intValue();
        int y = Integer.valueOf(in.getParameter("y")).intValue();
        blockType = Integer.valueOf(in.getParameter("blocktype")).intValue();
        setPos(x,y);
    }
}
