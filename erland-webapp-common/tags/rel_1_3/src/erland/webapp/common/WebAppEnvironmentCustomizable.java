package erland.webapp.common;
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

import erland.util.ParameterValueStorageExInterface;

public class WebAppEnvironmentCustomizable implements WebAppEnvironmentInterface {
    private WebAppEnvironmentInterface environment;
    private ParameterValueStorageExInterface resources;
    private ParameterValueStorageExInterface configurableResources;
    private EntityFactoryInterface entityFactory;
    private EntityStorageFactoryInterface entityStorageFactory;
    private ServiceFactoryInterface serviceFactory;

    public WebAppEnvironmentCustomizable(WebAppEnvironmentInterface environment) {
        this.environment = environment;
    }
    public void setResources(ParameterValueStorageExInterface resources) {
        this.resources = resources;
    }
    public void setConfigurableResources(ParameterValueStorageExInterface configurableResources) {
        this.configurableResources = configurableResources;
    }
    public void setEntityFactory(EntityFactoryInterface entityFactory) {
        this.entityFactory = entityFactory;
    }
    public void setEntityStorageFactory(EntityStorageFactoryInterface entityStorageFactory) {
        this.entityStorageFactory = entityStorageFactory;
    }
    public void setServiceFactory(ServiceFactoryInterface serviceFactory) {
        this.serviceFactory = serviceFactory;
    }

    public ParameterValueStorageExInterface getResources() {
        if(resources!=null) {
            return resources;
        }else {
            return environment.getResources();
        }
    }
    public ParameterValueStorageExInterface getConfigurableResources() {
        if(configurableResources!=null) {
            return configurableResources;
        }else {
            return environment.getConfigurableResources();
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

    public ServiceFactoryInterface getServiceFactory() {
        if(serviceFactory!=null) {
            return serviceFactory;
        }else {
            return environment.getServiceFactory();
        }
    }
}
