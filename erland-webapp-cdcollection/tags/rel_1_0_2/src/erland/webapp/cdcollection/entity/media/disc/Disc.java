package erland.webapp.cdcollection.entity.media.disc;

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

import erland.webapp.common.BaseEntity;

public class Disc extends BaseEntity {
    private Integer mediaId;
    private Integer id;
    private String uniqueDiscId;
    private String title;
    private String artist;
    private Integer year;
    private String trackArtistPattern;
    private String trackTitlePattern;

    public Integer getMediaId() {
        return mediaId;
    }

    public void setMediaId(Integer mediaId) {
        this.mediaId = mediaId;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
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
}