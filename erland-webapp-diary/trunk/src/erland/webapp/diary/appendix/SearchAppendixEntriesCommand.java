package erland.webapp.diary.appendix;

import erland.webapp.common.CommandInterface;
import erland.webapp.common.WebAppEnvironmentInterface;
import erland.webapp.common.QueryFilter;
import erland.webapp.common.EntityInterface;
import erland.webapp.diary.appendix.AppendixEntry;

import javax.servlet.http.HttpServletRequest;

public class SearchAppendixEntriesCommand implements CommandInterface, ViewAppendixEntriesInterface {
    private WebAppEnvironmentInterface environment;
    private AppendixEntry[] entries;

    public void init(WebAppEnvironmentInterface environment) {
        this.environment = environment;
    }

    public String execute(HttpServletRequest request) {
        EntityInterface[] entities = environment.getEntityStorageFactory().getStorage("appendixentry").search(new QueryFilter("all"));
        entries = new AppendixEntry[entities.length];
        for(int i=0;i<entities.length;i++) {
            entries[i] = (AppendixEntry)entities[i];
        }
        return null;
    }

    public AppendixEntry[] getEntries() {
        return entries;
    }
}
