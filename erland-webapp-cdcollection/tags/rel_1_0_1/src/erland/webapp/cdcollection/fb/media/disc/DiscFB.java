package erland.webapp.cdcollection.fb.media.disc;

/*
 * Copyright (C) 2003-2004 Erland Isaksson (erland_i@hotmail.com)
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

import erland.util.StringUtil;
import erland.webapp.common.fb.BaseFB;
import org.apache.struts.action.ActionMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;

public class DiscFB extends BaseFB {
    private String mediaIdDisplay;
    private String idDisplay;
    private String uniqueDiscId;
    private String title;
    private String artist;
    private String yearDisplay;
    private String trackArtistPattern;
    private String trackTitlePattern;

    public String getMediaIdDisplay() {
        return mediaIdDisplay;
    }

    public void setMediaIdDisplay(String mediaIdDisplay) {
        this.mediaIdDisplay = mediaIdDisplay;
    }

    public Integer getMediaId() {
        return StringUtil.asInteger(mediaIdDisplay,null);
    }

    public void setMediaId(Integer mediaId) {
        this.mediaIdDisplay = StringUtil.asString(mediaId,null);
    }

    public String getIdDisplay() {
        return idDisplay;
    }

    public void setIdDisplay(String idDisplay) {
        this.idDisplay = idDisplay;
    }

    public Integer getId() {
        return StringUtil.asInteger(idDisplay,null);
    }

    public void setId(Integer id) {
        this.idDisplay = StringUtil.asString(id,null);
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUniqueDiscId() {
        return uniqueDiscId;
    }

    public void setUniqueDiscId(String uniqueDiscId) {
        this.uniqueDiscId = uniqueDiscId;
    }

    public String getArtist() {
        return artist;
    }

    public void setArtist(String artist) {
        this.artist = artist;
    }

    public String getYearDisplay() {
        return yearDisplay;
    }

    public void setYearDisplay(String yearDisplay) {
        this.yearDisplay = yearDisplay;
    }

    public Integer getYear() {
        return StringUtil.asInteger(yearDisplay,null);
    }

    public void setYear(Integer year) {
        this.yearDisplay = StringUtil.asString(year,null);
    }

    public String getTrackArtistPattern() {
        return trackArtistPattern;
    }

    public void setTrackArtistPattern(String trackArtistPattern) {
        this.trackArtistPattern = trackArtistPattern;
    }

    public String getTrackTitlePattern() {
        return trackTitlePattern;
    }

    public void setTrackTitlePattern(String trackTitlePattern) {
        this.trackTitlePattern = trackTitlePattern;
    }

    public void reset(ActionMapping actionMapping, HttpServletRequest httpServletRequest) {
        super.reset(actionMapping, httpServletRequest);
        mediaIdDisplay = null;
        idDisplay = null;
        uniqueDiscId = null;
        title = null;
        artist = null;
        yearDisplay = null;
        trackTitlePattern = null;
        trackArtistPattern = null;
    }
}