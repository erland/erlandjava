package erland.webapp.diagram;

import erland.webapp.diagram.DateValueSerieInterface;
import erland.webapp.diagram.DateValueInterface;

import java.util.*;

public class DateValueSerie implements DateValueSerieInterface {
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
                return i;
            }else if(value.getDate().getTime()>date.getTime()) {
                return i-1;
            }
        }
        if(serie.size()>0) {
            return serie.size()-1;
        }
        return -1;
    }
    public int indexOf(Date date) {
        return indexOf(date,0);
    }
}
