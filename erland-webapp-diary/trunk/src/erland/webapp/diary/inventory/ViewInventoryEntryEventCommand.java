package erland.webapp.diary.inventory;

import erland.webapp.common.CommandInterface;
import erland.webapp.common.WebAppEnvironmentInterface;

import javax.servlet.http.HttpServletRequest;

public class ViewInventoryEntryEventCommand implements CommandInterface, ViewInventoryEntryEventInterface {
    private WebAppEnvironmentInterface environment;
    private InventoryEntryEvent entry;

    public void init(WebAppEnvironmentInterface environment) {
        this.environment = environment;
    }

    public String execute(HttpServletRequest request) {
        String id = request.getParameter("id");
        String eventId = request.getParameter("eventid");
        InventoryEntryEvent template = (InventoryEntryEvent)environment.getEntityFactory().create("inventoryentryevent");
        if(eventId!=null) {
            template.setId(Integer.valueOf(id));
            template.setEventId(Integer.valueOf(eventId));
            entry = (InventoryEntryEvent) environment.getEntityStorageFactory().getStorage("inventoryentryevent").load(template);
        }
        return null;
    }

    public InventoryEntryEvent getEntry() {
        return entry;
    }
}
