package erland.util;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Field;
import java.util.*;
import java.util.regex.Pattern;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.text.DateFormat;

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

/**
 * A helper class for various String and StringBuffer methods
 * @author Erland Isaksson
 */
public class StringUtil {
    /** Default date format to use in string conversion methods */
    private final static DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

    /**
     * Converts a number to a String with specified length, if the number is
     * shorter than the specified length it will be filled out with 0 in the
     * beginning
     * @param number The number to convert
     * @param length The string length requested
     * @return The number converted to a String object
     */
    public static String getFixLength(long number, int length) {
        StringBuffer sb = new StringBuffer(length);
        addFixLength(sb,number,length);
        return sb.toString();
    }

    /**
     * Converts a number to string with a specified length and append it to
     * a StringBuffer, if the number is shorter than the specified length it
     * will be filled out with 0 in the beginning
     * @param sb The StringBuffer to append the converted number to
     * @param number The number to convert
     * @param length The string length requested
     */
    public static void addFixLength(StringBuffer sb, long number, int length) {
        int maxNumber=1;
        for(int i=1;i<length;i++) {
            maxNumber*=10;
            if(number<maxNumber) {
                sb.append('0');
            }
        }
        sb.append(number);
    }

    /**
     * Converts a String to a String with specified length, if the string is
     * shorter you can choose if it should be filled with spaces in the end or in
     * the beginning
     * @param str The string to convert
     * @param length The string length requested
     * @param fillAtEnd Indicates that the string should be filled at end. True = filled at end, False = filled at begining
     * @return The number converted to a String object
     */
    public static String getFixLength(String str, int length, boolean fillAtEnd) {
        StringBuffer sb = new StringBuffer(length);
        addFixLength(sb,str,length,fillAtEnd);
        return sb.toString();
    }

    /**
     * Converts a String to a String with specified length and append it to a
     * StringBuffer object, if the string is shorter you can choose if it should
     * be filled with spaces in the end or in the beginning
     * @param sb The StringBuffer to append the string to
     * @param str The string to convert
     * @param length The string length requested
     * @param fillAtEnd Indicates that the string should be filled at end. True = filled at end, False = filled at begining
     */
    public static void addFixLength(StringBuffer sb, String str, int length, boolean fillAtEnd) {
        int curPos=0;
        int lastPos=0;
        if(str!=null) {
            if(fillAtEnd) {
                sb.append(str);
                curPos = str.length();
                lastPos=length;
            }else {
                lastPos=length-str.length();
            }
        }else {
            lastPos=length;
        }
        for(int i=curPos;i<lastPos;i++) {
            sb.append(' ');
        }
        if(!fillAtEnd && str!=null) {
            sb.append(str);
        }
    }

    /**
     * Converts a string to a number
     * @param str The string containing the number
     * @return The number created from the string
     */
    public static long getNumber(String str) {
        if(str!=null) {
            return getNumber(str,0,str.length());
        }else {
            return 0;
        }
    }

    /**
     * Converts a sub string between a start and end position to a number
     * @param str The string containg the number
     * @param beginIndex The start position of the sub string
     * @param endIndex The end position of the sub string
     * @return The number created from the sub string
     */
    public static long getNumber(String str, int beginIndex, int endIndex) {
        if(str!=null && endIndex>beginIndex) {
            int value=0;
            int multiplier=1;
            for(int i=endIndex-1;i>=beginIndex;i--,multiplier*=10) {
                value += (str.charAt(i)-'0')*multiplier;
            }
            return value;
        }else {
            return 0;
        }
    }

    /**
     * Converts a string to a number
     * @param sb The StringBuffer containing the number
     * @return The number created from the string
     */
    public static long getNumber(StringBuffer sb) {
        if(sb!=null) {
            return getNumber(sb,0,sb.length());
        }else {
            return 0;
        }
    }

    /**
     * Converts a sub string between a start and end position to a number
     * @param sb The StringBuffer containg the number
     * @param beginIndex The start position of the sub string
     * @param endIndex The end position of the sub string
     * @return The number created from the sub string
     */
    public static long getNumber(StringBuffer sb, int beginIndex, int endIndex) {
        if(sb!=null && endIndex>beginIndex) {
            int value=0;
            int multiplier=1;
            for(int i=endIndex-1;i>=beginIndex;i--,multiplier*=10) {
                value += (sb.charAt(i)-'0')*multiplier;
            }
            return value;
        }else {
            return 0;
        }
    }

    /**
     * Replace all occurrances of a specifiec character in a StringBuffer with
     * another character
     * @param sb The StringBuffer containing the string
     * @param oldChar The character to replace
     * @param newChar The new character to insert
     * @return The StringBuffer object sent as in-parameter sb
     */
    public static StringBuffer replaceChar(StringBuffer sb, char oldChar, char newChar) {
        for(int i=0;i<sb.length();i++) {
            if(sb.charAt(i)==oldChar) {
                sb.setCharAt(i,newChar);
            }
        }
        return sb;
    }

    /**
     * Removes space and newline characters in the beginning and end of a
     * StringBuffer
     * @param sb StringBuffer to trim
     * @return The StringBuffer object sent as in-parameter sb
     */
    public static StringBuffer trim(StringBuffer sb) {
        int endPos = sb.length();
        int startPos = 0;
        for(startPos=0;startPos<endPos;startPos++) {
            if(sb.charAt(startPos)>'\u0020') {
                break;
            }
        }
        for(endPos--;endPos>startPos;endPos--) {
            if(sb.charAt(endPos)>'\u0020') {
                break;
            }
        }
        sb.setLength(endPos+1);
        sb.delete(0,startPos);
        return sb;
    }

    /**
     * Returns null if string is null or if String.trim().length()==0
     * @param str The string to check
     * @return null if str is null or if str.trim().length()==0, else unmodified str
     */
    public static String asNull(String str) {
        if(str!=null && str.trim().length()==0) {
            str=null;
        }
        return str;
    }
    /**
     * Returns "" if string is null
     * @param str The string to check
     * @return "" if str is null else unmodified str
     */
    public static String asEmpty(String str) {
        if(str==null) {
            str="";
        }
        return str;
    }

    /**
     * Generates a string with all properties of an object which has a getXXX or isXXX method
     * In the case that the methods return a value that is not a String it will be converted to a
     * String by using the toString() method of the object
     * @param object The object to print
     * @param exclude A regular expression, if a getXXX or isXXX method matches this it will not be printed
     * @param excludeFrom If the getXXX or isXXX method is declared in this class or a subclass of this class it will not be printed
     * @param shortNames Indicates that short class names should be used instead of full names which include the package
     * @return A string like MyClass1(attr1=value1,attr2=MyClass2[5],attr3=List(5))
     */
    public static String beanToString(Object object, String exclude, Class excludeFrom, boolean shortNames) {
        StringBuffer sb = new StringBuffer(500);
        if(shortNames) {
            if(object instanceof Object[]) {
                int start = object.getClass().getComponentType().getPackage().getName().length();
                sb.append(object.getClass().getComponentType().getName().substring(start+1));
                sb.append("[");
                sb.append(((Object[])object).length);
                sb.append("]");
            }else {
                int start = object.getClass().getPackage().getName().length();
                sb.append(object.getClass().getName().substring(start+1));
            }
        }else {
            if(object instanceof Object[]) {
                sb.append(object.getClass().getComponentType().getName());
                sb.append("[");
                sb.append(((Object[])object).length);
                sb.append("]");
            }else {
                sb.append(object.getClass().getName());
            }
        }
        sb.append("(");
        Method[] methods = object.getClass().getMethods();
        boolean bFirst = true;
        for (int i = 0; i < methods.length; i++) {
            try {
                Method method = methods[i];
                String fieldName = null;
                if(method.getName().startsWith("get")) {
                    fieldName = method.getName().substring(3);
                }else if(method.getName().startsWith("is")) {
                    fieldName = method.getName().substring(2);
                }
                if(exclude!=null && Pattern.matches(exclude,method.getName())) {
                    fieldName = null;
                }

                if(fieldName!=null && Modifier.isPublic(method.getModifiers()) && method.getParameterTypes().length==0 && (excludeFrom==null || !method.getDeclaringClass().isAssignableFrom(excludeFrom))) {
                    fieldName = ""+Character.toLowerCase(fieldName.charAt(0))+(fieldName.length()>1?fieldName.substring(1):"");
                    if(!bFirst) {
                        sb.append(",");
                    }
                    bFirst = false;
                    sb.append(fieldName);
                    sb.append("=");
                    Object value = method.invoke(object,null);
                    if(value instanceof Object[]) {
                        Object[] o = (Object[]) value;
                        sb.append(o.getClass().getComponentType().getName()+"["+o.length+"]");
                    }else if(value instanceof Collection) {
                        Collection o = (Collection) value;
                        sb.append(o.getClass().getName()+"("+o.size()+")");
                    }else {
                        sb.append(value);
                    }
                }
            } catch (IllegalAccessException e1) {
                e1.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
            } catch (InvocationTargetException e1) {
                e1.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
            }
        }
        sb.append(")");
        return sb.toString();
    }

    /**
     * Generates a string with all attributes of an object
     * In the case that the methods return a value that is not a String it will be converted to a
     * String by using the toString() method of the object
     * @param object The object to print
     * @param exclude A regular expression, if an attribute name matches this it will not be printed
     * @param excludeFrom If the attribute is declared in this class or a subclass of this class it will not be printed
     * @param shortNames Indicates that short class names should be used instead of full names which include the package
     * @return A string like MyClass1(attr1=value1,attr2=MyClass2[5],attr3=List(5))
     */
    public static String objectToString(Object object, String exclude, Class excludeFrom, boolean shortNames) {
        StringBuffer sb = new StringBuffer(500);
        if(shortNames) {
            if(object instanceof Object[]) {
                int start = object.getClass().getComponentType().getPackage().getName().length();
                sb.append(object.getClass().getComponentType().getName().substring(start+1));
                sb.append("[");
                sb.append(((Object[])object).length);
                sb.append("]");
            }else {
                int start = object.getClass().getPackage().getName().length();
                sb.append(object.getClass().getName().substring(start+1));
            }
        }else {
            if(object instanceof Object[]) {
                sb.append(object.getClass().getComponentType().getName());
                sb.append("[");
                sb.append(((Object[])object).length);
                sb.append("]");
            }else {
                sb.append(object.getClass().getName());
            }
        }
        sb.append("(");
        Class cls = object.getClass();
        while(cls!=null) {
            Field[] fields = cls.getDeclaredFields();
            boolean bFirst = true;
            for (int i = 0; i < fields.length; i++) {
                try {
                    Field field = fields[i];
                    String fieldName = field.getName();
                    if(exclude!=null && Pattern.matches(exclude,field.getName())) {
                        fieldName = null;
                    }

                    if(fieldName!=null && (excludeFrom==null || !field.getDeclaringClass().isAssignableFrom(excludeFrom))) {
                        fieldName = ""+Character.toLowerCase(fieldName.charAt(0))+(fieldName.length()>1?fieldName.substring(1):"");
                        if(!bFirst) {
                            sb.append(",");
                        }
                        bFirst = false;
                        sb.append(fieldName);
                        sb.append("=");
                        field.setAccessible(true);
                        Object value = field.get(object);
                        if(value instanceof Object[]) {
                            Object[] o = (Object[]) value;
                            sb.append(o.getClass().getComponentType().getName()+"["+o.length+"]");
                        }else if(value instanceof Collection) {
                            Collection o = (Collection) value;
                            sb.append(o.getClass().getName()+"("+o.size()+")");
                        }else {
                            sb.append(value);
                        }
                    }
                } catch (IllegalAccessException e1) {
                    e1.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
                }
            }
            cls = cls.getSuperclass();
        }
        sb.append(")");
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

    public static Date asDate(String value, Date defaultValue, DateFormat format) {
        Date dateValue = defaultValue;
        if(value!=null) {
            try {
                dateValue = format.parse(value);
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
    public static String asString(Date value, String defaultValue, DateFormat format) {
        return value!=null?format.format(value):defaultValue;
    }
    public static String wordWrap(String str, int width) {
        StringBuffer sb = new StringBuffer(str);

        int lastSpace = -1;
        int lineStart = 0;
        int i = 0;
        while (i < sb.length()) {
            if (Character.isWhitespace(sb.charAt(i))) {
                lastSpace = i;
            }
            if (sb.charAt(i) == '\n') {
                lastSpace = -1;
                lineStart = i + 1;
            }
            if (i > lineStart + width - 1) {
                if (lastSpace != -1) {
                    sb.setCharAt(lastSpace, '\n');
                    lineStart = lastSpace + 1;
                    lastSpace = -1;
                } else {
                    sb.insert(i, '\n');
                    lineStart = i + 1;
                }
            }
            i++;
        }
        return sb.toString();
    }
}
