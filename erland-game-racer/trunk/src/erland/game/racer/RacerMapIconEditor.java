package erland.game.racer;

import erland.game.MapEditorBlockInterface;

import java.awt.*;
/**
 * This is an editor for the icon objects that can be used
 * in the {@link RacerMapBlockEditor} to create a block that
 * can be used to create a track in the {@link RacerMapTrackEditor}
 * The RacerMapIconEditor is mainly used to set the friction
 * on the icon objects.
 */
public class RacerMapIconEditor extends RacerMapEditor {

    protected MapEditorBlockInterface getSelectBlock(int blockNo)
    {
        BlockText b = null;
        b = new BlockText();
        b.init(getEnvironment());
        b.setText(String.valueOf(blockNo));
        return b;
    }
    protected int getNoOfSelectBlocks() {
        return 5;
    }

    protected void initFinish()
    {
        levelManager = new LevelManager();
        LevelFactoryInterface levelFactoryLevel = new RacerLevelFactoryEditorIcon(new BlockClone(),cont.getSizeX(),cont.getSizeY());
        levelManager.init(getEnvironment(),levelFactoryLevel,getLevelFileLabel(),getLevelFileGroupLabel());
        levelManager.setContainer(cont);
    }

    protected Image getMapBlockImage()
    {
        return null;
    }
    protected int getSelectSizeX()
    {
        return 1;
    }
    protected int getSelectSizeY()
    {
        return 5;
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
        return 88;
    }

}
