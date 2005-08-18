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

public class DiscTrackFB extends BaseFB {
    private String mediaIdDisplay;
    private String discIdDisplay;
    private String idDisplay;
    private String artist;
    private String title;
    private String lengthDisplay;
    private String trackNoDisplay;


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

    public String getDiscIdDisplay() {
        return discIdDisplay;
    }

    public void setDiscIdDisplay(String discIdDisplay) {
        this.discIdDisplay = discIdDisplay;
    }

    public Integer getDiscId() {
        return StringUtil.asInteger(discIdDisplay,null);
    }

    public void setDiscId(Integer discId) {
        this.discIdDisplay = StringUtil.asString(discId,null);
    }

    public String getLengthDisplay() {
        return lengthDisplay;
    }

    public void setLengthDisplay(String lengthDisplay) {
        this.lengthDisplay = lengthDisplay;
    }

    public Integer getLength() {
        return StringUtil.asInteger(lengthDisplay,null);
    }

    public void setLength(Integer length) {
        this.lengthDisplay = StringUtil.asString(length,null);
    }

    public String getTrackNoDisplay() {
        return trackNoDisplay;
    }

    public void setTrackNoDisplay(String trackNoDisplay) {
        this.trackNoDisplay = trackNoDisplay;
    }

    public Integer getTrackNo() {
        return StringUtil.asInteger(trackNoDisplay,null);
    }

    public void setTrackNo(Integer trackNo) {
        this.trackNoDisplay = StringUtil.asString(trackNo,null);
    }

    public String getArtist() {
        return artist;
    }

    public void setArtist(String artist) {
        this.artist = artist;
    }

    public String getLengthInMinutes() {
        int length = StringUtil.asInteger(lengthDisplay,new Integer(0)).intValue();
        int minutes = length/60;
        int seconds = length%60;
        return ""+minutes+":"+(seconds<10?"0":"")+seconds;
    }

    public void setLengthInMinutes(String lengthInMinutes) {
        // Not implemented
    }

    public String getMediaIdDisplay() {
        return mediaIdDisplay;
    }

    public void setMediaIdDisplay(String mediaIdDisplay) {
        this.mediaIdDisplay = mediaIdDisplay;
    }

    public void reset(ActionMapping actionMapping, HttpServletRequest httpServletRequest) {
        super.reset(actionMapping, httpServletRequest);
        mediaIdDisplay = null;
        discIdDisplay = null;
        idDisplay = null;
        title = null;
        lengthDisplay = null;
        trackNoDisplay = null;
        artist = null;
    }
}