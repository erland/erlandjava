package erland.webapp.gallery.gallery.category;

import erland.webapp.common.CommandInterface;
import erland.webapp.common.WebAppEnvironmentInterface;
import erland.webapp.gallery.gallery.GalleryHelper;

import javax.servlet.http.HttpServletRequest;

public class ViewCategoryCommand implements CommandInterface, ViewCategoryInterface {
    private WebAppEnvironmentInterface environment;
    private Category category;

    public void init(WebAppEnvironmentInterface environment) {
        this.environment = environment;
    }

    public String execute(HttpServletRequest request) {
        Integer gallery = getGalleryId(request);
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
            Integer virtualGalleryId = Integer.valueOf(request.getParameter("gallery"));
            if(category!=null) {
                category.setGallery(virtualGalleryId);
            }
        }
        return null;
    }

    protected Integer getGalleryId(HttpServletRequest request) {
        return GalleryHelper.getGalleryId(environment,request);
    }

    public Category getCategory() {
        return category;
    }

    public WebAppEnvironmentInterface getEnvironment() {
        return environment;
    }
}