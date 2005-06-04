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

import org.apache.commons.logging.LogFactory;
import org.apache.commons.logging.Log;

import java.util.Calendar;
import java.util.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

public class DateInterval {
    /** Logging instance */
    private static Log LOG = LogFactory.getLog(DateInterval.class);
    private static int counter=0;
    private String value;
    private int intValue;
    private static DateFormat yearFormat = new SimpleDateFormat("yyyy");
    private static DateFormat yearMonthFormat = new SimpleDateFormat("yyyy-MM");
    private static DateFormat yearMonthDayFormat = new SimpleDateFormat("yyyy-MM-dd");
    private DateInterval(String value) {
        this.value=value;
        this.intValue = counter++;
    }
    public static final DateInterval DAY=new DateInterval("day");
    public static final DateInterval SECONDDAY=new DateInterval("secondday");
    public static final DateInterval WEEK=new DateInterval("week");
    public static final DateInterval MONTH=new DateInterval("month");
    public static final DateInterval SECONDMONTH=new DateInterval("secondmonth");
    public static final DateInterval THIRDMONTH=new DateInterval("thirdmonth");
    public static final DateInterval SIXTHMONTH=new DateInterval("sixthmonth");
    public static final DateInterval YEAR=new DateInterval("year");
    public static final DateInterval SECONDYEAR=new DateInterval("secondyear");
    public static final DateInterval FIFTHYEAR=new DateInterval("fifthyear");
    public static final DateInterval TENTHYEAR=new DateInterval("tenthyear");

    public String toString() {
        return value;
    }

    public static DateInterval calculateInterval(Date minDate, Date maxDate, int maxNoOfInterval) {
        Calendar calMin = Calendar.getInstance();
        calMin.setTime(minDate);
        Calendar calMax = Calendar.getInstance();
        calMax.setTime(maxDate);
        int yearDiff = (calMax.get(Calendar.YEAR)-calMin.get(Calendar.YEAR));
        int monthDiff = yearDiff*12+calMax.get(Calendar.MONTH)-calMin.get(Calendar.MONTH);
        int dayDiff = yearDiff*365+calMax.get(Calendar.DAY_OF_YEAR)-calMin.get(Calendar.DAY_OF_YEAR);
        if(calMax.get(Calendar.MONTH)<calMin.get(Calendar.MONTH)) {
            yearDiff--;
        }
        if(calMax.get(Calendar.DAY_OF_MONTH)<calMin.get(Calendar.DAY_OF_MONTH)) {
            monthDiff--;
        }
        if(dayDiff<=maxNoOfInterval) {
            return DAY;
        }if(dayDiff/2<=maxNoOfInterval) {
            return SECONDDAY;
        }if(dayDiff/7<=maxNoOfInterval) {
            return WEEK;
        }else if(dayDiff/31<=maxNoOfInterval) {
            return MONTH;
        }else if(monthDiff/2<=maxNoOfInterval) {
            return SECONDMONTH;
        }else if(monthDiff/3<=maxNoOfInterval) {
            return THIRDMONTH;
        }else if(monthDiff/6<=maxNoOfInterval) {
            return SIXTHMONTH;
        }else if(monthDiff/12<=maxNoOfInterval) {
            return YEAR;
        }else if(monthDiff/12/2<=maxNoOfInterval) {
            return SECONDYEAR;
        }else if(monthDiff/12/5<=maxNoOfInterval) {
            return FIFTHYEAR;
        }else if(monthDiff/12/10<=maxNoOfInterval) {
            return TENTHYEAR;
        }else {
            return null;
        }
    }

    private void nextMatching(Calendar cal) {
        LOG.debug("nextMatching in="+cal.getTime());
        cal.set(Calendar.HOUR_OF_DAY,0);
        cal.set(Calendar.MINUTE,0);
        cal.set(Calendar.SECOND,0);
        cal.set(Calendar.MILLISECOND,0);

        if(this==DAY) {
            cal.add(Calendar.DAY_OF_MONTH,1);
        }else if(this==SECONDDAY) {
            cal.add(Calendar.DAY_OF_MONTH,1);
            cal.add(Calendar.DAY_OF_MONTH,1);
        }else if(this==WEEK) {
            do {
                cal.add(Calendar.DAY_OF_MONTH,1);
            }while(cal.get(Calendar.DAY_OF_WEEK)!=Calendar.MONDAY);
        }else {
            cal.set(Calendar.DAY_OF_MONTH,1);
            if(this==MONTH) {
                cal.add(Calendar.MONTH,1);
            }else if(this==SECONDMONTH) {
                cal.add(Calendar.MONTH,1);
                cal.add(Calendar.MONTH,1);
            }else if(this==THIRDMONTH) {
                do {
                    cal.add(Calendar.MONTH,1);
                }while((cal.get(Calendar.MONTH))%3!=0);
            }else if(this==SIXTHMONTH) {
                do {
                    cal.add(Calendar.MONTH,1);
                }while((cal.get(Calendar.MONTH))%6!=0);
            }else {
                cal.set(Calendar.MONTH,0);
                if(this==YEAR) {
                    cal.add(Calendar.YEAR,1);
                }else if(this==SECONDYEAR) {
                    cal.add(Calendar.YEAR,1);
                    cal.add(Calendar.YEAR,1);
                }else if(this==FIFTHYEAR) {
                    do {
                        cal.add(Calendar.YEAR,1);
                    }while(cal.get(Calendar.YEAR)%5!=0);
                }else if(this==TENTHYEAR) {
                    do {
                        cal.add(Calendar.YEAR,1);
                    }while(cal.get(Calendar.YEAR)%10!=0);
                }
            }
        }
        LOG.debug("nextMatching out="+cal.getTime());
   }
    private boolean isMatching(Calendar cal) {
        if(cal.get(Calendar.HOUR_OF_DAY)==0 &&
            cal.get(Calendar.MINUTE)==0 &&
            cal.get(Calendar.SECOND)==0 &&
            cal.get(Calendar.MILLISECOND)==0) {

            if(this==DAY) {
                return true;
            }else if(this==SECONDDAY) {
                return cal.get(Calendar.DAY_OF_MONTH)%2==0;
            }else if(this==WEEK) {
                return cal.get(Calendar.DAY_OF_WEEK)==Calendar.MONDAY;
            }else if(cal.get(Calendar.DAY_OF_MONTH)==1) {
                if(this==MONTH) {
                    return true;
                }else if(this == SECONDMONTH) {
                    return true;
                }else if(this == THIRDMONTH) {
                    return (cal.get(Calendar.MONTH))%3==0;
                }else if(this == SIXTHMONTH) {
                    return (cal.get(Calendar.MONTH))%6==0;
                }else if(cal.get(Calendar.MONTH)==0) {
                    if(this == YEAR) {
                        return true;
                    }else if(this == SECONDYEAR) {
                        return true;
                    }else if(this == FIFTHYEAR) {
                        return cal.get(Calendar.YEAR)%5==0;
                    }else if(this == TENTHYEAR) {
                        return cal.get(Calendar.YEAR)%10==0;
                    }
                }
            }
        }
        LOG.debug("isMatching "+cal.getTime()+" false");
        return false;
    }
    public Date nextAfter(Date startDate) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(startDate);
        nextMatching(cal);
        return cal.getTime();
    }
    public Date next(Date startDate) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(startDate);
        if(!isMatching(cal)) {
            nextMatching(cal);
        }
        return cal.getTime();
    }
    public String format(Date date) {
        if(this.intValue>=YEAR.intValue) {
            return yearFormat.format(date);
        }else if(this.intValue>=MONTH.intValue) {
            return yearMonthFormat.format(date);
        }else {
            return yearMonthDayFormat.format(date);
        }
    }
}
