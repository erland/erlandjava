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

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.util.Iterator;
import java.util.Set;
import java.util.StringTokenizer;
import java.util.HashSet;


/**
 * Get, set or delete parameters stored in a parameter string
 * @author Erland Isaksson
 */
public class ParameterStorageParameterString
	implements ParameterValueStorageExInterface
{
    /** Logging instance */
    private static Log LOG = LogFactory.getLog(ParameterStorageParameterString.class);

	/**
	 * The in-memory representation of the file where the parameters
	 * are stored
	 */
    private StringBuffer primaryData;
    private StringBuffer secondaryData;

    /** String that separates two parameters */
    private char parameterDelimiter;

    /** Set with parameters allowed in primary storage */
    private Set primaryParameters = null;

    /** Set with parameters allowed in secondary storage */
    private Set secondaryParameters = null;

	/** Storage which parameters are accessed in */
	private StorageInterface primaryStorage;

    /** Indicates that this ParameterStorageString is just a part of a bigger ParameterValueStorageInterface object */
    private boolean bLogging;

    /**
     * Createa a storage object
     * @param primaryStorage The storage object which the parameters should be read/write from/to
     * @param secondaryStorage The storage object which the parameters should be read from if they don't exist in the primaryStorage
     * @param parameterDelimiter The character that separates two parameters
     */
	public ParameterStorageParameterString(StorageInterface primaryStorage, StorageInterface secondaryStorage, char parameterDelimiter)
	{
		init(primaryStorage,secondaryStorage,parameterDelimiter,null,null);
	}

    /**
     * Createa a storage object
     * @param primaryStorage The storage object which the parameters should be read/write from/to
     * @param secondaryStorage The storage object which the parameters should be read from if they don't exist in the primaryStorage
     * @param parameterDelimiter The character that separates two parameters
     * @param primaryStorageParameters A comma separated list with parameters allowed in primaryStorage, null means that all parameters are allowed
     * @param secondaryStorageParameters comma separated list with parameters allowed in secondaryStorage, null means that all parameters are allowed
     */
	public ParameterStorageParameterString(StorageInterface primaryStorage, StorageInterface secondaryStorage, char parameterDelimiter, String primaryStorageParameters, String secondaryStorageParameters)
	{
		init(primaryStorage,secondaryStorage,parameterDelimiter, primaryStorageParameters,secondaryStorageParameters);
	}

	/**
	 * Initialize this object
     * @param primaryStorage The storage object which the parameters should be read/write from/to
     * @param secondaryStorage The storage object which the parameters should be read from if they don't exist in the primaryStorage
     * @param parameterDelimiter The character that separates two parameters
     * @param primaryStorageParameters A comma separated list with parameters allowed in primaryStorage, null means that all parameters are allowed
     * @param secondaryStorageParameters comma separated list with parameters allowed in secondaryStorage, null means that all parameters are allowed
	 */
	protected void init(StorageInterface primaryStorage, StorageInterface secondaryStorage, char parameterDelimiter, String primaryStorageParameters, String secondaryStorageParameters)
	{
        bLogging = LOG.isDebugEnabled();
        this.primaryStorage = primaryStorage;
        if(primaryStorage!=null) {
            if(bLogging) LOG.debug("PrimaryStorage = "+primaryStorage);
            this.primaryData = getData(primaryStorage);
            if(primaryData!=null) {
                if(bLogging) LOG.debug("PrimaryData = "+primaryData.getClass().getName()+"@"+Integer.toHexString(primaryData.hashCode()));
            }else {
                if(bLogging) LOG.debug("PrimaryData = null");
            }
        }
        if(secondaryStorage!=null && secondaryStorage!=primaryStorage) {
            if(bLogging) LOG.debug("SecondaryStorage = "+secondaryStorage);
            this.secondaryData = getData(secondaryStorage);
            if(secondaryData!=null) {
                if(bLogging) LOG.debug("SecondaryData = "+secondaryData.getClass().getName()+"@"+Integer.toHexString(secondaryData.hashCode()));
            }else {
                if(bLogging) LOG.debug("SecondaryData = null");
            }
        }
        this.parameterDelimiter = parameterDelimiter;
        if(primaryStorageParameters!=null) {
            primaryParameters = new HashSet();
            StringTokenizer tokens = new StringTokenizer(primaryStorageParameters,""+parameterDelimiter);
            while(tokens.hasMoreElements()) {
                String token = (String) tokens.nextElement();
                if(StringUtil.asNull(token)!=null) {
                    primaryParameters.add(token);
                }
            }
        }
        if(secondaryStorageParameters!=null) {
            secondaryParameters = new HashSet();
            StringTokenizer tokens = new StringTokenizer(secondaryStorageParameters,""+parameterDelimiter);
            while(tokens.hasMoreElements()) {
                String token = (String) tokens.nextElement();
                if(StringUtil.asNull(token)!=null) {
                    secondaryParameters.add(token);
                }
            }
        }
	}

    /**
     * Get a StringBuffer object for a storage
     * @param storage The storage object to get StringBuffer object for
     * @return The StringBuffer object representing the storage object
     */
    private StringBuffer getData(StorageInterface storage) {
        String dataString = storage.load();
        if(dataString!=null && dataString.length()>0) {
            return new StringBuffer(dataString);
        }else {
            return null;
        }
    }


	/**
	 * Check if this parameter should be special handled, will be autmatically called
	 * when a parameter is accessed. Implement this in your sub class if you want to handle some
	 * parameters in a special way.
	 * @param name The parameter name
	 * @return Always false
	 *         <pre>true - This parameter should be special handled,then one of 
	 *         {@link #getSpecialParameterInData(StringBuffer,String)}, {@link #setSpecialParameterInData(StringBuffer,String,String)}, {@link #delSpecialParameterInData(StringBuffer,String)},
	 *         will be called to access the parameter</pre>
	 *         <pre>false - This parameter is handled in the normal way, one of
	 *         {@link #getParameter(String)}, {@link #setParameter(String,String)}, {@link #delParameter(String)}, 
	 *         will be called to access the parameter</pre>
	 */
	protected boolean isSpecialHandled(String name)
	{
		return false;
	}

    /**
     * Gets the parameter value for a parameter from the StringBuffer specified,
     * this method is only called if {@link #isSpecialHandled(String)} returns true
     * @param data The StringBuffer to search for the parameter in
     * @param name The parameter name of the parameter
     * @return The value of the parameter
     */
    protected String getSpecialParameterInData(StringBuffer data, String name) {
        return null;
        // Not needed, implemented in sub classes if required
    }

    /**
     * Gets a storage object for a parameter from the StringBuffer specified,
     * this method is only called if {@link #isSpecialHandled(String)} returns true
     * @param data The StringBuffer to search for the parameter in
     * @param name The parameter name of the parameter
     * @return The storage object for the parameter
     */
    protected StorageInterface getSpecialParameterAsStorageInData(StringBuffer data, String name) {
        return null;
        // Not needed, implemented in sub classes if required
    }

	public String getParameter(String name)
	{
        String value = null;
        if(primaryParameters==null || primaryParameters.contains(name)) {
            value = getParameterInData(primaryData, name);
        }
        if(value==null || value.length()==0) {
            if(secondaryParameters==null || secondaryParameters.contains(name)) {
                value = getParameterInData(secondaryData,name);
            }
        }

        if(value==null) {
            if(bLogging) LOG.debug("getParameter "+name+" -> null");
            return null;
        }else {
            if(bLogging) LOG.debug("getParameter "+name+" -> "+value);
            return value;
        }
	}

    /**
     * Gets the parameter value for a parameter from the StringBuffer specified
     * @param data The StringBuffer to search for the parameter in
     * @param name The parameter name of the parameter
     * @return The value of the parameter
     */
    private String getParameterInData(StringBuffer data, String name) {
        if(data!=null) {
            if(bLogging) LOG.debug("getParameterInData "+data.getClass().getName()+"@"+Integer.toHexString(data.hashCode())+","+name);
            if(isSpecialHandled(name)) {
                if(bLogging) LOG.debug("getSpecialParameterInData "+data.getClass().getName()+"@"+Integer.toHexString(data.hashCode())+","+name);
                return getSpecialParameterInData(data,name);
            }else {
                String parameterString = data.toString();
                if(parameterString.startsWith(name+"=")) {
                    int startPos = (name+"=").length();
                    int pos = parameterString.indexOf(parameterDelimiter);
                    if(pos<0) {
                        return parameterString.substring(startPos);
                    }
                    return parameterString.substring(startPos,pos);
                }else {
                    int pos = parameterString.indexOf(""+parameterDelimiter+name+"=");
                    if(pos>=0) {
                        int startPos = pos + (""+parameterDelimiter+name+"=").length();
                        int endPos = parameterString.indexOf(parameterDelimiter,pos+1);
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
        return null;

    }


    /**
     * Sets the parameter value for a parameter in the StringBuffer specified,
     * this method is only called if {@link #isSpecialHandled(String)} returns true
     * @param data The StringBuffer to set the parameter in
     * @param name The parameter name of the parameter
     * @param value The parameter value of the parameter
     */
    protected void setSpecialParameterInData(StringBuffer data,String name, String value) {
        // Not needed, implemented in sub classes if required
    }

    /**
     * Sets the parameter value for a parameter in the StringBuffer specified,
     * this method is only called if {@link #isSpecialHandled(String)} returns true
     * The value is specified as a StorageInterface object
     * @param data The StringBuffer to set the parameter in
     * @param name The parameter name of the parameter
     * @param value The parameter value of the parameter
     */
    protected void setSpecialParameterAsStorageInData(StringBuffer data,String name, StorageInterface value) {
        // Not needed, implemented in sub classes if required
    }

	public void setParameter(String name, String value)
	{
        if(primaryParameters==null || primaryParameters.contains(name)) {
            primaryData = setParameterInData(primaryData,name,value);
        }
        if(secondaryParameters==null || secondaryParameters.contains(name)) {
            secondaryData = setParameterInData(secondaryData,name,value);
        }
        save();
	}

    /**
     * Sets the parameter value for a parameter in the StringBuffer specified
     * @param data The StringBuffer to set the parameter in
     * @param name The parameter name of the parameter
     * @param value The parameter value of the parameter
     */
    private StringBuffer setParameterInData(StringBuffer data,String name,String value) {
        if(data==null) {
            data = new StringBuffer();
        }
        if(bLogging) LOG.debug("setParameterInData "+data.getClass().getName()+"@"+Integer.toHexString(data.hashCode())+","+name);
        if(isSpecialHandled(name)) {
            if(bLogging) LOG.debug("setSpecialParameterInData "+data.getClass().getName()+"@"+Integer.toHexString(data.hashCode())+","+name+"="+value+" "+this.getClass());
            setSpecialParameterInData(data,name,value);
        }else {
            String parameterString = data.toString();
            int startPos = 0;
            if(parameterString.startsWith(name+"=")) {
                int pos = parameterString.indexOf(parameterDelimiter);
                if(pos<0) {
                    data.setLength(0);
                    if(StringUtil.asNull(value)!=null) {
                        data.append(name);
                        data.append("=");
                        data.append(value);
                    }
                }else {
                    if(StringUtil.asNull(value)!=null) {
                        data.replace(0,pos,name+"="+value);
                    }else {
                        data.replace(0,pos,"");
                    }
                }
                startPos = name.length()+1+value.length();
            }else {
                int pos = parameterString.indexOf(""+parameterDelimiter+name+"=");
                if(pos>=0) {
                    int endPos = parameterString.indexOf(parameterDelimiter,pos+1);
                    if(endPos<0) {
                        endPos = data.length();
                    }
                    if(StringUtil.asNull(value)!=null) {
                        data.replace(pos+1,endPos,name+"="+value);
                        startPos = pos+1+name.length()+1+value.length();
                    }else {
                        data.replace(pos+1,endPos,"");
                        startPos = pos+1;
                    }
                }else {
                    if(StringUtil.asNull(value)!=null) {
                        data.append(parameterDelimiter);
                        data.append(name);
                        data.append("=");
                        data.append(value);
                    }
                    startPos = data.length();
                }
            }
            int pos = data.indexOf(""+parameterDelimiter+name+"=",startPos);
            while(pos>=0) {
                int endPos = data.indexOf(""+parameterDelimiter,pos+1);
                if(endPos<0) {
                    endPos = data.length();
                }
                data.replace(pos,endPos,"");
                pos = data.indexOf(""+parameterDelimiter+name+"=",pos);
            }
        }
        return data;
    }


    /**
     * Delets a parameter in the StringBuffer specified,
     * this method is only called if {@link #isSpecialHandled(String)} returns true
     * @param data The StringBuffer to set the parameter in
     * @param name The parameter name of the parameter
     */
    protected void delSpecialParameterInData(StringBuffer data, String name) {
        // Not needed, implemented in sub classes if required
    }

	public void delParameter(String name)
	{
        if(primaryParameters==null || primaryParameters.contains(name)) {
            delParameterInData(primaryData,name);
        }
        if(secondaryParameters==null || secondaryParameters.contains(name)) {
            delParameterInData(secondaryData,name);
        }
        save();
	}
    /**
     * Delets a parameter in the StringBuffer specified
     * @param data The StringBuffer to set the parameter in
     * @param name The parameter name of the parameter
     */
    private void delParameterInData(StringBuffer data,String name) {
        if(data!=null) {
            if(bLogging) LOG.debug("delParameterInData "+data.getClass().getName()+"@"+Integer.toHexString(data.hashCode())+","+name);
            if(isSpecialHandled(name)) {
                if(bLogging) LOG.debug("delSpecialParameterInData "+data.getClass().getName()+"@"+Integer.toHexString(data.hashCode())+","+name);
                delSpecialParameterInData(data,name);
                return;
            }else {
                String parameterString = data.toString();
                int startPos = 0;
                if(parameterString.startsWith(name+"=")) {
                    int pos = parameterString.indexOf(parameterDelimiter);
                    if(pos<0) {
                        data.setLength(0);
                    }else {
                        data.replace(0,pos,"");
                    }
                    startPos = 0;
                }else {
                    int pos = parameterString.indexOf(""+parameterDelimiter+name+"=");
                    if(pos>=0) {
                        int endPos = parameterString.indexOf(parameterDelimiter,pos+1);
                        if(endPos<0) {
                            endPos = data.length();
                        }
                        data.replace(pos+1,endPos,"");
                        startPos = pos+1;
                    }else {
                        startPos = data.length();
                    }
                }
                int pos = data.indexOf(""+parameterDelimiter+name+"=",startPos);
                while(pos>=0) {
                    int endPos = data.indexOf(""+parameterDelimiter,pos+1);
                    if(endPos<0) {
                        endPos = data.length();
                    }
                    data.replace(pos,endPos,"");
                    pos = data.indexOf(""+parameterDelimiter+name+"=",pos);
                }
                while(data.charAt(data.length()-1)==parameterDelimiter) {
                    data.setLength(data.length()-1);
                }
            }
        }
    }

	/**
	 * Save the parameters in {@link #primaryData data} to a storage.
	 * This will be automatically called in all the parameter access methods
	 */
	private void save()
	{
		primaryStorage.save(primaryData.toString());
	}

    public String toString()
    {
        if(primaryData!=null) {
            return primaryData.toString();
        }else {
            return secondaryData.toString();
        }
    }

    public StorageInterface getParameterAsStorage(String name) {
        String value = null;
        if(primaryParameters==null || primaryParameters.contains(name)) {
            value = getParameterInData(primaryData, name);
        }
        StorageInterface storage=null;
        if(value!=null && value.length()>0) {
            storage = getParameterAsStorageInData(primaryData,name);
        }else {
            if(secondaryParameters==null || secondaryParameters.contains(name)) {
                storage = getParameterAsStorageInData(secondaryData,name);
            }
        }
        if(storage!=null) {
            if(bLogging) LOG.debug("getParameterAsStorage "+name+" -> "+storage.getClass().getName()+"@"+Integer.toHexString(storage.hashCode()));
        }else {
            if(bLogging) LOG.debug("getParameterAsStorage "+name+" -> null");
        }
        return storage;
    }

    /**
     * Gets a storage object for a parameter from the StringBuffer specified
     * @param data The StringBuffer to search for the parameter in
     * @param name The parameter name of the parameter
     * @return The storage object for the parameter
     */
    private StorageInterface getParameterAsStorageInData(StringBuffer data, String name) {
        if(data!=null) {
            if(bLogging) LOG.debug("getParameterAsStorageInData "+data.getClass().getName()+"@"+Integer.toHexString(data.hashCode())+","+name);
            if(isSpecialHandled(name)) {
                if(bLogging) LOG.debug("getSpecialParameterAsStorageInData "+data.getClass().getName()+"@"+Integer.toHexString(data.hashCode())+","+name);
                return getSpecialParameterAsStorageInData(data,name);
            }else {
                String value = getParameterInData(data,name);
                if(StringUtil.asNull(value)!=null) {
                    return new StringStorage(value);
                }
            }
		}
        return null;
    }

    /**
     * Sets the parameter value from a StorageInterface object for a parameter
     * in the StringBuffer specified
     * @param data The StringBuffer to set the parameter in
     * @param name The parameter name of the parameter
     * @param value The parameter value of the parameter
     */
    private StringBuffer setParameterAsStorageInData(StringBuffer data,String name,StorageInterface value) {
        if(data==null) {
            data = new StringBuffer();
        }
        if(bLogging) LOG.debug("setParameterAsStorageInData "+data.getClass().getName()+"@"+Integer.toHexString(data.hashCode())+","+name);
        if(isSpecialHandled(name)) {
            if(bLogging) LOG.debug("setSpecialParameterAsStorageInData "+data.getClass().getName()+"@"+Integer.toHexString(data.hashCode())+","+name+"="+value+" "+this.getClass());
            setSpecialParameterAsStorageInData(data,name,value);
        }else {
            setParameterInData(data,name,value.load());
        }
        return data;
    }

    public void setParameterAsStorage(String name, StorageInterface value) {
        if(primaryParameters==null || primaryParameters.contains(name)) {
            primaryData = setParameterAsStorageInData(primaryData,name,value);
        }
        if(secondaryParameters==null || secondaryParameters.contains(name)) {
            secondaryData = setParameterAsStorageInData(secondaryData,name,value);
        }
        save();
    }
}
