package erland.webapp.diary.diary;

import java.util.Date;

public interface ViewEntriesInterface {
    DiaryEntryInterface[] getEntries();
    DiaryEntryInterface getEntry(Date date);
}
