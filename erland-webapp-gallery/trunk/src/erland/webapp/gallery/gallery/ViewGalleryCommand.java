package erland.webapp.gallery.gallery;

import erland.webapp.common.CommandInterface;
import erland.webapp.common.WebAppEnvironmentInterface;
import erland.webapp.common.EntityInterface;
import erland.webapp.common.QueryFilter;
import erland.webapp.usermgmt.User;
import erland.webapp.gallery.gallery.category.ViewCategoriesInterface;
import erland.webapp.gallery.gallery.category.Category;

import javax.servlet.http.HttpServletRequest;

public class ViewGalleryCommand implements CommandInterface, ViewGalleryInterface, ViewCategoriesInterface {
    private WebAppEnvironmentInterface environment;
    private Gallery gallery;
    private Category[] categories;

    public void init(WebAppEnvironmentInterface environment) {
        this.environment = environment;
    }

    public String execute(HttpServletRequest request) {
        String id = request.getParameter("id");
        if(id!=null && id.length()>0) {
            Gallery template = (Gallery) environment.getEntityFactory().create("gallery");
            User user = (User) request.getSession().getAttribute("user");
            String username = user.getUsername();
            template.setId(Integer.valueOf(id));
            gallery = (Gallery) environment.getEntityStorageFactory().getStorage("gallery").load(template);
            if(gallery!=null && !gallery.getUsername().equals(username)) {
                gallery=null;
            }
        }
        return null;
    }

    public Gallery getGallery() {
        return gallery;
    }

    public Category[] getCategories() {
        if(gallery!=null && categories==null) {
            QueryFilter filter = new QueryFilter("allforgallery");
            filter.setAttribute("gallery",gallery.getId());
            EntityInterface[] entities = environment.getEntityStorageFactory().getStorage("category").search(filter);
            categories = new Category[entities.length];
            for (int i = 0; i < entities.length; i++) {
                categories[i] = (Category) entities[i];
            }
        }
        return categories;
    }
}
