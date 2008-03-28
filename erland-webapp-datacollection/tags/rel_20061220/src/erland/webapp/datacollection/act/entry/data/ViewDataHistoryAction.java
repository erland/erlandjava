package erland.webapp.datacollection.act.entry.data;

import erland.webapp.common.act.BaseAction;
import erland.webapp.datacollection.fb.entry.data.DataFB;
import erland.webapp.datacollection.entity.entry.data.Data;
import erland.webapp.datacollection.entity.entry.data.DataHistory;
import erland.webapp.datacollection.entity.entry.Entry;
import erland.webapp.datacollection.entity.entry.EntryHistory;
import erland.util.StringUtil;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionForm;
import org.apache.commons.beanutils.PropertyUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;
import java.net.URL;

public class ViewDataHistoryAction extends BaseAction {
    protected void executeLogic(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        DataFB fb = (DataFB) form;
        DataHistory template = (DataHistory) getEnvironment().getEntityFactory().create("datacollection-datahistory");

        template.setHistoryId(fb.getId());
        DataHistory data = (DataHistory) getEnvironment().getEntityStorageFactory().getStorage("datacollection-datahistory").load(template);
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
        EntryHistory entryTemplate =(EntryHistory) getEnvironment().getEntityFactory().create("datacollection-entryhistory");
        entryTemplate.setHistoryId(data.getEntryHistoryId());
        EntryHistory entry = (EntryHistory) getEnvironment().getEntityStorageFactory().getStorage("datacollection-entryhistory").load(entryTemplate);
        request.setAttribute("filenamePB",entry.getUniqueEntryId()+"."+data.getType());
    }
}
