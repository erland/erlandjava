package erland.webapp.datacollection.fb.entry;

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

import erland.util.StringUtil;
import erland.webapp.common.fb.BaseFB;
import org.apache.struts.action.ActionMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

public class EntryFB extends BaseFB {
    private String collectionDisplay;
    private String idDisplay;
    private String uniqueEntryId;
    private String title;
    private String description;
    private String lastChangedDisplay;

    private static final DateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd HH:mm");

    public String getCollectionDisplay() {
        return collectionDisplay;
    }

    public void setCollectionDisplay(String collectionDisplay) {
        this.collectionDisplay = collectionDisplay;
    }

    public Integer getCollection() {
        return StringUtil.asInteger(collectionDisplay,null);
    }

    public void setCollection(Integer collection) {
        this.collectionDisplay = StringUtil.asString(collection,null);
    }

    public String getIdDisplay() {
        return idDisplay;
    }

    public void setIdDisplay(String idDisplay) {
        this.idDisplay = idDisplay;
    }

    public Integer getId() {
        return StringUtil.asInteger(idDisplay,null);
    }

    public void setId(Integer id) {
        this.idDisplay = StringUtil.asString(id,null);
    }

    public String getUniqueEntryId() {
        return uniqueEntryId;
    }

    public void setUniqueEntryId(String uniqueEntryId) {
        this.uniqueEntryId = uniqueEntryId;
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

    public String getLastChangedDisplay() {
        return lastChangedDisplay;
    }

    public void setLastChangedDisplay(String lastChangedDisplay) {
        this.lastChangedDisplay = lastChangedDisplay;
    }

    public Date getLastChanged() {
        return StringUtil.asDate(lastChangedDisplay,null,DATE_FORMAT);
    }

    public void setLastChanged(Date lastChanged) {
        this.lastChangedDisplay = StringUtil.asString(lastChanged,null,DATE_FORMAT);
    }

    public void reset(ActionMapping actionMapping, HttpServletRequest httpServletRequest) {
        super.reset(actionMapping, httpServletRequest);
        collectionDisplay = null;
        idDisplay = null;
        uniqueEntryId = null;
        title = null;
        description = null;
        lastChangedDisplay = null;
    }
}