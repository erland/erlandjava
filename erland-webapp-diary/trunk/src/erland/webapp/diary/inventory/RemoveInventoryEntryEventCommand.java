package erland.webapp.diary.inventory;

import erland.webapp.common.*;

import javax.servlet.http.HttpServletRequest;

public class RemoveInventoryEntryEventCommand implements CommandInterface {
    private WebAppEnvironmentInterface environment;

    public void init(WebAppEnvironmentInterface environment) {
        this.environment = environment;
    }

    public String execute(HttpServletRequest request) {
        String id = request.getParameter("id");
        String eventId = request.getParameter("eventid");
        InventoryEntryEvent entry = (InventoryEntryEvent)environment.getEntityFactory().create("inventoryentryevent");
        if(id!=null && eventId!=null) {
            entry.setId(Integer.valueOf(id));
            entry.setEventId(Integer.valueOf(eventId));
            environment.getEntityStorageFactory().getStorage("inventoryentryevent").delete(entry);
        }
        return null;
    }
}
