package erland.webapp.diary.account;

import erland.webapp.common.CommandInterface;
import erland.webapp.common.WebAppEnvironmentInterface;
import erland.webapp.common.QueryFilter;
import erland.webapp.common.EntityInterface;
import erland.webapp.usermgmt.User;
import erland.webapp.diary.diary.ViewDiariesInterface;
import erland.webapp.diary.diary.Diary;

import javax.servlet.http.HttpServletRequest;

public class ViewUserAccountCommand implements CommandInterface, ViewUserAccountInterface {
    private WebAppEnvironmentInterface environment;
    private UserAccount account;
    private Diary[] diaries;

    public void init(WebAppEnvironmentInterface environment) {
        this.environment = environment;
    }

    public String execute(HttpServletRequest request) {
        User user = (User)request.getSession().getAttribute("user");
        String username = request.getParameter("user");
        if(user!=null) {
            username = user.getUsername();
            if(user.hasRole("manager")) {
                username = request.getParameter("user");
                if(username==null || username.length()==0) {
                    username = user.getUsername();
                }
            }
        }
        UserAccount template = (UserAccount)environment.getEntityFactory().create("useraccount");
        template.setUsername(username);
        account = (UserAccount) environment.getEntityStorageFactory().getStorage("useraccount").load(template);
        return null;
    }

    public UserAccount getAccount() {
        return account;
    }

    public User getUser() {
        User template = (User) environment.getEntityFactory().create("userinfo");
        template.setUsername(account.getUsername());
        User user = (User) environment.getEntityStorageFactory().getStorage("userinfo").load(template);
        return user;
    }

    public Diary[] getDiaries() {
        if(diaries==null) {
            QueryFilter filter = new QueryFilter("allforuser");
            filter.setAttribute("username",account.getUsername());
            EntityInterface[] entities = environment.getEntityStorageFactory().getStorage("diary").search(filter);
            diaries = new Diary[entities.length];
            for (int i = 0; i < entities.length; i++) {
                diaries[i] = (Diary) entities[i];
            }
        }
        return diaries;
    }
}
