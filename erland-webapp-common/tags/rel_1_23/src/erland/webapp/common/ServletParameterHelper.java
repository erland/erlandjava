package erland.webapp.common;

import java.util.Map;
import java.util.StringTokenizer;

import org.apache.commons.logging.LogFactory;
import org.apache.commons.logging.Log;
import erland.util.*;

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
    /** Logging instance */
    private static Log LOG = LogFactory.getLog(ServletParameterHelper.class);
    private static boolean bLogging = LOG.isDebugEnabled();
    public static String replaceDynamicParameters(String address, Map parameters) {
        return replaceDynamicParameters(address,new ObjectStorageMap(parameters));
    }
    public static String replaceDynamicParameters(String address, ObjectStorageInterface parameters) {
        if(bLogging) LOG.debug("Got: "+address);
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
                        if(StringUtil.asNull(resultString)!=null) {
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
        String result = internalReplaceDynamicParameters(sb.toString(),parameters, 0);
        if(bLogging) LOG.debug("Return: "+result);
        return result;
    }

    private static String internalReplaceDynamicParameters(String address, ObjectStorageInterface parameters, int index) {
        if(bLogging) LOG.debug("Got: "+address);
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
                    if(StringUtil.asNull(value.toString())!=null) {
                        sb.replace(startPos,endPos+1,value.toString());
                        endPos = startPos+value.toString().length();
                    }else {
                        sb.replace(startPos,endPos+1,"");
                        endPos = startPos;
                    }
                }else {
                    sb.replace(startPos,endPos+1,"");
                    endPos = startPos;
                }
                startPos = sb.indexOf("{",endPos);
            }else {
                startPos = -1;
            }
        }
        String result = sb.toString();
        if(bLogging) LOG.debug("Return: "+result);
        return result;
    }

    public static String replaceParametersInUrl(String url, String replaceParameterString) {
        return replaceParametersInUrl(url,replaceParameterString,'&');
    }
    public static String replaceParametersInUrl(String url, String replaceParameterString, char parameterDelimiter) {
        if(bLogging) LOG.debug("Got: "+url);
        String result = null;
        int pos = url.indexOf('?');
        if(pos>=0) {
            String parameterString = "";
            if(pos<url.length()-1) {
                parameterString = url.substring(pos+1);
                url = url.substring(0,pos+1);
            }
            result = url+replaceParameters(parameterString,replaceParameterString,parameterDelimiter);
        }else {
            result = url+"?"+replaceParameterString;
        }
        if(bLogging) LOG.debug("Return: "+result);
        return result;
    }
    public static String replaceParameters(String parameterString, String replaceParameterString) {
        return replaceParameters(parameterString,replaceParameterString,'&');
    }
    public static String replaceParameters(String parameterString, String replaceParameterString,char parameterDelimiter) {
        if(bLogging) LOG.debug("Got: "+parameterString);
        StringTokenizer tokenString = new StringTokenizer(replaceParameterString,""+parameterDelimiter);
        while(tokenString.hasMoreElements()) {
            String parameterValue = (String) tokenString.nextElement();
            int pos = parameterValue.indexOf('=');
            if(pos>=0) {
                String value = "";
                if(pos<parameterValue.length()-1) {
                    value = parameterValue.substring(pos+1);
                }
                if(StringUtil.asNull(value)!=null) {
                    parameterString = replaceParameter(parameterString,parameterValue.substring(0,pos),value,parameterDelimiter);
                }else {
                    parameterString = removeParameter(parameterString,parameterValue.substring(0,pos),parameterDelimiter);
                }
            }
        }
        if(bLogging) LOG.debug("Return: "+parameterString);
        return parameterString;
    }
    public static String replaceParameter(String parameterString,String parameter,String value) {
        return replaceParameter(parameterString,parameter,value,'&');
    }
    public static String replaceParameter(String parameterString,String parameter,String value,char parameterDelimiter) {
        if(bLogging) LOG.debug("replaceParameter: "+parameter+"="+value);
        StorageInterface storage = new StringStorage(parameterString);
        ParameterValueStorageInterface parameters = new ParameterStorageParameterString(storage,null,parameterDelimiter);
        parameters.setParameter(parameter,value);
        return storage.load();
    }

    public static String removeParameter(String parameterString, String parameter) {
        return removeParameter(parameterString,parameter,'&');
    }
    public static String removeParameter(String parameterString, String parameter,char parameterDelimiter) {
        if(bLogging) LOG.debug("removeParameter: "+parameter);
        StorageInterface storage = new StringStorage(parameterString);
        ParameterValueStorageInterface parameters = new ParameterStorageParameterString(storage,null,parameterDelimiter);
        parameters.delParameter(parameter);
        return storage.load();
    }

    public static String getParameter(String parameterString, String parameter) {
        return getParameter(parameterString,parameter,'&');
    }
    public static String getParameter(String parameterString, String parameter,char parameterDelimiter) {
        StorageInterface storage = new StringStorage(parameterString);
        ParameterValueStorageInterface parameters = new ParameterStorageParameterString(storage,null,parameterDelimiter);
        String result = parameters.getParameter(parameter);
        if(bLogging) LOG.debug("getParameter: "+parameter+"="+result);
        return result;
    }
}