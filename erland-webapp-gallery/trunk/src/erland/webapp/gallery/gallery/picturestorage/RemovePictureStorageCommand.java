package erland.webapp.gallery.gallery.picturestorage;

import erland.webapp.common.CommandInterface;
import erland.webapp.common.WebAppEnvironmentInterface;
import erland.webapp.usermgmt.User;

import javax.servlet.http.HttpServletRequest;

public class RemovePictureStorageCommand implements CommandInterface {
    private WebAppEnvironmentInterface environment;

    public void init(WebAppEnvironmentInterface environment) {
        this.environment = environment;
    }

    public String execute(HttpServletRequest request) {
        String id = request.getParameter("id");
        if(id!=null && id.length()>0) {
            PictureStorage storage = (PictureStorage) environment.getEntityFactory().create("picturestorage");
            User user = (User) request.getSession().getAttribute("user");
            String username = user.getUsername();
            storage.setId(Integer.valueOf(id));
            storage = (PictureStorage) environment.getEntityStorageFactory().getStorage("picturestorage").load(storage);
            if(storage.getUsername().equals(username)) {
                environment.getEntityStorageFactory().getStorage("picturestorage").delete(storage);
            }
        }
        return null;
    }
}