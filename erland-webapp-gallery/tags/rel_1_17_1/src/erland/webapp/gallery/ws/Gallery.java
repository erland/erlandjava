/**
 * Gallery.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis WSDL2Java emitter.
 */

package erland.webapp.gallery.ws;

public interface Gallery extends java.rmi.Remote {
    public boolean importPictures(java.lang.String username, java.lang.String password, java.lang.String gallery, boolean clearCategories, boolean clearPictures, boolean localLinks, boolean filenameAsPictureTitle, boolean filenameAsPictureDescription, java.lang.String pictures) throws java.rmi.RemoteException;
    public java.lang.String getGalleries(java.lang.String username, java.lang.String language) throws java.rmi.RemoteException;
    public java.lang.String getCategories(int galleryId, java.lang.String language) throws java.rmi.RemoteException;
    public java.lang.String getPicturesForGallery(int galleryId, int start, int max, java.lang.String language) throws java.rmi.RemoteException;
    public java.lang.String getPicturesForCategory(int galleryId, int categoryId, int start, int max, java.lang.String language) throws java.rmi.RemoteException;
}
