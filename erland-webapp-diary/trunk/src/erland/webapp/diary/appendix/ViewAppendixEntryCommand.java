package erland.webapp.diary.appendix;

import erland.webapp.common.WebAppEnvironmentInterface;
import erland.webapp.common.CommandInterface;
import erland.webapp.diary.appendix.AppendixEntry;

import javax.servlet.http.HttpServletRequest;

public class ViewAppendixEntryCommand implements CommandInterface, ViewAppendixEntryInterface {
    private WebAppEnvironmentInterface environment;
    private AppendixEntry entry;

    public void init(WebAppEnvironmentInterface environment) {
        this.environment = environment;
    }

    public String execute(HttpServletRequest request) {
        String id = request.getParameter("id");
        AppendixEntry template = (AppendixEntry)environment.getEntityFactory().create("appendixentry");
        if(id!=null) {
            template.setId(Integer.valueOf(id));
            entry = (AppendixEntry) environment.getEntityStorageFactory().getStorage("appendixentry").load(template);
        }
        return null;
    }

    public AppendixEntry getEntry() {
        return entry;
    }
}
