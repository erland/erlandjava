package erland.webapp.gallery.gallery.picturestorage;

import erland.webapp.common.CommandInterface;
import erland.webapp.common.WebAppEnvironmentInterface;
import erland.webapp.usermgmt.User;

import javax.servlet.http.HttpServletRequest;

public class ViewPictureStorageCommand implements CommandInterface, ViewPictureStorageInterface {
    private WebAppEnvironmentInterface environment;
    private PictureStorage storage;

    public void init(WebAppEnvironmentInterface environment) {
        this.environment = environment;
    }

    public String execute(HttpServletRequest request) {
        String id = request.getParameter("id");
        if(id!=null && id.length()>0) {
            PictureStorage template = (PictureStorage) environment.getEntityFactory().create("picturestorage");
            User user = (User) request.getSession().getAttribute("user");
            String username = user.getUsername();
            template.setId(Integer.valueOf(id));
            storage = (PictureStorage) environment.getEntityStorageFactory().getStorage("picturestorage").load(template);
            if(storage!=null && !storage.getUsername().equals(username)) {
                storage=null;
            }
        }
        return null;
    }

    public PictureStorage getStorage() {
        return storage;
    }
}