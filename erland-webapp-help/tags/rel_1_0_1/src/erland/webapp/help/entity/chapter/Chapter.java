package erland.webapp.help.entity.chapter;

import erland.webapp.common.BaseEntity;

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

public class Chapter extends BaseEntity {
    private Integer id;
    private String application;
    private String version;
    private String chapter;
    private Integer orderNo;
    private String titleNative;
    private String titleEnglish;
    private String headerNative;
    private String headerEnglish;
    private String footerNative;
    private String footerEnglish;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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

    public String getChapter() {
        return chapter;
    }

    public void setChapter(String chapter) {
        this.chapter = chapter;
    }

    public Integer getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(Integer orderNo) {
        this.orderNo = orderNo;
    }

    public String getTitleNative() {
        return titleNative;
    }

    public void setTitleNative(String titleNative) {
        this.titleNative = titleNative;
    }

    public String getTitleEnglish() {
        return titleEnglish;
    }

    public void setTitleEnglish(String titleEnglish) {
        this.titleEnglish = titleEnglish;
    }

    public String getHeaderNative() {
        return headerNative;
    }

    public void setHeaderNative(String headerNative) {
        this.headerNative = headerNative;
    }

    public String getHeaderEnglish() {
        return headerEnglish;
    }

    public void setHeaderEnglish(String headerEnglish) {
        this.headerEnglish = headerEnglish;
    }

    public String getFooterNative() {
        return footerNative;
    }

    public void setFooterNative(String footerNative) {
        this.footerNative = footerNative;
    }

    public String getFooterEnglish() {
        return footerEnglish;
    }

    public void setFooterEnglish(String footerEnglish) {
        this.footerEnglish = footerEnglish;
    }
}