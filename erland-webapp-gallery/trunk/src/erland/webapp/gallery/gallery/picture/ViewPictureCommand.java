package erland.webapp.gallery.gallery.picture;

import erland.webapp.common.CommandInterface;
import erland.webapp.common.WebAppEnvironmentInterface;
import erland.webapp.gallery.gallery.GalleryHelper;

import javax.servlet.http.HttpServletRequest;

public class ViewPictureCommand implements CommandInterface, ViewPictureInterface {
    private Picture picture;
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

            Integer virtualGalleryId = Integer.valueOf(request.getParameter("gallery"));
            template.setGallery(gallery);
            template.setId(Integer.valueOf(id));
            picture = (Picture) environment.getEntityStorageFactory().getStorage("picture").load(template);
            if(picture!=null) {
                picture.setGallery(virtualGalleryId);
            }
        }
        return null;
    }

    protected Integer getGalleryId(HttpServletRequest request) {
        return GalleryHelper.getGalleryId(environment,request);
    }

    public Picture getPicture() {
        return picture;
    }

    public WebAppEnvironmentInterface getEnvironment() {
        return environment;
    }
}
