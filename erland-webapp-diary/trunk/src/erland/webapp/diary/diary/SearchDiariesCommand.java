package erland.webapp.diary.diary;

import erland.webapp.common.CommandInterface;
import erland.webapp.common.WebAppEnvironmentInterface;
import erland.webapp.common.QueryFilter;
import erland.webapp.common.EntityInterface;
import erland.webapp.usermgmt.User;

import javax.servlet.http.HttpServletRequest;

public class SearchDiariesCommand implements CommandInterface, ViewDiariesInterface {
    private WebAppEnvironmentInterface environment;
    private Diary[] diaries;

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
        EntityInterface[] entities = environment.getEntityStorageFactory().getStorage("diary").search(filter);
        diaries = new Diary[entities.length];
        for (int i = 0; i < entities.length; i++) {
            diaries[i]= (Diary) entities[i];
        }
        return null;
    }

    public Diary[] getDiaries() {
        return diaries;
    }
    protected String getQueryFilter() {
        return "allforuser";
    }
}
