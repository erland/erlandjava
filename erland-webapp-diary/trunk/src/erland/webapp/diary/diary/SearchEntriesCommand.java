package erland.webapp.diary.diary;

import erland.webapp.common.CommandInterface;
import erland.webapp.common.EntityInterface;
import erland.webapp.common.QueryFilter;
import erland.webapp.diary.diary.ViewEntriesInterface;
import erland.webapp.diary.diary.ViewEntryInterface;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.Calendar;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.text.ParseException;

public class SearchEntriesCommand extends BaseEntryCommand implements CommandInterface, ViewEntryInterface, ViewEntriesInterface, ViewDiaryInterface {
    private DiaryEntryInterface[] entries;
    private final static DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
    private Date date;
    private Integer diaryId;
    private Diary diary;

    public String execute(HttpServletRequest request) {
        QueryFilter filter = new QueryFilter("allfordiary");
        diaryId = getDiary(request.getParameter("diary"),request.getParameter("user"),request.getSession());
        if(diaryId!=null) {
            filter.setAttribute("diary",diaryId);
            String date = request.getParameter("date");
            if(date!=null && date.length()>0) {
                try {
                    this.date = dateFormat.parse(date);
                } catch (ParseException e) {
                    e.printStackTrace();  //To change body of catch statement use Options | File Templates.
                }
            }
            EntityInterface[] entities = getEnvironment().getEntityStorageFactory().getStorage("diaryentry").search(filter);
            entries = new DiaryEntryInterface[entities.length];
            for(int i=0;i<entities.length;i++) {
                entries[i] = (DiaryEntryInterface)entities[i];
            }
            if(date!=null && date.length()>0) {
                return "withdate";
            }else {
                if(request.getParameter("diary")!=null) {
                    return "withoutdatewithdiary";
                }else {
                    return "withoutdatewithoutdiary";
                }
            }
        }else {
            return "withoutdatewithoutdiary";
        }
    }

    public DiaryEntryInterface[] getEntries() {
        return entries;
    }

    public DiaryEntryInterface getEntry() {
        if(date!=null) {
            return getEntry(date);
        }else {
            return null;
        }
    }

    public DiaryEntryInterface getEntry(Date date) {
        for (int i = 0; i < entries.length; i++) {
            DiaryEntryInterface entry = entries[i];
            Calendar cal = Calendar.getInstance();
            cal.setTime(date);
            cal.set(Calendar.HOUR_OF_DAY,0);
            cal.set(Calendar.MINUTE,0);
            cal.set(Calendar.SECOND,0);
            cal.set(Calendar.MILLISECOND,0);
            if(entry.getDate().equals(cal.getTime())) {
                return entry;
            }
        }
        return null;
    }

    public Diary getDiary() {
        if(diary==null) {
            Diary template = (Diary) getEnvironment().getEntityFactory().create("diary");
            template.setId(diaryId);
            diary = (Diary) getEnvironment().getEntityStorageFactory().getStorage("diary").load(template);
        }
        return diary;
    }
}
