package erland.webapp.gallery.entity.gallery.picture;

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

public class Picture extends BaseEntity {
    private Integer gallery;
    private Integer id;
    private String title;
    private String titleEnglish;
    private String description;
    private String descriptionEnglish;
    private String file;
    private String image;
    private String link;
    private Boolean official;
    private Boolean officialGuest;
    private Date date;
    private Long orderNo;

    public Integer getGallery() {
        return gallery;
    }

    public void setGallery(Integer gallery) {
        this.gallery = gallery;
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

    public String getTitleEnglish() {
        return titleEnglish;
    }

    public void setTitleEnglish(String titleEnglish) {
        this.titleEnglish = titleEnglish;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDescriptionEnglish() {
        return descriptionEnglish;
    }

    public void setDescriptionEnglish(String descriptionEnglish) {
        this.descriptionEnglish = descriptionEnglish;
    }

    public String getFile() {
        return file;
    }

    public void setFile(String file) {
        this.file = file;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public Boolean getOfficial() {
        return official;
    }

    public void setOfficial(Boolean official) {
        this.official = official;
    }

    public Boolean getOfficialGuest() {
        return officialGuest;
    }

    public void setOfficialGuest(Boolean officialGuest) {
        this.officialGuest = officialGuest;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Long getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(Long orderNo) {
        this.orderNo = orderNo;
    }

    public String getShortTitle() {
        String title = this.title;
        if(title!=null) {
            int pos = title.lastIndexOf("/");
            int pos2 = title.lastIndexOf("\\");
            if(pos2>pos) {
                pos = pos2;
            }
            if(pos>=0) {
                title = title.substring(pos+1);
            }
            pos = title.lastIndexOf(".");
            if(pos>=0) {
                title = title.substring(0,pos);
            }
        }
        return title;
    }
}
