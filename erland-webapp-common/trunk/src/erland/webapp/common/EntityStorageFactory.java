package erland.webapp.common;

import erland.webapp.common.WebAppEnvironmentInterface;
import erland.webapp.common.EntityInterface;

import java.util.HashMap;
import java.util.Map;

public class EntityStorageFactory implements EntityStorageFactoryInterface {
    private WebAppEnvironmentInterface environment;
    private Map storages = new HashMap();
    public EntityStorageFactory(WebAppEnvironmentInterface environment) {
        this.environment = environment;
    }

    public EntityStorageInterface getStorage(String entityName) {
        String clsName = environment.getResources().getParameter("entities."+entityName+".storage.class");
        if (clsName != null && clsName.length()>0) {
            try {
                EntityStorageInterface storage = (EntityStorageInterface) storages.get(entityName);
                if(storage==null) {
                    storage = (EntityStorageInterface)Class.forName(clsName).newInstance();
                    storage.init(environment);
                    storage.setEntityName(entityName);
                    storages.put(entityName,storage);
                }
                return storage;
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            } catch (InstantiationException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (ClassCastException e) {
                e.printStackTrace();
            }
        }
        return null;
    }
}
