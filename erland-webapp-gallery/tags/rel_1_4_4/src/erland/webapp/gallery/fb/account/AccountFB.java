package erland.webapp.gallery.fb.account;

import org.apache.struts.validator.ValidatorForm;

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

public class AccountFB extends ValidatorForm {
    private String username;
    private String firstName;
    private String lastName;
    private String password1;
    private String password2;
    private String welcomeText;
    private String copyrightText;
    private String description;
    private String logo;
    private Boolean official;
    private Integer defaultGallery;

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
        return official;
    }

    public void setOfficial(Boolean official) {
        this.official = official;
    }

    public String getOfficialDisplay() {
        return official != null ? official.toString() : Boolean.FALSE.toString();
    }

    public void setOfficialDisplay(String officialDisplay) {
        this.official = Boolean.TRUE.toString().equalsIgnoreCase(officialDisplay) ? Boolean.TRUE : Boolean.FALSE;
    }

    public Integer getDefaultGallery() {
        return defaultGallery;
    }

    public void setDefaultGallery(Integer defaultGallery) {
        this.defaultGallery = defaultGallery;
    }

    public String getDefaultGalleryDisplay() {
        return defaultGallery!=null?defaultGallery.toString():"";
    }

    public void setDefaultGalleryDisplay(String defaultGalleryDisplay) {
        try {
            this.defaultGallery = Integer.valueOf(defaultGalleryDisplay);
        } catch (NumberFormatException e) {
            this.defaultGallery = null;
        }
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }
}