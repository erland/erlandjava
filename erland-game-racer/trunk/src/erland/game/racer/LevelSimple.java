package erland.game.racer;

import erland.util.*;
import erland.game.MapEditorBlockInterface;
import erland.game.BlockContainerInterface;
import erland.game.GameEnvironmentInterface;

public class LevelSimple extends Level {

    public LevelSimple(BlockCloneInterface blockCloneManager,int sizeX, int sizeY)
    {
        super(blockCloneManager,sizeX,sizeY);
    }

    public void write(ParameterValueStorageInterface out)
    {
        if(blocks!=null) {
            StringStorage levelStorage = new StringStorage();
            ParameterStorageGroup allBlocks = new ParameterStorageGroup(levelStorage,"blockdata","block",true);
            writeBlocks(allBlocks);
            out.setParameter("data",levelStorage.load());
        }
    }

    protected void writeBlocks(ParameterValueStorageInterface out)
    {
        int i=0;
        for(int x=0;x<blocks.length;x++) {
            for(int y=0;y<blocks[x].length;y++) {
                if(blocks[x][y]!=null) {
                    i++;
                    StringStorage blockStorage = new StringStorage();
                    ParameterStorageString oneBlock = new ParameterStorageString(blockStorage,true);
                    oneBlock.setParameter("class",blocks[x][y].getClass().getName());
                    blocks[x][y].write(oneBlock);
                    out.setParameter("block"+i,blockStorage.load());
                }
            }
        }
    }

    protected MapEditorBlockInterface prepareNewBlock(MapEditorBlockInterface oldBlock,MapEditorBlockInterface newBlock)
    {
        return newBlock;
    }
    public void read(ParameterValueStorageInterface in)
    {
        clearBlocks();
        readBlocks(in);
    }
    protected void readBlocks(ParameterValueStorageInterface in)
    {
        StringStorage levelStorage = new StringStorage(in.getParameter("data"));
        ParameterStorageGroup allBlocks = new ParameterStorageGroup(levelStorage,"blockdata","block");
        int i=0;
        boolean bQuit = false;
        while(!bQuit) {
            i++;
            String s = allBlocks.getParameter("block"+i);
            if(s!=null && s.length()>0) {
                StringStorage blockStorage = new StringStorage(s);
                ParameterStorageString oneBlock = new ParameterStorageString(blockStorage);
                String cls = oneBlock.getParameter("class");
                MapEditorBlockInterface b = null;
                try {
                    b = (MapEditorBlockInterface)Class.forName(cls).newInstance();
                } catch (InstantiationException e) {
                } catch (IllegalAccessException e) {
                } catch (ClassNotFoundException e) {
                }
                b.init(environment);
                b.setContainer(cont);
                b.read(oneBlock);
                int x = b.getPosX();
                int y = b.getPosY();
                if(x>=0 && x<sizeX && y>=0 && y<sizeY) {
                    blocks[x][y] = prepareNewBlock(blocks[x][y],b);
                }
            }else {
                bQuit = true;
            }
        }
    }

}
