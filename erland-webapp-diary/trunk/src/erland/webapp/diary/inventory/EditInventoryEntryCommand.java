package erland.webapp.diary.inventory;

import erland.webapp.common.WebAppEnvironmentInterface;
import erland.webapp.common.CommandInterface;
import erland.webapp.common.QueryFilter;
import erland.webapp.common.EntityInterface;
import erland.webapp.usermgmt.User;

import javax.servlet.http.HttpServletRequest;
import java.text.*;
import java.util.Date;

public class EditInventoryEntryCommand implements CommandInterface, ViewInventoryEntryInterface {
    private InventoryEntry entry;
    private final static DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
    private WebAppEnvironmentInterface environment;

    public void init(WebAppEnvironmentInterface environment) {
        this.environment = environment;
    }

    public String execute(HttpServletRequest request) {
        try {
            String id = request.getParameter("id");
            String name = request.getParameter("name");
            String description = request.getParameter("description");
            String image = request.getParameter("image");
            String link = request.getParameter("link");
            String type = request.getParameter("type");
            String size = request.getParameter("size");
            User user = (User) request.getSession().getAttribute("user");
            String username = user.getUsername();
            entry = (InventoryEntry)environment.getEntityFactory().create("inventoryentry");
            if(id!=null && id.length()>0) {
                entry.setId(Integer.valueOf(id));
            }
            entry.setType(Integer.valueOf(type));
            entry.setName(name);
            entry.setDescription(description);
            entry.setImage(image);
            entry.setLink(link);
            entry.setUsername(username);
            environment.getEntityStorageFactory().getStorage("inventoryentry").store(entry);
            if(id==null || id.length()==0) {
                String event = request.getParameter("eventdescription");
                String dateString = request.getParameter("date");
                Date date = dateFormat.parse(dateString);
                InventoryEntryEvent entryEvent = (InventoryEntryEvent)environment.getEntityFactory().create("inventoryentryevent");
                entryEvent.setId(entry.getId());
                entryEvent.setDate(date);
                entryEvent.setSize(Double.valueOf(size));
                entryEvent.setDescription(Integer.valueOf(event));
                environment.getEntityStorageFactory().getStorage("inventoryentryevent").store(entryEvent);
            }
        } catch (ParseException e) {
            e.printStackTrace();  //To change body of catch statement use Options | File Templates.
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
