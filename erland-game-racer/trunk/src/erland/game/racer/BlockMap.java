package erland.game.racer;

import erland.game.BlockContainerInterface;
import erland.game.BlockContainerData;

import java.awt.*;

class BlockMap {
    Block mapData[][];
    BlockContainerInterface cont;

    public void setContainer(BlockContainerInterface cont)
    {
        this.cont = cont;
    }

    public void setMapData(Block data[][]) {
        mapData = data;
    }

    public void draw(Graphics g,int level)
    {
        g.setClip(cont.getOffsetX(),cont.getOffsetY(),cont.getDrawingSizeX(),cont.getDrawingSizeY());
        for(int x=0;x<mapData.length;x++) {
            for(int y=0;y<mapData.length;y++) {
                if(mapData[x][y]!=null) {
                    if(cont.getVisible(mapData[x][y].getPosX(),mapData[x][y].getPosY())) {
                        mapData[x][y].draw(g,level);
                    }
                }
            }
        }
    }
}
