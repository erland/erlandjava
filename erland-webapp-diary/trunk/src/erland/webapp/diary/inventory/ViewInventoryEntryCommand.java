package erland.webapp.diary.inventory;

import erland.webapp.common.WebAppEnvironmentInterface;
import erland.webapp.common.CommandInterface;
import erland.webapp.common.QueryFilter;
import erland.webapp.common.EntityInterface;

import javax.servlet.http.HttpServletRequest;

public class ViewInventoryEntryCommand implements CommandInterface, ViewInventoryEntryInterface {
    private WebAppEnvironmentInterface environment;
    private InventoryEntry entry;

    public void init(WebAppEnvironmentInterface environment) {
        this.environment = environment;
    }

    public String execute(HttpServletRequest request) {
        String id = request.getParameter("id");
        InventoryEntry template = (InventoryEntry)environment.getEntityFactory().create("inventoryentry");
        if(id!=null) {
            template.setId(Integer.valueOf(id));
            entry = (InventoryEntry) environment.getEntityStorageFactory().getStorage("inventoryentry").load(template);
        }
        return null;
    }

    public InventoryEntry getEntry() {
        return entry;
    }

    public InventoryEntryEvent[] getEvents() {
        QueryFilter filter = new QueryFilter("allforid");
        filter.setAttribute("id",entry.getId());
        EntityInterface[] entities = environment.getEntityStorageFactory().getStorage("inventoryentryevent").search(filter);
        InventoryEntryEvent[] events = new InventoryEntryEvent[entities.length];
        for (int i = 0; i < entities.length; i++) {
            events[i] = (InventoryEntryEvent) entities[i];
        }
        return events;
    }
}
