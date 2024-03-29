package erland.webapp.common.act;

import org.apache.struts.action.PlugIn;
import org.apache.struts.action.ActionServlet;
import org.apache.struts.config.ModuleConfig;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;

import erland.webapp.common.*;
import erland.util.*;

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

public class WebAppEnvironmentPlugin implements PlugIn, WebAppEnvironmentInterface {

    private static WebAppEnvironmentInterface environment;
    private ParameterValueStorageExInterface resources=null;
    private ParameterValueStorageExInterface configurableResources=null;
    private EntityFactoryInterface entityFactory = null;
    private EntityStorageFactoryInterface entityStorageFactory = null;
    private ServiceFactoryInterface serviceFactory = null;
    private StorageInterface storage;
    private HttpServlet servlet = null;
    private String applicationName;
    private String resourceFile = "WEB-INF/resources.xml";
    private String configurableResourcesEntityName = "common-resource";
    private Boolean configurableResourcesCache = Boolean.TRUE;

    public ParameterValueStorageExInterface getConfigurableResources() {
        if(configurableResources==null) {
            configurableResources = new ParameterStorageChild("resources.",new ResourceStorage(getCurrentEnvironment(),applicationName,getConfigurableResourcesEntityName(),getConfigurableResourcesCache().booleanValue()));
        }
        return configurableResources;
    }

    public EntityFactoryInterface getEntityFactory() {
        if(entityFactory==null) {
            entityFactory = new EntityFactory(getCurrentEnvironment());
        }
        return entityFactory;
    }

    public EntityStorageFactoryInterface getEntityStorageFactory() {
        if(entityStorageFactory==null) {
            entityStorageFactory = new EntityStorageFactory(getCurrentEnvironment());
        }
        return entityStorageFactory;
    }

    public ParameterValueStorageExInterface getResources() {
        if(resources==null) {
            resources = new ParameterStorageChild("resources.",new ParameterStorageTree(getStorage(),new JarFileStorageFactory()));
        }
        return resources;
    }

    public ServiceFactoryInterface getServiceFactory() {
        if(serviceFactory==null) {
            serviceFactory = new ServiceFactory(getCurrentEnvironment());
        }
        return serviceFactory;
    }

    protected StorageInterface getStorage() {
        if(storage==null) {
            storage = new FileStorage(servlet.getServletContext().getRealPath("/")+getResourceFile());
        }
        return storage;
    }

    public void destroy() {
        //To change body of implemented methods use Options | File Templates.
    }

    public void init(ActionServlet actionServlet, ModuleConfig moduleConfig) throws ServletException {
        servlet = actionServlet;
        environment = getCurrentEnvironment();
    }

    protected WebAppEnvironmentInterface getCurrentEnvironment() {
        return this;
    }

    protected String getConfigurableResourcesEntityName() {
        return configurableResourcesEntityName;
    }
    protected Boolean getConfigurableResourcesCache() {
        return configurableResourcesCache;
    }

    public static WebAppEnvironmentInterface getEnvironment() {
        return environment;
    }

    public void setApplicationName(String name) {
        applicationName = name;
    }

    public String getApplicationName() {
        return applicationName;
    }

    public String getResourceFile() {
        return resourceFile;
    }

    public void setResourceFile(String resourceFile) {
        this.resourceFile = resourceFile;
    }

    public void setConfigurableResourcesEntityName(String configurableResourcesEntityName) {
        this.configurableResourcesEntityName = configurableResourcesEntityName;
    }

    public void setConfigurableResourcesCache(Boolean configurableResourcesCache) {
        this.configurableResourcesCache = configurableResourcesCache;
    }
    public void setXmlParser(String xmlParser) {
        try {
            Class parser = getClass().getClassLoader().loadClass(xmlParser);
            if(parser != null) {
                Object obj = parser.newInstance();
                if(obj instanceof XMLParserInterface) {
                    XMLParser.setInstance((XMLParserInterface)obj);
                }
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        } catch (InstantiationException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        } catch (IllegalAccessException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
    }
}