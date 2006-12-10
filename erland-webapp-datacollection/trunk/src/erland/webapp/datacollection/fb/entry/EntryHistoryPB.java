package erland.webapp.datacollection.fb.entry;

import erland.webapp.datacollection.fb.entry.data.DataPB;
import erland.util.StringUtil;

public class EntryHistoryPB extends EntryFB {
    private String historyIdDisplay;
    private String historyLink;

    public String getHistoryIdDisplay() {
        return historyIdDisplay;
    }

    public void setHistoryIdDisplay(String historyIdDisplay) {
        this.historyIdDisplay = historyIdDisplay;
    }

    public Integer getHistoryId() {
        return StringUtil.asInteger(historyIdDisplay,null);
    }

    public void setHistoryId(Integer historyId) {
        this.historyIdDisplay = StringUtil.asString(historyId,null);
    }

    public String getHistoryLink() {
        return historyLink;
    }

    public void setHistoryLink(String historyLink) {
        this.historyLink = historyLink;
    }
}
