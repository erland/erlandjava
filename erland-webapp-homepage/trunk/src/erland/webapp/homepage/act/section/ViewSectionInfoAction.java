package erland.webapp.homepage.act.section;

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

import erland.webapp.common.DescriptionIdHelper;
import erland.webapp.common.ServletParameterHelper;
import erland.webapp.common.act.BaseAction;
import erland.webapp.common.fb.DescriptionIdPB;
import erland.webapp.homepage.entity.section.Section;
import erland.webapp.homepage.entity.service.Service;
import erland.webapp.homepage.fb.section.SectionFB;
import erland.webapp.homepage.fb.section.SelectSectionFB;
import erland.webapp.homepage.fb.section.SectionPB;
import erland.webapp.homepage.logic.service.ServiceInterface;
import erland.webapp.homepage.logic.service.TransformerInterface;
import erland.util.StringUtil;
import erland.util.ParameterValueStorageExInterface;
import erland.util.StringStorage;
import erland.util.ParameterStorageParameterString;
import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionForward;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;
import java.net.URL;
import java.net.URLConnection;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.BufferedReader;

public class ViewSectionInfoAction extends BaseAction {
    /** Logging instance */
    private static Log LOG = LogFactory.getLog(ViewSectionInfoAction.class);

    protected void executeLogic(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        SelectSectionFB fb = (SelectSectionFB) form;
        String username = request.getParameter("user");
        if(StringUtil.asNull(username)==null) {
            username = request.getRemoteUser();
        }
        Section template = (Section) getEnvironment().getEntityFactory().create("homepage-section");
        template.setId(fb.getSection());
        template.setUsername(username);
        Section section = (Section) getEnvironment().getEntityStorageFactory().getStorage("homepage-section").load(template);
        SectionPB pb = new SectionPB();
        PropertyUtils.copyProperties(pb, section);
        boolean useEnglish = !request.getLocale().getLanguage().equals(getEnvironment().getConfigurableResources().getParameter("nativelanguage"));
        if(useEnglish && StringUtil.asNull(section.getNameEnglish())!=null) {
            pb.setName(section.getNameEnglish());
        }
        if(useEnglish && StringUtil.asNull(section.getTitleEnglish())!=null) {
            pb.setTitle(section.getTitleEnglish());
        }
        if(useEnglish && StringUtil.asNull(section.getDescriptionEnglish())!=null) {
            pb.setDescription(section.getDescriptionEnglish());
        }
        Map parameters = new HashMap();
        parameters.put("user",username);
        parameters.put("section",section.getId());
        ActionForward forward = mapping.findForward("update-section-link");
        if(forward!=null) {
            pb.setUpdateLink(ServletParameterHelper.replaceDynamicParameters(forward.getPath(),parameters));
        }
        forward = mapping.findForward("remove-section-link");
        if(forward!=null) {
            pb.setRemoveLink(ServletParameterHelper.replaceDynamicParameters(forward.getPath(),parameters));
        }
        pb.setServiceResult("");
        if(section.getService()!=null && section.getService().intValue()>0) {
            Service templateService = (Service) getEnvironment().getEntityFactory().create("homepage-service");
            templateService.setId(section.getService());
            Service service = (Service) getEnvironment().getEntityStorageFactory().getStorage("homepage-service").load(templateService);
            if(service!=null) {
                try {
                    LOG.debug("Loading service: "+service.getServiceClass());
                    Class serviceCls = Class.forName(service.getServiceClass());
                    Object serviceInstance = serviceCls.newInstance();
                    if(serviceInstance instanceof ServiceInterface) {
                        Map parameterValues = new HashMap();
                        parameterValues.put("language",request.getLocale().getLanguage());
                        parameterValues.put("user",username);
                        String sectionParameterString = ServletParameterHelper.replaceDynamicParameters(StringUtil.asEmpty(section.getServiceParameters()),parameterValues);
                        String serviceParameterString = ServletParameterHelper.replaceDynamicParameters(StringUtil.asEmpty(service.getServiceData()),parameterValues);
                        ParameterValueStorageExInterface serviceParameters = new ParameterStorageParameterString(new StringStorage(sectionParameterString),new StringStorage(serviceParameterString),',',StringUtil.asEmpty(service.getCustomizedServiceData()),null);
                        LOG.debug("Executing service: "+serviceInstance);
                        String result = (((ServiceInterface)serviceInstance).execute(serviceParameters));
                        LOG.trace(result);
                        if(StringUtil.asNull(service.getTransformerClass())!=null) {
                            LOG.debug("Loading transformer: "+service.getTransformerClass());
                            Class transformerCls = Class.forName(service.getTransformerClass());
                            Object transformerInstance = transformerCls.newInstance();
                            if(transformerInstance instanceof TransformerInterface) {
                                sectionParameterString = StringUtil.asEmpty(sectionParameterString);
                                if(request.getRemoteUser()!=null) {
                                    sectionParameterString+=",usertype=user";
                                }else {
                                    sectionParameterString+=",usertype=guest";
                                    sectionParameterString+=",user="+username;
                                }
                                ParameterValueStorageExInterface transformerParameters = new ParameterStorageParameterString(new StringStorage(sectionParameterString),new StringStorage(service.getTransformerData()),',',StringUtil.asEmpty(service.getCustomizedTransformerData()),null);
                                LOG.debug("Transforming with: "+transformerInstance);
                                result = ((TransformerInterface)transformerInstance).transform(result,transformerParameters);
                                LOG.trace("After transform: "+result);
                            }
                        }
                        pb.setServiceResult(result);
                    }
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
                }
            }
        }
        request.setAttribute("sectionPB",pb);
    }
}
