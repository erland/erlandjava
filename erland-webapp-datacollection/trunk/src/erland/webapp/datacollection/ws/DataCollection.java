/**
 * DataCollection.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis WSDL2Java emitter.
 */

package erland.webapp.datacollection.ws;

public interface DataCollection extends java.rmi.Remote {
    public java.lang.String apiVersion() throws java.rmi.RemoteException;
    public java.lang.String registerUser(java.lang.String username, java.lang.String password, java.lang.String firstName, java.lang.String lastName, java.lang.String eMail) throws java.rmi.RemoteException;
    public java.lang.String loginUser(java.lang.String username, java.lang.String password) throws java.rmi.RemoteException;
    public java.lang.String addDataEntry(java.lang.String username, java.lang.String password,java.lang.String application, int collectionId,int overwrite,java.lang.String data) throws java.rmi.RemoteException;
    public java.lang.String addVersionedDataEntry(java.lang.String username, java.lang.String password,java.lang.String application, int collectionId,int overwrite,int version,java.lang.String data) throws java.rmi.RemoteException;
    public java.lang.String getEntries(java.lang.String application) throws java.rmi.RemoteException;
    public java.lang.String getVersionedEntries(java.lang.String application,int version) throws java.rmi.RemoteException;
    public java.lang.String getEntry(int entryId) throws java.rmi.RemoteException;
    public java.lang.String getVersionedEntry(int entryId,int version) throws java.rmi.RemoteException;
    public java.lang.String getCollections(java.lang.String username, java.lang.String application) throws java.rmi.RemoteException;
    public java.lang.String getCollection(int collectionId) throws java.rmi.RemoteException;
    public java.lang.String getVersionedCollection(int collectionId, int version) throws java.rmi.RemoteException;
}
