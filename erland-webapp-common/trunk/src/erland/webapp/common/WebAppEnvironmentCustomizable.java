package erland.webapp.common;

import erland.util.ParameterValueStorageExInterface;

public class WebAppEnvironmentCustomizable implements WebAppEnvironmentInterface {
    private WebAppEnvironmentInterface environment;
    private ParameterValueStorageExInterface resources;
    private EntityFactoryInterface entityFactory;
    private EntityStorageFactoryInterface entityStorageFactory;
    private CommandFactoryInterface commandFactory;

    public WebAppEnvironmentCustomizable(WebAppEnvironmentInterface environment) {
        this.environment = environment;
    }
    public void setResources(ParameterValueStorageExInterface resources) {
        this.resources = resources;
    }
    public void setEntityFactory(EntityFactoryInterface entityFactory) {
        this.entityFactory = entityFactory;
    }
    public void setEntityStorageFactory(EntityStorageFactoryInterface entityStorageFactory) {
        this.entityStorageFactory = entityStorageFactory;
    }

    public void setCommandFactory(CommandFactoryInterface commandFactory) {
        this.commandFactory = commandFactory;
    }
    public ParameterValueStorageExInterface getResources() {
        if(resources!=null) {
            return resources;
        }else {
            return environment.getResources();
        }
    }

    public EntityFactoryInterface getEntityFactory() {
        if(entityFactory!=null) {
            return entityFactory;
        }else {
            return environment.getEntityFactory();
        }
    }

    public EntityStorageFactoryInterface getEntityStorageFactory() {
        if(entityStorageFactory!=null) {
            return entityStorageFactory;
        }else {
            return environment.getEntityStorageFactory();
        }
    }

    public CommandFactoryInterface getCommandFactory() {
        if(commandFactory!=null) {
            return commandFactory;
        }else {
            return environment.getCommandFactory();
        }
    }
}
