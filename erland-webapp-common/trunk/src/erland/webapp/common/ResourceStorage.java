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
import erland.util.StorageInterface;
import erland.util.StringStorage;
import erland.webapp.common.WebAppEnvironmentInterface;

import java.util.Map;
import java.util.HashMap;

public class ResourceStorage implements ParameterValueStorageExInterface {
    private String entityName;
    private WebAppEnvironmentInterface environment;
    private boolean enableCache;
    private Map cache = new HashMap();

    public ResourceStorage(WebAppEnvironmentInterface environment, String entityName, boolean enableCache) {
        this.entityName = entityName;
        this.environment = environment;
        this.enableCache = enableCache;
    }
    public StorageInterface getParameterAsStorage(String name) {
        if(enableCache) {
            String value = (String) cache.get(name);
            if(value!=null) {
                return new StringStorage("<"+name+">"+value+"</"+name+">");
            }
        }
        ResourceInterface resource = (ResourceInterface) environment.getEntityFactory().create(entityName);
        resource.setId(name);
        resource = (ResourceInterface) environment.getEntityStorageFactory().getStorage(entityName).load(resource);
        if(resource!=null) {
            if(enableCache) {
                cache.put(name,resource.getValue());
            }
            return new StringStorage("<"+name+">"+resource.getValue()+"</"+name+">");
        }else {
            return null;
        }
    }

    public String getParameter(String name) {
        if(enableCache) {
            String value = (String) cache.get(name);
            if(value!=null) {
                return value;
            }
        }
        ResourceInterface resource = (ResourceInterface) environment.getEntityFactory().create(entityName);
        resource.setId(name);
        resource = (ResourceInterface) environment.getEntityStorageFactory().getStorage(entityName).load(resource);
        if(resource!=null) {
            if(enableCache) {
                cache.put(name,resource.getValue());
            }
            return resource.getValue();
        }else {
            return null;
        }
    }

    public void setParameter(String name, String value) {
        ResourceInterface resource = (ResourceInterface) environment.getEntityFactory().create(entityName);
        resource.setId(name);
        resource.setValue(value);
        environment.getEntityStorageFactory().getStorage(entityName).store(resource);
        if(enableCache) {
            cache.put(name,value);
        }
    }

    public void delParameter(String name) {
        ResourceInterface resource = (ResourceInterface) environment.getEntityFactory().create(entityName);
        resource.setId(name);
        environment.getEntityStorageFactory().getStorage(entityName).delete(resource);
        if(enableCache) {
            cache.remove(name);
        }
    }
}