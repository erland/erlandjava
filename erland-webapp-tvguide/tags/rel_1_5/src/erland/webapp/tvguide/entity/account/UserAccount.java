package erland.webapp.tvguide.entity.account;

/*
 * Copyright (C) 2005 Erland Isaksson (erland_i@hotmail.com)
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
    private String description;
    private String welcomeText;
    private String logo;
    private Boolean official;
    private String stylesheet;
    private String skin;
    private Boolean mailNotification;
    private String jabberId;
    private Boolean jabberNotification;
    private Boolean includeTips;
    private Integer minTipsReview;

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

    public Boolean getMailNotification() {
        return mailNotification;
    }

    public void setMailNotification(Boolean mailNotification) {
        this.mailNotification = mailNotification;
    }

    public Boolean getJabberNotification() {
        return jabberNotification;
    }

    public void setJabberNotification(Boolean jabberNotification) {
        this.jabberNotification = jabberNotification;
    }

    public String getJabberId() {
        return jabberId;
    }

    public void setJabberId(String jabberId) {
        this.jabberId = jabberId;
    }

    public Boolean getIncludeTips() {
        return includeTips;
    }

    public void setIncludeTips(Boolean includeTips) {
        this.includeTips = includeTips;
    }

    public Integer getMinTipsReview() {
        return minTipsReview;
    }

    public void setMinTipsReview(Integer minTipsReview) {
        this.minTipsReview = minTipsReview;
    }
}
