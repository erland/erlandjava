package erland.webapp.diary.gallery;

import erland.webapp.common.CommandInterface;
import erland.webapp.common.WebAppEnvironmentInterface;
import erland.webapp.common.QueryFilter;
import erland.webapp.common.EntityInterface;

import javax.servlet.http.HttpServletRequest;

public class SearchGalleryEntriesCommand implements CommandInterface, ViewGalleryEntriesInterface, ViewGalleryInterface {
    private WebAppEnvironmentInterface environment;
    private GalleryEntry[] entries;
    private Integer galleryId;
    private Gallery gallery;

    public void init(WebAppEnvironmentInterface environment) {
        this.environment = environment;
    }

    public String execute(HttpServletRequest request) {
        String galleryString = request.getParameter("gallery");
        if(galleryString!=null && galleryString.length()>0) {
            galleryId = Integer.valueOf(galleryString);
            QueryFilter filter = new QueryFilter("allforgallery");
            filter.setAttribute("gallery",galleryId);
            EntityInterface[] entities = environment.getEntityStorageFactory().getStorage("galleryentry").search(filter);
            entries = new GalleryEntry[entities.length];
            for (int i = 0; i < entities.length; i++) {
                entries[i] = (GalleryEntry) entities[i];
            }
        }
        return null;
    }

    public GalleryEntry[] getEntries() {
        return entries;
    }

    public Gallery getGallery() {
        if(galleryId!=null && gallery==null) {
            Gallery template = (Gallery) environment.getEntityFactory().create("gallery");
            template.setId(galleryId);
            gallery = (Gallery) environment.getEntityStorageFactory().getStorage("gallery").load(template);
        }
        return gallery;
    }
}
