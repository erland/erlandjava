package erland.webapp.common.tags.calendar;

import erland.webapp.common.ServletParameterHelper;
import org.apache.commons.beanutils.PropertyUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.TagSupport;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.*;
import java.util.regex.Pattern;
import java.net.URL;
import java.net.MalformedURLException;

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

public class CalendarTag extends TagSupport {
    private String days;
    private String year;
    private String month;
    private String dateProperty;
    private String linkProperty;
    private String descriptionProperty;
    private String image;
    private String imageEmpty;
    private String styleText;
    private String styleLink;
    private String styleTable;
    private String styleCell;
    private String descriptions;

    public String getDays() {
        return days;
    }

    public void setDays(String days) {
        this.days = days;
    }

    public String getImage() {
        return image;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public String getDateProperty() {
        return dateProperty;
    }

    public void setDateProperty(String dateProperty) {
        this.dateProperty = dateProperty;
    }

    public String getLinkProperty() {
        return linkProperty;
    }

    public void setLinkProperty(String linkProperty) {
        this.linkProperty = linkProperty;
    }

    public String getDescriptionProperty() {
        return descriptionProperty;
    }

    public void setDescriptionProperty(String descriptionProperty) {
        this.descriptionProperty = descriptionProperty;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getImageEmpty() {
        return imageEmpty;
    }

    public void setImageEmpty(String imageEmpty) {
        this.imageEmpty = imageEmpty;
    }

    public String getStyleText() {
        return styleText;
    }

    public void setStyleText(String styleText) {
        this.styleText = styleText;
    }

    public String getStyleLink() {
        return styleLink;
    }

    public void setStyleLink(String styleLink) {
        this.styleLink = styleLink;
    }

    public String getStyleTable() {
        return styleTable;
    }

    public void setStyleTable(String styleTable) {
        this.styleTable = styleTable;
    }

    public String getStyleCell() {
        return styleCell;
    }

    public void setStyleCell(String styleCell) {
        this.styleCell = styleCell;
    }

    public String getDescriptions() {
        return descriptions;
    }

    public void setDescriptions(String descriptions) {
        this.descriptions = descriptions;
    }

    public int doStartTag() throws JspException {
        try {
            JspWriter out = pageContext.getOut();
            Calendar cal = Calendar.getInstance();
            String month = this.month;
            String year = this.year;
            if (month != null) {
                int monthNo = Integer.valueOf(month).intValue();
                cal.set(Calendar.MONTH, monthNo - 1);
            }
            month = ""+(cal.get(Calendar.MONTH) + 1);
            if (year != null) {
                int yearNo = Integer.valueOf(year).intValue();
                cal.set(Calendar.YEAR, yearNo);
            }
            year = ""+cal.get(Calendar.YEAR);
            cal.set(Calendar.DAY_OF_MONTH, 1);
            int maxDay = cal.getActualMaximum(Calendar.DAY_OF_MONTH);
            int start = cal.get(Calendar.DAY_OF_WEEK) - 1;
            if (start == 0) {
                start += 7;
            }
            Object[] daysObject = (Object[]) pageContext.findAttribute(days);
            Object[] daysArray = new Object[maxDay];
            for (int i = 0; i < daysObject.length; i++) {
                Object o = daysObject[i];
                Date date = (Date) PropertyUtils.getProperty(o,dateProperty);
                Calendar calTmp = Calendar.getInstance();
                calTmp.setTime(date);
                if(year.equals(""+calTmp.get(Calendar.YEAR)) && month.equals(""+(calTmp.get(Calendar.MONTH)+1))) {
                    daysArray[calTmp.get(Calendar.DAY_OF_MONTH)-1] = o;
                }
            }
            String[] titleStrings = new String[maxDay + 1];
            if (descriptionProperty != null) {
                for (int i = 1; i <= maxDay; i++) {
                    if(daysArray[i-1]!=null) {
                        titleStrings[i] = (String) PropertyUtils.getProperty(daysArray[i-1], descriptionProperty);
                    }
                }
            }
            if (descriptions != null) {
                pageContext.getRequest().setAttribute(descriptions, titleStrings);
            }

            out.write("<table" + (styleTable != null ? " class=\"" + styleTable + "\"" : "") + ">");
            int day = 1;
            Map parameters = new HashMap();
            while (day <= maxDay) {
                out.write("<tr>");
                for (int i = 1; i < start; i++) {
                    out.write("<td" + (styleCell != null ? " class=\"" + styleCell + "\"" : "") + "></td>");
                }
                for (int weekday = start; weekday <= 7 && day <= maxDay; weekday++, day++) {
                    cal.set(Calendar.DAY_OF_MONTH, day);

                    out.write("<td" + (styleCell != null ? " class=\"" + styleCell + "\"" : "") + ">");
                    String link = null;
                    if (titleStrings[day] != null && linkProperty != null) {
                        link = (String) PropertyUtils.getProperty(daysArray[day - 1], linkProperty);
                    }
                    parameters.put("day", "" + day);
                    if (link != null) {
                        out.write("<a href=\"" + addContextPath(link)+ "\" title=\"" + titleStrings[day] + "\""+(styleLink != null ? " class=\"" + styleLink + "\"" : "")+">");
                    }
                    if (daysArray[day-1] != null && image != null) {
                        String imageLink = ServletParameterHelper.replaceDynamicParameters(image, parameters);
                        out.write("<img src=\"" + addContextPath(imageLink) + "\" border=\"0\" onmouseover=\"doChangeText(" + day + ")\" onmouseout=\"doChangeText(0)\"></img>");
                    } else if (daysArray[day-1] == null && imageEmpty != null) {
                        String imageLink = ServletParameterHelper.replaceDynamicParameters(imageEmpty, parameters);
                        out.write("<img src=\"" + addContextPath(imageLink) + "\" border=\"0\" onmouseover=\"doChangeText(" + day + ")\" onmouseout=\"doChangeText(0)\"></img>");
                    } else {
                        out.write("<p"+(styleText != null ? " class=\"" + styleText + "\"" : "")+">"+day+"</p>");
                    }
                    if (link != null) {
                        out.write("</a>");
                    }
                    out.write("</td>");
                }
                start = 1;
                out.write("</tr>");
            }

            out.write("</table>");
        } catch (IOException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        } catch (NoSuchMethodException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        } catch (IllegalAccessException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        } catch (InvocationTargetException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
        return SKIP_BODY;
    }

    public void release() {
        super.release();
        days = null;
        year = null;
        month = null;
        dateProperty = null;
        linkProperty = null;
        descriptionProperty = null;
        image = null;
        imageEmpty = null;
        styleText = null;
        styleLink = null;
        styleTable = null;
        styleCell = null;
        descriptions = null;
    }

    private String addContextPath(String link) {
        if(link==null) {
            return link;
        }
        if(Pattern.matches("[a-z]*:.*",link)) {
            return link;
        }else {
            return ((HttpServletRequest) pageContext.getRequest()).getContextPath()+link;
        }
    }
}