package erland.webapp.gallery.gallery.picture;

import erland.webapp.common.CommandInterface;
import erland.webapp.common.WebAppEnvironmentInterface;
import erland.webapp.common.QueryFilter;
import erland.webapp.gallery.gallery.picture.Picture;

import javax.servlet.http.HttpServletRequest;

public class RemovePictureCommand implements CommandInterface {
    private WebAppEnvironmentInterface environment;

    public void init(WebAppEnvironmentInterface environment) {
        this.environment = environment;
    }

    public String execute(HttpServletRequest request) {
        String gallery = request.getParameter("gallery");
        String id = request.getParameter("id");
        Picture template = (Picture)environment.getEntityFactory().create("picture");
        if(gallery!=null && gallery.length()>0 &&
                id!=null && id.length()>0) {

            template.setGallery(Integer.valueOf(gallery));
            template.setId(Integer.valueOf(id));
            environment.getEntityStorageFactory().getStorage("picture").delete(template);
            QueryFilter filter = new QueryFilter("allforgalleryandpicture");
            filter.setAttribute("gallery",template.getGallery());
            filter.setAttribute("picture",template.getId());
            environment.getEntityStorageFactory().getStorage("categorypictureassociation").delete(filter);
        }
        return null;
    }
}
