package erland.game.racer;

import erland.game.MapEditorBlockInterface;
import erland.util.Log;

public class RacerMapIconEditor extends RacerMapEditor {

    protected MapEditorBlockInterface getSelectBlock(int blockNo)
    {
        BlockText b = null;
        if(blockNo<5) {
            b = new BlockText();
            b.init(environment);
            b.setText(String.valueOf(blockNo));
        }
        return b;
    }

    protected void initFinish()
    {
        levelManager = new LevelManager();
        LevelFactoryInterface levelFactoryLevel = new RacerLevelFactoryEditorIcon(new BlockClone(),cont.getSizeX(),cont.getSizeY());
        levelManager.init(environment,levelFactoryLevel,getLevelFileLabel(),getLevelFileGroupLabel());
        levelManager.setContainer(cont);
    }

    protected int getSizeX()
    {
        return 4;
    }
    protected int getSizeY()
    {
        return 4;
    }
    protected String getLevelFileLabel()
    {
        return "mapicons";
    }
    protected String getLevelFileGroupLabel()
    {
        return "mapicon";
    }
    protected String getLevelLabelText()
    {
        return "Icon:";
    }
    protected MapEditorBlockInterface prepareNewBlock(MapEditorBlockInterface oldBlock,MapEditorBlockInterface newBlock)
    {
        if(oldBlock!=null) {
            if(oldBlock instanceof BlockText) {
                if(newBlock instanceof BlockText) {
                    ((BlockText)newBlock).setBackgroundBlock(((BlockText)oldBlock).getBackgroundBlock());
                }
            }else {
                if(newBlock instanceof BlockText) {
                    ((BlockText)newBlock).setBackgroundBlock((Block)oldBlock);
                }
            }
        }
        return newBlock;
    }
    protected int getMaxLevel()
    {
        return 14;
    }
}
