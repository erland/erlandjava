package erland.webapp.gallery.gallery.category;

import erland.webapp.common.CommandInterface;
import erland.webapp.common.WebAppEnvironmentInterface;

import javax.servlet.http.HttpServletRequest;

public class ViewCategoryCommand implements CommandInterface, ViewCategoryInterface {
    private WebAppEnvironmentInterface environment;
    private Category category;

    public void init(WebAppEnvironmentInterface environment) {
        this.environment = environment;
    }

    public String execute(HttpServletRequest request) {
        String galleryString = request.getParameter("gallery");
        Integer gallery = null;
        if(galleryString!=null && galleryString.length()>0) {
            gallery = Integer.valueOf(galleryString);
        }
        String categoryString = request.getParameter("category");
        Integer categoryId = null;
        if(categoryString!=null && categoryString.length()>0) {
            categoryId = Integer.valueOf(categoryString);
        }
        if(gallery!=null && categoryId!=null) {
            Category template = (Category) environment.getEntityFactory().create("category");
            template.setGallery(gallery);
            template.setCategory(categoryId);
            category = (Category) environment.getEntityStorageFactory().getStorage("category").load(template);
        }
        return null;
    }

    public Category getCategory() {
        return category;
    }
}