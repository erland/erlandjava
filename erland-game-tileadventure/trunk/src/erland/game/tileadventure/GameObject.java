package erland.game.tileadventure;
/*
 * Copyright (C) 2004 Erland Isaksson (erland_i@hotmail.com)
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 2
 * of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA  02111-1307, USA.
 *
 */

import erland.game.GameEnvironmentInterface;
import erland.util.ParameterValueStorageExInterface;

import java.awt.*;


public abstract class GameObject implements Cloneable, MapObjectInterface {
    /** The block container which the block exist in */
    private IrregularBlockContainerInterface cont;
    /** The x position of the block */
    private float posX;
    /** The y position of the block */
    private float posY;
    /** The z position of the block */
    private float posZ;
    /** The game environment of the block */
    private GameEnvironmentInterface environment;
    /** The object map which the object lives in */
    private MapObjectContainerInterface objectMap;
    /** The object that shall take car of object actions */
    private GameObjectMapActionInterface actionMap;
    /** Child objects */
    private GameObject[] childs;
    /** Parent object */
    private GameObject parent;

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
    public IrregularBlockContainerInterface getContainer()
    {
        return cont;
    }

    public void setContainer(IrregularBlockContainerInterface cont)
    {
        this.cont = cont;
    }

    public void setObjectMap(MapObjectContainerInterface objectMap) {
        this.objectMap = objectMap;
    }

    public MapObjectContainerInterface getObjectMap() {
        return objectMap;
    }

    public void setActionMap(GameObjectMapActionInterface actionMap) {
        this.actionMap = actionMap;
    }
    public GameObjectMapActionInterface getActionMap() {
        return actionMap;
    }

    public float getPosX()
    {
        return posX;
    }

    public float getPosY()
    {
        return posY;
    }

    public float getPosZ() {
        return posZ;
    }

    public float getMovingPosX()
    {
        return getPosX();
    }

    public float getMovingPosY()
    {
        return getPosY();
    }

    public float getMovingPosZ() {
        return getPosZ();
    }

    public int getPixelPosX() {
        return getContainer().getPositionX(getPosX(),getPosY(),getPosZ());
    }
    public int getPixelPosY() {
        return getContainer().getPositionY(getPosX(),getPosY(),getPosZ());
    }
    public int getDrawingPosX(float dx,float dy, float dz) {
        return getContainer().getDrawingPositionX(getPosX()+dx,getPosY()+dy,getPosZ()+dz);
    }

    public int getDrawingPosY(float dx, float dy, float dz) {
        return getContainer().getDrawingPositionY(getPosX()+dx,getPosY()+dy,getPosZ()+dz);
    }

    public void setPos(float x, float y, float z)
    {
        if(objectMap!=null) {
            objectMap.removeObject(this,(int)posX,(int)posY,(int)posZ);
            objectMap.setObject(this,(int)x,(int)y,(int)z);
        }
        posX = x;
        posY = y;
        posZ = z;
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
     */
    public Object clone()
	{
        GameObject b=null;
        try {
            b = (GameObject)super.clone();
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
        return b;
	}

    public void write(ParameterValueStorageExInterface out) {
        out.setParameter("x",Integer.toString((int)getPosX()));
        out.setParameter("y",Integer.toString((int)getPosY()));
        out.setParameter("z",Integer.toString((int)getPosZ()));
    }

    public void read(ParameterValueStorageExInterface in) {
        int x = Integer.valueOf(in.getParameter("x")).intValue();
        int y = Integer.valueOf(in.getParameter("y")).intValue();
        int z = Integer.valueOf(in.getParameter("z")).intValue();
        setPos(x,y,z);
    }

    protected boolean actionChild(Action action) {
        return false;
    }

    public boolean action(Action action) {
        if(parent!=null) {
            return parent.action(action);
        }else {
            Action newAction = action;
            if(childs!=null) {
                for (int i = 0; i < childs.length; i++) {
                    Action a = childs[i].isActionPossible(action);
                    if(a==Action.NONE) {
                        return false;
                    }else if(!action.equals(a)) {
                        newAction = a;
                    }
                }
                Action a = isActionPossible(action);
                if(a==Action.NONE) {
                    return false;
                }else if(!action.equals(a)) {
                    newAction = a;
                }
                for (int i = 0; i < childs.length; i++) {
                    childs[i].actionChild(newAction);
                }
            }
            return actionChild(newAction);
        }
    }

    protected Action isActionPossible(Action action) {
        return Action.NONE;
    }
    protected boolean isMovableChild(Direction direction) {
        return false;
    }
    public boolean isMovable(Direction direction) {
        if(parent!=null) {
            return parent.isMovable(direction);
        }else {
            if(childs!=null) {
                for (int i = 0; i < childs.length; i++) {
                    if(!childs[i].isMovableChild(direction)) {
                        return false;
                    }
                }
            }
            return isMovableChild(direction);
        }
    }

    protected boolean isPushableChild(Direction direction) {
        return false;
    }
    public boolean isPushable(Direction direction) {
        if(parent!=null) {
            return parent.isPushable(direction);
        }else {
            if(childs!=null) {
                for (int i = 0; i < childs.length; i++) {
                    if(!childs[i].isPushableChild(direction)) {
                        return false;
                    }
                }
            }
            return isPushableChild(direction);
        }
    }
    public void setParent(GameObject parent) {
        this.parent = parent;
    }
    public void setChilds(GameObject[] childs) {
        this.childs = childs;
    }

    public GameObject[] getChilds() {
        return childs;
    }

    public GameObject getParent() {
        return parent;
    }
}

