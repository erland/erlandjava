/**
 * CDCollection.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis WSDL2Java emitter.
 */

package erland.webapp.cdcollection.ws;

public interface CDCollection extends java.rmi.Remote {
    public int importMedia(java.lang.String username, java.lang.String password, java.lang.String category, java.lang.String discId) throws java.rmi.RemoteException;
    public int importDisc(java.lang.String username, java.lang.String password, int mediaId, java.lang.String category, java.lang.String discId) throws java.rmi.RemoteException;
    public boolean addMediaToCollection(java.lang.String username, java.lang.String password, int collectionId, int mediaId) throws java.rmi.RemoteException;
    public java.lang.String getMedias() throws java.rmi.RemoteException;
    public java.lang.String getMedia(int mediaId) throws java.rmi.RemoteException;
    public java.lang.String getCollections(java.lang.String username, java.lang.String language) throws java.rmi.RemoteException;
    public java.lang.String getCollection(int collectionId, java.lang.String language) throws java.rmi.RemoteException;
}
