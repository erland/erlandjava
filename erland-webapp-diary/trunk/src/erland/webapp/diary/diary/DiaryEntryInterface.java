package erland.webapp.diary.diary;

import erland.webapp.common.EntityInterface;

import java.util.Date;

public interface DiaryEntryInterface extends EntityInterface {
    public void setDiary(Integer diary);
    public Integer getDiary();
    public void setDate(Date date);
    public Date getDate();
    public void setTitle(String title);
    public String getTitle();
    public void setDescription(String description);
    public String getDescription();
}
