package erland.webapp.issuetracking.fb.issue;

import erland.webapp.common.BaseEntity;
import erland.webapp.common.ServletParameterHelper;
import erland.webapp.common.fb.BaseFB;
import erland.util.StringUtil;
import org.apache.struts.action.ActionMapping;

import javax.servlet.http.HttpServletRequest;

/*
 * Copyright (C) 2004 Erland Isaksson (erland_i@hotmail.com)
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

public class IssueFB extends BaseFB {
    private Integer id;
    private String application;
    private String version;
    private String username;
    private String realname;
    private String mail;
    private String title;
    private String description;
    private String externalReference;
    private String randomKey;
    private String requestedRandomKey;
    private Integer type;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getIdDisplay() {
        return StringUtil.asString(id,null);
    }

    public void setIdDisplay(String idDisplay) {
        this.id = StringUtil.asInteger(idDisplay,null);
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getTypeDisplay() {
        return StringUtil.asString(type,null);
    }

    public void setTypeDisplay(String typeDisplay) {
        this.type = StringUtil.asInteger(typeDisplay,null);
    }

    public String getApplication() {
        return application;
    }

    public void setApplication(String application) {
        this.application = application;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
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

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getRealname() {
        return realname;
    }

    public void setRealname(String realname) {
        this.realname = realname;
    }

    public String getExternalReference() {
        return externalReference;
    }

    public void setExternalReference(String externalReference) {
        this.externalReference = externalReference;
    }

    public String getRandomKey() {
        return randomKey;
    }

    public void setRandomKey(String randomKey) {
        this.randomKey = randomKey;
    }

    public String getRequestedRandomKey() {
        return requestedRandomKey;
    }

    public void setRequestedRandomKey(String requestedRandomKey) {
        this.requestedRandomKey = requestedRandomKey;
    }

    public void reset(ActionMapping actionMapping, HttpServletRequest httpServletRequest) {
        super.reset(actionMapping, httpServletRequest);
        id = null;
        application = null;
        version = null;
        username = null;
        realname = null;
        type = null;
        title = null;
        description = null;
        mail = null;
        externalReference = null;
        randomKey = null;
        requestedRandomKey = ""+(int)(1000000*Math.random());
    }
}