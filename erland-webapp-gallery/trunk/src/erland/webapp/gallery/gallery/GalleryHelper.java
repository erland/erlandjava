package erland.webapp.gallery.gallery;

import erland.webapp.common.WebAppEnvironmentInterface;

import javax.servlet.http.HttpServletRequest;

public class GalleryHelper {
    public static Integer getGalleryId(WebAppEnvironmentInterface environment,HttpServletRequest request) {
        String galleryString = request.getParameter("gallery");
        Integer galleryId = null;
        if(galleryString!=null && galleryString.length()>0) {
            galleryId = Integer.valueOf(galleryString);
        }
        if(galleryId!=null) {
            Gallery template = (Gallery) environment.getEntityFactory().create("gallery");
            template.setId(galleryId);
            Gallery entity = (Gallery) environment.getEntityStorageFactory().getStorage("gallery").load(template);
            if(entity!=null) {
                if(entity.getReferencedGallery()!=null && !entity.getReferencedGallery().equals(new Integer(0))) {
                    return entity.getReferencedGallery();
                }
            }
        }
        return galleryId;
    }

}