package erland.webapp.homepage.entity.account;

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

import erland.webapp.common.BaseEntity;

public class UserAccount extends BaseEntity {
    private String username;
    private String headerText;
    private String headerTextEnglish;
    private String description;
    private String descriptionEnglish;
    private String welcomeText;
    private String welcomeTextEnglish;
    private String logo;
    private String logoEnglish;
    private String logoLink;
    private Integer defaultSection;
    private Boolean official;
    private String copyrightText;
    private String stylesheet;
    private String skin;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
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

    public Boolean getOfficial() {
        return official;
    }

    public void setOfficial(Boolean official) {
        this.official = official;
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    public String getWelcomeText() {
        return welcomeText;
    }

    public void setWelcomeText(String welcomeText) {
        this.welcomeText = welcomeText;
    }

    public String getWelcomeTextEnglish() {
        return welcomeTextEnglish;
    }

    public void setWelcomeTextEnglish(String welcomeTextEnglish) {
        this.welcomeTextEnglish = welcomeTextEnglish;
    }

    public String getCopyrightText() {
        return copyrightText;
    }

    public void setCopyrightText(String copyrightText) {
        this.copyrightText = copyrightText;
    }

    public String getStylesheet() {
        return stylesheet;
    }

    public void setStylesheet(String stylesheet) {
        this.stylesheet = stylesheet;
    }

    public String getSkin() {
        return skin;
    }

    public void setSkin(String skin) {
        this.skin = skin;
    }

    public String getHeaderText() {
        return headerText;
    }

    public void setHeaderText(String headerText) {
        this.headerText = headerText;
    }

    public String getHeaderTextEnglish() {
        return headerTextEnglish;
    }

    public void setHeaderTextEnglish(String headerTextEnglish) {
        this.headerTextEnglish = headerTextEnglish;
    }

    public String getLogoEnglish() {
        return logoEnglish;
    }

    public void setLogoEnglish(String logoEnglish) {
        this.logoEnglish = logoEnglish;
    }

    public String getLogoLink() {
        return logoLink;
    }

    public void setLogoLink(String logoLink) {
        this.logoLink = logoLink;
    }

    public Integer getDefaultSection() {
        return defaultSection;
    }

    public void setDefaultSection(Integer defaultSection) {
        this.defaultSection = defaultSection;
    }
}
