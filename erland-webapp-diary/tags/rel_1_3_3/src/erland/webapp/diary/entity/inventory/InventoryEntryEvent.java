package erland.webapp.diary.entity.inventory;

/*
 * Copyright (C) 2003 Erland Isaksson (erland_i@hotmail.com)
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 2
 * of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA  02111-1307, USA.
 *
 */

import erland.webapp.common.BaseEntity;

import java.util.Date;

public class InventoryEntryEvent extends BaseEntity {
    private Integer id;
    private Integer eventId;
    private Integer description;
    private Integer container;
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

    public Integer getContainer() {
        return container;
    }

    public void setContainer(Integer container) {
        this.container = container;
    }

    public boolean isSizeRelevant() {
        if (description != null) {
            switch (description.intValue()) {
                case 1: //Ink�pt
                case 2: //D�dad
                case 3: //D�d
                case 4: //S�ld
                case 7: //Uppm�tt
                case 9: //Flyttad
                    return true;
                case 5: //Lekt
                case 6: //Yngel
                case 8: //F�dd
                default:
                    return false;
            }
        }
        return false;
    }

    public boolean isActive() {
        if (description != null) {
            switch (description.intValue()) {
                case 1: //Ink�pt
                case 5: //Lekt
                case 6: //Yngel
                case 7: //Uppm�tt
                case 8: //F�dd
                case 9: //Flyttad
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
