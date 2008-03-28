package erland.webapp.datacollection.act.entry;

import erland.webapp.common.act.BaseAction;
import erland.webapp.common.ServletParameterHelper;
import erland.webapp.common.QueryFilter;
import erland.webapp.common.EntityInterface;
import erland.webapp.datacollection.fb.entry.EntryFB;
import erland.webapp.datacollection.fb.entry.EntryPB;
import erland.webapp.datacollection.fb.entry.data.DataPB;
import erland.webapp.datacollection.entity.entry.Entry;
import erland.webapp.datacollection.entity.entry.EntryHistory;
import erland.webapp.datacollection.entity.entry.data.Data;
import erland.webapp.datacollection.entity.entry.data.DataHistory;
import erland.util.StringUtil;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.commons.beanutils.PropertyUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;
import java.util.HashMap;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;
import java.net.URL;

public class ViewEntryHistoryInfoAction extends BaseAction {
    protected void executeLogic(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        EntryFB fb = (EntryFB) form;
        EntryHistory template = (EntryHistory) getEnvironment().getEntityFactory().create("datacollection-entryhistory");

        template.setHistoryId(fb.getId());
        EntryHistory entry = (EntryHistory) getEnvironment().getEntityStorageFactory().getStorage("datacollection-entryhistory").load(template);
        EntryPB pb = new EntryPB();
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
            QueryFilter dataFilter = new QueryFilter("allforhistoryentry");
            dataFilter.setAttribute("historyentry",entry.getHistoryId());
            EntityInterface[] datas = getEnvironment().getEntityStorageFactory().getStorage("datacollection-datahistory").search(dataFilter);
            DataPB[] datasPB = new DataPB[datas.length];
            for (int i = 0; i < datas.length; i++) {
                DataHistory data = (DataHistory) datas[i];
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
                parameters.put("datahistory",data.getHistoryId());
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
            }
            pb.setDatas(datasPB);
        }
        request.setAttribute("entryPB",pb);
    }
}
