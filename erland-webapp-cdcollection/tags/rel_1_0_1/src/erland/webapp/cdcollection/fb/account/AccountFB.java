package erland.webapp.cdcollection.fb.account;

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

public class AccountFB extends BaseFB {
    private String username;
    private String firstName;
    private String lastName;
    private String password1;
    private String password2;
    private String welcomeText;
    private String welcomeTextEnglish;
    private String copyrightText;
    private String description;
    private String descriptionEnglish;
    private String logo;
    private String officialDisplay;
    private String defaultCollectionDisplay;
    private String stylesheet;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPassword1() {
        return password1;
    }

    public void setPassword1(String password1) {
        this.password1 = password1;
    }

    public String getPassword2() {
        return password2;
    }

    public void setPassword2(String password2) {
        this.password2 = password2;
    }

    public String getWelcomeText() {
        return welcomeText;
    }

    public void setWelcomeText(String welcomeText) {
        this.welcomeText = welcomeText;
    }

    public String getCopyrightText() {
        return copyrightText;
    }

    public void setCopyrightText(String copyrightText) {
        this.copyrightText = copyrightText;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Boolean getOfficial() {
        return StringUtil.asBoolean(officialDisplay,Boolean.FALSE);
    }

    public void setOfficial(Boolean official) {
        this.officialDisplay = StringUtil.asString(official,null);
    }

    public String getOfficialDisplay() {
        return officialDisplay;
    }

    public void setOfficialDisplay(String officialDisplay) {
        this.officialDisplay = officialDisplay;
    }

    public Integer getDefaultCollection() {
        return StringUtil.asInteger(defaultCollectionDisplay,null);
    }

    public void setDefaultCollection(Integer defaultCollection) {
        this.defaultCollectionDisplay = StringUtil.asString(defaultCollection,null);
    }

    public String getDefaultCollectionDisplay() {
        return defaultCollectionDisplay;
    }

    public void setDefaultCollectionDisplay(String defaultCollectionDisplay) {
        this.defaultCollectionDisplay = defaultCollectionDisplay;
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    public String getStylesheet() {
        return stylesheet;
    }

    public void setStylesheet(String stylesheet) {
        this.stylesheet = stylesheet;
    }

    public String getWelcomeTextEnglish() {
        return welcomeTextEnglish;
    }

    public void setWelcomeTextEnglish(String welcomeTextEnglish) {
        this.welcomeTextEnglish = welcomeTextEnglish;
    }

    public String getDescriptionEnglish() {
        return descriptionEnglish;
    }

    public void setDescriptionEnglish(String descriptionEnglish) {
        this.descriptionEnglish = descriptionEnglish;
    }

    public void reset(ActionMapping actionMapping, HttpServletRequest httpServletRequest) {
        super.reset(actionMapping, httpServletRequest);
        username = null;
        firstName = null;
        lastName = null;
        password1 = null;
        password2 = null;
        welcomeText = null;
        welcomeTextEnglish = null;
        copyrightText = null;
        description = null;
        descriptionEnglish = null;
        logo = null;
        officialDisplay = null;
        defaultCollectionDisplay = null;
        stylesheet = null;
    }
}