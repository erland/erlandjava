package erland.webapp.common;

import java.util.Map;
import java.util.Date;
import java.util.Locale;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
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

public class ServletParameterHelper {
    private final static DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

    public static String replaceDynamicParameters(String address, Map parameters) {
        StringBuffer sb = new StringBuffer(address);
        int startPos = sb.indexOf("[");
        while(startPos>=0) {
            int endPos = sb.indexOf("]",startPos+1);
            if(endPos>=0) {
                int attributeEndPos = sb.indexOf(",",startPos+1);
                if(attributeEndPos>=0 && attributeEndPos<endPos) {
                    String visibleAttribute = sb.substring(startPos+1,attributeEndPos);
                    Object visibleValue = parameters.get(visibleAttribute);
                    if(visibleValue!=null) {
                        if((visibleValue instanceof Object[] && ((Object[])visibleValue).length>0) ||
                            visibleValue.toString().length()>0) {

                            String realString = sb.substring(attributeEndPos+1,endPos);
                            String resultString = "";
                            if(visibleValue instanceof Object[]) {
                                Object[] visibleValues = (Object[]) visibleValue;
                                for (int i = 0; i < visibleValues.length; i++) {
                                    resultString += internalReplaceDynamicParameters(realString,parameters,i);
                                }
                            }else {
                                resultString = internalReplaceDynamicParameters(realString,parameters,0);
                            }
                            if(resultString!=null) {
                                sb.replace(startPos,endPos+1,resultString);
                                endPos = startPos+resultString.length();
                            }else {
                                sb.replace(startPos,endPos+1,"");
                                endPos = startPos;
                            }
                        }
                    }else {
                        sb.replace(startPos,endPos+1,"");
                        endPos = startPos;
                    }
                }
                startPos = sb.indexOf("[",endPos);
            }else {
                startPos = -1;
            }
        }
        return internalReplaceDynamicParameters(sb.toString(),parameters, 0);
    }

    private static String internalReplaceDynamicParameters(String address, Map parameters, int index) {
        StringBuffer sb = new StringBuffer(address);
        int startPos = sb.indexOf("{");
        while(startPos>=0) {
            int endPos = sb.indexOf("}",startPos+1);
            if(endPos>=0) {
                String attribute = sb.substring(startPos+1,endPos);
                Object value = parameters.get(attribute);
                if(value!=null) {
                    if(value instanceof Object[]) {
                        value = ((Object[])value)[index];
                    }
                    sb.replace(startPos,endPos+1,value.toString());
                    endPos = startPos+value.toString().length();
                }else {
                    sb.replace(startPos,endPos+1,"");
                    endPos = startPos;
                }
                startPos = sb.indexOf("{",endPos);
            }else {
                startPos = -1;
            }
        }
        return sb.toString();
    }

    public static String replaceParameter(String parameterString,String parameter,String value) {
        StringBuffer sb = new StringBuffer(parameterString);
        int startPos = 0;
        if(parameterString.startsWith(parameter+"=")) {
            int pos = parameterString.indexOf('&');
            if(pos<0) {
                sb.setLength(0);
                sb.append(parameter);
                sb.append("=");
                sb.append(value);
            }else {
                sb.replace(0,pos,parameter+"="+value);
            }
            startPos = parameter.length()+1+value.length();
        }else {
            int pos = parameterString.indexOf("&"+parameter+"=");
            if(pos>=0) {
                int endPos = parameterString.indexOf("&",pos+1);
                if(endPos<0) {
                    endPos = sb.length();
                }
                sb.replace(pos+1,endPos,parameter+"="+value);
                startPos = pos+1+parameter.length()+1+value.length();
            }else {
                sb.append("&");
                sb.append(parameter);
                sb.append("=");
                sb.append(value);
                startPos = sb.length();
            }
        }
        int pos = sb.indexOf("&"+parameter+"=",startPos);
        while(pos>=0) {
            int endPos = sb.indexOf("&",pos+1);
            if(endPos<0) {
                endPos = sb.length();
            }
            sb.replace(pos,endPos,"");
            pos = sb.indexOf("&"+parameter+"=",pos);
        }
        return sb.toString();
    }

    public static String removeParameter(String parameterString, String parameter) {
        StringBuffer sb = new StringBuffer(parameterString);
        int startPos = 0;
        if(parameterString.startsWith(parameter+"=")) {
            int pos = parameterString.indexOf('&');
            if(pos<0) {
                sb.setLength(0);
            }else {
                sb.replace(0,pos,"");
            }
            startPos = 0;
        }else {
            int pos = parameterString.indexOf("&"+parameter+"=");
            if(pos>=0) {
                int endPos = parameterString.indexOf("&",pos+1);
                if(endPos<0) {
                    endPos = sb.length();
                }
                sb.replace(pos+1,endPos,"");
                startPos = pos+1;
            }else {
                startPos = sb.length();
            }
        }
        int pos = sb.indexOf("&"+parameter+"=",startPos);
        while(pos>=0) {
            int endPos = sb.indexOf("&",pos+1);
            if(endPos<0) {
                endPos = sb.length();
            }
            sb.replace(pos,endPos,"");
            pos = sb.indexOf("&"+parameter+"=",pos);
        }
        return sb.toString();
    }
    public static Boolean asBoolean(String value,Boolean defaultValue) {
        Boolean booleanValue = defaultValue;
        if(value!=null && value.equalsIgnoreCase("true")) {
            booleanValue = Boolean.TRUE;
        }else if(value!=null && value.equalsIgnoreCase("false")) {
            booleanValue = Boolean.FALSE;
        }
        return booleanValue;
    }

    public static Integer asInteger(String value,Integer defaultValue) {
        Integer integerValue = defaultValue;
        if(value!=null && value.length()>0) {
            try {
                integerValue = Integer.valueOf(value);
            } catch (NumberFormatException e) {
            }
        }
        return integerValue;
    }

    public static Long asLong(String value, Long defaultValue) {
        Long longValue = defaultValue;
        if(value!=null && value.length()>0) {
            try {
                longValue = Long.valueOf(value);
            } catch (NumberFormatException e) {
            }
        }
        return longValue;
    }

    public static Double asDouble(String value, Double defaultValue) {
        Double doubleValue = defaultValue;
        if(value!=null && value.length()>0) {
            try {
                doubleValue = Double.valueOf(value);
            } catch (NumberFormatException e) {
            }
        }
        return doubleValue;
    }

    public static Float asFloat(String value, Float defaultValue) {
        Float floatValue = defaultValue;
        if(value!=null && value.length()>0) {
            try {
                floatValue = Float.valueOf(value);
            } catch (NumberFormatException e) {
            }
        }
        return floatValue;
    }

    public static Date asDate(String value, Date defaultValue) {
        Date dateValue = defaultValue;
        if(value!=null) {
            try {
                dateValue = dateFormat.parse(value);
            } catch (ParseException e) {
            }
        }
        return dateValue;
    }

    public static Date asDate(String value, Date defaultValue, Locale locale) {
        Date dateValue = defaultValue;
        if(value!=null) {
            try {
                dateValue = DateFormat.getDateInstance(DateFormat.SHORT,locale).parse(value);
            } catch (ParseException e) {
            }
        }
        return dateValue;
    }

    public static String asString(Integer value, String defaultValue) {
        return value!=null?value.toString():defaultValue;
    }
    public static String asString(Long value, String defaultValue) {
        return value!=null?value.toString():defaultValue;
    }
    public static String asString(Boolean value, String defaultValue) {
        return value!=null?value.toString():defaultValue;
    }
    public static String asString(Float value, String defaultValue) {
        return value!=null?value.toString():defaultValue;
    }
    public static String asString(Double value, String defaultValue) {
        return value!=null?value.toString():defaultValue;
    }
    public static String asString(Date value, String defaultValue) {
        return value!=null?dateFormat.format(value):defaultValue;
    }
    public static String asString(Date value, String defaultValue, Locale locale) {
        return value!=null?DateFormat.getDateInstance(DateFormat.SHORT,locale).format(value):defaultValue;
    }
}