package erland.webapp.tvguide.act.service;

/*
 * Copyright (C) 2005 Erland Isaksson (erland_i@hotmail.com)
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

import erland.util.ParameterStorageParameterString;
import erland.util.ParameterValueStorageExInterface;
import erland.util.StringStorage;
import erland.util.StringUtil;
import erland.webapp.common.EntityInterface;
import erland.webapp.common.QueryFilter;
import erland.webapp.common.ServletParameterHelper;
import erland.webapp.common.act.BaseAction;
import erland.webapp.tvguide.entity.service.Service;
import erland.webapp.tvguide.fb.service.SelectServiceFB;
import erland.webapp.tvguide.logic.service.ServiceInterface;
import erland.webapp.tvguide.logic.service.TransformerInterface;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class ViewServiceInfoAction extends BaseAction{
    /** Logging instance */
    private static Log LOG = LogFactory.getLog(ViewServiceInfoAction.class);

    protected void executeLogic(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        SelectServiceFB fb = (SelectServiceFB) form;
        String username = request.getParameter("user");
        if(StringUtil.asNull(username)==null) {
            username = request.getRemoteUser();
        }
        if(StringUtil.asNull(fb.getService())!=null) {
            QueryFilter filter = new QueryFilter("allforname");
            filter.setAttribute("name",fb.getService());
            EntityInterface[] entities = getEnvironment().getEntityStorageFactory().getStorage("tvguide-service").search(filter);
            if(entities.length>0) {
                Service serviceEntity = (Service) entities[0];
                try {
                    LOG.debug("Loading service: "+serviceEntity.getServiceClass());
                    Class serviceCls = Class.forName(serviceEntity.getServiceClass());
                    Object serviceInstance = serviceCls.newInstance();
                    if(serviceInstance instanceof ServiceInterface) {
                        Map parameterValues = new HashMap();
                        parameterValues.put("language",request.getLocale().getLanguage());
                        parameterValues.put("user",username);
                        if(request.getRemoteUser()!=null) {
                            parameterValues.put("usertype","user");
                        }else {
                            parameterValues.put("usertype","guest");
                        }
                        String sectionParameterString = ServletParameterHelper.replaceDynamicParameters(StringUtil.asEmpty(getRequestParameters(request)),parameterValues);
                        String serviceParameterString = ServletParameterHelper.replaceDynamicParameters(StringUtil.asEmpty(serviceEntity.getServiceData()),parameterValues);
                        ParameterValueStorageExInterface serviceParameters = new ParameterStorageParameterString(new StringStorage(sectionParameterString),new StringStorage(serviceParameterString),',',StringUtil.asEmpty(serviceEntity.getCustomizedServiceData()),null);
                        LOG.debug("Executing service: "+serviceInstance);
                        String result = (((ServiceInterface)serviceInstance).execute(serviceParameters));
                        LOG.trace(result);
                        if(StringUtil.asNull(serviceEntity.getTransformerClass())!=null) {
                            LOG.debug("Loading transformer: "+serviceEntity.getTransformerClass());
                            Class transformerCls = Class.forName(serviceEntity.getTransformerClass());
                            Object transformerInstance = transformerCls.newInstance();
                            if(transformerInstance instanceof TransformerInterface) {
                                sectionParameterString = StringUtil.asEmpty(sectionParameterString);
                                if(request.getRemoteUser()!=null) {
                                    sectionParameterString=ServletParameterHelper.replaceParameter(sectionParameterString,"usertype","user",',');
                                }else {
                                    sectionParameterString=ServletParameterHelper.replaceParameter(sectionParameterString,"usertype","guest",',');
                                    sectionParameterString=ServletParameterHelper.replaceParameter(sectionParameterString,"user",username,',');
                                }
                                ParameterValueStorageExInterface transformerParameters = new ParameterStorageParameterString(new StringStorage(sectionParameterString),new StringStorage(serviceEntity.getTransformerData()),',',StringUtil.asEmpty(serviceEntity.getCustomizedTransformerData()),null);
                                LOG.debug("Transforming with: "+transformerInstance);
                                result = ((TransformerInterface)transformerInstance).transform(result,transformerParameters);
                                LOG.trace("After transform: "+result);
                            }
                        }
                        request.setAttribute("serivceResultPB",result);
                    }
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
                }
            }
        }
    }
    private String getRequestParameters(HttpServletRequest request) {
        Map map = request.getParameterMap();
        StringBuffer sb = new StringBuffer();
        boolean bFirst = true;
        for(Iterator it=map.entrySet().iterator();it.hasNext();) {
            Map.Entry entry = (Map.Entry) it.next();
            Object value = entry.getValue();
            if(value instanceof String[]) {
                String[] values = (String[]) value;
                for (int i = 0; i < values.length; i++) {
                    if(!bFirst) {
                        sb.append(',');
                    }
                    sb.append(entry.getKey());
                    sb.append("=");
                    sb.append(values[i]);
                    bFirst = false;
                }
            }else if(value!=null) {
                if(!bFirst) {
                    sb.append(',');
                }
                sb.append(entry.getKey());
                sb.append("=");
                sb.append(value);
                bFirst = false;
            }
        }
        return sb.toString();
    }
}