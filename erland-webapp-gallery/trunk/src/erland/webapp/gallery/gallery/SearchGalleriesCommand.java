package erland.webapp.gallery.gallery;

import erland.webapp.common.CommandInterface;
import erland.webapp.common.WebAppEnvironmentInterface;
import erland.webapp.common.QueryFilter;
import erland.webapp.common.EntityInterface;
import erland.webapp.usermgmt.User;
import erland.util.Log;

import javax.servlet.http.HttpServletRequest;

public class SearchGalleriesCommand implements CommandInterface, ViewGalleriesInterface {
    private WebAppEnvironmentInterface environment;
    private GalleryInterface[] galleries;

    public void init(WebAppEnvironmentInterface environment) {
        this.environment = environment;
    }

    public String execute(HttpServletRequest request) {
        initCommand(request);
        String username = request.getParameter("user");
        if(username==null || username.length()==0) {
            User user = (User) request.getSession().getAttribute("user");
            username = user.getUsername();
        }
        QueryFilter filter = new QueryFilter(getQueryFilter());
        filter.setAttribute("username",username);
        EntityInterface[] entities = environment.getEntityStorageFactory().getStorage(getEntityName()).search(filter);
        galleries = new GalleryInterface[entities.length];
        for (int i = 0; i < entities.length; i++) {
            galleries[i]= (GalleryInterface) entities[i];
        }
        return null;
    }

    protected String getEntityName() {
        return "gallery";
    }

    public GalleryInterface[] getGalleries() {
        return galleries;
    }
    protected String getQueryFilter() {
        return "allforuser";
    }
    protected void initCommand(HttpServletRequest request) {
        // Do nothing
    }

    public WebAppEnvironmentInterface getEnvironment() {
        return environment;
    }
}
