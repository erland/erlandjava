package erland.webapp.diary.purchase;

import erland.webapp.common.CommandInterface;
import erland.webapp.common.WebAppEnvironmentInterface;
import erland.webapp.common.EntityInterface;
import erland.webapp.common.QueryFilter;
import erland.webapp.usermgmt.User;
import erland.webapp.diary.purchase.ViewPurchaseEntriesInterface;

import javax.servlet.http.HttpServletRequest;

public class SearchPurchaseEntriesCommand implements CommandInterface, ViewPurchaseEntriesInterface{
    private WebAppEnvironmentInterface environment;
    private PurchaseEntry[] entries;

    public void init(WebAppEnvironmentInterface environment) {
        this.environment = environment;
    }

    public String execute(HttpServletRequest request) {
        QueryFilter filter = new QueryFilter("allforuser");
        User user = (User) request.getSession().getAttribute("user");
        String username = user.getUsername();
        filter.setAttribute("username",username);
        EntityInterface[] entities = environment.getEntityStorageFactory().getStorage("purchaseentry").search(filter);
        entries = new PurchaseEntry[entities.length];
        for(int i=0;i<entities.length;i++) {
            entries[i] = (PurchaseEntry)entities[i];
        }
        return null;
    }

    public PurchaseEntry[] getEntries() {
        return entries;
    }
}
