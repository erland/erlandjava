package erland.game.isoadventure;

import erland.game.GameEnvironmentInterface;
import erland.util.ParameterValueStorageExInterface;

import java.awt.*;


public abstract class IsoObject implements Cloneable, IsoObjectInterface {
    /** The block container which the block exist in */
    private IrregularBlockContainerInterface cont;
    /** The x position of the block */
    private int posX;
    /** The y position of the block */
    private int posY;
    /** The z position of the block */
    private int posZ;
    /** The game environment of the block */
    private GameEnvironmentInterface environment;
    /** The object map which the object lives in */
    private IsoObjectMapInterface objectMap;
    /** The object that shall take car of object actions */
    private IsoObjectMapActionInterface actionMap;

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
    protected IrregularBlockContainerInterface getContainer()
    {
        return cont;
    }

    public void setContainer(IrregularBlockContainerInterface cont)
    {
        this.cont = cont;
    }

    public void setObjectMap(IsoObjectMapInterface objectMap) {
        this.objectMap = objectMap;
    }

    protected IsoObjectMapInterface getObjectMap() {
        return objectMap;
    }

    public void setActionMap(IsoObjectMapActionInterface actionMap) {
        this.actionMap = actionMap;
    }
    public IsoObjectMapActionInterface getActionMap() {
        return actionMap;
    }

    public int getPosX()
    {
        return posX;
    }

    public int getPosY()
    {
        return posY;
    }

    public int getPosZ() {
        return posZ;
    }

    public int getMovingPosX()
    {
        return getPosX();
    }

    public int getMovingPosY()
    {
        return getPosY();
    }

    public int getMovingPosZ() {
        return getPosZ();
    }

    public int getPixelPosX() {
        return getContainer().getPositionX(getPosX(),getPosY(),getPosZ());
    }
    public int getPixelPosY() {
        return getContainer().getPositionY(getPosX(),getPosY(),getPosZ());
    }
    public int getDrawingPosX(float dx,float dy, float dz) {
        return getContainer().getDrawingPositionX(getPosX(),getPosY(),getPosZ(),dx,dy,dz);
    }

    public int getDrawingPosY(float dx, float dy, float dz) {
        return getContainer().getDrawingPositionY(getPosX(),getPosY(),getPosZ(),dx,dy,dz);
    }

    public void setPos(int x, int y, int z)
    {
        if(objectMap!=null) {
            objectMap.removeObject(this,posX,posY,posZ);
            objectMap.addObject(this,x,y,z);
        }
        posX = x;
        posY = y;
        posZ = z;
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
     * Clone the block
     * @return A clone of the block
     * @throws CloneNotSupportedException
     */
    public Object clone()
		throws CloneNotSupportedException
	{
		IsoObject b=(IsoObject)super.clone();
		return b;
	}

    public void write(ParameterValueStorageExInterface out) {
        out.setParameter("x",Integer.toString(getPosX()));
        out.setParameter("y",Integer.toString(getPosY()));
        out.setParameter("z",Integer.toString(getPosZ()));
    }

    public void read(ParameterValueStorageExInterface in) {
        int x = Integer.valueOf(in.getParameter("x")).intValue();
        int y = Integer.valueOf(in.getParameter("y")).intValue();
        int z = Integer.valueOf(in.getParameter("z")).intValue();
        setPos(x,y,z);
    }

    public boolean action(Action action) {
        return false;
    }

    public boolean isMovable(Direction direction) {
        return false;
    }

    public boolean isPushable(Direction direction) {
        return false;
    }
}

