package erland.webapp.issuetracking.entity.issue;

import erland.webapp.common.BaseEntity;

import java.util.Date;

/*
 * Copyright (C) 2004 Erland Isaksson (erland_i@hotmail.com)
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

public class IssueEvent extends BaseEntity {
    private Integer issueId;
    private Integer eventId;
    private Integer prevState;
    private Integer state;
    private Date date;
    private String username;
    private String description;

    public final static Integer STATE_UNINITIALIZED = new Integer(0);
    public final static Integer STATE_NEW = new Integer(1);
    public final static Integer STATE_ANALYZING = new Integer(2);
    public final static Integer STATE_ANALYZED = new Integer(3);
    public final static Integer STATE_CORRECTING = new Integer(4);
    public final static Integer STATE_CORRECTED = new Integer(5);
    public final static Integer STATE_TESTING = new Integer(6);
    public final static Integer STATE_TESTED = new Integer(7);
    public final static Integer STATE_DELIVERED = new Integer(8);
    public final static Integer STATE_REJECTED = new Integer(9);
    public final static Integer STATE_CORRECTED_FAIL = new Integer(10);
    public final static Integer STATE_ANALYZED_FAIL = new Integer(11);
    public final static Integer STATE_TESTED_FAIL = new Integer(12);
    public final static Integer STATE_WAITING = new Integer(13);

    public Integer getIssueId() {
        return issueId;
    }

    public void setIssueId(Integer issueId) {
        this.issueId = issueId;
    }

    public Integer getEventId() {
        return eventId;
    }

    public void setEventId(Integer eventId) {
        this.eventId = eventId;
    }

    public Integer getPrevState() {
        return prevState;
    }

    public void setPrevState(Integer prevState) {
        this.prevState = prevState;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}