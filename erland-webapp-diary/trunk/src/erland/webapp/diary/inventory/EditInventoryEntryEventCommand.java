package erland.webapp.diary.inventory;

import erland.webapp.common.CommandInterface;
import erland.webapp.common.WebAppEnvironmentInterface;
import erland.webapp.usermgmt.User;
import erland.util.Log;

import javax.servlet.http.HttpServletRequest;
import java.text.*;
import java.util.Date;

public class EditInventoryEntryEventCommand implements CommandInterface, ViewInventoryEntryEventInterface {
    private InventoryEntryEvent entry;
    private final static DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
    private WebAppEnvironmentInterface environment;

    public void init(WebAppEnvironmentInterface environment) {
        this.environment = environment;
    }

    public String execute(HttpServletRequest request) {
        try {
            String id = request.getParameter("id");
            String eventId = request.getParameter("eventid");
            String description = request.getParameter("type");
            String size = request.getParameter("size");
            String dateString = request.getParameter("date");
            Date date = dateFormat.parse(dateString);
            entry = (InventoryEntryEvent)environment.getEntityFactory().create("inventoryentryevent");
            if(eventId!=null && eventId.length()>0) {
                entry.setEventId(Integer.valueOf(eventId));
            }
            entry.setId(Integer.valueOf(id));
            entry.setDate(date);
            entry.setSize(Double.valueOf(size));
            entry.setDescription(Integer.valueOf(description));
            environment.getEntityStorageFactory().getStorage("inventoryentryevent").store(entry);
        } catch (ParseException e) {
            e.printStackTrace();  //To change body of catch statement use Options | File Templates.
        }
        return null;
    }

    public InventoryEntryEvent getEntry() {
        return entry;
    }
}
