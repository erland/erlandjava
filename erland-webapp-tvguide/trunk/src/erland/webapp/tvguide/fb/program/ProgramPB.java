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
import java.text.DateFormat;
import java.text.SimpleDateFormat;

public class ProgramPB extends BasePB {
    private static DateFormat TIME_FORMAT = new SimpleDateFormat("HH:mm");
    private static DateFormat DATE_FORMAT = new SimpleDateFormat("EEEE d MMM");
    private Date start;
    private Date stop;
    private String name;
    private String description;
    private String channelLogo;
    private String channelName;
    private String channelLink;
    private Boolean started;
    private Boolean startOtherDay;
    private String newSubscriptionLink;
    private String viewSubscriptionLink;

    public Date getStart() {
        return start;
    }

    public void setStart(Date start) {
        this.start = start;
    }

    public Date getStop() {
        return stop;
    }

    public void setStop(Date stop) {
        this.stop = stop;
    }

    public String getStartTimeDisplay() {
        return TIME_FORMAT.format(start);
    }

    public String getStartDateDisplay() {
        return DATE_FORMAT.format(start);
    }

    public void setStartTimeDisplay(String startTimeDisplay) {
        //No implementation needed
    }

    public void setStartDateDisplay(String startTimeDisplay) {
        //No implementation needed
    }

    public String getStopTimeDisplay() {
        return TIME_FORMAT.format(stop);
    }

    public String getStopDateDisplay() {
        return DATE_FORMAT.format(stop);
    }
    public void setStopTimeDisplay(String stopTimeDisplay) {
        //No implementation needed
    }

    public void setStopDateDisplay(String stopTimeDisplay) {
        //No implementation needed
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }


    public String getChannelLogo() {
        return channelLogo;
    }

    public void setChannelLogo(String channelLogo) {
        this.channelLogo = channelLogo;
    }

    public String getChannelName() {
        return channelName;
    }

    public void setChannelName(String channelName) {
        this.channelName = channelName;
    }

    public String getChannelLink() {
        return channelLink;
    }

    public void setChannelLink(String channelLink) {
        this.channelLink = channelLink;
    }

    public Boolean getStarted() {
        return started;
    }

    public void setStarted(Boolean started) {
        this.started = started;
    }
    public String getStartedDisplay() {
        return StringUtil.asString(started,"false");
    }

    public void setStartedDisplay(String startedDisplay) {
        this.started = StringUtil.asBoolean(startedDisplay,Boolean.FALSE);
    }

    public Boolean getStartOtherDay() {
        return startOtherDay;
    }

    public void setStartSameDay(Boolean startOtherDay) {
        this.startOtherDay = startOtherDay;
    }

    public String getStartSameDayDisplay() {
        return StringUtil.asString(startOtherDay,"true");
    }

    public void setStartSameDayDisplay(String startSameDayDisplay) {
        this.startOtherDay = StringUtil.asBoolean(startSameDayDisplay,Boolean.TRUE);
    }

    public String getNewSubscriptionLink() {
        return newSubscriptionLink;
    }

    public void setNewSubscriptionLink(String newSubscriptionLink) {
        this.newSubscriptionLink = newSubscriptionLink;
    }

    public String getViewSubscriptionLink() {
        return viewSubscriptionLink;
    }

    public void setViewSubscriptionLink(String viewSubscriptionLink) {
        this.viewSubscriptionLink = viewSubscriptionLink;
    }
}
