package erland.webapp.diary.diary;

import erland.webapp.common.CommandInterface;
import erland.webapp.common.WebAppEnvironmentInterface;
import erland.webapp.usermgmt.User;
import erland.webapp.diary.account.UserAccount;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.text.ParseException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

public class EditEntryCommand extends BaseEntryCommand implements CommandInterface, ViewEntryInterface {
    private DiaryEntryInterface entry;
    private final static DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

    public String execute(HttpServletRequest request) {
        try {
            Integer diary = getDiary(request.getParameter("diary"),null,request.getSession(true));
            if(diary!=null) {
                String dateString = request.getParameter("date");
                Date date = dateFormat.parse(dateString);
                String title = request.getParameter("title");
                String description = request.getParameter("description");
                entry = (DiaryEntryInterface)getEnvironment().getEntityFactory().create("diaryentry");
                entry.setDiary(diary);
                entry.setDate(date);
                entry.setTitle(title);
                entry.setDescription(description);
                getEnvironment().getEntityStorageFactory().getStorage("diaryentry").store(entry);
            }
        } catch (ParseException e) {
            e.printStackTrace();  //To change body of catch statement use Options | File Templates.
        }
        return null;
    }

    public DiaryEntryInterface getEntry() {
        return entry;
    }
}
