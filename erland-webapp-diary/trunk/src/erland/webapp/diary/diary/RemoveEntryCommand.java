package erland.webapp.diary.diary;

import erland.webapp.common.CommandInterface;
import erland.webapp.common.WebAppEnvironmentInterface;
import erland.webapp.usermgmt.User;

import javax.servlet.http.HttpServletRequest;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.text.ParseException;
import java.util.Date;

public class RemoveEntryCommand extends BaseEntryCommand implements CommandInterface {
    private final static DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

    public String execute(HttpServletRequest request) {
        try {
            Integer diary = getDiary(request.getParameter("diary"),null,request.getSession(true));
            if(diary!=null) {
                String dateString = request.getParameter("date");
                Date date = dateFormat.parse(dateString);
                DiaryEntryInterface entry = (DiaryEntryInterface)getEnvironment().getEntityFactory().create("diaryentry");
                entry.setDiary(diary);
                entry.setDate(date);
                getEnvironment().getEntityStorageFactory().getStorage("diaryentry").delete(entry);
            }
        } catch (ParseException e) {
            e.printStackTrace();  //To change body of catch statement use Options | File Templates.
        }
        return null;
    }
}
