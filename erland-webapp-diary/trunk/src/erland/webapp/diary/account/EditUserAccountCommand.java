package erland.webapp.diary.account;

import erland.webapp.common.CommandInterface;
import erland.webapp.common.WebAppEnvironmentInterface;
import erland.webapp.common.QueryFilter;
import erland.webapp.common.EntityInterface;
import erland.webapp.usermgmt.User;
import erland.webapp.diary.diary.Diary;

import javax.servlet.http.HttpServletRequest;

public class EditUserAccountCommand implements CommandInterface, ViewUserAccountInterface {
    private WebAppEnvironmentInterface environment;
    private UserAccount account;
    private Diary[] diaries;

    public void init(WebAppEnvironmentInterface environment) {
        this.environment = environment;
    }

    public String execute(HttpServletRequest request) {
        User user = (User)request.getSession().getAttribute("user");
        if(user!=null) {
            String username = user.getUsername();
            if(user.hasRole("manager")) {
                username = request.getParameter("username");
                if(username==null || username.length()==0) {
                    username = user.getUsername();
                }
            }
            String welcomeText = request.getParameter("welcometext");
            String description = request.getParameter("description");
            String logo = request.getParameter("logo");
            String officialString = request.getParameter("official");
            Boolean official = Boolean.FALSE;
            if(officialString!=null && officialString.equals("true")) {
                official = Boolean.TRUE;
            }
            String defaultDiaryString = request.getParameter("defaultdiary");
            Integer defaultDiary = null;
            if(defaultDiaryString!=null && defaultDiaryString.length()>0) {
                defaultDiary=Integer.valueOf(defaultDiaryString);
            }
            account = (UserAccount)environment.getEntityFactory().create("useraccount");
            account.setUsername(username);
            account.setWelcomeText(welcomeText);
            account.setDescription(description);
            account.setLogo(logo);
            account.setOfficial(official);
            account.setDefaultDiary(defaultDiary);
            environment.getEntityStorageFactory().getStorage("useraccount").store(account);
        }
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
            QueryFilter filter = new QueryFilter("addforuser");
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
