package erland.webapp.gallery.gallery.picturestorage;

import erland.webapp.common.CommandInterface;
import erland.webapp.common.WebAppEnvironmentInterface;
import erland.webapp.usermgmt.User;

import javax.servlet.http.HttpServletRequest;

public class EditPictureStorageCommand implements CommandInterface, ViewPictureStorageInterface {
    private WebAppEnvironmentInterface environment;
    private PictureStorage storage;

    public void init(WebAppEnvironmentInterface environment) {
        this.environment = environment;
    }

    public String execute(HttpServletRequest request) {
        String idString = request.getParameter("id");
        String name = request.getParameter("name");
        String path = request.getParameter("path");
        User user = (User) request.getSession().getAttribute("user");
        String username = user.getUsername();
        storage = (PictureStorage) environment.getEntityFactory().create("picturestorage");
        if(idString!=null && idString.length()>0) {
            storage.setId(Integer.valueOf(idString));
        }
        storage.setName(name);
        storage.setPath(path);
        storage.setUsername(username);
        environment.getEntityStorageFactory().getStorage("picturestorage").store(storage);
        return null;
    }

    public PictureStorage getStorage() {
        return storage;
    }
}