package erland.game.racer;

import erland.game.MapEditorBlockInterface;
import erland.game.BlockContainerData;

import java.awt.*;

/**
 * This object implements the editor for the map blocks, this object
 * is not the editor for the track instead it is the editor for the
 * blocks that can be used when editing the track
 */
public class RacerMapBlockEditor extends RacerMapEditor
{
    /** Image with sub images for all icons/blocks in the block select area */
    Image mapIconImage;

    protected Image getMapBlockImage()
    {
        return mapIconImage;
    }

    protected void initFinish()
    {
        levelManager = new LevelManager();
        mapIconImage = getEnvironment().getImageCreator().createImage(256,512);
        LevelFactoryInterface levelFactoryLevel = new RacerLevelFactorySimple(new BlockTypeClone(getSelectBlocks()),cont.getSizeX(),cont.getSizeY());
        levelManager.init(getEnvironment(),levelFactoryLevel,getLevelFileLabel(),getLevelFileGroupLabel());
        levelManager.setContainer(cont);

        LevelManager iconLevelManager = new LevelManager();
        LevelFactoryInterface levelFactoryIcon = new RacerLevelFactoryGameIcon(new BlockClone());
        iconLevelManager.init(getEnvironment(),levelFactoryIcon,"mapicons","mapicon");
        iconLevelManager.setContainer(new BlockContainerData(0,0,1,1,32));
        int i=0;
        boolean bLast = false;
        Image imgTemp = getEnvironment().getImageCreator().createImage(cont.getSquareSize(),cont.getSquareSize());
        imgTemp.getGraphics().setColor(Color.black);
        mapIconImage.getGraphics().setColor(Color.black);
        mapIconImage.getGraphics().fillRect(-1,-1,mapIconImage.getWidth(null)+2,mapIconImage.getHeight(null)+2);
        while(!bLast) {
            LevelInfoInterface levelInfo = iconLevelManager.getLevel(i+1);
            MapEditorBlockInterface[][] blocks = null;
            if(levelInfo!=null) {
                blocks = levelInfo.getBlocks();
            }
            if(blocks!=null) {
                if(blocks[0][0]!=null) {
                    blocks[0][0].setPos(0,0);
                    imgTemp.getGraphics().fillRect(-1,-1,imgTemp.getWidth(null)+2,imgTemp.getHeight(null)+2);
                    drawBlock(imgTemp.getGraphics(),blocks[0][0]);
                    int posX = (i%8)*32;
                    int posY = (i/8)*32;
                    mapIconImage.getGraphics().drawImage(imgTemp,posX,posY,posX+cont.getSquareSize(),posY+cont.getSquareSize(),0,0,32,32,null);
                }
                i++;
            }else {
                bLast = true;
            }
        }
        for(;i<8*8;i++) {
            int posX = (i%8)*32;
            int posY = (i/8)*32;
            mapIconImage.getGraphics().fillRect(posX,posY,32,32);
        }
    }

    protected int getSizeX()
    {
        return 3;
    }
    protected int getSizeY()
    {
        return 3;
    }
    protected String getLevelFileLabel()
    {
        return "mapblocks";
    }
    protected String getLevelFileGroupLabel()
    {
        return "mapblock";
    }
    protected String getLevelLabelText()
    {
        return "Block:";
    }
    protected int getMaxLevel()
    {
        return 40;
    }
    protected int getSelectSizeX()
    {
        return 4;
    }
    protected int getSelectSizeY()
    {
        return 10;
    }

}
