package erland.webapp.gallery.gallery.picture;

import erland.webapp.common.CommandInterface;
import erland.webapp.common.WebAppEnvironmentInterface;
import erland.webapp.common.QueryFilter;
import erland.webapp.common.EntityInterface;
import erland.webapp.gallery.gallery.ViewGalleryInterface;
import erland.webapp.gallery.gallery.Gallery;
import erland.webapp.gallery.gallery.category.ViewCategoryInterface;
import erland.webapp.gallery.gallery.category.Category;
import erland.webapp.gallery.gallery.picturestorage.PictureStorage;
import erland.webapp.usermgmt.User;
import erland.util.Log;

import javax.servlet.http.HttpServletRequest;
import java.util.Collection;
import java.util.ArrayList;

public class SearchPicturesCommand extends SearchPicturesBaseCommand implements ViewCategoryInterface {
    private Integer categoryId;
    private Category category;

    protected Collection getCategories(HttpServletRequest request) {
        String categoryString = request.getParameter("category");
        if(categoryString!=null && categoryString.length()>0) {
            categoryId = Integer.valueOf(categoryString);
        }
        if(categoryId==null && galleryId!=null) {
            Gallery template = (Gallery) environment.getEntityFactory().create("gallery");
            template.setId(galleryId);
            Gallery entity = (Gallery) environment.getEntityStorageFactory().getStorage("gallery").load(template);
            if(entity!=null && !entity.getTopCategory().equals(new Integer(0))) {
                categoryId = entity.getTopCategory();
            }
        }
        if(categoryId!=null) {
            Category[] categories = getCategoryTree(galleryId,categoryId);
            Collection result = new ArrayList();
            for (int i = 0; i < categories.length; i++) {
                result.add(categories[i].getCategory());
            }
            return result;
        }else {
            return null;
        }
    }

    protected String getAllFilter() {
        return "allforgallery";
    }

    protected String getCategoryTreeFilter() {
        return "allforgalleryandcategorylist";
    }

    public Category getCategory() {
        if(categoryId!=null && category==null && galleryId!=null) {
            Category template = (Category) environment.getEntityFactory().create("category");
            template.setGallery(galleryId);
            template.setCategory(categoryId);
            category = (Category) environment.getEntityStorageFactory().getStorage("category").load(template);
        }
        return category;
    }

}
