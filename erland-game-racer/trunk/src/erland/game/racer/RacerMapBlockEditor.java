package erland.game.racer;

import erland.game.MapEditorBlockInterface;
import erland.game.BlockContainerData;
import erland.util.Log;

import java.awt.*;

public class RacerMapBlockEditor extends RacerMapEditor
{
    Image mapIconImage;
    protected MapEditorBlockInterface getSelectBlock(int blockNo)
    {
        BlockGroup b = new BlockGroup();
        b.init(environment);
        b.setImage(mapIconImage,blockNo);
        b.setBlockType(blockNo+1);
        return b;
        /*
        BlockBitmap b = new BlockBitmap();
        b.init(environment);
        b.setImage("mapicons.gif",blockNo);
        return b;
        */
    }

    protected void initFinish()
    {
        levelManager = new LevelManager();
        mapIconImage = environment.getImageCreator().createImage(256,256);
        LevelFactoryInterface levelFactoryLevel = new RacerLevelFactorySimple(new BlockTypeClone(getSelectBlocks()),cont.getSizeX(),cont.getSizeY());
        levelManager.init(environment,levelFactoryLevel,getLevelFileLabel(),getLevelFileGroupLabel());
        levelManager.setContainer(cont);

        LevelManager iconLevelManager = new LevelManager();
        LevelFactoryInterface levelFactoryIcon = new RacerLevelFactoryGameIcon(new BlockClone());
        iconLevelManager.init(environment,levelFactoryIcon,"mapicons","mapicon");
        iconLevelManager.setContainer(new BlockContainerData(0,0,1,1,32));
        int i=0;
        boolean bLast = false;
        Image imgTemp = environment.getImageCreator().createImage(cont.getSquareSize(),cont.getSquareSize());
        imgTemp.getGraphics().setColor(Color.black);
        mapIconImage.getGraphics().setColor(Color.black);
        mapIconImage.getGraphics().fillRect(-1,-1,mapIconImage.getWidth(null)+2,mapIconImage.getHeight(null)+2);
        while(!bLast) {
            MapEditorBlockInterface[][] blocks = iconLevelManager.getLevel(i+1);
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

}
