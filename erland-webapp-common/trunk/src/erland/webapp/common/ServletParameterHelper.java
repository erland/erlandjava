package erland.webapp.common;

import javax.servlet.http.HttpServletRequest;

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
    public static Boolean asBoolean(String value) {
        Boolean booleanValue = Boolean.FALSE;
        if(value!=null && value.equalsIgnoreCase("true")) {
            booleanValue = Boolean.TRUE;
        }
        return booleanValue;
    }

    public static Integer asInteger(String value) {
        Integer integerValue = null;
        if(value!=null && value.length()>0) {
            integerValue = Integer.valueOf(value);
        }
        return integerValue;
    }

    public static Long asLong(String value) {
        Long longValue = null;
        if(value!=null && value.length()>0) {
            longValue = Long.valueOf(value);
        }
        return longValue;
    }

    public static Double asDouble(String value) {
        Double doubleValue = null;
        if(value!=null && value.length()>0) {
            doubleValue = Double.valueOf(value);
        }
        return doubleValue;
    }

    public static Float asFloat(String value) {
        Float floatValue = null;
        if(value!=null && value.length()>0) {
            floatValue = Float.valueOf(value);
        }
        return floatValue;
    }
}