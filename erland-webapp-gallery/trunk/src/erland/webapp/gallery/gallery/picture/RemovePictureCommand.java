package erland.webapp.gallery.gallery.picture;

import erland.webapp.common.CommandInterface;
import erland.webapp.common.WebAppEnvironmentInterface;
import erland.webapp.common.QueryFilter;
import erland.webapp.gallery.gallery.GalleryHelper;

import javax.servlet.http.HttpServletRequest;

public class RemovePictureCommand implements CommandInterface {
    private WebAppEnvironmentInterface environment;

    public void init(WebAppEnvironmentInterface environment) {
        this.environment = environment;
    }

    public String execute(HttpServletRequest request) {
        Integer gallery = getGalleryId(request);
        String id = request.getParameter("id");
        Picture template = (Picture)environment.getEntityFactory().create("picture");
        if(gallery!=null &&
                id!=null && id.length()>0) {

            template.setGallery(gallery);
            template.setId(Integer.valueOf(id));
            environment.getEntityStorageFactory().getStorage("picture").delete(template);
            QueryFilter filter = new QueryFilter("allforgalleryandpicture");
            filter.setAttribute("gallery",template.getGallery());
            filter.setAttribute("picture",template.getId());
            environment.getEntityStorageFactory().getStorage("categorypictureassociation").delete(filter);
        }
        return null;
    }

    protected Integer getGalleryId(HttpServletRequest request) {
        return GalleryHelper.getGalleryId(environment,request);
    }

    public WebAppEnvironmentInterface getEnvironment() {
        return environment;
    }
}
