package erland.webapp.gallery.fb.gallery;

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

import erland.webapp.common.fb.BaseFB;
import erland.util.StringUtil;
import org.apache.struts.action.ActionMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;

public class QuickSetupFB extends BaseFB {
    private String galleryDisplay;
    private String publishCategoriesDisplay;
    private String officialCategoryDisplay;
    private String officialGuestCategoryDisplay;
    private String[] hiddenCategoriesDisplay;
    private String topCategoryDisplay;

    public String getGalleryDisplay() {
        return galleryDisplay;
    }

    public void setGalleryDisplay(String galleryDisplay) {
        this.galleryDisplay = galleryDisplay;
    }

    public Integer getGallery() {
        return StringUtil.asInteger(galleryDisplay,null);
    }

    public void setGallery(Integer id) {
        this.galleryDisplay = StringUtil.asString(id,null);
    }

    public String getPublishCategoriesDisplay() {
        return publishCategoriesDisplay;
    }

    public void setPublishCategoriesDisplay(String publishCategoriesDisplay) {
        this.publishCategoriesDisplay = publishCategoriesDisplay;
    }

    public Boolean getPublishCategories() {
        return StringUtil.asBoolean(publishCategoriesDisplay,Boolean.FALSE);
    }

    public void setPublishCategories(Boolean publishCategories) {
        this.publishCategoriesDisplay = StringUtil.asString(publishCategories,null);
    }

    public String getOfficialCategoryDisplay() {
        return officialCategoryDisplay;
    }

    public void setOfficialCategoryDisplay(String officialCategoryDisplay) {
        this.officialCategoryDisplay = officialCategoryDisplay;
    }

    public Integer getOfficialCategory() {
        return StringUtil.asInteger(officialCategoryDisplay,null);
    }

    public void setOfficialCategory(Integer officialCategory) {
        this.officialCategoryDisplay = StringUtil.asString(officialCategory,null);
    }

    public String getOfficialGuestCategoryDisplay() {
        return officialGuestCategoryDisplay;
    }

    public void setOfficialGuestCategoryDisplay(String officialGuestCategoryDisplay) {
        this.officialGuestCategoryDisplay = officialGuestCategoryDisplay;
    }

    public Integer getOfficialGuestCategory() {
        return StringUtil.asInteger(officialGuestCategoryDisplay,null);
    }

    public void setOfficialGuestCategory(Integer officialGuestCategory) {
        this.officialGuestCategoryDisplay = StringUtil.asString(officialGuestCategory,null);
    }

    public String[] getHiddenCategoriesDisplay() {
        return hiddenCategoriesDisplay;
    }

    public void setHiddenCategoriesDisplay(String[] hiddenCategoriesDisplay) {
        this.hiddenCategoriesDisplay = hiddenCategoriesDisplay;
    }

    public Integer[] getHiddenCategories() {
        if(hiddenCategoriesDisplay!=null) {
            ArrayList result = new ArrayList();
            for (int i = 0; i < hiddenCategoriesDisplay.length; i++) {
                Integer cat = StringUtil.asInteger(hiddenCategoriesDisplay[i],null);
                if(cat!=null) {
                    result.add(cat);
                }
            }
            return (Integer[]) result.toArray(new Integer[0]);
        }else {
            return null;
        }
    }

    public void setHiddenCategories(Integer[] hiddenCategories) {
        if(hiddenCategories!=null) {
            ArrayList result = new ArrayList();
            for (int i = 0; i < hiddenCategories.length; i++) {
                String value = StringUtil.asString(hiddenCategories[i],null);
                if(value!=null) {
                    result.add(value);
                }
            }
            this.hiddenCategoriesDisplay = (String[]) result.toArray(new String[0]);
        }else {
            this.hiddenCategoriesDisplay = null;
        }
    }

    public String getTopCategoryDisplay() {
        return topCategoryDisplay;
    }

    public void setTopCategoryDisplay(String topCategoryDisplay) {
        this.topCategoryDisplay = topCategoryDisplay;
    }

    public Integer getTopCategory() {
        return StringUtil.asInteger(topCategoryDisplay,null);
    }

    public void setTopCategory(Integer topCategory) {
        this.topCategoryDisplay = StringUtil.asString(topCategory,null);
    }

    public void reset(ActionMapping actionMapping, HttpServletRequest httpServletRequest) {
        super.reset(actionMapping, httpServletRequest);
        galleryDisplay = null;
        publishCategoriesDisplay = null;
        officialCategoryDisplay = null;
        officialGuestCategoryDisplay = null;
        hiddenCategoriesDisplay = null;
        topCategoryDisplay = null;
    }
}