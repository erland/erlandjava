package erland.webapp.diary.diary;

import erland.webapp.common.CommandInterface;
import erland.webapp.common.WebAppEnvironmentInterface;
import erland.webapp.usermgmt.User;

import javax.servlet.http.HttpServletRequest;

public class EditDiaryCommand implements CommandInterface, ViewDiaryInterface {
    private WebAppEnvironmentInterface environment;
    private Diary diary;

    public void init(WebAppEnvironmentInterface environment) {
        this.environment = environment;
    }

    public String execute(HttpServletRequest request) {
        String id = request.getParameter("id");
        String title = request.getParameter("title");
        String description = request.getParameter("description");
        String officialString = request.getParameter("official");
        diary = (Diary) environment.getEntityFactory().create("diary");
        User user = (User) request.getSession().getAttribute("user");
        String username = user.getUsername();
        Boolean official = Boolean.FALSE;
        if(officialString!=null && officialString.equals("true")) {
            official = Boolean.TRUE;
        }
        if(id!=null && id.length()>0) {
            diary.setId(Integer.valueOf(id));
        }
        diary.setUsername(username);
        diary.setTitle(title);
        diary.setDescription(description);
        diary.setOfficial(official);
        environment.getEntityStorageFactory().getStorage("diary").store(diary);
        return null;
    }

    public Diary getDiary() {
        return diary;
    }
}
