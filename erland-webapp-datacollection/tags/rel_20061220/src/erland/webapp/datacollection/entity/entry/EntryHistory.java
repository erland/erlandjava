package erland.webapp.datacollection.entity.entry;

import erland.webapp.common.BaseEntity;

import java.util.Date;

public class EntryHistory extends BaseEntity {
    private Integer historyId;
    private Integer id;
    private Integer collection;
    private String uniqueEntryId;
    private String title;
    private String description;
    private Date lastChanged;

    public Integer getHistoryId() {
        return historyId;
    }

    public void setHistoryId(Integer historyId) {
        this.historyId = historyId;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getCollection() {
        return collection;
    }

    public void setCollection(Integer collection) {
        this.collection = collection;
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

    public String getUniqueEntryId() {
        return uniqueEntryId;
    }

    public void setUniqueEntryId(String uniqueEntryId) {
        this.uniqueEntryId = uniqueEntryId;
    }

    public Date getLastChanged() {
        return lastChanged;
    }

    public void setLastChanged(Date lastChanged) {
        this.lastChanged = lastChanged;
    }
}
