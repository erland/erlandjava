package erland.webapp.diary.inventory;

import erland.webapp.common.*;

import javax.servlet.http.HttpServletRequest;

public class RemoveInventoryEntryCommand implements CommandInterface {
    private WebAppEnvironmentInterface environment;

    public void init(WebAppEnvironmentInterface environment) {
        this.environment = environment;
    }

    public String execute(HttpServletRequest request) {
        String id = request.getParameter("id");
        InventoryEntry entry = (InventoryEntry)environment.getEntityFactory().create("inventoryentry");
        if(id!=null) {
            entry.setId(Integer.valueOf(id));
            QueryFilter filter = new QueryFilter("allforid");
            filter.setAttribute("id",entry.getId());
            EntityStorageInterface storage = environment.getEntityStorageFactory().getStorage("inventoryentryevent");
            EntityInterface[] entities = storage.search(filter);
            for (int i = 0; i < entities.length; i++) {
                EntityInterface entity = entities[i];
                storage.delete(entity);
            }
            environment.getEntityStorageFactory().getStorage("inventoryentry").delete(entry);
        }
        return null;
    }
}
