package erland.util;

import java.util.Hashtable;

/**
 * Logging class which makes it possible to activate/deactivate logging
 */
public abstract class Log 
{
	/** Indicates if logging is enabled or not */
	protected static boolean enabled=false;
	/** Hash table with all parts with logging enabled */
	protected static Hashtable configTable = null;
	
	/** 
	 * Enabled or disable logging everywhere
	 * @param active Indicates if logging should be enable
	 */
	public static void setLog(final boolean active)
	{
		enabled=active;
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
	 * <br>&lt;logitem2&gt;erland.util.ParameterStorage&lt;/logitem1&gt;
	 * <br>&lt;/log&gt;
	 * <br>
	 *
	 */
	public static void setLog(final String logConfig)
	{
		ParameterStorageString config = new ParameterStorageString(logConfig,"log");
		if(configTable==null) {
			configTable = new Hashtable();
		}

		int i=1;
		String item = config.getParameter("logitem"+i++);
		while(item!=null && item.length()>0) {
			System.out.println("Logging enabled on: " + item);
			configTable.put(item.intern(),item.intern());
			item = config.getParameter("logitem"+i++);
		}
		if(configTable.size()==0) {
			configTable=null;
		}
		enabled=true;
	}

	
	/**
	 * Log a text string if logging is enabled or
	 * just return without doing anything if logging is
	 * disabled 
	 * @param obj The object in which the logging occurred
	 * @param logString The string that should be logged
	 */
	public static void println(final Object obj, final String logString) {
		if(enabled) {
			boolean bLog = false;
			if(configTable==null) {
				bLog = true;
			}else {
				if(configTable.containsKey(obj.getClass().getName().intern())) {
					bLog=true;
				}else if(configTable.containsKey(obj.getClass().getSuperclass().getName().intern())) {
					bLog=true;
				}
				/*getPackage() is not part of the classes when running IE5.5 and an applet
				else if(configTable.containsKey(obj.getClass().getPackage().getName().intern())) {
					bLog=true;
				}*/

			}
			if(bLog) {
				System.out.println(logString);
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
		Log.println(obj,logObject.toString());
	}
}