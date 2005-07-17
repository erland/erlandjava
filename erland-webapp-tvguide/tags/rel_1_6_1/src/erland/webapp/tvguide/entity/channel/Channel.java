package erland.webapp.tvguide.entity.channel;

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

import erland.webapp.common.BaseEntity;

import java.util.Date;

public class Channel extends BaseEntity {
    private Integer id;
    private String tag;
    private String name;
    private String description;
    private String logo;
    private String link;
    private Boolean reviewAvailable;
    private String reviewCategories;
    private String noReviewCategories;
    private Integer service;
    private String serviceParameters;
    private Date cacheDate;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
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

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public Integer getService() {
        return service;
    }

    public void setService(Integer service) {
        this.service = service;
    }

    public String getServiceParameters() {
        return serviceParameters;
    }

    public void setServiceParameters(String serviceParameters) {
        this.serviceParameters = serviceParameters;
    }

    public Date getCacheDate() {
        return cacheDate;
    }

    public void setCacheDate(Date cacheDate) {
        this.cacheDate = cacheDate;
    }

    public Boolean getReviewAvailable() {
        return reviewAvailable;
    }

    public void setReviewAvailable(Boolean reviewAvailable) {
        this.reviewAvailable = reviewAvailable;
    }

    public String getReviewCategories() {
        return reviewCategories;
    }

    public void setReviewCategories(String reviewCategories) {
        this.reviewCategories = reviewCategories;
    }

    public String getNoReviewCategories() {
        return noReviewCategories;
    }

    public void setNoReviewCategories(String noReviewCategories) {
        this.noReviewCategories = noReviewCategories;
    }
}