/*
 * Created by IntelliJ IDEA.
 * User: Erland Isaksson
 * Date: 2002-apr-14
 * Time: 12:51:06
 * To change template for new interface use 
 * Code Style | Class Templates options (Tools | IDE Options).
 */
package erland.game.racer;

import erland.util.ParameterSerializable;
import erland.util.ParameterValueStorageInterface;
import erland.game.GameEnvironmentInterface;
import erland.game.BlockContainerInterface;
import erland.game.MapEditorBlockInterface;

public interface LevelInterface extends ParameterSerializable {
    void init(GameEnvironmentInterface environment);

    void setContainer(BlockContainerInterface cont);

    void write(ParameterValueStorageInterface out);

    void read(ParameterValueStorageInterface in);

    MapEditorBlockInterface[][] getBlocks();

    void setBlocks(MapEditorBlockInterface[][] blocks);

    String getName();

    void setName(String name);

}
