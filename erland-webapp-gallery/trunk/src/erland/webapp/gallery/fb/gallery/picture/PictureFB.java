package erland.webapp.gallery.fb.gallery.picture;

import org.apache.struts.validator.ValidatorForm;
import org.apache.struts.action.ActionMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.text.SimpleDateFormat;
import java.text.DateFormat;
import java.text.ParseException;

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

public class PictureFB extends ValidatorForm {
    private Integer gallery;
    private Integer id;
    private String title;
    private String description;
    private String image;
    private String link;
    private Boolean official;
    private Date date;

    private static DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
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

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getIdDisplay() {
        return id!=null?id.toString():null;
    }

    public void setIdDisplay(String idDisplay) {
        try {
            this.id = Integer.valueOf(idDisplay);
        } catch (NumberFormatException e) {
            this.id = null;
        }
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

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

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

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getDateDisplay() {
        return date!=null?dateFormat.format(date):null;
    }

    public void setDateDisplay(String dateDisplay) {
        try {
            this.date = dateFormat.parse(dateDisplay);
        } catch (ParseException e) {
            this.date = null;
        }
    }

    public void reset(ActionMapping actionMapping, HttpServletRequest httpServletRequest) {
        super.reset(actionMapping, httpServletRequest);
        gallery = null;
        id = null;
        title = null;
        description = null;
        image = null;
        link = null;
        official = Boolean.FALSE;
        date = null;
    }
}