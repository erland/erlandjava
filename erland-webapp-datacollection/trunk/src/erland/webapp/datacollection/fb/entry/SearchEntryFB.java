package erland.webapp.datacollection.fb.entry;

import erland.util.StringUtil;
import erland.webapp.common.fb.BaseFB;
import org.apache.struts.action.ActionMapping;

import javax.servlet.http.HttpServletRequest;

public class SearchEntryFB extends BaseFB {
    private String application;
    private String versionDisplay;

    public String getApplication() {
        return application;
    }

    public void setApplication(String application) {
        this.application = application;
    }

    public Integer getVersion() {
        return StringUtil.asInteger(versionDisplay,null);
    }

    public void setVersion(Integer version) {
        versionDisplay = StringUtil.asString(version,null);
    }

    public String getVersionDisplay() {
        return versionDisplay;
    }

    public void setVersionDisplay(String versionDisplay) {
        this.versionDisplay = versionDisplay;
    }

    public void reset(ActionMapping actionMapping, HttpServletRequest servletRequest) {
        super.reset(actionMapping, servletRequest);
        application=null;
        versionDisplay=null;
    }
}
