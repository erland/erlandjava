package erland.game.racer;

import erland.game.MapEditorBlockInterface;
import erland.game.GameEnvironmentInterface;
import erland.game.BlockContainerInterface;
import erland.util.ParameterValueStorageExInterface;

import java.awt.*;

/**
 * Abstract class for all blocks
 * @author Erland Isaksson
 */
public abstract class Block implements MapEditorBlockInterface, Cloneable {
    /** The block container which the block exist in */
    private BlockContainerInterface cont;
    /** The x position of the block */
    private int posX;
    /** The y position of the block */
    private int posY;
    /** The game environment of the block */
    private GameEnvironmentInterface environment;
    /** The array of friction objects associated with the block */
    private FrictionBlock[] frictionObjects;

    public void init(GameEnvironmentInterface environment)
    {
        this.environment = environment;
    }

    /**
     * Get the game environment which the block exists in
     * @return The game environment the block exists in
     */
    protected GameEnvironmentInterface getEnvironment()
    {
        return environment;
    }
    /**
     * Get the container which the block exists in
     * @return The block container
     */
    protected BlockContainerInterface getContainer()
    {
        return cont;
    }

    public void setContainer(BlockContainerInterface cont)
    {
        this.cont = cont;
    }

    /**
     * Set the friction objects associated with the block
     * @param frictionObjects An array with all {@link FrictionBlock} associated with the block
     */
    public void setFrictionObjects(FrictionBlock[] frictionObjects)
    {
        //Log.println(this,"setFrictionObjects: "+posX+","+posY+","+frictionObjects);
        for(int i=0;i<frictionObjects.length;i++) {
            //Log.println(this,"setFrictionObjects2: "+frictionObjects[i].getBounds().getX()+","+frictionObjects[i].getBounds().getY());
        }
        this.frictionObjects = frictionObjects;
    }

    /**
     * Get all friction objects associated with the block
     * @return An array of friction objects
     */
    public FrictionObjectInterface[] getFrictionObjects()
    {
        return frictionObjects;
    }

    public int getPosX()
    {
        return posX;
    }

    public int getPosY()
    {
        return posY;
    }

    public void setPos(int x, int y)
    {
        posX = x;
        posY = y;
        if(frictionObjects!=null) {
            for(int i=0;i<frictionObjects.length;i++) {
                frictionObjects[i].setOffset(cont.getPositionX(x),cont.getPositionY(y));
                if(x>=2 && x<=4 && y>=2 && y<=4) {
                    //Log.println(this,"setPos("+x+","+y+") friction offset "+cont.getPositionX(x)+","+cont.getPositionY(y)+","+frictionObjects[i].getBounds().getX()+","+frictionObjects[i].getBounds().getY()+","+frictionObjects[i]);
                }
            }
        }
    }

    /**
     * Uppdate the block
     */
    public void update()
    {
    }

    /**
     * Check if the block needs to be redrawn
     * @return true/false (Needs redraw/Does not need redraw)
     */
    public boolean getRedraw()
    {
        return false;
    }

    /**
     * Draw the specified level of the block on the specified Graphics object
     * @param g The Graphics object to draw on
     * @param level The level of the block to draw, the background is 0
     */
    public abstract void draw(Graphics g,int level);

    /**
     * Clone the block
     * @return A clone of the block
     * @throws CloneNotSupportedException
     */
    public Object clone()
		throws CloneNotSupportedException
	{
		Block b=(Block)super.clone();
        if(frictionObjects!=null) {
            if(posX>=2 && posX<=4 && posY>=2 & posY<=4) {
                //Log.println(this,"Friction clone: "+this.posX+","+this.posY);
            }
            b.frictionObjects = new FrictionBlock[frictionObjects.length];
            for(int i=0;i<frictionObjects.length;i++) {
                b.frictionObjects[i] = (FrictionBlock)(frictionObjects[i].clone());
                if(posX>=2 && posX<=4 && posY>=2 & posY<=4) {
                    //Log.println(this,"Friction clone2: "+frictionObjects[i].getBounds().getX()+","+frictionObjects[i].getBounds().getX()+","+b.frictionObjects[i].getBounds().getX()+","+b.frictionObjects[i].getBounds().getX());
                }
            }
        }
		return b;
	}

    public void write(ParameterValueStorageExInterface out) {
        out.setParameter("x",Integer.toString(getPosX()));
        out.setParameter("y",Integer.toString(getPosY()));
    }

    public void read(ParameterValueStorageExInterface in) {
        int x = Integer.valueOf(in.getParameter("x")).intValue();
        int y = Integer.valueOf(in.getParameter("y")).intValue();
        setPos(x,y);
    }
}
