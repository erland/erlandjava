package erland.webapp.gallery.fb.loader;

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

public class ThumbnailImageFB extends ImageFB {
    private Boolean useCache;
    private Float compression;
    private Integer width;

    public Boolean getUseCache() {
        return useCache;
    }

    public void setUseCache(Boolean useCache) {
        this.useCache = useCache;
    }

    public String getUseCacheDisplay() {
        return useCache!=null?useCache.toString():null;
    }

    public void setUseCacheDisplay(String useCacheDisplay) {
        this.useCache = Boolean.valueOf(useCacheDisplay);
    }

    public Float getCompression() {
        return compression;
    }

    public void setCompression(Float compression) {
        this.compression = compression;
    }

    public String getCompressionDisplay() {
        return compression!=null?compression.toString():null;
    }

    public void setCompressionDisplay(String compressionDisplay) {
        try {
            this.compression = Float.valueOf(compressionDisplay);
        } catch (NumberFormatException e) {
            this.compression = null;
        }
    }

    public Integer getWidth() {
        return width;
    }

    public void setWidth(Integer width) {
        this.width = width;
    }

    public String getWidthDisplay() {
        return width!=null?width.toString():null;
    }

    public void setWidthDisplay(String widthDisplay) {
        try {
            this.width = Integer.valueOf(widthDisplay);
        } catch (NumberFormatException e) {
            this.width = null;
        }
    }

    public void reset(ActionMapping actionMapping, HttpServletRequest httpServletRequest) {
        super.reset(actionMapping, httpServletRequest);
        useCache = Boolean.TRUE;
        compression = null;
        width = null;
    }
}