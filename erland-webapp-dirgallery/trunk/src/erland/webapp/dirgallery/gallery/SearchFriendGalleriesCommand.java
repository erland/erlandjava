package erland.webapp.dirgallery.gallery;

import erland.webapp.common.CommandInterface;
import erland.webapp.common.EntityInterface;
import erland.webapp.common.QueryFilter;
import erland.webapp.common.WebAppEnvironmentInterface;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Collection;

public class SearchFriendGalleriesCommand implements CommandInterface, ViewGalleriesInterface {
    private WebAppEnvironmentInterface environment;
    private GalleryInterface[] galleries;

    public void init(WebAppEnvironmentInterface environment) {
        this.environment = environment;
    }

    public String execute(HttpServletRequest request) {
        String galleryString = request.getParameter("gallery");
        Integer gallery = null;
        if (galleryString != null && galleryString.length() > 0) {
            gallery = Integer.valueOf(galleryString);
        }
        if (gallery != null) {
            QueryFilter filter = new QueryFilter("allforgallery");
            filter.setAttribute("gallery", gallery);
            EntityInterface[] entities = environment.getEntityStorageFactory().getStorage("friendgallery").search(filter);
            if (entities.length > 0) {
                Collection requestedGalleries = new ArrayList();
                for (int i = 0; i < entities.length; i++) {
                    requestedGalleries.add(((FriendGallery) entities[i]).getFriendGallery());
                }
                filter = new QueryFilter(getGalleryListQuery());
                filter.setAttribute("galleries", requestedGalleries);
                entities = environment.getEntityStorageFactory().getStorage("gallery").search(filter);
                galleries = new GalleryInterface[entities.length];
                for (int i = 0; i < entities.length; i++) {
                    galleries[i] = (GalleryInterface) entities[i];
                }
            } else {
                galleries = new GalleryInterface[0];
            }
        }
        return null;
    }

    protected String getGalleryListQuery() {
        return "allforgallerylist";
    }

    public GalleryInterface[] getGalleries() {
        return galleries;
    }
}
