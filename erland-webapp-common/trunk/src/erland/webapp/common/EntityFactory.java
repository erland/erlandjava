package erland.webapp.common;

import erland.webapp.common.WebAppEnvironmentInterface;
import erland.webapp.common.EntityInterface;

public class EntityFactory implements EntityFactoryInterface {
    private WebAppEnvironmentInterface environment;
    public EntityFactory(WebAppEnvironmentInterface environment) {
        this.environment = environment;
    }
    public EntityInterface create(String entityName) {
        String clsName = environment.getResources().getParameter("entities."+entityName+".class");
        if (clsName != null && clsName.length()>0) {
            try {
                EntityInterface entity = (EntityInterface)Class.forName(clsName).newInstance();
                entity.init(environment);
                return entity;
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
