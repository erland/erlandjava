package erland.webapp.gallery.gallery.category;

import erland.webapp.common.CommandInterface;
import erland.webapp.common.WebAppEnvironmentInterface;
import erland.webapp.common.QueryFilter;
import erland.webapp.common.EntityInterface;
import erland.webapp.gallery.gallery.Gallery;

import javax.servlet.http.HttpServletRequest;

public class SearchCategoriesCommand implements CommandInterface, ViewCategoriesInterface {
    private WebAppEnvironmentInterface environment;
    private Category[] categories;
    public void init(WebAppEnvironmentInterface environment) {
        this.environment = environment;
    }

    public String execute(HttpServletRequest request) {
        Integer gallery = null;
        Integer category = null;
        String galleryString = request.getParameter("gallery");
        if(galleryString!=null&&galleryString.length()>0) {
            gallery = Integer.valueOf(galleryString);
        }
        String categoryString = request.getParameter("category");
        if(categoryString!=null&&categoryString.length()>0) {
            category = Integer.valueOf(categoryString);
        }
        if(gallery!=null) {
            if(category==null) {
                Gallery template = (Gallery) environment.getEntityFactory().create("gallery");
                template.setId(gallery);
                Gallery entity = (Gallery) environment.getEntityStorageFactory().getStorage("gallery").load(template);
                if(entity!=null && !entity.getTopCategory().equals(new Integer(0))) {
                    category = entity.getTopCategory();
                }
            }
            QueryFilter filter = null;
            if(category!=null) {
                filter = new QueryFilter(getParentFilter());
                filter.setAttribute("parent",category);
            }else {
                filter = new QueryFilter(getNoParentFilter());
            }
            filter.setAttribute("gallery",gallery);
            EntityInterface[] entities = environment.getEntityStorageFactory().getStorage("category").search(filter);
            categories = new Category[entities.length];
            for (int i = 0; i < entities.length; i++) {
                categories[i] = (Category) entities[i];
            }
        }
        return null;
    }

    protected String getNoParentFilter() {
        return "allforgallerywithoutparent";
    }

    protected String getParentFilter() {
        return "allforgallerywithparent";
    }

    public Category[] getCategories() {
        return categories;
    }
}