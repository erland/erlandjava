package erland.game.racer;

import erland.game.MapEditorBlockInterface;
import erland.game.BlockContainerInterface;
import erland.game.GameEnvironmentInterface;
import erland.util.*;

import java.util.Vector;
import java.util.ListIterator;
import java.util.Enumeration;

/**
 * Level manager, handles loading and saving of the different levels
 */
public class LevelManager {
    /** All currently loaded levels */
    private Vector levels;
    /** The game environment */
    private GameEnvironmentInterface environment;
    /** The block container that the levels exists in */
    private BlockContainerInterface cont;
    /** The level prefix to insert before the level number*/
    private String levelprefix;
    /** The storage prefix that is used */
    private String storageprefix;
    /** The level factory that is used to create new levels */
    private LevelFactoryInterface levelFactory;

    /**
     * Initialize level manager
     * @param environment The game environment
     * @param levelFactory The level factory to use
     * @param storageprefix The storage prefix to use
     * @param levelprefix The level prefix to insert before the level number
     */
    public void init(GameEnvironmentInterface environment,LevelFactoryInterface levelFactory, String storageprefix, String levelprefix)
    {
        this.environment = environment;
        this.levelprefix = levelprefix;
        this.storageprefix = storageprefix;
        this.levelFactory = levelFactory;
        levels = new Vector();
    }
    /**
     * Set the block container that the levels exists in
     * @param cont The block container
     */
    public void setContainer(BlockContainerInterface cont)
    {
        this.cont = cont;
    }

    /**
     * Get the last level number currently available
     * @return The last level number
     */
    public int getMaxLevel()
    {
        return levels.size();
    }

    /**
     * Search for the specified level among currently loaded levels
     * @param level The level number to search for
     * @return The level or null if level is not loaded
     */
    protected LevelInterface findLevel(int level)
    {
        Enumeration enum = levels.elements();
        while(enum.hasMoreElements()) {
            LevelInterface l = (LevelInterface)enum.nextElement();
            if(l.getName().equalsIgnoreCase(levelprefix+level)) {
                return l;
            }
        }
        return null;
    }

    /**
     * Get the specified level, if the level is not currently loaded it will be loaded when this
     * method is called
     * @param level The level number of the level to get
     * @return The level or null if failed to load level
     */
    public LevelInfoInterface getLevel(int level)
    {
        LevelInterface l = findLevel(level);
        if(l == null) {
            StorageInterface groupStorage = environment.getStorage().getParameterAsStorage(storageprefix);
            Log.println(this,"getLevel("+level+") = "+String.valueOf(groupStorage!=null));
            if(groupStorage != null ) {
                ParameterStorageGroup allLevels = new ParameterStorageGroup(groupStorage,null,storageprefix+"data",levelprefix);
                StorageInterface levelStorage = allLevels.getParameterAsStorage(levelprefix+level);
                Log.println(this,"getLevel("+(levelprefix+level)+") = "+String.valueOf(levelStorage!=null));
                if(levelStorage != null ) {
                    ParameterStorageString oneLevel = new ParameterStorageString(levelStorage,null,levelprefix+"data");
                    l = levelFactory.createLevel();
                    l.init(environment);
                    l.setName(levelprefix+level);
                    l.setContainer(cont);
                    l.read(oneLevel);
                    levels.addElement(l);
                }else {
                    return null;
                }
            }else {
                return null;
            }
        }
        return l;
    }

    /**
     * Set the blocks and information in a specified level
     * @param level The level number of the level to set blocks in
     * @param blocks A matrix with the new blocks for the level
     * @param extendedInfo Extended information about the level
     */
    public void setLevel(int level, MapEditorBlockInterface[][] blocks,ParameterSerializable extendedInfo)
    {
        LevelInterface l = findLevel(level);
        if(l == null) {
            l = levelFactory.createLevel();
            l.init(environment);
            l.setName(levelprefix+level);
            l.setContainer(cont);
            l.setBlocks(blocks);
            l.setExtendedInfo(extendedInfo);
            levels.addElement(l);
        }else {
            l.setBlocks(blocks);
            l.setExtendedInfo(extendedInfo);
        }
        StringStorage levelStorage = new StringStorage();
        ParameterStorageString oneLevel = new ParameterStorageString(levelStorage,null,levelprefix+"data");
        l.write(oneLevel);
        StringStorage groupStorage = new StringStorage(environment.getStorage().getParameter(storageprefix));
        ParameterStorageGroup allLevels = new ParameterStorageGroup(groupStorage,null,storageprefix+"data",levelprefix);
        allLevels.setParameter(levelprefix+level,levelStorage.load());
        environment.getStorage().setParameter(storageprefix,groupStorage.load());
    }
}
