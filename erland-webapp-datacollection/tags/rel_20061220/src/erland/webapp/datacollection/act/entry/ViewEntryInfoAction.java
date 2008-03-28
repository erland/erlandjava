package erland.webapp.datacollection.act.entry;

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
import erland.webapp.common.QueryFilter;
import erland.webapp.common.EntityInterface;
import erland.webapp.datacollection.fb.entry.EntryFB;
import erland.webapp.datacollection.fb.entry.EntryPB;
import erland.webapp.datacollection.fb.entry.EntryHistoryPB;
import erland.webapp.datacollection.fb.entry.data.DataPB;
import erland.webapp.datacollection.fb.collection.CollectionPB;
import erland.webapp.datacollection.entity.entry.Entry;
import erland.webapp.datacollection.entity.entry.EntryHistory;
import erland.webapp.datacollection.entity.entry.data.Data;
import erland.webapp.datacollection.entity.collection.Collection;
import erland.util.StringUtil;
import org.apache.commons.beanutils.PropertyUtils;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionForward;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;
import java.net.URL;

public class ViewEntryInfoAction extends BaseAction {
    protected void executeLogic(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        EntryFB fb = (EntryFB) form;
        Entry template = (Entry) getEnvironment().getEntityFactory().create("datacollection-entry");

        template.setId(fb.getId());
        Entry entry = (Entry) getEnvironment().getEntityStorageFactory().getStorage("datacollection-entry").load(template);
        EntryPB pb = new EntryPB();
        CollectionPB pbCollection = new CollectionPB();
        if (entry != null) {
            PropertyUtils.copyProperties(pb, entry);
            Map parameters = new HashMap();
            if(StringUtil.asNull(request.getServerName())!=null) {
                parameters.put("hostname",request.getServerName());
                if(request.getServerPort()!=80) {
                    parameters.put("port",new Integer(request.getServerPort()));
                }
            }
            parameters.put("contextpath",request.getContextPath());
            parameters.put("entry",pb.getId());
            ActionForward forward = mapping.findForward("entry-update-link");
            if(forward != null) {
                pb.setUpdateLink(ServletParameterHelper.replaceDynamicParameters(forward.getPath(),parameters));
            }
            forward = mapping.findForward("entry-remove-link");
            if(forward != null) {
                pb.setRemoveLink(ServletParameterHelper.replaceDynamicParameters(forward.getPath(),parameters));
            }
            forward = mapping.findForward("entry-newdata-link");
            if(forward != null) {
                pb.setNewDataLink(ServletParameterHelper.replaceDynamicParameters(forward.getPath(),parameters));
            }

            ActionForward dataViewForward = mapping.findForward("data-view-link");
            ActionForward dataDownloadForward = mapping.findForward("data-download-link");
            ActionForward dataUpdateForward = mapping.findForward("data-update-link");
            ActionForward dataRemoveForward = mapping.findForward("data-remove-link");
            QueryFilter dataFilter = new QueryFilter("allforentry");
            dataFilter.setAttribute("entry",pb.getId());
            EntityInterface[] datas = getEnvironment().getEntityStorageFactory().getStorage("datacollection-data").search(dataFilter);
            DataPB[] datasPB = new DataPB[datas.length];
            for (int i = 0; i < datas.length; i++) {
                Data data = (Data) datas[i];
                datasPB[i] = new DataPB();
                PropertyUtils.copyProperties(datasPB[i],data);
                if(StringUtil.asNull(data.getUrl())!=null) {
                    try {
                        BufferedReader reader = new BufferedReader(new InputStreamReader(new URL(data.getUrl()).openStream()));
                        StringBuffer buffer = new StringBuffer(10000);
                        String line = reader.readLine();
                        while(line!=null) {
                            buffer.append(line);
                            buffer.append("\n");
                            line = reader.readLine();
                        }
                        datasPB[i].setContent(buffer.toString());
                    } catch (IOException e) {
                        datasPB[i].setContent(null);
                    }
                }
                if(StringUtil.asEmpty(data.getType()).equals(entry.getTitle())) {
                    datasPB[i].setType(null);
                }
                parameters.put("data",data.getId());
                if(dataViewForward!=null) {
                    datasPB[i].setViewLink(ServletParameterHelper.replaceDynamicParameters(dataViewForward.getPath(),parameters));
                }
                if(dataDownloadForward!=null) {
                    datasPB[i].setDownloadLink(ServletParameterHelper.replaceDynamicParameters(dataDownloadForward.getPath(),parameters));
                }
                if(dataUpdateForward!=null) {
                    datasPB[i].setUpdateLink(ServletParameterHelper.replaceDynamicParameters(dataUpdateForward.getPath(),parameters));
                }
                if(dataRemoveForward!=null) {
                    datasPB[i].setRemoveLink(ServletParameterHelper.replaceDynamicParameters(dataRemoveForward.getPath(),parameters));
                }
                if(StringUtil.asNull(datasPB[i].getUrl()) != null) {
                    pb.setLastChanged(null);
                }
            }
            pb.setDatas(datasPB);

            ActionForward entryHistoryForward = mapping.findForward("entry-history-link");
            if(entryHistoryForward!=null) {
                QueryFilter filter = new QueryFilter("allforentry");
                filter.setAttribute("entry",entry.getId());
                EntityInterface[] historyEntries = getEnvironment().getEntityStorageFactory().getStorage("datacollection-entryhistory").search(filter);
                EntryHistoryPB[] historyPB = new EntryHistoryPB[historyEntries.length];
                for (int i = 0; i < historyEntries.length; i++) {
                    EntryHistory historyEntry = (EntryHistory) historyEntries[i];
                    historyPB[i] = new EntryHistoryPB();
                    PropertyUtils.copyProperties(historyPB[i],historyEntry);
                    parameters.put("entryhistory",historyEntry.getHistoryId());
                    historyPB[i].setHistoryLink(ServletParameterHelper.replaceDynamicParameters(entryHistoryForward.getPath(),parameters));
                }
                if(historyPB.length>0) {
                    pb.setHistoryEntries(historyPB);
                }
            }

            Collection collectionTemplate = (Collection) getEnvironment().getEntityFactory().create("datacollection-collection");
            collectionTemplate.setId(entry.getCollection());
            Collection collection = (Collection) getEnvironment().getEntityStorageFactory().getStorage("datacollection-collection").load(collectionTemplate);
            PropertyUtils.copyProperties(pbCollection, collection);
            request.setAttribute("collectionPB",pbCollection);
        }
        request.setAttribute("entryPB",pb);
    }
}
