package erland.webapp.diary.gallery;

import erland.webapp.common.CommandInterface;
import erland.webapp.common.WebAppEnvironmentInterface;
import erland.webapp.usermgmt.User;

import javax.servlet.http.HttpServletRequest;

public class EditGalleryCommand implements CommandInterface, ViewGalleryInterface {
    private WebAppEnvironmentInterface environment;
    private Gallery gallery;

    public void init(WebAppEnvironmentInterface environment) {
        this.environment = environment;
    }

    public String execute(HttpServletRequest request) {
        String id = request.getParameter("id");
        String title = request.getParameter("title");
        String description = request.getParameter("description");
        String officialString = request.getParameter("official");
        gallery = (Gallery) environment.getEntityFactory().create("gallery");
        User user = (User) request.getSession().getAttribute("user");
        String username = user.getUsername();
        Boolean official = Boolean.FALSE;
        if(officialString!=null && officialString.equals("true")) {
            official = Boolean.TRUE;
        }
        if(id!=null && id.length()>0) {
            gallery.setId(Integer.valueOf(id));
        }
        gallery.setUsername(username);
        gallery.setTitle(title);
        gallery.setDescription(description);
        gallery.setOfficial(official);
        environment.getEntityStorageFactory().getStorage("gallery").store(gallery);
        return null;
    }

    public Gallery getGallery() {
        return gallery;
    }
}
