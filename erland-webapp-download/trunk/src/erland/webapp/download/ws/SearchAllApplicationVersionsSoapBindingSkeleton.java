package erland.webapp.download.ws;

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

public class SearchAllApplicationVersionsSoapBindingSkeleton implements erland.webapp.download.ws.SearchAllApplicationVersions, org.apache.axis.wsdl.Skeleton {
    private erland.webapp.download.ws.SearchAllApplicationVersions impl;
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
            new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "language"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false),
        };
        _oper = new org.apache.axis.description.OperationDesc("search", _params, new javax.xml.namespace.QName("", "searchReturn"));
        _oper.setReturnType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        _oper.setElementQName(new javax.xml.namespace.QName("http://erland.homeip.net/download", "search"));
        _oper.setSoapAction("");
        _myOperationsList.add(_oper);
        if (_myOperations.get("search") == null) {
            _myOperations.put("search", new java.util.ArrayList());
        }
        ((java.util.List)_myOperations.get("search")).add(_oper);
    }

    public SearchAllApplicationVersionsSoapBindingSkeleton() {
        this.impl = new erland.webapp.download.ws.SearchAllApplicationVersionsSoapBindingImpl();
    }

    public SearchAllApplicationVersionsSoapBindingSkeleton(erland.webapp.download.ws.SearchAllApplicationVersions impl) {
        this.impl = impl;
    }
    public java.lang.String search(java.lang.String language) throws java.rmi.RemoteException
    {
        java.lang.String ret = impl.search(language);
        return ret;
    }

}
