package erland.util;

/**
 * A helper class for various String and StringBuffer methods
 * @author Erland Isaksson
 */
public class StringUtil {
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
}
