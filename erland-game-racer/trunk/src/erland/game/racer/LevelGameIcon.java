package erland.game.racer;

import erland.game.MapEditorBlockInterface;
import erland.util.*;

import java.awt.*;
import java.util.Vector;

/**
 * Implementation of the block that is used to build the track in the race mode.
 */
public class LevelGameIcon extends Level {
    /**
     * Creates a block
     * @param blockCloneManager Object used for cloning the blocks
     * @param sizeX Number of horizontal blocks in the game area
     * @param sizeY Number of vertial blocks in the game area
     */
    public LevelGameIcon(BlockCloneInterface blockCloneManager, int sizeX, int sizeY)
    {
        super(blockCloneManager,sizeX,sizeY);
    }
    /**
     * This method is NOT IMPLEMENTED in this class
     * @param out
     */
    public void write(ParameterValueStorageExInterface out)
    {

    }

    public void readBlocks(ParameterValueStorageExInterface in)
    {
        int i=0;
        int x;
        int y;
        StorageInterface blockStorage = in.getParameterAsStorage("block"+i);
        MapEditorBlockInterface b = null;
        if(blockStorage!=null) {
            ParameterStorageString oneBlock = new ParameterStorageString(blockStorage,null);
            String cls = oneBlock.getParameter("class");
            try {
                b = (MapEditorBlockInterface)Class.forName(cls).newInstance();
            } catch (InstantiationException e) {
            } catch (IllegalAccessException e) {
            } catch (ClassNotFoundException e) {
            }
            b.init(environment);
            b.setContainer(cont);
            b.read(oneBlock);
            x = b.getPosX();
            y = b.getPosY();
            if(x>=0 && x<sizeX && y>=0 && y<sizeY) {
                blocks[x][y] = prepareNewBlock(blocks[x][y],b);
            }

            //Log.println(this,"Block: "+x+","+y);
            // Extract friction information
            boolean bQuit = false;
            i=0;
            Vector frictionBlocks = new Vector();
            while(!bQuit) {
                i++;
                StorageInterface frictionStorage = in.getParameterAsStorage("block"+i);
                if(frictionStorage!=null ) {
                    ParameterStorageString oneBlockText = new ParameterStorageString(frictionStorage,null);
                    cls = oneBlockText.getParameter("class");
                    MapEditorBlockInterface blockText = null;
                    try {
                        blockText = (MapEditorBlockInterface)Class.forName(cls).newInstance();
                    } catch (InstantiationException e) {
                    } catch (IllegalAccessException e) {
                    } catch (ClassNotFoundException e) {
                    }
                    blockText.init(environment);
                    blockText.setContainer(cont);
                    blockText.read(oneBlockText);
                    if(blockText instanceof BlockText) {
                        String frictionStr = ((BlockText)blockText).getText();
                        int friction = 0;
                        try {
                            friction = Integer.valueOf(frictionStr).intValue();
                        } catch (NumberFormatException e) {
                        }
                        FrictionBlock frictionBlock = new FrictionBlock(cont.getSquareSize()/4);
                        frictionBlock.setFriction(friction);
                        frictionBlock.setOffset(cont.getPositionX(x),cont.getPositionY(y));
                        //Log.println(this,"Friction: "+cont.getPositionX(x)+","+cont.getPositionY(y)+","+blockText.getPosX()+","+blockText.getPosY());
                        frictionBlock.setPos(blockText.getPosX(),blockText.getPosY());
                        frictionBlocks.addElement(frictionBlock);
                    }
                }else {
                    bQuit = true;
                }
            }
            if(frictionBlocks.size()>0) {
                FrictionBlock[] tmp = new FrictionBlock[frictionBlocks.size()];
                for(int j=0;j<frictionBlocks.size();j++) {
                    tmp[j] = (FrictionBlock) frictionBlocks.elementAt(j);
                }

                ((Block)b).setFrictionObjects(tmp);
            }
        }
    }
}
