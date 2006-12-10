package erland.webapp.datacollection.act.entry;

import erland.webapp.common.act.BaseAction;
import erland.webapp.datacollection.fb.entry.EntryFB;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionForm;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class NewEntryAction extends BaseAction {

    protected void executeLogic(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        EntryFB fb = (EntryFB) form;
        fb.setId(null);
        fb.setTitle(null);
        fb.setUniqueEntryId(null);
    }
}
