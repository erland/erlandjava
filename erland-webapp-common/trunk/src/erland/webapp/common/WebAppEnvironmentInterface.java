package erland.webapp.common;

import erland.util.ParameterValueStorageExInterface;

public interface WebAppEnvironmentInterface {
    public ParameterValueStorageExInterface getResources();

    public EntityFactoryInterface getEntityFactory();

    public EntityStorageFactoryInterface getEntityStorageFactory();

    public CommandFactoryInterface getCommandFactory();
}
