package erland.game.racer;

import erland.game.MapEditorBlockInterface;
import erland.game.BlockContainerInterface;
import erland.game.GameEnvironmentInterface;
import erland.util.*;

import java.util.Vector;
import java.util.ListIterator;

public class LevelManager {
    protected Vector levels;
    protected GameEnvironmentInterface environment;
    protected BlockContainerInterface cont;
    protected String levelprefix;
    protected String storageprefix;
    protected LevelFactoryInterface levelFactory;

    public void init(GameEnvironmentInterface environment,LevelFactoryInterface levelFactory, String storageprefix, String levelprefix)
    {
        this.environment = environment;
        this.levelprefix = levelprefix;
        this.storageprefix = storageprefix;
        this.levelFactory = levelFactory;
        levels = new Vector();
    }
    public void setContainer(BlockContainerInterface cont)
    {
        this.cont = cont;
    }

    public int getMaxLevel()
    {
        return levels.size();
    }

    protected LevelInterface findLevel(int level)
    {
        ListIterator it = levels.listIterator();
        while(it.hasNext()) {
            LevelInterface l = (LevelInterface)it.next();
            if(l.getName().compareToIgnoreCase(levelprefix+level)==0) {
                return l;
            }
        }
        return null;
    }
    public MapEditorBlockInterface[][] getLevel(int level)
    {
        LevelInterface l = findLevel(level);
        if(l == null) {
            String s = environment.getStorage().getParameter(storageprefix);
            if(s != null && s.length()>0) {
                StringStorage groupStorage = new StringStorage(s);
                ParameterStorageGroup allLevels = new ParameterStorageGroup(groupStorage,storageprefix+"data",levelprefix,true);
                s = allLevels.getParameter(levelprefix+level);
                if(s != null && s.length()>0) {
                    StringStorage levelStorage = new StringStorage(s);
                    ParameterStorageString oneLevel = new ParameterStorageString(levelStorage,levelprefix+"data");
                    l = levelFactory.createLevel();
                    l.init(environment);
                    l.setName(levelprefix+level);
                    l.setContainer(cont);
                    l.read(oneLevel);
                    levels.add(l);
                }else {
                    return null;
                }
            }else {
                return null;
            }
        }
        return l.getBlocks();
    }

    public void setLevel(int level, MapEditorBlockInterface[][] blocks)
    {
        LevelInterface l = findLevel(level);
        if(l == null) {
            l = levelFactory.createLevel();
            l.init(environment);
            l.setName(levelprefix+level);
            l.setContainer(cont);
            l.setBlocks(blocks);
            levels.add(l);
        }else {
            l.setBlocks(blocks);
        }
        StringStorage levelStorage = new StringStorage();
        ParameterStorageString oneLevel = new ParameterStorageString(levelStorage,levelprefix+"data",true);
        l.write(oneLevel);
        StringStorage groupStorage = new StringStorage(environment.getStorage().getParameter(storageprefix));
        ParameterStorageGroup allLevels = new ParameterStorageGroup(groupStorage,storageprefix+"data",levelprefix,true);
        allLevels.setParameter(levelprefix+level,levelStorage.load());
        environment.getStorage().setParameter(storageprefix,groupStorage.load());
    }
}
