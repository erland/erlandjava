package erland.webapp.gallery.fb.gallery.category;

import org.apache.struts.validator.ValidatorForm;
import org.apache.struts.action.ActionMapping;

import javax.servlet.http.HttpServletRequest;

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

public class CategoryFB extends ValidatorForm {
    private Boolean official;
    private Boolean forcedOfficial;
    private Boolean officialAlways;
    private Boolean officialVisible;
    private Integer category;
    private Integer gallery;
    private String name;

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
        return officialAlways!=null?officialAlways.toString():null;
    }

    public void setOfficialAlwaysDisplay(String officialAlwaysDisplay) {
        this.officialAlways = Boolean.valueOf(officialAlwaysDisplay);
    }

    public Boolean getForcedOfficial() {
        return forcedOfficial;
    }

    public void setForcedOfficial(Boolean forcedOfficial) {
        this.forcedOfficial = forcedOfficial;
    }

    public String getForcedOfficialDisplay() {
        return forcedOfficial!=null?forcedOfficial.toString():null;
    }

    public void setForcedOfficialDisplay(String forcedOfficialDisplay) {
        this.forcedOfficial = Boolean.valueOf(forcedOfficialDisplay);
    }

    public Boolean getOfficialVisible() {
        return officialVisible;
    }

    public void setOfficialVisible(Boolean officialVisible) {
        this.officialVisible = officialVisible;
    }

    public String getOfficialVisibleDisplay() {
        return officialVisible!=null?officialVisible.toString():null;
    }

    public void setOfficialVisibleDisplay(String officialVisibleDisplay) {
        this.officialVisible = Boolean.valueOf(officialVisibleDisplay);
    }

    public Integer getCategory() {
        return category;
    }

    public void setCategory(Integer category) {
        this.category = category;
    }

    public String getCategoryDisplay() {
        return category!=null?category.toString():null;
    }

    public void setCategoryDisplay(String categoryDisplay) {
        try {
            this.category = Integer.valueOf(categoryDisplay);
        } catch (NumberFormatException e) {
            this.category = null;
        }
    }

    public Integer getGallery() {
        return gallery;
    }

    public void setGallery(Integer gallery) {
        this.gallery = gallery;
    }

    public String getGalleryDisplay() {
        return gallery!=null?gallery.toString():null;
    }

    public void setGalleryDisplay(String galleryDisplay) {
        try {
            this.gallery = Integer.valueOf(galleryDisplay);
        } catch (NumberFormatException e) {
            this.gallery = null;
        }
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void reset(ActionMapping actionMapping, HttpServletRequest httpServletRequest) {
        super.reset(actionMapping, httpServletRequest);
        official = Boolean.FALSE;
        forcedOfficial = Boolean.FALSE;
        officialAlways = Boolean.FALSE;
        officialVisible = Boolean.FALSE;
        category = null;
        gallery = null;
        name = null;
    }
}