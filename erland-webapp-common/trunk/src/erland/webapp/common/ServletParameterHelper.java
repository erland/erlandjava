package erland.webapp.common;
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
            integerValue = Integer.valueOf(value);
        }
        return integerValue;
    }

    public static Long asLong(String value, Long defaultValue) {
        Long longValue = defaultValue;
        if(value!=null && value.length()>0) {
            longValue = Long.valueOf(value);
        }
        return longValue;
    }

    public static Double asDouble(String value, Double defaultValue) {
        Double doubleValue = defaultValue;
        if(value!=null && value.length()>0) {
            doubleValue = Double.valueOf(value);
        }
        return doubleValue;
    }

    public static Float asFloat(String value, Float defaultValue) {
        Float floatValue = defaultValue;
        if(value!=null && value.length()>0) {
            floatValue = Float.valueOf(value);
        }
        return floatValue;
    }
}