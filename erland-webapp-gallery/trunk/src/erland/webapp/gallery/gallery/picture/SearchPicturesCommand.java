package erland.webapp.gallery.gallery.picture;

import erland.webapp.common.QueryFilter;
import erland.webapp.common.EntityInterface;
import erland.webapp.gallery.gallery.*;
import erland.webapp.gallery.gallery.category.ViewCategoryInterface;
import erland.webapp.gallery.gallery.category.Category;

import javax.servlet.http.HttpServletRequest;
import java.util.Collection;
import java.util.ArrayList;

public class SearchPicturesCommand extends SearchPicturesBaseCommand implements ViewCategoryInterface {
    private Integer categoryId;
    private Category category;
    private Integer virtualGalleryId;

    protected Collection getCategories(HttpServletRequest request) {
        String categoryString = request.getParameter("category");
        if(categoryString!=null && categoryString.length()>0) {
            categoryId = Integer.valueOf(categoryString);
        }
        if(categoryId==null && getGalleryId()!=null) {
            Gallery template = (Gallery) getEnvironment().getEntityFactory().create("gallery");
            template.setId(getGalleryId());
            GalleryInterface entity = (GalleryInterface) getEnvironment().getEntityStorageFactory().getStorage("gallery").load(template);
            if(entity!=null && !entity.getTopCategory().equals(new Integer(0))) {
                categoryId = entity.getTopCategory();
            }
        }
        if(categoryId!=null) {
            Category[] categories = getCategoryTree(getGalleryId(),categoryId);
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
        if(categoryId!=null && category==null && getGalleryId()!=null) {
            Category template = (Category) getEnvironment().getEntityFactory().create("category");
            template.setGallery(getGalleryId());
            template.setCategory(categoryId);
            category = (Category) getEnvironment().getEntityStorageFactory().getStorage("category").load(template);
        }
        return category;
    }

    protected Integer getGalleryId(HttpServletRequest request) {
        String galleryString = request.getParameter("gallery");
        virtualGalleryId = null;
        if(galleryString!=null && galleryString.length()>0) {
            virtualGalleryId = Integer.valueOf(galleryString);
        }
        return GalleryHelper.getGalleryId(getEnvironment(),request);
    }

    protected Collection getPictures(HttpServletRequest request) {
        if(!virtualGalleryId.equals(getGalleryId())) {
            QueryFilter categoryFilter = new QueryFilter("allforgallery");
            categoryFilter.setAttribute("gallery",virtualGalleryId);
            EntityInterface[] entities = getEnvironment().getEntityStorageFactory().getStorage("gallerycategoryassociation").search(categoryFilter);
            if(entities.length==0) {
                return null;
            }
            Collection categories = new ArrayList();
            for (int i = 0; i < entities.length; i++) {
                GalleryCategoryAssociation entity = (GalleryCategoryAssociation) entities[i];
                categories.add(entity.getCategory());
            }
            QueryFilter pictureFilter = new QueryFilter("allforgalleryandcategorylistallrequired");
            pictureFilter.setAttribute("gallery",getGalleryId());
            pictureFilter.setAttribute("categories",categories);
            pictureFilter.setAttribute("numberofcategories",new Integer(categories.size()));
            entities = getEnvironment().getEntityStorageFactory().getStorage("picture").search(pictureFilter);
            Collection pictures = new ArrayList();
            for (int i = 0; i < entities.length; i++) {
                Picture entity = (Picture) entities[i];
                pictures.add(entity.getId());
            }
            return pictures;
        }
        return null;
    }
}
