package erland.webapp.gallery.gallery;

import erland.webapp.common.CommandInterface;
import erland.webapp.common.WebAppEnvironmentInterface;
import erland.webapp.common.QueryFilter;
import erland.webapp.usermgmt.User;

import javax.servlet.http.HttpServletRequest;

public class RemoveGalleryCommand implements CommandInterface {
    private WebAppEnvironmentInterface environment;

    public void init(WebAppEnvironmentInterface environment) {
        this.environment = environment;
    }

    public String execute(HttpServletRequest request) {
        String id = request.getParameter("id");
        if(id!=null && id.length()>0) {
            Gallery gallery = (Gallery) environment.getEntityFactory().create("gallery");
            User user = (User) request.getSession().getAttribute("user");
            String username = user.getUsername();
            gallery.setId(Integer.valueOf(id));
            gallery = (Gallery) environment.getEntityStorageFactory().getStorage("gallery").load(gallery);
            if(gallery.getUsername().equals(username)) {
                environment.getEntityStorageFactory().getStorage("gallery").delete(gallery);
                QueryFilter filter = new QueryFilter("allforgallery");
                filter.setAttribute("gallery",gallery.getId());
                environment.getEntityStorageFactory().getStorage("picture").delete(filter);
                environment.getEntityStorageFactory().getStorage("categorypictureassociation").delete(filter);
                environment.getEntityStorageFactory().getStorage("categorymembership").delete(filter);
                environment.getEntityStorageFactory().getStorage("category").delete(filter);
                environment.getEntityStorageFactory().getStorage("gallerycategoryassociation").delete(filter);
            }
        }
        return null;
    }
}
