package erland.webapp.diary.diary;

import erland.webapp.usermgmt.User;
import erland.webapp.diary.account.UserAccount;
import erland.webapp.common.CommandInterface;
import erland.webapp.common.WebAppEnvironmentInterface;

import javax.servlet.http.HttpSession;

public abstract class BaseEntryCommand implements CommandInterface {
    private WebAppEnvironmentInterface environment;
    public void init(WebAppEnvironmentInterface environment) {
        this.environment = environment;
    }

    protected WebAppEnvironmentInterface getEnvironment() {
        return environment;
    }

    protected Integer getDiary(String diary, String username, HttpSession session) {
        if(session!=null) {
            User user = (User) session.getAttribute("user");
            if(user!=null) {
                username = user.getUsername();
            }
        }

        Integer result = null;
        if(diary==null || diary.length()==0) {
            UserAccount template = (UserAccount) environment.getEntityFactory().create("useraccount");
            template.setUsername(username);
            UserAccount account = (UserAccount) environment.getEntityStorageFactory().getStorage("useraccount").load(template);
            if(account!=null) {
                result = account.getDefaultDiary();
            }
        }else {
            result = Integer.valueOf(diary);
        }
        return result;
    }
}
