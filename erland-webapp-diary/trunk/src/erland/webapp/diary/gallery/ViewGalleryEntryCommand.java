package erland.webapp.diary.gallery;

import erland.webapp.common.CommandInterface;
import erland.webapp.common.WebAppEnvironmentInterface;

import javax.servlet.http.HttpServletRequest;

public class ViewGalleryEntryCommand implements CommandInterface, ViewGalleryEntryInterface {
    private GalleryEntry entry;
    private WebAppEnvironmentInterface environment;

    public void init(WebAppEnvironmentInterface environment) {
        this.environment = environment;
    }

    public String execute(HttpServletRequest request) {
        String gallery = request.getParameter("gallery");
        String id = request.getParameter("id");
        GalleryEntry template = (GalleryEntry)environment.getEntityFactory().create("galleryentry");
        if(gallery!=null && gallery.length()>0 &&
                id!=null && id.length()>0) {

            template.setGallery(Integer.valueOf(gallery));
            template.setId(Integer.valueOf(id));
            entry = (GalleryEntry) environment.getEntityStorageFactory().getStorage("galleryentry").load(template);
        }
        return null;
    }

    public GalleryEntry getEntry() {
        return entry;
    }
}