package erland.webapp.diary.inventory;

import erland.webapp.common.BaseEntity;

import java.util.Date;

public class InventoryEntryEvent extends BaseEntity {
    private Integer id;
    private Integer eventId;
    private Integer description;
    private Double size;
    private Date date;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getEventId() {
        return eventId;
    }

    public void setEventId(Integer eventId) {
        this.eventId = eventId;
    }

    public Integer getDescription() {
        return description;
    }

    public void setDescription(Integer description) {
        this.description = description;
    }

    public Double getSize() {
        return size;
    }

    public void setSize(Double size) {
        this.size = size;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public boolean isSizeRelevant() {
        if(description!=null) {
            switch(description.intValue()) {
                case 1: //Inköpt
                case 2: //Dödad
                case 3: //Död
                case 4: //Såld
                case 7: //Uppmätt
                    return true;
                case 5: //Lekt
                case 6: //Yngel
                default:
                    return false;
            }
        }
        return false;
    }
    public boolean isActive() {
        if(description!=null) {
            switch(description.intValue()) {
                case 1: //Inköpt
                case 5: //Lekt
                case 6: //Yngel
                case 7: //Uppmätt
                    return true;
                case 2: //Dödad
                case 3: //Död
                case 4: //Såld
                default:
                    return false;
            }
        }
        return false;
    }
}
