/*
 * Created by IntelliJ IDEA.
 * User: Erland Isaksson
 * Date: 2002-apr-21
 * Time: 08:14:35
 * To change template for new class use 
 * Code Style | Class Templates options (Tools | IDE Options).
 */
package erland.game.racer;

import erland.game.MapEditorBlockInterface;
import erland.game.BlockContainerData;
import erland.util.*;

import java.awt.*;
import java.util.Vector;

public class LevelGameIcon extends LevelSimple {
    protected Image iconImage;
    protected MapEditorBlockInterface iconBlock;
    public LevelGameIcon(BlockCloneInterface blockCloneManager, int sizeX, int sizeY)
    {
        super(blockCloneManager,sizeX,sizeY);
    }
    public void write(ParameterValueStorageInterface out)
    {

    }

    public void read(ParameterValueStorageInterface in)
    {
        StringStorage levelStorage = new StringStorage(in.getParameter("data"));
        ParameterStorageGroup allBlocks = new ParameterStorageGroup(levelStorage,"blockdata","block");
        int i=0;
        int x;
        int y;
        String s = allBlocks.getParameter("block"+i);
        MapEditorBlockInterface b = null;
        if(s!=null && s.length()>0) {
            StringStorage blockStorage = new StringStorage(s);
            ParameterStorageString oneBlock = new ParameterStorageString(blockStorage);
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
                s = allBlocks.getParameter("block"+i);
                if(s!=null && s.length()>0) {
                    StringStorage frictionStorage = new StringStorage(s);
                    ParameterStorageString oneBlockText = new ParameterStorageString(frictionStorage);
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
                        frictionBlocks.add(frictionBlock);
                    }
                }else {
                    bQuit = true;
                }
            }
            if(frictionBlocks.size()>0) {
                FrictionBlock[] tmp = new FrictionBlock[0];
                tmp = (FrictionBlock[])frictionBlocks.toArray(tmp);
                ((Block)b).setFrictionObjects(tmp);
            }
        }
    }
}
