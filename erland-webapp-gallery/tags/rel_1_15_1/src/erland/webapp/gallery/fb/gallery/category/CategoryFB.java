package erland.webapp.gallery.fb.gallery.category;

import org.apache.struts.action.ActionMapping;

import javax.servlet.http.HttpServletRequest;

import erland.webapp.common.fb.BaseFB;
import erland.webapp.common.ServletParameterHelper;
import erland.util.StringUtil;

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

public class CategoryFB extends BaseFB {
    private Boolean official;
    private Boolean officialGuest;
    private Boolean officialAlways;
    private Boolean officialVisible;
    private Integer category;
    private Integer gallery;
    private String name;
    private String nameEnglish;

    public Boolean getOfficial() {
        return official;
    }

    public void setOfficial(Boolean official) {
        this.official = official;
    }

    public String getOfficialDisplay() {
        return official!=null?official.toString():null;
    }

    public void setOfficialDisplay(String officialDisplay) {
        this.official = Boolean.valueOf(officialDisplay);
    }

    public Boolean getOfficialAlways() {
        return officialAlways;
    }

    public void setOfficialAlways(Boolean officialAlways) {
        this.officialAlways = officialAlways;
    }

    public String getOfficialAlwaysDisplay() {
        return StringUtil.asString(officialAlways,null);
    }

    public void setOfficialAlwaysDisplay(String officialAlwaysDisplay) {
        this.officialAlways = StringUtil.asBoolean(officialAlwaysDisplay,Boolean.FALSE);
    }

    public Boolean getOfficialGuest() {
        return officialGuest;
    }

    public void setOfficialGuest(Boolean officialGuest) {
        this.officialGuest = officialGuest;
    }

    public String getOfficialGuestDisplay() {
        return StringUtil.asString(officialGuest,null);
    }

    public void setOfficialGuestDisplay(String officialGuestDisplay) {
        this.officialGuest = StringUtil.asBoolean(officialGuestDisplay,Boolean.FALSE);
    }

    public Boolean getOfficialVisible() {
        return officialVisible;
    }

    public void setOfficialVisible(Boolean officialVisible) {
        this.officialVisible = officialVisible;
    }

    public String getOfficialVisibleDisplay() {
        return StringUtil.asString(officialVisible,null);
    }

    public void setOfficialVisibleDisplay(String officialVisibleDisplay) {
        this.officialVisible = StringUtil.asBoolean(officialVisibleDisplay,Boolean.FALSE);
    }

    public Integer getCategory() {
        return category;
    }

    public void setCategory(Integer category) {
        this.category = category;
    }

    public String getCategoryDisplay() {
        return StringUtil.asString(category,null);
    }

    public void setCategoryDisplay(String categoryDisplay) {
        this.category = StringUtil.asInteger(categoryDisplay,null);
    }

    public Integer getGallery() {
        return gallery;
    }

    public void setGallery(Integer gallery) {
        this.gallery = gallery;
    }

    public String getGalleryDisplay() {
        return StringUtil.asString(gallery,null);
    }

    public void setGalleryDisplay(String galleryDisplay) {
        this.gallery = StringUtil.asInteger(galleryDisplay,null);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNameEnglish() {
        return nameEnglish;
    }

    public void setNameEnglish(String nameEnglish) {
        this.nameEnglish = nameEnglish;
    }

    public void reset(ActionMapping actionMapping, HttpServletRequest httpServletRequest) {
        super.reset(actionMapping, httpServletRequest);
        official = Boolean.FALSE;
        officialGuest = Boolean.FALSE;
        officialAlways = Boolean.FALSE;
        officialVisible = Boolean.FALSE;
        category = null;
        gallery = null;
        name = null;
        nameEnglish = null;
    }
}