package erland.webapp.cdcollection.ws;

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

public class CDCollectionSoapBindingSkeleton implements erland.webapp.cdcollection.ws.CDCollection, org.apache.axis.wsdl.Skeleton {
    private erland.webapp.cdcollection.ws.CDCollection impl;
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
            new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "username"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false), 
            new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "password"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false), 
            new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "category"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false), 
            new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "discId"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false), 
        };
        _oper = new org.apache.axis.description.OperationDesc("importMedia", _params, new javax.xml.namespace.QName("", "importMediaReturn"));
        _oper.setReturnType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        _oper.setElementQName(new javax.xml.namespace.QName("urn:http://erland.homeip.net/cdcollection", "importMedia"));
        _oper.setSoapAction("");
        _myOperationsList.add(_oper);
        if (_myOperations.get("importMedia") == null) {
            _myOperations.put("importMedia", new java.util.ArrayList());
        }
        ((java.util.List)_myOperations.get("importMedia")).add(_oper);
        _params = new org.apache.axis.description.ParameterDesc [] {
            new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "username"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false), 
            new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "password"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false), 
            new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "mediaId"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"), int.class, false, false), 
            new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "category"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false), 
            new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "discId"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false), 
        };
        _oper = new org.apache.axis.description.OperationDesc("importDisc", _params, new javax.xml.namespace.QName("", "importDiscReturn"));
        _oper.setReturnType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        _oper.setElementQName(new javax.xml.namespace.QName("urn:http://erland.homeip.net/cdcollection", "importDisc"));
        _oper.setSoapAction("");
        _myOperationsList.add(_oper);
        if (_myOperations.get("importDisc") == null) {
            _myOperations.put("importDisc", new java.util.ArrayList());
        }
        ((java.util.List)_myOperations.get("importDisc")).add(_oper);
        _params = new org.apache.axis.description.ParameterDesc [] {
            new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "username"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false), 
            new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "password"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false), 
            new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "collectionId"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"), int.class, false, false), 
            new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "mediaId"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"), int.class, false, false), 
        };
        _oper = new org.apache.axis.description.OperationDesc("addMediaToCollection", _params, new javax.xml.namespace.QName("", "addMediaToCollectionReturn"));
        _oper.setReturnType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "boolean"));
        _oper.setElementQName(new javax.xml.namespace.QName("urn:http://erland.homeip.net/cdcollection", "addMediaToCollection"));
        _oper.setSoapAction("");
        _myOperationsList.add(_oper);
        if (_myOperations.get("addMediaToCollection") == null) {
            _myOperations.put("addMediaToCollection", new java.util.ArrayList());
        }
        ((java.util.List)_myOperations.get("addMediaToCollection")).add(_oper);
        _params = new org.apache.axis.description.ParameterDesc [] {
        };
        _oper = new org.apache.axis.description.OperationDesc("getMedias", _params, new javax.xml.namespace.QName("", "getMediasReturn"));
        _oper.setReturnType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        _oper.setElementQName(new javax.xml.namespace.QName("urn:http://erland.homeip.net/cdcollection", "getMedias"));
        _oper.setSoapAction("");
        _myOperationsList.add(_oper);
        if (_myOperations.get("getMedias") == null) {
            _myOperations.put("getMedias", new java.util.ArrayList());
        }
        ((java.util.List)_myOperations.get("getMedias")).add(_oper);
        _params = new org.apache.axis.description.ParameterDesc [] {
            new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "mediaId"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"), int.class, false, false),
        };
        _oper = new org.apache.axis.description.OperationDesc("getMedia", _params, new javax.xml.namespace.QName("", "getMediaReturn"));
        _oper.setReturnType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        _oper.setElementQName(new javax.xml.namespace.QName("urn:http://erland.homeip.net/cdcollection", "getMedia"));
        _oper.setSoapAction("");
        _myOperationsList.add(_oper);
        if (_myOperations.get("getMedia") == null) {
            _myOperations.put("getMedia", new java.util.ArrayList());
        }
        ((java.util.List)_myOperations.get("getMedia")).add(_oper);
        _params = new org.apache.axis.description.ParameterDesc [] {
            new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "username"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false), 
            new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "language"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false), 
        };
        _oper = new org.apache.axis.description.OperationDesc("getCollections", _params, new javax.xml.namespace.QName("", "getCollectionsReturn"));
        _oper.setReturnType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        _oper.setElementQName(new javax.xml.namespace.QName("urn:http://erland.homeip.net/cdcollection", "getCollections"));
        _oper.setSoapAction("");
        _myOperationsList.add(_oper);
        if (_myOperations.get("getCollections") == null) {
            _myOperations.put("getCollections", new java.util.ArrayList());
        }
        ((java.util.List)_myOperations.get("getCollections")).add(_oper);
        _params = new org.apache.axis.description.ParameterDesc [] {
            new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "collectionId"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"), int.class, false, false), 
            new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "language"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false), 
        };
        _oper = new org.apache.axis.description.OperationDesc("getCollection", _params, new javax.xml.namespace.QName("", "getCollectionReturn"));
        _oper.setReturnType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        _oper.setElementQName(new javax.xml.namespace.QName("urn:http://erland.homeip.net/cdcollection", "getCollection"));
        _oper.setSoapAction("");
        _myOperationsList.add(_oper);
        if (_myOperations.get("getCollection") == null) {
            _myOperations.put("getCollection", new java.util.ArrayList());
        }
        ((java.util.List)_myOperations.get("getCollection")).add(_oper);
    }

    public CDCollectionSoapBindingSkeleton() {
        this.impl = new erland.webapp.cdcollection.ws.CDCollectionSoapBindingImpl();
    }

    public CDCollectionSoapBindingSkeleton(erland.webapp.cdcollection.ws.CDCollection impl) {
        this.impl = impl;
    }
    public int importMedia(java.lang.String in0, java.lang.String in1, java.lang.String in2, java.lang.String in3) throws java.rmi.RemoteException
    {
        int ret = impl.importMedia(in0, in1, in2, in3);
        return ret;
    }

    public int importDisc(java.lang.String in0, java.lang.String in1, int in2, java.lang.String in3, java.lang.String in4) throws java.rmi.RemoteException
    {
        int ret = impl.importDisc(in0, in1, in2, in3, in4);
        return ret;
    }

    public boolean addMediaToCollection(java.lang.String in0, java.lang.String in1, int in2, int in3) throws java.rmi.RemoteException
    {
        boolean ret = impl.addMediaToCollection(in0, in1, in2, in3);
        return ret;
    }

    public java.lang.String getMedias() throws java.rmi.RemoteException
    {
        java.lang.String ret = impl.getMedias();
        return ret;
    }

    public java.lang.String getMedia(int in0) throws java.rmi.RemoteException
    {
        java.lang.String ret = impl.getMedia(in0);
        return ret;
    }

    public java.lang.String getCollections(java.lang.String in0, java.lang.String in1) throws java.rmi.RemoteException
    {
        java.lang.String ret = impl.getCollections(in0, in1);
        return ret;
    }

    public java.lang.String getCollection(int in0, java.lang.String in1) throws java.rmi.RemoteException
    {
        java.lang.String ret = impl.getCollection(in0, in1);
        return ret;
    }

}
