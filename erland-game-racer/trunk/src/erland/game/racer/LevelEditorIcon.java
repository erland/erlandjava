package erland.game.racer;

import erland.util.*;
import erland.game.MapEditorBlockInterface;
import erland.game.BlockContainerData;

import java.awt.*;

/**
 * Implements the icons used in the {@link RacerMapIconEditor} where it is possible to
 * set a fricton on each icon. This object basically makes it possible to overlay the
 * original block with another block which is partly transparent
 */
public class LevelEditorIcon extends Level {
    /** Original block without the overlay */
    protected MapEditorBlockInterface iconBlock;
    /**
     * Creates the icon
     * @param blockCloneManager Object that is used to clone the icons
     * @param sizeX Number of horizontal blocks in the container
     * @param sizeY Number of vertical blocks in the container
     */
    public LevelEditorIcon(BlockCloneInterface blockCloneManager, int sizeX, int sizeY)
    {
        super(blockCloneManager,sizeX,sizeY);
    }
    public void writeBlocks(ParameterValueStorageExInterface out)
    {
        if(iconBlock!=null) {
            StringStorage blockStorage = new StringStorage();
            ParameterStorageString oneBlock = new ParameterStorageString(blockStorage,null);
            oneBlock.setParameter("class",iconBlock.getClass().getName());
            iconBlock.write(oneBlock);
            out.setParameter("block0",blockStorage.load());

            if(blocks!=null) {
                int i=0;
                for(int x=0;x<blocks.length;x++) {
                    for(int y=0;y<blocks[x].length;y++) {
                        if(blocks[x][y]!=null) {
                            if(blocks[x][y] instanceof BlockText) {
                                i++;
                                blockStorage = new StringStorage();
                                oneBlock = new ParameterStorageString(blockStorage,null);
                                oneBlock.setParameter("class",blocks[x][y].getClass().getName());
                                blocks[x][y].write(oneBlock);
                                out.setParameter("block"+i,blockStorage.load());
                            }
                        }
                    }
                }
            }
        }
    }

    public void readBlocks(ParameterValueStorageExInterface in)
    {
        StorageInterface blockStorage = in.getParameterAsStorage("block0");
        if(blockStorage!=null ) {
            ParameterStorageString oneBlock = new ParameterStorageString(blockStorage,null);
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
                Image iconImage = environment.getImageCreator().createImage(256,256);
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
        super.readBlocks(in);
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
