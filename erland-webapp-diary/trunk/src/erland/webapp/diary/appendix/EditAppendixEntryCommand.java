package erland.webapp.diary.appendix;

import erland.webapp.common.CommandInterface;
import erland.webapp.common.WebAppEnvironmentInterface;
import erland.webapp.diary.appendix.AppendixEntry;

import javax.servlet.http.HttpServletRequest;

public class EditAppendixEntryCommand implements CommandInterface, ViewAppendixEntryInterface {
    private AppendixEntry entry;
    private WebAppEnvironmentInterface environment;

    public void init(WebAppEnvironmentInterface environment) {
        this.environment = environment;
    }

    public String execute(HttpServletRequest request) {
        String id = request.getParameter("id");
        String name = request.getParameter("name");
        String description = request.getParameter("description");
        entry = (AppendixEntry)environment.getEntityFactory().create("appendixentry");
        if(id!=null && id.length()>0) {
            entry.setId(Integer.valueOf(id));
        }
        entry.setName(name);
        entry.setDescription(description);
        environment.getEntityStorageFactory().getStorage("appendixentry").store(entry);
        return null;
    }

    public AppendixEntry getEntry() {
        return entry;
    }
}
