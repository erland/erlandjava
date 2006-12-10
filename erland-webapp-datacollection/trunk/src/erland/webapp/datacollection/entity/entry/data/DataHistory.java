package erland.webapp.datacollection.entity.entry.data;

import erland.webapp.common.BaseEntity;

public class DataHistory extends BaseEntity {
    private Integer historyId;
    private Integer entryHistoryId;
    private Integer entryId;
    private Integer id;
    private String type;
    private String content;
    private String url;

    public Integer getHistoryId() {
        return historyId;
    }

    public void setHistoryId(Integer historyId) {
        this.historyId = historyId;
    }

    public Integer getEntryHistoryId() {
        return entryHistoryId;
    }

    public void setEntryHistoryId(Integer entryHistoryId) {
        this.entryHistoryId = entryHistoryId;
    }

    public Integer getEntryId() {
        return entryId;
    }

    public void setEntryId(Integer entryId) {
        this.entryId = entryId;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
