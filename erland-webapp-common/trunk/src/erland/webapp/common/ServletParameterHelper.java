package erland.webapp.common;

import erland.util.ParameterValueStorageInterface;
import erland.util.ObjectStorageMap;
import erland.util.ObjectStorageInterface;

import java.util.Map;
import java.util.Date;
import java.util.Locale;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.text.ParseException;

/*
 * Copyright (C) 2003-2004 Erland Isaksson (erland_i@hotmail.com)
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
    public static String replaceDynamicParameters(String address, Map parameters) {
        return replaceDynamicParameters(address,new ObjectStorageMap(parameters));
    }
    public static String replaceDynamicParameters(String address, ObjectStorageInterface parameters) {
        StringBuffer sb = new StringBuffer(address);
        int startPos = sb.indexOf("[");

        while(startPos>=0) {
            boolean bExists = true;
            boolean bIgnore = false;
            int endPos;
            if(sb.charAt(startPos+1)=='!') {
                bExists = false;
            }
            if(sb.charAt(startPos+1)=='[') {
                bIgnore = true;
                endPos = sb.indexOf("]]",startPos+2);
            }else {
                endPos = sb.indexOf("]",startPos+1);
            }
            if(!bIgnore && endPos>=0) {
                int attributeEndPos = sb.indexOf(",",startPos+1);
                if(attributeEndPos>=0 && attributeEndPos<endPos) {
                    String visibleAttribute = sb.substring(startPos+1+(bExists?0:1),attributeEndPos);
                    Object visibleValue = parameters.get(visibleAttribute);
                    if(bExists && visibleValue!=null) {
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
                        }else {
                            sb.replace(startPos,endPos+1,"");
                            endPos = startPos;
                        }
                    }else if(!bExists && (visibleValue==null ||
                            (visibleValue instanceof Object[] && ((Object[])visibleValue).length==0) ||
                            (visibleValue.toString().length()==0))) {
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
                    }else {
                        sb.replace(startPos,endPos+1,"");
                        endPos = startPos;
                    }
                }
                startPos = sb.indexOf("[",endPos);
            }else if(bIgnore && endPos>=0) {
                sb.replace(startPos,endPos+2,sb.substring(startPos+1,endPos+1));
                endPos-=2;
                startPos = sb.indexOf("[",endPos);
            }else {
                startPos = -1;
            }
        }
        return internalReplaceDynamicParameters(sb.toString(),parameters, 0);
    }

    private static String internalReplaceDynamicParameters(String address, ObjectStorageInterface parameters, int index) {
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

    public static String getParameter(String parameterString, String parameter) {
        if(parameterString.startsWith(parameter+"=")) {
            int startPos = (parameter+"=").length();
            int pos = parameterString.indexOf('&');
            if(pos<0) {
                return parameterString.substring(startPos);
            }
            return parameterString.substring(startPos,pos);
        }else {
            int pos = parameterString.indexOf("&"+parameter+"=");
            if(pos>=0) {
                int startPos = pos + ("&"+parameter+"=").length();
                int endPos = parameterString.indexOf("&",pos+1);
                if(endPos<0) {
                    return parameterString.substring(startPos);
                }
                return parameterString.substring(startPos,endPos);
            }else {
                return null;
            }
        }
    }
}