package erland.webapp.cdcollection.fb.collection;

import erland.webapp.common.fb.BaseFB;
import erland.util.StringUtil;
import org.apache.struts.action.ActionMapping;

import javax.servlet.http.HttpServletRequest;

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

public class CollectionMediaFB extends BaseFB {
    private String collectionIdDisplay;
    private String mediaIdDisplay;

    public String getCollectionIdDisplay() {
        return collectionIdDisplay;
    }

    public void setCollectionIdDisplay(String collectionIdDisplay) {
        this.collectionIdDisplay = collectionIdDisplay;
    }

    public Integer getCollectionId() {
        return StringUtil.asInteger(collectionIdDisplay,null);
    }

    public void setCollectionId(Integer collectionId) {
        this.collectionIdDisplay = StringUtil.asString(collectionId,null);
    }

    public String getMediaIdDisplay() {
        return mediaIdDisplay;
    }

    public void setMediaIdDisplay(String mediaIdDisplay) {
        this.mediaIdDisplay = mediaIdDisplay;
    }

    public Integer getMediaId() {
        return StringUtil.asInteger(mediaIdDisplay,null);
    }

    public void setMediaId(Integer mediaId) {
        this.mediaIdDisplay = StringUtil.asString(mediaId,null);
    }

    public void reset(ActionMapping actionMapping, HttpServletRequest httpServletRequest) {
        super.reset(actionMapping, httpServletRequest);
        collectionIdDisplay = null;
        mediaIdDisplay = null;
    }
}