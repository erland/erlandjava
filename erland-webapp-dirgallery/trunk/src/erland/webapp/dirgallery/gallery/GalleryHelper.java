package erland.webapp.dirgallery.gallery;

import erland.webapp.common.WebAppEnvironmentInterface;

import javax.servlet.http.HttpServletRequest;

public class GalleryHelper {
    public static Integer getGalleryId(WebAppEnvironmentInterface environment, HttpServletRequest request) {
        String galleryString = request.getParameter("gallery");
        Integer galleryId = null;
        if (galleryString != null && galleryString.length() > 0) {
            galleryId = Integer.valueOf(galleryString);
        }
        return galleryId;
    }

}