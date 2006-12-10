package erland.webapp.datacollection.ws;

import java.rmi.RemoteException;

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

public class DataCollectionSoapBindingSkeleton implements erland.webapp.datacollection.ws.DataCollection, org.apache.axis.wsdl.Skeleton {
    private erland.webapp.datacollection.ws.DataCollection impl;
    private static java.util.Map _myOperations = new java.util.Hashtable();
    private static java.util.Collection _myOperationsList = new java.util.ArrayList();

    /**
    * Returns List of OperationDesc objects with this name
    */
    public static java.util.List getOperationDescByName(java.lang.String methodName) {
        return (java.util.List)_myOperations.get(methodName);
    }

    /**
    * Returns Collection of OperationDescs
    */
    public static java.util.Collection getOperationDescs() {
        return _myOperationsList;
    }

    static {
        org.apache.axis.description.OperationDesc _oper;
        org.apache.axis.description.FaultDesc _fault;
        org.apache.axis.description.ParameterDesc [] _params;
        _params = new org.apache.axis.description.ParameterDesc [] {
        };
        _oper = new org.apache.axis.description.OperationDesc("apiVersion", _params, new javax.xml.namespace.QName("", "apiVersionReturn"));
        _oper.setReturnType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        _oper.setElementQName(new javax.xml.namespace.QName("urn:http://erland.homeip.net/datacollection", "apiVersion"));
        _oper.setSoapAction("");
        _myOperationsList.add(_oper);
        if (_myOperations.get("apiVersion") == null) {
            _myOperations.put("apiVersion", new java.util.ArrayList());
        }
        ((java.util.List)_myOperations.get("apiVersion")).add(_oper);
        _params = new org.apache.axis.description.ParameterDesc [] {
            new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "application"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false),
        };
        _oper = new org.apache.axis.description.OperationDesc("getEntries", _params, new javax.xml.namespace.QName("", "getEntriesReturn"));
        _oper.setReturnType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        _oper.setElementQName(new javax.xml.namespace.QName("urn:http://erland.homeip.net/datacollection", "getEntries"));
        _oper.setSoapAction("");
        _myOperationsList.add(_oper);
        if (_myOperations.get("getEntries") == null) {
            _myOperations.put("getEntries", new java.util.ArrayList());
        }
        ((java.util.List)_myOperations.get("getEntries")).add(_oper);
        _params = new org.apache.axis.description.ParameterDesc [] {
            new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "entryId"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"), int.class, false, false),
        };
        _oper = new org.apache.axis.description.OperationDesc("getEntry", _params, new javax.xml.namespace.QName("", "getEntryReturn"));
        _oper.setReturnType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        _oper.setElementQName(new javax.xml.namespace.QName("urn:http://erland.homeip.net/datacollection", "getEntry"));
        _oper.setSoapAction("");
        _myOperationsList.add(_oper);
        if (_myOperations.get("getEntry") == null) {
            _myOperations.put("getEntry", new java.util.ArrayList());
        }
        ((java.util.List)_myOperations.get("getEntry")).add(_oper);
        _params = new org.apache.axis.description.ParameterDesc [] {
            new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "username"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false),
            new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "application"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false),
        };
        _oper = new org.apache.axis.description.OperationDesc("getCollections", _params, new javax.xml.namespace.QName("", "getCollectionsReturn"));
        _oper.setReturnType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        _oper.setElementQName(new javax.xml.namespace.QName("urn:http://erland.homeip.net/datacollection", "getCollections"));
        _oper.setSoapAction("");
        _myOperationsList.add(_oper);
        if (_myOperations.get("getCollections") == null) {
            _myOperations.put("getCollections", new java.util.ArrayList());
        }
        ((java.util.List)_myOperations.get("getCollections")).add(_oper);
        _params = new org.apache.axis.description.ParameterDesc [] {
            new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "collectionId"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"), int.class, false, false), 
        };
        _oper = new org.apache.axis.description.OperationDesc("getCollection", _params, new javax.xml.namespace.QName("", "getCollectionReturn"));
        _oper.setReturnType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        _oper.setElementQName(new javax.xml.namespace.QName("urn:http://erland.homeip.net/datacollection", "getCollection"));
        _oper.setSoapAction("");
        _myOperationsList.add(_oper);
        if (_myOperations.get("getCollection") == null) {
            _myOperations.put("getCollection", new java.util.ArrayList());
        }
        ((java.util.List)_myOperations.get("getCollection")).add(_oper);
        _params = new org.apache.axis.description.ParameterDesc [] {
            new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "username"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false),
            new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "password"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false),
            new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "firstname"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false),
            new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "lastname"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false),
            new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "email"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false),
        };
        _oper = new org.apache.axis.description.OperationDesc("registerUser", _params, new javax.xml.namespace.QName("", "registerUserReturn"));
        _oper.setReturnType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        _oper.setElementQName(new javax.xml.namespace.QName("urn:http://erland.homeip.net/datacollection", "registerUser"));
        _oper.setSoapAction("");
        _myOperationsList.add(_oper);
        if (_myOperations.get("registerUser") == null) {
            _myOperations.put("registerUser", new java.util.ArrayList());
        }
        ((java.util.List)_myOperations.get("registerUser")).add(_oper);
        _params = new org.apache.axis.description.ParameterDesc [] {
            new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "username"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false),
            new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "password"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false),
        };
        _oper = new org.apache.axis.description.OperationDesc("loginUser", _params, new javax.xml.namespace.QName("", "loginUserReturn"));
        _oper.setReturnType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        _oper.setElementQName(new javax.xml.namespace.QName("urn:http://erland.homeip.net/datacollection", "loginUser"));
        _oper.setSoapAction("");
        _myOperationsList.add(_oper);
        if (_myOperations.get("loginUser") == null) {
            _myOperations.put("loginUser", new java.util.ArrayList());
        }
        ((java.util.List)_myOperations.get("loginUser")).add(_oper);
        _params = new org.apache.axis.description.ParameterDesc [] {
            new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "username"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false),
            new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "password"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false),
            new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "application"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false),
            new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "collectionId"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"), int.class, false, false),
            new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "data"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false),
        };
        _oper = new org.apache.axis.description.OperationDesc("addDataEntry", _params, new javax.xml.namespace.QName("", "addDataEntryReturn"));
        _oper.setReturnType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        _oper.setElementQName(new javax.xml.namespace.QName("urn:http://erland.homeip.net/datacollection", "addDataEntry"));
        _oper.setSoapAction("");
        _myOperationsList.add(_oper);
        if (_myOperations.get("addDataEntry") == null) {
            _myOperations.put("addDataEntry", new java.util.ArrayList());
        }
        ((java.util.List)_myOperations.get("addDataEntry")).add(_oper);
    }

    public DataCollectionSoapBindingSkeleton() {
        this.impl = new erland.webapp.datacollection.ws.DataCollectionSoapBindingImpl();
    }

    public DataCollectionSoapBindingSkeleton(erland.webapp.datacollection.ws.DataCollection impl) {
        this.impl = impl;
    }

    public String apiVersion() throws RemoteException {
        java.lang.String ret = impl.apiVersion();
        return ret;
    }

    public java.lang.String getEntries(java.lang.String in0) throws java.rmi.RemoteException
    {
        java.lang.String ret = impl.getEntries(in0);
        return ret;
    }

    public java.lang.String getEntry(int in0) throws java.rmi.RemoteException
    {
        java.lang.String ret = impl.getEntry(in0);
        return ret;
    }

    public java.lang.String getCollections(java.lang.String in0, java.lang.String in1) throws java.rmi.RemoteException
    {
        java.lang.String ret = impl.getCollections(in0, in1);
        return ret;
    }

    public java.lang.String getCollection(int in0) throws java.rmi.RemoteException
    {
        java.lang.String ret = impl.getCollection(in0);
        return ret;
    }

    public String registerUser(String in0, String in1, String in2, String in3,String in4) throws java.rmi.RemoteException {
        java.lang.String ret = impl.registerUser(in0, in1, in2, in3, in4);
        return ret;
    }

    public String loginUser(String in0, String in1) throws java.rmi.RemoteException {
        java.lang.String ret = impl.loginUser(in0, in1);
        return ret;
    }

    public String addDataEntry(String in0, String in1, String in2, int in3, String in4) throws java.rmi.RemoteException {
        java.lang.String ret = impl.addDataEntry(in0, in1, in2, in3, in4);
        return ret;
    }

}
