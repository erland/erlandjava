package erland.webapp.diary.purchase;

import erland.webapp.common.CommandInterface;
import erland.webapp.common.WebAppEnvironmentInterface;
import erland.webapp.diary.purchase.ViewPurchaseEntryInterface;

import javax.servlet.http.HttpServletRequest;

public class ViewPurchaseEntryCommand implements CommandInterface, ViewPurchaseEntryInterface {
    private WebAppEnvironmentInterface environment;
    private PurchaseEntry entry;

    public void init(WebAppEnvironmentInterface environment) {
        this.environment = environment;
    }

    public String execute(HttpServletRequest request) {
        String id = request.getParameter("id");
        PurchaseEntry template = (PurchaseEntry)environment.getEntityFactory().create("purchaseentry");
        if(id!=null) {
            template.setId(Integer.valueOf(id));
            entry = (PurchaseEntry) environment.getEntityStorageFactory().getStorage("purchaseentry").load(template);
        }
        return null;
    }

    public PurchaseEntry getEntry() {
        return entry;
    }
}
