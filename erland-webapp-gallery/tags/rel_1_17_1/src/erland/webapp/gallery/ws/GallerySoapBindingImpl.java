package erland.webapp.gallery.ws;

import org.apache.axis.MessageContext;
import org.apache.commons.logging.LogFactory;
import org.apache.commons.logging.Log;

import javax.servlet.http.HttpServletRequest;
import java.io.*;
import java.net.URL;
import java.net.URLConnection;
import java.net.HttpURLConnection;

import erland.util.StringUtil;

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

public class GallerySoapBindingImpl implements erland.webapp.gallery.ws.Gallery{
    /** Logging instance */
    private static Log LOG = LogFactory.getLog(GallerySoapBindingImpl.class);
    public boolean importPictures(java.lang.String username, java.lang.String password, java.lang.String gallery, boolean clearCategories, boolean clearPictures, boolean localLinks, boolean filenameAsPictureTitle, boolean filenameAsPictureDescription, java.lang.String pictures) throws java.rmi.RemoteException {
        MessageContext context = MessageContext.getCurrentContext();
        HttpServletRequest request = (HttpServletRequest) context.getProperty("transport.http.servletRequest");
        try {
            LOG.debug("username="+username);
            LOG.debug("password="+password);
            LOG.debug("clearPictures="+clearPictures);
            LOG.debug("clearCategories="+clearCategories);
            LOG.debug("localLinks="+localLinks);
            LOG.debug("filenameAsPictureDescription="+filenameAsPictureDescription);
            LOG.debug("filenameAsPictureTitle="+filenameAsPictureTitle);
            LOG.debug("gallery="+gallery);
            LOG.trace("pictures="+pictures);
            String port = "";
            if(request.getServerPort()!=80) {
                port=":"+request.getServerPort();
            }
            URLConnection connection = new URL("http://"+request.getServerName()+port+request.getContextPath()+"/do/ws/importpictures?user="+username+"&clearCategoriesDisplay="+clearCategories+"&clearPicturesDisplay="+clearPictures+"&localLinksDisplay="+localLinks+"&filenameAsPictureDescriptionDisplay="+filenameAsPictureDescription+"&filenameAsPictureTitleDisplay="+filenameAsPictureTitle+"&pass="+password+"&gallery="+gallery).openConnection();
            connection.setDoOutput(true);
            connection.setUseCaches(false);
            connection.setRequestProperty("Content-Type","application/x-www-form-urlencoded");
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(connection.getOutputStream()));
            writer.write(pictures);
            writer.flush();
            writer.close();
            return true;
        } catch (IOException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
            throw new java.rmi.RemoteException("Unable to import pictures",e);
        }
    }

    public java.lang.String getGalleries(java.lang.String username,java.lang.String language) throws java.rmi.RemoteException {
        MessageContext context = MessageContext.getCurrentContext();
        HttpServletRequest request = (HttpServletRequest) context.getProperty("transport.http.servletRequest");
        try {
            if(StringUtil.asNull(language)!=null) {
                language = "&language="+language;
            }
            String port = "";
            if(request.getServerPort()!=80) {
                port=":"+request.getServerPort();
            }
            BufferedReader reader = new BufferedReader(new InputStreamReader(new URL("http://"+request.getServerName()+port+request.getContextPath()+"/do/ws/getgalleries?user="+username+StringUtil.asEmpty(language)).openStream()));
            StringBuffer sb = new StringBuffer(100000);
            int character = reader.read();
            while(character>=0) {
                sb.append((char)character);
                character = reader.read();
            }
            return sb.toString();
        } catch (IOException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
            throw new java.rmi.RemoteException("Unable to load data",e);
        }
    }

    public java.lang.String getCategories(int galleryId, java.lang.String language) throws java.rmi.RemoteException {
        MessageContext context = MessageContext.getCurrentContext();
        HttpServletRequest request = (HttpServletRequest) context.getProperty("transport.http.servletRequest");
        try {
            if(StringUtil.asNull(language)!=null) {
                language = "&language="+language;
            }
            String port = "";
            if(request.getServerPort()!=80) {
                port=":"+request.getServerPort();
            }
            BufferedReader reader = new BufferedReader(new InputStreamReader(new URL("http://"+request.getServerName()+port+request.getContextPath()+"/do/ws/getcategories?galleryDisplay="+galleryId+StringUtil.asEmpty(language)).openStream()));
            StringBuffer sb = new StringBuffer(100000);
            int character = reader.read();
            while(character>=0) {
                sb.append((char)character);
                character = reader.read();
            }
            return sb.toString();
        } catch (IOException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
            throw new java.rmi.RemoteException("Unable to load data",e);
        }
    }

    public java.lang.String getPicturesForGallery(int galleryId, int start, int max,java.lang.String language) throws java.rmi.RemoteException {
        MessageContext context = MessageContext.getCurrentContext();
        HttpServletRequest request = (HttpServletRequest) context.getProperty("transport.http.servletRequest");
        try {
            if(StringUtil.asNull(language)!=null) {
                language = "&language="+language;
            }
            String port = "";
            if(request.getServerPort()!=80) {
                port=":"+request.getServerPort();
            }
            BufferedReader reader = new BufferedReader(new InputStreamReader(new URL("http://"+request.getServerName()+port+request.getContextPath()+"/do/ws/getpicturesforgallery?galleryDisplay="+galleryId+"&start="+start+"&max="+max+StringUtil.asEmpty(language)).openStream()));
            StringBuffer sb = new StringBuffer(100000);
            int character = reader.read();
            while(character>=0) {
                sb.append((char)character);
                character = reader.read();
            }
            return sb.toString();
        } catch (IOException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
            throw new java.rmi.RemoteException("Unable to load data",e);
        }
    }

    public java.lang.String getPicturesForCategory(int galleryId, int categoryId, int start, int max, java.lang.String language) throws java.rmi.RemoteException {
        MessageContext context = MessageContext.getCurrentContext();
        HttpServletRequest request = (HttpServletRequest) context.getProperty("transport.http.servletRequest");
        try {
            if(StringUtil.asNull(language)!=null) {
                language = "&language="+language;
            }
            String port = "";
            if(request.getServerPort()!=80) {
                port=":"+request.getServerPort();
            }
            BufferedReader reader = new BufferedReader(new InputStreamReader(new URL("http://"+request.getServerName()+port+request.getContextPath()+"/do/ws/getpicturesforcategory?galleryDisplay="+galleryId+"&categoryDisplay="+categoryId+"&start="+start+"&max="+max+StringUtil.asEmpty(language)).openStream()));
            StringBuffer sb = new StringBuffer(100000);
            int character = reader.read();
            while(character>=0) {
                sb.append((char)character);
                character = reader.read();
            }
            return sb.toString();
        } catch (IOException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
            throw new java.rmi.RemoteException("Unable to load data",e);
        }
    }
}
