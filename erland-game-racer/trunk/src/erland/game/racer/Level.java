package erland.game.racer;

import erland.game.GameEnvironmentInterface;
import erland.game.BlockContainerInterface;
import erland.game.MapEditorBlockInterface;
import erland.util.ParameterValueStorageInterface;

public abstract class Level implements LevelInterface {
    protected MapEditorBlockInterface blocks[][];
    protected String name;
    protected BlockContainerInterface cont;
    protected GameEnvironmentInterface environment;
    protected int sizeX;
    protected int sizeY;
    protected BlockCloneInterface blockCloneManager;

    protected Level(BlockCloneInterface blockCloneManager,int sizeX, int sizeY)
    {
        this.blockCloneManager = blockCloneManager;
        this.sizeX = sizeX;
        this.sizeY = sizeY;
        clearBlocks();
    }
    public void init(GameEnvironmentInterface environment)
    {
        this.environment = environment;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public void setContainer(BlockContainerInterface cont)
    {
        this.cont = cont;
    }

    protected void clearBlocks()
    {
        blocks = new MapEditorBlockInterface[sizeX][sizeY];
        for(int x=0;x<blocks.length;x++) {
            for(int y=0;y<blocks[x].length;y++) {
                blocks[x][y]=null;
            }
        }
    }

    public abstract void write(ParameterValueStorageInterface out);

    public abstract void read(ParameterValueStorageInterface in);

    protected MapEditorBlockInterface[][] cloneBlocks(MapEditorBlockInterface[][] blocks)
    {
        MapEditorBlockInterface[][] clonedBlocks = new MapEditorBlockInterface[blocks.length][blocks[0].length];
        for(int x=0;x<blocks.length;x++) {
            for(int y=0;y<blocks[0].length;y++) {
                if(blocks[x][y]!=null) {
                    clonedBlocks[x][y] = blockCloneManager.cloneBlock(blocks[x][y]);
                    if(clonedBlocks[x][y]!=null) {
                        clonedBlocks[x][y].init(environment);
                        clonedBlocks[x][y].setContainer(cont);
                        clonedBlocks[x][y].setPos(x,y);
                    }
                }else {
                    clonedBlocks[x][y] = null;
                }
            }
        }
        return clonedBlocks;
    }

    public MapEditorBlockInterface[][] getBlocks()
    {
        return cloneBlocks(blocks);
    }

    public void setBlocks(MapEditorBlockInterface[][] blocks)
    {
        this.blocks = cloneBlocks(blocks);
    }

    public String getName()
    {
        return name;
    }
}
