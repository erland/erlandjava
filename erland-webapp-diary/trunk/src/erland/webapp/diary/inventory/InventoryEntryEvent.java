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
                case 1: //Ink�pt
                case 2: //D�dad
                case 3: //D�d
                case 4: //S�ld
                case 7: //Uppm�tt
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
                case 1: //Ink�pt
                case 5: //Lekt
                case 6: //Yngel
                case 7: //Uppm�tt
                    return true;
                case 2: //D�dad
                case 3: //D�d
                case 4: //S�ld
                default:
                    return false;
            }
        }
        return false;
    }
}
