package erland.webapp.diagram;
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

import erland.webapp.diagram.DateValueSerieInterface;
import erland.webapp.diagram.DateValueInterface;

import java.util.*;

import org.apache.commons.logging.LogFactory;
import org.apache.commons.logging.Log;

public class DateValueSerie implements DateValueSerieInterface {
    /** Logging instance */
    private static Log LOG = LogFactory.getLog(DateValueSerie.class);
    private String name;
    private Vector serie = new Vector();

    public DateValueSerie(String name) {
        this.name = name;
    }

    public void setName(String name) {
        this.name = name;
    }
    public String getName() {
        return name;
    }
    public Iterator getSerie(DateValueSerieType type) {
        Calendar cal = Calendar.getInstance();
        Vector vec = new Vector();
        int oldday=0;
        int oldmonth=0;
        int day=0;
        int month=0;
        if(type==DateValueSerieType.YEARLY) {
            for(int i=0;i<serie.size();i++) {
                DateValueInterface rate = (DateValueInterface) serie.elementAt(i);
                Date date = rate.getDate();
                cal.setTime(date);
                oldmonth=month;
                oldday=day;
                day = cal.get(Calendar.DAY_OF_MONTH);
                month = cal.get(Calendar.MONTH);
                if(oldday==0 || (day<oldday && month<oldmonth)) {
                    vec.addElement(rate);
                }else {
                    if(i==serie.size()-1) {
                        vec.addElement(rate);
                    }
                }
            }
        }else if(type==DateValueSerieType.QUARTERLY) {
            for(int i=0;i<serie.size();i++) {
                DateValueInterface rate = (DateValueInterface) serie.elementAt(i);
                Date date = rate.getDate();
                cal.setTime(date);
                oldmonth=month;
                oldday=day;
                day = cal.get(Calendar.DAY_OF_MONTH);
                month = cal.get(Calendar.MONTH);
                if(oldday==0 || (day<oldday &&
                    (month==0 || month==3 || month==6||month==9))) {

                    vec.addElement(rate);
                }else {
                    if(i==serie.size()-1) {
                        vec.addElement(rate);
                    }
                }
            }
        }else if(type==DateValueSerieType.MONTHLY) {
            for(int i=0;i<serie.size();i++) {
                DateValueInterface rate = (DateValueInterface) serie.elementAt(i);
                Date date = rate.getDate();
                cal.setTime(date);
                oldday=day;
                day = cal.get(Calendar.DAY_OF_MONTH);
                if(oldday==0 || (day<oldday)) {
                    vec.addElement(rate);
                }else {
                    if(i==serie.size()-1) {
                        vec.addElement(rate);
                    }
                }
            }
        }else if(type==DateValueSerieType.WEEKLY) {
            for(int i=0;i<serie.size();i++) {
                DateValueInterface rate = (DateValueInterface) serie.elementAt(i);
                Date date = rate.getDate();
                cal.setTime(date);
                oldday=day;
                day = cal.get(Calendar.DAY_OF_WEEK);
                if(oldday==0 || (day<oldday)) {
                    vec.addElement(rate);
                }else {
                    if(i==serie.size()-1) {
                        vec.addElement(rate);
                    }
                }
            }
        }else {
            vec = serie;
        }
        return vec.iterator();
    }

    public int size() {
        return serie.size();
    }

    public DateValueInterface getDateValue(int pos) {
        return (DateValueInterface) serie.elementAt(pos);
    }
    public void addDateValue(DateValueInterface dateValue) {
        serie.addElement(dateValue);
    }
    public void setDateValue(DateValueInterface dateValue,int pos) {
        serie.setElementAt(dateValue,pos);
    }
    public void insertDateValue(DateValueInterface dateValue, int pos) {
        serie.insertElementAt(dateValue,pos);
    }
    public int indexOf(Date date,int startPos) {
        for(int i=startPos;i<serie.size();i++) {
            DateValueInterface value = getDateValue(i);
            if(value.getDate().equals(date)) {
                if(LOG.isDebugEnabled()) {
                    LOG.debug("indexOf("+date+")="+value.getDate());
                }
                return i;
            }else if(value.getDate().getTime()>date.getTime()) {
                if(LOG.isDebugEnabled()) {
                    if(i-1>=0) {
                        LOG.debug("indexOf("+date+")="+getDateValue(i-1).getDate());
                    }else {
                        LOG.debug("indexOf("+date+")=NONE");
                    }
                }
                return i-1;
            }
        }
        if(serie.size()>0) {
            if(LOG.isDebugEnabled()) {
                LOG.debug("indexOf("+date+")="+getDateValue(serie.size()-1).getDate());
            }
            return serie.size()-1;
        }
        if(LOG.isDebugEnabled()) {
            LOG.debug("indexOf("+date+")=NONE");
        }
        return -1;
    }
    public int indexOf(Date date) {
        return indexOf(date,0);
    }

    public void deleteBefore(Date date) {
        for(Iterator it=serie.iterator();it.hasNext();) {
            DateValueInterface value = (DateValueInterface) it.next();
            if(value.getDate().before(date)) {
                LOG.debug("remove: "+value.getDate());
                it.remove();
            }
        }
    }

    public void deleteAfter(Date date) {
        for(Iterator it=serie.iterator();it.hasNext();) {
            DateValueInterface value = (DateValueInterface) it.next();
            if(value.getDate().after(date)) {
                LOG.debug("remove: "+value.getDate());
                it.remove();
            }
        }
    }
}
