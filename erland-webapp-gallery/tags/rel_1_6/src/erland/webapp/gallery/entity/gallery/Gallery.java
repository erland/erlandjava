package erland.webapp.gallery.entity.gallery;

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

public class Gallery extends BaseEntity {
    private Integer id;
    private String username;
    private String title;
    private String description;
    private Integer topCategory;
    private Boolean official;
    private Integer referencedGallery;
    private String defaultResolution;
    private Integer maxWidth;
    private Integer officialCategory;
    private Integer officialGuestCategory;
    private Float thumbnailCompression;
    private Float compression;
    private String skin;
    private Boolean antialias;
    private Boolean thumbnailAntialias;
    private Date cacheDate;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Boolean getOfficial() {
        return official;
    }

    public void setOfficial(Boolean official) {
        this.official = official;
    }

    public Integer getTopCategory() {
        return topCategory;
    }

    public void setTopCategory(Integer topCategory) {
        this.topCategory = topCategory;
    }

    public Integer getReferencedGallery() {
        return referencedGallery;
    }

    public void setReferencedGallery(Integer referencedGallery) {
        this.referencedGallery = referencedGallery;
    }

    public String getDefaultResolution() {
        return defaultResolution;
    }

    public void setDefaultResolution(String defaultResolution) {
        this.defaultResolution = defaultResolution;
    }

    public Integer getMaxWidth() {
        return maxWidth;
    }

    public void setMaxWidth(Integer maxWidth) {
        this.maxWidth = maxWidth;
    }

    public Integer getOfficialCategory() {
        return officialCategory;
    }

    public void setOfficialCategory(Integer officialCategory) {
        this.officialCategory = officialCategory;
    }

    public Integer getOfficialGuestCategory() {
        return officialGuestCategory;
    }

    public void setOfficialGuestCategory(Integer officialGuestCategory) {
        this.officialGuestCategory = officialGuestCategory;
    }

    public Float getThumbnailCompression() {
        return thumbnailCompression;
    }

    public void setThumbnailCompression(Float thumbnailCompression) {
        this.thumbnailCompression = thumbnailCompression;
    }

    public Float getCompression() {
        return compression;
    }

    public void setCompression(Float compression) {
        this.compression = compression;
    }

    public String getSkin() {
        return skin;
    }

    public void setSkin(String skin) {
        this.skin = skin;
    }

    public Boolean getAntialias() {
        return antialias;
    }

    public void setAntialias(Boolean antialias) {
        this.antialias = antialias;
    }

    public Boolean getThumbnailAntialias() {
        return thumbnailAntialias;
    }

    public void setThumbnailAntialias(Boolean thumbnailAntialias) {
        this.thumbnailAntialias = thumbnailAntialias;
    }

    public Date getCacheDate() {
        return cacheDate;
    }

    public void setCacheDate(Date cacheDate) {
        this.cacheDate = cacheDate;
    }
}
