package erland.webapp.diary.diary;

import erland.webapp.common.CommandInterface;
import erland.webapp.common.WebAppEnvironmentInterface;
import erland.webapp.usermgmt.User;
import erland.webapp.diary.account.UserAccount;

import javax.servlet.http.HttpServletRequest;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.text.ParseException;
import java.util.Date;

public class ViewEntryCommand extends BaseEntryCommand implements CommandInterface, ViewEntryInterface {
    private DiaryEntryInterface entry;
    private final static DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

    public String execute(HttpServletRequest request) {
        try {
            String dateString = request.getParameter("date");
            Date date = dateFormat.parse(dateString);
            DiaryEntryInterface template = (DiaryEntryInterface)getEnvironment().getEntityFactory().create("diaryentry");
            Integer diary = getDiary(request.getParameter("diary"),request.getParameter("user"),request.getSession());
            if(diary!=null) {
                template.setDiary(diary);
                template.setDate(date);
                entry = (DiaryEntryInterface) getEnvironment().getEntityStorageFactory().getStorage("diaryentry").load(template);
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
