package erland.webapp.gallery.fb.gallery.picture;

import org.apache.struts.action.ActionMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;

import erland.webapp.common.fb.BaseFB;
import erland.webapp.common.ServletParameterHelper;

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

public class PictureFB extends BaseFB {
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

    public String getGalleryDisplay() {
        return ServletParameterHelper.asString(gallery,null);
    }

    public void setGalleryDisplay(String galleryDisplay) {
        this.gallery = ServletParameterHelper.asInteger(galleryDisplay,null);
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getIdDisplay() {
        return ServletParameterHelper.asString(id,null);
    }

    public void setIdDisplay(String idDisplay) {
        this.id = ServletParameterHelper.asInteger(idDisplay,null);
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

    public String getOfficialDisplay() {
        return ServletParameterHelper.asString(official,null);
    }

    public void setOfficialDisplay(String officialDisplay) {
        this.official = ServletParameterHelper.asBoolean(officialDisplay,Boolean.FALSE);
    }

    public Boolean getOfficialGuest() {
        return officialGuest;
    }

    public void setOfficialGuest(Boolean officialGuest) {
        this.officialGuest = officialGuest;
    }

    public String getOfficialGuestDisplay() {
        return ServletParameterHelper.asString(officialGuest,null);
    }

    public void setOfficialGuestDisplay(String officialGuestDisplay) {
        this.officialGuest = ServletParameterHelper.asBoolean(officialGuestDisplay,Boolean.FALSE);
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getDateDisplay() {
        return ServletParameterHelper.asString(date,null);
    }

    public void setDateDisplay(String dateDisplay) {
        this.date = ServletParameterHelper.asDate(dateDisplay,null);
    }

    public Long getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(Long orderNo) {
        this.orderNo = orderNo;
    }

    public String getOrderNoDisplay() {
        return ServletParameterHelper.asString(orderNo,null);
    }

    public void setOrderNoDisplay(String orderNoDisplay) {
        this.orderNo = ServletParameterHelper.asLong(orderNoDisplay,null);
    }

    public void reset(ActionMapping actionMapping, HttpServletRequest httpServletRequest) {
        super.reset(actionMapping, httpServletRequest);
        gallery = null;
        id = null;
        title = null;
        titleEnglish = null;
        description = null;
        descriptionEnglish = null;
        file = null;
        image = null;
        link = null;
        official = Boolean.FALSE;
        officialGuest = Boolean.FALSE;
        date = null;
        orderNo = null;
    }
}