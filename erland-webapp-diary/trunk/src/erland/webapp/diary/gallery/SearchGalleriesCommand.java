package erland.webapp.diary.gallery;

import erland.webapp.common.CommandInterface;
import erland.webapp.common.WebAppEnvironmentInterface;
import erland.webapp.common.QueryFilter;
import erland.webapp.common.EntityInterface;
import erland.webapp.usermgmt.User;

import javax.servlet.http.HttpServletRequest;

public class SearchGalleriesCommand implements CommandInterface, ViewGalleriesInterface {
    private WebAppEnvironmentInterface environment;
    private Gallery[] galleries;

    public void init(WebAppEnvironmentInterface environment) {
        this.environment = environment;
    }

    public String execute(HttpServletRequest request) {
        String username = request.getParameter("user");
        if(username==null || username.length()==0) {
            User user = (User) request.getSession().getAttribute("user");
            username = user.getUsername();
        }
        QueryFilter filter = new QueryFilter(getQueryFilter());
        filter.setAttribute("username",username);
        EntityInterface[] entities = environment.getEntityStorageFactory().getStorage("diarygallery").search(filter);
        galleries = new Gallery[entities.length];
        for (int i = 0; i < entities.length; i++) {
            galleries[i]= (Gallery) entities[i];
        }
        return null;
    }

    public Gallery[] getGalleries() {
        return galleries;
    }
    protected String getQueryFilter() {
        return "allforuser";
    }
}
