package erland.webapp.diary.gallery;

import erland.webapp.common.CommandInterface;
import erland.webapp.common.WebAppEnvironmentInterface;
import erland.webapp.usermgmt.User;

import javax.servlet.http.HttpServletRequest;

public class ViewGalleryCommand implements CommandInterface, ViewGalleryInterface {
    private WebAppEnvironmentInterface environment;
    private Gallery gallery;

    public void init(WebAppEnvironmentInterface environment) {
        this.environment = environment;
    }

    public String execute(HttpServletRequest request) {
        String id = request.getParameter("id");
        if(id!=null && id.length()>0) {
            Gallery template = (Gallery) environment.getEntityFactory().create("gallery");
            User user = (User) request.getSession().getAttribute("user");
            String username = user.getUsername();
            template.setId(Integer.valueOf(id));
            gallery = (Gallery) environment.getEntityStorageFactory().getStorage("gallery").load(template);
            if(gallery!=null && !gallery.getUsername().equals(username)) {
                gallery=null;
            }
        }
        return null;
    }

    public Gallery getGallery() {
        return gallery;
    }
}
