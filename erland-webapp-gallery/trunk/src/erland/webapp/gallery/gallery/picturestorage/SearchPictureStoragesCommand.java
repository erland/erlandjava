package erland.webapp.gallery.gallery.picturestorage;

import erland.webapp.common.CommandInterface;
import erland.webapp.common.WebAppEnvironmentInterface;
import erland.webapp.common.QueryFilter;
import erland.webapp.common.EntityInterface;
import erland.webapp.usermgmt.User;

import javax.servlet.http.HttpServletRequest;

public class SearchPictureStoragesCommand implements CommandInterface, ViewPictureStoragesInterface {
    private WebAppEnvironmentInterface environment;
    private PictureStorage[] storages;

    public void init(WebAppEnvironmentInterface environment) {
        this.environment = environment;
    }

    public String execute(HttpServletRequest request) {
        String username = request.getParameter("user");
        if(username==null || username.length()==0) {
            User user = (User) request.getSession().getAttribute("user");
            username = user.getUsername();
        }
        QueryFilter filter = new QueryFilter("allforuser");
        filter.setAttribute("username",username);
        EntityInterface[] entities = environment.getEntityStorageFactory().getStorage("picturestorage").search(filter);
        storages = new PictureStorage[entities.length];
        for (int i = 0; i < entities.length; i++) {
            storages[i]= (PictureStorage) entities[i];
        }
        return null;
    }

    public PictureStorage[] getStorages() {
        return storages;
    }
}