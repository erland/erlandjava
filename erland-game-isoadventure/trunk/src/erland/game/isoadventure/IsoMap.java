package erland.game.isoadventure;

import java.awt.*;
import java.util.Vector;

public class IsoMap implements MapDrawInterface {
    private IrregularBlockContainerInterface cont;
    private Vector maps = new Vector();

    public void setContainer(IrregularBlockContainerInterface cont) {
        this.cont = cont;
    }

    public void removeAllObjectMaps() {
        maps.removeAllElements();
    }
    public void addObjectMap(IsoObjectMapInterface map) {
        maps.addElement(map);
    }

    public void draw(Graphics g) {
        if(maps.size()>0) {
            IsoObjectMapInterface main = (IsoObjectMapInterface) maps.elementAt(0);
            for(int z=0;z<main.getSizeZ();z++) {
                int noOfRows = main.getSizeX();
                for(int row=0;row<noOfRows;row++) {
                    for(int x=0,y=row;y>=0;y--,x++) {
                        IsoObjectInterface obj = main.getObject(x,y,z);
                        if(obj!=null) {
                            obj.draw(g);
                        }
                        for(int i=1;i<maps.size();i++) {
                            IsoObjectMapInterface map = (IsoObjectMapInterface) maps.elementAt(i);
                            obj = map.getObject(x,y,z);
                            if(obj!=null) {
                                obj.draw(g);
                            }
                        }
                    }
                }
                int noOfColumns = main.getSizeY()-1;
                for(int column=1;column<=noOfColumns;column++) {
                    for(int x=column,y=noOfColumns;y>=column;y--,x++) {
                        IsoObjectInterface obj = main.getObject(x,y,z);
                        if(obj!=null) {
                            obj.draw(g);
                        }
                        for(int i=1;i<maps.size();i++) {
                            IsoObjectMapInterface map = (IsoObjectMapInterface) maps.elementAt(i);
                            obj = map.getObject(x,y,z);
                            if(obj!=null) {
                                obj.draw(g);
                            }
                        }
                    }
                }
            }
        }
    }
}

