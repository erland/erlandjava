package erland.webapp.diary.diary;

import erland.webapp.common.BaseEntity;

import java.util.Date;

public class DiaryEntry extends BaseEntity {
    private Integer diary;
    private Date date;
    private String title;
    private String description;

    public Integer getDiary() {
        return diary;
    }

    public void setDiary(Integer diary) {
        this.diary = diary;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
