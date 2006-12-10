package erland.webapp.datacollection.fb.entry;

import erland.webapp.common.fb.BaseFB;
import org.apache.struts.action.ActionMapping;

import javax.servlet.http.HttpServletRequest;

public class SearchEntryFB extends BaseFB {
    private String application;

    public String getApplication() {
        return application;
    }

    public void setApplication(String application) {
        this.application = application;
    }

    public void reset(ActionMapping actionMapping, HttpServletRequest servletRequest) {
        super.reset(actionMapping, servletRequest);
        application=null;
    }
}
