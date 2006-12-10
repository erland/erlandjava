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
import erland.webapp.datacollection.fb.entry.data.DataFB;
import erland.webapp.datacollection.entity.entry.data.Data;
import erland.webapp.datacollection.entity.entry.Entry;
import erland.util.StringUtil;
import org.apache.commons.beanutils.PropertyUtils;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;
import java.net.URL;

public class ViewDataAction extends BaseAction {
    protected void executeLogic(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        DataFB fb = (DataFB) form;
        Data template = (Data) getEnvironment().getEntityFactory().create("datacollection-data");

        template.setId(fb.getId());
        Data data = (Data) getEnvironment().getEntityStorageFactory().getStorage("datacollection-data").load(template);
        if (data != null) {
            PropertyUtils.copyProperties(fb, data);
            if(StringUtil.asNull(fb.getUrl())!=null){
                try {
                    BufferedReader reader = new BufferedReader(new InputStreamReader(new URL(fb.getUrl()).openStream()));
                    StringBuffer buffer = new StringBuffer(10000);
                    String line = reader.readLine();
                    while(line!=null) {
                        buffer.append(line);
                        buffer.append("\n");
                        line = reader.readLine();
                    }
                    fb.setContent(buffer.toString());
                } catch (IOException e) {
                    fb.setContent(null);
                }
            }
        }
        Entry entryTemplate =(Entry) getEnvironment().getEntityFactory().create("datacollection-entry");
        entryTemplate.setId(data.getEntryId());
        Entry entry = (Entry) getEnvironment().getEntityStorageFactory().getStorage("datacollection-entry").load(entryTemplate);
        request.setAttribute("filenamePB",entry.getUniqueEntryId()+"."+data.getType());
    }
}
