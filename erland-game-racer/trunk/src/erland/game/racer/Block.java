package erland.game.racer;

import erland.game.MapEditorBlockInterface;
import erland.game.GameEnvironmentInterface;
import erland.game.BlockContainerInterface;
import erland.util.Log;
import erland.util.ParameterValueStorageInterface;

import java.awt.*;

public abstract class Block implements MapEditorBlockInterface, Cloneable {
    protected BlockContainerInterface cont;
    protected int posX;
    protected int posY;
    protected GameEnvironmentInterface environment;
    protected FrictionBlock[] frictionObjects;

    public void init(GameEnvironmentInterface environment)
    {
        this.environment = environment;
    }

    public void setContainer(BlockContainerInterface cont)
    {
        this.cont = cont;
    }

    public void setFrictionObjects(FrictionBlock[] frictionObjects)
    {
        //Log.println(this,"setFrictionObjects: "+posX+","+posY+","+frictionObjects);
        for(int i=0;i<frictionObjects.length;i++) {
            //Log.println(this,"setFrictionObjects2: "+frictionObjects[i].getBounds().getX()+","+frictionObjects[i].getBounds().getY());
        }
        this.frictionObjects = frictionObjects;
    }

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

    public void update()
    {
    }

    public boolean getRedraw()
    {
        return false;
    }

    public abstract void draw(Graphics g,int level);

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

    public void write(ParameterValueStorageInterface out) {
        out.setParameter("x",Integer.toString(getPosX()));
        out.setParameter("y",Integer.toString(getPosY()));
    }

    public void read(ParameterValueStorageInterface in) {
        int x = Integer.valueOf(in.getParameter("x")).intValue();
        int y = Integer.valueOf(in.getParameter("y")).intValue();
        setPos(x,y);
    }
}
