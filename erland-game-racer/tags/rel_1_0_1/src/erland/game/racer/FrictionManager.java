package erland.game.racer;
/*
 * Copyright (C) 2003 Erland Isaksson (erland_i@hotmail.com)
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

import java.util.Vector;
import java.awt.*;

/**
 * Manager that applies current friction to managed object
 */
public class FrictionManager {
    /** Managed objects that frictions should be applied to */
    Vector objects;
    /** Friction object container that contains the friction of the environment */
    FrictionObjectContainerInterface cont;

    /**
     * Creates a friction manager
     * @param cont The fricton object container that this friction manager should get frictions from
     */
    public FrictionManager(FrictionObjectContainerInterface cont)
    {
        objects = new Vector();
        this.cont = cont;
    }
    /**
     * Add a friction sensitive object that friction of the environment should be applied to
     * @param object The friction object to add
     */
    public void addObject(FrictionSensitiveInterface object)
    {
        if(!objects.contains(object)) {
            objects.addElement(object);
        }
    }
    /**
     * Remove an earlier added friction sensitive object that friction of the environment no longer should be applied to
     * @param object The friction object to remove
     */
    public void removeObject(FrictionSensitiveInterface object)
    {
        objects.removeElement(object);
    }

    /**
     * Check if the specified friction sensitive object collides with a specified friction object and if it does
     * return the friction else return 0 friction
     * @param obj The friction sensitive object
     * @param friction The friction object to check collision with
     * @return The current friction
     */
    protected double checkFriction(FrictionSensitiveInterface obj,FrictionObjectInterface friction)
    {
        Rectangle rc1 = obj.getBounds();
        Rectangle rc2 = friction.getBounds();
        if(rc1.intersects(rc2) || CommonRectangle.contains(rc1,rc2) || CommonRectangle.contains(rc2,rc1)) {
            return friction.getFriction();
        }else {
            return 0;
        }
    }

    /**
     * Check friction of all managed friction sensitive objects agains the environment and apply
     * the environmen friction to the managed friction sensitive objects
     */
    public void handleFrictions()
    {
        for(int i=0;i<objects.size();i++) {
            double maxFriction=0;
            FrictionSensitiveInterface obj = (FrictionSensitiveInterface)objects.elementAt(i);
            FrictionObjectInterface frictions[] = cont.getFrictionObjects(obj);
            for(int j=0;j<frictions.length && frictions[j]!=null;j++) {
                double currentFriction = checkFriction(obj,frictions[j]);
                if(currentFriction>maxFriction) {
                    maxFriction=currentFriction;
                }
            }
            obj.setFriction(maxFriction);
        }
    }
}
