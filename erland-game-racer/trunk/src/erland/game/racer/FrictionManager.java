package erland.game.racer;

import java.util.Vector;
import java.awt.*;

public class FrictionManager {
    Vector objects;
    FrictionObjectContainerInterface cont;

    public FrictionManager(FrictionObjectContainerInterface cont)
    {
        objects = new Vector();
        this.cont = cont;
    }
    public void addObject(FrictionSensitiveInterface object)
    {
        if(!objects.contains(object)) {
            objects.add(object);
        }
    }
    public void removeObject(FrictionSensitiveInterface object)
    {
        objects.remove(object);
    }

    protected double checkFriction(FrictionSensitiveInterface obj,FrictionObjectInterface friction)
    {
        Rectangle rc1 = obj.getBounds();
        Rectangle rc2 = friction.getBounds();
        if(rc1.intersects(rc2) || rc1.contains(rc2) || rc2.contains(rc1)) {
            return friction.getFriction();
        }else {
            return 0;
        }
    }

    public void handleFrictions()
    {
        for(int i=0;i<objects.size()-1;i++) {
            double maxFriction=0;
            FrictionSensitiveInterface obj = (FrictionSensitiveInterface)objects.get(i);
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
