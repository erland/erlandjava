package erland.webapp.diary.appendix;

import erland.webapp.common.CommandInterface;
import erland.webapp.common.WebAppEnvironmentInterface;
import erland.webapp.diary.appendix.AppendixEntry;

import javax.servlet.http.HttpServletRequest;

public class RemoveAppendixEntryCommand implements CommandInterface {
    private WebAppEnvironmentInterface environment;

    public void init(WebAppEnvironmentInterface environment) {
        this.environment = environment;
    }

    public String execute(HttpServletRequest request) {
        String id = request.getParameter("id");
        AppendixEntry entry = (AppendixEntry)environment.getEntityFactory().create("appendixentry");
        if(id!=null) {
            entry.setId(Integer.valueOf(id));
            environment.getEntityStorageFactory().getStorage("appendixentry").delete(entry);
        }
        return null;
    }
}
