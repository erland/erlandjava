package erland.util;
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

import org.apache.commons.logging.LogFactory;

import java.util.Hashtable;
import java.util.Calendar;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

/**
 * Logging class which makes it possible to activate/deactivate logging
 * @author Erland Isaksson
 * @deprecated You should use commons-logging classes instead
 */
public abstract class Log
{
    /** Constant for DEBUG logging level */
    public static int DEBUG = 10;
    /** Constant for INFORMATION logging level */
    public static int INFORMATION = 5;
    /** Constant for WARNING logging level */
    public static int WARNING = 3;
    /** Constant for ERROR logging level */
    public static int ERROR = 1;

	/** Indicates if logging is enabled or not */
	private static boolean enabled=false;
    /** Indicates that logging is enabled in all files */
    private static boolean allEnabled=false;
	/** Hash table with all parts with logging enabled */
	private static Hashtable configTable = null;
    /** Indicates that timestamps should be logged */
    private static boolean bTimestamp = false;
    /** Indicates that classnames should be logged */
    private static boolean bClassname = false;
    /** Indicates that row numbers should be logged */
    private static boolean bRowNumber = false;
    /** Indicates that class instances should be logged */
    private static boolean bClassinstance = false;
    /** Indicates that thread id should be logged */
    private static boolean bThreadId = false;
    /** Indicates that common-loggings should be used for logging
     * instead of this package */
    private static boolean bCommonsLogging = false;
    /** Calendar object used if timestamps should be shown */
    private static Calendar cal = Calendar.getInstance();
    /** Date format to use when logging timestamps */
    private static DateFormat dateFormat = new SimpleDateFormat();

	/**
	 * Enabled or disable logging everywhere
	 * @param active Indicates if logging should be enable
	 */
	public static void setLog(final boolean active)
	{
		enabled=active;
        allEnabled=active;
	}

    /**
     * Checks if logging is enabled on the specified object.
     * This method can be used if you want to avoid preparing
     * a log string if logging is not enabled
     * @param obj Object to check if logging is enabled on
     */
    public static boolean isEnabled(final Object obj)
    {
        return isEnabled(obj,Log.ERROR);
    }

    /**
     * Checks if logging is enabled on the specified object.
     * This method can be used if you want to avoid preparing
     * a log string if logging is not enabled
     * @param obj Object to check if logging is enabled on
     * @param level The logging level to check
     */
    public static boolean isEnabled(final Object obj, final int level)
    {
        if(!bCommonsLogging) {
            if(enabled) {
                boolean bLog = false;
                if(allEnabled) {
                    bLog = true;
                }else if(configTable==null) {
                    bLog = false;
                }else {
                    Integer logLevel = (Integer)configTable.get(obj.getClass().getName().intern());
                    if(logLevel!=null && (logLevel.intValue()==0 || logLevel.intValue()>=level)) {
                        bLog=true;
                    }else {
                        logLevel = (Integer)configTable.get(obj.getClass().getSuperclass().getName().intern());
                        if(logLevel!=null && (logLevel.intValue()==0 || logLevel.intValue()>=level)) {
                            bLog=true;
                        }
                    }
                    /*getPackage() is not part of the classes when running IE5.5 and an applet
                    else if(configTable.containsKey(obj.getClass().getPackage().getName().intern())) {
                        bLog=true;
                    }*/

                }
                return bLog;
            }else {
                return false;
            }
        }else {
            org.apache.commons.logging.Log log = LogFactory.getLog(obj.getClass());
            if(level<INFORMATION) {
                return log.isInfoEnabled();
            }else if(level==INFORMATION) {
                return log.isDebugEnabled();
            }else {
                return log.isTraceEnabled();
            }
        }
    }

	/**
	 * Enabled or disable logging by using a configuration string
	 * @param logFile Configuration file with instruction of
	 * which parts that should be logged. A file like below would
	 * enable logging in erland.util.XMLNode and erland.util.ParameterStorage
	 * classes:
	 * <br>
	 * <br>&lt;log&gt;
	 * <br>&lt;logitem1&gt;erland.util.XMLNode&lt;/logitem1&gt;
     * <br>&lt;logitem2&gt;erland.util.ParameterStorage&lt;/logitem2&gt;
     * <br>&lt;logitemlevel2&gt;4&lt;/logitemlevel2&gt;
	 * <br>&lt;/log&gt;
	 * <br>
	 *
	 */
	public static void setLog(final String logFile)
	{
		FileStorage storage = new FileStorage(logFile);
        ParameterStorageString config = new ParameterStorageString(storage,null,"log");
        setLogConfig(config);
	}

    /**
     * Enabled or disable logging by using a configuration string
     * @param config Configuration storage with instruction of
     * which parts that should be logged. A string like below would
     * enable logging in erland.util.XMLNode and erland.util.ParameterStorage
     * classes:
     * <br>
     * <br>&lt;log&gt;
     * <br>&lt;logitem1&gt;erland.util.XMLNode&lt;/logitem1&gt;
     * <br>&lt;logitem2&gt;erland.util.ParameterStorage&lt;/logitem2&gt;
     * <br>&lt;logitemlevel2&gt;4&lt;/logitemlevel2&gt;
     * <br>&lt;/log&gt;
     * <br>
     *
     */
    public static void setLogConfig(final ParameterValueStorageInterface config) {
        if(configTable==null) {
            configTable = new Hashtable();
        }else {
            configTable.clear();
        }

        int i=1;
        String timestamp = config.getParameter("timestamp");
        if(timestamp!=null && timestamp.equalsIgnoreCase("true")) {
            bTimestamp = true;
        }
        String classname = config.getParameter("classname");
        if(classname!=null && classname.equalsIgnoreCase("true")) {
            bClassname = true;
        }
        String rownumber = config.getParameter("rownumber");
        if(rownumber!=null && rownumber.equalsIgnoreCase("true")) {
            bRowNumber = true;
        }
        String timestampFormat = config.getParameter("timestampformat");
        if(timestampFormat!=null && timestampFormat.length()>0) {
            dateFormat = new SimpleDateFormat(timestampFormat);
        }
        String classinstance = config.getParameter("classinstance");
        if(classinstance!=null && classinstance.equals("true")) {
            bClassinstance = true;
        }
        String threadid = config.getParameter("threadid");
        if(threadid!=null && threadid.equals("true")) {
            bThreadId = true;
        }
        String commonsLogging = config.getParameter("commons-logging");
        if(commonsLogging!=null && commonsLogging.equals("true")) {
            bCommonsLogging = true;
        }
        if(!bCommonsLogging) {
            String item = config.getParameter("logitem"+i);
            String itemlevelstr = config.getParameter("logitemlevel"+i++);
            while(item!=null && item.length()>0) {
                int itemlevel = 0;
                if(itemlevelstr!=null) {
                    try {
                        itemlevel = Integer.valueOf(itemlevelstr).intValue();
                    } catch (NumberFormatException e) {
                    }
                }
                System.out.println("Logging enabled on: " + item + " ,level="+itemlevel);
                configTable.put(item.intern(),new Integer(itemlevel));
                item = config.getParameter("logitem"+i);
                itemlevelstr = config.getParameter("logitemlevel"+i++);
            }
            if(configTable.size()==0) {
                configTable=null;
            }
            enabled=true;
            allEnabled=false;
        }
    }

	/**
	 * Log a text string if logging is enabled or
	 * just return without doing anything if logging is
	 * disabled 
	 * @param obj The object in which the logging occurred
	 * @param logString The string that should be logged
	 */
	public static void println(final Object obj, final String logString) {
        println(obj,logString,1);
	}


    /**
	 * Log a text string if logging is enabled or
	 * just return without doing anything if logging is
	 * disabled
	 * @param obj The object in which the logging occurred
     * @param logString The string that should be logged
     * @param level The log level of this log string
	 */
	public synchronized static void println(final Object obj, final String logString, final int level) {
        if(!bCommonsLogging) {
            if(isEnabled(obj,level)) {
                if(bTimestamp) {
                    cal.setTimeInMillis(System.currentTimeMillis());
                    System.out.print(dateFormat.format(cal.getTime())+" ");
                }
                if(bThreadId) {
                    System.out.println(Thread.currentThread().getName());
                }
                if(bClassinstance) {
                    System.out.print(obj.getClass().getName()+"@"+Integer.toHexString(obj.hashCode()));
                }else if(bClassname) {
                    System.out.print(obj.getClass().getName()+" ");
                }
                if(bRowNumber) {
                    StackTraceElement[] stack = new Throwable().getStackTrace();
                    if(stack.length>0) {
                        int pos = -1;
                        for(int i=0;i<stack.length;i++) {
                            if(!stack[i].getClassName().equals(Log.class.getName())) {
                                pos = i;
                                break;
                            }
                        }
                        if(pos>=0) {
                            System.out.print("("+stack[pos].getClassName()+":"+stack[pos].getLineNumber()+") ");
                        }
                    }
                }
                System.out.println(logString);
            }
        }else {
            org.apache.commons.logging.Log log = LogFactory.getLog(obj.getClass());
            if(level<INFORMATION) {
                log.info(logString);
            }else if(level==INFORMATION) {
                log.debug(logString);
            }else {
                log.trace(logString);
            }
        }
	}

	/**
	 * Log a object if logging is enabled or
	 * just return without doing anything if logging is
	 * disabled 
	 * @param obj The object in which the logging occurred
	 * @param logObject The object that should be logged the toString() method will be called
	 */
	public static void println(final Object obj, final Object logObject) {
		Log.println(obj,logObject.toString(),1);
	}


	/**
	 * Log a object if logging is enabled or
	 * just return without doing anything if logging is
	 * disabled
	 * @param obj The object in which the logging occurred
	 * @param logObject The object that should be logged the toString() method will be called
     * @param level The log level of this log string
	 */
	public static void println(final Object obj, final Object logObject, final int level) {
		Log.println(obj,logObject.toString(),level);
	}
}