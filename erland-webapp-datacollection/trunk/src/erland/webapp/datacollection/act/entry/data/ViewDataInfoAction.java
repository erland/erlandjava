package erland.webapp.datacollection.act.entry.data;

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

import erland.webapp.common.act.BaseAction;
import erland.webapp.common.ServletParameterHelper;
import erland.webapp.datacollection.fb.entry.data.DataPB;
import erland.webapp.datacollection.fb.entry.data.DataFB;
import erland.webapp.datacollection.entity.entry.data.Data;
import erland.util.StringUtil;
import org.apache.commons.beanutils.PropertyUtils;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionForward;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;
import java.net.URL;
import java.io.*;

public class ViewDataInfoAction extends BaseAction {
    protected void executeLogic(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        DataFB fb = (DataFB) form;
        Data template = (Data) getEnvironment().getEntityFactory().create("datacollection-data");

        template.setId(fb.getId());
        Data entry = (Data) getEnvironment().getEntityStorageFactory().getStorage("datacollection-data").load(template);
        DataPB pb = new DataPB();
        if (entry != null) {
            PropertyUtils.copyProperties(pb, entry);
            if(StringUtil.asNull(pb.getUrl())!=null){
                try {
                    BufferedReader reader = new BufferedReader(new InputStreamReader(new URL(pb.getUrl()).openStream()));
                    StringBuffer buffer = new StringBuffer(10000);
                    String line = reader.readLine();
                    while(line!=null) {
                        buffer.append(line);
                        buffer.append("\n");
                        line = reader.readLine();
                    }
                    pb.setContent(buffer.toString());
                } catch (IOException e) {
                    pb.setContent(null);
                }
            }
            Map parameters = new HashMap();
            if(StringUtil.asNull(request.getServerName())!=null) {
                parameters.put("hostname",request.getServerName());
                if(request.getServerPort()!=80) {
                    parameters.put("port",new Integer(request.getServerPort()));
                }
            }
            parameters.put("contextpath",request.getContextPath());
            parameters.put("entry",pb.getId());
            ActionForward forward = mapping.findForward("data-update-link");
            if(forward != null) {
                pb.setUpdateLink(ServletParameterHelper.replaceDynamicParameters(forward.getPath(),parameters));
            }
            forward = mapping.findForward("data-remove-link");
            if(forward != null) {
                pb.setRemoveLink(ServletParameterHelper.replaceDynamicParameters(forward.getPath(),parameters));
            }
        }
        request.setAttribute("dataPB",pb);
    }
}
