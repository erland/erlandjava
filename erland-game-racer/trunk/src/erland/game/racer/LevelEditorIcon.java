package erland.game.racer;

import erland.util.*;
import erland.game.MapEditorBlockInterface;
import erland.game.BlockContainerData;

import java.awt.*;

public class LevelEditorIcon extends LevelSimple {
    protected Image iconImage;
    protected MapEditorBlockInterface iconBlock;
    public LevelEditorIcon(BlockCloneInterface blockCloneManager, int sizeX, int sizeY)
    {
        super(blockCloneManager,sizeX,sizeY);
    }
    public void write(ParameterValueStorageInterface out)
    {
        StringStorage levelStorage = new StringStorage();
        ParameterStorageGroup allBlocks = new ParameterStorageGroup(levelStorage,"blockdata","block",true);
        if(iconBlock!=null) {
            StringStorage blockStorage = new StringStorage();
            ParameterStorageString oneBlock = new ParameterStorageString(blockStorage,true);
            oneBlock.setParameter("class",iconBlock.getClass().getName());
            iconBlock.write(oneBlock);
            allBlocks.setParameter("block0",blockStorage.load());

            if(blocks!=null) {
                int i=0;
                for(int x=0;x<blocks.length;x++) {
                    for(int y=0;y<blocks[x].length;y++) {
                        if(blocks[x][y]!=null) {
                            if(blocks[x][y] instanceof BlockText) {
                                i++;
                                blockStorage = new StringStorage();
                                oneBlock = new ParameterStorageString(blockStorage,true);
                                oneBlock.setParameter("class",blocks[x][y].getClass().getName());
                                blocks[x][y].write(oneBlock);
                                allBlocks.setParameter("block"+i,blockStorage.load());
                            }
                        }
                    }
                }
            }
            out.setParameter("data",levelStorage.load());
        }
    }

    public void read(ParameterValueStorageInterface in)
    {
        clearBlocks();
        StringStorage levelStorage = new StringStorage(in.getParameter("data"));
        ParameterStorageGroup allBlocks = new ParameterStorageGroup(levelStorage,"blockdata","block");
        String s = allBlocks.getParameter("block0");
        if(s!=null && s.length()>0) {
            StringStorage blockStorage = new StringStorage(s);
            ParameterStorageString oneBlock = new ParameterStorageString(blockStorage);
            String cls = oneBlock.getParameter("class");
            iconBlock = null;
            try {
                iconBlock = (MapEditorBlockInterface)Class.forName(cls).newInstance();
            } catch (InstantiationException e) {
            } catch (IllegalAccessException e) {
            } catch (ClassNotFoundException e) {
            }
            iconBlock.init(environment);
            iconBlock.setContainer(new BlockContainerData(0,0,1,1,cont.getSquareSize()));
            iconBlock.read(oneBlock);

            if(iconBlock instanceof Block) {
                iconImage = environment.getImageCreator().createImage(256,256);
                Image tmpImage = environment.getImageCreator().createImage(cont.getSquareSize(),cont.getSquareSize());
                ((Block)iconBlock).draw(tmpImage.getGraphics(),0);
                iconImage.getGraphics().setColor(Color.black);
                iconImage.getGraphics().fillRect(0,0,iconImage.getWidth(null),iconImage.getHeight(null));

                int i=0;
                for(int x=0;x<blocks.length;x++) {
                    for(int y=0;y<blocks[x].length;y++) {
                        int posX = (i%8)*cont.getSquareSize();
                        int posY = (i/8)*cont.getSquareSize();
                        int sizeX2 = cont.getSquareSize()/blocks.length;
                        int sizeY2 = cont.getSquareSize()/blocks[x].length;
                        int posX2 = (x)*sizeX2;
                        int posY2 = (y)*sizeY2;
                        iconImage.getGraphics().drawImage(tmpImage,posX,posY,posX+cont.getSquareSize(),posY+cont.getSquareSize(),posX2,posY2,posX2+sizeX2,posY2+sizeY2,null);
                        blocks[x][y] = new BlockBitmap();
                        blocks[x][y].init(environment);
                        blocks[x][y].setContainer(cont);
                        blocks[x][y].setPos(x,y);
                        ((BlockBitmap)blocks[x][y]).setImage(iconImage,i++);
                    }
                }
            }
        }
        readBlocks(in);
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
}
