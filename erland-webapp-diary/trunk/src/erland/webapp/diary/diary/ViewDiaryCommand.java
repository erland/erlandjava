package erland.webapp.diary.diary;

import erland.webapp.common.CommandInterface;
import erland.webapp.common.WebAppEnvironmentInterface;
import erland.webapp.usermgmt.User;

import javax.servlet.http.HttpServletRequest;

public class ViewDiaryCommand implements CommandInterface, ViewDiaryInterface {
    private WebAppEnvironmentInterface environment;
    private Diary diary;

    public void init(WebAppEnvironmentInterface environment) {
        this.environment = environment;
    }

    public String execute(HttpServletRequest request) {
        String id = request.getParameter("id");
        if(id!=null && id.length()>0) {
            Diary template = (Diary) environment.getEntityFactory().create("diary");
            User user = (User) request.getSession().getAttribute("user");
            String username = user.getUsername();
            template.setId(Integer.valueOf(id));
            diary = (Diary) environment.getEntityStorageFactory().getStorage("diary").load(template);
            if(diary!=null && !diary.getUsername().equals(username)) {
                diary=null;
            }
        }
        return null;
    }

    public Diary getDiary() {
        return diary;
    }
}
