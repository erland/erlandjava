package erland.webapp.homepage.logic.service;

import erland.util.ParameterStorageParameterString;
import erland.util.ParameterValueStorageExInterface;
import erland.util.StringStorage;
import erland.util.StringUtil;
import erland.webapp.common.EntityInterface;
import erland.webapp.common.QueryFilter;
import erland.webapp.common.ServletParameterHelper;
import erland.webapp.common.WebAppEnvironmentInterface;
import erland.webapp.homepage.entity.service.Service;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.util.HashMap;
import java.util.Map;

/*
 * Copyright (C) 2003-2004 Erland Isaksson (erland_i@hotmail.com)
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

public class ServiceHelper {
    /**
     * Logging instance
     */
    private static Log LOG = LogFactory.getLog(ServiceHelper.class);

    public static String getServiceData(WebAppEnvironmentInterface environment, String service, String parameters) {
        LOG.debug("Get service: "+service+"="+parameters);
        QueryFilter filter = new QueryFilter("allforname");
        filter.setAttribute("name", service);
        EntityInterface[] entities = environment.getEntityStorageFactory().getStorage("homepage-service").search(filter);
        if (entities.length > 0) {
            Service serviceEntity = (Service) entities[0];
            try {
                LOG.debug("Loading service: " + serviceEntity.getServiceClass());
                Class serviceCls = Class.forName(serviceEntity.getServiceClass());
                Object serviceInstance = serviceCls.newInstance();
                if (serviceInstance instanceof ServiceInterface) {
                    Map parameterValues = new HashMap();
                    String user = ServletParameterHelper.getParameter(parameters,"user",',');
                    if(StringUtil.asNull(user)!=null) {
                        parameterValues.put("user",user);
                    }
                    String usertype = ServletParameterHelper.getParameter(parameters,"usertype",',');
                    if(StringUtil.asNull(usertype)!=null) {
                        parameterValues.put("usertype",usertype);
                    }
                    String language = ServletParameterHelper.getParameter(parameters,"language",',');
                    if(StringUtil.asNull(language)!=null) {
                        parameterValues.put("language",language);
                    }
                    String sectionParameterString = ServletParameterHelper.replaceDynamicParameters(StringUtil.asEmpty(parameters), parameterValues);
                    String serviceParameterString = ServletParameterHelper.replaceDynamicParameters(StringUtil.asEmpty(serviceEntity.getServiceData()), parameterValues);
                    ParameterValueStorageExInterface serviceParameters = new ParameterStorageParameterString(new StringStorage(sectionParameterString), new StringStorage(serviceParameterString), ',', StringUtil.asEmpty(serviceEntity.getCustomizedServiceData()), null);
                    LOG.debug("Executing service: " + serviceInstance);
                    String result = (((ServiceInterface) serviceInstance).execute(serviceParameters));
                    LOG.trace(result);
                    if (StringUtil.asNull(serviceEntity.getTransformerClass()) != null) {
                        LOG.debug("Loading transformer: " + serviceEntity.getTransformerClass());
                        Class transformerCls = Class.forName(serviceEntity.getTransformerClass());
                        Object transformerInstance = transformerCls.newInstance();
                        if (transformerInstance instanceof TransformerInterface) {
                            sectionParameterString = StringUtil.asEmpty(sectionParameterString);
                            if(StringUtil.asEmpty(usertype).equals("user")) {
                                sectionParameterString=ServletParameterHelper.replaceParameter(sectionParameterString,"usertype","user",',');
                            }else {
                                sectionParameterString=ServletParameterHelper.replaceParameter(sectionParameterString,"usertype","guest",',');
                                sectionParameterString=ServletParameterHelper.replaceParameter(sectionParameterString,"user",user,',');
                            }
                            ParameterValueStorageExInterface transformerParameters = new ParameterStorageParameterString(new StringStorage(sectionParameterString), new StringStorage(serviceEntity.getTransformerData()), ',', StringUtil.asEmpty(serviceEntity.getCustomizedTransformerData()), null);
                            LOG.debug("Transforming with: " + transformerInstance);
                            result = ((TransformerInterface) transformerInstance).transform(result, transformerParameters);
                            LOG.trace("After transform: " + result);
                        }
                    }
                    return result;
                }
            } catch (ClassNotFoundException e) {
                e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
            } catch (IllegalAccessException e) {
                e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
            } catch (InstantiationException e) {
                e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
            }
        }
        return null;
    }
}