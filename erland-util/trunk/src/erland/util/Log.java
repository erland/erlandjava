package erland.util;

import java.util.Hashtable;

/**
 * Logging class which makes it possible to activate/deactivate logging
 */
public abstract class Log 
{
	/** Indicates if logging is enabled or not */
	protected static boolean enabled=false;
    /** Indicates that logging is enabled in all files */
    protected static boolean allEnabled=false;
	/** Hash table with all parts with logging enabled */
	protected static Hashtable configTable = null;
	
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
        return isEnabled(obj,1);
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
    }

	/**
	 * Enabled or disable logging by using a configuration string
	 * @param logConfig Configuration string with instruction of
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
	public static void setLog(final String logConfig)
	{
		FileStorage storage = new FileStorage(logConfig);
        ParameterStorageString config = new ParameterStorageString(storage,"log",false);
		if(configTable==null) {
			configTable = new Hashtable();
		}

		int i=1;
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
	public static void println(final Object obj, final String logString, final int level) {
        if(isEnabled(obj,level)) {
            System.out.println(logString);
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