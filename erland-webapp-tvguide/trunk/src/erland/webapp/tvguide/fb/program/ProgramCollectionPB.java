package erland.webapp.tvguide.fb.program;

/*
 * Copyright (C) 2005 Erland Isaksson (erland_i@hotmail.com)
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

import erland.webapp.common.fb.BasePB;
import erland.util.StringUtil;

import java.util.Date;
import java.text.SimpleDateFormat;
import java.text.DateFormat;

public class ProgramCollectionPB extends BasePB {
    private static final DateFormat FORMAT = new SimpleDateFormat("EEEE d MMM");
    private String currentLink;
    private String currentDateDisplay;
    private String nextLink;
    private String nextDateDisplay;
    private String prevLink;
    private String prevDateDisplay;
    private String title;
    private ProgramPB[] programs;

    public String getCurrentLink() {
        return currentLink;
    }

    public void setCurrentLink(String currentLink) {
        this.currentLink = currentLink;
    }

    public String getCurrentDateDisplay() {
        return currentDateDisplay;
    }

    public void setCurrentDateDisplay(String currentDateDisplay) {
        this.currentDateDisplay = currentDateDisplay;
    }

    public String getNextLink() {
        return nextLink;
    }

    public void setNextLink(String nextLink) {
        this.nextLink = nextLink;
    }

    public String getNextDateDisplay() {
        return nextDateDisplay;
    }

    public void setNextDateDisplay(String nextDateDisplay) {
        this.nextDateDisplay = nextDateDisplay;
    }

    public String getPrevLink() {
        return prevLink;
    }

    public void setPrevLink(String prevLink) {
        this.prevLink = prevLink;
    }

    public String getPrevDateDisplay() {
        return prevDateDisplay;
    }

    public void setPrevDateDisplay(String prevDateDisplay) {
        this.prevDateDisplay = prevDateDisplay;
    }

    public ProgramPB[] getPrograms() {
        return programs;
    }

    public void setPrograms(ProgramPB[] programs) {
        this.programs = programs;
    }

    public Date getCurrentDate() {
        return StringUtil.asDate(currentDateDisplay,null);
    }

    public void setCurrentDate(Date currentDate) {
        this.currentDateDisplay = StringUtil.asString(currentDate,null,FORMAT);
    }

    public Date getNextDate() {
        return StringUtil.asDate(nextDateDisplay,null);
    }

    public void setNextDate(Date nextDate) {
        this.nextDateDisplay = StringUtil.asString(nextDate,null,FORMAT);
    }

    public Date getPrevDate() {
        return StringUtil.asDate(prevDateDisplay,null);
    }

    public void setPrevDate(Date prevDate) {
        this.prevDateDisplay = StringUtil.asString(prevDate,null,FORMAT);
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
