package erland.webapp.diary.purchase;

import erland.webapp.common.CommandInterface;
import erland.webapp.common.WebAppEnvironmentInterface;

import javax.servlet.http.HttpServletRequest;

public class RemovePurchaseEntryCommand implements CommandInterface {
    private WebAppEnvironmentInterface environment;

    public void init(WebAppEnvironmentInterface environment) {
        this.environment = environment;
    }

    public String execute(HttpServletRequest request) {
        String id = request.getParameter("id");
        PurchaseEntry entry = (PurchaseEntry)environment.getEntityFactory().create("purchaseentry");
        if(id!=null) {
            entry.setId(Integer.valueOf(id));
            environment.getEntityStorageFactory().getStorage("purchaseentry").delete(entry);
        }
        return null;
    }
}
