package erland.webapp.common;

import erland.webapp.common.EntityInterface;
import erland.webapp.common.WebAppEnvironmentInterface;

public interface EntityStorageInterface {
    void init(WebAppEnvironmentInterface environment);
    void setEntityName(String entityName);
    EntityInterface load(EntityInterface template);
    boolean store(EntityInterface entity);
    boolean delete(EntityInterface entity);
    EntityInterface[] search(FilterInterface filter);
    boolean update(FilterInterface filter, EntityInterface entity);
    boolean delete(FilterInterface filter);
}
